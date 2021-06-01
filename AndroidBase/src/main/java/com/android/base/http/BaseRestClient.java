package com.android.base.http;

import android.content.Context;

import com.android.base.http.impl.IRetrofit;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by Administrator on 2016/4/29.
 */
public class BaseRestClient implements IRetrofit {

    private Retrofit mRetrofit;

    @Override
    public void attachBaseUrl(Context context, String baseUrl) {

        //okhttp3 cookie 持久化
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

        //okhttp3 提供的日志系统
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .cookieJar(cookieJar)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
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
