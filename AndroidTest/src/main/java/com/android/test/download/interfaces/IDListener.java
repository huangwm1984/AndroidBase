package com.android.test.download.interfaces;

import com.android.test.download.bizs.DLInfo;

import java.io.File;

/**
 * @author AigeStudio 2015-10-18
 */
public interface IDListener {
    /*void onPrepare();

    void onStart(String fileName, String appName, String realUrl, int fileLength);

    void onProgress(int progress);

    void onStop(int progress);

    void onFinish(File file, int description);

    void onError(int status, String error);*/

    void onPrepare(DLInfo info);

    void onStart(DLInfo info);

    void onProgress(DLInfo info);

    void onStop(DLInfo info);

    void onFinish(DLInfo info);

    void onError(int status, String error, DLInfo info);
}