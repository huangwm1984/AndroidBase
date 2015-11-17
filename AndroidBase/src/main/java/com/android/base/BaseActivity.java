package com.android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.android.base.common.Log;
import com.android.base.lifecyclelistener.ActivityLifecycleCallbacksCompat;
import com.android.base.netstate.NetWorkUtil;
import com.android.base.netstate.NetworkStateReceiver;
import com.android.base.block.UIBlockManager;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by huangwm on 2015/7/28 0028.
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityLifecycleCallbacksCompat {

    public Context mApplicationContext;

    public MyHandler mHandler;

    public UIBlockManager mUIBlockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        if (getMainContentViewId() != 0) {
            setContentView(getMainContentViewId()); // set view
        }
        ButterKnife.bind(this);
        mApplicationContext = getApplicationContext();
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        mHandler = new MyHandler(this);
        mUIBlockManager = new UIBlockManager(this);
        onActivityCreated(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onActivityStarted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onActivityResumed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onActivityPaused(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onActivityStopped(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onActivitySaveInstanceState(this, outState);
    }

    @Override
    public void onBackPressed() {
        if (!mUIBlockManager.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        AppManager.getAppManager().finishActivity();
        mUIBlockManager.onDestroy();
        onActivityDestroyed(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUIBlockManager.onActivityResult(requestCode, resultCode, data);
    }

    private static class MyHandler extends Handler {

        WeakReference<BaseActivity> mReference = null;

        MyHandler(BaseActivity activity) {
            this.mReference = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity outer = mReference.get();
            if (outer == null && outer.isFinishing()) {
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

    public UIBlockManager getUIBlockManager() {
        if(mUIBlockManager == null) mUIBlockManager = new UIBlockManager(this);
        return mUIBlockManager;
    }

    public void onConnect(NetWorkUtil.netType type) {
    }


    public void onDisConnect() {
    }


    public void handleMessage(Message msg) {
    }


    protected abstract int getMainContentViewId();
}
