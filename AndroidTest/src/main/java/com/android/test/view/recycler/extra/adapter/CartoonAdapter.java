package com.android.test.view.recycler.extra.adapter;

import android.widget.ImageView;

import com.android.base.block.SampleBlock;
import com.android.base.quickadapter.recycler.BaseRcvAdapterHelper;
import com.android.base.quickadapter.recycler.ExBaseRcvQuickAdapter;
import com.android.base.widget.DynamicHeightImageView;
import com.android.test.R;
import com.android.test.view.recycler.extra.entity.TestDataBean.DataEntity.ObjectListEntity;
import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class CartoonAdapter extends SampleBlock {

    public ExBaseRcvQuickAdapter mQuickRcvAdapter;

    @Override
    protected void onCreated() {

    }

    public void setAdapter(List<ObjectListEntity> data){

        if(mQuickRcvAdapter!=null){
            mQuickRcvAdapter.replaceAll(data);
            return;
        }

        mQuickRcvAdapter = new ExBaseRcvQuickAdapter<ObjectListEntity>(mActivity, R.layout.item_waterfall, data) {

            @Override
            protected void convert(BaseRcvAdapterHelper helper, ObjectListEntity item) {

                float picRatio = (float) item.getPhoto().getHeight() / item.getPhoto().getWidth();
                DynamicHeightImageView contentImageView = helper.getView(R.id.wf_item_content_DraweeView);
                Glide.with(mActivity)
                        .load(item.getPhoto().getPath())
                        .crossFade()
                                //.placeholder(R.drawable.default_head_pic)
                        .into(contentImageView);
                contentImageView.setHeightRatio(picRatio);
                ImageView userImageView = helper.getView(R.id.wf_item_user_head_draweeView);
                helper.setText(R.id.wf_item_description_textView, item.getMsg());
                Glide.with(mActivity)
                        .load("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg")
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(mActivity))
                        .into(userImageView);
                helper.setText(R.id.wf_item_positon_textView, "No." + helper.getLayoutPosition());
            }
        };

    }


}
