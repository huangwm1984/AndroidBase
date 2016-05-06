package com.hwm.test.http.presenter;

import android.support.annotation.NonNull;

import com.android.base.util.RxUtil;
import com.apkfuns.logutils.LogUtils;
import com.hwm.test.http.model.IRetrofitTask;
import com.hwm.test.http.model.RetrofitTask;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;
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
    private final RetrofitTask mRetrofitTaskModel;
    private RetrofitFragment mRetrofitFragment;
    private CompositeSubscription mCompositeSubscription;

    public RetrofitPresenter(@NonNull RetrofitTask retrofitTaskModel, @NonNull RetrofitContract.View view){
        this.mView = view;
        this.mRetrofitTaskModel = retrofitTaskModel;
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

            if(mRetrofitFragment == null){
                mRetrofitFragment = (RetrofitFragment) mView;
            }

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
        mRetrofitTaskModel.loadGeyeData(new IRetrofitTask.LoadNetDataCallback() {
            @Override
            public void onDataLoaded(Object o) {

                Geye geyeEntity = (Geye) o;
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
        mRetrofitTaskModel.loadLastestNewsData(new IRetrofitTask.LoadNetDataCallback() {
            @Override
            public void onDataLoaded(Object o) {

                News newsEntity = (News) o;

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
