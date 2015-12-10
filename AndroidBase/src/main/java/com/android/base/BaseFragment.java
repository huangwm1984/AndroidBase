package com.android.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.lifecycle.FragmentLifecycleCallbacks;
import com.apkfuns.logutils.LogUtils;
//import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/7/28 0028.
 */
public abstract class BaseFragment extends Fragment implements FragmentLifecycleCallbacks {

    //public View rootView;//缓存Fragment view
    /**
     * 是否可见状态
     */
    private boolean isVisible;
    /**
     * 标志位，View已经初始化完成。
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onFragmentAttach(this, activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onFragmentCreated(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /*if (rootView == null) {
            if (getMainContentViewId() != 0) {
                rootView = inflater.inflate(getMainContentViewId(), container, false); // set view
            }
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }*/
        isFirstLoad = true;
        View rootView = null;
        if (getMainContentViewId() != 0) {
            rootView = inflater.inflate(getMainContentViewId(), container, false);
        }
        isPrepared = true;
        lazyLoad();
        onFragmentCreateView(this, inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        onFragmentViewCreated(this, view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onFragmentActivityCreated(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        onFragmentStarted(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        onFragmentResumed(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        onFragmentPaused(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        onFragmentStopped(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        //refWatcher.watch(this);
        onFragmentDestroyed(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentDetach(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onFragmentSaveInstanceState(this, outState);
    }



    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layoutId) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(layoutId, container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        LifecycleDispatcher.get().onFragmentCreateView(this, inflater, container, savedInstanceState);
        return rootView;
    }*/


    private void bindViews(View view) {
        ButterKnife.bind(this, view);
    }


    /**
     * @return the context from the application
     */
    public final Context getApplicationContext() {
        Activity activity = getActivity();

        if (activity == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }

        return activity.getApplicationContext();
    }

    /**
     * @return the context from the activity
     */
    public final Context getContext() {
        Activity activity = getActivity();

        if (activity == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }

        return activity.getBaseContext();
    }

    /**
     * The same as press the back key.
     *
     * @see android.support.v4.app.FragmentActivity#onBackPressed
     */
    public final void finishFragment() {
        Activity activity = getActivity();

        if (activity == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }

        activity.onBackPressed();
    }

    /**
     * close several fragment by step
     *
     * @param step the number of the fragments which will be finished.
     */
    public final void finishFragmentByStep(int step) {
        Activity activity = getActivity();

        if (activity == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }

        List<Fragment> list = getFragmentManager().getFragments();
        if (list == null || list.size() < step) {
            throw new IllegalStateException("There is not enough Fragment to finish.");
        }

        for (int i = 0; i < step; i++) {
            activity.onBackPressed();
        }
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        //initData();
    }

    protected abstract int getMainContentViewId();

}
