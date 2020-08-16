package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.SendSystemSettingOrder;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

public class SystemSettingFragmentAS extends SystemSettingFragmentBase {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llTop)
    RelativeLayout llTop;
    @BindView(R.id.Time)
    TextView Time;
    @BindView(R.id.Date)
    TextView Date;
    @BindView(R.id.Language)
    TextView Language;
    @BindView(R.id.Brightness)
    TextView Brightness;
    @BindView(R.id.Volume)
    TextView Volume;
    @BindView(R.id.Reset)
    TextView Reset;
    @BindView(R.id.Power_usage)
    TextView PowerUsage;
    @BindView(R.id.Filter)
    TextView Filter;
    Unbinder unbinder;

    // Constants
    private static final int DURATION_MIN = 10 * 1000;

    // Fields
    private long lastLanguageTouchTime = 0;
    private long lastPowerUsageTouchTime = 0;
    private boolean demoModeToggled = false;

    @Override
    public int initLayout() {
        return R.layout.setting_system_as;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_settings);
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

    @OnClick({R.id.back, R.id.Time, R.id.Date, R.id.Language, R.id.Brightness,
            R.id.Volume, R.id.Reset, R.id.Power_usage, R.id.Filter, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_back));  // 点击 back 按钮
                break;
            case R.id.Time:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_time));  // 点击时间 按钮
                break;
            case R.id.Date:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_date));
                break;
            case R.id.Language:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_language));
                break;
            case R.id.Brightness:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_brightness));
                break;
            case R.id.Volume:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_volume));
                break;
            case R.id.Reset:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_reset));
                break;
            case R.id.Power_usage:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_power_usage));
                break;
            case R.id.Filter:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_filter));
                break;
        }
    }
    @OnTouch({R.id.Language, R.id.Power_usage})
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.Language:
                return onTouchLanguage(event);
            case R.id.Power_usage:
                return onTouchPowerUsage(event);
        }
        return false;
    }

    // Private functions
    private boolean onTouchLanguage(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastLanguageTouchTime = SystemClock.elapsedRealtime();
                demoModeToggled = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取两个按钮中最后点击的按钮的点击时间
                long lastTouchTime = lastLanguageTouchTime > lastPowerUsageTouchTime ? lastLanguageTouchTime : lastPowerUsageTouchTime;
                if (lastPowerUsageTouchTime != 0
                        && SystemClock.elapsedRealtime() - lastTouchTime >= DURATION_MIN) {
                    toggleDemoMode();
                    demoModeToggled = true;
                    lastLanguageTouchTime = SystemClock.elapsedRealtime();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                lastLanguageTouchTime = 0;
                if (lastPowerUsageTouchTime != 0 || demoModeToggled) {
                    return true;
                }
                break;
        }
        return false;
    }
    private boolean onTouchPowerUsage(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastPowerUsageTouchTime = SystemClock.elapsedRealtime();
                demoModeToggled = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取两个按钮中最后点击的按钮的点击时间
                long lastTouchTime = lastLanguageTouchTime > lastPowerUsageTouchTime ? lastLanguageTouchTime : lastPowerUsageTouchTime;
                if (lastLanguageTouchTime != 0
                        && SystemClock.elapsedRealtime() - lastTouchTime >= DURATION_MIN) {
                    toggleDemoMode();
                    demoModeToggled = true;
                    lastPowerUsageTouchTime = SystemClock.elapsedRealtime();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                lastPowerUsageTouchTime = 0;
                if (lastLanguageTouchTime != 0 || demoModeToggled) {
                    return true;
                }
                break;
        }
        return false;
    }
    private void toggleDemoMode() {
        boolean on = SettingPreferencesUtil.getDemonstrationMode(getActivity());
        SendSystemSettingOrder order = new SendSystemSettingOrder(TFTCookerConstant.Setting_demo_mode_toggle_req);
        order.setParamW(on ? 1 : 0);
        EventBus.getDefault().post(order);
    }
}
