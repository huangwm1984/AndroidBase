package com.android.base.quickadapter.recycler;

public interface RecyclerViewMultiItemTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}