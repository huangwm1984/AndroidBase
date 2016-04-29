package com.hwm.test.download.test;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.android.base.BaseActivity;
import com.android.base.LoadingAndRetryManager;
import com.android.base.OnLoadingAndRetryListener;
import com.android.base.common.assist.Toastor;
import com.android.base.widget.recyclerview.OnRcvScrollListener;
import com.android.base.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.android.base.widget.recyclerview.RecyclerViewUtils;
import com.hwm.test.R;
import com.hwm.test.download.test.adapter.GameAdapter;
import com.hwm.test.download.test.data.GameDataManager;
import com.hwm.test.download.test.entity.GameInfo;
import com.hwm.test.view.pulltorefresh.recycler.widget.SimpleFooterTest;
import com.hwm.test.view.recycler.extra.impl.ResponseCallback;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class GameListActivity extends BaseActivity implements ResponseCallback {

    @Bind(R.id.game_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_Layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    LoadingAndRetryManager mLoadingAndRetryManager;
    GameAdapter mGameAdapter;
    GameDataManager mGameDataManager;
    SimpleFooterTest mSimpleFooter;
    HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    boolean isLoadingData = false;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_new_game_list;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setToolBar();
        setLoadingAndRetry();
        setBlock();
        setSwipeRefreshLayout();
        setGameRcv();
        setGameRcvOnScroll();
        mGameDataManager.loadNewData(this);
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("多线程断点下载例子");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setLoadingAndRetry() {
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mSwipeRefreshLayout, new OnLoadingAndRetryListener(){
            @Override
            public void setRetryEvent(View retryView)
            {
                GameListActivity.this.setRetryEvent(retryView);
            }
        });
        mLoadingAndRetryManager.showLoading();
    }

    private void setBlock() {
        getCommonBlockManager().add(new GameDataManager()).add(new GameAdapter()).add(new SimpleFooterTest(this));
        mGameAdapter = getCommonBlockManager().get(GameAdapter.class);
        mGameDataManager = getCommonBlockManager().get(GameDataManager.class);
        mSimpleFooter = getCommonBlockManager().get(SimpleFooterTest.class);
    }


    /**
     * 设置下拉刷新控件，下拉后加载新的数据
     */
    private void setSwipeRefreshLayout() {
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoadingData) {
                    //Log.d(TAG, "加载新的数据");
                    mGameDataManager.loadNewData(GameListActivity.this);
                    isLoadingData = true;
                }
            }
        });
    }

    private void setGameRcv() {

        List<GameInfo.DataEntity> mData = new ArrayList<>();// 先放一个空的list
        mGameAdapter.setAdapter(mRecyclerView, mData);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mGameAdapter.getAdapter());
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .paint(paint)
                .build());
        //add a FooterView
        RecyclerViewUtils.setFooterView(mRecyclerView, mSimpleFooter);
        // 不显示滚动到顶部/底部的阴影（减少绘制）
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //解决上下拉刷新时还可以进行滑动的问题
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isLoadingData) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void setGameRcvOnScroll() {
        /**
         * 监听滚动事件
         */
        mRecyclerView.addOnScrollListener(new OnRcvScrollListener() {

            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onBottom() {
                // 到底部自动加载
                if (!isLoadingData) {
                    if(mGameAdapter.getAdapter().getItemCount() == mGameDataManager.getTotalCount()){
                        isLoadingData = false;
                        mHeaderAndFooterRecyclerViewAdapter.removeFooterView(mSimpleFooter);
                    }else{
                        isLoadingData = true;
                        mGameDataManager.loadOldData(GameListActivity.this);
                        mSimpleFooter.setVisibility(View.VISIBLE);
                    }
                }
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

    public void setRetryEvent(View retryView)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toastor.showSingletonToast(mApplicationContext, "retry event invoked");
                isLoadingData = true;
                mGameDataManager.loadNewData(GameListActivity.this);
            }
        });
    }

    @Override
    public void onSuccess(Object object) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mSimpleFooter.setVisibility(View.GONE);
        mGameAdapter.initFlag((List<GameInfo.DataEntity>)object);
        mGameAdapter.getAdapter().addAll((List<GameInfo.DataEntity>)object);
        mLoadingAndRetryManager.showContent();
        isLoadingData = false;
    }

    @Override
    public void onError(Call call, Exception e) {
        isLoadingData = false;
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
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
}
