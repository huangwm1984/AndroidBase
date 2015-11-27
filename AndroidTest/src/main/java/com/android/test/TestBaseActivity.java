package com.android.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.base.BaseActivity;
import com.android.base.common.Log;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
public abstract class TestBaseActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = "TestBaseActivity";

    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.container)
    LinearLayout mContainer;
    public ScrollView mScrollView;

    @Override
    protected int getMainContentViewId() {
        return R.layout.act_main;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();

        mScrollView = (ScrollView) mContainer.getParent();
        mTitle.setText(getMainTitle());
        Log.e("getMainTitle()" + getMainTitle());
        //mTvSubTitle = (TextView) container.findViewById(R.id.sub_title);

        String[] bttxt = getButtonTexts();
        if (bttxt != null) {
            for (int i = 0; i < bttxt.length; i++) {
                Button bt = new Button(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int margin = getResources().getDimensionPixelSize(R.dimen.common_marin);
                lp.setMargins(margin, margin, margin, margin);
                bt.setId(i);
                bt.setText(bttxt[i]);
                bt.setOnClickListener(this);
                bt.setLayoutParams(lp);
                mContainer.addView(bt);
            }
        }
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

    /**
     * 获取主标题
     */
    public abstract String getMainTitle();

    /**
     * 设置二级标题
     */
    /*public void setSubTitile(String st) {
        mTvSubTitle.setText(st);
    }*/

    /**
     * 取button列表
     */
    public abstract String[] getButtonTexts();

    /**
     * 在{@link #onClick(View)} 里调用。
     * id值得含义为：若{@link #getButtonTexts()}的string[]数组长度为len，则id从0,1,2到len-1.
     * 点击第N个按钮，id变为N。
     */
    //public abstract Runnable getButtonClickRunnable(final int id);
    public abstract void getButtonClick(final int id);

    @Override
    public void onClick(View v) {
        /*Runnable r = getButtonClickRunnable(v.getId());
        if (r != null) {
            new Thread(r).start();
        }*/
        getButtonClick(v.getId());
    }

}
