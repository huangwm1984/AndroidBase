package com.android.base.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;

import java.lang.ref.WeakReference;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class BaseActivity extends SupportActivity implements IBaseActivity, ActivityLifecycleProvider, EasyPermissions.PermissionCallbacks {

    private Toast mToast;
    public Activity mActivity;
    public Context mApplicationContext;
    public ActivityHandler mActivityHandler = new ActivityHandler(this);

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        mApplicationContext = getApplicationContext();

        ActivityStack.create().addActivity(this);

        if(setContentViewId()!=0)
            setContentView(setContentViewId());

        onActivityCreated(savedInstanceState);

        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        ActivityStack.create().finishActivity(this);
        mActivityHandler = null;
        mActivity = null;
    }

    @Override
    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
    }

    //////////////////////////////////////////////////////////////////////////////////

    public static class ActivityHandler extends Handler {

        WeakReference<BaseActivity> mReference = null;

        public ActivityHandler(BaseActivity outer) {
            this.mReference = new WeakReference<>(outer);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity outer = mReference.get();
            if (outer != null && !outer.isFinishing()) {
                outer.handleMessage(msg);
            }
        }
    }

    protected void handleMessage(Message msg) {
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

    public void showShortToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showLongToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

}
