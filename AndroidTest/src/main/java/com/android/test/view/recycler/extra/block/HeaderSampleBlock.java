package com.android.test.view.recycler.extra.block;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.test.R;
import com.bumptech.glide.Glide;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/13 0013.
 */
public class HeaderSampleBlock extends CommonUiBlock {


    @Bind(R.id.header_imageView)
    public ImageView mHeaderIv;
    @Bind(R.id.header_linearLayout)
    public LinearLayout mHeaderLl;

    @Override
    public View getRootView() {
        return LayoutInflater.from(mActivity).inflate(R.layout.item_waterfall_header, null);
    }

    @Override
    protected void onCreated() {
        Glide.with(mActivity).load(R.drawable.default_head_pic).into(mHeaderIv);
    }
}
