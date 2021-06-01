package com.android.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.apkfuns.logutils.LogUtils;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ormlite操作数据库Helper
 */
public class BaseOrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {

    List<DatabaseHandler> tableHandlers;

    /**
     * dao缓存
     */
    Map<String, Dao> daoMap;

    public BaseOrmLiteDatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
        daoMap = new HashMap<>();
    }

    /**
     * 注册数据表
     *
     * @param clazz 表的列结构bean
     * @param <T>
     */
    public <T> void registerTable(Class<T> clazz) {
        if (tableHandlers == null) {
            tableHandlers = new ArrayList<>();
        }
        DatabaseHandler handler = new DatabaseHandler<>(clazz);
        if (isValidTable(handler)) {
            tableHandlers.add(handler);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            for (DatabaseHandler handler : tableHandlers) {
                handler.create(connectionSource);
            }
        } catch (SQLException e) {
            LogUtils.e("database create fail", e);
        }
    }

    /**
     * 数据库升级，注意控制好数据库版本号，不然此方法将不会被调用到
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {
        LogUtils.i("数据库升级了" + " oldVersion = " + oldVersion + " newVersion = " + newVersion);
        try {
            for (DatabaseHandler handler : tableHandlers) {
                handler.onUpgrade(db, cs, oldVersion, newVersion);
            }
        } catch (SQLException e) {
            LogUtils.e("database upgrade fail", e);
        }
    }

    /**
     * 数据库降级
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ConnectionSource cs = getConnectionSource();
        Object conn = cs.getSpecialConnection();
        boolean clearSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true, this.cancelQueriesEnabled);
            try {
                cs.saveSpecialConnection((DatabaseConnection) conn);
                clearSpecial = true;
            } catch (SQLException var11) {
                throw new IllegalStateException("Could not save special connection", var11);
            }
        }

        try {
            this.onDowngrade(cs, oldVersion, newVersion);
        } finally {
            if (clearSpecial) {
                cs.clearSpecialConnection((DatabaseConnection) conn);
            }
        }
    }

    public void onDowngrade(ConnectionSource cs, int oldVersion, int newVersion) {
        LogUtils.i("数据库降级了" + " oldVersion = " + oldVersion + " newVersion = " + newVersion);
        try {
            for (DatabaseHandler handler : tableHandlers) {
                handler.onDowngrade(cs, oldVersion, newVersion);
            }
        } catch (SQLException e) {
            LogUtils.e("database downgrade fail", e);
        }
    }

    /**
     * 清空所有表的数据
     */
    public void clearAllTable() {
        try {
            for (DatabaseHandler handler : tableHandlers) {
                handler.clear(connectionSource);
            }
        } catch (SQLException e) {
            LogUtils.e("clear all table fail", e);
        }
    }

    public synchronized Dao getDao(Class cls) {
        Dao dao;
        String clsName = cls.getSimpleName();
        if (daoMap.containsKey(clsName)) {
            dao = daoMap.get(clsName);
        } else {
            try {
                dao = super.getDao(cls);
            } catch (SQLException e) {
                LogUtils.e("database operate fail", e);
                return null;
            }
            daoMap.put(clsName, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        synchronized (this) {
            Iterator<Map.Entry<String, Dao>> it = daoMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Dao> entry = it.next();
                Dao dao = entry.getValue();
                dao = null;
                it.remove();
            }
        }
    }

    /**
     * 判断新注册的数据表是否有效
     *
     * @param handler 数据表对应的DatabaseHandler
     * @return
     */
    public boolean isValidTable(DatabaseHandler handler) {
        if (tableHandlers == null || handler == null) {
            return false;
        }
        String tableName = handler.getTableName();
        for (DatabaseHandler element : tableHandlers) {
            if (tableName.equals(element.getTableName())) {
                return false;
            }
        }
        return true;
    }
}
