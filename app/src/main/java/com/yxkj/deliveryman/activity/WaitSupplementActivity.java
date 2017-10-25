package com.yxkj.deliveryman.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.event.WaitSupAddressEvent;
import com.yxkj.deliveryman.adapter.GoodsCategoryPagerAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.response.GoodsCategoryBean;
import com.yxkj.deliveryman.response.SceneListBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.LogUtil;
import com.yxkj.deliveryman.view.popupwindow.WaitSupAddressPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private GoodsCategoryPagerAdapter mGoodsCategoryPagerAdapter;
    /**
     * 商品类别
     */
    private List<GoodsCategoryBean.GroupsBean> mGoodsCategoryList;
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
        initTabLayout();
        initPopupWindow();
        getWaitSupAdresses();
        getGoodsCategories();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initTabLayout() {
        mGoodsCategoryPagerAdapter = new GoodsCategoryPagerAdapter(mContext);
        mViewPager.setAdapter(mGoodsCategoryPagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);

        //先从本地缓存取
        tabsRaw = SharePrefreceHelper.getInstance().getString(SharedKey.GOODS_CATEGORY);
        if (!TextUtils.isEmpty(tabsRaw)) {
            Gson gson = new Gson();
            GoodsCategoryBean bean = gson.fromJson(tabsRaw, GoodsCategoryBean.class);
            mGoodsCategoryList = bean.groups;
            mGoodsCategoryPagerAdapter.setList(mGoodsCategoryList);
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
                .getWaitSupplyGoodsCategoryList(SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GoodsCategoryBean>() {
                    @Override
                    protected void onHandleSuccess(GoodsCategoryBean goodsCategoryBean) {
                        Gson gson = new Gson();
                        String categoryJson = gson.toJson(goodsCategoryBean);
                        LogUtil.i(TAG, "categoryjson--" + categoryJson);

                        mGoodsCategoryList = goodsCategoryBean.groups;
                        //不相等则存起来
                        if (!tabsRaw.equals(categoryJson)) {
                            SharePrefreceHelper.getInstance().setString(SharedKey.GOODS_CATEGORY, categoryJson);
                            mGoodsCategoryPagerAdapter.setList(mGoodsCategoryList);
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
                // TODO: 2017/10/25 完成取货
                break;
        }
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

    private SceneListBean.GroupsBean mCurrentAdressBean;

    public void getWaitSupAdresses() {
        HttpApi.getInstance()
                .getWaitSupplySceneList(SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SceneListBean>() {
                    @Override
                    protected void onHandleSuccess(SceneListBean sceneListBean) {
                        mCurrentAdressBean = sceneListBean.groups.get(0);
                        mTvCurrentAdress.setText(mCurrentAdressBean.sceneName);
                        mWaitSupAddressPopupWindow.mAddressAdapter.settList(sceneListBean.groups);
                        mGoodsCategoryPagerAdapter.setSceneSn(mCurrentAdressBean.sceneSn);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

}
