package com.android.base.db;

import android.database.sqlite.SQLiteDatabase;

import com.apkfuns.logutils.LogUtils;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表升级方案的基类
 * 需要自定义升级方案的数据表可继承改类，重写对应方法
 */
public class DatabaseHandler<T> {

    private Class<T> mClazz;
    private String mTableName;

    public DatabaseHandler(Class<T> clazz) {
        this.mClazz = clazz;
        mTableName = DatabaseUtil.extractTableName(clazz);
    }

    public String getTableName() {
        return mTableName;
    }

    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs) throws SQLException {
        List<ColumnStruct> oldStruct = DatabaseUtil.getOldTableStruct(db, mTableName);
        List<ColumnStruct> newStruct = DatabaseUtil.getNewTableStruct(cs, mClazz);

        if (oldStruct.isEmpty() && newStruct.isEmpty()) {
            LogUtils.d("数据表结构都为空！不是合法的数据库bean！！！");
        } else if (oldStruct.isEmpty()) {
            LogUtils.d("新增表");
            create(cs);
        } else if (newStruct.isEmpty()) {
            // 永远不会执行
            LogUtils.d("删除表");
            drop(cs);
        } else {
            dealColumnChange(db, cs, oldStruct, newStruct);
        }
    }

    /**
     * 处理表有变化的情况
     */
    private void dealColumnChange(SQLiteDatabase db, ConnectionSource cs, List<ColumnStruct> oldStruct,
                                  List<ColumnStruct> newStruct) throws SQLException {
        if (DatabaseUtil.hasChangeColumnLimit(oldStruct, newStruct)) {
            LogUtils.d("数据表已有字段的描述改变");
            // 字段的限制条件改变时，要保证数据的无损一直没有想到完美的方案，欢迎完善
            // 已有字段描述改变了，删除旧表，重建新表
            reset(cs);
        } else {
            // 数据表已有字段的描述没有改变
            // 判断列是否有增减
            List<String> oldColumns = DatabaseUtil.getColumnNames(oldStruct);
            List<String> newColumns = DatabaseUtil.getColumnNames(newStruct);
            if (!oldColumns.equals(newColumns)) {
                LogUtils.d("表发生了变化");
                // 判断列的变化情况：增加、减少、增减
                List<String> deleteList = DatabaseUtil.getDeleteColumns(oldColumns, newColumns);
                // 自增的列不纳入数据拷贝的范围
//                deleteList = DatabaseUtil.addGeneratedId(deleteList, oldStruct);
                upgradeByCopy(db, cs, getCopyColumns(oldColumns, deleteList));
            } else {
                LogUtils.i("表没有发生变化,不需要更新数据表");
            }
        }
    }

    /**
     * 新增列的方式更新
     */
    private void upgradeByAdd(SQLiteDatabase db, List<String> addList, List<ColumnStruct> newStruct) {
        List<ColumnStruct> addColumn = new ArrayList<>();
        for (String columnName : addList) {
            ColumnStruct columnStruct = DatabaseUtil.existColumn(columnName, newStruct);
            if (columnStruct != null) {
                addColumn.add(new ColumnStruct(columnName, columnStruct.getColumnLimit()));
            }
        }

        for (ColumnStruct addStruct : addColumn) {
            db.execSQL(appendAddColumn(addStruct));
        }
    }

    private String appendAddColumn(ColumnStruct addStruct) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ");
        sb.append(mTableName);
        sb.append(" ADD COLUMN ");
        sb.append('`').append(addStruct.getColumnName()).append('`').append(' ');
        sb.append(addStruct.getColumnLimit()).append(' ');
        return sb.toString();
    }

    /**
     * 拷贝数据的方式更新
     *
     * @param columns 原始列减去删除的列
     */
    private void upgradeByCopy(SQLiteDatabase db, ConnectionSource cs, String columns) throws SQLException {
        db.beginTransaction();
        try {
            //Rename table
            String tempTableName = mTableName + "_temp";
            String sql = "ALTER TABLE " + mTableName + " RENAME TO " + tempTableName;
            db.execSQL(sql);

            //Create table
            try {
                sql = TableUtils.getCreateTableStatements(cs, mClazz).get(0);
                db.execSQL(sql);
            } catch (Exception e) {
                LogUtils.e("", e);
                TableUtils.createTable(cs, mClazz);
            }
            sql = "INSERT INTO " + mTableName + " (" + columns + ") " +
                    " SELECT " + columns + " FROM " + tempTableName;
            db.execSQL(sql);

            //Drop temp table
            sql = "DROP TABLE IF EXISTS " + tempTableName;
            db.execSQL(sql);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            throw new SQLException("update fail");
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 获得没有变化的列
     */
    private String getCopyColumns(List<String> oldColumns, List<String> deleteList) {
        StringBuilder columns = new StringBuilder("");
        if (oldColumns == null || deleteList == null) {
            return columns.toString();
        }
        int index = 0;
        // 存在删除集合里的列不添加
        for (String columnName : oldColumns) {
            if (!CollectionUtil.existValue(columnName, deleteList)) {
                if (index == 0) {
                    columns.append("`").append(columnName).append("`");
                } else {
                    columns.append(", ").append("`").append(columnName).append("`");
                }
                index++;
            }
        }
        return columns.toString();
    }

    /**
     * 数据库升级
     */
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) throws SQLException {
        try {
            onUpgrade(db, cs);
        } catch (Exception e) {
            LogUtils.e("数据表升级异常，重建表", e);
            reset(cs);
        }

    }

    /**
     * 数据库降级
     *
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(ConnectionSource connectionSource, int oldVersion, int newVersion) throws SQLException {
        reset(connectionSource);
    }

    /**
     * 删除重新创建数据表
     *
     * @param connectionSource
     * @throws SQLException
     */
    private void reset(ConnectionSource connectionSource) throws SQLException {
        drop(connectionSource);
        create(connectionSource);
    }

    /**
     * 清除表数据
     *
     * @throws SQLException
     */
    public void clear(ConnectionSource connectionSource) throws SQLException {
        TableUtils.clearTable(connectionSource, mClazz);
    }

    /**
     * 创建表
     *
     * @throws SQLException
     */
    public void create(ConnectionSource connectionSource) throws SQLException {
        TableUtils.createTable(connectionSource, mClazz);
    }

    /**
     * 删除表
     *
     * @param connectionSource
     * @throws SQLException
     */
    public void drop(ConnectionSource connectionSource) throws SQLException {
        TableUtils.dropTable(connectionSource, mClazz, true);
    }

}
