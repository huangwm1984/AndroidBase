package com.android.test.download.dao;

import android.content.Context;

import com.android.test.download.bean.DownLoadBean;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class DownLoadDao extends BaseDaoImpl<DownLoadBean, Integer>{
    public DownLoadDao(Context context, Class<DownLoadBean> clazz) {
        super(context, clazz);
    }
}
