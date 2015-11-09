package com.android.test.view.recyclerview.normal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.base.BaseActivity;
import com.android.test.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/4 0004.
 */
public class MultiItemActivity extends BaseActivity {
    @Bind(R.id.main_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_normal_recyclerview;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setToolBar();
        initRecyclerView();
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
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .paint(paint)
                .build());
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
