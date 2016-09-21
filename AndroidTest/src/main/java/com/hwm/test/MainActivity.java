package com.hwm.test;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.activity.BaseActivity;
import com.hwm.test.http.view.RetrofitActivity;

import butterknife.Bind;


/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.container) LinearLayout mContainer;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mTitle.setText("huangwm test");
        String[] bttxt = getResources().getStringArray(R.array.test_list);
        if (bttxt != null) {
            for (int i = 0; i < bttxt.length; i++) {
                Button bt = new Button(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int margin = getResources().getDimensionPixelSize(R.dimen.common_marin);
                lp.setMargins(margin, margin, margin, margin);
                bt.setId(i);
                bt.setText(bttxt[i]);
                bt.setOnClickListener(this);
                bt.setLayoutParams(lp);
                mContainer.addView(bt);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                gotoActivity(RetrofitActivity.class, false);
                break;
            case 1:
                //gotoActivity(FragmentTabHostActivity.class, false);
                break;
            default:
                //showShortToast("还在开发中...");
                break;
        }

    }

}
