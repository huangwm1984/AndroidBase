package com.hwm.test.http.model;


import android.support.annotation.NonNull;

import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;
import com.hwm.test.http.model.http.GeyeService;
import com.hwm.test.http.model.http.RetrofitServiceFactory;
import com.hwm.test.http.model.http.ZhihuService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitTask implements IRetrofitTask {

    private static RetrofitTask mInstance;
    private ZhihuService mZhihuService;
    private GeyeService mGeyeService;

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


    @Override
    public void loadLastestNewsData(@NonNull final LoadNetDataCallback callback) {
        mZhihuService = RetrofitServiceFactory.provideZhihuService();
        mZhihuService.getLastestNews()
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
                });

    }

    @Override
    public void loadGeyeData(@NonNull final LoadNetDataCallback callback) {
        mGeyeService = RetrofitServiceFactory.provideGeyeService();
        mGeyeService.getGeyeData("1590", "1")
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
                });
    }
}
