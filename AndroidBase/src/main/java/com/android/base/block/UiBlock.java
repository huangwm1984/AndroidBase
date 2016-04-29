package com.android.base.block;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.base.block.impl.IBlock;

import butterknife.ButterKnife;

public abstract class UiBlock implements IBlock{

    protected String TAG = getClass().getSimpleName();

    public View mRootView;

    public Activity mActivity;

    public Context mApplicationContext;

    public void attachActivity(Activity activity) {
        onAttach(activity);
        mRootView = activity.findViewById(getRootViewId());
        mRootView = resetRootView(mRootView, LayoutInflater.from(activity));

        //bindViews();
        //beforeSetViews();
        //setViews();
        if(mRootView!=null)ButterKnife.bind(this, mRootView);
        onViewCreated();
    }

    /**
     * @return 得到UIBlock作用于的容器view的id
     */
    public abstract @IdRes int getRootViewId();

    public abstract void onViewCreated();

    protected void onAttach(Activity activity) {
        mActivity = activity;
        mApplicationContext = mActivity.getApplicationContext();
    }

    /**
     * 找到所有的views
     */
    //protected abstract void onBindViews();

    /**
     * 在这里初始化设置view的各种资源，比如适配器或各种变量
     */
    //protected void beforeSetViews() {}

    /**
     * 设置所有的view
     */
    //protected abstract void onSetViews();

    /**
     * 重置根布局
     */
    public View resetRootView(View oldRootView, LayoutInflater inflater) {
        return oldRootView;
    }

    public View getRootView() {
        return mRootView;
    }

    protected Activity getActivity() {
        return mActivity;
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {}

    public void onRestoreInstanceState(Bundle savedInstanceState) {}

    public void onStart() {}

    public void onResume() {}

    public void onPause() {}

    public void onStop() {}

    public void onRestart() {}

    public void onDestroy() {
        mActivity = null;
        mRootView = null;
    }

    /**
     * @return true if you want to shield back key
     */
    public boolean onBackPressed() {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    protected final <E extends View> E getView(int id) {
        try {
            return (E) mRootView.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}
