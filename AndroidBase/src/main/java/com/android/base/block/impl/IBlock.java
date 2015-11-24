package com.android.base.block.impl;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public interface IBlock {

    void attachActivity(Activity activity);

    boolean onBackPressed();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
