package com.android.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Listview嵌套在SrcollView中会出现显示不全的情况
 * <p>
 * 这个类通过设置不滚动来避免
 * 
 */
public class NoScrollListview extends ListView {
	
	public NoScrollListview(Context context) {
		super(context);
	}

	public NoScrollListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	        
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
	
}
