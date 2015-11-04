package com.android.base.quickadapter;

public interface RecyclerViewMultiItemTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}