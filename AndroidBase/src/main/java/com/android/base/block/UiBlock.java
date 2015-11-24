package com.android.base.block;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;

import com.android.base.block.impl.IBlock;

import butterknife.ButterKnife;

public abstract class UiBlock implements IBlock{

    protected String TAG = getClass().getSimpleName();

    public View mRootView;

    public Activity mActivity;

    public void attachActivity(Activity activity) {
        mActivity = activity;
        //mRootView = mActivity.findViewById(getRootViewId());
        mRootView = getRootView();

        //bindViews();
        //beforeSetViews();
        //setViews();
        if(mRootView!=null)ButterKnife.bind(this, mRootView);
        onCreated();
    }

    /**
     * 找到所有的views
     */
    //protected abstract void bindViews();

    /**
     * 在这里初始化设置view的各种资源，比如适配器或各种变量
     */
    //protected void beforeSetViews() {}

    /**
     * 设置所有的view
     */
    //protected abstract void setViews();


    /**
     * @return true if you want to shield back key
     */
    public boolean onBackPressed() {
        return false;
    }

    public void onResume(){

    }

    public void onPause(){

    }

    public void onStop(){

    }

    public void onDestroy() {
        mActivity = null;
        mRootView = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * 获取块的主布局
     * @return fragment的根view
     */
    public abstract View getRootView();

    /**
     * view创建完成之后逻辑写在这里
     */
    protected abstract void onCreated();

    protected Activity getActivity() {
        return mActivity;
    }

    protected <T extends Activity> T getActivity(Class<T> cls) {
        return (T)mActivity;
    }

    @SuppressWarnings("unchecked")
    protected final <E extends View> E getView(int id) {
        try {
            if(mRootView!=null){
                return (E) mRootView.findViewById(id);
            }else{
                return null;
            }
        } catch (ClassCastException ex) {
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}
