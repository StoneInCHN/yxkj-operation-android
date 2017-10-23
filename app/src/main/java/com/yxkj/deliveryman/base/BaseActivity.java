package com.yxkj.deliveryman.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.tools.AppManager;
import com.yxkj.deliveryman.tools.SystemStatusManager;
import com.yxkj.deliveryman.util.NetConnectUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreateTime： 2017/8/25
 * @Describe:
 * @Author： 曾强
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    protected boolean useDefaultTitleBarColor;//状态栏颜色是否使用默认颜色
    protected Context mContext = this;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            //被杀死之后重新创建
            getSaveData(savedInstanceState);
        }
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        initBase();
        beforeInitView();
        if (useDefaultTitleBarColor) {
            //改变状态栏颜色;注意：此处一旦设置 android:fitsSystemWindows="false"将无效
            setTitleBarColor(R.color.colorAccent);
        }
        initView();
        initData();
        setEvent();
    }

    /**
     * 线程调度
     */
    protected <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return (observable) -> {
            return observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> {
                                // 可添加网络连接判断等
                                if (!NetConnectUtil.isNetworkAvailable(BaseActivity.this)) {
                                    Toast.makeText(BaseActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(lifecycle);
        };
    }


    /**
     * 通用的一些基本设置
     */
    private void initBase() {
        //如果存在actionBar，就隐藏(也可以通过主题AppTheme.NoActionBar隐藏)
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //将新建的activity添加到stack里进行管理
        AppManager.getInstance().addActivity(this);

        //设置状态栏透明
        setTranslucentStatus();

        useDefaultTitleBarColor = true;

    }

    /**
     * 设置rootView据顶部为通知栏的高度
     */
    public void setRootPadding(View view) {
//        getWindow().getDecorView().setPadding(0, MyApplication.statusHeight,0,0);
//        if (view != null) {
//            view.setPadding(0, MyApplication.statusHeight, 0, 0);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //此处activity被杀死可以保存数据
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 获取activity被杀死时候保存的数据
     *
     * @param savedInstanceState
     */
    public void getSaveData(Bundle savedInstanceState) {

    }


    public abstract int getContentViewId();//放layoutId

    /**
     * 初始化View之前做的事
     */
    public void beforeInitView() {

    }

    public abstract void initView();//初始化View

    public abstract void initData();//初始化数据

    public abstract void setEvent();//设置事件

    /**
     * 状态栏透明只有Android 4.4 以上才支持
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    public void setTitleBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
    }

    /**
     * 不用强制转换的findviewbyid（本案例使用了注解此处可以忽略）
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewByIdNoCast(int id) {
        return (T) findViewById(id);
    }

    public void setOnClick(Object... objects) {

        for (Object object : objects) {
            if (object instanceof View) {
                ((View) object).setOnClickListener(this);
            }
            if (object instanceof Integer) {
                findViewById((Integer) object).setOnClickListener(this);
            }

        }
    }


    /**
     * 本段代码用来处理如果输入法还显示的话就消失掉输入键盘
     */
    protected void dismissSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    protected void showKeyboard(View view) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().remove(this);
        //注：unregister官方是放入到onStop方法中，真实开发一般是放入onDestroy即当被销毁才取消注册
//        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        super.onDestroy();
    }

}
