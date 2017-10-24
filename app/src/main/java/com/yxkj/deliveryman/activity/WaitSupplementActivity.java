package com.yxkj.deliveryman.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.GoodsCategoryPagerAdapter;
import com.yxkj.deliveryman.adapter.WaitSupListAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.response.GoodsCategoryBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.popupwindow.WaitSupAddressPopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 待补清单页面
 */
public class WaitSupplementActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    /*导航栏*/
    private TabLayout tablayout;
    /*导航标签*/
    private String[] tabs;
    /*待补清单列表*/
    private LRecyclerView recyclerView;
    private TextView tvSpinner;
    private WaitSupListAdapter adapter;
    private WaitSupAddressPopupWindow waitSupAddressPopupWindow;

    private ViewPager mViewPager;
    /**
     * 商品类别
     */
    private List<GoodsCategoryBean.GroupsBean> goodsCategoryList;

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
        mViewPager = findViewByIdNoCast(R.id.vp_wait_sup);
    }

    @Override
    public void initData() {
        initTabLayout();
        initViewpager();
        initPopupWindow();
        initRv();

        getGoodsCategories();

    }

    private GoodsCategoryPagerAdapter mGoodsCategoryPagerAdapter;

    private void initViewpager() {
        mGoodsCategoryPagerAdapter = new GoodsCategoryPagerAdapter();
        mViewPager.setAdapter(mGoodsCategoryPagerAdapter);
    }

    private void getGoodsCategories() {
        HttpApi.getInstance()
                .getWaitSupplyGoodsCategoryList(SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GoodsCategoryBean>() {
                    @Override
                    protected void onHandleSuccess(GoodsCategoryBean goodsCategoryBean) {
                        goodsCategoryList = goodsCategoryBean.groups;
                        tabs = new String[goodsCategoryList.size()];
                        // TODO: 2017/10/24 改成rxjava处理
                        StringBuilder tabSaved = new StringBuilder();
                        for (int i = 0; i < goodsCategoryList.size(); i++) {
                            GoodsCategoryBean.GroupsBean bean = goodsCategoryList.get(i);
                            tabs[i] = bean.cateName;
                            tabSaved.append(bean.cateName);
                            if (i != goodsCategoryList.size() - 1) {
                                tabSaved.append("|");
                            }
                        }
                        SharePrefreceHelper.getInstance().setString(SharedKey.GOODS_CATEGORY, tabSaved.toString());
                        setTabs();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void initRv() {
        adapter = new WaitSupListAdapter(this);
        adapter.settList(getData());
        RecyclerViewSetUtil.setRecyclerView(this, recyclerView, adapter, true);
    }

    private void initPopupWindow() {
        waitSupAddressPopupWindow = new WaitSupAddressPopupWindow(mContext);
        waitSupAddressPopupWindow.setBackgroundDrawable(null);
        waitSupAddressPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isAddressWindowOpen = !isAddressWindowOpen;
                Drawable drawableDown = getResources().getDrawable(R.mipmap.triangle_down);
                //必须给drawable设置setBounds
                drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
                tvSpinner.setCompoundDrawables(null, null, drawableDown, null);
            }
        });
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
                showOrDismissAddressPopup();
                break;
        }
    }

    /**
     * 显示或隐藏地址选择框
     */

    private boolean isAddressWindowOpen = false;

    private void showOrDismissAddressPopup() {
        Drawable drawableUp = getResources().getDrawable(R.mipmap.triangle_up);
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        if (!isAddressWindowOpen) {//目前是收起状态
            tvSpinner.setCompoundDrawables(null, null, drawableUp, null);
            waitSupAddressPopupWindow.showAsDropDown(tvSpinner);
            waitSupAddressPopupWindow.getWaitSupList();
        }

        isAddressWindowOpen = !isAddressWindowOpen;

    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        String tabRaw = SharePrefreceHelper.getInstance().getString(SharedKey.GOODS_CATEGORY);
        if (TextUtils.isEmpty(tabRaw)) {
            return;
        }

        tabs = tabRaw.split("\\|");
        setTabs();
        tablayout.setupWithViewPager(mViewPager);
    }

    private void setTabs() {
        tablayout.removeAllTabs();
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
