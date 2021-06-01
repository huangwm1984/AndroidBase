package com.android.base.util.diskcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

/**
 * Created by zhy on 15/7/28.
 */
public class DiskLruCacheHelper {
    private static final String DIR_NAME = "diskCache";
    private static final int MAX_COUNT = 5 * 1024 * 1024;
    private static final int DEFAULT_APP_VERSION = 1;

    /**
     * The default valueCount when open DiskLruCache.
     */
    private static final int DEFAULT_VALUE_COUNT = 1;

    private static final String TAG = "DiskLruCacheHelper";

    private DiskLruCache mDiskLruCache;

    public DiskLruCacheHelper(Context context) throws IOException {
        mDiskLruCache = generateCache(context, DIR_NAME, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, String dirName) throws IOException {
        mDiskLruCache = generateCache(context, dirName, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, String dirName, int maxCount) throws IOException {
        mDiskLruCache = generateCache(context, dirName, maxCount);
    }

    //custom cache dir
    public DiskLruCacheHelper(File dir) throws IOException {
        mDiskLruCache = generateCache(null, dir, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, File dir) throws IOException {
        mDiskLruCache = generateCache(context, dir, MAX_COUNT);
    }

    public DiskLruCacheHelper(Context context, File dir, int maxCount) throws IOException {
        mDiskLruCache = generateCache(context, dir, maxCount);
    }

    private DiskLruCache generateCache(Context context, File dir, int maxCount) throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException(
                    dir + " is not a directory or does not exists. ");
        }

        int appVersion = context == null ? DEFAULT_APP_VERSION : Utils.getAppVersion(context);

        DiskLruCache diskLruCache = DiskLruCache.open(
                dir,
                appVersion,
                DEFAULT_VALUE_COUNT,
                maxCount);

        return diskLruCache;
    }

    private DiskLruCache generateCache(Context context, String dirName, int maxCount) throws IOException {
        DiskLruCache diskLruCache = DiskLruCache.open(
                getDiskCacheDir(context, dirName),
                Utils.getAppVersion(context),
                DEFAULT_VALUE_COUNT,
                maxCount);
        return diskLruCache;
    }
    // =======================================
    // ============== String 数据 读写 =============
    // =======================================

    public void put(String key, String value) {
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        try {
            edit = editor(key);
            if (edit == null) return;
            OutputStream os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(value);
            edit.commit();//write CLEAN
        } catch (IOException e) {
            e.printStackTrace();
            try {
                //s
                edit.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAsString(String key) {
        InputStream inputStream = null;
        inputStream = get(key);
        if (inputStream == null) return null;
        String str = null;
        try {
            str = Util.readFully(new InputStreamReader(inputStream, Util.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return str;
    }


    public void put(String key, JSONObject jsonObject) {
        put(key, jsonObject.toString());
    }

    public JSONObject getAsJson(String key) {
        String val = getAsString(key);
        try {
            if (val != null)
                return new JSONObject(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // =======================================
    // ============ JSONArray 数据 读写 =============
    // =======================================

    public void put(String key, JSONArray jsonArray) {
        put(key, jsonArray.toString());
    }

    public JSONArray getAsJSONArray(String key) {
        String JSONString = getAsString(key);
        try {
            JSONArray obj = new JSONArray(JSONString);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // =======================================
    // ============== byte 数据 读写 =============
    // =======================================

    /**
     * 保存 byte数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的数据
     */
    public void put(String key, byte[] value) {
        OutputStream out = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = editor(key);
            if (editor == null) {
                return;
            }
            out = editor.newOutputStream(0);
            out.write(value);
            out.flush();
            editor.commit();//write CLEAN
        } catch (Exception e) {
            e.printStackTrace();
            try {
                editor.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public byte[] getAsBytes(String key) {
        byte[] res = null;
        InputStream is = get(key);
        if (is == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[256];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================
    public void put(String key, Serializable value) {
        DiskLruCache.Editor editor = editor(key);
        ObjectOutputStream oos = null;
        if (editor == null) return;
        try {
            OutputStream os = editor.newOutputStream(0);
            oos = new ObjectOutputStream(os);
            oos.writeObject(value);
            oos.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T getAsSerializable(String key) {
        T t = null;
        InputStream is = get(key);
        ObjectInputStream ois = null;
        if (is == null) return null;
        try {
            ois = new ObjectInputStream(is);
            t = (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    // =======================================
    // ============== bitmap 数据 读写 =============
    // =======================================
    public void put(String key, Bitmap bitmap) {
        put(key, Utils.bitmap2Bytes(bitmap));
    }

    public Bitmap getAsBitmap(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) return null;
        return Utils.bytes2Bitmap(bytes);
    }

    // =======================================
    // ============= drawable 数据 读写 =============
    // =======================================
    public void put(String key, Drawable value) {
        put(key, Utils.drawable2Bitmap(value));
    }

    public Drawable getAsDrawable(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) {
            return null;
        }
        return Utils.bitmap2Drawable(Utils.bytes2Bitmap(bytes));
    }

    // =======================================
    // ============= other methods =============
    // =======================================
    public boolean remove(String key) {
        try {
            key = Utils.hashKeyForDisk(key);
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws IOException {
        mDiskLruCache.close();
    }

    public void delete() throws IOException {
        mDiskLruCache.delete();
    }

    public void flush() throws IOException {
        mDiskLruCache.flush();
    }

    public boolean isClosed() {
        return mDiskLruCache.isClosed();
    }

    public long size() {
        return mDiskLruCache.size();
    }

    public void setMaxSize(long maxSize) {
        mDiskLruCache.setMaxSize(maxSize);
    }

    public File getDirectory() {
        return mDiskLruCache.getDirectory();
    }

    public long getMaxSize() {
        return mDiskLruCache.getMaxSize();
    }


    // =======================================
    // ===遇到文件比较大的，可以直接通过流读写 =====
    // =======================================
    //basic editor
    public DiskLruCache.Editor editor(String key) {
        try {
            key = Utils.hashKeyForDisk(key);
            //wirte DIRTY
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            //edit maybe null :the entry is editing
            if (edit == null) {
                Log.w(TAG, "the entry spcified key:" + key + " is editing by other . ");
            }
            return edit;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    //basic get
    public InputStream get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(Utils.hashKeyForDisk(key));
            if (snapshot == null) //not find entry , or entry.readable = false
            {
                Log.e(TAG, "not find entry , or entry.readable = false");
                return null;
            }
            //write READ
            return snapshot.getInputStream(0);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

}



