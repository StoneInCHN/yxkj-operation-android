package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.SceneListBean;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   WaitSupAddressAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/18 14:59
 *  @描述：    补货清单地址list
 */
public class WaitSupAddressAdapter extends BaseRecyclerViewAdapter<SceneListBean.GroupsBean> {
    public WaitSupAddressAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_wait_sup_address;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, SceneListBean.GroupsBean bean) {
        TextView tvAddress = holder.getView(R.id.tv_address_item_wait_up);
        tvAddress.setText(bean.sceneName);
    }
}
