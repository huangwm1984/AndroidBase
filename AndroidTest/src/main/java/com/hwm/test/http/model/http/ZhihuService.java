package com.hwm.test.http.model.http;

import com.hwm.test.http.model.entity.NewsEntity;
import com.hwm.test.http.model.entity.StoryDetailsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ZhihuService {

    @GET("api/4/news/latest")
    Observable<NewsEntity> getLastestNews();

    @GET("api/4/news/before/{id}")
    Observable<NewsEntity> getBeforeNews(@Path("id") String id);

    @GET("api/4/news/{id}")
    Observable<StoryDetailsEntity> getNewsDetails(@Path("id") int id);

}
