package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.yxkj.deliveryman.callback.CommonDialogSureListener;
import com.yxkj.deliveryman.callback.OnCommon1Listener;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 补货商品详细信息
 */
public class SupGoodsPopupWindow extends PopupWindow {

    private Context mContext;
    @BindView(R.id.iv_goods_sup_info)
    ImageView ivGoodsPic;
    @BindView(R.id.tv_goods_name_sup_info)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_num_sup_info)
    TextView tvSerialNum;
    @BindView(R.id.tv_should_sup_num_sup_info)
    TextView tvShouldSupNum;
    @BindView(R.id.et_actual_sup_num_sum_info)
    EditText etActualSupNum;
    @BindView(R.id.tv_confirm_sum_info)
    TextView tvConfirm;

    private Unbinder unbinder;

    private WaitSupContainerGoodsBean.GroupsBean mBean;

    public SupGoodsPopupWindow(Context context, WaitSupContainerGoodsBean.GroupsBean groupsBean) {
        super(context);
        mContext = context;
        mBean = groupsBean;
        initView();
        initData();
    }

    private OnCommon1Listener<Integer> mOnCommon1Listener;


    public void setOnCommon1Listener(OnCommon1Listener<Integer> onCommon1Listener) {
        mOnCommon1Listener = onCommon1Listener;
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.popup_sup_goods_info, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        unbinder = ButterKnife.bind(this, view);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actualNumString = etActualSupNum.getText().toString().trim();
                if (TextUtils.isEmpty(actualNumString)) {
                    actualNumString = etActualSupNum.getHint().toString().trim();
                }
                int actualNum = Integer.parseInt(actualNumString);
                mOnCommon1Listener.onCommon1(actualNum);
            }
        });
        //手动设置最大宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        //背景色
        setBackgroundDrawable(new BitmapDrawable());
        //获取焦点，如果不设置则edittext可能无法弹出键盘
        setFocusable(true);

    }

    private void initData() {
        ImageLoadUtil.loadImage(ivGoodsPic, Constants.BASE_URL + mBean.goodsPic);
        tvGoodsName.setText(mBean.goodsName);
        tvSerialNum.setText("商品条码：" + mBean.goodsSn);
        tvShouldSupNum.setText("总待补数：" + mBean.waitSupplyCount);


        etActualSupNum.setHint(mBean.waitSupplyCount + "");

        etActualSupNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                int sInt = Integer.parseInt(s.toString());
                if (sInt > mBean.waitSupplyCount) {
                    etActualSupNum.setText(mBean.remainCount + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }


}
