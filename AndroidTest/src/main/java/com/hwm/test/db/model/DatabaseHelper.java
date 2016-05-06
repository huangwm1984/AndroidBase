package com.hwm.test.db.model;

import android.content.Context;

import com.android.base.db.BaseOrmLiteDatabaseHelper;
import com.hwm.test.db.model.entity.City;
import com.hwm.test.db.model.entity.Dept;
import com.hwm.test.db.model.entity.User;

/**
 * ormlite操作数据库Helper
 */
public class DatabaseHelper extends BaseOrmLiteDatabaseHelper {

    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "TestDB.db";

    /**
     * 数据库版本号
     */
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        addTable();
    }

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseHelper(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * 注册数据表
     */
    private void addTable() {
        registerTable(User.class);
        registerTable(Dept.class);
        registerTable(City.class);
    }
}
