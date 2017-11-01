package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.OnCommon1Listener;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view.popupwindow
 *  @文件名:   CancelPopupWindow
 *  @创建者:   hhe
 *  @创建时间:  2017/10/27 15:37
 *  @描述：    是否取消完成弹窗/出货测试
 */
public class CancelPopupWindow extends PopupWindow {
    private Context mContext;

    private TextView tvCancel;

    public CancelPopupWindow(Context context, String text) {
        super(context);
        mContext = context;
        initView();

        tvCancel.setText(text);
    }

    private OnCommon1Listener mOnCommon1Listener;

    public void setOnCommon1Listener(OnCommon1Listener onCommon1Listener) {
        mOnCommon1Listener = onCommon1Listener;
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_cancel, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        //背景色
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);

        tvCancel = view.findViewById(R.id.tv_cancel_popup_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCommon1Listener.onCommon1("");
            }
        });
    }
}
