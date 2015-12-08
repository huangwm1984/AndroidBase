package com.android.test.download.bizs;


import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.android.test.download.db.dao.DLInfoDao;
import com.android.test.download.db.dao.DLThreadInfoDao;
import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.UUID;

import static com.android.test.download.bizs.DLCons.DBCons;
import static com.android.test.download.bizs.DLCons.DLState;
import static com.android.test.download.bizs.DLCons.DLDescription;
import static com.android.test.download.bizs.DLCons.Base.DEFAULT_TIMEOUT;
import static com.android.test.download.bizs.DLCons.Base.LENGTH_PER_THREAD;
import static com.android.test.download.bizs.DLCons.Base.MAX_REDIRECTS;
import static com.android.test.download.bizs.DLCons.Code.HTTP_MOVED_PERM;
import static com.android.test.download.bizs.DLCons.Code.HTTP_MOVED_TEMP;
import static com.android.test.download.bizs.DLCons.Code.HTTP_NOT_MODIFIED;
import static com.android.test.download.bizs.DLCons.Code.HTTP_OK;
import static com.android.test.download.bizs.DLCons.Code.HTTP_PARTIAL;
import static com.android.test.download.bizs.DLCons.Code.HTTP_SEE_OTHER;
import static com.android.test.download.bizs.DLCons.Code.HTTP_TEMP_REDIRECT;
import static com.android.test.download.bizs.DLError.ERROR_OPEN_CONNECT;

class DLTask implements Runnable, IDLThreadListener {
    private static final String TAG = DLTask.class.getSimpleName();

    private DLInfo info;
    private Context context;

    private int totalProgress;
    private int count;
    private long lastTime = System.currentTimeMillis();

    private DLInfoDao mDLInfoDao;
    private DLThreadInfoDao mDLThreadInfoDao;
    //开始下载时间，用户计算加载速度
    private long mPreviousTime;

    DLTask(Context context, DLInfo info, DLInfoDao infoDao, DLThreadInfoDao threadInfoDao) {
        this.info = info;
        this.context = context;
        this.totalProgress = info.currentBytes;
        this.mDLInfoDao = infoDao;
        this.mDLThreadInfoDao = threadInfoDao;
        //if (!info.isResume) DLDBManager.getInstance(context).insertTaskInfo(info);
        if (!info.isResume) {
            try {
                mDLInfoDao.save(info);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void onProgress(int progress) {
        //已下载大小
        totalProgress += progress;
        info.setProgress(totalProgress);
        info.setCurrentBytes(totalProgress);

        //计算下载速度
        long totalTime = (System.currentTimeMillis() - mPreviousTime)/1000;
        if ( totalTime == 0 ) {
            totalTime += 1;
        }
        long networkSpeed = totalProgress / totalTime;
        info.setNetworkSpeed(networkSpeed);

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > 1000 || totalProgress == info.totalBytes) {
            //LogUtils.e(info.appName + "  下载进度：" + totalProgress);
            try {
                mDLInfoDao.update(info);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (info.hasListener) info.listener.onProgress(info);
            lastTime = currentTime;
        }
    }

    @Override
    public synchronized void onStop(DLThreadInfo threadInfo) {
        //DLDBManager.getInstance(context).updateThreadInfo(threadInfo);
        try {
            mDLThreadInfoDao.update(threadInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        count++;
        if (count >= info.threads.size()) {
            LogUtils.e("All the threads was stopped.");
            info.currentBytes = totalProgress;
            info.state = DLState.PAUSE;
            DLManager.getInstance(context).addStopTask(info).removeDLTask(info.baseUrl);
            //DLDBManager.getInstance(context).updateTaskInfo(info);
            try {
                mDLInfoDao.update(info);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count = 0;
            if (info.hasListener) info.listener.onStop(info);
        }
    }

    @Override
    public synchronized void onFinish(DLThreadInfo threadInfo) {
        LogUtils.e("已经完成："+info.appName+"  进度："+info.progress+"  "+threadInfo);
        if (null == threadInfo) {
            if (info.hasListener) {
                try {
                    info.state = DLState.COMPLETE;
                    mDLInfoDao.update(info);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                info.listener.onProgress(info);
                info.listener.onFinish(info);
            }
            return;
        }
        info.removeDLThread(threadInfo);
        //DLDBManager.getInstance(context).deleteThreadInfo(threadInfo.id);
        try {
            mDLThreadInfoDao.delete(DBCons.TB_THREAD_ID, threadInfo.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LogUtils.e("Thread size " + info.threads.size());
        if (info.threads.isEmpty()) {
            LogUtils.e("Task was finished.");
            DLManager.getInstance(context).removeDLTask(info.baseUrl);
            //DLDBManager.getInstance(context).deleteTaskInfo(info.baseUrl);
            try {
                info.state = DLState.COMPLETE;
                mDLInfoDao.update(info);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LogUtils.e("info.hasListener="+info.hasListener);
            if (info.hasListener) {
                info.listener.onProgress(info);
                info.listener.onFinish(info);
            }
            DLManager.getInstance(context).addDLTask();
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (info.redirect < MAX_REDIRECTS) {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(info.realUrl).openConnection();
                conn.setInstanceFollowRedirects(false);
                conn.setConnectTimeout(DEFAULT_TIMEOUT);
                conn.setReadTimeout(DEFAULT_TIMEOUT);

                addRequestHeaders(conn);

                final int code = conn.getResponseCode();
                switch (code) {
                    case HTTP_OK:
                    case HTTP_PARTIAL:
                        dlInit(conn, code);
                        return;
                    case HTTP_MOVED_PERM:
                    case HTTP_MOVED_TEMP:
                    case HTTP_SEE_OTHER:
                    case HTTP_NOT_MODIFIED:
                    case HTTP_TEMP_REDIRECT:
                        final String location = conn.getHeaderField("location");
                        if (TextUtils.isEmpty(location))
                            throw new DLException(
                                    "Can not obtain real url from location in header.");
                        info.realUrl = location;
                        info.redirect++;
                        continue;
                    default:
                        if (info.hasListener)
                            info.listener.onError(code, conn.getResponseMessage(), info);
                        DLManager.getInstance(context).removeDLTask(info.baseUrl);
                        info.state = DLState.FAIL;
                        try {
                            mDLInfoDao.update(info);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        return;
                }
            } catch (Exception e) {
                if (info.hasListener) info.listener.onError(ERROR_OPEN_CONNECT, e.toString(), info);
                DLManager.getInstance(context).removeDLTask(info.baseUrl);
                info.state = DLState.FAIL;
                try {
                    mDLInfoDao.update(info);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                return;
            } finally {
                if (null != conn) conn.disconnect();
            }
        }
        throw new RuntimeException("Too many redirects");
    }

    private void dlInit(HttpURLConnection conn, int code) throws Exception {
        readResponseHeaders(conn);
        //DLDBManager.getInstance(context).updateTaskInfo(info);
        try {
            LogUtils.e("保存数据库前安装包的大小："+info.totalBytes);
            mDLInfoDao.update(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!DLUtil.createFile(info.dirPath, info.fileName))
            throw new DLException("Can not create file");
        info.file = new File(info.dirPath, info.fileName);
        //if (info.file.exists() && info.file.length() == info.totalBytes) {
        //    Log.d(TAG, "The file which we want to download was already here.");
        //    return;
        //}
        if (info.hasListener) info.listener.onStart(info);
        switch (code) {
            case HTTP_OK:
                mPreviousTime = System.currentTimeMillis();
                dlData(conn);
                break;
            case HTTP_PARTIAL:
                if (info.totalBytes <= 0) {
                    mPreviousTime = System.currentTimeMillis();
                    dlData(conn);
                    break;
                }
                if (info.isResume) {
                    mPreviousTime = System.currentTimeMillis();
                    for (DLThreadInfo threadInfo : info.threads) {
                        DLManager.getInstance(context)
                                .addDLThread(new DLThread(threadInfo, info, this));
                    }
                    break;
                }
                dlDispatch();
                break;
        }
    }

    private void dlDispatch() {
        int threadSize;
        int threadLength = LENGTH_PER_THREAD;
        if (info.totalBytes <= LENGTH_PER_THREAD) {
            threadSize = 2;
            threadLength = info.totalBytes / threadSize;
        } else {
            threadSize = info.totalBytes / LENGTH_PER_THREAD;
        }
        int remainder = info.totalBytes % threadLength;
        for (int i = 0; i < threadSize; i++) {
            int start = i * threadLength;
            int end = start + threadLength - 1;
            if (i == threadSize - 1) {
                end = start + threadLength + remainder;
            }
            DLThreadInfo threadInfo =
                    new DLThreadInfo(UUID.randomUUID().toString(), info.baseUrl, start, end);
            info.addDLThread(threadInfo);
            //DLDBManager.getInstance(context).insertThreadInfo(threadInfo);
            try {
                mDLThreadInfoDao.save(threadInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mPreviousTime = System.currentTimeMillis();
            DLManager.getInstance(context).addDLThread(new DLThread(threadInfo, info, this));
        }
    }

    private void dlData(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        FileOutputStream fos = new FileOutputStream(info.file);
        byte[] b = new byte[4096];
        int len;
        while ((len = is.read(b)) != -1) {
            fos.write(b, 0, len);
            onProgress(len);
        }
        onFinish(null);
        fos.close();
        is.close();
    }

    private void addRequestHeaders(HttpURLConnection conn) {
        for (DLHeader header : info.requestHeaders) {
            conn.addRequestProperty(header.key, header.value);
        }
    }

    private void readResponseHeaders(HttpURLConnection conn) {
        info.disposition = conn.getHeaderField("Content-Disposition");
        info.location = conn.getHeaderField("Content-Location");
        info.mimeType = DLUtil.normalizeMimeType(conn.getContentType());
        final String transferEncoding = conn.getHeaderField("Transfer-Encoding");
        if (TextUtils.isEmpty(transferEncoding)) {
            try {
                info.totalBytes = Integer.parseInt(conn.getHeaderField("Content-Length"));
                LogUtils.e("安装包的大小："+info.totalBytes);
            } catch (NumberFormatException e) {
                info.totalBytes = -1;
            }
        } else {
            info.totalBytes = -1;
        }
        if (info.totalBytes == -1 && (TextUtils.isEmpty(transferEncoding) ||
                !transferEncoding.equalsIgnoreCase("chunked")))
            throw new RuntimeException("Can not obtain size of download file.");
        if (TextUtils.isEmpty(info.fileName))
            info.fileName = DLUtil.obtainFileName(info.realUrl, info.disposition, info.location);
    }
}