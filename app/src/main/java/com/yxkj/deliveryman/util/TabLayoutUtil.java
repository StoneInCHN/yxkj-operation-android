package com.yxkj.deliveryman.util;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 项目名：  yxkj-operation-android
 * 包名：    com.yxkj.deliveryman.util
 * 文件名:   TabLayoutUtil
 * 创建者:   hhe
 * 创建时间:  2017/11/7 15:08
 * 描述：    tablayout修改
 */
public class TabLayoutUtil {

    /**
     * 利用反射修改tablayout的下划线宽度为包裹文字宽度 ,需在添加完tab之后使用
     *
     * @param tabs target
     */
    public static void setIndicator(TabLayout tabs) {
        try {
            Class<?> tabLayout = tabs.getClass();
            Field tabStrip = null;
            tabStrip = tabLayout.getDeclaredField("mTabStrip");

            tabStrip.setAccessible(true);
            LinearLayout llTab = null;
            llTab = (LinearLayout) tabStrip.get(tabs);

            //左右margin为10dp
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                Field mTextViewField = child.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);
                TextView tabTextView = (TextView) mTextViewField.get(child);
                tabTextView.setPadding(0, 0, 0, 0);
                int tvWidth = tabTextView.getWidth();
                if (tvWidth == 0) {
                    tabTextView.measure(0, 0);
                    tvWidth = tabTextView.getMeasuredWidth();
                }
                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 child的宽度来设置的
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.width = tvWidth;
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);


                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
