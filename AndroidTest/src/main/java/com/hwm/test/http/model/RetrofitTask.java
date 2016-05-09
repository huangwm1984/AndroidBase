package com.hwm.test.http.model;


import android.support.annotation.NonNull;

import com.android.base.util.RxUtil;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;
import com.hwm.test.http.model.http.GeyeService;
import com.hwm.test.http.model.http.RetrofitServiceFactory;
import com.hwm.test.http.model.http.ZhihuService;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitTask implements IRetrofitTask {

    private static RetrofitTask mInstance;
    private ZhihuService mZhihuService;
    private GeyeService mGeyeService;
    private CompositeSubscription mCompositeSubscription;

    public static RetrofitTask getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitTask.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitTask();
                }
            }
        }
        return mInstance;
    }

    public RetrofitTask(){
        subscribe();
    }


    @Override
    public void loadLastestNewsData(@NonNull final LoadNetDataCallback callback) {
        mZhihuService = RetrofitServiceFactory.provideZhihuService();
        RxUtil.subscribeForRetrofit(mZhihuService.getLastestNews(), new Subscriber<News>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable(e.toString());
            }

            @Override
            public void onNext(News news) {
                callback.onDataLoaded(news);
            }
        }, RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription));
        /*mZhihuService.getLastestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable(e.toString());
                    }

                    @Override
                    public void onNext(News newsEntity) {
                        callback.onDataLoaded(newsEntity);
                    }
                });*/

    }

    @Override
    public void loadGeyeData(@NonNull final LoadNetDataCallback callback) {
        mGeyeService = RetrofitServiceFactory.provideGeyeService();
        RxUtil.subscribeForRetrofit(mGeyeService.getGeyeData("1590", "1"), new Subscriber<Geye>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable(e.toString());
            }

            @Override
            public void onNext(Geye geye) {
                callback.onDataLoaded(geye);
            }
        }, RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription));
        /*mGeyeService.getGeyeData("1590", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Geye>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable(e.toString());
                    }

                    @Override
                    public void onNext(Geye geyeEntity) {
                        callback.onDataLoaded(geyeEntity);
                    }
                });*/
    }

    public void unsubscribe(){
        RxUtil.unsubscribeIfNotNull(mCompositeSubscription);
    }

    public void subscribe() {
        mCompositeSubscription = RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }

    public CompositeSubscription getCompositeSubscription(){
        return RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }
}
