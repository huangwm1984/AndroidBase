package com.android.base.util.adapter;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}
