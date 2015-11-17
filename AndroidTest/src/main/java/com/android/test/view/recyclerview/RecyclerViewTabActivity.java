package com.android.test.view.recyclerview;

import android.content.Intent;

import com.android.test.R;
import com.android.test.TestBaseActivity;
import com.android.test.view.recyclerview.extra.HeaderOrFooterActivity;
import com.android.test.view.recyclerview.extra.block.HeaderBlock;
import com.android.test.view.recyclerview.multi.MultiItemActivity;
import com.android.test.view.recyclerview.normal.NormalActivity;

/**
 * Created by Administrator on 2015/10/31 0031.
 */
public class RecyclerViewTabActivity extends TestBaseActivity {


    @Override
    public String getMainTitle() {
        return "RecyclerView Test";
    }

    @Override
    public String[] getButtonTexts() {
        return getResources().getStringArray(R.array.recyclerview_list);
    }

    @Override
    public Runnable getButtonClickRunnable(final int id) {
        return new Runnable() {

            @Override
            public void run() {

                onClickButton(id);
            }
        };
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

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            default:
                break;
        }

    }
}