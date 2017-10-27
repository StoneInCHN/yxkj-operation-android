package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerManageActivity;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * 首页补货货柜信息
 */

public abstract class ContainerSupPopWindow extends PopupWindow implements View.OnClickListener{
    private Context context;
    /*开始补货*/
    private TextView tv_start;
    private TextView tv_cancel;

    public ContainerSupPopWindow(Context context) {
        this(context, null);
    }

    public ContainerSupPopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContainerSupPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View rootView = View.inflate(context, R.layout.view_sup_dialog, null);
        tv_start = rootView.findViewById(R.id.tv_start_sup_dialog);
        tv_cancel = rootView.findViewById(R.id.tv_cancel_sup_dialog);

        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(l.width);
        setHeight(l.height);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(rootView);
        tv_start.setOnClickListener(this);
        tv_cancel.setOnClickListener(view -> dismiss());
    }
}
