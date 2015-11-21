package com.android.test.banner.block;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.android.base.block.CommonBlock;
import com.android.base.common.assist.Toastor;
import com.android.base.widget.DynamicHeightImageView;
import com.android.base.widget.banner.BGABanner;
import com.android.test.R;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class BannerBlock extends CommonBlock {

    public BGABanner mBanner;
    public List<View> mDefaultViews;

    private String[] imageUrls = {
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Override
    public View getRootView() {
        return mActivity.findViewById(R.id.banner_main_default);
    }

    @Override
    protected void onCreated() {
        mBanner = (BGABanner) getRootView();
        mDefaultViews = getViews(imageUrls.length);
        mBanner.setViews(mDefaultViews);
        for(int i=0;i<mDefaultViews.size();i++){
            Glide.with(mActivity)
                    .load(imageUrls[i])
                    .crossFade()
                    .placeholder(R.drawable.default_viewpager_pic)
                    .into((ImageView) mDefaultViews.get(i)).getSize(new SizeReadyCallback() {
                @Override
                public void onSizeReady(int width, int height) {

                }
            });
        }

        mBanner.setImageViewListener(new BGABanner.ImageViewListener() {
            @Override
            public void onImageViewOnClick(int position, View view) {
                Toastor.showSingletonToast(mActivity.getApplicationContext(), "你点了中了第" + position + "个");
            }
        });
    }

    @Override
    protected void onDestroy() {
        Glide.get(mActivity).clearMemory();
        super.onDestroy();
    }

    private List<View> getViews(int count) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add(mActivity.getLayoutInflater().inflate(R.layout.item_view_image, null).findViewById(R.id.my_image_view));
        }
        return views;
    }
}
