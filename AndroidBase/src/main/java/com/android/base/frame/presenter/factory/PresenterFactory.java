package com.android.base.frame.presenter.factory;


import com.android.base.frame.presenter.XPresenter;

public interface PresenterFactory<P extends XPresenter> {
    P createPresenter();
}
