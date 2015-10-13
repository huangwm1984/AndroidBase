package com.android.test.view.main.ui1.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.base.BaseFragment;
import com.android.test.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/8/3 0003.
 */
public class FragmentC extends BaseFragment {

    @Bind(R.id.tv)
    TextView mTv;

    @Override
    protected int getMainContentViewId() {
        return R.layout.frg_test;
    }

    @Override
    public void onFragmentAttach(Fragment fragment, Activity activity) {

    }

    @Override
    public void onFragmentCreated(Fragment fragment, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentCreateView(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentViewCreated(Fragment fragment, View view, Bundle savedInstanceState) {
        mTv.setText("我是第三页");
    }

    @Override
    public void onFragmentActivityCreated(Fragment fragment, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentStarted(Fragment fragment) {

    }

    @Override
    public void onFragmentResumed(Fragment fragment) {

    }

    @Override
    public void onFragmentPaused(Fragment fragment) {

    }

    @Override
    public void onFragmentStopped(Fragment fragment) {

    }

    @Override
    public void onFragmentDestroyed(Fragment fragment) {

    }

    @Override
    public void onFragmentDetach(Fragment fragment) {

    }

    @Override
    public void onFragmentSaveInstanceState(Fragment fragment, Bundle outState) {

    }
}
