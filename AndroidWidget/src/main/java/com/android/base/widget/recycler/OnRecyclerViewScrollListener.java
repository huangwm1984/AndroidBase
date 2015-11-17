package com.android.base.widget.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


/**
 * @author Jack Tony
 * @brief recyle view 滚动监听器
 * @date 2015/4/6
 */
public abstract class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private String TAG = getClass().getSimpleName();

    private enum LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * layoutManager的类型（枚举）
     */
    protected LayoutManagerType mLayoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] mLastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int mLastVisibleItemPosition;

    /**
     * 触发在上下滑动监听器的容差距离
     */
    private static final int HIDE_THRESHOLD = 20;

    /**
     * 滑动的距离
     */
    private int mDistance = 0;

    /**
     * 是否需要监听控制
     */
    private boolean mIsScrollDown = true;

    /**
     * Y轴移动的实际距离（最顶部为0）
     */
    private int mScrolledYDistance = 0;

    /**
     * X轴移动的实际距离（最左侧为0）
     */
    private int mScrolledXDistance = 0;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int firstVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // 判断layout manageer的类型
        judgeLayoutManager(layoutManager);
        // 根据类型来计算出第一个可见的item的位置，由此判断是否触发到底部的监听器
        firstVisibleItemPosition = calculateFirstVisibleItemPos(layoutManager, firstVisibleItemPosition);
        // 计算并判断当前是向上滑动还是向下滑动
        calculateScrollUpOrDown(firstVisibleItemPosition, dy);
        // 移动距离超过一定的范围，我们监听就没有啥实际的意义了
        mScrolledXDistance += dx;
        mScrolledYDistance += dy;
        mScrolledXDistance = (mScrolledXDistance < 0) ? 0 : mScrolledXDistance;
        mScrolledYDistance = (mScrolledYDistance < 0) ? 0 : mScrolledYDistance;
        onMoved(mScrolledXDistance, mScrolledYDistance);
    }


    /**
     * 判断layoutmanager的类型
     */
    private void judgeLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            mLayoutManagerType = LayoutManagerType.LINEAR;
        } else if (layoutManager instanceof GridLayoutManager) {
            mLayoutManagerType = LayoutManagerType.GRID;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            mLayoutManagerType = LayoutManagerType.STAGGERED_GRID;
        } else {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are "
                    + "LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
    }

    /**
     * 计算第一个元素的位置
     */
    private int calculateFirstVisibleItemPos(RecyclerView.LayoutManager layoutManager, int firstVisibleItemPosition) {
        switch (mLayoutManagerType) {
            case LINEAR:
                mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case GRID:
                mLastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (mLastPositions == null) {
                    mLastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                mLastPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(mLastPositions);
                mLastVisibleItemPosition = findMax(mLastPositions);
                staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(mLastPositions);
                firstVisibleItemPosition = findMax(mLastPositions);
                break;
        }
        return firstVisibleItemPosition;
    }

    /**
     * 计算当前是向上滑动还是向下滑动
     */
    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (firstVisibleItemPosition == 0) {
            if (!mIsScrollDown) {
                onScrollDown();
                mIsScrollDown = true;
            }
        } else {
            if (mDistance > HIDE_THRESHOLD && mIsScrollDown) {
                onScrollUp();
                mIsScrollDown = false;
                mDistance = 0;
            } else if (mDistance < -HIDE_THRESHOLD && !mIsScrollDown) {
                onScrollDown();
                mIsScrollDown = true;
                mDistance = 0;
            }
        }
        if ((mIsScrollDown && dy > 0) || (!mIsScrollDown && dy < 0)) {
            mDistance += dy;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                && mLastVisibleItemPosition >= totalItemCount - 1) {
            //Log.d(TAG, "is loading more");
            onBottom();
        }
    }


    public abstract void onScrollUp();

    public abstract void onScrollDown();

    public abstract void onBottom();

    public abstract void onMoved(int distanceX ,int distanceY);

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}