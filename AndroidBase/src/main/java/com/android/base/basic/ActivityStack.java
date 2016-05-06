package com.android.base.basic;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Administrator on 2016/4/28.
 */
public class ActivityStack {

    private static Stack<IBaseActivity> mActivityStack;
    private static final ActivityStack sInstance = new ActivityStack();

    private ActivityStack() {
    }

    public static ActivityStack create() {
        return sInstance;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return mActivityStack.size();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(IBaseActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (mActivityStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend KJActivity");
        }
        if (mActivityStack.isEmpty()) {
            return null;
        }
        IBaseActivity activity = mActivityStack.lastElement();
        return (Activity) activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        IBaseActivity activity = null;
        for (IBaseActivity aty : mActivityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return (Activity) activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        IBaseActivity activity = mActivityStack.lastElement();
        finishActivity((Activity) activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            // activity.finish();//此处不用finish
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (IBaseActivity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity((Activity) activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (IBaseActivity activity : mActivityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity((Activity) activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                ((Activity) mActivityStack.get(i)).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
