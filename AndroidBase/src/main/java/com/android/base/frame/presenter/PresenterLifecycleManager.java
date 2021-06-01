package com.android.base.frame.presenter;

import android.os.Bundle;

import com.android.base.frame.presenter.factory.PresenterFactory;
import com.android.base.frame.presenter.factory.PresenterStorage;
import com.android.base.frame.presenter.impl.PresenterLifecycleImpl;
import com.android.base.util.ParcelFn;

/**
 * Created by Administrator on 2016/9/19.
 */
public class PresenterLifecycleManager<P extends XPresenter> implements PresenterLifecycleImpl {

    private static final String PRESENTER_ID_KEY = "presenter_id";

    private PresenterFactory<P> presenterFactory;
    private P presenter;
    private Bundle bundle;

    public PresenterLifecycleManager(PresenterFactory<P> presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    @Override
    public void onCreated(Object view) {
        getPresenter();
        if(presenter!=null){
            presenter.attachView(view);
            presenter.onCreated();
        }
    }

    @Override
    public void onStart() {}

    @Override
    public void onResume() {
        getPresenter();
        if(presenter!=null){
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        getPresenter();
        if(presenter!=null){
            presenter.onPause();
        }
    }

    @Override
    public void onStop() {}

    @Override
    public void onDestroy() {
        getPresenter();
        if(presenter!=null){
            presenter.onDestroy();
        }
    }

    public P getPresenter() {
        if (presenterFactory != null) {
            if (presenter == null && bundle != null)
                presenter = PresenterStorage.INSTANCE.getPresenter(bundle.getString(PRESENTER_ID_KEY));

            if (presenter == null) {
                presenter = presenterFactory.createPresenter();
                PresenterStorage.INSTANCE.add(presenter);
            }
            bundle = null;
        }
        return presenter;
    }

    public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
        if (presenter != null)
            throw new IllegalArgumentException("setPresenterFactory() should be called before onResume()");
        this.presenterFactory = presenterFactory;
    }

    public PresenterFactory<P> getPresenterFactory() {
        return presenterFactory;
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getPresenter();
        if (presenter != null) {
            bundle.putString(PRESENTER_ID_KEY, PresenterStorage.INSTANCE.getId(presenter));
        }
        return bundle;
    }

    public void onRestoreInstanceState(Bundle presenterState) {
        if (presenter != null)
            throw new IllegalArgumentException("onRestoreInstanceState() should be called before onResume()");
        this.bundle = ParcelFn.unmarshall(ParcelFn.marshall(presenterState));
    }
}