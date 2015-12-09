package com.android.base.widget;

/**
 * Created by Administrator on 2015/11/27 0027.
 */
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;

public class SideLayout extends LinearLayout implements Animator.AnimatorListener {
    private ObjectAnimator showAnimator, hideAnimator;
    private boolean isShowing;
    ViewDragHelper mDragHelper;

    public SideLayout(Context context) {
        super(context);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    public SideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    public SideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    private void init(int width) {
        int childCount = getChildCount();
        if (childCount > 1) {
            View lastView = getChildAt(getChildCount() - 1);
            float sideTransX = -(width - lastView.getMeasuredWidth());
            setTranslationX(sideTransX);
            showAnimator = ObjectAnimator.ofFloat(this, "translationX", sideTransX, 0);
            showAnimator.addListener(this);
            hideAnimator = ObjectAnimator.ofFloat(this, "translationX", 0, sideTransX);
            showAnimator.addListener(this);
        } else {
            throw new RuntimeException("SideLayout must have 2 or more child views");
        }
    }

    public void toggle() {
        if (!isShowing) show();
        else {
            hide();
        }
    }

    public void show() {
        isShowing = true;
        showAnimator.start();
    }

    public void hide() {
        isShowing = false;
        hideAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init(w);
    }

    @Override
    public void onAnimationStart(Animator animator) {
        setClickable(false);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        setClickable(true);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    class DragHelperCallback extends ViewDragHelper.Callback {

        /**
         * 进行捕获拦截，那些View可以进行drag操作
         * @param child
         * @param pointerId
         * @return  直接返回true，拦截所有的VIEW
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        /**
         * 水平滑动 控制left
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtils.e(left);
            return left;
        }

        /**
         * 垂直滑动，控制top
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        /**
         * 返回一个大于0的数，然后才会在水平方向移动
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        /**
         * 返回一个大于0的数，然后才会在垂直方向移动
         * @param child
         * @return
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        /**
         * 拖拽状态发生变化回调
         * @param state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        /**
         * 当拖拽的View的位置发生变化的时候回调(特指capturedview)
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**
         * 捕获captureview的时候回调
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 当拖拽的View手指释放的时候回调
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        /**
         * 当触摸屏幕边界的时候回调
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        /**
         * 是否锁住边界
         * @param edgeFlags
         * @return
         */
        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        /**
         * 在边缘滑动的时候可以设置滑动另一个子View跟着滑动
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }
    }

    /**
     * 事件分发
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }
}