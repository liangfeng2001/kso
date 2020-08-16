package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.SendSystemSettingOrder;
import com.ekek.tftcooker.utils.BrightnessUtil;
import com.ekek.tftcooker.views.CircleProgress;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SystemSettingFragmentBrightness extends BaseFragment
        implements CircleProgress.OnCircleProgressListener {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.circle_progress)
    CircleProgress circleProgress;
    @BindView(R.id.tv_value_hint)
    TextView tvValueHint;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.ok)
    TextView ok;
    Unbinder unbinder;

    private static final int HANDLE_OK_BUTTON = 0;

    private MessageHandler handler;
    private int brightness;
    private int brightnessGear;

    @Override
    public int initLayout() {
        return R.layout.system_setting_brightness_layout;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
        initUI();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onProgress(float value, boolean fromWidget) {
        brightnessGear = (int) value;
        tvValue.setText(String.valueOf(brightnessGear));

        //改变亮度
        brightness = gearToBrightness(brightnessGear);
        BrightnessUtil.changeAppBrightness(getActivity(), brightness);
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_brightness);
        tvValueHint.setText(R.string.title_brightness);
    }

    private void initUI() {
        circleProgress.setAnimTime(TFTCookerConstant.CIRCLE_PROGRESS_ANIMATION_TIME);
        resetBrightness();
    }

    private void resetBrightness() {
        String brightnessStr = SettingPreferencesUtil.getDefaultBrightness(getActivity());
        brightness = Integer.valueOf(brightnessStr);
        brightnessGear = brightnessToGear(brightness);
        switchCircleProgressMode(TFTCookerConstant.BRIGHTNESS_GEAR_MAX_VALUE, brightnessGear, true);
        circleProgress.setOnCircleProgressListener(this);
        tvValue.setText(String.valueOf(brightnessGear));
    }

    private void switchCircleProgressMode(int maxValue, int value, boolean canTouch) {
        int max = (int) circleProgress.getMaxValue();
        if (max != maxValue) circleProgress.setMaxValue(maxValue);
        int currentValue = (int) circleProgress.getValue();
        if (currentValue != value) circleProgress.setValue(value);
        if (canTouch) circleProgress.enable();
        else circleProgress.disable();
    }

    private int gearToBrightness(int gear) {
        if (gear < 0) {
            gear = 0;
        } else if (gear > TFTCookerConstant.BRIGHTNESS_GEAR_MAX_VALUE) {
            gear = TFTCookerConstant.BRIGHTNESS_GEAR_MAX_VALUE;
        }

        return gear * TFTCookerConstant.BRIGHTNESS_GEAR_STEP;
    }

    private int brightnessToGear(int brightness) {
        int gear = brightness / TFTCookerConstant.BRIGHTNESS_GEAR_STEP;
        if (gear < 0) {
            gear = 0;
        } else if (gear > TFTCookerConstant.BRIGHTNESS_GEAR_MAX_VALUE) {
            gear = TFTCookerConstant.BRIGHTNESS_GEAR_MAX_VALUE;
        }
        return gear;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRootView != null) {
            resetBrightness();
        }
    }

    @OnClick({R.id.back, R.id.ok, R.id.tv_title, R.id.ivOk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));
                break;
            case R.id.ok:
            case R.id.ivOk:
                SettingPreferencesUtil.saveDefaultBrightness(getActivity(), String.valueOf(brightness));
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Setting_brightness_complete));
                back.setEnabled(false);
                ok.setEnabled(false);
                circleProgress.disable();
                handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
                break;
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

    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentBrightness> master;

        private MessageHandler(SystemSettingFragmentBrightness master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }

}
