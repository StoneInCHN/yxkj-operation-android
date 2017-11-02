package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

/**
 * 首页补货货柜信息
 * @author hhe
 */

public abstract class AbstractStartSupPopWindow extends PopupWindow implements View.OnClickListener {
    private String mContainerName;
    private String mSceneName;
    private Context context;
    /*开始补货*/
    private TextView mTvStart;
    private TextView mTvCancel;


    public AbstractStartSupPopWindow(Context context, String sceneName, String containerName) {
        this.context = context;
        mSceneName = sceneName;
        mContainerName = containerName;
        init();
    }

    private void init() {
        View rootView = View.inflate(context, R.layout.dialog_start_sup, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mTvStart = rootView.findViewById(R.id.tv_start_sup_dialog);
        mTvCancel = rootView.findViewById(R.id.tv_cancel_sup_dialog);
        TextView tvTitle = rootView.findViewById(R.id.tv_title_dialog_start_sup);
        TextView tvContainerName = rootView.findViewById(R.id.tv_container_name_dialog_start_sup);


        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(l.width);
        setHeight(l.height);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(rootView);
        tvTitle.setText(mSceneName);
        tvContainerName.setText(String.format("对%s进行补货", mContainerName));

        mTvStart.setOnClickListener(this);
        mTvCancel.setOnClickListener(view -> dismiss());
    }
}
