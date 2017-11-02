package com.yxkj.deliveryman.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * APP启动页
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * 等待时间
     */
    private int WAIT_TIME = 1000 * 1;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openNextActivity();
            }
        }, WAIT_TIME);
    }

    /**
     * 根据登录状态打开不同activity
     */
    private void openNextActivity() {
        String token = SharePrefreceHelper.getInstance().getString(SharedKey.TOKEN);
        if (TextUtils.isEmpty(token)) {
            IntentUtil.openActivity(mContext, LoginActivity.class);
        } else {
           // IntentUtil.openActivity(mContext, ContainerManageActivity.class);
            IntentUtil.openActivity(mContext, MainActivity.class);
        }

        finish();
    }
}
