package com.android.test.autolayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.base.BaseActivity;
import com.android.test.R;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class AutoLayoutActivity extends BaseActivity{
    @Override
    protected int getMainContentViewId() {
        return R.layout.act_auto_layout;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setToolBar();
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("测试多层嵌套的AutoLayout适配");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
