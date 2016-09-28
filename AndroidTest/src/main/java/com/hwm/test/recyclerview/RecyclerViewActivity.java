package com.hwm.test.recyclerview;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.base.frame.activity.BaseActivity;
import com.android.base.util.adapter.HeaderAndFooterWrapper;
import com.android.base.widget.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.hwm.test.R;
import com.hwm.test.recyclerview.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/23.
 */
public class RecyclerViewActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @Bind(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private LoadMoreFooter mLoadMoreFooter;

    private HeaderWrapper mHeaderWrapper;

    private int mCount = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_recyclerview;
    }

    @Override
    protected void initData() {
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);

        mRecyclerViewAdapter = new RecyclerViewAdapter(this, R.layout.item_recyclerview_list);
        initHeaderAndFooter();
        mPullLoadMoreRecyclerView.setAdapter(mHeaderAndFooterWrapper);
        getData();

    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mRecyclerViewAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderWrapper = new HeaderWrapper(this);
        mHeaderWrapper.addView(t1);
        mHeaderWrapper.addView(t2);
        mHeaderAndFooterWrapper.addHeaderView(mHeaderWrapper);
        mHeaderWrapper.setVisibility(View.GONE);

        mLoadMoreFooter = new LoadMoreFooter(this);
        mHeaderAndFooterWrapper.addFootView(mLoadMoreFooter);
        mLoadMoreFooter.setVisibility(View.GONE);

    }

    @Override
    public void onRefresh() {
        setRefresh();
        getData();

    }

    @Override
    public void onLoadMore() {
        mCount = mCount + 1;
        if(mCount >= 5){
            mLoadMoreFooter.setNoLoadMore(View.GONE);
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }else{
            mLoadMoreFooter.setVisibility(View.VISIBLE);
            getData();
        }
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerViewAdapter.addAll(setList());
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        mHeaderWrapper.setVisibility(View.VISIBLE);
                    }
                });

            }
        }, 1000);
    }

    private List<String> setList() {
        List<String> dataList = new ArrayList<>();
        int start = 20 * (mCount - 1);
        for (int i = start; i < 20 * mCount; i++) {
            dataList.add("Frist" + i);
        }
        return dataList;

    }

    private void setRefresh() {
        mRecyclerViewAdapter.clear();
        mCount = 1;
    }
}
