package com.yxkj.deliveryman.activity;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.WaitSupListAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.popupwindow.WaitSupAddressPopupWindow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 待补清单页面
 */
public class WaitSupplementActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    /*导航栏*/
    private TabLayout tablayout;
    /*导航标签*/
    private String[] tabs = new String[]{"全部", "水饮牛奶", "饼干蛋糕", "美味零食", "香烟"};
    /*待补清单列表*/
    private LRecyclerView recyclerView;
    private TextView tvSpinner;
    private WaitSupListAdapter adapter;
    private String[] entries = new String[]{"全部", "香年广场T3", "美年广场T1", "软件园C2", "软件园E1"};
    private WaitSupAddressPopupWindow waitSupAddressPopupWindow;

    @Override
    public int getContentViewId() {
        return R.layout.activity_wait_supplement;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        tablayout = findViewByIdNoCast(R.id.tablayout);
        recyclerView = findViewByIdNoCast(R.id.recyclerView);
        tvSpinner = findViewByIdNoCast(R.id.tv_spinner);
    }

    @Override
    public void initData() {
        initTabLayout();
        waitSupAddressPopupWindow = new WaitSupAddressPopupWindow(mContext);

        adapter = new WaitSupListAdapter(this);
        adapter.settList(getData());
        RecyclerViewSetUtil.setRecyclerView(this, recyclerView, adapter, true);
    }

    @Override
    public void setEvent() {
        /*设置TabLayout切换监听*/
        tablayout.addOnTabSelectedListener(this);
        setOnClick(tvSpinner);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_spinner:
                showAddressPopup();
                break;
        }
    }

    private void showAddressPopup() {
        waitSupAddressPopupWindow.showAsDropDown(tvSpinner);
        waitSupAddressPopupWindow.setList(entries);
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        Observable.fromArray(tabs).subscribe(tab -> {
            TabLayout.Tab t = tablayout.newTab().setText(tab);
            tablayout.addTab(t);
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + "");
        }
        return list;
    }
}
