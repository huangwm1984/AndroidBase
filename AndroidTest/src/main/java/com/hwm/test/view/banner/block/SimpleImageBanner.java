package com.hwm.test.view.banner.block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.common.assist.Check;
import com.android.base.widget.banner.BaseBanner;
import com.hwm.test.R;
import com.hwm.test.view.banner.entity.BannerModel;
import com.bumptech.glide.Glide;


/**
 * Created by Administrator on 2015/11/23 0023.
 */
public class SimpleImageBanner extends BaseBanner<BannerModel> {

    Context mContext;

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final BannerModel item = mData.get(position);
        tv.setText(item.getTips());
    }

    @Override
    public View onCreateItemView(int position) {
        /*View inflate = View.inflate(context, R.layout.adapter_simple_image, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);

        final BannerItem item = list.get(position);
        int itemWidth = dm.widthPixels;
        int itemHeight = (int) (itemWidth * 360 * 1.0f / 640);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

        String imgUrl = item.imgUrl;

        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context)
                    .load(imgUrl)
                    .override(itemWidth, itemHeight)
                    .centerCrop()
                    .placeholder(colorDrawable)
                    .into(iv);
        } else {
            iv.setImageDrawable(colorDrawable);
        }*/
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        final BannerModel item = mData.get(position);
        String imgUrl = item.getImageUrl();
        if (!Check.isEmpty(imgUrl)) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .placeholder(R.drawable.default_viewpager_pic)
                    .into(iv);
        } else {
            iv.setImageResource(R.drawable.default_viewpager_pic);
        }

        return iv;
    }


}
