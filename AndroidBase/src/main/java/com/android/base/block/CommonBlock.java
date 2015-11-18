package com.android.base.block;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public abstract class CommonBlock {

    protected String TAG = getClass().getSimpleName();

    public Activity mActivity;

    protected void attachActivity(Activity activity) {
        mActivity = activity;
        onCreated();
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

    /**
     * @return true if you want to shield back key
     */
    protected boolean onBackPressed() {
        return false;
    }

    protected void onResume(){}

    protected void onPause(){}

    protected void onDestroy() {
        mActivity = null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {}



}
