package com.android.base.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.widget.R;
import com.android.base.widget.banner.transformer.AccordionPageTransformer;
import com.android.base.widget.banner.transformer.AlphaPageTransformer;
import com.android.base.widget.banner.transformer.CubePageTransformer;
import com.android.base.widget.banner.transformer.DefaultPageTransformer;
import com.android.base.widget.banner.transformer.DepthPageTransformer;
import com.android.base.widget.banner.transformer.FadePageTransformer;
import com.android.base.widget.banner.transformer.FlipPageTransformer;
import com.android.base.widget.banner.transformer.RotatePageTransformer;
import com.android.base.widget.banner.transformer.StackPageTransformer;
import com.android.base.widget.banner.transformer.ZoomCenterPageTransformer;
import com.android.base.widget.banner.transformer.ZoomFadePageTransformer;
import com.android.base.widget.banner.transformer.ZoomPageTransformer;
import com.android.base.widget.banner.transformer.ZoomStackPageTransformer;
import com.android.base.widget.banner.widget.LoopViewPager;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public abstract class BaseBanner<T> extends RelativeLayout {
    private static final String TAG = BaseBanner.class.getSimpleName();
    private static final int RMP = LayoutParams.MATCH_PARENT;
    private static final int RWC = LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private ViewPager mViewPager;
    protected List<T> mData = new ArrayList<>();
    private LinearLayout mPointRealContainerLl;
    private TextView mTipTv;
    private boolean mAutoPlayAble = true;
    private boolean mIsAutoPlaying = false;
    private int mAutoPlayInterval = 3;
    private int mPageChangeDuration = 800;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private int mPointLeftRightMargin;
    private int mPointTopBottomMargin;
    private int mPointContainerLeftRightPadding;
    private int mTipTextSize;
    private int mTipTextColor = Color.WHITE;
    private int mPointDrawableResId = R.drawable.selector_basebanner_point;
    private Drawable mPointContainerBackgroundDrawable;
    //huangwm new add
    protected int currentPositon;
    protected boolean isLoopEnable = true;
    protected ScheduledExecutorService mExecutor;

    private Handler mAutoPlayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            scrollToNextItem(currentPositon);
        }
    };

    public BaseBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        initView(context);
    }

    private void initDefaultAttrs(Context context) {
        mPointLeftRightMargin = dp2px(context, 3);
        mPointTopBottomMargin = dp2px(context, 6);
        mPointContainerLeftRightPadding = dp2px(context, 10);
        mTipTextSize = sp2px(context, 8);
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseBanner);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BaseBanner_banner_pointDrawable) {
            mPointDrawableResId = typedArray.getResourceId(attr, R.drawable.selector_basebanner_point);
        } else if (attr == R.styleable.BaseBanner_banner_pointContainerBackground) {
            mPointContainerBackgroundDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BaseBanner_banner_pointLeftRightMargin) {
            mPointLeftRightMargin = typedArray.getDimensionPixelSize(attr, mPointLeftRightMargin);
        } else if (attr == R.styleable.BaseBanner_banner_pointContainerLeftRightPadding) {
            mPointContainerLeftRightPadding = typedArray.getDimensionPixelSize(attr, mPointContainerLeftRightPadding);
        } else if (attr == R.styleable.BaseBanner_banner_pointTopBottomMargin) {
            mPointTopBottomMargin = typedArray.getDimensionPixelSize(attr, mPointTopBottomMargin);
        } else if (attr == R.styleable.BaseBanner_banner_pointGravity) {
            mPointGravity = typedArray.getInt(attr, mPointGravity);
        } else if (attr == R.styleable.BaseBanner_banner_pointAutoPlayAble) {
            mAutoPlayAble = typedArray.getBoolean(attr, mAutoPlayAble);
        } else if (attr == R.styleable.BaseBanner_banner_pointAutoPlayInterval) {
            mAutoPlayInterval = typedArray.getInteger(attr, mAutoPlayInterval);
        } else if (attr == R.styleable.BaseBanner_banner_pageChangeDuration) {
            mPageChangeDuration = typedArray.getInteger(attr, mPageChangeDuration);
        } else if (attr == R.styleable.BaseBanner_banner_transitionEffect) {
            int ordinal = typedArray.getInt(attr, TransitionEffect.Accordion.ordinal());
            setTransitionEffect(TransitionEffect.values()[ordinal]);
        } else if (attr == R.styleable.BaseBanner_banner_tipTextColor) {
            mTipTextColor = typedArray.getColor(attr, mTipTextColor);
        } else if (attr == R.styleable.BaseBanner_banner_tipTextSize) {
            mTipTextSize = typedArray.getDimensionPixelSize(attr, mTipTextSize);
        } else if(attr == R.styleable.BaseBanner_banner_LoopEnable){
            isLoopEnable = typedArray.getBoolean(attr, isLoopEnable);
        }
    }

    private void initView(Context context) {
        //create ViewPager
        mViewPager = isLoopEnable ? new BaseLoopViewPager(context) : new BaseViewPager(context);
        addView(mViewPager, new LayoutParams(RMP, RMP));
        setPageChangeDuration(mPageChangeDuration);

        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }
        pointContainerRl.setPadding(mPointContainerLeftRightPadding, 0, mPointContainerLeftRightPadding, 0);
        LayoutParams pointContainerLp = new LayoutParams(RMP, RWC);
        // 处理圆点在顶部还是底部
        if ((mPointGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        addView(pointContainerRl, pointContainerLp);

        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setId(R.id.banner_pointContainerId);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams pointRealContainerLp = new LayoutParams(RWC, RWC);
        pointContainerRl.addView(mPointRealContainerLl, pointRealContainerLp);

        LayoutParams tipLp = new LayoutParams(RMP, getResources().getDrawable(mPointDrawableResId).getIntrinsicHeight() + 2 * mPointTopBottomMargin);
        mTipTv = new TextView(context);
        mTipTv.setGravity(Gravity.CENTER_VERTICAL);
        mTipTv.setSingleLine(true);
        mTipTv.setEllipsize(TextUtils.TruncateAt.END);
        mTipTv.setTextColor(mTipTextColor);
        mTipTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTipTextSize);
        pointContainerRl.addView(mTipTv, tipLp);

        int horizontalGravity = mPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 处理圆点在左边、右边还是水平居中
        if (horizontalGravity == Gravity.LEFT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tipLp.addRule(RelativeLayout.RIGHT_OF, R.id.banner_pointContainerId);
            mTipTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else if (horizontalGravity == Gravity.RIGHT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_pointContainerId);
        } else {
            pointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_pointContainerId);
        }
    }

    /**
     * 设置页码切换过程的时间长度
     *
     * @param duration 页码切换过程的时间长度
     */
    public void setPageChangeDuration(int duration) {
        if (duration >= 0 && duration <= 2000) {
            if(mViewPager instanceof BaseLoopViewPager){
                ((BaseLoopViewPager) mViewPager).setPageChangeDuration(duration);
            }else if(mViewPager instanceof BaseViewPager){
                ((BaseViewPager) mViewPager).setPageChangeDuration(duration);
            }
        }
    }

    public void startScroll() {
        if (mData == null) {
            return;
        }

        onTitleSlect(mTipTv, currentPositon);
        setViewPager();
        goScroll();
    }

    public void start() {
        if (mData == null) {
            return;
        }

        onTitleSlect(mTipTv, currentPositon);
        setViewPager();
    }

    public void setViewPager() {
        mViewPager.setAdapter(new PageAdapter());
        mViewPager.setOffscreenPageLimit(mData.size());
        mViewPager.addOnPageChangeListener(new ChangePointListener());

        initPoints();
        switchToPoint(0);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPager.addOnPageChangeListener(listener);
    }

    private void initPoints() {
        mPointRealContainerLl.removeAllViews();
        mViewPager.removeAllViews();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        lp.setMargins(mPointLeftRightMargin, mPointTopBottomMargin, mPointLeftRightMargin, mPointTopBottomMargin);
        ImageView imageView;
        for (int i = 0; i < mData.size(); i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setImageResource(mPointDrawableResId);
            mPointRealContainerLl.addView(imageView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pauseScroll();
//              Log.d(TAG, "dispatchTouchEvent--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                goScroll();
//                Log.d(TAG, "dispatchTouchEvent--->ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                goScroll();
//              Log.d(TAG, "dispatchTouchEvent--->ACTION_CANCEL");
                break;

        }
        return super.dispatchTouchEvent(ev);
    }


    /** scroll to next item */
    private void scrollToNextItem(int position) {
        position++;
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            goScroll();
        } else if (visibility == INVISIBLE) {
            pauseScroll();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseScroll();
    }

    private void switchToPoint(int newCurrentPoint) {
        for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
            mPointRealContainerLl.getChildAt(i).setEnabled(false);
        }
        mPointRealContainerLl.getChildAt(newCurrentPoint).setEnabled(true);

        if (mTipTv != null) {
            onTitleSlect(mTipTv, currentPositon);
        }
    }

    /**
     * 设置页面也换动画
     *
     * @param effect
     */
    public void setTransitionEffect(TransitionEffect effect) {
        switch (effect) {
            case Default:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new DefaultPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new DefaultPageTransformer());
                }
                break;
            case Alpha:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new AlphaPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new AlphaPageTransformer());
                }
                break;
            case Rotate:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new RotatePageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new RotatePageTransformer());
                }
                break;
            case Cube:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new CubePageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new CubePageTransformer());
                }
                break;
            case Flip:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new FlipPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new FlipPageTransformer());
                }
                break;
            case Accordion:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new AccordionPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new AccordionPageTransformer());
                }
                break;
            case ZoomFade:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new ZoomFadePageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new ZoomFadePageTransformer());
                }
                break;
            case Fade:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new FadePageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new FadePageTransformer());
                }
                break;
            case ZoomCenter:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new ZoomCenterPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new ZoomCenterPageTransformer());
                }
                break;
            case ZoomStack:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new ZoomStackPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new ZoomStackPageTransformer());
                }
                break;
            case Stack:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new StackPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new StackPageTransformer());
                }
                break;
            case Depth:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new DepthPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new DepthPageTransformer());
                }
                break;
            case Zoom:
                if(mViewPager instanceof BaseLoopViewPager){
                    mViewPager.setPageTransformer(true, new ZoomPageTransformer());
                }else if(mViewPager instanceof BaseViewPager){
                    mViewPager.setPageTransformer(true, new ZoomPageTransformer());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置自定义页面切换动画
     *
     * @param transformer
     */
    public void setPageTransformer(ViewPager.PageTransformer transformer) {
        if (transformer != null) {
            mViewPager.setPageTransformer(true, transformer);
        }
    }

    private final class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //return mAutoPlayAble ? Integer.MAX_VALUE : mViews.size();
            return mData.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            /*final int pos = position % mViews.size();
            //View view = mViews.get(position % mViews.size());
            View view = mViews.get(pos);

            // 在destroyItem方法中销毁的话，当只有3页时会有问题
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
            container.addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mImageViewListener!=null){
                        mImageViewListener.onImageViewOnClick(pos, v);
                    }
                }
            });*/
            View view = onCreateItemView(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onVpItemClickListener != null) {
                        onVpItemClickListener.onItemClick(position);
                    }
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private final class ChangePointListener extends BaseLoopViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            currentPositon = position % mData.size();
            switchToPoint(currentPositon);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mTipTv != null) {
                if (positionOffset > 0.5) {
                    onTitleSlect(mTipTv, currentPositon);
                    ViewHelper.setAlpha(mTipTv, positionOffset);
                } else {
                    ViewHelper.setAlpha(mTipTv, 1 - positionOffset);
                    onTitleSlect(mTipTv, currentPositon);
                }
            }
        }
    }

    public enum TransitionEffect {
        Default,
        Alpha,
        Rotate,
        Cube,
        Flip,
        Accordion,
        ZoomFade,
        Fade,
        ZoomCenter,
        ZoomStack,
        Stack,
        Depth,
        Zoom
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    private static void debug(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * Override this method to set title content when vp scroll to the position,
     * also you can set title attr,such as textcolor and etc.
     * if setIndicatorGravity == Gravity.CENTER,do nothing.
     */
    public void onTitleSlect(TextView tv, int position) {
    }

    /** set data source list */
    public void setSource(List<T> list) {
        this.mData = list;
        //return (T) this;
    }

    /** for LoopViewPager */
    public void goScroll() {
        if (!isValid()) {
            return;
        }

        if (mIsAutoPlaying) {
            return;
        }
        if (isLoopViewPager() && mAutoPlayAble) {
            pauseScroll();
            mExecutor = Executors.newSingleThreadScheduledExecutor();
            //command：执行线程
            //initialDelay：初始化延时
            //period：两次开始执行最小间隔时间
            //unit：计时单位
            mExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    mAutoPlayHandler.obtainMessage().sendToTarget();
                }
            }, mAutoPlayInterval, mAutoPlayInterval, TimeUnit.SECONDS);
            mIsAutoPlaying = true;
        } else {
            mIsAutoPlaying = false;
            switchToPoint(0);
        }
    }

    /** for LoopViewPager */
    public void pauseScroll() {
        if (mExecutor != null) {
            mExecutor.shutdown();
            mExecutor = null;
        }
        Log.d(TAG, this.getClass().getSimpleName() + "--->pauseScroll()");

        mIsAutoPlaying = false;
    }

    protected boolean isLoopViewPager() {
        return mViewPager instanceof LoopViewPager;
    }

    protected boolean isValid() {
        if (mViewPager == null) {
            Log.e(TAG, "ViewPager is not exist!");
            return false;
        }

        if (mData == null || mData.size() == 0) {
            Log.e(TAG, "DataList must be not empty!");
            return false;
        }

        return true;
    }

    /** create viewpager item layout */
    public abstract View onCreateItemView(int position);

    private OnVpItemClickListener onVpItemClickListener;

    public void setOnItemClickListener(OnVpItemClickListener listener) {
        this.onVpItemClickListener = listener;
    }

    public interface OnVpItemClickListener {
        void onItemClick(int position);
    }
}