package com.android.test.view.pulltorefresh.recycler.adapter;

import android.view.View;

import com.android.base.block.CommonSampleBlock;
import com.android.base.quickadapter.recycler.BaseRcvAdapterHelper;
import com.android.base.quickadapter.recycler.ExBaseRcvQuickAdapter;
import com.android.test.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class DataAdapter extends CommonSampleBlock {

    public ExBaseRcvQuickAdapter mQuickRcvAdapter;

    @Override
    protected void onCreated() {

    }

    public void setAdapter(List<String> data){

        if(mQuickRcvAdapter!=null){
            mQuickRcvAdapter.replaceAll(data);
            return;
        }

        mQuickRcvAdapter = new ExBaseRcvQuickAdapter<String>(mActivity, R.layout.item_pull_to_refresh, data) {

            @Override
            protected void convert(BaseRcvAdapterHelper helper, String item) {
                helper.setText(R.id.textView, "No." + item);
            }
        };

    }
}
