package com.android.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.android.base.http.OkHttpClientManager;
import com.android.base.netstate.NetChangeObserver;
import com.android.base.netstate.NetWorkUtil;
import com.android.base.netstate.NetworkStateReceiver;
import com.apkfuns.logutils.LogUtils;


/**
 * Created by Administrator on 2015/7/28 0028.
 */
public class BaseApplication extends Application {

    public Activity mCurrentActivity;

    private NetChangeObserver mNetChangeObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        initLogUtils();
        registerNetWorkStateListener();// 注册网络状态监测器
    }

    private void initLogUtils() {
        // 配置日志是否输出(默认true)
        LogUtils.configAllowLog = true;

        // 配置日志前缀
        LogUtils.configTagPrefix = "Huangwm-";
    }

    private void registerNetWorkStateListener() {
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onConnect(NetWorkUtil.netType type) {
                super.onConnect(type);
                try {
                    BaseApplication.this.onConnect(type);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onDisConnect() {
                super.onDisConnect();
                try {
                    BaseApplication.this.onDisConnect();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        };
        NetworkStateReceiver.registerObserver(mNetChangeObserver);
    }

    /**
     * 当前没有网络连接通知
     */
    public void onDisConnect() {
        mCurrentActivity = AppManager.getAppManager().currentActivity();
        if (mCurrentActivity != null) {
            if (mCurrentActivity instanceof BaseActivity) {
                ((BaseActivity) mCurrentActivity).onDisConnect();
            }
        }
    }

    /**
     * 网络连接连接时通知
     */
    protected void onConnect(NetWorkUtil.netType type) {
        mCurrentActivity = AppManager.getAppManager().currentActivity();
        if (mCurrentActivity != null) {
            if (mCurrentActivity instanceof BaseActivity) {
                ((BaseActivity) mCurrentActivity).onConnect(type);
            }
        }
    }

}
