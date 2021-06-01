package com.android.base.frame.presenter;

import com.android.base.util.rxjava.RxManager;
import com.android.base.util.rxjava.RxUtil;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/9/19.
 */
public class XPresenter<V> extends BasePresenter<V> {

    public V view;
    public CompositeSubscription compositeSubscription;
    public RxManager rxManager = new RxManager();

    /**
     * Presenter初始化
     */
    public void onCreated() {
        this.compositeSubscription = RxUtil.getNewCompositeSubIfUnsubscribed(compositeSubscription);
        view = getView();
    }

    /**
     * Presenter开始工作
     */
    public void start(){}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(rxManager!=null){
            rxManager.clear();
        }
        RxUtil.unsubscribeIfNotNull(compositeSubscription);
    }

}
