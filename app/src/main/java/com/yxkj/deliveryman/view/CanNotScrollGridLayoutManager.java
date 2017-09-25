package com.yxkj.deliveryman.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * 禁止滑动
 */

public class CanNotScrollGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;

    public CanNotScrollGridLayoutManager(Context context, int num) {
        super(context, num);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
