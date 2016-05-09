package com.hwm.test.db;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.base.basic.Base;
import com.android.base.basic.BaseActivity;
import com.hwm.test.R;
import com.hwm.test.db.model.OrmLiteTask;
import com.hwm.test.db.presenter.OrmLitePresenter;
import com.hwm.test.db.view.OrmLiteFragment;

/**
 * Created by Administrator on 2016/5/5.
 */
public class OrmLiteActivity extends BaseActivity {

    @Override
    public int setContentViewId() {
        return R.layout.activity_test_ormlite;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("OrmLite测试");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            OrmLiteFragment ormLiteFragment = OrmLiteFragment.newInstance();
            start(ormLiteFragment);
            new OrmLitePresenter(OrmLiteTask.getInstance(Base.getContext()), ormLiteFragment);
        }

    }

    @Override
    protected int setContainerId() {
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
