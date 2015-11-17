package com.android.test.view.recyclerview.extra;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.base.BaseActivity;
import com.android.base.LoadingAndRetryManager;
import com.android.base.OnLoadingAndRetryListener;
import com.android.base.common.assist.Toastor;
import com.android.base.quickadapter.recycler.BaseRcvAdapterHelper;
import com.android.base.quickadapter.recycler.ExBaseRcvQuickAdapter;
import com.android.base.widget.DynamicHeightImageView;
import com.android.base.widget.recycler.ExRecyclerView;
import com.android.base.widget.recycler.OnRecyclerViewScrollListener;
import com.android.base.widget.recycler.decoration.DividerGridItemDecoration;
import com.android.base.widget.recycler.layoutmanager.ExStaggeredGridLayoutManager;
import com.android.test.AppConfig;
import com.android.test.MainActivity;
import com.android.test.R;
import com.android.test.view.recyclerview.extra.block.HeaderBlock;
import com.android.test.view.recyclerview.extra.entity.TestDataBean;
import com.android.test.view.recyclerview.extra.entity.TestDataBean.DataEntity.ObjectListEntity;
import com.android.test.view.recyclerview.extra.http.HttpReq;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public class HeaderOrFooterActivity extends BaseActivity {

    @Bind(R.id.content)
    View mContentView;
    @Bind(R.id.waterFall_recyclerView)
    ExRecyclerView mWaterFallRcv;
    @Bind(R.id.float_imageButton)
    ImageView mFloatIv;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private Button mFooterBtn;
    private ExBaseRcvQuickAdapter mQuickRcvAdapter;
    private float mHeaderHeight;
    /** 是否加载数据标志 **/
    private boolean isLoadingData = false;
    /** 下一页的起始数 */
    private int mNextStart;

    HeaderBlock mHeaderBlock;
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_extra_rcv;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setLoadingAndRetry();
        Glide.get(this).setMemoryCategory(MemoryCategory.LOW);
        getUIBlockManager().add(new HeaderBlock());
        setWaterFallRcv();
        setHeaderView();
        setFooterView();
        HttpReq.loadNewData(mHandler);
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

    private void setWaterFallRcv() {

        mHeaderBlock = getUIBlockManager().get(HeaderBlock.class);
        mFooterBtn = new Button(this);

        // 设置头部或底部的操作应该在setAdapter之前
        mWaterFallRcv.addHeaderView(mHeaderBlock.mHeaderLl);
        mWaterFallRcv.addFooterView(mFooterBtn);

        ExStaggeredGridLayoutManager staggeredGridLayoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        mWaterFallRcv.setLayoutManager(staggeredGridLayoutManager);

        // 添加分割线
        mWaterFallRcv.addItemDecoration(new DividerGridItemDecoration(this));
        //mWaterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换

        List<ObjectListEntity> mData = new ArrayList<>();// 先放一个空的list
        setWaterFallAdapter(mData);


        // 不显示滚动到顶部/底部的阴影（减少绘制）
        mWaterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //waterFallRcv.setClipToPadding(true);
        mWaterFallRcv.setAdapter(mQuickRcvAdapter);

        /**
         * 监听滚动事件
         */
        mWaterFallRcv.addOnScrollListener(new OnRecyclerViewScrollListener() {

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
                    HttpReq.loadOldData(mHandler, mNextStart);
                    mFooterBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onMoved(int distanceX, int distanceY) {
                //LogUtils.d("distance X = " + distanceX + "distance Y = " + distanceY);
                setToolbarBgByScrollDistance(distanceY);
            }
        });
    }

    private void setWaterFallAdapter(List<ObjectListEntity> data) {

        mQuickRcvAdapter = new ExBaseRcvQuickAdapter<ObjectListEntity>(this, R.layout.item_waterfall, data) {

            @Override
            protected void convert(BaseRcvAdapterHelper helper, ObjectListEntity item) {
                //helper.setImageUrl(R.id.wf_item_content_imageView, item.getPhoto().getPath(), MyApplication.getInstance().getDisplayImageOptions(R.drawable.default_head_pic, R.drawable.default_head_pic));
                float picRatio = (float) item.getPhoto().getHeight() / item.getPhoto().getWidth();
                //helper.setHeightRatio(R.id.wf_item_content_imageView, item.getPhoto().getHeight(), picRatio);
                //helper.setImageUrl(HeaderOrFooterActivity.this, R.id.wf_item_content_imageView, item.getPhoto().getPath());
                //DynamicHeightSimpleDraweeView imageView = (DynamicHeightSimpleDraweeView) helper.getView(R.id.wf_item_content_DraweeView);
                //imageView.setImageURI(Uri.parse(item.getPhoto().getPath()));
                DynamicHeightImageView contentImageView = helper.getView(R.id.wf_item_content_DraweeView);
                Glide.with(HeaderOrFooterActivity.this)
                        .load(item.getPhoto().getPath())
                        .crossFade()
                        //.placeholder(R.drawable.default_head_pic)
                        .into(contentImageView);
                contentImageView.setHeightRatio(picRatio);
                ImageView userImageView = helper.getView(R.id.wf_item_user_head_draweeView);
                helper.setText(R.id.wf_item_description_textView, item.getMsg());
                Glide.with(HeaderOrFooterActivity.this)
                        .load("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg")
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(HeaderOrFooterActivity.this))
                        .into(userImageView);
                //helper.setImageUrl(R.id.wf_item_user_head_imageView, "http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg", MyApplication.getInstance().getDisplayImageOptions());
                helper.setText(R.id.wf_item_positon_textView, "No." + helper.getLayoutPosition());
            }
        };
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
        /*mHeaderBlock.mHeaderIv.post(new Runnable() {
            @Override
            public void run() {
                mHeaderHeight = mHeaderBlock.mHeaderIv.getHeight();
                LogUtils.d("headerHeight" + mHeaderHeight);
            }
        });*/

        ViewTreeObserver vto = mHeaderBlock.mHeaderIv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mHeaderBlock.mHeaderIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    mHeaderBlock.mHeaderIv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mHeaderHeight = mHeaderBlock.mHeaderIv.getHeight();
                LogUtils.d("headerHeight" + mHeaderHeight);
            }
        });

    }

    /**
     * 设置底部的view
     */
    private void setFooterView() {
        mFooterBtn.setText("正在加载...");
        mFooterBtn.getBackground().setAlpha(80);
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
        //Glide.get(this).clearMemory();
        //Glide.get(this).trimMemory(ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case AppConfig.REQUEST_GET_FAIL_FOR_BEAN://失败
                mLoadingAndRetryManager.showEmpty();
                break;
            case AppConfig.REQUEST_GET_SUCCESS_FOR_BEAN://成功
                TestDataBean mData = (TestDataBean) msg.obj;
                mNextStart = mData.getData().getNext_start();
                if(mQuickRcvAdapter!=null) {
                    mQuickRcvAdapter.addAll(mData.getData().getObject_list());
                    mLoadingAndRetryManager.showContent();
                    isLoadingData = false;
                }
                break;
            default:
                break;
        }
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
}
