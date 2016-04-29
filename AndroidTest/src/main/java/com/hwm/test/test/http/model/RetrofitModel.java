package com.hwm.test.test.http.model;


import android.support.annotation.NonNull;

import com.hwm.test.test.http.model.entity.NewsEntity;
import com.hwm.test.test.http.model.http.RetrofitServiceFactory;
import com.hwm.test.test.http.model.http.ZhihuService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitModel implements RetrofitDataSource {

    private static RetrofitModel mInstance;
    private ZhihuService mService;

    public static RetrofitModel getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitModel.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitModel();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void loadLastestNewsData(@NonNull final LoadNetDataCallback callback) {
        mService = RetrofitServiceFactory.provideZhihuService();
        mService.getLastestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NewsEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable(e.toString());
                    }

                    @Override
                    public void onNext(NewsEntity newsEntity) {
                        callback.onDataLoaded(newsEntity);
                    }
                });

    }

    @Override
    public void loadBeforeNewsData(@NonNull LoadNetDataCallback callback) {


    }

    @Override
    public void loadNewsDetailsData(@NonNull LoadNetDataCallback callback) {

    }
}
