package com.hwm.test.db.model.dao;

import android.content.Context;

import com.android.base.db.BaseRxDao;
import com.hwm.test.db.model.DatabaseHelper;
import com.hwm.test.db.model.entity.Dept;
import com.j256.ormlite.dao.Dao;

/**
 * Created by Administrator on 2016/5/5.
 */
public class DeptDao extends BaseRxDao<Dept, Integer> {

    public DeptDao(Context context, Class<Dept> cls) {
        super(context, cls);
    }

    @Override
    public Dao<Dept, Integer> getOrmLiteDao(Context context) {
        return DatabaseHelper.getInstance(context).getDao(getClass());
    }
}
