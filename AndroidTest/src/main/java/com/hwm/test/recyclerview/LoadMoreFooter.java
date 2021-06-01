package com.hwm.test.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hwm.test.R;

public class LoadMoreFooter extends LinearLayout {

    private LinearLayout mContainer;
    private ProgressBar progressBar;
    private TextView mText;


    public LoadMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mContainer = new LinearLayout(getContext());
        mContainer.setGravity(Gravity.CENTER);
        mContainer.setOrientation(HORIZONTAL);
        addView(mContainer, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContainer.addView(progressBar);

        mText = new TextView(getContext());
        mText.setText("正在加载...");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins( (int)getResources().getDimension(R.dimen.common_marin),0,0,0 );
        mText.setLayoutParams(layoutParams);
        mContainer.addView(mText);
    }

    public void setNoLoadMore(int visibility){
        mContainer.setVisibility(visibility);
    }

}
