package com.hwm.test.view.recycler.extra.impl;


import okhttp3.Call;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public interface ResponseCallback {
    public void onSuccess(Object object);
    public void onError(Call call, Exception e);
}