/*
package com.hwm.test.db.model;


import android.content.Context;

import com.android.base.db.BaseRxDao;
import com.android.base.db.impl.DbCallBack;
import com.hwm.test.db.model.dao.CityDao;
import com.hwm.test.db.model.dao.DeptDao;
import com.hwm.test.db.model.dao.UserDao;
import com.hwm.test.db.model.entity.City;
import com.hwm.test.db.model.entity.Dept;
import com.hwm.test.db.model.entity.User;

*/
/**
 * Created by Administrator on 2016/5/5.
 *//*

public class OrmLiteTask implements IOrmLiteTask {

    private static OrmLiteTask mInstance;
    private Context mContext;
    private UserDao mUserDao;
    private DeptDao mDeptDao;
    private CityDao mCityDao;

    public static OrmLiteTask getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OrmLiteTask.class) {
                if (mInstance == null) {
                    mInstance = new OrmLiteTask(context);
                }
            }
        }
        return mInstance;
    }

    public OrmLiteTask(Context context){
        this.mContext = context;
        mUserDao = new UserDao(context, User.class);
        mDeptDao = new DeptDao(context, Dept.class);
        mCityDao = new CityDao(context, City.class);
    }


    @Override
    public void testSave() {

        City city1 = new City();
        city1.setCityName("厦门");
        city1.setProvinceName("福建省");
        city1.setCityNo("110");
        city1.setIndex(1100);

        mCityDao.subscribe();
        */
/*mCityDao.insertAsync(city1, new DbCallBack() {
            @Override
            public void onComplete(Object data) {

            }
        });*//*




    }

    @Override
    public void testInsert() {

    }

    @Override
    public void testUpdate() {

    }

    @Override
    public void testUpdateColumn() {

    }

    @Override
    public void testQueryAll() {

    }

    @Override
    public void testQueryByWhere() {

    }

    @Override
    public void testQueryByID() {

    }

    @Override
    public void testQueryAnyUwant() {

    }

    @Override
    public void testMapping() {

    }

    @Override
    public void testDelete() {

    }

    @Override
    public void testDeleteByIndex() {

    }

    @Override
    public void testDeleteByWhereBuilder() {

    }

    @Override
    public void testDeleteAll() {

    }

    @Override
    public void testLargeScaleUseLite() {

    }

    @Override
    public void testLargeScaleUseSystem() {

    }
}

*/
