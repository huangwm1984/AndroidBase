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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by huangwm on 2015/7/28 0028.
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityLifecycleCallbacksCompat {

    public Context mApplicationContext;

    public Activity mContext;

    public MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        if (getMainContentViewId() != 0) {
            setContentView(getMainContentViewId()); // set view
        }
        ButterKnife.bind(this);
        mApplicationContext = getApplicationContext();
        mContext = this;
        mHandler = new MyHandler(this);
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
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        onActivityResumed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
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
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
        AppManager.getAppManager().finishActivity();
        onActivityDestroyed(this);
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

    /**
     * ImageLoader配置1
     */
    public DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();

    }

    /**
     * ImageLoader配置2
     */
    public DisplayImageOptions getDisplayImageOptions(int loadingDrawableId, int FailDrawableId) {
        return new DisplayImageOptions.Builder().showImageOnLoading(loadingDrawableId).showImageForEmptyUri(FailDrawableId).showImageOnFail(FailDrawableId).cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();
    }

    /**
     * ImageLoader配置3
     */
    public DisplayImageOptions getDisplayImageOptions(int FailDrawableId) {
        return new DisplayImageOptions.Builder().showImageForEmptyUri(FailDrawableId).showImageOnFail(FailDrawableId).cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();
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
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    public void gotoActivity(Class<? extends Activity> clazz, int flags, boolean finish, Bundle bundle) {

        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        intent.addFlags(flags);

        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    public void onConnect(NetWorkUtil.netType type) {
    }


    public void onDisConnect() {
    }


    public void handleMessage(Message msg) {
    }


    protected abstract int getMainContentViewId();
}
