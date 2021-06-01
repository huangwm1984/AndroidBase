package com.android.base.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库处理的辅助工具
 */
public class DatabaseUtil {

    /**
     * 获取表名
     *
     * @param clazz 表的实体bean
     * @param <T>
     * @return
     */
    public static <T> String extractTableName(Class<T> clazz) {
        DatabaseTable databaseTable = clazz.getAnnotation(DatabaseTable.class);
        String name;
        if (databaseTable != null && databaseTable.tableName() != null && databaseTable.tableName().length() > 0) {
            name = databaseTable.tableName();
        } else {
            name = JavaxPersistence.getEntityName(clazz);
            if (name == null) {
                name = clazz.getSimpleName().toLowerCase();
            }
        }
        return name;
    }

    /**
     * 获取数据库中对应表的列名集合
     *
     * @param columnStructList 表的列结构集合
     * @return
     */
    public static List<String> getColumnNames(List<ColumnStruct> columnStructList) {
        List<String> columnNames = new ArrayList<>();
        if (columnStructList == null) {
            return columnNames;
        }
        for (ColumnStruct columnStruct : columnStructList) {
            columnNames.add(columnStruct.getColumnName());
        }

        return columnNames;
    }

    /**
     * 生成新的数据表结构
     *
     * @param connectionSource
     * @param clazz            表的实体bean
     * @param <T>
     * @return 新版本数据库表的列结构集合
     */
    public static <T> List<ColumnStruct> getNewTableStruct(ConnectionSource connectionSource, Class<T> clazz) {
        List<ColumnStruct> columnStruct = new ArrayList<>();
        try {
            String struct = TableUtils.getCreateTableStatements(connectionSource, clazz).get(0);
            LogUtils.i("新的建表语句：" + struct);
            columnStruct = getColumnStruct(struct);
        } catch (SQLException e) {
            LogUtils.e(e);
        }
        return columnStruct;
    }

    /**
     * 查询数据库中对应表的表结构
     *
     * @param db
     * @param tableName 表名
     * @return 旧版本数据库表的列结构集合
     */
    public static List<ColumnStruct> getOldTableStruct(SQLiteDatabase db, String tableName) {
        List<ColumnStruct> columnStruct = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from sqlite_master where type = ? AND name = ?",
                    new String[]{"table", tableName});
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex("sql");
                if (-1 != columnIndex && cursor.getCount() > 0) {
                    String struct = cursor.getString(columnIndex);
                    LogUtils.i("旧的建表语句：" + struct);
                    columnStruct = getColumnStruct(struct);
                } else {
                    LogUtils.i("不存在旧表");
                }
            } else {
                LogUtils.i("数据库操作失败");
            }
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return columnStruct;
    }

    /**
     * 生成表结构集合
     *
     * @param tableStruct 规范建表语句
     * @return 表结构集合
     */
    public static List<ColumnStruct> getColumnStruct(String tableStruct) {
        List<ColumnStruct> columnStructList = new ArrayList<>();
//      "CREATE TABLE `msg_record` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `title` VARCHAR ,  UNIQUE (`number`))"
        // 解析过程根据标准的ormlite建表语句设计
        String subString = tableStruct.substring(tableStruct.indexOf("(") + 1, tableStruct.length() - 1);
        String[] sub = subString.split(", ");
        for (String str : sub) {
            if (str.contains("(") || str.contains(")")) {
                str = str.replace("(", "").replace(")", "");
            }
            str = str.trim();//去除字符串的首尾空格
            if (str.startsWith("`")) {
                String[] column = str.split("` ");
                columnStructList.add(new ColumnStruct(column[0].replace("`", ""), column[1]));
            } else {
                // 附加的额外字段限制,不是以`开始
                if (str.contains(",")) {
                    String[] column = str.split(" `");
                    String[] columns = column[1].replace("`", "").split(",");
                    for (String str1 : columns) {
                        modifyColumnStruct(columnStructList, str1, "UniqueCombo");
                    }
                } else {
                    String[] column = str.split(" `");
                    modifyColumnStruct(columnStructList, column[1].replace("`", ""), column[0]);
                }
            }
        }
        return columnStructList;
    }

    /**
     * 修改列结构
     * 部分列结构有多个限制字段，需要进行合并
     *
     * @param list       需要加入的列结构集合
     * @param columnName 需要合并的列名
     * @param limit      额外限制字段
     */
    private static void modifyColumnStruct(List<ColumnStruct> list, String columnName, String limit) {
        if (list == null || list.isEmpty()) {
            LogUtils.e("list is null.");
            return;
        }
        if (TextUtils.isEmpty(columnName) || TextUtils.isEmpty(limit)) {
            LogUtils.e("columnName is null or limit is null.");
            return;
        }

        int size = list.size();
        for (int i = 0; i < size; i++) {
            ColumnStruct struct = list.get(i);
            if (columnName.equals(struct.getColumnName())) {
                StringBuilder sb = new StringBuilder(struct.getColumnLimit());
                sb.append(" ");
                sb.append(limit);
                struct.setColumnLimit(sb.toString());
                return;
            }
        }

        list.add(new ColumnStruct(columnName, limit));
    }

    /**
     * 判断数据表已有字段的限制是否有改变
     *
     * @param oldStructList 旧的表列结构集合
     * @param newStructList 新的表列结构集合
     * @return true：改变；false：未改变
     */
    public static boolean hasChangeColumnLimit(List<ColumnStruct> oldStructList,
                                               List<ColumnStruct> newStructList) {
        if (oldStructList == null || newStructList == null) {
            return false;
        }

        for (ColumnStruct oldStruct : oldStructList) {
            if (existChangeColumn(newStructList, oldStruct)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断列结构集合中是否存在与给定列结构列名相同，且列的限制已改变的列
     *
     * @param newStructList 列结构集合
     * @param oldStruct     给定列
     * @return true：存在满足条件的列；false：不存在满足条件的列
     */
    private static boolean existChangeColumn(List<ColumnStruct> newStructList, ColumnStruct oldStruct) {
        if (oldStruct == null) {
            return false;
        }
        String oldName = oldStruct.getColumnName();
        if (TextUtils.isEmpty(oldName)) {
            // 列名异常，当做不满足条件处理
            return false;
        }
        for (ColumnStruct newStruct : newStructList) {
            if (isChangeColumnStruct(oldStruct, newStruct)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 比较列结构之间是否有变化
     *
     * @param oldStruct 比较列
     * @param newStruct 被比较列
     * @return true：改变；false：未改变
     */
    private static boolean isChangeColumnStruct(ColumnStruct oldStruct, ColumnStruct newStruct) {
        if (oldStruct == null || newStruct == null) {
            return false;
        }
        String oldName = oldStruct.getColumnName();
        String oldLimit = oldStruct.getColumnLimit();
        if (oldName == null || !oldName.equals(newStruct.getColumnName())) {
            // 不是同一字段
            return false;
        }
        // 比较同一字段的差异
        String newLimit = newStruct.getColumnLimit();
        if (oldLimit == null && newLimit == null) {
            // 都没有限制符
            return false;
        } else {
            // 某一个添加了限制符 或者限制符被改变
            return oldLimit == null || newLimit == null || !oldLimit.equals(newLimit);
        }
    }

    /**
     * 获得数据表中已删除字段
     *
     * @param oldColumns 旧的表列结构集合
     * @param newColumns 新的表列结构集合
     * @return 已删除字段集合
     */
    public static List<String> getDeleteColumns(List<String> oldColumns, List<String> newColumns) {
        return getColumnChange(oldColumns, newColumns);
    }

    /**
     * 获取已更改的列集合
     *
     * @param oldColumns 旧的表列结构集合
     * @param newColumns 新的表列结构集合
     * @return 返回oldColumns中存在，而newColumns中不存在的列集合
     */
    private static List<String> getColumnChange(List<String> oldColumns, List<String> newColumns) {
        List<String> columnList = new ArrayList<>();
        if (oldColumns == null || newColumns == null) {
            return columnList;
        }

        boolean exist;
        for (String oldColumn : oldColumns) {
            exist = false;
            for (String newColumn : newColumns) {
                if (newColumn != null && newColumn.equals(oldColumn)) {
                    // 存在对应的字段
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                // 字段不匹配
                columnList.add(oldColumn);
            }
        }
        return columnList;
    }

    /**
     * 集合中是否存在指定列
     */
    public static ColumnStruct existColumn(String columnName, List<ColumnStruct> list) {
        if (list == null || columnName == null) {
            return null;
        }
        for (ColumnStruct struct : list) {
            if (struct == null) {
                continue;
            }
            if (columnName.equals(struct.getColumnName())) {
                return struct;
            }
        }
        return null;
    }

}

