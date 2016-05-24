package com.hwm.test.http.model.entity;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/5/23.
 */
public class NewsDetail {

    public int id;

    public int type;

    public String title;

    public String image;

    public String imageSource;

    public String body;

    public String shareUrl;

    public String[] css;

    public String[] js;

    public String gaPrefix;

    public String toString() {
        return "GetNewsResponse{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", imageSource='" + imageSource + '\'' +
                ", body='" + body + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", css=" + Arrays.toString(css) +
                ", js=" + Arrays.toString(js) +
                ", gaPrefix='" + gaPrefix + '\'' +
                "} " + super.toString();
    }
}
