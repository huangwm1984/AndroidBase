package com.hwm.test.http.model;


import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface IRetrofitTask {

    interface LoadNetDataCallback {

        void onDataLoaded(Object o);

        void onDataNotAvailable(Object o);
    }

    void loadLastestNewsData(@NonNull LoadNetDataCallback callback);

    void loadGeyeData(@NonNull LoadNetDataCallback callback);


}
