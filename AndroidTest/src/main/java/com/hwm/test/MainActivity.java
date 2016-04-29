package com.hwm.test;

import android.app.Activity;
import android.content.Intent;

import com.android.base.common.assist.Toastor;
import com.hwm.test.R;
import com.hwm.test.download.bizs.DLCons;
import com.hwm.test.download.bizs.DLInfo;
import com.hwm.test.download.bizs.DLManager;
import com.hwm.test.download.db.dao.DLInfoDao;
import com.hwm.test.download.test.GameListActivity;
import com.hwm.test.net.TestHttpActivity;
import com.hwm.test.leakcanary.LeakcanaryActivity;
import com.hwm.test.permission.TestPermissionActivity;
import com.hwm.test.view.autolayout.AutoLayoutTestActivity;
import com.hwm.test.view.banner.BannerTestActivity;
import com.hwm.test.view.pulltorefresh.PullToRefreshTestActivity;
import com.hwm.test.view.tabhost.FragmentTabHostActivity;
import com.hwm.test.view.recycler.RecyclerViewTestActivity;
import com.apkfuns.logutils.LogUtils;

import java.sql.SQLException;
import java.util.List;

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
                gotoActivity(GameListActivity.class, false);
                break;
            case 3:
                gotoActivity(RecyclerViewTestActivity.class, false);
                break;
            case 4:
                gotoActivity(TestPermissionActivity.class, false);
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
                gotoActivity(AutoLayoutTestActivity.class, false);
                break;
            case 9:
                //gotoActivity(SideLayoutActivity.class, false);
                Toastor.showSingletonToast(mApplicationContext, "还在开发中...");
                break;
            default:
                Toastor.showSingletonToast(mApplicationContext, "还在开发中...");
                break;
        }

    }


    @Override
    public void onActivityDestroyed(Activity activity) {
        stopDownloadingTask();
        super.onActivityDestroyed(activity);

    }

    public void stopDownloadingTask(){
        DLManager dlManager = DLManager.getInstance(mApplicationContext);
        DLInfoDao dlInfoDao = dlManager.getDLInfoDao();
        List<DLInfo> infos = null;
        if(dlInfoDao!=null){
            try {
                infos = dlInfoDao.query(new String[]{DLCons.DBCons.TB_TASK_STATE}, new Integer[]{DLCons.DLState.DOWNLOADING});
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(infos!=null && infos.size()!=0){
                LogUtils.e("要暂停下载的任务数："+ infos.size());
                for(DLInfo info : infos){
                    try {
                        LogUtils.e("要暂停的任务："+ info.appName);
                        info.state = DLCons.DLState.PAUSE;
                        dlInfoDao.update(info);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dlManager.dlStop(info.baseUrl);
                }
            }
        }
    }
}
