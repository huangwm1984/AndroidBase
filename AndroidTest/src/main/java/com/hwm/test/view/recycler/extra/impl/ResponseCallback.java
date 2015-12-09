package com.hwm.test.view.recycler.extra.impl;

import com.squareup.okhttp.Request;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public interface ResponseCallback {
    public void onSuccess(Object object);
    public void onError(Request request, Exception e);
}