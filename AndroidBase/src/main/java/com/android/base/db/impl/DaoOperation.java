package com.android.base.db.impl;


public interface DaoOperation {

    int INSERT = 1;
    int DELETE = 2;
    int UPDATE = 3;
    int SELECT = 4;
    int INSERT_BATCH = 5;
    int DELETE_BATCH = 6;
    int UPDATE_BATCH = 7;
}
