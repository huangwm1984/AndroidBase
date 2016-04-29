package com.android.base.basic;

import android.os.Bundle;

/**
 * Created by Administrator on 2016/4/28.
 */
public interface IBaseActivity {

    int setContentViewId();

    void onActivityCreated(Bundle savedInstanceState);

}
