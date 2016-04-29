package com.hwm.test.test.http.model;


import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface RetrofitDataSource {

    interface LoadNetDataCallback {

        void onDataLoaded(Object o);

        void onDataNotAvailable(Object o);
    }

    void loadLastestNewsData(@NonNull LoadNetDataCallback callback);

    void loadBeforeNewsData(@NonNull LoadNetDataCallback callback);

    void loadNewsDetailsData(@NonNull LoadNetDataCallback callback);


}
