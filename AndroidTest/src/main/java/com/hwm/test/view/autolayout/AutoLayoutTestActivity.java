package com.hwm.test.view.autolayout;

import android.content.Intent;

import com.hwm.test.R;
import com.hwm.test.TestBaseActivity;

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
