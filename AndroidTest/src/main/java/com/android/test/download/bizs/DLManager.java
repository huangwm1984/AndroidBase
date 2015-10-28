package com.android.test.download.bizs;

import android.content.Context;

import com.android.base.common.thread.SmartExecutor;
import com.android.test.download.bean.DownLoadBean;
import com.android.test.download.cons.HttpConnPars;
import com.android.test.download.cons.PublicCons;
import com.android.test.download.dao.DownLoadDao;
import com.android.test.download.interfaces.DLTaskListener;
import com.android.test.download.interfaces.IDLThreadListener;
import com.android.test.download.utils.FileUtil;
import com.android.test.download.utils.NetUtil;
import com.apkfuns.logutils.LogUtils;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 下载管理器
 * Download manager
 * 执行具体的下载操作
 *
 */
public final class DLManager {

    private static DLManager mInstance;
    /**
     * 任务列表
     */
    private static ConcurrentHashMap<String, DLTask> mTaskDLing;


    private SmartExecutor mExecutor;
    private Context mContext;
    private DownLoadDao mDownLoadDao;

    public DLManager(Context context) {
        mContext = context;
        mExecutor = new SmartExecutor();
        mTaskDLing = new ConcurrentHashMap<String, DLTask>();
        mDownLoadDao = new DownLoadDao(mContext, DownLoadBean.class);

    }

    public static DLManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DLManager.class) {
                if (mInstance == null) {
                    mInstance = new DLManager(context);
                }
            }
        }
        return mInstance;
    }

    public void dlStart(String url, String dirPath, DLTaskListener listener) {
        DLPrepare dlPrepare = new DLPrepare(url, dirPath, listener);
        mExecutor.execute(dlPrepare);
    }

    public void dlStop(String url) {
        synchronized (mTaskDLing){
            if (mTaskDLing.containsKey(url)) {
                DLTask task = mTaskDLing.get(url);
                task.setStop(true);
                mTaskDLing.remove(url);
            }
        }
    }

    public void dlCancel(String url) {
        dlStop(url);
        /*if (null != sDBManager.queryTaskInfoByUrl(url)) {
            sDBManager.deleteTaskInfo(url);
            List<ThreadInfo> infos = sDBManager.queryThreadInfos(url);
            if (null != infos && infos.size() != 0) {
                sDBManager.deleteThreadInfos(url);
            }
        }*/
        try {
            if (null != mDownLoadDao.query(PublicCons.DBCons.TB_TASK_URL_BASE, url)) {
                mDownLoadDao.delete(new String[]{PublicCons.DBCons.TB_TASK_URL_BASE}, new String[]{url});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件已经开始下载错误提示
     */
    public static final String ERROR_DOWNLOADING = "File is downloading";
    /**
     * 下载失败：没有网络 错误提示
     */
    public static final String ERROR_NO_NETWORK = "no_network";

    private class DLPrepare implements Runnable {
        private String url, dirPath;// 下载路径和保存目录
        private DLTaskListener listener;// 下载监听器

        private DLPrepare(String url, String dirPath, DLTaskListener listener) {
            this.url = url;
            this.dirPath = dirPath;
            this.listener = listener;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            try {
                String realUrl = url;
                conn = NetUtil.buildConnection(url);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestProperty(HttpConnPars.REFERER.content, url);
                if (conn.getResponseCode() == HttpStatus.SC_MOVED_TEMPORARILY ||
                        conn.getResponseCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
                    realUrl = conn.getHeaderField(HttpConnPars.LOCATION.content);
                }
                synchronized (mTaskDLing){//fix: 如果文件正在取消或异常，这里不能立即重新开始，表现为当多次点击下载时：1. 同时引发多个任务下载；2. 点击无效且无任何返回值；需要进行并发线程的业务处理；
                    // 如果文件正在下载
                    if (mTaskDLing.containsKey(url)) {
                        // 文件正在下载 File is downloading
                        if(isDebug){
                            LogUtils.d("DLPrepare File is downloading ,url:"+url);
                        }
                        if(listener!=null)listener.onError(ERROR_DOWNLOADING);
                    } else {
                        //TaskInfo info = sDBManager.queryTaskInfoByUrl(url);
                        //DownLoadBean info = (DownLoadBean) mDownLoadDao.query(PublicCons.DBCons.TB_TASK_URL_BASE, url);
                        DownLoadBean info = mDownLoadDao.getDao().queryBuilder().where().eq(PublicCons.DBCons.TB_TASK_URL_BASE, url).queryForFirst();
                        String fileName = FileUtil.getFileNameFromUrl(realUrl).replace("/", "");
                        if (null != listener) listener.onStart(fileName, realUrl);
                        File file = new File(dirPath, fileName);
                        if (null == info /*|| !file.exists()*/) {
                            //info = new TaskInfo(FileUtil.createFile(dirPath, fileName), url, realUrl, 0, 0,threadNum);
                            info = new DownLoadBean();
                            info.setId(UUID.randomUUID().toString());
                            info.setDlLocalFile(FileUtil.createFile(dirPath, fileName).toString());
                            info.setBaseUrl(url);
                            info.setRealUrl(realUrl);
                            info.setStart(0);
                            info.setEnd(0);
                            info.setProgress(0);
                            info.setState(PublicCons.DownLoadState.DOWNLOAD_START);
                            info.setLength(0);
                            mDownLoadDao.save(info);
                        }else{
                            if(info.getState() == PublicCons.DownLoadState.DOWNLOAD_SUCCESS){
                                LogUtils.d("DLPrepare file has downloaded,need no thread ,url:"+info.baseUrl);
                               return;
                            }else{
                                info.setState(PublicCons.DownLoadState.DOWNLOAD_START);
                                mDownLoadDao.update(info);
                            }
                        }
                        DLTask task = new DLTask(info, listener);
                        mTaskDLing.put(info.baseUrl, task);
                        mExecutor.execute(task);
                        if(isDebug){
                            LogUtils.d("DLPrepare File begin new task ,url:"+url);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (null != conn) {
                    conn.disconnect();
                }
            }
        }
    }

    private class DLTask implements Runnable, IDLThreadListener {
        private static final int LENGTH_PER_THREAD = 2097152;

        private DownLoadBean info;
        private DLTaskListener mListener;

        private int totalProgress, fileLength;
        private int totalProgressIn100;
        private boolean isResume;
        private boolean isStop;
        private boolean isExists;
        private boolean isConnect = true;

        private List<DownLoadBean> mThreadInfos;

        private DLTask(DownLoadBean info, DLTaskListener listener) {
            this.info = info;
            this.mListener = listener;
            this.totalProgress = info.progress;
            this.fileLength = info.length;

            /*if (null != sDBManager.queryTaskInfoByUrl(info.baseUrl)) {
                if (!info.dlLocalFile.exists()) {
                    sDBManager.deleteTaskInfo(info.baseUrl);
                }
                mThreadInfos = sDBManager.queryThreadInfos(info.baseUrl);
                if (null != mThreadInfos && mThreadInfos.size() != 0) {
                    isResume = true;
                } else {
                    sDBManager.deleteTaskInfo(info.baseUrl);
                }
            }*/

            try {
                mThreadInfos = mDownLoadDao.query(PublicCons.DBCons.TB_TASK_URL_BASE, info.baseUrl);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (null != mThreadInfos && mThreadInfos.size() != 0) {
                isResume = true;
            } else {
                try {
                    mDownLoadDao.delete(mThreadInfos);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setStop(boolean isStop) {
            this.isStop = isStop;
        }

        @Override
        public void run() {
            if (NetUtil.getNetWorkType(mContext) == PublicCons.NetType.INVALID) {
                if (null != mListener)
                    mListener.onConnect(PublicCons.NetType.INVALID, "无网络连接");
                isConnect = false;
            } else if (NetUtil.getNetWorkType(mContext) == PublicCons.NetType.NO_WIFI) {
                if (null != mListener)
                    isConnect = mListener.onConnect(PublicCons.NetType.NO_WIFI, "正在使用非WIFI网络下载");
            }
            if (isConnect) {
                if (isResume) {
                    for (DownLoadBean i : mThreadInfos) {
                        mExecutor.execute(new DLThread(i, this));
                        if(isDebug){
                            LogUtils.d("DLTask resume thread:"+i+" ,url:"+info.baseUrl);
                        }
                    }
                } else {
                    HttpURLConnection conn = null;
                    try {
                        conn = NetUtil.buildConnection(info.realUrl);
                        conn.setRequestProperty("Range", "bytes=" + 0 + "-" + Integer.MAX_VALUE);
                        if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                            if(isDebug){
                                LogUtils.d("DLTask has 206 ,url:"+info.baseUrl);
                            }
                            fileLength = conn.getContentLength();
                            File file = new File(info.dlLocalFile);
                            if (file.exists() && file.length() == fileLength) {
                                isExists = true;
                                mTaskDLing.remove(info.baseUrl);
                                info.setLength(fileLength);
                                info.setState(PublicCons.DownLoadState.DOWNLOAD_SUCCESS);
                                mDownLoadDao.update(info);
                                if (null != mListener) mListener.onFinish(file);
                            }
                            if (!isExists) {
                                info.length = fileLength;
                                //sDBManager.insertTaskInfo(info);
                                //mDownLoadDao.update(info);
                                int threadSize;
                                int length = LENGTH_PER_THREAD;
//                                if (fileLength <= LENGTH_PER_THREAD) {
//                                    threadSize = 3;
//                                    length = fileLength / threadSize;
//                                } else {
//                                    threadSize = fileLength / LENGTH_PER_THREAD;
//                                }
                                //不建议设定过多线程，根据手机硬件及系统调度特定，最好和cpu核数匹配；
                                threadSize = mExecutor.getCoreSize();
                                length = fileLength / threadSize;
                                int remainder = fileLength % length;
                                if(isDebug){
                                    LogUtils.d("DLTask has multiThread begin,threadSize:"+threadSize+";prelength:"+length+" ;url:"+info.baseUrl);
                                }
                                for (int i = 0; i < threadSize; i++) {
                                    int start = i * length;
                                    int end = start + length - 1;
                                    if (i == threadSize - 1) {
                                        end = start + length + remainder;
                                    }
                                    //String id = UUID.randomUUID().toString();
                                    //ThreadInfo ti = new ThreadInfo(info.dlLocalFile,
                                    //        info.baseUrl, info.realUrl, start, end, id);
                                    info.setStart(start);
                                    info.setEnd(end);
                                    mDownLoadDao.update(info);

                                    mExecutor.execute(new DLThread(info, this));
                                    if(isDebug){
                                        LogUtils.d("DLTask begin thread:"+i+" ,url:"+info.baseUrl);
                                    }
                                }
                            }
                        } else if (conn.getResponseCode() == HttpStatus.SC_OK) {
                            if(isDebug){
                                LogUtils.d("DLTask has 200 ,url:"+info.baseUrl);
                            }
                            fileLength = conn.getContentLength();
                            File dlLocalFile = new File(info.dlLocalFile);
                            if (dlLocalFile.exists() && dlLocalFile.length() == fileLength) {
                                mTaskDLing.remove(info.baseUrl);
                                info.setLength(fileLength);
                                info.setState(PublicCons.DownLoadState.DOWNLOAD_SUCCESS);
                                mDownLoadDao.update(info);
                                if (null != mListener) mListener.onFinish(dlLocalFile);
                                if(isDebug){
                                    LogUtils.d("DLTask  file has downloaded,need no thread ,url:"+info.baseUrl);
                                }
                            } else {
                                //ThreadInfo ti = new ThreadInfo(info.dlLocalFile, info.baseUrl,
                                //        info.realUrl, 0, fileLength, UUID.randomUUID().toString());
                                info.setStart(0);
                                info.setEnd(fileLength);
                                mDownLoadDao.update(info);
                                mExecutor.execute(new DLThread(info, this));
                                if(isDebug){
                                    LogUtils.d("DLTask begin single thread ,url:"+info.baseUrl);
                                }
                            }
                        }
                    } catch (Exception e) {
                        /*if (null != sDBManager.queryTaskInfoByUrl(info.baseUrl)) {
                            info.progress = totalProgress;
                            sDBManager.updateTaskInfo(info);
                            dlStop(info.baseUrl);
                            this.setStop(true);
                        }*/
                        try {
                            if (null != mDownLoadDao.query(PublicCons.DBCons.TB_TASK_URL_BASE, info.baseUrl)) {
                                info.progress = totalProgress;
                                info.setState(PublicCons.DownLoadState.DOWNLOAD_FAIL);
                                mDownLoadDao.update(info);
                                dlStop(info.baseUrl);
                                this.setStop(true);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        if(isDebug){
                            LogUtils.e("DLTask running error:"+e+",url:" + info.baseUrl);
                            e.printStackTrace();
                        }
                        if (null != mListener) mListener.onError(e.getMessage());
                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }
                }
            }else{
                //下载失败：网络异常
                try {
                    info.setState(PublicCons.DownLoadState.DOWNLOAD_FAIL);
                    mDownLoadDao.update(info);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dlStop(info.baseUrl);
                if(isDebug){
                    LogUtils.e("DLTask no network error ,url:" + info.baseUrl);
                }
                if (null != mListener) mListener.onError(ERROR_NO_NETWORK);
            }
        }

        @Override
        public void onThreadProgress(int progress) {
            synchronized (this) {
                totalProgress += progress;
                int tmp = (int) (totalProgress * 1.0 / fileLength * 100);
                if (null != mListener && tmp != totalProgressIn100) {
                    mListener.onProgress(tmp);
                    totalProgressIn100 = tmp;
                }
                if (fileLength == totalProgress) {
                    //sDBManager.deleteTaskInfo(info.baseUrl);
                    try {
                        mTaskDLing.remove(info.baseUrl);
                        info.setState(PublicCons.DownLoadState.DOWNLOAD_SUCCESS);
                        mDownLoadDao.update(info);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (null != mListener) mListener.onFinish(new File(info.dlLocalFile));
                    if(isDebug){
                        LogUtils.d("onThreadProgress has download finish ,url:"+info.baseUrl);
                    }
                }
                if (isStop) {
                    info.progress = totalProgress;
                    //sDBManager.updateTaskInfo(info);
                    try {
                        info.setState(PublicCons.DownLoadState.DOWNLOAD_STOP);
                        mDownLoadDao.update(info);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(isDebug){
                        LogUtils.d("onThreadProgress has stop ,url:"+info.baseUrl);
                    }
                }
            }
        }

        private class DLThread implements Runnable {
            private DownLoadBean info;
            private IDLThreadListener mListener;

            private int progress;

            public DLThread(DownLoadBean info, IDLThreadListener listener) {
                this.info = info;
                this.mListener = listener;
            }

            @Override
            public void run() {
                HttpURLConnection conn = null;
                RandomAccessFile raf = null;
                InputStream is = null;
                try {
                    conn = NetUtil.buildConnection(info.realUrl);
                    conn.setRequestProperty("Range", "bytes=" + info.start + "-" + info.end);

                    raf = new RandomAccessFile(info.dlLocalFile,
                            PublicCons.AccessModes.ACCESS_MODE_RWD);
                    if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                        if(isDebug){
                            LogUtils.d("DLThread has 206 ,url:"+info.baseUrl);
                        }
                        if (!isResume) {
                            //sDBManager.insertThreadInfo(info);
                            mDownLoadDao.saveOrUpdate(info);
                        }
                        is = conn.getInputStream();
                        raf.seek(info.start);
                        int total = info.end - info.start;
                        byte[] b = new byte[1024];
                        int len;
                        while (!isStop && (len = is.read(b)) != -1) {
                            raf.write(b, 0, len);
                            progress += len;
                            mListener.onThreadProgress(len);
                            if (progress >= total) {
                                //sDBManager.deleteThreadInfoById(info.id);
                                mTaskDLing.remove(info.baseUrl);
                                info.setState(PublicCons.DownLoadState.DOWNLOAD_SUCCESS);
                                mDownLoadDao.update(info);
                            }
                        }
                        /*if (isStop && null != sDBManager.queryThreadInfoById(info.id)) {
                            mListener.onThreadProgress(0);
                            info.start = info.start + progress;
                            sDBManager.updateThreadInfo(info);
                            if(isDebug){
                                LogUtils.d(TAG,"DLThread "+info.id+" has stop ,url:"+info.baseUrl);
                            }
                        }*/
                        if (isStop && null != mDownLoadDao.query(PublicCons.DBCons.TB_TASK_ID, info.id)) {
                            mListener.onThreadProgress(0);
                            info.start = info.start + progress;
                            info.setState(PublicCons.DownLoadState.DOWNLOAD_STOP);
                            mDownLoadDao.update(info);
                            if(isDebug){
                                LogUtils.d("DLThread "+info.id+" has stop ,url:"+info.baseUrl);
                            }
                        }
                    } else if (conn.getResponseCode() == HttpStatus.SC_OK) {
                        if(isDebug){
                            LogUtils.d("DLThread has 200 ,url:"+info.baseUrl);
                        }
                        is = conn.getInputStream();
                        raf.seek(info.start);
                        byte[] b = new byte[1024];
                        int len;
                        while (!isStop && (len = is.read(b)) != -1) {
                            raf.write(b, 0, len);
                            mListener.onThreadProgress(len);
                        }
                        if(isStop){
                            mListener.onThreadProgress(0);
                            info.start = info.start + progress;
                            //sDBManager.updateThreadInfo(info);
                            info.setState(PublicCons.DownLoadState.DOWNLOAD_STOP);
                            mDownLoadDao.update(info);
                            if(isDebug){
                                LogUtils.d("DLThread(200) "+info.id+" has stop ,url:"+info.baseUrl);
                            }
                        }
                    }
                } catch (Exception e) {
                    /*if (null != sDBManager.queryThreadInfoById(info.id)) {
                        info.start = info.start + progress;
                        sDBManager.updateThreadInfo(info);
                        if(isDebug){
                            LogUtils.e(TAG, "DLThread 's running error:" + e);
                            e.printStackTrace();
                        }
                    }*/
                    try {
                        if (null != mDownLoadDao.query(PublicCons.DBCons.TB_TASK_ID, info.id)) {
                            info.start = info.start + progress;
                            info.setState(PublicCons.DownLoadState.DOWNLOAD_FAIL);
                            mDownLoadDao.update(info);
                            if(isDebug){
                                LogUtils.e("DLThread 's running error:" + e);
                                e.printStackTrace();
                            }
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } finally {
                    try {
                        if (null != is) {
                            is.close();
                        }
                        if (null != raf) {
                            raf.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (null != conn) {
                        conn.disconnect();
                    }
                }
            }
        }
    }


    //调试日志开关

    private boolean isDebug = true;

    private static final String TAG = DLManager.class.getSimpleName();

}