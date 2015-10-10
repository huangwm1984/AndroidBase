package com.android.base.db.dao.impl;

import android.content.Context;

import com.android.base.db.dao.BaseDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * BaseDao泛型实现类
 */
public class BaseDaoImpl<T,Integer> extends BaseDao<T,Integer> {
    private Dao<T, Integer> mDao;
    private Class<T> clazz;
    public BaseDaoImpl(Context context,Class<T> clazz) {
        super(context);
        this.clazz=clazz;
    }

    @Override
    public Dao<T, Integer> getDao() throws SQLException {
        if (null == mDao){
            mDao = mDatabaseHelper.getDao(clazz);
        }
        return mDao;
    }
}