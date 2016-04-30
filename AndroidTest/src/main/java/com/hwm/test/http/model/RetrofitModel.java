package com.hwm.test.http.model;


import android.support.annotation.NonNull;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.hwm.test.http.model.entity.GeyeEntity;
import com.hwm.test.http.model.entity.NewsEntity;
import com.hwm.test.http.model.http.ApiConstant;
import com.hwm.test.http.model.http.GeyeService;
import com.hwm.test.http.model.http.RetrofitServiceFactory;
import com.hwm.test.http.model.http.ZhihuService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitModel implements RetrofitDataSource {

    private static RetrofitModel mInstance;
    private ZhihuService mZhihuService;
    private GeyeService mGeyeService;

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
        mZhihuService = RetrofitServiceFactory.provideZhihuService();
        mZhihuService.getLastestNews()
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
    public void loadGeyeData(@NonNull final LoadNetDataCallback callback) {
        mGeyeService = RetrofitServiceFactory.provideGeyeService();
        mGeyeService.getGeyeData("1590", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GeyeEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable(e.toString());
                    }

                    @Override
                    public void onNext(GeyeEntity geyeEntity) {
                        callback.onDataLoaded(geyeEntity);
                    }
                });
    }
}
