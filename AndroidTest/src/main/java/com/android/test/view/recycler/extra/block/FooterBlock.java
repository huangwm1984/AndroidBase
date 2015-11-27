package com.android.test.view.recycler.extra.block;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.base.block.UiBlock;
import com.android.test.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class FooterBlock extends UiBlock {

    @Bind(R.id.footer_linearlayout)
    public LinearLayout mFooterLl;

    @Override
    public int getRootViewId() {
        return 0;
    }

    @Override
    public View resetRootView(View oldRootView, LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_waterfall_footer, null);
    }

    @Override
    public void onViewCreated() {
    }

    public View getFooterView(){
        return mFooterLl;
    }
}
