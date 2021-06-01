package com.hwm.test.recyclerview.adapter;

import android.content.Context;
import android.widget.TextView;

import com.android.base.util.adapter.CommonRcvAdapter;
import com.android.base.util.adapter.CommonViewHolder;
import com.hwm.test.R;

/**
 * Created by Administrator on 2016/9/23.
 */

public class RecyclerViewAdapter extends CommonRcvAdapter<String> {

    public RecyclerViewAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    protected void convert(CommonViewHolder viewHolder, String item, int position) {
        TextView textView = viewHolder.findView(R.id.id_item_list_title);
        textView.setText(item);
    }
}
