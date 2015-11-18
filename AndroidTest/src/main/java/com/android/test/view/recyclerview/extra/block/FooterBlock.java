package com.android.test.view.recyclerview.extra.block;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.base.block.UIBlock;
import com.android.test.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class FooterBlock extends UIBlock {

    @Bind(R.id.footer_linearlayout)
    public LinearLayout mFooterLl;

    @Override
    public View getRootView() {
        return LayoutInflater.from(mActivity).inflate(R.layout.item_waterfall_footer, null);
    }

    @Override
    protected void onViewCreated() {

    }
}
