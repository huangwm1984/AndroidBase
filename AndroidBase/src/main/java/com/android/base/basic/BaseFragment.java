package com.android.base.basic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;

import java.lang.ref.WeakReference;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class BaseFragment extends SupportFragment implements FragmentLifecycleProvider, EasyPermissions.PermissionCallbacks {

    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    protected View mRootView = null;
    public FragmentHandler fragmentHandler = new FragmentHandler(this);

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return mLifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindFragment(mLifecycleSubject);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (setContentViewId() != 0)
            mRootView = inflater.inflate(setContentViewId(), container, false);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFragmentViewCreated(view, savedInstanceState);
        mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onFragmentActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        mLifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        mLifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        fragmentHandler = null;
    }

    @Override
    public void onDetach() {
        mLifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
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

    public static class FragmentHandler extends Handler {

        WeakReference<BaseFragment> mReference = null;

        public FragmentHandler(BaseFragment outer) {
            this.mReference = new WeakReference<>(outer);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseFragment outer = mReference.get();
            if (outer != null && outer.isAdded()) {
                outer.handleMessage(msg);
            }
        }
    }

    protected void handleMessage(Message msg) {
    }

    protected <T extends View> T bindView(int id) {
        return (T) mRootView.findViewById(id);
    }

    protected abstract int setContentViewId();
    protected abstract void onFragmentViewCreated(View view, Bundle savedInstanceState);
    protected abstract void onFragmentActivityCreated(Bundle savedInstanceState);
}
