package com.hwm.test.http.view;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.frame.activity.BaseMvpActivity;
import com.android.base.frame.presenter.factory.RequiresPresenter;
import com.android.base.widget.LoadProgressLayout;
import com.hwm.test.R;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;
import com.hwm.test.http.presenter.RetrofitContract;
import com.hwm.test.http.presenter.RetrofitPresenter;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/27.
 */
@RequiresPresenter(RetrofitPresenter.class)
public class RetrofitActivity extends BaseMvpActivity<RetrofitPresenter> implements RetrofitContract.View{

    @Bind(R.id.progress_layout) LoadProgressLayout mProgressLayout;
    @Bind(R.id.tv1) TextView mTvLastestNews;
    @Bind(R.id.tv2) TextView mTvGeyeData;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_retrofit;
    }

    @Override
    protected void initData() {
        getSupportActionBar().setTitle("Retrofit测试");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RetrofitPresenter presenter = getPresenter();
        if(presenter!=null){
            presenter.start();
        }
    }

    @Override
    public void showLoadingView() {
        mProgressLayout.showLoadingView();
    }

    @Override
    public void showEmptyView() {
        mProgressLayout.showEmptyView();
    }

    @Override
    public void showErrorView() {
        mProgressLayout.showErrorView();
    }

    @Override
    public void showContentView() {
        mProgressLayout.showContentView();
    }

    @Override
    public void loadSuccessMessage(Object o) {
        if(o instanceof News){
            News newsEntity = (News) o;
            mTvLastestNews.setText(newsEntity.toString());
        }else if(o instanceof Geye){
            Geye geyeEntity = (Geye) o;
            mTvGeyeData.setText(JSON.toJSONString(geyeEntity));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnTryAgain)
    public void retryListener() {
        RetrofitPresenter presenter = getPresenter();
        if(presenter!=null){
            presenter.start();
        }
    }

}
