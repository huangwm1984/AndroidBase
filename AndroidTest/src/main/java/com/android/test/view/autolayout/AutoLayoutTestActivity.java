package com.android.test.view.autolayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.android.base.BaseActivity;
import com.android.base.common.assist.Toastor;
import com.android.test.R;
import com.android.test.TestBaseActivity;
import com.android.test.download.test.GameListActivity;
import com.android.test.leakcanary.LeakcanaryActivity;
import com.android.test.net.TestHttpActivity;
import com.android.test.permission.PermissionActivity;
import com.android.test.view.banner.BannerTestActivity;
import com.android.test.view.other.SideLayoutActivity;
import com.android.test.view.pulltorefresh.PullToRefreshTestActivity;
import com.android.test.view.recycler.RecyclerViewTestActivity;
import com.android.test.view.tabhost.FragmentTabHostActivity;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class AutoLayoutTestActivity extends TestBaseActivity {

    @Override
    public String getMainTitle() {
        return "AutoLayout";
    }

    @Override
    public String[] getButtonTexts() {
        return getResources().getStringArray(R.array.auto_list);
    }

    @Override
    public void getButtonClick(int id) {
        onClickButton(id);
    }

    private void onClickButton(int id) {

        Intent intent = new Intent();
        switch (id) {
            case 0:
                gotoActivity(AutoLayoutActivity.class, false);
                break;
            case 1:
                gotoActivity(AutoLayoutViewPagerActivity.class, false);
                break;
            case 2:
                gotoActivity(AutoLayoutCategoryActivity.class, false);
                break;
            default:
                break;
        }

    }
}
