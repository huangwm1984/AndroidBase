package com.android.test.download.db.dao;

import android.content.Context;

import com.android.test.download.bizs.DLInfo;


/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class DLInfoDao extends BaseDaoImpl<DLInfo, Integer> {
    public DLInfoDao(Context context, Class<DLInfo> clazz) {
        super(context, clazz);
    }
}
