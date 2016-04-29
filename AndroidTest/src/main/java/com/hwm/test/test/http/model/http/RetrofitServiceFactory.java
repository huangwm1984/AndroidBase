package com.hwm.test.test.http.model.http;

import com.hwm.test.test.http.model.config.ApiConstant;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitServiceFactory {

    private static ConcurrentHashMap<Class, Object> mService = new ConcurrentHashMap();


    public static ZhihuService provideZhihuService() {
        return provideService(ZhihuService.class);
    }

    public static <T> T provideService(Class cls) {
        Object o = mService.get(cls);
        if (o == null) {
            o = mService.get(cls);
            if (o == null) {
                o = ZhihuClient.getInstance(ApiConstant.BASE_ZHIHU_URL).createService(cls);
                mService.put(cls, o);
            }
        }
        return (T) o;
    }
}

