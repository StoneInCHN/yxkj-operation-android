package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.LackGoodsInfoAdapter;
import com.yxkj.deliveryman.adapter.PhotoAdapter;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.util.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成补货弹窗
 */

public class CompleteSupPopWindow extends PopupWindow {
    private Context context;
    private ImageView ivGoodsPic;
    private TextView tvRestartTakePhoto;
    private TextView tvCompleteSup;

    public void setBitmaps(Bitmap bitmap) {
        ImageLoadUtil.loadImageWithBitmap(ivGoodsPic, bitmap);
    }

    public CompleteSupPopWindow(Context context) {
        this(context, null);
    }

    public CompleteSupPopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompleteSupPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    private void initView() {
        View view = View.inflate(context, R.layout.popup_complete_sup, null);
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
    }
}
