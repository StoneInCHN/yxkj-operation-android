package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ShowBigImageActivity;
import com.yxkj.deliveryman.callback.OnCommon2Listener;
import com.yxkj.deliveryman.util.BitmapUtil;
import com.yxkj.deliveryman.util.ImageLoadUtil;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.LogUtil;

import java.io.File;

/**
 * 完成补货弹窗
 */

public class CompleteSupPopWindow extends PopupWindow {
    private Context mContext;
    private ImageView ivGoodsPic;
    private TextView tvRestartTakePhoto;
    private TextView tvCompleteSup;
    private String filePath;

    public void setPicFilePath(String path) {
        filePath = path;
        ImageLoadUtil.loadImageWithNoCache(ivGoodsPic, path);
    }


    public CompleteSupPopWindow(Context context) {
        this.mContext = context;
        initView();
    }

    private OnCommon2Listener<String, String> mCommon2Listener;

    public void setCommon2Listener(OnCommon2Listener common2Listener) {
        mCommon2Listener = common2Listener;
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.popup_complete_sup, null);
        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(l.width);
        setHeight(l.height);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(view);

        ivGoodsPic = view.findViewById(R.id.iv_goods_pic_popup_complete_sup);
        tvRestartTakePhoto = view.findViewById(R.id.tv_restart_take_photo_popup_complete_sup);
        tvCompleteSup = view.findViewById(R.id.tv_complete_sup_popup_complete_sup);
        ImageView ivCancel = view.findViewById(R.id.iv_cancel_popup_complete_sup);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvRestartTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommon2Listener.onCommon1("");
            }
        });

        tvCompleteSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommon2Listener.onCommon2(filePath);
            }
        });


        ivGoodsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("pic_path", filePath);
                IntentUtil.openActivity(mContext, ShowBigImageActivity.class, bundle);
            }
        });
    }

}
