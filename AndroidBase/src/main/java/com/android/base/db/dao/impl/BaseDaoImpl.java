package com.android.base.db.dao.impl;

import android.content.Context;

import com.android.base.db.dao.BaseDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * BaseDao泛型实现类
 */
public abstract class BaseDaoImpl<T,Integer> extends BaseDao<T,Integer> {
    public Dao<T, Integer> mDao;
    public Class<T> clazz;
    public BaseDaoImpl(Context context,Class<T> clazz) {
        super(context);
        this.clazz=clazz;
    }
}