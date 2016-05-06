package com.hwm.test.db.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * 用户类
 * Created by Administrator on 2016/5/5.
 */
@DatabaseTable(tableName = "tb_user")
public class User {
    //用户编号
    @DatabaseField(generatedId=true)
    private int userId;
    //用户名
    @DatabaseField
    private String userName;
    //密码
    @DatabaseField
    private int age;
    //入职时间
    @DatabaseField(format="DATE_STRING")
    private Date date;
    //用户所属部门
    /**
     * foreign = true:说明这是一个外部引用关系
     * foreignAutoRefresh = true：当对象被查询时，外部属性自动刷新（暂时我也没看懂其作用）
     *
     */
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Dept dept;

    public User() {
        //提供无参构造函数，这样查询的时候可以返回查询出来的对象
    }

    public User( int userId,String userName, int age) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
    }
}
