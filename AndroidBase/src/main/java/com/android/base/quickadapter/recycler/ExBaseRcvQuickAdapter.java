package com.android.base.quickadapter.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public abstract class ExBaseRcvQuickAdapter<T> extends BaseRcvQuickAdapter<T, BaseRcvAdapterHelper> {

    public View customHeaderView = null;

    public View customFooterView = null;

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    public class VIEW_TYPES {

        public static final int HEADER = 7;

        public static final int FOOTER = 8;
    }

    class SimpleViewHolder extends BaseRcvAdapterHelper {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected ExBaseRcvQuickAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    protected ExBaseRcvQuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }

    protected ExBaseRcvQuickAdapter(Context context, MultiItemRcvTypeSupport<T> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }

    protected ExBaseRcvQuickAdapter(Context context, MultiItemRcvTypeSupport<T> multiItemTypeSupport, List<T> data) {
        super(context, multiItemTypeSupport, data);
    }

    @Override
    public BaseRcvAdapterHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.HEADER && customHeaderView != null) {
            return new SimpleViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.FOOTER && customFooterView != null) {
            return new SimpleViewHolder(customFooterView);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (customHeaderView != null) {
            headerOrFooter++;
        }
        if (customFooterView != null) {
            headerOrFooter++;
        }
        return super.getItemCount() + headerOrFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (customFooterView != null && position == getItemCount() - 1) {
            return VIEW_TYPES.FOOTER;
        } else if (customHeaderView != null && position == 0) {
            return VIEW_TYPES.HEADER;
        } else {
            if (customHeaderView != null) {
                return super.getItemViewType(position - 1);
            }
            return super.getItemViewType(position);
        }
    }

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(BaseRcvAdapterHelper helper, int position) {

        if ((customHeaderView != null && position == 0) || (customFooterView != null && position == getItemCount() - 1)) {
            // 如果是header或者是footer则不处理
        } else {
            if (customHeaderView != null) {
                position--;
            }
            super.onBindViewHolder(helper, position);
        }
    }
}
