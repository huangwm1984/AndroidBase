package com.android.base.quickadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class RecyclerViewBaseQuickAdapter<T, H extends com.android.base.quickadapter.RecyclerViewBaseAdapterHelper> extends RecyclerView.Adapter<com.android.base.quickadapter.RecyclerViewBaseAdapterHelper> implements View.OnClickListener {

    protected static final String TAG = RecyclerViewBaseQuickAdapter.class.getSimpleName();

    protected final Context context;

    protected int layoutResId;

    protected final List<T> data;

    private OnItemClickListener mOnItemClickListener = null;

    protected RecyclerViewMultiItemTypeSupport<T> mMultiItemTypeSupport;


    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * Create a QuickAdapter.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    protected RecyclerViewBaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    protected RecyclerViewBaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : data;
        this.context = context;
        this.layoutResId = layoutResId;
    }

    protected RecyclerViewBaseQuickAdapter(Context context, RecyclerViewMultiItemTypeSupport<T> multiItemTypeSupport) {
        this(context, multiItemTypeSupport, null);
    }

    protected RecyclerViewBaseQuickAdapter(Context context, RecyclerViewMultiItemTypeSupport<T> multiItemTypeSupport, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public T getItem(int position) {
        if (position >= data.size()) return null;
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null) {
            return mMultiItemTypeSupport.getItemViewType(position, getItem(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerViewBaseAdapterHelper onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (mMultiItemTypeSupport != null) {
            int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        }
        view.setOnClickListener(this);
        com.android.base.quickadapter.RecyclerViewBaseAdapterHelper vh = new com.android.base.quickadapter.RecyclerViewBaseAdapterHelper(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(com.android.base.quickadapter.RecyclerViewBaseAdapterHelper helper, int position) {
        helper.itemView.setTag(position);
        T item = getItem(position);
        convert((H) helper, item);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(H helper, T item);

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        set(data.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /**
     * Clear data list
     */
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

}