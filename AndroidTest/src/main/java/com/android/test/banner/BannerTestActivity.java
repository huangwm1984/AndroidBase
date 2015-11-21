package com.android.test.banner;

import android.content.Intent;

import com.android.base.common.assist.Toastor;
import com.android.test.R;
import com.android.test.TestBaseActivity;
import com.android.test.net.TestHttpActivity;
import com.android.test.view.tabhost.FragmentTabHostActivity;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class BannerTestActivity extends TestBaseActivity {

    @Override
    public String getMainTitle() {
        return "Banner Test";
    }

    @Override
    public String[] getButtonTexts() {
        return getResources().getStringArray(R.array.banner_list);
    }

    @Override
    public void getButtonClick(int id) {
        onClickButton(id);
    }

    protected void onClickButton(int id) {
        Intent intent = new Intent();
        switch (id) {
            case 0:
                gotoActivity(BannerActivity.class, false);
                break;
            case 1:

                break;
            default:
                Toastor.showSingletonToast(BannerTestActivity.this, "还在开发中...");
                break;
        }
    }
}
