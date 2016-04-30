package com.hwm.test.http.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.basic.BaseFragment;
import com.android.base.widget.ProgressLayout;
import com.hwm.test.R;
import com.hwm.test.http.model.entity.GeyeEntity;
import com.hwm.test.http.model.entity.NewsEntity;
import com.hwm.test.http.presenter.RetrofitContract;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitFragment extends BaseFragment implements RetrofitContract.View {

    private RetrofitContract.Presenter mPresenter;
    private ProgressLayout mProgressLayout;
    private TextView mTvLastestNews;
    private TextView mTvGeyeData;

    public static RetrofitFragment newInstance() {
        return new RetrofitFragment();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_test_retrofit;
    }


    @Override
    protected void onFragmentViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        initView();
    }


    @Override
    protected void onFragmentActivityCreated(@Nullable Bundle savedInstanceState) {
        if(mPresenter!=null)
            mPresenter.start();
    }

    private void initView() {
        mTvLastestNews = bindView(R.id.tv1);
        mTvGeyeData = bindView(R.id.tv2);
        mProgressLayout = bindView(R.id.progress_layout);
    }

    @Override
    public void setPresenter(@Nullable RetrofitContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * 加载中
     */
    @Override
    public void startLoading() {
        mProgressLayout.showProgress();
    }

    /**
     * 加载失败
     */
    @Override
    public void showErrorMessage(Object o) {
        mProgressLayout.showErrorText((String) o);
    }

    /**
     * 请求成功
     */
    @Override
    public void showSuccessMessage(Object o) {
        if(o instanceof NewsEntity){
            NewsEntity newsEntity = (NewsEntity) o;
            mTvLastestNews.setText(newsEntity.toString());
        }else if(o instanceof GeyeEntity){
            GeyeEntity geyeEntity = (GeyeEntity) o;
            mTvGeyeData.setText(JSON.toJSONString(geyeEntity));
            mProgressLayout.showContent();
        }
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }


}
