package com.yxkj.deliveryman.activity;

import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.ContainerSupRecordAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 某个货柜某次补货记录
 */
public class ContainerSupRecordActivity extends BaseActivity {
    private LRecyclerView recyclerView;
    private ContainerSupRecordAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_container_sup_record;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        recyclerView = findViewByIdNoCast(R.id.recyclerView);
    }

    @Override
    public void initData() {
        adapter = new ContainerSupRecordAdapter(this);
        adapter.settList(getData());
        RecyclerViewSetUtil.setRecyclerView(this, recyclerView, adapter);

    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + "");
        }
        return list;
    }
}
