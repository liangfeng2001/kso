package com.ekek.tftcooker.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.common.NumberPickerView;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.SendSystemSettingOrder;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SystemSettingFragmentPower extends BaseFragment implements NumberPickerView.OnValueChangeListener, NumberPickerView.OnBottomViewChangeListener {
    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.FramelayoutTemp_FrameLayout2)
    FrameLayout FramelayoutTempFrameLayout2;
    @BindView(R.id.ok)
    TextView ok;
    Unbinder unbinder;
    @BindView(R.id.set_power)
    NumberPickerView setPower;
    @BindView(R.id.minutes)
    TextView minutes;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private static final int HANDLE_OK_BUTTON = 0;

    private MessageHandler handler;
    private int activationTime;

    @Override
    public int initLayout() {
        return R.layout.system_setting_power_layout;
    }

    @Override
    public void initListener() {
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
    }

    private void initData() {

        //初始化 语言列表

        setPower.setDisplayedValues(TFTCookerConstant.Oven_Minute_GEAR_LIST_STAND_BY);
        setPower.setOnValueChangedListener(this);
        setPower.setOnBottomViewChangeListener(this);
        setPower.setMaxValue(TFTCookerConstant.Oven_Minute_GEAR_LIST_STAND_BY.length - 1);
        setPower.setMinValue(0);
        setPower.setValue(getTheStandByTimeFromSQLite());// 从数据库读取到的值
        //   calAndUpdateFanGearValue(TFTOvenConstant.HOOD_FAN_DEFAULT_GEAR_VALUE);
        setPower.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        Typeface typeface1 = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        setPower.setContentTextTypeface(typeface1);
        setPower.setHintTextTypeface(typeface1);
    }

    private int getTheStandByTimeFromSQLite() {
        activationTime = SettingPreferencesUtil.getActivationTime(getActivity());
        return activationTime / 60;
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

    @OnClick({R.id.back, R.id.ivOk, R.id.ok, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));
                break;
            case R.id.ok:
            case R.id.ivOk:
                SettingPreferencesUtil.saveActivationTime(getActivity(), activationTime);
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Setting_power_usage_complete));
                back.setEnabled(false);
                ok.setEnabled(false);
                setPower.disable();
                handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
                break;
        }
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        if (picker == setPower) {
            if (newVal == 0) {  // 设置成 0分钟的时候，默认设置为 30秒
                activationTime = 30;
            } else {
                activationTime = 60 * newVal;
            }
        }
    }

    @Override
    public void onBottomViewChange(boolean visible) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRootView != null && !hidden) {
            initData();
        }
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_stand_by);
        minutes.setText(R.string.title_minutes);
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentPower> master;

        private MessageHandler(SystemSettingFragmentPower master) {
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
                setPower.enable();
                break;

        }
    }
}
