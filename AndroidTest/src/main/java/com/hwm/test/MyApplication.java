package com.hwm.test;

import com.android.base.basic.BaseApplication;
import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MyApplication extends BaseApplication {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initLogUtils();
    }

    private void initLogUtils() {
        LogUtils.getLogConfig()
                .configAllowLog(true)                    //是否允许日志输出
                .configTagPrefix("AndroidTest")          //日志log的前缀
                .configShowBorders(true)                 //是否显示边界
                .configLevel(LogLevel.TYPE_VERBOSE);     //日志显示等级
    }

    public static MyApplication getInstance() {
        return sInstance;
    }
}
