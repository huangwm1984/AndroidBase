package com.hwm.test.http.model.entity;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ThemeItem {

    public int id;

    public String name;

    public String thumbnail;

    public String description;

    public int color;

    public String toString() {
        return "ThemeItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", color=" + color +
                '}';
    }


}
