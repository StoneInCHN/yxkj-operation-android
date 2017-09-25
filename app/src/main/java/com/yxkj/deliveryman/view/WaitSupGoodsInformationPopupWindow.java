package com.yxkj.deliveryman.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.WaitSupInfoListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 待补货物信息
 */

public class WaitSupGoodsInformationPopupWindow extends PopupWindow {

    private Context context;
    /*商品图片*/
    private ImageView img_goods;
    /*商品名称*/
    private TextView tv_goods_name;
    /*商品编号*/
    private TextView tv_goods_num;
    /*待补信息列表*/
    private RecyclerView recyclerView_info;
    /*总待补数*/
    private TextView tv_all_wait_sup;
    /*实际取货数*/
    private EditText et_true_num;
    /*确定按钮*/
    private TextView tv_sure;
    /*适配器*/
    private WaitSupInfoListAdapter adapter;

    public WaitSupGoodsInformationPopupWindow(Context context) {
        this(context, null);
    }

    public WaitSupGoodsInformationPopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaitSupGoodsInformationPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        initAdapter();
    }


    private void init() {
        View view = View.inflate(context, R.layout.view_wait_sup_info, null);
        img_goods = view.findViewById(R.id.img_goods);
        tv_goods_name = view.findViewById(R.id.tv_goods_name);
        tv_goods_num = view.findViewById(R.id.tv_goods_num);
        recyclerView_info = view.findViewById(R.id.recyclerView_info);
        tv_all_wait_sup = view.findViewById(R.id.tv_all_wait_sup);
        et_true_num = view.findViewById(R.id.et_true_num);
        tv_sure = view.findViewById(R.id.tv_sure);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        tv_sure.setOnClickListener(view1 -> dismiss());
    }

    private void initAdapter() {
        adapter = new WaitSupInfoListAdapter(context);
        adapter.settList(getData());
        recyclerView_info.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView_info.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i + "");
        }
        return list;
    }

}
