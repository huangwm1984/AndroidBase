package com.android.test.view.pulltorefresh.recycler.adapter;

import android.support.v7.widget.RecyclerView;

import com.android.base.block.SampleBlock;
import com.android.base.quickadapter.recycler.BaseRcvAdapterHelper;
import com.android.base.quickadapter.recycler.ExQuickRcvAdapter;
import com.android.test.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class DataAdapter extends SampleBlock {

    public ExQuickRcvAdapter mQuickRcvAdapter;

    @Override
    protected void onCreated() {

    }

    public void setAdapter(List<String> data, RecyclerView.LayoutManager layoutManager){

        if(mQuickRcvAdapter!=null){
            mQuickRcvAdapter.replaceAll(data);
            return;
        }

        mQuickRcvAdapter = new ExQuickRcvAdapter<String>(mActivity, R.layout.item_pull_to_refresh, data, layoutManager) {

            @Override
            protected void convert(BaseRcvAdapterHelper helper, String item) {
                helper.setText(R.id.textView, "No." + item);
            }
        };

    }
}
