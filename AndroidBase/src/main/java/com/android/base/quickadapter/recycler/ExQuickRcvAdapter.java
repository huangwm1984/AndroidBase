package com.android.base.quickadapter.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public abstract class ExQuickRcvAdapter<T> extends BaseRcvQuickAdapter<T, BaseRcvAdapterHelper> {

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    public static final int TYPE_HEADER = 99930;

    public static final int TYPE_FOOTER = 99931;

    public RecyclerView.LayoutManager mLayoutManager;

    public View mHeaderView = null;

    public View mFooterView = null;

    protected ExQuickRcvAdapter(Context context, int layoutResId, RecyclerView.LayoutManager layoutManager) {
        super(context, layoutResId);
        setLayoutManager(layoutManager);
    }

    protected ExQuickRcvAdapter(Context context, int layoutResId, List<T> data, RecyclerView.LayoutManager layoutManager) {
        super(context, layoutResId, data);
        setLayoutManager(layoutManager);
    }

    protected ExQuickRcvAdapter(Context context, MultiItemRcvTypeSupport<T> multiItemTypeSupport, RecyclerView.LayoutManager layoutManager) {
        super(context, multiItemTypeSupport);
        setLayoutManager(layoutManager);
    }

    protected ExQuickRcvAdapter(Context context, MultiItemRcvTypeSupport<T> multiItemTypeSupport, List<T> data, RecyclerView.LayoutManager layoutManager) {
        super(context, multiItemTypeSupport, data);
        setLayoutManager(layoutManager);
    }

    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        mLayoutManager = layoutManager;
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager) mLayoutManager);
            final GridLayoutManager.SpanSizeLookup lookup = gridLayoutManager.getSpanSizeLookup();
            final int spanCount = gridLayoutManager.getSpanCount();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    final int type = getItemViewType(position);
                    if (type == TYPE_HEADER || type == TYPE_FOOTER) {
                        return spanCount;
                    } else {
                        return lookup.getSpanSize(position - getHeaderCount());
                    }
                }
            });
        }
    }

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (mHeaderView != null) {
            headerOrFooter++;
        }
        if (mFooterView != null) {
            headerOrFooter++;
        }
        return super.getItemCount() + headerOrFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return super.getItemViewType(position - getHeaderCount());
        }
    }

    @Override
    public BaseRcvAdapterHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER && mHeaderView != null) {
            return new SimpleViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER && mFooterView != null) {
            return new SimpleViewHolder(mFooterView);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(BaseRcvAdapterHelper helper, int position) {
        final int type = getItemViewType(position);
        if (type != TYPE_HEADER && type != TYPE_FOOTER) {
            super.onBindViewHolder(helper, position - getHeaderCount());
        }
    }

    public void setHeaderView(@NonNull View headerView) {
        mHeaderView = headerView;
        setFulSpan(mHeaderView);
    }

    public void setFooterView(@NonNull View footerView) {
        mFooterView = footerView;
        setFulSpan(mFooterView);
    }

    private void setFulSpan(View view) {
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
        }
        notifyDataSetChanged();
    }

    /**
     * @return recycle的头部视图
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * 得到底部的视图
     */
    public View getFooterView() {
        return mFooterView;
    }

    public void removeHeaderView() {
        mHeaderView = null;
        // notifyItemRemoved(0);如果这里需要做头部的删除动画，
        // 可以复写这个方法，然后进行改写
        notifyDataSetChanged();
    }

    public void removeFooterView() {
        mFooterView = null;
        notifyItemRemoved(getItemCount());
        // 这里因为删除尾部不会影响到前面的pos的改变，所以不用刷新了。
    }

    public int getHeaderCount() {
        return mHeaderView != null ? 1 : 0;
    }

    public int getFooterCount() {
        return mFooterView != null ? 1 : 0;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @NonNull
    private RecyclerView.AdapterDataObserver getObserver() {
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                notifyItemRangeChanged(positionStart + getHeaderCount(), itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int count = getHeaderCount();
                notifyItemRangeInserted(positionStart + count, itemCount);
                notifyItemRangeChanged(positionStart + count + itemCount - 1, getItemCount() - itemCount - positionStart - getFooterCount());
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                int count = getHeaderCount();
                notifyItemRangeRemoved(count + positionStart, itemCount);
                notifyItemRangeChanged(count + positionStart, getItemCount() - count - positionStart - getFooterCount());
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                // TODO: 2015/11/23 还没支持转移的操作
            }
        };
    }

    class SimpleViewHolder extends BaseRcvAdapterHelper {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
