package com.android.test.view.recycler.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.android.base.BaseActivity;
import com.android.base.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.android.base.widget.recyclerview.RecyclerViewUtils;
import com.android.test.R;
import com.android.test.view.pulltorefresh.recycler.adapter.DataAdapter;
import com.android.test.view.pulltorefresh.recycler.widget.HeaderBanner;
import com.android.test.view.recycler.scrollview.block.HeaderBannerBlock;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/27 0027.
 */
public class RecyclerViewInsideScrollViewActivity extends BaseActivity {


    @Bind(R.id.main_recyclerview)
    RecyclerView mRecyclerView;

    List<String> mData = new ArrayList<>();
    DataAdapter mDataAdapter;
    HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    HeaderBanner mFooterView;


    @Override
    protected int getMainContentViewId() {
        return R.layout.act_rcv_inside_sc;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setData();
        setBlock();
        setRecyclerView();
    }

    private void setData() {
        for (int i = 0; i < 100; i++) {
            mData.add("这是一条RecyclerView的数据" + (i + 1));
        }
    }

    private void setBlock() {
        getCommonBlockManager().add(new DataAdapter()).add(new HeaderBannerBlock()).add(new HeaderBanner());
        mDataAdapter = getCommonBlockManager().get(DataAdapter.class);
        mFooterView = getCommonBlockManager().get(HeaderBanner.class);
    }

    private void setRecyclerView() {

        mDataAdapter.setDataAndAdapter(mData);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter.getAdapter());
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //add a FooterView
        RecyclerViewUtils.setFooterView(mRecyclerView, mFooterView.getRootView());


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
