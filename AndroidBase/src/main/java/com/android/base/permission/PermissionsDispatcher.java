package com.android.base.permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.android.base.permission.impl.PermissionListener;

/**
 * 权限管理工具类
 *
 */
public final class PermissionsDispatcher {

    private PermissionsDispatcher() {
    }

    /**
     * 检查权限的授权情况<br/>
     * (进行需要权限（不是普通权限）的功能代码时，调用 )<br/>
     * check permissions are whether granted or not
     *
     * @param act
     * @param requestCode
     * @param listener
     * @param permissions
     */
    public static void checkPermissions(Activity act, int requestCode, PermissionListener listener, String... permissions) {
        /****
         * check params
         * 检查参数
         */
        if (act == null) {
            if (listener != null) {
                listener.onPermissionsError(act, requestCode, null, "checkPermissions()-->param act :the activity is null", permissions);
            }
            return;
        }
        if (permissions == null || permissions.length < 1) {
            if (listener != null) {
                listener.onPermissionsError(act, requestCode, null, "checkPermissions()-->param permissions: is null or length is 0", permissions);
            }
            return;
        }

        /*****
         * check permissions are granted ?
         * 开始检查权限的授权情况
         * */
        if (PermissionUtils.hasSelfPermissions(act, permissions)) {

            //已经获得了权限的授权
            //permissions are granted

            if (listener != null) {
                listener.onPermissionsGranted(act, requestCode, null, permissions);
            }
            return;
        } else {

            //没有获得权限的授权
            //permissions are not granted

            if (PermissionUtils.shouldShowRequestPermissionRationale(act, permissions)) {

                //需要显示权限的说明：为什么需要这个权限的授权
                //show the permissions rationale:why your app need their permissions?

                if (listener != null) {
                    listener.onShowRequestPermissionRationale(act, requestCode, true, permissions);
                }
            } else {

                //不需要显示权限的说明
                //don't need to show their permissions rationale

                if (listener != null) {
                    listener.onShowRequestPermissionRationale(act, requestCode, false, permissions);
                }
            }
        }
    }


    /**
     * 请求权限的授权<br/>
     * request permissions to be granted
     *
     * @param act
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissions(Activity act, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(act, permissions, requestCode);
    }


    /**
     * 处理权限授权的结果<br/>
     * do their permissions results
     *
     * @param act
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param listener
     */
    public static void onRequestPermissionsResult(Activity act, int requestCode, String[] permissions, int[] grantResults, PermissionListener listener) {

        /****
         * check params
         * 检查参数
         */
        if (act == null) {
            if (listener != null) {
                listener.onPermissionsError(act, requestCode, grantResults, "onRequestPermissionsResult()-->param act :the activity is null", permissions);
            }
            return;
        }

        /****
         * 检查是否所有的权限已经授权
         * check whether all permissions are granted
         */

        if (PermissionUtils.verifyPermissions(grantResults)) {

            // all permissions are granted

            if (listener != null) {
                listener.onPermissionsGranted(act, requestCode, grantResults, permissions);
            }
        } else {
            if (listener != null) {
                listener.onPermissionsDenied(act, requestCode, grantResults, permissions);
            }
        }
    }


}
