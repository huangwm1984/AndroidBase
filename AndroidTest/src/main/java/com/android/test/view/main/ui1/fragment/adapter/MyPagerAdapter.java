package com.android.test.view.main.ui1.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.test.view.main.ui1.fragment.childfragment.FragmentA_1;
import com.android.test.view.main.ui1.fragment.childfragment.FragmentA_2;
import com.android.test.view.main.ui1.fragment.childfragment.FragmentA_3;
import com.android.test.view.main.ui1.fragment.childfragment.FragmentA_4;

/**
 * Created by Administrator on 2015/10/15 0015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private FragmentA_1 mFragmentA_1;
    private FragmentA_2 mFragmentA_2;
    private FragmentA_3 mFragmentA_3;
    private FragmentA_4 mFragmentA_4;

    private final String[] titles = { "资讯", "热点", "博客", "推荐" };

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mFragmentA_1 == null) {
                    mFragmentA_1 = new FragmentA_1();
                }
                return mFragmentA_1;
            case 1:
                if (mFragmentA_2 == null) {
                    mFragmentA_2 = new FragmentA_2();
                }
                return mFragmentA_2;
            case 2:
                if (mFragmentA_3 == null) {
                    mFragmentA_3 = new FragmentA_3();
                }
                return mFragmentA_3;
            case 3:
                if (mFragmentA_4 == null) {
                    mFragmentA_4 = new FragmentA_4();
                }
                return mFragmentA_4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
