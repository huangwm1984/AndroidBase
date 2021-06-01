package com.android.base.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public CommonViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public static CommonViewHolder createViewHolder(View itemView) {
        CommonViewHolder holder = new CommonViewHolder(itemView);
        return holder;
    }

    public static CommonViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        CommonViewHolder holder = new CommonViewHolder(itemView);
        return holder;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }
}
