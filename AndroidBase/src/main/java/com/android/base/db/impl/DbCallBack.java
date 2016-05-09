package com.android.base.db.impl;

/**
 * 数据库操作回调
 */
public interface DbCallBack {

    void onComplete(Object data);

    void onError(Object errorMsg);
}
