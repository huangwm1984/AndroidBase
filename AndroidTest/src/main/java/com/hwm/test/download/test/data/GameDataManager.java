package com.hwm.test.download.test.data;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.android.base.block.SampleBlock;
import com.hwm.test.download.test.entity.GameInfo;
import com.hwm.test.view.recycler.extra.impl.ResponseCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;
import okhttp3.Request;

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

    String mUrl;

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

        mUrl = String.format(URL_HOT_APP, page);

        // 执行网络请求
        OkHttpUtils
                .get()
                .url(mUrl)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response)
                    {
                        GameInfo mGameInfo = JSON.parseObject(response,GameInfo.class);

                        if(isFirst){
                            mTotalCount = mGameInfo.getTotal();
                            isFirst = false;
                        }
                        mPage = page + 1;
                        callback.onSuccess(mGameInfo.getData());

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
        RequestCall call = OkHttpUtils.get().url(mUrl).build();
        call.cancel();
        super.onDestroy();
    }
}
