package com.android.test.view.recycler.extra.block;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.block.UiBlock;
import com.android.test.R;
import com.android.test.view.recycler.RecyclerViewTestActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class FooterBlock extends UiBlock {

    @Bind(R.id.footer_linearlayout)
    public LinearLayout mFooterLl;

    @Override
    public View getRootView() {
        return LayoutInflater.from(mActivity).inflate(R.layout.item_waterfall_footer, null);
    }

    @Override
    protected void onCreated() {
    }

    public View getFooterView(){
        return mFooterLl;
    }
}
