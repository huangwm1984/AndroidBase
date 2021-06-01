package com.android.base.frame.presenter;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2016/9/19.
 */
public class BasePresenter<V>{

    private V view;

    private CopyOnWriteArrayList<OnDestroyListener> onDestroyListeners = new CopyOnWriteArrayList<>();

    public void onAttachView(V view) {}

    public void onResume() {}

    public void onPause() {}

    public void onDestroy() {
        destroy();
    }

    public V getView() {
        return view;
    }

    public void attachView(V view) {
        this.view = view;
        onAttachView(view);
    }

    public interface OnDestroyListener {
        void onDestroy();
    }

    public void addOnDestroyListener(OnDestroyListener listener) {
        onDestroyListeners.add(listener);
    }

    public void destroy() {
        for (OnDestroyListener listener : onDestroyListeners) {
            listener.onDestroy();
        }
    }

}
