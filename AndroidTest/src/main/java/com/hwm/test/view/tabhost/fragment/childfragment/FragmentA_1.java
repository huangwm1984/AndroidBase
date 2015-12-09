package com.hwm.test.view.tabhost.fragment.childfragment;

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
import com.hwm.test.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/15 0015.
 */
public class FragmentA_1 extends BaseFragment{

    @Bind(R.id.listview)
    ListView mListView;
    private QuickAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    protected int getMainContentViewId() {
        return R.layout.frag_test_tab_a;
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
        initData();
        setListView();
    }


    private void initData() {
        for (int i = 0; i < 50; i++){
            mDatas.add("测试"+" -> " + i);
        }
    }


    private void setListView() {

        mAdapter = new QuickAdapter<String>(getActivity(), R.layout.item_left_menu, mDatas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.id_item_title, item);
            }
        };

        mListView.setAdapter(mAdapter);
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
