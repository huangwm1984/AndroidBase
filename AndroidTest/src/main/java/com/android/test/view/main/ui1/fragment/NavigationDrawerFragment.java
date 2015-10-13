package com.android.test.view.main.ui1.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.base.BaseFragment;
import com.android.base.quickadapter.BaseAdapterHelper;
import com.android.base.quickadapter.QuickAdapter;
import com.android.test.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class NavigationDrawerFragment extends BaseFragment {


    @Bind(R.id.lv)
    ListView mMenu;

    private QuickAdapter<String> mAdapter;

    private List<String> mListStr;

    @Override
    protected int getMainContentViewId() {
        return R.layout.frg_left_menu;
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

        mListStr = new ArrayList<String>();
        mListStr.add("技术问答");
        mListStr.add("开源软件");
        mListStr.add("博客区");
        mListStr.add("Git客户端");

        mAdapter = new QuickAdapter<String>(getActivity(), R.layout.item_left_menu, mListStr) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.id_item_title, item);


            }
        };

        mMenu.setAdapter(mAdapter);

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
