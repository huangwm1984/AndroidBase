package com.hwm.test.http.model.http;

import com.hwm.test.http.model.entity.AllThemes;
import com.hwm.test.http.model.entity.News;
import com.hwm.test.http.model.entity.NewsDetail;
import com.hwm.test.http.model.entity.StartInfo;
import com.hwm.test.http.model.entity.ThemeDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ZhihuService {

    /**
     *
     * 启动界面图像获取
     * @param width
     * @param height
     * @return
     */
    @GET("api/4/start-image/{width}*{height}")
    Observable<StartInfo> getStartInfo(@Path("width") int width, @Path("height") int height);

    /*
     * 主题日报列表查看
     * @return
     */
    @GET("api/4/themes")
    Observable<AllThemes> getAllThemes();

    /*
     * 最新消息
     * @return
     */
    @GET("api/4/news/latest")
    Observable<News> getLastestNews();

    /*
     * 消息内容获取与离线下载
     * @param id
     * @return
     */
    @GET("api/4/news/{id}")
    Observable<NewsDetail> getNewsDetail(@Path("id") int id);

    /*
     * 主题日报内容查看
     * @param id
     * @return
     */
    @GET("api/4/theme/{id}")
    Observable<ThemeDetail> getThemeDetail(@Path("id") int id);

    /*
     * 新闻额外信息
     * @param id
     * @return
     */
    /*@GET("api/4/story-extra/{id}")
    Call<GetStoryExtraResponse> getStoryExtraResponse(@Path("id") int id);*/

    /*
     * 新闻对应短评论查看
     * @param id
     * @return
     */
    /*@GET("api/4/story/{id}/short-comments")
    Call<GetShortCommentsResponse> getShortComments(@Path("id") int id);*/

    /*
     * 新闻对应长评论查看
     * @param id
     * @return
     */
    /*@GET("api/4/story/{id}/long-comments")
    Call<GetLongCommentsResponse> getLongComments(@Path("id") int id);*/

}
