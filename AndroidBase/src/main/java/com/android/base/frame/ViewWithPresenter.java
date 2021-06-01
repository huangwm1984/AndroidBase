package com.android.base.frame;

import com.android.base.frame.presenter.XPresenter;
import com.android.base.frame.presenter.factory.PresenterFactory;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface ViewWithPresenter<P extends XPresenter> {

    /**
     * Returns a current presenter factory.
     */
    PresenterFactory<P> getPresenterFactory();

    /**
     * Sets a presenter factory.
     * Call this method before onCreate/onFinishInflate to override default {#@link ReflectionPresenterFactory} presenter factory.
     * Use this method for presenter dependency injection.
     */
    void setPresenterFactory(PresenterFactory<P> presenterFactory);

    /**
     * Returns a current attached presenter.
     * This method is guaranteed to return a non-null value between
     * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
     * if the presenter factory returns a non-null value.
     *
     * @return a currently attached presenter or null.
     */
    P getPresenter();
}
