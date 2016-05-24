package com.hwm.test.http.model.http;


import com.android.base.frame.Base;
import com.android.base.http.BaseRestClient;

/**
 * Created by Administrator on 2016/4/29.
 */
public class ZhihuClient extends BaseRestClient {

    private static ZhihuClient mInstance;

    public static ZhihuClient getInstance(String baseUrl) {
        if (mInstance == null) {
            synchronized (ZhihuClient.class) {
                if (mInstance == null) {
                    mInstance = new ZhihuClient(baseUrl);
                }
            }
        }
        return mInstance;
    }

    public ZhihuClient(String baseUrl){
        attachBaseUrl(Base.getContext(), baseUrl);
    }

}
