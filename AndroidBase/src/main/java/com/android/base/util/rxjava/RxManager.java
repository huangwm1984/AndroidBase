package com.android.base.util.rxjava;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 */
public class RxManager {

    public RxBus mRxBus = RxBus.$();
    private Map<String, Observable<?>> mObservables;// 管理观察源
    private CompositeSubscription mCompositeSubscription;// 管理订阅者者

    public RxManager(){
        mObservables = new HashMap<>();
        mCompositeSubscription = RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }


    public void on(String eventName, Action1<Object> action1) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription).add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void add(Subscription subscription) {
        RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription).add(subscription);
    }

    public void clear() {
        RxUtil.unsubscribeIfNotNull(mCompositeSubscription);// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet())
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
