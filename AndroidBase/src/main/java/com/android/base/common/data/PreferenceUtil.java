package com.android.base.common.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.base.common.Log;
import com.android.base.common.data.cipher.Cipher;
import com.android.base.common.utils.ByteUtil;
import com.android.base.common.utils.HexUtil;

/**
 * Created by Administrator on 2015/9/23 0023.
 */
public class PreferenceUtil {

    public static final  String KEY_PK_HOME = "msg_pk_home";
    public static final  String KEY_PK_NEW  = "msg_pk_new";
    private static final String TAG         = PreferenceUtil.class.getSimpleName();


    /**
     * *************** get ******************
     */

    public static String get(Context context, String fileName, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static boolean get(Context context, String fileName, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static float get(Context context, String fileName, String key, float defValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static int getInt(Context context, String fileName, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static long get(Context context, String fileName, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static Object get(Context context, String fileName, String key) {
        return get(context, fileName, key, (Cipher) null);
    }

    public static Object get(Context context, String fileName, String key, Cipher cipher) {
        try {
            String hex = get(context, fileName, key, (String) null);
            if (hex == null) return null;
            byte[] bytes = HexUtil.decodeHex(hex.toCharArray());
            if (cipher != null) bytes = cipher.decrypt(bytes);
            Object obj = ByteUtil.byteToObject(bytes);
            Log.i(TAG, key + " get: " + obj);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * *************** put ******************
     */
    public static void put(Context context, String fileName, String key, Object ser) {
        put(context, fileName, key, ser, null);
    }

    public static void put(Context context, String fileName, String key, Object ser, Cipher cipher) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        try {
            Log.i(TAG, key + " put: " + ser);
            if (ser == null) {
                sp.edit().remove(key).commit();
            } else {
                byte[] bytes = ByteUtil.objectToByte(ser);
                if (cipher != null) bytes = cipher.encrypt(bytes);
                put(context, fileName, key, HexUtil.encodeHexStr(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put(Context context, String fileName, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public static void put(Context context, String fileName, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static void put(Context context, String fileName, String key, float value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putFloat(key, value).commit();
    }

    public static void put(Context context, String fileName, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }

    public static void putInt(Context context, String fileName, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
}
