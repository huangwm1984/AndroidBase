package com.hwm.test.download.test.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.hwm.test.download.bizs.DLInfo;

import java.io.File;

/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class Utils {

    /*
    * check the app is installed
    */
    public static boolean isAppInstalled(Context context, String packagename)
    {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            //System.out.println("没有安装");
            return false;
        }else{
            //System.out.println("已经安装");
            return true;
        }
    }

    /**
     * 启动第三方应用
     * @param context
     * @param packageName
     */
    public static void startThirdApp(Context context, String packageName){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null)
            context.startActivity(intent);
    }

    /**
     * 计算下载百分比
     *
     * @param info
     * @return
     */
    public static int getProgress(DLInfo info) {
        double result = 0;
        if (info != null && info.totalBytes != 0) {
            result = (info.currentBytes * 1.00 / info.totalBytes) * 100;
        }
        return (int) result;
    }

    /**
     * 文件是否存在
     * @param file
     * @return
     */
    public static Boolean isFileExist(File file){
        if(file == null){
            return false;
        }
        return file.exists();
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public static Boolean deleteFile(File file){
        if(file == null){
            return false;
        }
        return file.delete();
    }

    /**
     * 安装应用
     *
     * @param file
     */
    public static void installApp(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        // 如果仅是简单的startActivity(intent),会造成onCreate()再执行一次。
        //((Activity) context).startActivityForResult(intent, 100);
        context.startActivity(intent);
    }
}
