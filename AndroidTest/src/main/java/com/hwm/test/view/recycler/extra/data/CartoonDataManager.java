package com.hwm.test.view.recycler.extra.data;


import com.alibaba.fastjson.JSON;
import com.android.base.block.SampleBlock;
import com.hwm.test.view.recycler.extra.entity.TestDataBean;
import com.hwm.test.view.recycler.extra.impl.ResponseCallback;
import com.apkfuns.logutils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class CartoonDataManager extends SampleBlock {

    private String TAG = getClass().getSimpleName();

    /** 加载最新数据的起点 */
    public final int LATEST_INDEX = 0;

    /** 下一页的起始数 */
    private int mNextStart;

    /** 数据的list */
    //private List<ObjectListEntity> mDataList;

    @Override
    protected void onCreated() {

    }

    public void loadNewData(final ResponseCallback callback) {
        loadData(0, callback);
    }

    public void loadData(final int index, final ResponseCallback callback) {
        // 请求的地址头部
        final String URL_HEAD
                = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";
        // 执行网络请求
        OkHttpUtils
                .get()
                .url(URL_HEAD + index)
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
                        LogUtils.d("获取到数据");

                        TestDataBean mTestDataBean = JSON.parseObject(response, TestDataBean.class);

                        mNextStart = mTestDataBean.getData().getNext_start();
                        /*if (mDataList == null || index == LATEST_INDEX) {
                            mDataList = response.getData().getObject_list();
                        } else {
                            mDataList.addAll(response.getData().getObject_list());

                        }
                        callback.onSuccess(mDataList);*/
                        callback.onSuccess(mTestDataBean.getData().getObject_list());
                    }
                });

    }

    //public List<ObjectListEntity> getData() {
    //    return mDataList;
    //}

    public void loadOldData(final ResponseCallback callback) {
        loadData(mNextStart, callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

