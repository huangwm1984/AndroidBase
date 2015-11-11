package com.android.test.view.recyclerview.multi;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.base.BaseActivity;
import com.android.base.common.assist.Toastor;
import com.android.base.quickadapter.recycler.BaseAdapterHelper;
import com.android.base.quickadapter.recycler.MultiItemTypeSupport;
import com.android.base.quickadapter.recycler.QuickAdapter;
import com.android.test.MyApplication;
import com.android.test.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/4 0004.
 */
public class MultiItemActivity extends BaseActivity {
    @Bind(R.id.main_recyclerview)
    RecyclerView mRecyclerView;

    private QuickAdapter<News> mQuickAdapter;
    private List<News> mData;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_normal_recyclerview;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        initData();
        setToolBar();
        initRecyclerView();
        initAdapter();
    }

    private void initData() {
        mData = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            News news = new News();
            int index = random.nextInt(100) % 3;
            if (index == 0) {
                news.setText("新闻标题" + i);
                news.setItemType(News.ITEM_TYPE_TEXT);
            } else if (index == 1) {
                news.setButton("点击我" + i);
                news.setItemType(News.ITEM_TYPE_BUTTON);
            } else if (index == 2) {
                news.setImage("http://e.hiphotos.baidu.com/image/pic/item/d1a20cf431adcbef8286360faeaf2edda3cc9f0d.jpg");
                news.setItemType(News.ITEM_TYPE_IMAGE);
            }
            mData.add(news);
        }
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("多种类型的Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        //paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).paint(paint).build());
    }

    private void initAdapter() {
        MultiItemTypeSupport<News> multiItemTypeSupport = new MultiItemTypeSupport<News>() {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == News.ITEM_TYPE_TEXT) {
                    return R.layout.item_multi_text;
                } else if (viewType == News.ITEM_TYPE_BUTTON) {
                    return R.layout.item_multi_button;
                } else if (viewType == News.ITEM_TYPE_IMAGE) {
                    return R.layout.item_multi_image;
                }
                //默认返回是文本
                return News.ITEM_TYPE_TEXT;
            }

            @Override
            public int getItemViewType(int position, News news) {
                return news.getItemType();
            }
        };

        mQuickAdapter = new QuickAdapter<News>(this, multiItemTypeSupport) {
            @Override
            protected void convert(final BaseAdapterHelper helper, News item) {
                switch (helper.getItemViewType()) {
                    case News.ITEM_TYPE_TEXT:
                        helper.setText(R.id.tv_text, item.getText());
                        break;
                    case News.ITEM_TYPE_BUTTON:
                        helper.setText(R.id.btn_click, item.getButton());
                        helper.setOnClickListener(R.id.btn_click, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(MultiItemActivity.this, "你点击了按钮" + helper.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                                Toastor.showSingleLongToast(mApplicationContext, "你点击了按钮" + helper.getAdapterPosition());
                            }
                        });
                        break;
                    case News.ITEM_TYPE_IMAGE:
                        helper.setImageUrl(R.id.iv_image, item.getImage(), MyApplication.getInstance().getDisplayImageOptions());
                        break;
                }
            }
        };

        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.addAll(mData);
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
