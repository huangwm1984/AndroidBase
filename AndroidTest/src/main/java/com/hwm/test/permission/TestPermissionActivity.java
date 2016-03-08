package com.hwm.test.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.android.base.BaseActivity;
import com.android.base.common.assist.Toastor;
import com.apkfuns.logutils.LogUtils;
import com.hwm.test.R;

import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by huangwm on 2016/3/8.
 */
public class TestPermissionActivity extends BaseActivity {

    public static final int PERMISSIONS_CODE_GALLERY = 201;
    public static final int PERMISSIONS_CODE_CAMERA = 202;


    @Override
    protected int getMainContentViewId() {
        return R.layout.act_permission_main;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @OnClick(R.id.button_camera) void showCamera() {
        requestCameraPermission();
    }

    @OnClick(R.id.button_gallery) void showGallery() {
        requestGalleryPermission();
    }

    @AfterPermissionGranted(PERMISSIONS_CODE_GALLERY)
    private void requestGalleryPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toastor.showToast(getApplicationContext(), "允许访问相册权限已通过");
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permissions_tips_gallery),
                    PERMISSIONS_CODE_GALLERY, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(PERMISSIONS_CODE_CAMERA)
    private void requestCameraPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Toastor.showToast(getApplicationContext(), "允许访问照相机权限已通过");
        } else {
            LogUtils.e("失败");
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permissions_tips_camera),
                    PERMISSIONS_CODE_CAMERA, Manifest.permission.CAMERA);
        }
    }
}
