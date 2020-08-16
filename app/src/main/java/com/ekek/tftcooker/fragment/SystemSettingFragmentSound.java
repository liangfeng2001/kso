package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.common.SoundUtil;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.SendSystemSettingOrder;
import com.ekek.tftcooker.utils.LogUtil;
import com.ekek.tftcooker.views.CircleProgress;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SystemSettingFragmentSound extends BaseFragment implements CircleProgress.OnCircleProgressListener {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder;


    @BindView(R.id.circle_progress)
    CircleProgress circleProgress;
    @BindView(R.id.tv_value_hint)
    TextView tvValueHint;
    @BindView(R.id.tv_value)
    TextView tvValue;
    private static final int HANDLE_OK_BUTTON = 0;

    private MessageHandler handler;
    private int maxVolume;
    private int volume;
    private int volumeGear;
    private int gearStep;

    private void init() {
        maxVolume = SoundUtil.getSystemMaxVolume(getActivity());
        gearStep = maxVolume / TFTCookerConstant.VOLUME_GEAR_MAX_VALUE;
        circleProgress.setAnimTime(TFTCookerConstant.CIRCLE_PROGRESS_ANIMATION_TIME);
        circleProgress.setOnCircleProgressListener(this);
        resetSound();
    }

    private void resetSound() {
        volume = Integer.parseInt(SettingPreferencesUtil.getDefaultSound(getActivity()));
        SoundUtil.setSystemVolume(getActivity(), volume);

        volumeGear = volumeToGear(volume);
        switchCircleProgressMode(
                TFTCookerConstant.VOLUME_GEAR_MAX_VALUE,
                volumeGear,
                true);

        tvValue.setText(String.valueOf(volumeGear));
    }

    private void switchCircleProgressMode(int maxValue, int value, boolean canTouch) {
        int max = (int) circleProgress.getMaxValue();
        if (max != maxValue) circleProgress.setMaxValue(maxValue);
        int currentValue = (int) circleProgress.getValue();
        if (currentValue != value) circleProgress.setValue(value);
        if (canTouch) circleProgress.enable();
        else circleProgress.disable();
    }

    private int gearToVolume(int gear) {
        if (gear < 0) {
            gear = 0;
        } else if (gear > TFTCookerConstant.VOLUME_GEAR_MAX_VALUE) {
            gear = TFTCookerConstant.VOLUME_GEAR_MAX_VALUE;
        }

        return gear == TFTCookerConstant.VOLUME_GEAR_MAX_VALUE ? maxVolume : gear * gearStep;
    }

    private int volumeToGear(int volume) {
        int gear = volume / gearStep;
        if (gear < 0) {
            gear = 0;
        } else if (gear > TFTCookerConstant.VOLUME_GEAR_MAX_VALUE) {
            gear = TFTCookerConstant.VOLUME_GEAR_MAX_VALUE;
        }
        return gear;
    }

    @Override
    public int initLayout() {
        return R.layout.system_setting_sound_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_volume);
        tvValueHint.setText(R.string.title_level);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRootView != null) {
            resetSound();
        }
    }

    @OnClick({R.id.back, R.id.ok, R.id.tv_title, R.id.ivOk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));  // 点击 back 按钮
                break;
            case R.id.ok:
            case R.id.ivOk:
                SettingPreferencesUtil.saveDefaultSound(getActivity(), String.valueOf(volume));
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Setting_volume_complete));
                back.setEnabled(false);
                ok.setEnabled(false);
                circleProgress.disable();
                handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onProgress(float value, boolean fromWidget) {
        volumeGear = (int) value;
        tvValue.setText(String.valueOf(volumeGear));
        volume = gearToVolume(volumeGear);
        SoundUtil.setSystemVolume(getActivity(), volume);
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentSound> master;

        private MessageHandler(SystemSettingFragmentSound master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLE_OK_BUTTON:
                back.setEnabled(true);
                ok.setEnabled(true);
                circleProgress.enable();
                break;

        }
    }
}
