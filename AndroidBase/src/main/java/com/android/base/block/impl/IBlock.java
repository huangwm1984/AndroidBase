package com.android.base.block.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public interface IBlock {

    void attachActivity(Activity activity);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    boolean onBackPressed();
}
