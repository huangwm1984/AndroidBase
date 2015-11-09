package com.android.test.view.recyclerview.normal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.test.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/10/31 0031.
 */
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.ViewHolder> {

    private static final int ITEM_SIZE = 50;

    public NormalAdapter(Context context) {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSampleText.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return ITEM_SIZE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_sample)
        TextView mSampleText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

