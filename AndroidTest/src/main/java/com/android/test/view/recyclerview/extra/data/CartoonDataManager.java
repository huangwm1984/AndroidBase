package com.android.test.view.recyclerview.extra.data;


import com.android.base.block.CommonBlock;
import com.android.base.http.callback.ResultCallback;
import com.android.base.http.request.OkHttpRequest;
import com.android.test.view.recyclerview.extra.entity.TestDataBean;
import com.android.test.view.recyclerview.extra.entity.TestDataBean.DataEntity.ObjectListEntity;
import com.android.test.view.recyclerview.extra.impl.ResponseCallback;
import com.apkfuns.logutils.LogUtils;
import com.squareup.okhttp.Request;

import java.util.List;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class CartoonDataManager extends CommonBlock{

    private String TAG = getClass().getSimpleName();

    /** 加载最新数据的起点 */
    public final int LATEST_INDEX = 0;

    /** 下一页的起始数 */
    private int mNextStart;

    /** 数据的list */
    private List<ObjectListEntity> mDataList;

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
        new OkHttpRequest.Builder().url(URL_HEAD + index).get(new ResultCallback<TestDataBean>() {
            @Override
            public void onError(Request request, Exception e) {
                callback.onError(request, e);
            }

            @Override
            public void onResponse(TestDataBean response) {
                LogUtils.d("获取到数据");
                mNextStart = response.getData().getNext_start();
                if (mDataList == null || index == LATEST_INDEX) {
                    mDataList = response.getData().getObject_list();
                } else {
                    mDataList.addAll(response.getData().getObject_list());

                }
                callback.onSuccess(mDataList);
            }
        });
    }

    public List<ObjectListEntity> getData() {
        return mDataList;
    }

    public void loadOldData(final ResponseCallback callback) {
        loadData(mNextStart, callback);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}

