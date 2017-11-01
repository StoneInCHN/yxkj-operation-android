package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.view.View;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.bean.response.ControllerVolume;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.view.VolumeBar;
import com.yxkj.deliveryman.view.dialog.TextButtonDialog;
import com.yxkj.deliveryman.view.dialog.TextProgressbarDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 中控管理
 */
public class ControllerManageActivity extends BaseActivity {

    @BindView(R.id.volume_bar)
    VolumeBar mVolumeBar;

    /**
     * 当前音量,
     */
    @IntRange(from = 0, to = 10)
    private int currentVolume;

    @Override
    public int getContentViewId() {
        return R.layout.activity_controller_manage;
    }

    /**
     * 设备编号
     */
    private String deviceNo;

    @Override
    public void beforeInitView() {
        deviceNo = getIntent().getExtras().getString("deviceNo");
        // TODO: 2017/11/1 仅测试用
        deviceNo = "863010031227460";

    }

    @Override
    public void initView() {
        mVolumeBar.setOnBarFlingListener(new VolumeBar.OnBarFlingListener() {
            @Override
            public void onFling(int volume) {
                currentVolume = volume;
                updateVolume();
            }
        });
    }

    @Override
    public void initData() {
        getCurrentVolume();
    }

    private void getCurrentVolume() {
        HttpApi.getInstance()
                .getCurrentVolume(deviceNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ControllerVolume>() {
                    @Override
                    protected void onHandleSuccess(ControllerVolume controllerVolume) {
                        int volume = Integer.parseInt(controllerVolume.volume);
                        currentVolume = volume / 10;
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void setEvent() {

    }

    private TextProgressbarDialog mTextProgressbarDialog;


    private void rebootController() {
        HttpApi.getInstance()
                .rebootSystem(deviceNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean nullBean) {
                        mTextProgressbarDialog.dismiss();
                        new TextButtonDialog(mContext, "", "重启完成", "确定").show();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.iv_decrease_volume, R.id.iv_increase_volume, R.id.tv_reboot_controller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_decrease_volume:
                currentVolume--;
                updateVolume();
                break;
            case R.id.iv_increase_volume:
                currentVolume++;
                updateVolume();
                break;
            case R.id.tv_reboot_controller:
                mTextProgressbarDialog = new TextProgressbarDialog(mContext);
                mTextProgressbarDialog.show();
                rebootController();
                break;
        }
    }

    private void updateVolume() {
        if (currentVolume < 0) {
            currentVolume = 0;
        }
        if (currentVolume > 10) {
            currentVolume = 10;
        }
        HttpApi.getInstance().
                updateAudioVolume(deviceNo, currentVolume * 10 + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean nullBean) {
                        mVolumeBar.updateProgress(currentVolume);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    @Override
    public void onClick(View v) {

    }
}
