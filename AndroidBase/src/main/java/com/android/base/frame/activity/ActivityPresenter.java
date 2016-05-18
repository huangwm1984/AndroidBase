package com.android.base.frame.activity;

import com.android.base.frame.activity.impl.ActivityPresenterImpl;
import com.android.base.util.rx.RxManager;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class ActivityPresenter<V, M> implements ActivityPresenterImpl {

    public V view;
    public M model;
    public RxManager rxManager = new RxManager();

    public void setVM(V view, M model) {
        this.view = view;
        this.model = model;
        start();
    }

    @Override
    public void onDestroy() {
        rxManager.clear();
    }

    public abstract void start();
}
