package com.android.test.view.recycler.normal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.base.BaseActivity;
import com.android.test.R;
import com.android.test.view.recycler.normal.adapter.NormalAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/31 0031.
 */
public class NormalActivity extends BaseActivity {

    @Bind(R.id.main_recyclerview)
    RecyclerView mRecyclerView;
    NormalAdapter mNormalAdapter;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_normal_rcv;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setToolBar();
        initRecyclerView();
    }

    private void setToolBar() {
        getSupportActionBar().setTitle("单类型的Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            mRecyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mNormalAdapter = new NormalAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mNormalAdapter);

        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
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
    public void onActivitySaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_linearlayout_vertical:
                LinearLayoutManager mManagerVertical = new LinearLayoutManager(this);
                mManagerVertical.setOrientation(OrientationHelper.VERTICAL);
                mRecyclerView.setLayoutManager(mManagerVertical);
                return true;
            case R.id.action_linearlayout_horizontal:
                LinearLayoutManager mManagerHorizontal = new LinearLayoutManager(this);
                mManagerHorizontal.setOrientation(OrientationHelper.HORIZONTAL);
                mRecyclerView.setLayoutManager(mManagerHorizontal);
                return true;
            case R.id.action_gridlayout:
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 4);
                //mGridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                return true;
            case R.id.action_staggeredgridlayout:
                StaggeredGridLayoutManager mStaggeredGridLayout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(mStaggeredGridLayout);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
