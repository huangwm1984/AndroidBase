package com.android.test.view.autolayout;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.android.base.BaseActivity;
import com.android.test.R;
import com.android.test.view.autolayout.fragment.SimpleFragment;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class AutoLayoutCategoryActivity extends BaseActivity {

    @Bind(R.id.id_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.id_viewpager)
    ViewPager mViewPager;

    private String[] mTabTitles = new String[]
            {"单个UI", "正方形"};

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_auto_layout_category;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setToolBar();
        initAdapter();
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("测试多层嵌套的AutoLayout适配-3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initAdapter() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int i)
            {
                return new SimpleFragment();
            }

            @Override
            public CharSequence getPageTitle(int position)
            {

                return mTabTitles[position];
            }

            @Override
            public int getCount()
            {
                return mTabTitles.length;
            }
        });


        mTabLayout.setupWithViewPager(mViewPager);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
