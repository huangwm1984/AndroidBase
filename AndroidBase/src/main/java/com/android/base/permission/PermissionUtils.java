package com.android.base.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

/***
 * 权限工具类
 * @author
 */
public class PermissionUtils {

    private PermissionUtils() {
    }

    /**
     * 检查授权结果数组<br/>
     * Checks all given permissions have been granted.
     *
     * @param grantResults results 授权结果数组
     * @return returns true if all permissions have been granted.
     */
    public static boolean verifyPermissions(int... grantResults) {
        // At least one result must be checked.
        if(grantResults == null || grantResults.length < 1){
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限数组是否已经全部获取授权<br/>
     * Returns true if the Activity or Fragment has access to all given permissions.
     *
     * @param context     context
     * @param permissions permission list 权限数组
     * @return returns true if the Activity or Fragment has access to all given permissions.
     */
    public static boolean hasSelfPermissions(Context context, String... permissions) {
        if(context == null){
            return true;
        }
        if(permissions == null || permissions.length < 1){
            return true;
        }
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限数组是否需要显示使用权限的说明<br/>
     * Checks given permissions are needed to show rationale.
     *
     * @param activity    activity
     * @param permissions permission list 权限数组
     * @return returns true if one of the permission is needed to show rationale.
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        if(activity == null){
            return false;
        }
        if(permissions == null || permissions.length < 1){
            return false;
        }

        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

}
