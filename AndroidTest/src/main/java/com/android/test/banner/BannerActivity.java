package com.android.test.banner;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.base.BaseActivity;
import com.android.base.widget.banner.BGABanner;
import com.android.test.R;
import com.android.test.banner.block.BannerBlock;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class BannerActivity extends BaseActivity {


    @Override
    protected int getMainContentViewId() {
        return R.layout.act_banner_main;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        getCommonBlockManager().add(new BannerBlock());
        setToolBar();
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("Banner Test");
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
