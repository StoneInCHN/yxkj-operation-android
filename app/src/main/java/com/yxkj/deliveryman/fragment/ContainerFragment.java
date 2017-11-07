package com.yxkj.deliveryman.fragment;


import android.view.Gravity;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.SupRecordActivity;
import com.yxkj.deliveryman.activity.WaitSupplementActivity;
import com.yxkj.deliveryman.adapter.AddressSupAdapter;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.event.RefreshContainerEvent;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.callback.MainPageClickListener;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;
import com.yxkj.deliveryman.util.DisplayUtil;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.NetErrorView;
import com.yxkj.deliveryman.view.RichToolBar;
import com.yxkj.deliveryman.view.dialog.CustomProgressDialog;
import com.yxkj.deliveryman.view.popupwindow.MainPagePopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页货柜页
 */
public class ContainerFragment extends BaseFragment implements MainPageClickListener {
    /**
     * 补货列表
     */
    @BindView(R.id.lrv_fragment_container)
    LRecyclerView mLrv;
    @BindView(R.id.rtb_fragment_container)
    RichToolBar mToolBar;
    @BindView(R.id.nev_fragment_container)
    NetErrorView mNetErrorView;

    /**
     * 页码
     */
    private int mPageNum = 1;

    /*补货列表适配器*/
    private AddressSupAdapter mAddressSupAdapter;

    @Override
    protected int getResource() {
        return R.layout.fragment_container;
    }

    @Override
    protected void beforeInitView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().removeStickyEvent(RefreshContainerEvent.class);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView(View rootView) {
        mToolBar.setOnViewClickListener(new RichToolBar.OnViewClickListener() {
            @Override
            public void onClickRight() {
                showPopupWindow();
            }

        });
    }

    private void showPopupWindow() {
        MainPagePopupWindow pagePopupWindow = new MainPagePopupWindow(getActivity());
        pagePopupWindow.setClickListener(this);
        int offX = DisplayUtil.dip2px(getActivity(), 10);
        int offY = mToolBar.getHeight() + DisplayUtil.getStatusBarHeight(getActivity());
        //弹出位置用绝对位置来控制
        pagePopupWindow.showAtLocation(mToolBar, Gravity.RIGHT | Gravity.TOP, offX, offY);
    }

    @Override
    protected void initData() {
        //按大楼分的list
        mAddressSupAdapter = new AddressSupAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mAddressSupAdapter);
        mLrv.setFooterViewHint("加载中", "没有更多了", "网络出了一点问题");
        mLrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum = 1;
                getWaitSupplyState();
            }
        });

        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getWaitSupplyState();
            }
        });

        mNetErrorView.setOnRetryListener(new NetErrorView.OnRetryListener() {
            @Override
            public void onRetry() {
                getWaitSupplyState();
            }
        });

        getWaitSupplyState();

    }

    private void getWaitSupplyState() {
        HttpApi.getInstance()
                .getWaitSupplyState(UserInfo.USER_ID, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupStateBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupStateBean waitSupStateBean) {
                        updateView(waitSupStateBean);
                        mNetErrorView.dismiss();
                        mLrv.refreshComplete(10);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        // CustomProgressDialog.getInstance(getActivity()).show();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mLrv.refreshComplete(10);
                        mNetErrorView.show();
                    }

                    @Override
                    public void onComplete() {
                        //  CustomProgressDialog.getInstance(getActivity()).dismiss();
                    }
                });

    }

    /**
     * 更新数据
     *
     * @param waitSupStateBean
     */
    private void updateView(WaitSupStateBean waitSupStateBean) {
        String title = String.format(Locale.SIMPLIFIED_CHINESE, "货柜（%d）", waitSupStateBean.waitSupplySumCount);
        mToolBar.setTitle(title);
        if (mPageNum == 1) {
            mAddressSupAdapter.mScenesBeanList.clear();
        }
        mAddressSupAdapter.setMoreList(waitSupStateBean.scenes);

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View view) {
    }


    @Override
    public void onClickWaitSup() {
        IntentUtil.openActivity(getActivity(), WaitSupplementActivity.class);
    }

    @Override
    public void onClickSupRecord() {
        IntentUtil.openActivity(getActivity(), SupRecordActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void refreshContainer(RefreshContainerEvent event) {
        mPageNum = 1;
        getWaitSupplyState();
    }
}
