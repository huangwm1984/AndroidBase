package com.android.test.view.recycler.extra.block;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.base.block.UiBlock;
import com.android.test.R;
import com.bumptech.glide.Glide;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/13 0013.
 */
public class HeaderBlock extends UiBlock {


    @Bind(R.id.header_imageView)
    public ImageView mHeaderIv;
    @Bind(R.id.header_linearLayout)
    public LinearLayout mHeaderLl;

    @Override
    public int getRootViewId() {
        return 0;
    }

    @Override
    public void onViewCreated() {
        Glide.with(mActivity).load(R.drawable.default_head_pic).into(mHeaderIv);
    }

    @Override
    public View resetRootView(View oldRootView, LayoutInflater inflater) {
        View view = inflater.from(mActivity).inflate(R.layout.item_waterfall_header, null);
        return view;
    }

    public View getHeaderView(){
        return mHeaderLl;
    }
}
