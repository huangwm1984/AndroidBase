package com.hwm.test.view.pulltorefresh.recycler.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.android.base.block.impl.IBlock;
import com.hwm.test.R;

/**
 * Created by Administrator on 2015/11/26 0026.
 */
public class SimpleFooterTest extends RelativeLayout implements IBlock {

    public SimpleFooterTest(Context context) {
        super(context);
        init(context);
    }

    public SimpleFooterTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleFooterTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SimpleFooterTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_waterfall_footer, this);
    }

    @Override
    public void attachActivity(Activity activity) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
