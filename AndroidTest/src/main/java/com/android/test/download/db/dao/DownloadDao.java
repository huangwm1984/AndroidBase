package com.android.test.download.db.dao;

import android.content.Context;

import com.android.test.download.dao.BaseDaoImpl;
import com.android.test.download.entities.DownLoadEntity;

/**
 * Created by Administrator on 2015/10/17 0017.
 */
public class DownloadDao extends BaseDaoImpl<DownLoadEntity, Integer> {

    public DownloadDao(Context context, Class<DownLoadEntity> clazz) {
        super(context, clazz);
    }
}
