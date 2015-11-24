package com.android.base.block;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.android.base.block.impl.IBlock;


public abstract class SampleBlock implements IBlock {

    protected String TAG = getClass().getSimpleName();

    public View mRootView;

    public Activity mActivity;

    public void attachActivity(Activity activity) {
        mActivity = activity;
        onCreated();
    }

    /**
     * @return true if you want to shield back key
     */
    public boolean onBackPressed() {
        return false;
    }

    public void onResume(){

    }

    public void onPause(){

    }

    public void onStop(){

    }

    public void onDestroy() {
        mActivity = null;
        mRootView = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * view创建完成之后逻辑写在这里
     */
    protected abstract void onCreated();

    protected Activity getActivity() {
        return mActivity;
    }

    protected <T extends Activity> T getActivity(Class<T> cls) {
        return (T)mActivity;
    }

}