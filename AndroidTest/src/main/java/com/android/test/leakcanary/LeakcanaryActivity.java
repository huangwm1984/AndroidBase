package com.android.test.leakcanary;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;

import com.android.base.BaseActivity;
import com.android.base.permission.PermissionsDispatcher;
import com.android.test.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/17 0017.
 */
public class LeakcanaryActivity extends BaseActivity {

    private Object[] mObjs = new Object[10000];//快速消耗内存

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_leakcanary_main;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @OnClick(R.id.async_task) void startAsync() {
        for (int i = 0; i < mObjs.length; i++) {
            mObjs[i] = new Object();
        }

        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
