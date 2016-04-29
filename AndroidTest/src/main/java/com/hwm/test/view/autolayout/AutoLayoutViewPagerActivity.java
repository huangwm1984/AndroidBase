package com.hwm.test.view.autolayout;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.WindowManager;

import com.android.base.BaseActivity;
import com.hwm.test.R;
import com.hwm.test.view.autolayout.fragment.ListFragment;
import com.hwm.test.view.autolayout.fragment.PayFragment;
import com.hwm.test.view.autolayout.fragment.RegisterFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class AutoLayoutViewPagerActivity extends BaseActivity {
    @Bind(R.id.id_viewpager)
    ViewPager mViewPager;

    @Override
    protected int getMainContentViewId() {
        setImmersionStatus();
        return R.layout.act_auto_layout_viewpager;
    }

    private void setImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setToolBar();
        initView();
        initDatas();
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("测试多层嵌套的AutoLayout适配-2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDatas() {
        ArrayList<Fragment> mList = new ArrayList<Fragment>();
        mList.add(new ListFragment());
        mList.add(new RegisterFragment());
        mList.add(new PayFragment());
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), mList));
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
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
    public void onActivitySaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public class MyAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> tabs = null;

        public MyAdapter(FragmentManager fm, ArrayList<Fragment> tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int pos) {
            return tabs.get(pos);
        }

        @Override
        public int getCount() {
            return tabs.size();
        }
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
