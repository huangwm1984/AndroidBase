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


    @Bind(R.id.listview)
    ListView mMenu;

    private QuickAdapter<MenuBean> mAdapter;

    private List<MenuBean> mMenus;

    @Override
    protected int getMainContentViewId() {
        return R.layout.frag_left_menu;
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

        initMenu();
        initAdapter();

    }


    private void initMenu() {
        mMenus = new ArrayList<MenuBean>();

        MenuBean quest_menu = new MenuBean();
        quest_menu.menuName = "技术问答";
        quest_menu.resourceId = R.mipmap.drawer_menu_icon_quest_nor;

        MenuBean opensoft_menu = new MenuBean();
        opensoft_menu.menuName = "开源软件";
        opensoft_menu.resourceId = R.mipmap.drawer_menu_icon_opensoft_nor;

        MenuBean blog_menu = new MenuBean();
        blog_menu.menuName = "博客区";
        blog_menu.resourceId = R.mipmap.drawer_menu_icon_blog_nor;

        MenuBean gitapp_menu = new MenuBean();
        gitapp_menu.menuName = "Git客户端";
        gitapp_menu.resourceId = R.mipmap.drawer_menu_icon_gitapp_nor;

        mMenus.add(quest_menu);
        mMenus.add(opensoft_menu);
        mMenus.add(blog_menu);
        mMenus.add(gitapp_menu);
    }


    private void initAdapter() {

        mAdapter = new QuickAdapter<MenuBean>(getActivity(), R.layout.item_left_menu, mMenus) {
            @Override
            protected void convert(BaseAdapterHelper helper, MenuBean item) {
                helper.setImageResource(R.id.id_item_icon, item.resourceId);
                helper.setText(R.id.id_item_title, item.menuName);
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


    public class MenuBean{
        public int resourceId;
        public String menuName;
    }

}
