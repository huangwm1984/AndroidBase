package com.android.base.basic;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/4/29.
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Base.initialize(this);
    }
}
