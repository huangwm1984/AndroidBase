package com.hwm.test.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/9/27.
 */

public class HeaderWrapper extends LinearLayout {

    private LinearLayout mContainer;


    public HeaderWrapper(Context context) {
        super(context);
        initView();
    }

    public HeaderWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mContainer = new LinearLayout(getContext());
        mContainer.setGravity(Gravity.CENTER);
        mContainer.setOrientation(VERTICAL);
        addView(mContainer, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addView(View view){
        mContainer.addView(view);
    }

    public void setVisibility(int visiblity){
        super.setVisibility(visiblity);
        mContainer.setVisibility(visiblity);
    }
}
