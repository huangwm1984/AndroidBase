package com.hwm.test.http.presenter;

import android.util.Log;

import com.hwm.test.http.model.RetrofitModel;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitPresenter extends RetrofitContract.Presenter {

    private final String TAG = RetrofitPresenter.class.getSimpleName();
    private RetrofitModel model;

    @Override
    public void start() {
        super.start();
        if(model == null){
            model = new RetrofitModel();
        }
        loadLastestNewsData();
    }

    @Override
    public void loadLastestNewsData() {
        view.showLoadingView();
        compositeSubscription.add(Observable.mergeDelayError(model.loadLastestNewsData(), model.loadGeyeData("1590", "1")).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                view.showContentView();
            }

            @Override
            public void onError(Throwable e) {
                view.showErrorView();
            }

            @Override
            public void onNext(Object o) {
                view.loadSuccessMessage(o);
            }
        }));

        /*rxManager.add(model.loadLastestNewsData().subscribe(new Subscriber<News>() {
            @Override
            public void onCompleted() {
                loadGeyeData("1590", "1");
            }

            @Override
            public void onError(Throwable e) {
                view.showErrorView();
            }

            @Override
            public void onNext(News news) {
                view.loadSuccessMessage(news);
            }
        }));*/

    }

    @Override
    public void loadGeyeData(String geyeId, String isNeedff) {
        /*rxManager.add(model.loadGeyeData(geyeId, isNeedff).subscribe(new Subscriber<Geye>() {
            @Override
            public void onCompleted() {
                view.showContentView();
            }

            @Override
            public void onError(Throwable e) {
                view.showErrorView();
            }

            @Override
            public void onNext(Geye geye) {
                view.loadSuccessMessage(geye);
            }
        }));*/
    }
}
