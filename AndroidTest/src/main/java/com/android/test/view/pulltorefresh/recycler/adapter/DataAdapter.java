package com.android.test.view.pulltorefresh.recycler.adapter;

import android.content.Intent;
import android.view.View;

import com.android.base.block.SampleBlock;
import com.android.base.common.assist.Toastor;
import com.android.base.quickadapter.recycler.BaseRcvAdapterHelper;
import com.android.base.quickadapter.recycler.QuickRcvAdapter;
import com.android.test.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class DataAdapter extends SampleBlock {

    //public ExQuickRcvAdapterTest mQuickRcvAdapter;
    public QuickRcvAdapter mQuickRcvAdapter;

    @Override
    protected void onCreated() {

    }

    public void setDataAndAdapter(List<String> data){

        /*if(mQuickRcvAdapter!=null){
            mQuickRcvAdapter.replaceAll(data);
            return;
        }

        mQuickRcvAdapter = new ExQuickRcvAdapterTest<String>(mActivity, R.layout.item_pull_to_refresh, data, mQuickRcvAdapter) {

            @Override
            protected void convert(BaseRcvAdapterHelper helper, String item) {
                helper.setText(R.id.textView, "No." + item);
            }
        };*/

        mQuickRcvAdapter = new QuickRcvAdapter<String>(mActivity, R.layout.item_pull_to_refresh, data) {
            @Override
            protected void convert(BaseRcvAdapterHelper helper, int position, final String item) {
                helper.setText(R.id.textView, "No." + item);
                helper.setOnClickListener(R.id.textView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toastor.showSingleLongToast(v.getContext().getApplicationContext(), "你点中了" + item);
                    }
                });
            }
        };
    }

    public QuickRcvAdapter getAdapter(){
        return mQuickRcvAdapter;
    }

}
