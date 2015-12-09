package com.hwm.test.view.banner.block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.android.base.widget.banner.BaseBanner;

/**
 * Created by Administrator on 2015/11/23 0023.
 */
public class SimpleGuideBanner extends BaseBanner<View> {

    public SimpleGuideBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleGuideBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public View onCreateItemView(int position) {
        View view = mData.get(position);
        return view;
    }
}
