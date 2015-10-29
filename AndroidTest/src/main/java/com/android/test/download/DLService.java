package com.android.test.download;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.android.test.R;
import com.android.test.download.bizs.DLManager;
import com.android.test.download.interfaces.DLTaskListener;
import com.android.test.download.utils.FileUtil;
import com.apkfuns.logutils.LogUtils;

import java.io.File;

/**
 * 执行下载的Service
 * Service for download
 *
 * @author AigeStudio 2015-05-18
 */
public class DLService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");
        String path = intent.getStringExtra("path");
        final int id = intent.getIntExtra("id", -1);
        final NotificationManager nm = (NotificationManager) getSystemService(Context
                .NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(FileUtil.getFileNameFromUrl(url).replace("/", ""))
                .setSmallIcon(R.mipmap.ic_launcher);

        DLManager.getInstance(this).dlStart(url, path, new DLTaskListener() {
            @Override
            public void onProgress(int progress) {
                builder.setProgress(100, progress, false);
                nm.notify(id, builder.build());
            }

            @Override
            public void onFinish(File file) {
                LogUtils.i("onFinish");
                nm.cancel(id);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}