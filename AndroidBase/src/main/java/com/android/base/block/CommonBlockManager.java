package com.android.base.block;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.android.base.block.impl.IBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/6/28
 * modify:huangwm
 */
public class CommonBlockManager implements IBlock {

    private List<IBlock> mCommonBlockList;

    protected Activity activity;

    public CommonBlockManager(@NonNull Activity activity) {
        this.activity = activity;
        mCommonBlockList = new ArrayList<>();
    }

    public CommonBlockManager add(@NonNull IBlock block) {
        block.attachActivity(activity);
        mCommonBlockList.add(block);
        return this;
    }

    public CommonBlockManager remove(@NonNull IBlock block) {
        block.onDestroy();
        if (mCommonBlockList.contains(block)) {
            mCommonBlockList.remove(block);
        }
        return this;
    }

    @CheckResult
    public <T extends IBlock> T get(@NonNull Class<T> cls) {
        if (mCommonBlockList != null) {
            for (int i = 0, size = mCommonBlockList.size(); i < size; i++) {
                if (mCommonBlockList.get(i).getClass().getCanonicalName().equals(cls.getCanonicalName())) {
                    return (T) mCommonBlockList.get(i);
                }
            }
        }
        return null;
    }

    @CheckResult
    public List<IBlock> getCommonBlockList() {
        return mCommonBlockList;
    }


    /// 回调 start -------------------

    @Override
    public void onSaveInstanceState(final Bundle outState, final PersistableBundle outPersistentState) {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onSaveInstanceState(outState, outPersistentState);
            }
        });
    }
    @Override
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onRestoreInstanceState(savedInstanceState);
            }
        });
    }

    @Override
    public void onStart() {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onStart();
            }
        });
    }

    @Override
    public void onResume() {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onResume();
            }
        });
    }

    @Override
    public void onPause() {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onPause();
            }
        });
    }

    @Override
    public void onStop() {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onStop();
            }
        });
    }

    @Override
    public void onRestart() {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onRestart();
            }
        });
    }

    @Override
    public void onDestroy() {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onDestroy();
            }
        });
        mCommonBlockList.clear();
        mCommonBlockList = null;
    }

    @Override
    public boolean onBackPressed() {
        for (IBlock block : mCommonBlockList) {
            if (block.onBackPressed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        callBlock(new Callback() {
            @Override
            public void onCall(IBlock block) {
                block.onActivityResult(requestCode, resultCode, data);
            }
        });
    }

    //// 回调 end -------------------

    private void callBlock(final Callback callback) {
        for (int i = 0, size = mCommonBlockList.size(); i < size; i++) {
            callback.onCall(mCommonBlockList.get(i));
        }
    }

    public Activity getActivity() {
        return activity;
    }

    private interface Callback {

        void onCall(IBlock block);

    }

    @Override
    public void attachActivity(Activity activity) {

    }
}
