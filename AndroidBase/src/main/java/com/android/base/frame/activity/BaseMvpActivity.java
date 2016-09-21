package com.android.base.frame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.base.frame.AppManager;
import com.android.base.frame.ViewWithPresenter;
import com.android.base.frame.presenter.XPresenter;
import com.android.base.frame.presenter.PresenterLifecycleManager;
import com.android.base.frame.presenter.factory.PresenterFactory;
import com.android.base.frame.presenter.factory.ReflectionPresenterFactory;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class BaseMvpActivity<P extends XPresenter> extends AppCompatActivity implements ViewWithPresenter<P> {

    private static final String PRESENTER_STATE_KEY = "presenter_state";
    private PresenterLifecycleManager<P> presenterManager =
            new PresenterLifecycleManager<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.create().addActivity(this);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            presenterManager.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY));
        }
        presenterManager.onCreated(this);
        initData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_STATE_KEY, presenterManager.onSaveInstanceState());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenterManager.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterManager.onResume();
    }

    @Override
    protected void onPause() {
        presenterManager.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        presenterManager.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenterManager.onDestroy();
        super.onDestroy();
        AppManager.create().finishActivity(this);
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

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, int flags, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        intent.addFlags(flags);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected abstract int getContentViewId();

    protected abstract void initData();

}
