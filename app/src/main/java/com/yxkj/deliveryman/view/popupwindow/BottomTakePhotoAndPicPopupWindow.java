package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

import butterknife.ButterKnife;

/**
 * 底部拍照相册弹窗
 */

public class BottomTakePhotoAndPicPopupWindow extends PopupWindow {
    private Context mContext;
    private TextView tvTakePhoto;
    private TextView tvGoAlbum;
    private TextView tvCancel;

    public BottomTakePhotoAndPicPopupWindow(Context context) {
        this(context, null);
    }

    public BottomTakePhotoAndPicPopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTakePhotoAndPicPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.popup_take_photo, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(view);

        tvTakePhoto = view.findViewById(R.id.tv_take_photo);
        tvGoAlbum = view.findViewById(R.id.tv_go_album_popup_take_photo);
        tvCancel = view.findViewById(R.id.tv_cancel_popup_take_photo);

        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTakePhotoListener.onTakePhto();
            }
        });

        tvGoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTakePhotoListener.onGetFromAlbum();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnTakePhotoListener {
        void onTakePhto();

        void onGetFromAlbum();
    }

    private OnTakePhotoListener mOnTakePhotoListener;

    public void setOnTakePhotoListener(OnTakePhotoListener onTakePhotoListener) {
        mOnTakePhotoListener = onTakePhotoListener;
    }
}
