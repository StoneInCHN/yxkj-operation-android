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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.LackGoodsInfoAdapter;
import com.yxkj.deliveryman.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成补货弹窗
 */

public class CompleteSupPopWindow extends PopupWindow {
    private Context context;
    private RecyclerView recyclerView;//补货完成的图片列表
    private RecyclerView recyclerView_rest;//缺货情况
    private TextView tv_complete;//完成补货
    private List<Bitmap> bitmaps = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private PhotoAdapter adapter;
    private LackGoodsInfoAdapter lackAdapter;

    public void setBitmaps(Bitmap bitmap) {
        this.bitmaps.add(bitmaps.size() - 1, bitmap);
        adapter.settList(bitmaps);
    }

    public void setList(List<String> list) {
        this.list = list;
        lackAdapter.settList(this.list);
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
        bitmaps.add(null);
        init();
        initAdapter();
    }

    private void initAdapter() {
        adapter = new PhotoAdapter(context);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(adapter);
        lackAdapter = new LackGoodsInfoAdapter(context);
        recyclerView_rest.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_rest.setAdapter(lackAdapter);
    }

    private void init() {
        View v = View.inflate(context, R.layout.view_complete_sup, null);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView_rest = v.findViewById(R.id.recyclerView_rest);
        tv_complete = v.findViewById(R.id.tv_complete);
        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(l.width);
        setHeight(l.height);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setContentView(v);
    }
}
