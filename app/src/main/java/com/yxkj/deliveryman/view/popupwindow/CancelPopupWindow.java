package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view.popupwindow
 *  @文件名:   CancelPopupWindow
 *  @创建者:   hhe
 *  @创建时间:  2017/10/27 15:37
 *  @描述：    是否取消完成弹窗
 */
public abstract class CancelPopupWindow extends PopupWindow implements View.OnClickListener {
    private Context mContext;

    public CancelPopupWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private TextView tvCancel;

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_cancel, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        //背景色
        setBackgroundDrawable(new BitmapDrawable());
        //不可点击外面取消?
        setFocusable(true);

        tvCancel = view.findViewById(R.id.tv_cancel_popup_cancel);
        tvCancel.setOnClickListener(this);
    }
}
