package com.yxkj.deliveryman.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.util.NetConnectUtil;

import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreateTime： 2017/8/25
 * @Describe:
 * @Author： 曾强
 */
public abstract class BaseFragment extends RxFragment implements View.OnClickListener {
    protected String TAG = getClass().getSimpleName();
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getResource(), null);
            ButterKnife.bind(this, rootView);
            beforeInitView();
            initView(rootView);
            initData();
            setEvent();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }


    protected <T extends View> T findViewByIdNoCast(int id) {
        return rootView == null ? null : (T) rootView.findViewById(id);
    }

    protected abstract int getResource();

    protected abstract void beforeInitView();

    protected abstract void initView(View rootView);

    protected abstract void initData();

    protected abstract void setEvent();

    /**
     * 根据id设置点击事件
     */
    public void setOnClick(Object... objects) {
        for (Object object : objects) {
            if (object instanceof View) {
                ((View) object).setOnClickListener(this);
            }
            if (object instanceof Integer) {
                findViewByIdNoCast((Integer) object).setOnClickListener(this);
            }

        }
    }

    /**
     * 线程调度
     */
    protected <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return (observable) -> {
            return observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe((disposable) -> {
                        // 可添加网络连接判断等
                        if (!NetConnectUtil.isNetworkAvailable(getActivity())) {
                            Toast.makeText(getActivity(), R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycle);

        };
    }
}
