package com.android.test.download.db.dao;

import android.content.Context;

import com.android.test.download.bizs.DLThreadInfo;

/**
 * Created by Administrator on 2015/11/30 0030.
 */
public class DLThreadInfoDao extends BaseDaoImpl<DLThreadInfo, Integer> {
    public DLThreadInfoDao(Context context, Class<DLThreadInfo> clazz) {
        super(context, clazz);
    }

}
