package com.android.test.view.recycler;

import android.content.Intent;
import android.os.Bundle;

import com.android.base.common.assist.Toastor;
import com.android.test.R;
import com.android.test.TestBaseActivity;
import com.android.test.view.recycler.extra.HeaderOrFooterActivity;
import com.android.test.view.recycler.multi.MultiItemActivity;
import com.android.test.view.recycler.normal.NormalActivity;
import com.android.test.view.recycler.scrollview.RecyclerViewInsideScrollViewActivity;

/**
 * Created by Administrator on 2015/10/31 0031.
 */
public class RecyclerViewTestActivity extends TestBaseActivity {


    @Override
    public String getMainTitle() {
        return "RecyclerView Test";
    }

    @Override
    public String[] getButtonTexts() {
        return getResources().getStringArray(R.array.recyclerview_list);
    }

    @Override
    public void getButtonClick(final int id) {
        onClickButton(id);
    }

    protected void onClickButton(int id) {
        Intent intent = new Intent();
        switch (id) {
            case 0:
                gotoActivity(NormalActivity.class, false);
                break;
            case 1:
                gotoActivity(MultiItemActivity.class, false);
                break;
            case 2:
                gotoActivity(HeaderOrFooterActivity.class, false);
                break;
            case 3:
                gotoActivity(RecyclerViewInsideScrollViewActivity.class, false);
                break;
            case 4:
                Toastor.showSingletonToast(mApplicationContext, "还在开发中...");
                break;
            case 5:
                Toastor.showSingletonToast(mApplicationContext, "还在开发中...");
                break;
            case 6:
                Toastor.showSingletonToast(mApplicationContext, "还在开发中...");
                break;
            default:
                Toastor.showSingletonToast(mApplicationContext, "还在开发中...");
                break;
        }

    }
}