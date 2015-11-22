package com.android.test.banner.block;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.android.base.block.CommonBlock;
import com.android.base.widget.banner.BGABanner;
import com.android.test.R;
import com.android.test.banner.util.RecycleBitmap;
import com.apkfuns.logutils.LogUtils;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class SplashBlock extends CommonBlock {

    BGABanner mBanner;
    List<View> mViews;
    View mLastView;
    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, Bitmap> mMemoryCache;

    @Override
    public View getRootView() {
        return mActivity.findViewById(R.id.banner_splash_pager);
    }

    @Override
    protected void onCreated() {
        mBanner = (BGABanner) getRootView();
        setLruCache();
        setBanner();
        setData();
    }

    private void setLruCache() {
        // 获取我们应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheMemory)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }

        };
    }

    private void setBanner() {
        mBanner.setTransitionEffect(BGABanner.TransitionEffect.Rotate);
        mBanner.setPageChangeDuration(Integer.MAX_VALUE);
    }

    private void setData() {
        mViews = new ArrayList<>();
        mViews.add(getPageView(R.drawable.guide_1));
        mViews.add(getPageView(R.drawable.guide_2));
        mViews.add(getPageView(R.drawable.guide_3));

        mLastView = mActivity.getLayoutInflater().inflate(R.layout.item_view_last, null);
        setLastPageView((ImageView) mLastView.findViewById(R.id.iv), R.drawable.guide_4);
        mViews.add(mLastView);
        mLastView.findViewById(R.id.btn_last_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        mBanner.setViews(mViews);
    }

    private View getPageView(@DrawableRes int resid) {
        ImageView imageView = new ImageView(mActivity);
        Bitmap bitmp = getBitmapFromMemoryCache(String.valueOf(resid));
        if(bitmp!=null){
            imageView.setImageBitmap(bitmp);
        }else{
            imageView.setImageResource(resid);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bitmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            addBitmapToMemoryCache(String.valueOf(resid), bitmp);
        }
        return imageView;
    }

    private void setLastPageView(ImageView imageView, @DrawableRes int resid) {
        Bitmap bitmp = getBitmapFromMemoryCache(String.valueOf(resid));
        if(bitmp!=null){
            imageView.setImageBitmap(bitmp);
        }else{
            imageView.setImageResource(resid);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bitmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            addBitmapToMemoryCache(String.valueOf(resid), bitmp);
        }
    }

    @Override
    protected void onDestroy() {
        mMemoryCache.evictAll();
        mLastView = null;
        super.onDestroy();
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @param bitmap
     *            LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            LogUtils.e(key);
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
}
