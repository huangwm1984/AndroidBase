package com.hwm.test.http;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.base.basic.BaseActivity;
import com.hwm.test.R;
import com.hwm.test.http.model.RetrofitTask;
import com.hwm.test.http.presenter.RetrofitPresenter;
import com.hwm.test.http.view.RetrofitFragment;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitActivity extends BaseActivity {

    //private Toolbar mToolbar;

    @Override
    public int setContentViewId() {
        return R.layout.activity_test_retrofit;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        /*mToolbar = bindView(R.id.toolBar);
        setSupportActionBar(mToolbar);*/
        getSupportActionBar().setTitle("Retrofit测试");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            RetrofitFragment retrofitFragment = RetrofitFragment.newInstance();
            start(retrofitFragment);
            new RetrofitPresenter(RetrofitTask.getInstance(), retrofitFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LogUtils.e("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //LogUtils.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //LogUtils.e("onStop");
    }

    @Override
    protected int setContainerId() {
        return R.id.contentFrame;
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
