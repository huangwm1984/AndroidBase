package com.android.base.db;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.apkfuns.logutils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库查询后的缓存数据
 */
public class DbCache {

    /**
     * 最大缓存的查询记录条数
     */
    private static final int CACHE_SIZE = 100;

    /**
     * 所有缓存的总集，以表名为key，value为对应数据表下，所有查询条件的数据总集，每条查询对应的数据以json格式保存
     */
    private LruCache<String, Map<String, String>> mLruCache;

    private static DbCache sInstance;

    private DbCache() {
    }

    public static DbCache getInstance() {
        if (sInstance == null) {
            synchronized (DbCache.class) {
                if (sInstance == null) {
                    sInstance = new DbCache();
                }
            }
        }
        return sInstance;
    }

    private synchronized void initCache() {
        LogUtils.i("--------init cache----");
        mLruCache = new LruCache<String, Map<String, String>>(CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Map<String, String> value) {
                // 此处计算内存占用比较麻烦，所以使用了条数作为缓存限制
                // 如果大家有好的建议欢迎提出
                return value.size();
            }
        };
    }

    public <T> void addCache(String tableName, String query, T value) {
        if (mLruCache == null) {
            initCache();
        }
        if (inValid(tableName) || inValid(query) || value == null) {
            LogUtils.i("--------value is null----");
            return;
        }
        Map<String, String> tableCache = mLruCache.get(tableName);
        if (tableCache == null) {
            LogUtils.i("--------add cache");
            tableCache = new HashMap<>();
            tableCache.put(query, JSON.toJSONString(value));
            mLruCache.put(tableName, tableCache);
        } else {
            LogUtils.i("--------update cache");
            tableCache.put(query, JSON.toJSONString(value));
        }
    }

    public String getCache(String tableName, String query) {
        if (inValid(tableName) || inValid(query)) {
            LogUtils.i("--------mLruCache is empty");
            return null;
        }

        Map<String, String> tableCache = mLruCache.get(tableName);
        if (tableCache == null || tableCache.isEmpty()) {
            LogUtils.i("----------------tableCache is empty---");
            return null;
        }
        return tableCache.get(query);
    }

    public void clearByQuery(String tableName, String query) {
        if (inValid(tableName) || inValid(query)) {
            return;
        }
        Map<String, String> tableCache = mLruCache.get(tableName);
        if (tableCache != null) {
            tableCache.remove(query);
        }
    }

    public void clearByTable(String tableName) {
        if (inValid(tableName)) {
            return;
        }
        LogUtils.i("--------clear table cache---");
        mLruCache.remove(tableName);
    }

    public void clearAll() {
        if (mLruCache != null) {
            if (mLruCache.size() > 0) {
                mLruCache.evictAll();
            }
        }
        System.gc();
    }

    private boolean inValid(String key) {
        if (mLruCache == null) {
            return true;
        }
        return TextUtils.isEmpty(key);
    }

}
