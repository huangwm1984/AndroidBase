package com.android.test.http;

import android.os.Handler;

import com.android.base.common.Log;
import com.android.base.common.utils.HandlerUtil;
import com.android.base.http.OkHttpClientManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/9 0009.
 */
public class TestHttpReq {

    public static final int REQUEST_GET_FAIL = 1001;
    public static final int REQUEST_GET_SUCCESS = 1002;
    public static final int REQUEST_POST_FAIL = 1003;
    public static final int REQUEST_POST_SUCCESS = 1004;
    public static final int REQUEST_POST_FAIL_FOR_BEAN = 1005;
    public static final int REQUEST_POST_SUCCESS_FOR_BEAN = 1006;

    public static final String reqUrl1 = "https://api.douban.com/v2/user/googolmo";
    public static final String reqUrl2 = "http://ixiada.153.cn:50081/exter.shtml?baseLine=400&serviceType=1007";

    public static void request1(final Handler handler) {
        OkHttpClientManager.getAsyn(reqUrl1, new OkHttpClientManager.ResultCallback<String>() {

            @Override
            public void onError(Request request, Exception e) {
                HandlerUtil.sendMessage(handler, REQUEST_GET_FAIL);
            }

            @Override
            public void onResponse(String response) {
                HandlerUtil.sendMessage(handler, REQUEST_GET_SUCCESS, response.toString());
            }
        });
    }

    public static void request2(final Handler handler) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("imsi", "0000000000000000000");
        map.put("city_code", "1001");
        map.put("os_type", "1");
        map.put("province", "10");

        OkHttpClientManager.postAsyn(reqUrl2, map, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                HandlerUtil.sendMessage(handler, REQUEST_POST_FAIL);
            }

            @Override
            public void onResponse(String response) {
                HandlerUtil.sendMessage(handler, REQUEST_POST_SUCCESS, response);
            }
        });
    }

    public static void request3(final Handler handler) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("imsi", "0000000000000000000");
        map.put("city_code", "1001");
        map.put("os_type", "1");
        map.put("province", "10");

        OkHttpClientManager.postAsyn(reqUrl2, map, new OkHttpClientManager.ResultCallback<TestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                HandlerUtil.sendMessage(handler, REQUEST_POST_FAIL_FOR_BEAN);
            }

            @Override
            public void onResponse(TestBean response) {
                HandlerUtil.sendMessage(handler, REQUEST_POST_SUCCESS_FOR_BEAN, response);
            }
        });
    }
}
