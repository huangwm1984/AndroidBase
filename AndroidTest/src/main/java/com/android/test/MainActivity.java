package com.android.test;

import android.content.Intent;

import com.android.base.common.assist.Toastor;
import com.android.test.autolayout.AutoLayoutActivity;
import com.android.test.banner.BannerTestActivity;
import com.android.test.download.DownLoadActivity;
import com.android.test.net.TestHttpActivity;
import com.android.test.leakcanary.LeakcanaryActivity;
import com.android.test.permission.PermissionActivity;
import com.android.test.view.pulltorefresh.PullToRefreshTestActivity;
import com.android.test.view.tabhost.FragmentTabHostActivity;
import com.android.test.view.recycler.RecyclerViewTestActivity;

public class MainActivity extends TestBaseActivity {


    @Override
    public String getMainTitle() {
        return "huangwm Test";
    }

    @Override
    public String[] getButtonTexts() {
        return getResources().getStringArray(R.array.test_list);
    }

    @Override
    public void getButtonClick(final int id) {
        /*return new Runnable() {

            @Override
            public void run() {

                onClickButton(id);
            }
        };*/
        onClickButton(id);
    }

    protected void onClickButton(int id) {
        Intent intent = new Intent();
        switch (id) {
            case 0:
                gotoActivity(TestHttpActivity.class, false);
                break;
            case 1:
                gotoActivity(FragmentTabHostActivity.class, false);
                break;
            case 2:
                gotoActivity(DownLoadActivity.class, false);
                break;
            case 3:
                gotoActivity(RecyclerViewTestActivity.class, false);
                break;
            case 4:
                gotoActivity(PermissionActivity.class, false);
                break;
            case 5:
                gotoActivity(LeakcanaryActivity.class, false);
                break;
            case 6:
                gotoActivity(BannerTestActivity.class, false);
                break;
            case 7:
                gotoActivity(PullToRefreshTestActivity.class, false);
                break;
            case 8:
                gotoActivity(AutoLayoutActivity.class, false);
                break;
            default:
                Toastor.showSingletonToast(MainActivity.this, "还在开发中...");
                break;
        }

    }
}
