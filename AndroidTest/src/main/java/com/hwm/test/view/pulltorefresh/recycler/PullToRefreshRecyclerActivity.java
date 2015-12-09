package com.hwm.test.view.pulltorefresh.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.base.BaseActivity;
import com.android.base.widget.pulltorefresh.PullToRefreshBase;
import com.android.base.widget.pulltorefresh.PullToRefreshRecyclerView;
import com.hwm.test.R;
import com.hwm.test.view.pulltorefresh.recycler.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class PullToRefreshRecyclerActivity extends BaseActivity {

    @Bind(R.id.pull_refresh_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    DataAdapter mDataAdapter;

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
        for (int i = 0; i < 25; i++) {
            mData.add("这是一条RecyclerView的数据" + (i + 1));
        }
    }

    private void setBlock() {
        getCommonBlockManager().add(new DataAdapter());
        mDataAdapter = getCommonBlockManager().get(DataAdapter.class);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter.setDataAndAdapter(mData);
        mRecyclerView.setAdapter(mDataAdapter.mQuickRcvAdapter);
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
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
                        int count = mDataAdapter.getAdapter().getItemCount();
                        for (int i = count; i < count+15; i++) {
                            mData.add("这是一条RecyclerView的数据" + (i + 1));
                        }
                        mDataAdapter.getAdapter().notifyDataSetChanged();
                        refreshView.onRefreshComplete();
                        refreshView.getRefreshableView().smoothScrollToPosition(count + 1);
                    }
                }, 500);
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
