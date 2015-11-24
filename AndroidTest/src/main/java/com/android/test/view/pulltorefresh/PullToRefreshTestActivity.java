package com.android.test.view.pulltorefresh;

import android.content.Intent;

import com.android.base.common.assist.Toastor;
import com.android.test.R;
import com.android.test.TestBaseActivity;
import com.android.test.view.pulltorefresh.recycler.PullToRefreshRecyclerActivity;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class PullToRefreshTestActivity extends TestBaseActivity {


    @Override
    public String getMainTitle() {
        return "PullToRefresh Test";
    }

    @Override
    public String[] getButtonTexts() {
        return getResources().getStringArray(R.array.pulltorefresh_list);
    }

    @Override
    public void getButtonClick(final int id) {
        onClickButton(id);
    }

    protected void onClickButton(int id) {
        Intent intent = new Intent();
        switch (id) {
            case 0:
                gotoActivity(PullToRefreshRecyclerActivity.class, false);
                break;
            case 1:

                break;
            case 2:

                break;
            default:
                Toastor.showSingletonToast(PullToRefreshTestActivity.this, "还在开发中...");
                break;
        }

    }

}
