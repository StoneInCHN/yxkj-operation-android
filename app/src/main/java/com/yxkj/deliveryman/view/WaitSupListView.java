package com.yxkj.deliveryman.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.view.popupwindow.WaitSupGoodsInfoPopupWindow;

/**
 * 待补清单Item
 */

public class WaitSupListView extends LinearLayout {
    /*标为未取货*/
    private TextView tv_not_recieve;
    /*未取货数量*/
    private TextView tv_rest;
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
        View view = View.inflate(getContext(), R.layout.view_wait_sup, null);
        tv_not_recieve = view.findViewById(R.id.tv_not_recieve);
        tv_rest = view.findViewById(R.id.tv_rest);
        addView(view);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv_not_recieve.setVisibility(VISIBLE);
                tv_not_recieve.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_rest.setVisibility(VISIBLE);
                        tv_not_recieve.setVisibility(GONE);
                    }
                });
                return false;
            }
        });
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
//                    WaitSupGoodsInfoPopupWindow popupWindow = new WaitSupGoodsInfoPopupWindow(getContext(), (WaitSupGoodsListBean) data);
//                    popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, 0);
                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
