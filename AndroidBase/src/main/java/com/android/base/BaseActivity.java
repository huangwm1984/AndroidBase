package com.android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.android.base.autolayout.AutoLayoutActivity;
import com.android.base.common.Log;
import com.android.base.lifecycle.ActivityLifecycleCallbacksCompat;
import com.android.base.netstate.NetWorkUtil;
import com.android.base.netstate.NetworkStateReceiver;
import com.android.base.block.CommonBlockManager;
import com.apkfuns.logutils.LogUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by huangwm on 2015/7/28 0028.
 */
public abstract class BaseActivity extends AutoLayoutActivity implements ActivityLifecycleCallbacksCompat {

    public Context mApplicationContext;

    public MyHandler mHandler;

    public CommonBlockManager mCommonBlockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getMainContentViewId() != 0) {
            setContentView(getMainContentViewId()); // set view
        }
        AppManager.getAppManager().addActivity(this);
        mApplicationContext = getApplicationContext();
        ButterKnife.bind(this);
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        mHandler = new MyHandler(this);
        mCommonBlockManager = getCommonBlockManager();
        onActivityCreated(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onActivityStarted(this);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onActivityResumed(this);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        onActivityPaused(this);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        onActivityStopped(this);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onStop();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        onActivitySaveInstanceState(outState, outPersistentState);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onSaveInstanceState(outState, outPersistentState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onActivityRestoreInstanceState(savedInstanceState);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCommonBlockManager != null) {
            if (!mCommonBlockManager.onBackPressed()) {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        onActivityDestroyed(this);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onDestroy();
        }
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCommonBlockManager != null) {
            mCommonBlockManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private static class MyHandler extends Handler {

        WeakReference<BaseActivity> mReference = null;

        MyHandler(BaseActivity activity) {
            this.mReference = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity outer = mReference.get();
            if (outer == null || outer.isFinishing()) {
                Log.e("outer is null");
                return;
            }

            outer.handleMessage(msg);
        }
    }


    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    public void gotoActivity(Class<? extends Activity> clazz, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    public void gotoActivity(Class<? extends Activity> clazz, int flags, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);

        intent.addFlags(flags);

        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public CommonBlockManager getCommonBlockManager() {
        if(mCommonBlockManager == null) {
            mCommonBlockManager = new CommonBlockManager(this);
        }
        return mCommonBlockManager;
    }

    public Handler getHandler(){
        return mHandler;
    }

    public void onConnect(NetWorkUtil.netType type) {
    }


    public void onDisConnect() {
    }


    public void handleMessage(Message msg) {
    }


    protected abstract int getMainContentViewId();
}
