package com.android.base.http.impl;

import android.content.Context;

/**
 * Created by Administrator on 2016/4/29.
 */
public interface IRetrofit {

    void attachBaseUrl(Context context, String baseUrl);

    <T> T createService(Class<T> clz);

    void destory();

}
