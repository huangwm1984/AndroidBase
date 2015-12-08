package com.android.test.download.test.data;

import android.support.v4.util.ArrayMap;

import com.android.base.block.SampleBlock;
import com.android.base.http.OkHttpClientManager;
import com.android.base.http.callback.ResultCallback;
import com.android.base.http.request.OkHttpRequest;
import com.android.test.download.test.entity.GameInfo;
import com.android.test.view.recycler.extra.impl.ResponseCallback;
import com.squareup.okhttp.Request;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class GameDataManager extends SampleBlock {

    //public static String BASE_API_URL = "http://api150.aiyouyou.paojiao.cn";
    /** 新游接口 */
    //public static final String NEW_GAME = BASE_API_URL + "/v5/game/newlist.do";

    public static final String URL_HOT_APP = "http://setup.3533.com/appdownload/hotapp/%d.json";

    public int mPage = 1;

    public int mTotalCount = 0;

    public boolean isFirst = true;

    //默认一页加载十二条
    //public int mPageNum = 12;

    /** 数据的list */
    //private List<GameInfo.DataEntity> mDataList;

    @Override
    protected void onCreated() {

    }

    public void loadNewData(final ResponseCallback callback) {
        loadData(mPage, callback);
    }

    public void loadOldData(final ResponseCallback callback) {
        loadData(mPage, callback);
    }

    public void loadData(final int page, final ResponseCallback callback) {
        ArrayMap<String, String> map = new ArrayMap<>();
        //map.put("page", String.valueOf(page));
        //map.put("limit", String.valueOf(mPageNum));

        String url = String.format(URL_HOT_APP, page);

        // 执行网络请求
        new OkHttpRequest.Builder().url(url).params(map).get(new ResultCallback<GameInfo>() {
            @Override
            public void onError(Request request, Exception e) {
                callback.onError(request, e);
            }

            @Override
            public void onResponse(GameInfo response) {
                /*if (mDataList == null || mPage == 1) {
                    mDataList = response.getData();
                } else {
                    mDataList.addAll(response.getData());
                }
                mPage = page + 1;
                callback.onSuccess(mDataList);*/
                if(isFirst){
                    mTotalCount = response.getTotal();
                    isFirst = false;
                }
                mPage = page + 1;
                callback.onSuccess(response.getData());
            }
        });
    }

    /*public List<GameInfo.DataEntity> getData() {
        return mDataList;
    }*/

    public int getTotalCount(){
        return mTotalCount;
    }

    @Override
    public void onDestroy() {
        OkHttpClientManager.getInstance().cancelTag(mActivity);//取消以Activity.this作为tag的请求
        super.onDestroy();
    }
}
