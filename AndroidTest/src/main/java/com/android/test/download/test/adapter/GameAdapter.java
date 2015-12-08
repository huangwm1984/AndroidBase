package com.android.test.download.test.adapter;

import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.base.block.SampleBlock;
import com.android.base.quickadapter.recycler.BaseRcvAdapterHelper;
import com.android.base.quickadapter.recycler.BaseRcvQuickAdapter;
import com.android.base.quickadapter.recycler.QuickRcvAdapter;
import com.android.test.MyApplication;
import com.android.test.R;
import com.android.test.download.bizs.DLInfo;
import com.android.test.download.bizs.DLManager;
import com.android.test.download.bizs.DLThreadInfo;
import com.android.test.download.db.dao.DLInfoDao;
import com.android.test.download.interfaces.SimpleDListener;
import com.android.test.download.test.entity.GameInfo;
import com.android.test.download.test.util.FileUtils;
import com.android.test.download.test.util.Utils;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.android.test.download.bizs.DLCons.DBCons;
import static com.android.test.download.bizs.DLCons.DLState;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class GameAdapter extends SampleBlock implements BaseRcvQuickAdapter.OnItemClickListener{


    QuickRcvAdapter mQuickRcvAdapter;
    ArrayMap<Integer,Boolean> mIsExpend;
    String saveDir = Environment.getExternalStorageDirectory() + "/AndroidTest/download";
    DLManager mDLManager;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreated() {
        mIsExpend = new ArrayMap<>();
        mDLManager = DLManager.getInstance(mActivity.getApplicationContext());
        MyApplication.getInstance().setAllDLTasks(mDLManager.getAllTasks());
    }

    public void setAdapter(final RecyclerView recyclerView, List<GameInfo.DataEntity> data){

        mRecyclerView = recyclerView;

        mQuickRcvAdapter = new QuickRcvAdapter<GameInfo.DataEntity>(mActivity, R.layout.item_download_manager_list, data) {

            @Override
            protected void convert(final BaseRcvAdapterHelper helper, final GameInfo.DataEntity item) {

                //应用icon
                ImageView mIvIcon = helper.getView(R.id.iv_icon);
                //加载应用图片
                Glide.with(mActivity)
                        .load(item.getIcon())
                        .crossFade()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(mIvIcon);

                //应用名
                TextView mTvTitle = helper.getView(R.id.tv_title);
                mTvTitle.setText(item.getTopic_cn());

                //下载进度条
                final ProgressBar mNumberProgressBar = helper.getView(R.id.number_progress_bar);
                mNumberProgressBar.setTag(item.getAddress());

                //下载速率
                final TextView mTvDownloadSpeed = helper.getView(R.id.tv_download_speed);
                mTvDownloadSpeed.setTag(item.getIcon());

                //下载速度展示
                final TextView mTvDownloadScale = helper.getView(R.id.tv_download_scale);
                mTvDownloadScale.setTag(item.getPackageX());

                //下载、继续、暂停按钮
                final Button mBtnOperate = helper.getView(R.id.btn_operate);
                mBtnOperate.setTag(item.getShorturl());

                DLInfo info = null;
                if(getAllDLTasks()!=null && !getAllDLTasks().isEmpty()){
                    info = getAllDLTasks().get(item.getShorturl());
                    if(info!=null){
                        mNumberProgressBar.setProgress(info.progress);
                        mNumberProgressBar.setMax(info.totalBytes);

                        if (info!=null && info.getTotalBytes() > 0) {
                            String downladScale = FileUtils.generateFileSize(info.getCurrentBytes()) + "/"
                                    + FileUtils.generateFileSize(info.getTotalBytes());
                            mTvDownloadScale.setText(downladScale);
                        }

                        if(info.getState() == DLState.DOWNLOAD){
                            mBtnOperate.setText("下载");
                            mNumberProgressBar.setVisibility(View.GONE);
                            mTvDownloadSpeed.setVisibility(View.GONE);
                            mTvDownloadScale.setVisibility(View.GONE);
                        }else if(info.getState() == DLState.DOWNLOADING){
                            mBtnOperate.setText("暂停");
                            mNumberProgressBar.setProgress(info.progress);
                            LogUtils.e("下载中 当前进度="+info.progress);
                            mNumberProgressBar.setVisibility(View.VISIBLE);
                            mTvDownloadSpeed.setVisibility(View.VISIBLE);
                            mTvDownloadScale.setVisibility(View.VISIBLE);
                        }else if(info.getState() == DLState.PAUSE){
                            mBtnOperate.setText("继续");
                            mNumberProgressBar.setProgress(info.progress);
                            LogUtils.e("暂停 当前进度="+info.progress);
                            mNumberProgressBar.setVisibility(View.VISIBLE);
                            mTvDownloadSpeed.setVisibility(View.VISIBLE);
                            mTvDownloadScale.setVisibility(View.VISIBLE);
                        }else if(info.getState() == DLState.COMPLETE){
                            mBtnOperate.setText("安装");
                            mNumberProgressBar.setVisibility(View.GONE);
                            mTvDownloadSpeed.setVisibility(View.GONE);
                            mTvDownloadScale.setVisibility(View.GONE);
                        }else if(info.getState() == DLState.FAIL){
                            mBtnOperate.setText("继续");
                            mNumberProgressBar.setProgress(info.progress);
                            LogUtils.e("失败 当前进度="+info.progress);
                            mNumberProgressBar.setVisibility(View.VISIBLE);
                            mTvDownloadSpeed.setVisibility(View.VISIBLE);
                            mTvDownloadScale.setVisibility(View.VISIBLE);
                        }else if(Utils.isAppInstalled(mActivity.getApplicationContext(), item.getPackageX())){
                            mBtnOperate.setText("启动");
                            mNumberProgressBar.setVisibility(View.GONE);
                            mTvDownloadSpeed.setVisibility(View.GONE);
                            mTvDownloadScale.setVisibility(View.GONE);
                        }

                    }else{
                        if(Utils.isAppInstalled(mActivity.getApplicationContext(), item.getPackageX())){
                            mBtnOperate.setText("启动");
                            mNumberProgressBar.setVisibility(View.GONE);
                            mTvDownloadSpeed.setVisibility(View.GONE);
                            mTvDownloadScale.setVisibility(View.GONE);
                        } else{
                            mBtnOperate.setText("下载");
                            mNumberProgressBar.setVisibility(View.GONE);
                            mTvDownloadSpeed.setVisibility(View.GONE);
                            mTvDownloadScale.setVisibility(View.GONE);
                        }
                    }

                }else{
                    if(Utils.isAppInstalled(mActivity.getApplicationContext(), item.getPackageX())){
                        mBtnOperate.setText("启动");
                        mNumberProgressBar.setVisibility(View.GONE);
                        mTvDownloadSpeed.setVisibility(View.GONE);
                        mTvDownloadScale.setVisibility(View.GONE);
                    } else{
                        mBtnOperate.setText("下载");
                        mNumberProgressBar.setVisibility(View.GONE);
                        mTvDownloadSpeed.setVisibility(View.GONE);
                        mTvDownloadScale.setVisibility(View.GONE);
                    }
                }

                mBtnOperate.setOnClickListener(new OperateButtonClickListener(info, helper, item));

                //显示详情、卸载
                final View bar = helper.getView(R.id.ll_bottom_bar);
                if(mIsExpend !=null && !mIsExpend.isEmpty()){
                    if(mIsExpend.get(helper.getLayoutPosition()) == true){
                        bar.setVisibility(View.VISIBLE);
                    }else{
                        bar.setVisibility(View.GONE);
                    }
                }

            }
        };

        mQuickRcvAdapter.setOnItemClickListener(this);

    }


    /**
     * 继续、停止、安装按钮事件
     */
    private class OperateButtonClickListener implements View.OnClickListener {
        DLInfo mInfo;
        GameInfo.DataEntity mItem;
        BaseRcvAdapterHelper mHelper;

        public OperateButtonClickListener(DLInfo info, BaseRcvAdapterHelper helper, GameInfo.DataEntity item) {
            this.mInfo = info;
            this.mHelper = helper;
            this.mItem = item;
        }

        @Override
        public void onClick(View v) {
            //如果是安装的app,则启动该app
            if(Utils.isAppInstalled(mActivity.getApplicationContext(), mItem.getPackageX())){
                Utils.startThirdApp(mActivity, mItem.getPackageX());
                return;
            }

            Button mBtnOperate = mHelper.getView(R.id.btn_operate);

            //状态为下载完成
            final DLInfo info = MyApplication.getInstance().getDLTask(mInfo);
            if(info!=null){
                LogUtils.e("点击按钮时的下载状态="+info.state);
                if(info.state == DLState.COMPLETE){
                    info.file = new File(info.dirPath, info.fileName);
                    if(Utils.isFileExist(info.file) && info.file.length() == info.totalBytes){
                        // 安装包存在，则安装应用
                        LogUtils.e("安装"+info.appName);
                        Utils.installApp(v.getContext(), info.file);
                        return;
                    }else{
                        // 安装包不存在，则删除下载记录
                        DLInfoDao dlInfoDao = mDLManager.getDLInfoDao();
                        if(dlInfoDao!=null){
                            try {
                                dlInfoDao.delete(info);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        mBtnOperate.setText("下载");
                    }
                }else if(info.state == DLState.DOWNLOADING){
                    mDLManager.dlStop(info.baseUrl);
                    mBtnOperate.setText("继续");
                }else if(info.state == DLState.PAUSE || info.state == DLState.FAIL){
                    mDLManager.dlStart(info.baseUrl, new DownloadListener(mItem));
                    mBtnOperate.setText("暂停");
                }else if(info.state == DLState.DOWNLOAD){
                    addDownloadListener(info, mItem);
                    mBtnOperate.setText("继续");
                }
            }else{
                addDownloadListener(info, mItem);
                mBtnOperate.setText("继续");
            }
        }
    }


    /**
     * 添加下载回调
     * @param info
     * @param item
     */
    private void addDownloadListener(final DLInfo info, final GameInfo.DataEntity item) {

        //下载监听
        mDLManager.dlStart(item.getShorturl(), saveDir, null, item.getTopic_cn(), new DownloadListener(item));



    }

    class DownloadListener extends SimpleDListener{//创建下载监听对象

        GameInfo.DataEntity item;

        public DownloadListener(GameInfo.DataEntity item){
            this.item = item;
        }


        @Override
        public void onPrepare(DLInfo info) {
            MyApplication.getInstance().updateDLTask(info);
            LogUtils.e("准备下载状态="+info.state);
            if(mActivity!=null){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button mBtnOperate = (Button) mRecyclerView.findViewWithTag(item.getShorturl());
                        ProgressBar mNumberProgressBar = (ProgressBar) mRecyclerView.findViewWithTag(item.getAddress());
                        if(mBtnOperate!=null){
                            mBtnOperate.setText("暂停");
                        }
                        if(mNumberProgressBar!=null){
                            mNumberProgressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }

        @Override
        public void onStart(DLInfo info) {
            MyApplication.getInstance().updateDLTask(info);
            LogUtils.e("开始下载状态="+info.state);
            LogUtils.e("开始下载"+info.getAppName());
            ProgressBar mNumberProgressBar = (ProgressBar) mRecyclerView.findViewWithTag(item.getAddress());
            if(mNumberProgressBar!=null){
                if(info.getTotalBytes() != 0){
                    mNumberProgressBar.setMax(info.totalBytes);
                }
            }
        }

        @Override
        public void onProgress(final DLInfo info) {
            if(mActivity!=null){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar mNumberProgressBar = (ProgressBar) mRecyclerView.findViewWithTag(item.getAddress());
                        TextView mTvDownloadScale = (TextView) mRecyclerView.findViewWithTag(item.getPackageX());
                        TextView mTvDownloadSpeed = (TextView) mRecyclerView.findViewWithTag(item.getIcon());
                        if(mNumberProgressBar!=null){
                            mNumberProgressBar.setProgress(info.progress);
                        }
                        if(mTvDownloadScale!=null){
                            String downladScale = FileUtils.generateFileSize(info.currentBytes) + "/"
                                    + FileUtils.generateFileSize(info.totalBytes);
                            mTvDownloadScale.setText(downladScale);
                            mTvDownloadScale.setVisibility(View.VISIBLE);
                        }
                        if(mTvDownloadSpeed!=null){
                            mTvDownloadSpeed.setText(FileUtils.generateFileSize(info.networkSpeed));
                            mTvDownloadSpeed.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }

        @Override
        public void onStop(DLInfo info) {
            MyApplication.getInstance().updateDLTask(info);
            if(mActivity!=null){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button mBtnOperate = (Button) mRecyclerView.findViewWithTag(item.getShorturl());
                        if(mBtnOperate!=null){
                            mBtnOperate.setText("继续");
                        }
                    }
                });
            }
        }

        @Override
        public void onFinish(DLInfo info) {
            MyApplication.getInstance().updateDLTask(info);
            if(mActivity!=null){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button mBtnOperate = (Button) mRecyclerView.findViewWithTag(item.getShorturl());
                        TextView mTvDownloadScale = (TextView) mRecyclerView.findViewWithTag(item.getPackageX());
                        TextView mTvDownloadSpeed = (TextView) mRecyclerView.findViewWithTag(item.getIcon());
                        LogUtils.e("mBtnOperate="+mBtnOperate);
                        if(mBtnOperate!=null){
                            mBtnOperate.setText("安装");
                        }
                        if(mTvDownloadScale!=null){
                            mTvDownloadScale.setVisibility(View.GONE);
                            mTvDownloadScale.setVisibility(View.GONE);
                        }
                        if(mTvDownloadSpeed!=null){
                            mTvDownloadSpeed.setVisibility(View.GONE);
                            mTvDownloadSpeed.setVisibility(View.GONE);
                        }
                        getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }

        @Override
        public void onError(int status, String error, DLInfo info) {
            if (info!=null) {
                MyApplication.getInstance().updateDLTask(info);
            }
            if(mActivity!=null){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Button mBtnOperate = (Button) mRecyclerView.findViewWithTag(item.getShorturl());
                        if(mBtnOperate!=null){
                            mBtnOperate.setText("继续");
                        }
                        getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }


    };


    public void initFlag(List<GameInfo.DataEntity> data) {
        if(data == null || data.size() == 0){
            return;
        }

        if(getAdapter().getItemCount() == 0){
            for(int i=0;i<data.size();i++){
                mIsExpend.put(i, false);
            }
        }else{
            int flagCount = mIsExpend.size();
            int dataCount = data.size();
            for(int j=flagCount; j<flagCount + dataCount; j++){
                mIsExpend.put(j, false);
            }
        }

    }


    public QuickRcvAdapter getAdapter(){
        return mQuickRcvAdapter;
    }

    public ConcurrentHashMap<String, DLInfo> getAllDLTasks(){
        return MyApplication.getInstance().getAllTaks();
    }

    @Override
    public void onItemClick(View view, int position) {
        View bar = view.findViewById(R.id.ll_bottom_bar);
        if(bar.getVisibility() == View.VISIBLE){
            bar.setVisibility(View.GONE);
            mIsExpend.put(position, false);
        }else{
            bar.setVisibility(View.VISIBLE);
            mIsExpend.put(position, true);
        }
    }

}
