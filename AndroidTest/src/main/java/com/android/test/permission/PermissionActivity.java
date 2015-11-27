package com.android.test.permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.android.base.BaseActivity;
import com.android.base.permission.PermissionsDispatcher;
import com.android.base.permission.impl.PermissionListener;
import com.android.test.R;
import com.apkfuns.logutils.LogUtils;

import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class PermissionActivity extends BaseActivity {

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_CONTACTS = 1;
    private static final int REQUEST_IMPORTANT_PERMISSION = 2;

    /**
     * Permissions required to read and write contacts
     */
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};


    @Override
    protected int getMainContentViewId() {
        return R.layout.act_permission_main;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        //在应用打开后， 获取重要的权限
        checkImportantPermissions();

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
        PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CAMERA, new String[]{Manifest.permission.CAMERA});
    }

    @OnClick(R.id.button_contacts) void showContacts() {
        PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CONTACTS, PERMISSIONS_CONTACT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //处理权限的授权结果
        PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, permissions, grantResults, permissionListener);
    }

    /*****
     * 检查重要的权限的授权
     */
    private void checkImportantPermissions() {
        PermissionsDispatcher.checkPermissions(this,//
                REQUEST_IMPORTANT_PERMISSION, permissionListener, //
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS});
    }

    /*****
     * 获取重要的权限
     */
    private void requestImportantPermissions() {
        PermissionsDispatcher.requestPermissions(this,//
                REQUEST_IMPORTANT_PERMISSION,  //
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS});
    }

    /**
     * 权限监听器
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionsGranted(Activity act, int requestCode, int[] grantResults, String... permissions) {

            if (requestCode == REQUEST_CAMERA) {

                //获取摄像头的权限的授权成功

                // Camera permissions is already available, show the camera preview.
                LogUtils.i("CAMERA permission has already been granted. Displaying camera preview.");

                //showCameraPreview();

            } else if (requestCode == REQUEST_CONTACTS) {

                // 获取通讯录读取权限的授权成功

                // Contact permissions have been granted. Show the contacts fragment.
                LogUtils.i("Contact permissions have already been granted. Displaying contact details.");

                //showContactDetails();
            }else if(requestCode == REQUEST_IMPORTANT_PERMISSION){

                //获取重要的权限
                //不需要做什么事
                //don't need to do anything
            }

        }

        @Override
        public void onPermissionsDenied(Activity act, int requestCode, int[] grantResults, String... permissions) {

            if (requestCode == REQUEST_CAMERA) {

                //获取摄像头的权限的授权失败

                LogUtils.i("CAMERA permission was NOT granted.");
                //Snackbar.make(mLayout, R.string.permissions_not_granted, Snackbar.LENGTH_SHORT).show();

            } else if (requestCode == REQUEST_CONTACTS) {

                // 获取通讯录读取权限的授权失败

                LogUtils.i("Contacts permissions were NOT granted.");
                //Snackbar.make(mLayout, R.string.permissions_not_granted, Snackbar.LENGTH_SHORT).show();

            }else if(requestCode == REQUEST_IMPORTANT_PERMISSION){

                //获取重要的权限
                LogUtils.i("Contacts permissions were NOT granted.");
                //Snackbar.make(mLayout, R.string.permissions_not_granted, Snackbar.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onShowRequestPermissionRationale(Activity act, int requestCode, boolean isShowRationale, String... permissions) {

            if (requestCode == REQUEST_CAMERA) {
                if (isShowRationale) {

                    //获取摄像头的权限的授权:显示为什么需要这个权限的说明

                    LogUtils.i("Displaying camera permission rationale to provide additional context.");
                    /*Snackbar.make(mLayout, R.string.permission_camera_rationale, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CAMERA, new String[]{Manifest.permission.CAMERA});
                                }
                            })
                            .show();*/
                    new AlertDialog.Builder(PermissionActivity.this)
                            .setMessage(R.string.permission_camera_rationale)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CAMERA, new String[]{Manifest.permission.CAMERA});
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                            .create()
                            .show();
                } else {
                    //直接获取摄像头的权限的授权
                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CAMERA, new String[]{Manifest.permission.CAMERA});
                }


            } else if (requestCode == REQUEST_CONTACTS) {
                if (isShowRationale) {

                    //获取通讯录的权限的授权:显示为什么需要这个权限的说明

                    LogUtils.i("Displaying contacts permission rationale to provide additional context.");
                    /*Snackbar.make(mLayout, R.string.permission_contacts_rationale, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CONTACTS, PERMISSIONS_CONTACT);
                                }
                            })
                            .show();*/
                    new AlertDialog.Builder(PermissionActivity.this)
                            .setMessage(R.string.permission_contacts_rationale)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CONTACTS, PERMISSIONS_CONTACT);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CONTACTS, PERMISSIONS_CONTACT);
                                }
                            })
                            .create()
                            .show();
                } else {
                    //直接获取通讯录的权限的授权
                    PermissionsDispatcher.requestPermissions(PermissionActivity.this, REQUEST_CONTACTS, PERMISSIONS_CONTACT);

                }
            }else if(requestCode == REQUEST_IMPORTANT_PERMISSION){
                if (isShowRationale) {

                    //获取重要的权限的授权:显示为什么需要这个权限的说明

                    LogUtils.i("Displaying important permission rationale to provide additional context.");
                    /*Snackbar.make(mLayout, R.string.permission_important_rationale, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    requestImportantPermissions();
                                }
                            })
                            .show();*/

                    new AlertDialog.Builder(PermissionActivity.this)
                            .setMessage(R.string.permission_important_rationale)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestImportantPermissions();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                } else {
                    //直接获取重要的权限的授权
                    requestImportantPermissions();

                }
            }

        }

        @Override
        public void onPermissionsError(Activity act, int requestCode, int[] grantResults, String errorMsg, String... permissions) {

        }
    };
}
