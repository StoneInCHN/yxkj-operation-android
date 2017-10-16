package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;

/**
 * 底部弹窗
 */

public class BotomPopWindow extends PopupWindow {
    private Context context;
    private Button btn_photograph;
    private Button btn_cancle;
    private CommenDialogSureListener commenDialogSureListener;

    public BotomPopWindow(Context context) {
        this(context, null);
    }

    public BotomPopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BotomPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setCommenDialogSureListener(CommenDialogSureListener commenDialogSureListener) {
        this.commenDialogSureListener = commenDialogSureListener;
    }

    private void init() {
        View v = View.inflate(context, R.layout.view_botom, null);
        btn_photograph = v.findViewById(R.id.btn_photograph);
        btn_cancle = v.findViewById(R.id.btn_cancle);
        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(l.width);
        setHeight(l.height);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(v);
        btn_photograph.setOnClickListener(view -> {
            dismiss();
            if (commenDialogSureListener != null) {
                commenDialogSureListener.onSure();
            }
        });
        btn_cancle.setOnClickListener(view -> dismiss());
    }

}
