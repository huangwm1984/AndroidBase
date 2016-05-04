package com.hwm.test.http.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.base.util.RxUtil;
import com.apkfuns.logutils.LogUtils;
import com.hwm.test.http.model.RetrofitDataSource;
import com.hwm.test.http.model.RetrofitModel;
import com.hwm.test.http.model.entity.GeyeEntity;
import com.hwm.test.http.model.entity.NewsEntity;
import com.hwm.test.http.view.RetrofitFragment;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitPresenter implements RetrofitContract.Presenter {

    private final String TAG = RetrofitPresenter.class.getSimpleName();
    private final RetrofitContract.View mView;
    private final RetrofitModel mRetrofitModel;
    private RetrofitFragment mRetrofitFragment;
    private CompositeSubscription mCompositeSubscription;

    public RetrofitPresenter(@NonNull RetrofitModel retrofitModel, @NonNull RetrofitContract.View view){
        this.mView = view;
        this.mRetrofitModel = retrofitModel;
        if(mView!=null){
            mView.setPresenter(this);
            mRetrofitFragment = (RetrofitFragment) mView;
        }
    }

    @Override
    public void start() {
        loadLastestNewsData();
    }

    @Override
    public void loadLastestNewsData() {
        mView.showLoadingView();
        mCompositeSubscription = RxUtil.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);

        if(mView.isActive()){
            Subscription subscription = Observable.interval(1, TimeUnit.SECONDS)
                    .doOnUnsubscribe(new Action0() {
                        @Override
                        public void call() {
                            LogUtils.e("Unsubscribing subscription from onActivityCreated()");
                        }
                    })
                    .compose(mRetrofitFragment.<Long>bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            loadNewsData();
                        }
                    });
            mCompositeSubscription.add(subscription);
        }
    }

    @Override
    public void loadGeyeData() {
        mRetrofitModel.loadGeyeData(new RetrofitDataSource.LoadNetDataCallback() {
            @Override
            public void onDataLoaded(Object o) {

                GeyeEntity geyeEntity = (GeyeEntity) o;
                if(mView.isActive())
                    mView.loadSuccessMessage(geyeEntity);

                RxUtil.unsubscribeIfNotNull(mCompositeSubscription);//取消订阅
            }

            @Override
            public void onDataNotAvailable(Object o) {
                String errorMsg = (String) o;
                if(mView.isActive())
                    mView.loadErrorMessage(errorMsg);

            }
        });
    }

    public void loadNewsData(){
        mRetrofitModel.loadLastestNewsData(new RetrofitDataSource.LoadNetDataCallback() {
            @Override
            public void onDataLoaded(Object o) {

                NewsEntity newsEntity = (NewsEntity) o;

                if(mView.isActive())
                    mView.loadSuccessMessage(newsEntity);

                loadGeyeData();

            }

            @Override
            public void onDataNotAvailable(Object o) {
                String errorMsg = (String) o;
                if(mView.isActive())
                    mView.loadErrorMessage(errorMsg);
            }
        });
    }
}
