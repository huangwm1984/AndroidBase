package com.android.base.http.impl;

/**
 * Created by Administrator on 2016/4/29.
 */
public interface IRetrofit {

    void attachBaseUrl(String baseUrl);

    <T> T createService(Class<T> clz);

    void destory();

}
