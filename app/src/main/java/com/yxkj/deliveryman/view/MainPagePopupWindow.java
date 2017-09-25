package com.yxkj.deliveryman.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.MainPageClickListener;

/**
 * 首页PopupWindow
 */

public class MainPagePopupWindow extends PopupWindow {
    private Context mContext;
    private TextView tv_replenish;
    private TextView tv_replenish_record;
    private MainPageClickListener clickListener;

    public void setClickListener(MainPageClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MainPagePopupWindow(Context context) {
        this(context, null);
    }

    public MainPagePopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainPagePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.view_main_page_pop, null);
        tv_replenish = view.findViewById(R.id.tv_replenish);
        tv_replenish_record = view.findViewById(R.id.tv_replenish_record);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        tv_replenish.setOnClickListener(view1 -> {
            if (clickListener != null) {
                clickListener.onTvreplenish();
            }
        });
        tv_replenish_record.setOnClickListener(view1 -> {
            if (clickListener != null) {
                clickListener.onTvreplenishRecord();
            }
        });
    }
}
