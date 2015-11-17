package com.android.test;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.base.BaseApplication;
import com.android.base.LoadingAndRetryManager;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class MyApplication extends BaseApplication {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.act_base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.act_base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.act_base_empty;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }


}
