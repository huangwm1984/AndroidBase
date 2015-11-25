package com.android.test.view.pulltorefresh.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.base.BaseActivity;
import com.android.base.widget.pulltorefresh.PullToRefreshBase;
import com.android.base.widget.pulltorefresh.PullToRefreshRecyclerView;
import com.android.test.R;
import com.android.test.view.pulltorefresh.recycler.adapter.DataAdapter;
import com.android.test.view.recycler.extra.block.FooterBlock;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class LoadMoreRecyclerActivity extends BaseActivity {

    @Bind(R.id.pull_refresh_recycler_view)
    PullToRefreshRecyclerView mRecyclerView;

    private List<String> mData = new ArrayList<>();
    DataAdapter mDataAdapter;
    FooterBlock mFooterBlock;


    @Override
    protected int getMainContentViewId() {
        return R.layout.act_pull_to_refresh_recycler;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setData();
        setBlock();
        setRecyclerView();
    }

    private void setData() {
        for (int i = 0; i < 25; i++) {
            mData.add("这是一条RecyclerView的数据" + i);
        }
    }

    private void setBlock() {
        getCommonBlockManager().add(new DataAdapter()).add(new FooterBlock());
        mDataAdapter = getCommonBlockManager().get(DataAdapter.class);
        mFooterBlock = getCommonBlockManager().get(FooterBlock.class);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter.setAdapter(mData, layoutManager);
        mRecyclerView.setAdapter(mDataAdapter.mQuickRcvAdapter);
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
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
