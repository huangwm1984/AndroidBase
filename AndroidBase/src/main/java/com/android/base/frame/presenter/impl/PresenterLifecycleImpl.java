package com.android.base.frame.presenter.impl;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface PresenterLifecycleImpl {

    void onCreated(Object view);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
