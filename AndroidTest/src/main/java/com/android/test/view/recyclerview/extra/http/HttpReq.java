package com.android.test.view.recyclerview.extra.http;

import android.os.Handler;

import com.android.base.common.utils.HandlerUtil;
import com.android.base.http.callback.ResultCallback;
import com.android.base.http.request.OkHttpRequest;
import com.android.test.AppConfig;
import com.android.test.view.recyclerview.extra.entity.TestDataBean;
import com.squareup.okhttp.Request;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public class HttpReq {

    public static final String reqUrl = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";

    public static void loadNewData(final Handler handler){
        new OkHttpRequest.Builder().url(reqUrl + 0).get(new ResultCallback<TestDataBean>() {
            @Override
            public void onError(Request request, Exception e) {
                HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_FAIL_FOR_BEAN);
            }

            @Override
            public void onResponse(TestDataBean response) {
                HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_SUCCESS_FOR_BEAN, response);
            }
        });

    }

    public static void loadOldData(final Handler handler, int next){
        new OkHttpRequest.Builder().url(reqUrl + next).get(new ResultCallback<TestDataBean>() {
            @Override
            public void onError(Request request, Exception e) {
                HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_FAIL_FOR_BEAN);
            }

            @Override
            public void onResponse(TestDataBean response) {
                HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_SUCCESS_FOR_BEAN, response);
            }
        });

    }

}
