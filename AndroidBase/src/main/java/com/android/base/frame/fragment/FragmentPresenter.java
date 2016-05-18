package com.android.base.frame.fragment;

import com.android.base.frame.fragment.impl.FragmentPresenterImpl;
import com.android.base.util.rx.RxManager;

/**
 * Created by Administrator on 2016/5/17.
 */
public abstract class FragmentPresenter<V, M> implements FragmentPresenterImpl {

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
