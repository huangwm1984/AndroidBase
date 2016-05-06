package com.hwm.test.http.model.http;

import com.hwm.test.http.model.entity.News;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ZhihuService {

    @GET("api/4/news/latest")
    Observable<News> getLastestNews();

}
