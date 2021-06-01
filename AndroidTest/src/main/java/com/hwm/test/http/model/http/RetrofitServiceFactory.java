package com.hwm.test.http.model.http;


import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitServiceFactory {

    private static ConcurrentHashMap<Class, Object> mService = new ConcurrentHashMap();


    public static ZhihuService provideZhihuService() {
        return provideService(ApiConstant.BASE_ZHIHU_URL, ZhihuService.class);
    }

    public static GeyeService provideGeyeService() {
        return provideService(ApiConstant.BASE_GEYE_URL, GeyeService.class);
    }

    public static <T> T provideService(String baseUrl, Class cls) {
        Object o = mService.get(cls);
        if (o == null) {
            o = mService.get(cls);
            if (o == null) {
                if(baseUrl.equals(ApiConstant.BASE_ZHIHU_URL)){
                    o = ZhihuClient.getInstance(baseUrl).createService(cls);
                }else if(baseUrl.equals(ApiConstant.BASE_GEYE_URL)){
                    o = GeyeClient.getInstance(baseUrl).createService(cls);
                }
                mService.put(cls, o);
            }
        }
        return (T) o;
    }
}

