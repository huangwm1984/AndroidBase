package com.android.base.http;

import com.android.base.http.impl.IRetrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by Administrator on 2016/4/29.
 */
public class BaseRestClient implements IRetrofit {

    private Retrofit mRetrofit;

    @Override
    public void attachBaseUrl(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }

    @Override
    public <T> T createService(Class<T> clz) {
        return mRetrofit.create(clz);
    }

    @Override
    public void destory() {
        mRetrofit = null;
    }


}
