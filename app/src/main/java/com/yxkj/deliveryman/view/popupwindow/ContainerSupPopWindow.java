package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerManageActivity;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * 首页补货货柜信息
 */

public class ContainerSupPopWindow extends PopupWindow {
    private Context context;
    /*开始补货*/
    private TextView tv_start;
    /*取消*/
    private ImageView img_back;

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
        View v = View.inflate(context, R.layout.view_container_sup, null);
        tv_start = v.findViewById(R.id.tv_start);
        img_back = v.findViewById(R.id.img_back);
        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(l.width);
        setHeight(l.height);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(v);
        tv_start.setOnClickListener(view -> {
            IntentUtil.openActivity(context, ContainerManageActivity.class);
            dismiss();
        });
        img_back.setOnClickListener(view -> dismiss());
    }
}
