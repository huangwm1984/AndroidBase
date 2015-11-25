package com.android.test.view.recycler.extra;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.base.BaseActivity;
import com.android.base.LoadingAndRetryManager;
import com.android.base.OnLoadingAndRetryListener;
import com.android.base.common.assist.Toastor;
import com.android.base.widget.recycler.OnRcvScrollListener;
import com.android.base.widget.recycler.decoration.DividerGridItemDecoration;
import com.android.base.widget.recycler.layoutmanager.ExStaggeredGridLayoutManager;
import com.android.test.R;
import com.android.test.view.recycler.extra.adapter.CartoonAdapter;
import com.android.test.view.recycler.extra.block.FooterBlock;
import com.android.test.view.recycler.extra.block.HeaderBlock;
import com.android.test.view.recycler.extra.data.CartoonDataManager;
import com.android.test.view.recycler.extra.entity.TestDataBean.DataEntity.ObjectListEntity;
import com.android.test.view.recycler.extra.impl.ResponseCallback;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public class HeaderOrFooterActivity extends BaseActivity implements ResponseCallback {

    @Bind(R.id.content)
    View mContentView;
    @Bind(R.id.waterFall_recyclerView)
    RecyclerView mWaterFallRcv;
    @Bind(R.id.float_imageButton)
    ImageView mFloatIv;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private float mHeaderHeight;
    /** 是否加载数据标志 **/
    private boolean isLoadingData = false;

    HeaderBlock mHeaderBlock;
    FooterBlock mFooterBlock;
    CartoonAdapter mCartoonAdapter;
    CartoonDataManager mDataManager;

    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_extra_rcv;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setLoadingAndRetry();
        setBlock();
        setSwipeRefreshLayout();
        setWaterFallRcv();
        setHeaderView();
        mDataManager.loadNewData(this);
    }

    private void setLoadingAndRetry() {
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(mContentView, new OnLoadingAndRetryListener(){
            @Override
            public void setRetryEvent(View retryView)
            {
                HeaderOrFooterActivity.this.setRetryEvent(retryView);
            }
        });
        mLoadingAndRetryManager.showLoading();
    }

    @OnClick(R.id.float_imageButton) void onClickFloatIv(){
        mWaterFallRcv.smoothScrollToPosition(0);
    }

    private void setBlock() {
        getCommonBlockManager().add(new CartoonAdapter()).add(new CartoonDataManager()).add(new HeaderBlock()).add(new FooterBlock());
        mHeaderBlock = getCommonBlockManager().get(HeaderBlock.class);
        mFooterBlock = getCommonBlockManager().get(FooterBlock.class);
        mCartoonAdapter = getCommonBlockManager().get(CartoonAdapter.class);
        mDataManager = getCommonBlockManager().get(CartoonDataManager.class);
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
                    mDataManager.loadNewData(HeaderOrFooterActivity.this);
                    isLoadingData = true;
                }
            }
        });
    }

    private void setWaterFallRcv() {

        // 设置头部或底部的操作应该在setAdapter之前
        //mWaterFallRcv.addHeaderView(mHeaderBlock.mHeaderLl);
        //mWaterFallRcv.addFooterView(mFooterBlock.mFooterLl);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        mWaterFallRcv.setLayoutManager(layoutManager);

        // 添加分割线
        mWaterFallRcv.addItemDecoration(new DividerGridItemDecoration(this));
        //mWaterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换

        List<ObjectListEntity> mData = new ArrayList<>();// 先放一个空的list
        mCartoonAdapter.setAdapter(mData, layoutManager);
        mCartoonAdapter.getAdapter().setHeaderView(mHeaderBlock.getHeaderView());
        mCartoonAdapter.getAdapter().setFooterView(mFooterBlock.getFooterView());

        // 不显示滚动到顶部/底部的阴影（减少绘制）
        mWaterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //waterFallRcv.setClipToPadding(true);

        mWaterFallRcv.setAdapter(mCartoonAdapter.mQuickRcvAdapter);

        //Solve IndexOutOfBoundsException exception
        //解决上下拉刷新时还可以进行滑动的问题
        mWaterFallRcv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (isLoadingData) {
                            return true;
                        } else {
                            return false;
                        }
                    }
        });

        /**
         * 监听滚动事件
         */
        mWaterFallRcv.addOnScrollListener(new OnRcvScrollListener() {

            @Override
            public void onScrollUp() {
                //Log.d(TAG, "onScrollUp");
                hideViews();
            }

            @Override
            public void onScrollDown() {
                //Log.d(TAG, "onScrollDown");
                showViews();
            }

            @Override
            public void onBottom() {
                //Log.d(TAG, "on bottom");
                Toastor.showSingletonToast(HeaderOrFooterActivity.this, "bottom");
                // 到底部自动加载
                if (!isLoadingData) {
                    isLoadingData = true;
                    mDataManager.loadOldData(HeaderOrFooterActivity.this);
                    mFooterBlock.getFooterView().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {
                setToolbarBgByScrollDistance(distanceY);
            }

            /*@Override
            public void onMoved(int distanceX, int distanceY) {
                //LogUtils.d("distance X = " + distanceX + "distance Y = " + distanceY);
                setToolbarBgByScrollDistance(distanceY);
            }*/
        });
    }


    /**
     * 设置头部的view
     */
    private void setHeaderView() {
        mHeaderBlock.mHeaderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到某个位置
                mWaterFallRcv.scrollToPosition(10);
            }
        });

        ViewTreeObserver vto = mHeaderBlock.mHeaderIv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mHeaderBlock.mHeaderIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mHeaderBlock.mHeaderIv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mHeaderHeight = mHeaderBlock.mHeaderIv.getHeight();
                LogUtils.d("headerHeight" + mHeaderHeight);
            }
        });

    }

    /**
     * 滑动时影藏float button
     */
    private void hideViews() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFloatIv.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFloatIv.animate().translationY(mFloatIv.getHeight() + fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 滑动时出现float button
     */
    private void showViews() {
        mFloatIv.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    /**
     * 通过滚动的状态来设置toolbar的透明度
     */
    private void setToolbarBgByScrollDistance(int distance) {
        //Log.d(TAG, "distance" + distance);
        final float ratio = Math.min(distance / mHeaderHeight, 1);
        final float newAlpha = ratio * 1;
        mToolbar.setAlpha(newAlpha);
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
        Glide.get(activity).clearMemory();
    }

    public void setRetryEvent(View retryView)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toastor.showSingletonToast(HeaderOrFooterActivity.this, "retry event invoked");
                //loadData();
            }
        });
    }

    @Override
    public void onSuccess(Object object) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        isLoadingData = false;
        mFooterBlock.getFooterView().setVisibility(View.GONE);
        mCartoonAdapter.mQuickRcvAdapter.update(mDataManager.getData());
        mLoadingAndRetryManager.showContent();
    }

    @Override
    public void onError(Request request, Exception e) {
        mFooterBlock.getFooterView().setVisibility(View.GONE);
        int itemCount = mCartoonAdapter.mQuickRcvAdapter.getItemCount();
        if(itemCount - 2 <= 0){
            mLoadingAndRetryManager.showEmpty();
        }else{
            mLoadingAndRetryManager.showContent();
        }
    }
}
