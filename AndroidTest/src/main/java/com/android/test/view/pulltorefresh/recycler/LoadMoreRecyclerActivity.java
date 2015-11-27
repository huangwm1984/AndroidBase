package com.android.test.view.pulltorefresh.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.android.base.BaseActivity;
import com.android.base.common.assist.Toastor;
import com.android.base.widget.pulltorefresh.PullToRefreshBase;
import com.android.base.widget.pulltorefresh.PullToRefreshRecyclerView;
import com.android.base.widget.recyclerview.OnRcvScrollListener;
import com.android.base.widget.recyclerview.extra.HeaderAndFooterRecyclerViewAdapter;
import com.android.base.widget.recyclerview.extra.RecyclerViewUtils;
import com.android.test.R;
import com.android.test.view.pulltorefresh.recycler.adapter.DataAdapter;
import com.android.test.view.pulltorefresh.recycler.widget.HeaderBanner;
import com.android.test.view.pulltorefresh.recycler.widget.SimpleFooterTest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class LoadMoreRecyclerActivity extends BaseActivity {

    @Bind(R.id.pull_refresh_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;

    List<String> mData = new ArrayList<>();
    DataAdapter mDataAdapter;
    SimpleFooterTest mSimpleFooterTest;
    HeaderBanner mHeaderBanner;
    HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    /** 是否加载数据标志 **/
    private boolean isLoadingData = false;


    @Override
    protected int getMainContentViewId() {
        return R.layout.act_pull_to_refresh_rcv;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setData();
        setBlock();
        setRecyclerView();
    }

    private void setData() {
        for (int i = 0; i < 15; i++) {
            mData.add("这是一条RecyclerView的数据" + (i + 1));
        }
    }

    private void setBlock() {
        getCommonBlockManager().add(new DataAdapter()).add(new SimpleFooterTest(this)).add(new HeaderBanner());
        mDataAdapter = getCommonBlockManager().get(DataAdapter.class);
        mSimpleFooterTest = getCommonBlockManager().get(SimpleFooterTest.class);
        mHeaderBanner = getCommonBlockManager().get(HeaderBanner.class);
    }

    private void setRecyclerView() {

        mDataAdapter.setDataAndAdapter(mData);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter.getAdapter());
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //add a HeaderView
        RecyclerViewUtils.setHeaderView(mRecyclerView.getRefreshableView(), mHeaderBanner.getRootView());

        //add a FooterView
        RecyclerViewUtils.setFooterView(mRecyclerView.getRefreshableView(), /*new SimpleFooter(this)*/mSimpleFooterTest);

        mRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<RecyclerView> refreshView) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                        /*data.add(new Bean());
                        data.add(new Bean());
                        data.add(new Bean());
                        adapter.notifyItemInserted(data.size() - 1);
                        //adapter.notifyDataSetChanged();*/
                    }
                }, 500);
            }
        });

        //解决上下拉刷新时还可以进行滑动的问题
        mRecyclerView.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isLoadingData) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        mRecyclerView.getRefreshableView().addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onBottom() {
                Toastor.showSingletonToast(LoadMoreRecyclerActivity.this, "bottom");
                // 到底部自动加载


                isLoadingData = true;
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int count = mDataAdapter.getAdapter().getItemCount();
                        for (int i = count; i < count+15; i++) {
                            mData.add("这是一条RecyclerView的数据" + (i + 1));
                        }
                        mDataAdapter.getAdapter().notifyDataSetChanged();
                        //mDataAdapter.getAdapter().addAll(mData);
                        isLoadingData = false;
                    }
                }, 5000);


            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }
        });
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
