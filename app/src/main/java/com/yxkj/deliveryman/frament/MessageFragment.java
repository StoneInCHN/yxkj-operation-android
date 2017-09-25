package com.yxkj.deliveryman.frament;


import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
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
    private LRecyclerView recyclerView;
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
        recyclerView = findViewByIdNoCast(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        messageAdapter = new MessageAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), recyclerView, messageAdapter);
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
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        return list;
    }
}
