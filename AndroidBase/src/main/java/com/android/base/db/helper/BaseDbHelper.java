package com.android.base.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.base.common.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Helper类，提供单例Helper
 */
public abstract class BaseDbHelper extends OrmLiteSqliteOpenHelper {

    //Helper单例
    public BaseDbHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        dropTables();
        onCreate(database, connectionSource);
    }

    /**
     * 初始化所有的数据库表
     */
    private void createTables() {
        try {
            String[] tables = getDBTables();
            if (tables != null) {
                Log.i(".............begin creating table.............");
                for (int i = 0; i < tables.length; i++) {
                    Class<?> cls = Class.forName(tables[i]);
                    TableUtils.createTableIfNotExists(connectionSource, cls);
                    Log.i("create table " + i + " : " + tables[i] + " successfully!");
                }
                Log.i("..............end creating table..............");
            }
        } catch (ClassNotFoundException e) {
            Log.e("table class is not found." + e);
        } catch (SQLException e) {
            Log.e("can't create table .\n" + e);
        }
    }

    /**
     * Drop 掉所有的表格
     */
    private void dropTables() {
        try {
            String[] tables = getDBTables();
            if (tables != null) {
                Log.i("..............begin drop table................");
                for (int i = 0; i < tables.length; i++) {
                    Class<?> cls = Class.forName(tables[i]);
                    TableUtils.dropTable(connectionSource, cls, true);
                    Log.i("drop table " + i + " : " + tables[i] + " successfully!");
                }
                Log.i("................end drop table................");
            }
        } catch (ClassNotFoundException e) {
            Log.e("table class is not found." + e);
        } catch (SQLException e) {
            Log.e("can't create table .\n" + e);
        }
    }

    public abstract String[] getDBTables();

}