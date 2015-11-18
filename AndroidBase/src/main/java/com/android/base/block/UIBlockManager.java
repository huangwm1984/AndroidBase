package com.android.base.block;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/6/28
 */
public class UIBlockManager {

    private List<UIBlock> mUIBlockList;

    protected Activity activity;

    public UIBlockManager(@NonNull Activity activity) {
        this.activity = activity;
    }

    public UIBlockManager add(@NonNull UIBlock uiBlock) {
        uiBlock.attachActivity(activity);
        if (mUIBlockList == null) {
            mUIBlockList = new ArrayList<>();
        }
        mUIBlockList.add(uiBlock);
        return this;
    }

    public UIBlockManager remove(@NonNull UIBlock uiBlock) {
        uiBlock.onDestroy();
        if (mUIBlockList != null && mUIBlockList.contains(uiBlock)) {
            mUIBlockList.remove(uiBlock);
        }
        return this;
    }

    @CheckResult
    public <T extends UIBlock> T get(@NonNull Class<T> cls) {
        if (mUIBlockList != null) {
            for (int i = 0, size = mUIBlockList.size(); i < size; i++) {
                if (mUIBlockList.get(i).getClass().getName().equals(cls.getName())) {
                    return (T) mUIBlockList.get(i);
                }
            }
        }
        return null;
    }

    @CheckResult
    public List<UIBlock> getUIBlockList() {
        return mUIBlockList;
    }


    /// 回调 start -------------------

    public boolean onBackPressed() {
        boolean handled = false;
        if (mUIBlockList != null) {
            for (int i = 0, size = mUIBlockList.size(); i < size; i++) {
                handled = mUIBlockList.get(i).onBackPressed();
                if (handled) {
                    break;
                }
            }
        }
        return handled;
    }

    public void onResume() {
        if (mUIBlockList != null) {
            callBlock(new Callback() {
                @Override
                public void onCall(int i) {
                    mUIBlockList.get(i).onResume();
                }
            });
        }
    }

    public void onPause() {
        if (mUIBlockList != null) {
            callBlock(new Callback() {
                @Override
                public void onCall(int i) {
                    mUIBlockList.get(i).onPause();
                }
            });
        }
    }

    public void onDestroy() {
        if (mUIBlockList != null) {
            callBlock(new Callback() {
                @Override
                public void onCall(int i) {
                    mUIBlockList.get(i).onDestroy();
                }
            });
            mUIBlockList.clear();
            mUIBlockList = null;
        }
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        callBlock(new Callback() {
            @Override
            public void onCall(int i) {
                if(mUIBlockList != null){
                    mUIBlockList.get(i).onActivityResult(requestCode, resultCode, data);
                }
            }
        });
    }

    //// 回调 end -------------------

    private void callBlock(final Callback callback) {
        if (mUIBlockList != null) {
            for (int i = 0, size = mUIBlockList.size(); i < size; i++) {
                callback.onCall(i);
            }
        }
    }

    private interface Callback {

        void onCall(int i);

    }
}
