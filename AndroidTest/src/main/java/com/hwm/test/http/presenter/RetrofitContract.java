package com.hwm.test.http.presenter;

import com.android.base.frame.presenter.XPresenter;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface RetrofitContract {

    interface Model{
        Observable<News> loadLastestNewsData();
        Observable<Geye> loadGeyeData(String geyeId, String isNeedff);
    }

    interface View {

        void showLoadingView();

        void showEmptyView();

        void showErrorView();

        void showContentView();

        void loadSuccessMessage(Object o);

    }

    abstract class Presenter extends XPresenter<View> {

        public abstract void loadLastestNewsData();

        public abstract void loadGeyeData(String geyeId, String isNeedff);
    }

}
