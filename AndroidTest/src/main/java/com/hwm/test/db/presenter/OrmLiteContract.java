package com.hwm.test.db.presenter;

import com.android.base.basic.BasePresenter;
import com.android.base.basic.BaseView;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface OrmLiteContract {

    interface View extends BaseView<Presenter> {

        void loadErrorMessage(Object o);

        void loadSuccessMessage(Object o);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void Save();

        void Insert();

        void Update();

        void UpdateColumn();

        void QueryAll();

        void QueryByWhere();

        void QueryByID();

        void QueryAnyUwant();

        void Mapping();

        void Delete();

        void DeleteByIndex();

        void DeleteByWhereBuilder();

        void DeleteAll();

        void LargeScaleUseLite();

        void LargeScaleUseSystem();
    }
}
