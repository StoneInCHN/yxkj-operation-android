package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 * 补货拍照图片适配器
 */

public class PhotoAdapter extends BaseRecyclerViewAdapter<Bitmap> {

    public PhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_photo;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, Bitmap bean) {
        if (bean != null) {
            ((ImageView) holder.getView(R.id.img_photo)).setImageBitmap(bean);
        }else {
            ((ImageView) holder.getView(R.id.img_photo)).setImageResource(R.color.colorPrimary);
        }
        holder.setOnClickListener(R.id.img_del, view -> {
            tList.remove(position);
            notifyDataSetChanged();
        });
    }
}
