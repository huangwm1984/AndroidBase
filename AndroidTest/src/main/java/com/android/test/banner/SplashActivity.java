package com.android.test.banner;

import android.app.Activity;
import android.os.Bundle;

import com.android.base.BaseActivity;
import com.android.test.R;
import com.android.test.banner.block.SplashSampleBlock;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_splash_main;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        getCommonBlockManager().add(new SplashSampleBlock());
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
