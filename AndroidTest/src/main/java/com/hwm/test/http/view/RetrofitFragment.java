package com.hwm.test.http.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.basic.BaseFragment;
import com.android.base.widget.LoadProgressLayout;
import com.hwm.test.R;
import com.hwm.test.http.model.entity.GeyeEntity;
import com.hwm.test.http.model.entity.NewsEntity;
import com.hwm.test.http.presenter.RetrofitContract;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitFragment extends BaseFragment implements RetrofitContract.View {

    private RetrofitContract.Presenter mPresenter;
    private LoadProgressLayout mProgressLayout;
    private TextView mTvLastestNews;
    private TextView mTvGeyeData;
    private Button mBtnTryAgain;

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
        mBtnTryAgain = bindView(R.id.btnTryAgain);
        mBtnTryAgain.setOnClickListener(retryListener);

    }

    @Override
    public void setPresenter(@Nullable RetrofitContract.Presenter presenter) {
        this.mPresenter = presenter;
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
    public void loadErrorMessage(Object o) {
        showErrorView();
    }

    @Override
    public void loadSuccessMessage(Object o) {
        if(o instanceof NewsEntity){
            NewsEntity newsEntity = (NewsEntity) o;
            mTvLastestNews.setText(newsEntity.toString());
        }else if(o instanceof GeyeEntity){
            GeyeEntity geyeEntity = (GeyeEntity) o;
            mTvGeyeData.setText(JSON.toJSONString(geyeEntity));
            showContentView();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mPresenter!=null)
                mPresenter.start();
        }
    };

}
