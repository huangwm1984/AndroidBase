package com.hwm.test.view.tabhost.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.BaseFragment;

import java.util.List;

/**
 * 为ViewPager添加布局（Fragment），绑定和处理fragments和viewpager之间的逻辑关系
 */
public class FragmentViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
    private List<BaseFragment> fragments; // 每个Fragment对应一个Page
    private FragmentManager fragmentManager;
    private ViewPager viewPager; // viewPager对象
    private int currentPageIndex = 0; // 当前page索引（切换之前）

    private OnExtraPageChangeListener onExtraPageChangeListener; // ViewPager切换页面时的额外功能添加接口

    public FragmentViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, List<BaseFragment> fragments) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.viewPager.setAdapter(this);
        this.viewPager.setOnPageChangeListener(this);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(fragments.get(position).getView()); // 移出viewpager两边之外的page布局
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);
        if(!fragment.isAdded()){ // 如果fragment还没有added
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            fragmentManager.executePendingTransactions();
        }

        if(fragment.getView().getParent() == null){
            container.addView(fragment.getView()); // 为viewpager增加布局
        }

        return fragment.getView();
    }

    /**
     * 当前page索引（切换之前）
     * @return
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    /**
     * 设置页面切换额外功能监听器
     * @param onExtraPageChangeListener
     */
    public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(null != onExtraPageChangeListener){ // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        fragments.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
//        fragments.get(currentPageIndex).onStop(); // 调用切换前Fargment的onStop()
        if(fragments.get(position).isAdded()){
//            fragments.get(i).onStart(); // 调用切换后Fargment的onStart()
            fragments.get(position).onResume(); // 调用切换后Fargment的onResume()
        }
        currentPageIndex = position;

        if(null != onExtraPageChangeListener){ // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageSelected(position);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(null != onExtraPageChangeListener){ // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrollStateChanged(state);
        }
    }


    /**
     * page切换额外功能接口
     */
    public static class OnExtraPageChangeListener{
        public void onExtraPageScrolled(int position, float positionOffset, int positionOffsetPixels){}
        public void onExtraPageSelected(int position){}
        public void onExtraPageScrollStateChanged(int state){}
    }


}
