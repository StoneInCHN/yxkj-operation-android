package com.yxkj.deliveryman.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.event.WaitSupAddressEvent;
import com.yxkj.deliveryman.adapter.GoodsCategoryFragmentAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.fragment.WaitSupGoodsFragment;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.GoodsCategoryBean;
import com.yxkj.deliveryman.bean.response.SceneListBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.LogUtil;
import com.yxkj.deliveryman.view.popupwindow.WaitSupAddressPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 待补清单页面
 */
public class WaitSupplementActivity extends BaseActivity {
    @BindView(R.id.tab_wait_sup)
    TabLayout mTablayout;
    @BindView(R.id.vp_wait_sup)
    ViewPager mViewPager;
    @BindView(R.id.tv_spinner)
    TextView mTvSpinner;
    @BindView(R.id.tv_complete_wait_sup)
    TextView mTvComplete;
    @BindView(R.id.tv_current_address_wait_sup)
    TextView mTvCurrentAdress;

    private WaitSupAddressPopupWindow mWaitSupAddressPopupWindow;
    private GoodsCategoryFragmentAdapter mGoodsCategoryFragmentAdapter;
    /**
     * 商品类别
     */
    private List<GoodsCategoryBean.GroupsBean> mGoodsCategoryList = new ArrayList<>();
    /**
     * 缓存中取出来的
     */
    private String tabsRaw;

    @Override
    public int getContentViewId() {
        return R.layout.activity_wait_supplement;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {
        initScene();
        initTabLayout();
        initPopupWindow();
        getWaitSupAdressList();
        getGoodsCategories();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private SceneListBean.GroupsBean mCurrentAdressBean;

    private void initTabLayout() {
        //先从本地缓存取
        setTabsFromCache();
        mGoodsCategoryFragmentAdapter = new GoodsCategoryFragmentAdapter(getSupportFragmentManager(), getFragmentList(), mGoodsCategoryList);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(mGoodsCategoryFragmentAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void initScene() {
        mCurrentAdressBean = new SceneListBean.GroupsBean();
        Bundle data = getIntent().getExtras();
        String sceneSn = "";
        if (data != null) {
            sceneSn = data.getString("sceneSn", "");
        }
        mCurrentAdressBean.sceneSn = sceneSn;
        mCurrentAdressBean.sceneName = "全部商品";
    }

    private List<Fragment> mFragmentList = new ArrayList<>();

    private List<Fragment> getFragmentList() {
        mFragmentList.clear();
        for (GoodsCategoryBean.GroupsBean bean : mGoodsCategoryList) {
            WaitSupGoodsFragment fragment = new WaitSupGoodsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("sceneSn", mCurrentAdressBean.sceneSn);
            bundle.putString("cateId", bean.cateId + "");
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        return mFragmentList;
    }

    private void setTabsFromCache() {
        tabsRaw = SharePrefreceHelper.getInstance().getString(SharedKey.GOODS_CATEGORY);
        if (!TextUtils.isEmpty(tabsRaw)) {
            Gson gson = new Gson();
            GoodsCategoryBean bean = gson.fromJson(tabsRaw, GoodsCategoryBean.class);
            mGoodsCategoryList = bean.groups;
        }
    }

  /*  private void setTabTitles() {
        mTablayout.removeAllTabs();
        Observable.fromIterable(mGoodsCategoryList)
                .subscribe(new Consumer<GoodsCategoryBean.GroupsBean>() {
                    @Override
                    public void accept(@NonNull GoodsCategoryBean.GroupsBean groupsBean) throws Exception {
                        mTablayout.addTab(mTablayout.newTab().setText(groupsBean.cateName));
                    }
                });
    }*/


    private void getGoodsCategories() {
        HttpApi.getInstance()
                .getWaitSupplyGoodsCategoryList(UserInfo.USER_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GoodsCategoryBean>() {
                    @Override
                    protected void onHandleSuccess(GoodsCategoryBean goodsCategoryBean) {
                        //添加一个全部商品类别
                        GoodsCategoryBean.GroupsBean allCategoryBean = new GoodsCategoryBean.GroupsBean();
                        allCategoryBean.cateName = "全部商品";
                        allCategoryBean.cateId = 0;
                        goodsCategoryBean.groups.add(0, allCategoryBean);

                        Gson gson = new Gson();
                        String categoryJson = gson.toJson(goodsCategoryBean);
                        LogUtil.i(TAG, "categoryjson--" + categoryJson);

                        //不相等则存起来
                        if (!tabsRaw.equals(categoryJson)) {
                            mGoodsCategoryList = goodsCategoryBean.groups;
                            SharePrefreceHelper.getInstance().setString(SharedKey.GOODS_CATEGORY, categoryJson);
                            mGoodsCategoryFragmentAdapter.setTitlesAndFragmentList(mGoodsCategoryList, getFragmentList());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void initPopupWindow() {
        mWaitSupAddressPopupWindow = new WaitSupAddressPopupWindow(mContext);
        mWaitSupAddressPopupWindow.setBackgroundDrawable(null);
        mWaitSupAddressPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isAddressWindowOpen = !isAddressWindowOpen;
                Drawable drawableDown = getResources().getDrawable(R.mipmap.triangle_down);
                //必须给drawable设置setBounds
                drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
                mTvSpinner.setCompoundDrawables(null, null, drawableDown, null);
            }
        });
    }

    @Override
    public void setEvent() {
        setOnClick(mTvSpinner, mTvComplete);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_spinner:
                showOrDismissAddressPopup();
                break;
            case R.id.tv_complete_wait_sup:
                doCompleteGot();
                break;
        }
    }

    /**
     * 完成取货
     */
    private void doCompleteGot() {
        finish();


    }

    /**
     * 收到点击选择优享空间事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChooseWaitSupAddress(WaitSupAddressEvent event) {
        mCurrentAdressBean = event.addressBean;
        mTvCurrentAdress.setText(event.addressBean.sceneName);


    }

    /**
     * 显示或隐藏地址选择框
     */

    private boolean isAddressWindowOpen = false;

    private void showOrDismissAddressPopup() {
        Drawable drawableUp = getResources().getDrawable(R.mipmap.triangle_up);
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        if (!isAddressWindowOpen) {//目前是收起状态
            mTvSpinner.setCompoundDrawables(null, null, drawableUp, null);
            mWaitSupAddressPopupWindow.showAsDropDown(mTvSpinner);
            LogUtil.e("TAG", mWaitSupAddressPopupWindow.mAddressAdapter.gettList().toString());
        }

        isAddressWindowOpen = !isAddressWindowOpen;

    }

    /**
     * 获取待补优享空间地点
     */
    public void getWaitSupAdressList() {
        HttpApi.getInstance()
                .getWaitSupplySceneList(UserInfo.USER_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SceneListBean>() {
                    @Override
                    protected void onHandleSuccess(SceneListBean sceneListBean) {
                        SceneListBean.GroupsBean allSceneBean = new SceneListBean.GroupsBean();
                        //添加一个全部地点类别
                        allSceneBean.sceneName = "全部";
                        allSceneBean.sceneSn = "";
                        sceneListBean.groups.add(0, allSceneBean);

                        if (TextUtils.isEmpty(mCurrentAdressBean.sceneSn)) {//默认全部
                            mCurrentAdressBean = sceneListBean.groups.get(0);
                        } else {//有初始值
                            List<SceneListBean.GroupsBean> groups = sceneListBean.groups;
                            for (int i = 0; i < groups.size(); i++) {
                                SceneListBean.GroupsBean bean = groups.get(i);
                                if (bean.sceneSn.equals(mCurrentAdressBean.sceneSn))
                                    mCurrentAdressBean = sceneListBean.groups.get(i);
                            }
                        }

                        mTvCurrentAdress.setText(mCurrentAdressBean.sceneName);

                        mWaitSupAddressPopupWindow.mAddressAdapter.settList(sceneListBean.groups);
                        // mGoodsCategoryFragmentAdapter.setSceneSn(mCurrentAdressBean.sceneSn);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

}
