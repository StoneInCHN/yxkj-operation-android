package com.yxkj.deliveryman.frament;


import android.view.Gravity;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.SupRecordActivity;
import com.yxkj.deliveryman.activity.WaitSupplementActivity;
import com.yxkj.deliveryman.adapter.SupplementAdapter;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.callback.MainPageClickListener;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.response.WaitSupStateBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.DisplayUtil;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.RichToolBar;
import com.yxkj.deliveryman.view.popupwindow.MainPagePopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    /**
     * 页码
     */
    private int mPageNum = 1;

    /*补货列表适配器*/
    private SupplementAdapter mSupplementAdapter;

    @Override
    protected int getResource() {
        return R.layout.fragment_container;
    }

    @Override
    protected void beforeInitView() {

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
        mSupplementAdapter = new SupplementAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mSupplementAdapter);
        mLrv.setLoadMoreEnabled(false);


        getWaitSupplyState();

    }

    private void getWaitSupplyState() {
        String id = SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID);
        HttpApi.getInstance()
                .getWaitSupplyState(id, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupStateBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupStateBean waitSupStateBean) {
                        updateView(waitSupStateBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

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
        mSupplementAdapter.setScenesBeanList(waitSupStateBean.scenes);

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View view) {
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i + "");
        }
        return list;
    }

    @Override
    public void onTvreplenish() {
        IntentUtil.openActivity(getActivity(), WaitSupplementActivity.class);
    }

    @Override
    public void onTvreplenishRecord() {
        IntentUtil.openActivity(getActivity(), SupRecordActivity.class);
    }
}
