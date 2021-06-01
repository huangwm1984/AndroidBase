package com.hwm.test.db.model.entity;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 部门（这里假设一个用户只对应一个部门，而一个部门对应着多个用户，即一对多的关系）
 * Created by Administrator on 2016/5/5.
 */
@DatabaseTable(tableName="tb_dept")
public class Dept {
    //部门编号
    @DatabaseField(generatedId=true)
    private int deptId;
    //部门名称
    @DatabaseField
    private String deptName;

    /**
     * 这里需要注意的是：属性类型只能是ForeignCollection<T>或者Collection<T>
     * 如果需要懒加载（延迟加载）可以在@ForeignCollectionField加上参数eager=false
     * 这个属性也就说明一个部门对应着多个用户
     */
    //用户信息集合
    @ForeignCollectionField
    private ForeignCollection<User> users;

    public Dept(){

    }

    public Dept(int deptId,String deptName){
        this.deptId = deptId;
        this.deptName = deptName;
    }


}

