package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.WaitSupScenesAdapter;
import com.yxkj.deliveryman.callback.OnCommon1Listener;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsDetailBean;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.util.EditTextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 待取货物详细信息
 */
public class WaitPickGoodsInfoPopupWindow extends PopupWindow {

    private Context mContext;
    @BindView(R.id.iv_goods_sup_info)
    ImageView ivGoodsPic;
    @BindView(R.id.tv_goods_name_sup_info)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_num_sup_info)
    TextView tvSerialNum;
    @BindView(R.id.rv_scenes_sup_info)
    RecyclerView rvScenes;
    @BindView(R.id.tv_should_sup_num_sup_info)
    TextView tvShouldSupNum;
    @BindView(R.id.et_actual_sup_num_sum_info)
    EditText etActualSupNum;
    @BindView(R.id.tv_confirm_sum_info)
    TextView tvConfirm;

    private WaitSupScenesAdapter mWaitSupInfoListAdapter;
    private Unbinder unbinder;

    private WaitSupGoodsListBean.GroupsBean mSupGoodsListBean;

    public WaitPickGoodsInfoPopupWindow(Context context, WaitSupGoodsListBean.GroupsBean groupsBean) {
        super(context);
        mContext = context;
        mSupGoodsListBean = groupsBean;
        initView();
        initListener();
        initAdapter();
        getGoodsInfo();
    }

    private OnCommon1Listener<Integer> mOnCommon1Listener;

    public void setOnCommon1Listener(OnCommon1Listener<Integer> onCommon1Listener) {
        mOnCommon1Listener = onCommon1Listener;
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.popup_wait_pick_goods_info, null);
        unbinder = ButterKnife.bind(this, view);

        //手动设置最大宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        //背景色
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);

    }

    private void initListener() {
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultNum = 0;
                String result = etActualSupNum.getText().toString();
                if (TextUtils.isEmpty(result)) {//不填则返回hint值
                    result = etActualSupNum.getHint().toString();
                }
                resultNum = Integer.parseInt(result);
                if (resultNum > shouldSumCount) {
                    resultNum = shouldSumCount;
                }
                mOnCommon1Listener.onCommon1(resultNum);
            }
        });

        etActualSupNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    s = "0";
                }
                int inputValue = Integer.parseInt(s.toString());
                if (inputValue > shouldSumCount) {
                    etActualSupNum.setText(shouldSumCount + "");
                    EditTextUtil.setEditTextSelectionEnd(etActualSupNum);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initAdapter() {
        mWaitSupInfoListAdapter = new WaitSupScenesAdapter(mContext);
        rvScenes.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvScenes.setAdapter(mWaitSupInfoListAdapter);
    }

    /**
     * 应补数量
     */
    private int shouldSumCount;

    private void getGoodsInfo() {
        HttpApi.getInstance()
                .getWaitSupplyGoodsDetails(UserInfo.USER_ID, mSupGoodsListBean.goodsSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupGoodsDetailBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupGoodsDetailBean waitSupGoodsDetailBean) {
                        mWaitSupInfoListAdapter.settList(waitSupGoodsDetailBean.sceneCountList);
                        tvGoodsName.setText(waitSupGoodsDetailBean.goodsName);
                        shouldSumCount = waitSupGoodsDetailBean.sumCount;
                        tvSerialNum.setText("商品条码:" + waitSupGoodsDetailBean.goodsSn);
                        tvShouldSupNum.setText("总待补数:" + shouldSumCount);
                        etActualSupNum.setHint(shouldSumCount + "");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }

}
