package com.yxkj.deliveryman.frament;


import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.MessageAdapter;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页消息
 */
public class MessageFragment extends BaseFragment {
    /*消息列表*/
    private LRecyclerView mLrv;
    private MessageAdapter messageAdapter;

    @Override
    protected int getResource() {
        return R.layout.fragment_message;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView(View rootView) {
        mLrv = findViewByIdNoCast(R.id.lrv_fragment_message);
    }

    @Override
    protected void initData() {
        messageAdapter = new MessageAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, messageAdapter, true);
//        mLrv.setLoadMoreFooter(new LoadingFooter(getActivity()));
//        mLrv.setFooterViewHint("加载中", "没有更多了", "网络出错");
//        mLrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //mLrv.setLoadMoreEnabled(false);
        messageAdapter.settList(getData());
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(i + "");
        }
        return list;
    }
}
