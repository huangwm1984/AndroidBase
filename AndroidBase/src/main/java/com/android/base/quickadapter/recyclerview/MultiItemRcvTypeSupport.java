package com.android.base.quickadapter.recyclerview;

public interface MultiItemRcvTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}