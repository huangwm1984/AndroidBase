package com.hwm.test.http.model.http;

import com.hwm.test.http.model.entity.GeyeEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by huangwm on 2016/4/30.
 */
public interface GeyeService {

    @FormUrlEncoded
    @POST("geye/getRtsp")
    Observable<GeyeEntity> getGeyeData(@Field("geyeId") String geyeId, @Field("isNeedff") String isNeedff);
}
