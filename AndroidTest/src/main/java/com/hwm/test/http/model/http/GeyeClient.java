package com.hwm.test.http.model.http;

import com.android.base.http.BaseRestClient;

/**
 * Created by huangwm on 2016/4/30.
 */
public class GeyeClient extends BaseRestClient{

    private static GeyeClient mInstance;

    public static GeyeClient getInstance(String baseUrl) {
        if (mInstance == null) {
            synchronized (ZhihuClient.class) {
                if (mInstance == null) {
                    mInstance = new GeyeClient(baseUrl);
                }
            }
        }
        return mInstance;
    }

    public GeyeClient(String baseUrl){
        attachBaseUrl(baseUrl);
    }
}
