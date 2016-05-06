package com.android.base.basic;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/4/29.
 */
public class BaseApplication extends Application {

    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = getApplicationContext();
    }

    public static Context getInstance() {
        return sInstance;
    }
}
