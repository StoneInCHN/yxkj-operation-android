package com.yxkj.deliveryman.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yxkj.deliveryman.R;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;

/**
 * @CreateTime： 2017/9/6
 * @Describe:
 * @Author： 曾强
 */

public class RecyclerViewSetUtil {
    public static void setRecyclerView(Context context, LRecyclerView recyclerView, RecyclerView.Adapter adapter) {
        setRecyclerView(context, recyclerView, adapter, false);
    }

    public static void setRecyclerView(Context context, LRecyclerView recyclerView, RecyclerView.Adapter adapter, boolean isLine) {
        //设置动画
        AnimationAdapter mAdapter = new AlphaInAnimationAdapter(adapter);
        mAdapter.setFirstOnly(false);
        mAdapter.setDuration(500);
        //设置LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setLayoutManager(new GridLayoutManager(context,5));
        //设置adpter
        recyclerView.setAdapter(new LRecyclerViewAdapter(mAdapter));
        //设置分割线
        if (isLine) {
            recyclerView.addItemDecoration(SuperDividerUtil.newShapeDivider().setStartSkipCount(1).setEndSkipCount(0));
        }
        //设置头部加载颜色
        recyclerView.setHeaderViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);
        //设置底部加载颜色
        recyclerView.setFooterViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);
    }
}
