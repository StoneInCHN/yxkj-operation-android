package com.yxkj.deliveryman.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.frament.ContainerFragment;
import com.yxkj.deliveryman.frament.MessageFragment;
import com.yxkj.deliveryman.frament.MineFragment;

import io.reactivex.Observable;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    /* tab文字*/
    private String[] titles = {"货柜", "消息", "我的"};
    /*首页三个个页面*/
    private Class fragments[] = {ContainerFragment.class, MessageFragment.class, MineFragment.class};

    private FragmentTabHost tabHost;

    private TabWidget tabWidget;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        tabHost = findViewByIdNoCast(android.R.id.tabhost);
    }

    @Override
    public void initData() {
        tabHost.setup(this, getSupportFragmentManager(), R.id.layout_fragments);
        tabWidget = tabHost.getTabWidget();
        tabWidget.setDividerDrawable(null);
        Observable.range(0, 3).subscribe(n -> tabHost.addTab(tabHost.newTabSpec(titles[n]).setIndicator(getItemView(n)), fragments[n], null));

    }

    @Override
    public void setEvent() {
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    public View getItemView(int position) {
        View view = View.inflate(this, R.layout.tab, null);
        TextView tab_title = view.findViewById(R.id.tab_title);
        ImageView tab_icon = view.findViewById(R.id.tab_icon);
        tab_title.setText(titles[position]);
        if (position == 0) {
            tab_title.setTextColor(Color.GREEN);
        } else {
            tab_title.setTextColor(Color.GRAY);
        }

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            View view = tabWidget.getChildAt(i);
            TextView tab_title = view.findViewById(R.id.tab_title);
            ImageView tab_icon = view.findViewById(R.id.tab_icon);
            String text = tab_title.getText().toString();
            if (tabId.equals(text)) {
                tab_title.setTextColor(Color.GREEN);
            } else {
                tab_title.setTextColor(Color.GRAY);
            }
        }
    }
}
