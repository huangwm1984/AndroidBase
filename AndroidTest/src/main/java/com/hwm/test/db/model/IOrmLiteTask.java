package com.hwm.test.db.model;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface IOrmLiteTask {

    void testSave();

    void testInsert();

    void testUpdate();

    void testUpdateColumn();

    void testQueryAll();

    void testQueryByWhere();

    void testQueryByID();

    void testQueryAnyUwant();

    void testMapping();

    void testDelete();

    void testDeleteByIndex();

    void testDeleteByWhereBuilder();

    void testDeleteAll();

    void testLargeScaleUseLite();

    void testLargeScaleUseSystem();


}
