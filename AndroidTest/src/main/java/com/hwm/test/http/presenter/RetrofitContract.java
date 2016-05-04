package com.hwm.test.http.presenter;

import com.android.base.basic.BasePresenter;
import com.android.base.basic.BaseView;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface RetrofitContract {

    interface View extends BaseView<Presenter> {

        void showLoadingView();

        void showEmptyView();

        void showErrorView();

        void showContentView();

        void loadErrorMessage(Object o);

        void loadSuccessMessage(Object o);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadLastestNewsData();

        void loadGeyeData();

    }

}
