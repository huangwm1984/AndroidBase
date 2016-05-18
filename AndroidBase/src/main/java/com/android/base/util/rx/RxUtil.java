package com.android.base.util.rx;

import com.apkfuns.logutils.LogUtils;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/5/3.
 */
public class RxUtil {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }
        return subscription;
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> applySchedulersForRetrofit() {
        return new Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }

    public static <T> Observable<T> getObservable(final Callable<T> callable) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(callable.call());
                        } catch (Exception ex) {
                            LogUtils.e(ex);
                        }
                    }
                });
    }

    /*public static <T> Observable<T> subscribe(Callable<T> callable, Subscriber<T> subscriber, CompositeSubscription cs) {
        Observable<T> observable = getObservable(callable);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        getNewCompositeSubIfUnsubscribed(cs);
        cs.add(subscription);
        return observable;
    }

    public static <T> Observable<T> subscribe(Observable<T> observable, Subscriber<T> subscriber, CompositeSubscription cs) {
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        getNewCompositeSubIfUnsubscribed(cs);
        cs.add(subscription);
        return observable;
    }

    public static <T> Observable<T> subscribeForRetrofit(Observable<T> observable, Subscriber<T> subscriber, CompositeSubscription cs) {
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
        getNewCompositeSubIfUnsubscribed(cs);
        cs.add(subscription);
        return observable;
    }*/


}
