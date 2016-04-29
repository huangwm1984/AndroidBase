package com.hwm.test.test.http.presenter;

import android.support.annotation.NonNull;

import com.hwm.test.test.http.model.RetrofitDataSource;
import com.hwm.test.test.http.model.RetrofitModel;
import com.hwm.test.test.http.model.entity.NewsEntity;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitPresenter implements RetrofitContract.Presenter {

    private final RetrofitContract.View mView;
    private final RetrofitModel mRetrofitModel;

    public RetrofitPresenter(@NonNull RetrofitModel retrofitModel, @NonNull RetrofitContract.View view){
        this.mView = view;
        this.mRetrofitModel = retrofitModel;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadNetData();
    }

    @Override
    public void loadNetData() {
        mView.startLoading();
        mRetrofitModel.loadLastestNewsData(new RetrofitDataSource.LoadNetDataCallback() {
            @Override
            public void onDataLoaded(Object o) {
                NewsEntity newsEntity = (NewsEntity) o;
                if(mView.isActive())
                    mView.showSuccessMessage(newsEntity);
            }

            @Override
            public void onDataNotAvailable(Object o) {
                String errorMsg = (String) o;
                if(mView.isActive())
                    mView.showErrorMessage(errorMsg);
            }
        });
    }


}
