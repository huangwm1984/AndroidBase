package com.hwm.test.http.view;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.base.frame.activity.BaseMvpActivity;
import com.android.base.widget.LoadProgressLayout;
import com.hwm.test.R;
import com.hwm.test.http.model.RetrofitModel;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;
import com.hwm.test.http.presenter.RetrofitContract;
import com.hwm.test.http.presenter.RetrofitPresenter;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitActivity extends BaseMvpActivity<RetrofitPresenter, RetrofitModel> implements RetrofitContract.View{

    private LoadProgressLayout mProgressLayout;
    private TextView mTvLastestNews;
    private TextView mTvGeyeData;
    private Button mBtnTryAgain;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_retrofit;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("Retrofit测试");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvLastestNews = bindView(R.id.tv1);
        mTvGeyeData = bindView(R.id.tv2);
        mProgressLayout = bindView(R.id.progress_layout);
        mBtnTryAgain = bindView(R.id.btnTryAgain);
        mBtnTryAgain.setOnClickListener(retryListener);
    }

    @Override
    protected void initPresenter() {
        presenter.setVM(this, model);
    }

    @Override
    protected Class<RetrofitPresenter> getPresenterClass() {
        return RetrofitPresenter.class;
    }

    @Override
    protected Class<RetrofitModel> getModelClass() {
        return RetrofitModel.class;
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

    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(presenter!=null)
                presenter.start();
        }
    };

}
