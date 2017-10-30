package com.yxkj.deliveryman.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter基类
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int TYPE_HEADER = 0;//type为0表示为headerView

    public static final int TYPE_NORMAL = 1;//type为1表示为正常的item

    protected Context context;
    //adapte 加载的数据
    public List<T> tList = new ArrayList<T>();

    //添加headerView
    private View headerView;

    /**
     * Listener
     */
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void settList(List<T> tList) {
        this.tList = tList;
        notifyDataSetChanged();
    }

    public void setMoreList(List<T> list) {
        if (tList == null) {
            tList = new ArrayList<T>();
        }
        this.tList.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> gettList() {
        return tList;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null && viewType == TYPE_HEADER) {
            return new BaseViewHolder(headerView, context);
        }
        return new BaseViewHolder(LayoutInflater.from(context).inflate(getLayoutId(), parent, false), context);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {

        if (headerView != null && holder.getLayoutPosition() == 0)
            return;

        if (tList != null) {
            int realPos = getRealPosition(holder);
            if (realPos < tList.size()) {
                final T bean = tList.get(realPos);
                onCorvert(holder, realPos, bean);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(position, bean);
                        }
                    }
                });

            }

        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }


    @Override
    public int getItemCount() {

        if (headerView != null)
            return tList == null ? 1 : tList.size() + 1;

        return tList == null ? 0 : tList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    int SpanSize = layoutManager.getSpanCount();//
                    return getItemViewType(position) == 0 ? SpanSize : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == 0) {
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
        }

    }

    public abstract int getLayoutId();

    public abstract void onCorvert(BaseViewHolder holder, int position, T bean);


    /**
     * 处理item点击监听
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
