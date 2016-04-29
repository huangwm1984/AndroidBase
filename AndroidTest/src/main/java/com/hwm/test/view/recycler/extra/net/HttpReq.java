package com.hwm.test.view.recycler.extra.net;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.android.base.common.utils.HandlerUtil;
import com.hwm.test.AppConfig;
import com.hwm.test.view.recycler.extra.entity.TestDataBean;
import com.hwm.test.view.recycler.extra.impl.ResponseCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public class HttpReq {

    public static final String reqUrl = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";

    public static void loadNewData(final Handler handler){

        OkHttpUtils
                .get()
                .url(reqUrl + 0)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e) {
                        HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_FAIL_FOR_BEAN);
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        TestDataBean mTestDataBean = JSON.parseObject(response, TestDataBean.class);
                        HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_SUCCESS_FOR_BEAN, mTestDataBean);
                    }
                });

    }

    public static void loadOldData(final Handler handler, int next){
        OkHttpUtils
                .get()
                .url(reqUrl + next)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e) {
                        HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_FAIL_FOR_BEAN);
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        TestDataBean mTestDataBean = JSON.parseObject(response, TestDataBean.class);
                        HandlerUtil.sendMessage(handler, AppConfig.REQUEST_GET_SUCCESS_FOR_BEAN, response);
                    }
                });

    }

    public static void loadData(String url, final ResponseCallback callback){

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e) {
                        callback.onError(call, e);
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        TestDataBean mTestDataBean = JSON.parseObject(response, TestDataBean.class);
                        callback.onSuccess(response);
                    }
                });

    }

}
