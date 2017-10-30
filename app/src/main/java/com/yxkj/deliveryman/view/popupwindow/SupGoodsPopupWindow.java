package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.CommonDialogSureListener;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 真正补货商品详细信息
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

    private CommonDialogSureListener mOnClickListener;

    public void setOnClickListener(CommonDialogSureListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.popup_sup_goods_info, null);
        unbinder = ButterKnife.bind(this, view);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onSure();
            }
        });
        //手动设置最大宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(layoutParams.width);
        setHeight(layoutParams.height);
        setContentView(view);
        //背景色
        setBackgroundDrawable(new BitmapDrawable());
        //不可点击外面取消?
        // setFocusable(true);

    }

    private void initData() {
        ImageLoadUtil.loadImage(ivGoodsPic, Constants.BASE_URL + mBean.goodsPic);
        tvGoodsName.setText(mBean.goodsName);
        tvSerialNum.setText("商品条码：" + mBean.goodsSn);
        tvShouldSupNum.setText("总待补数：" + mBean.waitSupplyCount);

    }

    /**
     * 实际补货数量不能大于待补数量
     *
     * @return
     */
    public boolean isActualNumIllegal() {
        String actualNumString = etActualSupNum.getText().toString().trim();
        int actualNum = Integer.parseInt(actualNumString);
        int waitSupNum = mBean.waitSupplyCount;
        return actualNum <= waitSupNum;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }


}
