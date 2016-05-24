package com.hwm.test.http.model.entity;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/5/23.
 */
public class AllThemes {

    public int limit;

    public Object[] subscribed;

    public ThemeItem[] others;

    public String toString() {
        return "GetAllThemesResponse{" +
                "limit=" + limit +
                ", subscribed=" + Arrays.toString(subscribed) +
                ", others=" + Arrays.toString(others) +
                "} " + super.toString();
    }



}
