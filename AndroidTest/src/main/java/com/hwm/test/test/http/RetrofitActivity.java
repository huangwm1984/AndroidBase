package com.hwm.test.test.http;

import android.os.Bundle;

import com.android.base.basic.BaseActivity;
import com.apkfuns.logutils.LogUtils;
import com.hwm.test.R;
import com.hwm.test.test.http.model.RetrofitModel;
import com.hwm.test.test.http.presenter.RetrofitPresenter;
import com.hwm.test.test.http.view.RetrofitFragment;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitActivity extends BaseActivity {

    private static final String TAG = RetrofitActivity.class.getSimpleName();
    private RetrofitPresenter mRetrofitPresenter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_test_retrofit;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            RetrofitFragment mRetrofitFragment = RetrofitFragment.newInstance();
            start(mRetrofitFragment);
            mRetrofitPresenter = new RetrofitPresenter(RetrofitModel.getInstance(), mRetrofitFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e(TAG,"onStop");
    }

    @Override
    protected int setContainerId() {
        return R.id.contentFrame;
    }
}
