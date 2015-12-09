package com.android.base.block;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.android.base.block.impl.IBlock;


public abstract class SampleBlock implements IBlock {

    protected String TAG = getClass().getSimpleName();

    public Activity mActivity;

    public Context mApplicationContext;

    public void attachActivity(Activity activity) {
        onAttach(activity);
        onCreated();
    }

    /**
     * view创建完成之后逻辑写在这里
     */
    protected abstract void onCreated();

    protected void onAttach(Activity activity) {
        mActivity = activity;
        mApplicationContext = mActivity.getApplicationContext();
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){}

    /**
     * @return true if you want to shield back key
     */
    public boolean onBackPressed() {
        return false;
    }

}