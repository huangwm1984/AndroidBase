package com.hwm.test.test.http.presenter;

import com.android.base.basic.BasePresenter;
import com.android.base.basic.BaseView;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface RetrofitContract {

    interface View extends BaseView<Presenter> {

        void startLoading();

        void showErrorMessage(Object o);

        void showSuccessMessage(Object o);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadNetData();

    }

}
