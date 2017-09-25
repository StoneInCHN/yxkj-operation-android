package com.yxkj.deliveryman.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;

/**
 * 重新获取验证码
 */

public class GetCodePopWindow extends PopupWindow {
    private Context context;
    private TextView tv_get_code;
    private TextView tv_cancle;
    private CommenDialogSureListener commenDialogSureListener;

    public void setCommenDialogSureListener(CommenDialogSureListener commenDialogSureListener) {
        this.commenDialogSureListener = commenDialogSureListener;
    }

    public GetCodePopWindow(Context context) {
        this(context, null);
    }

    public GetCodePopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GetCodePopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.view_get_code, null);
        tv_get_code = view.findViewById(R.id.tv_get_code);
        tv_cancle = view.findViewById(R.id.tv_cancle);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        tv_cancle.setOnClickListener(view1 -> {
            dismiss();
        });
        tv_get_code.setOnClickListener(view1 -> {
            dismiss();
            if (commenDialogSureListener != null) {
                commenDialogSureListener.onSure();
            }
        });
    }
}
