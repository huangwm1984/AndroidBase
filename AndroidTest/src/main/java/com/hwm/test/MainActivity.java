package com.hwm.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.base.basic.BaseActivity;
import com.hwm.test.http.RetrofitActivity;


/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ScrollView mScrollView;
    private TextView mTitle;
    private LinearLayout mContainer;

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initView();
        initData();
    }


    public void initView() {
        mTitle = bindView(R.id.title);
        mContainer = bindView(R.id.container);
        mScrollView = (ScrollView) mContainer.getParent();
    }


    public void initData() {
        mTitle.setText("huangwm test");
        String[] bttxt = getResources().getStringArray(R.array.test_list);
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
    protected int setContainerId() {
        return 0;
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case 0:
                gotoActivity(RetrofitActivity.class, false);
                break;
            case 1:
                //gotoActivity(FragmentTabHostActivity.class, false);
                break;
            default:
                showShortToast("还在开发中...");
                break;
        }

    }

}
