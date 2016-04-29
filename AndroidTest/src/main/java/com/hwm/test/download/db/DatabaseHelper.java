package com.hwm.test.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.hwm.test.download.bizs.DLInfo;
import com.hwm.test.download.bizs.DLThreadInfo;
import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Helper类，提供单例Helper
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "android_test.db";
    //数据库名
    private static final int DB_VERSION = 1;
    //数据库版本
    private static DatabaseHelper mInstance;

    //Helper单例
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.createTable(connectionSource, DLInfo.class);
            TableUtils.createTable(connectionSource, DLThreadInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        try {
            TableUtils.dropTable(connectionSource, DLInfo.class, true);
            TableUtils.dropTable(connectionSource, DLThreadInfo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 双重加锁检查
     *
     * @param context 上下文
     * @return 单例
     */
    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseHelper(context);
                }
            }
        }
        return mInstance;
    }

    /*private static Object mHistoryObject = new Object();
    private static BaseDao<History, Integer> mHistoryDao;

    public static BaseDao<History, Integer> getHistoryDao(Context context) {
        if (mHistoryDao == null) {
            synchronized (mHistoryObject) {
                if (mHistoryDao == null) {
                    mHistoryDao = new HistoryDao(context, History.class);
                }
            }

        }
        return mHistoryDao;
    }

    private static Object mNewsItemObject = new Object();
    private static BaseDao<NewsItem, Integer> mNewsItemDao;

    public static BaseDao<NewsItem, Integer> getNewsItemDao(Context context) {
        if (mNewsItemDao == null) {
            synchronized (mNewsItemObject) {
                if (mNewsItemDao == null) {
                    mNewsItemDao = new NewsItemDao(context, NewsItem.class);
                }
            }
        }
        return mNewsItemDao;
    }*/

    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        // lookup the dao, possibly invoking the cached database config
        Dao<T, ?> dao = DaoManager.lookupDao(connectionSource, clazz);
        if (dao == null) {
            // try to use our new reflection magic
            DatabaseTableConfig<T> tableConfig = DatabaseTableConfigUtil.fromClass(connectionSource, clazz);
            if (tableConfig == null) {
                /**
                 * TODO: we have to do this to get to see if they are using the deprecated annotations like
                 * {@link DatabaseFieldSimple}.
                 */
                dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, clazz);
            } else {
                dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, tableConfig);
            }
        }

        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }

    public void close(){
        if(mInstance!=null){
            OpenHelperManager.releaseHelper();
            mInstance = null;
        }
    }
}