package com.yxkj.deliveryman.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

/**
 * 待补清单Item
 */

public class WaitSupListView extends LinearLayout {
    /*判断线是否显示*/
    private boolean isShow;
    private float touchDownX;
    private int TouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private float moveX;
    private long downTime;

    public WaitSupListView(Context context) {
        this(context, null);
    }

    public WaitSupListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitSupListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_wait_sup, null);
        addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                moveX = 0;
                downTime = System.currentTimeMillis();
            case MotionEvent.ACTION_MOVE:
                moveX += event.getX() - touchDownX;
                if (Math.abs(moveX) >= TouchSlop) { //移动
                    if (moveX > 100) {
                        if (!isShow) {
                            isShow = true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                long upTime = System.currentTimeMillis();
                if (Math.abs(moveX) < TouchSlop && upTime - downTime < 1000) {
                    // TODO: 2017/10/26 待处理
//                    WaitPickGoodsInfoPopupWindow popupWindow = new WaitPickGoodsInfoPopupWindow(getContext(), (WaitSupGoodsListBean) data);
//                    popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, 0);
                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
