package com.android.test.view.pulltorefresh.recycler.widget;

import android.view.LayoutInflater;
import android.view.View;

import com.android.base.block.UiBlock;
import com.android.test.R;
import com.android.test.view.banner.block.SimpleImageBanner;
import com.android.test.view.banner.entity.BannerModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/26 0026.
 */
public class HeaderBanner extends UiBlock {

    public SimpleImageBanner mBanner;
    public List<BannerModel> mData;

    private String[] imageUrls = {
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Override
    public int getRootViewId() {
        return 0;
    }

    @Override
    public View resetRootView(View oldRootView, LayoutInflater inflater) {
        return inflater.inflate(R.layout.act_banner_main, null);
    }

    @Override
    public void onViewCreated() {
        setData();
        mBanner = (SimpleImageBanner) getRootView().findViewById(R.id.banner_main_default);
        mBanner.setSource(mData);
        mBanner.startScroll();
    }

    private void setData() {
        mData = new ArrayList<>();
        for(int i=0;i<imageUrls.length;i++){
            BannerModel bm = new BannerModel();
            bm.setImageUrl(imageUrls[i]);
            bm.setTips("这是第" + i + "页");
            mData.add(bm);
        }
    }

    @Override
    public void onDestroy() {
        Glide.get(mActivity).clearMemory();
        super.onDestroy();
    }
}
