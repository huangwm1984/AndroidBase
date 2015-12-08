package com.android.test;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.base.BaseApplication;
import com.android.base.LoadingAndRetryManager;
import com.android.test.download.bizs.DLInfo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class MyApplication extends BaseApplication {

    private static MyApplication mInstance;
    private RefWatcher refWatcher;
    //下载任务列表
    private ConcurrentHashMap<String, DLInfo> mDownloadTaskMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initLeakCanary();
        initLoadingAndRetry();
    }

    private void initLeakCanary() {
        refWatcher = installLeakCanary(false);
    }

    protected RefWatcher installLeakCanary(boolean isRelease) {
        if (isRelease) {
            return RefWatcher.DISABLED;
        } else {
            return LeakCanary.install(this);
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private void initLoadingAndRetry() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.act_base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.act_base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.act_base_empty;
    }

    public void setAllDLTasks(List<DLInfo> infos){
        if(mDownloadTaskMap == null){
            mDownloadTaskMap = new ConcurrentHashMap<>();
        }
        if(infos!=null && !infos.isEmpty()){
            for(DLInfo info : infos){
                mDownloadTaskMap.put(info.baseUrl, info);
            }
        }
    }

    public void updateDLTask(DLInfo info){
        if(mDownloadTaskMap != null){
            mDownloadTaskMap.put(info.getBaseUrl(), info);

        }
    }

    public DLInfo getDLTask(DLInfo info){
        if(mDownloadTaskMap != null){
            return mDownloadTaskMap.get(info.getBaseUrl());
        }
        return null;
    }

    public void removeDLTask(DLInfo info){
        if(mDownloadTaskMap != null){
            mDownloadTaskMap.remove(info.getBaseUrl(), info);
        }
    }

    public ConcurrentHashMap<String, DLInfo> getAllTaks(){
        return mDownloadTaskMap;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

}
