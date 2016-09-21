package com.android.base.frame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.frame.ViewWithPresenter;
import com.android.base.frame.presenter.XPresenter;
import com.android.base.frame.presenter.PresenterLifecycleManager;
import com.android.base.frame.presenter.factory.PresenterFactory;
import com.android.base.frame.presenter.factory.ReflectionPresenterFactory;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class BaseMvpFragment<P extends XPresenter> extends Fragment implements ViewWithPresenter<P> {

    private static final String PRESENTER_STATE_KEY = "presenter_state";
    private PresenterLifecycleManager<P> presenterManager =
            new PresenterLifecycleManager<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            presenterManager.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(PRESENTER_STATE_KEY, presenterManager.onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(presenterManager!=null){
            presenterManager.onCreated(this);
        }
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(presenterManager!=null){
            presenterManager.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenterManager!=null){
            presenterManager.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(presenterManager!=null){
            presenterManager.onPause();
        }
    }

    @Override
    public void onStop() {
        if(presenterManager!=null){
            presenterManager.onStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        if(presenterManager!=null){
            presenterManager.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public PresenterFactory<P> getPresenterFactory() {
        return presenterManager.getPresenterFactory();
    }

    @Override
    public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
        presenterManager.setPresenterFactory(presenterFactory);
    }

    @Override
    public P getPresenter() {
        return presenterManager.getPresenter();
    }

    public abstract int getContentViewId();

    protected abstract void initData();

}
