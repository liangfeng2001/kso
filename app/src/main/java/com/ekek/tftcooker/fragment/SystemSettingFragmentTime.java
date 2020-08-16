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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.common.CheckIdleThread;
import com.ekek.tftcooker.common.NumberPickerView;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.event.SendSystemSettingOrder;
import com.ekek.tftcooker.utils.DateTimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SystemSettingFragmentTime extends BaseFragment
        implements NumberPickerView.OnValueChangeListener,
        NumberPickerView.OnBottomViewChangeListener,
        CheckIdleThread.CheckIdleThreadListener {
    @BindView(R.id.set_hour)
    NumberPickerView setHour;
    @BindView(R.id.FramelayoutTemp_FrameLayout)
    FrameLayout FramelayoutTempFrameLayout;
    @BindView(R.id.ok)
    TextView ok;
    Unbinder unbinder;
    @BindView(R.id.set_min)
    NumberPickerView setMin;
    @BindView(R.id.FramelayoutTemp_FrameLayout2)
    FrameLayout FramelayoutTempFrameLayout2;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int currentHour, currentMinute;
    private Typeface typeface;
    private boolean isAM;
    private boolean is24Format = true;

    private static final int HANDLE_OK_BUTTON = 0;
    private static final int HANDLE_IDLE = 1;

    private MessageHandler handler;
    private CheckIdleThread checkIdleThread;

    @Override
    public int initLayout() {
        return R.layout.system_setting_time_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_time);
    }

    private void notifyUpdateTime() {

        int h = currentHour;
        int m = currentMinute;
        if (isAM) {
            if (currentHour == 12) {
                h = 0;
            }
        } else {
            if (currentHour != 12) {
                h = currentHour + 12;
            }
        }

        DateTimeUtil.changeSystemTime(getActivity(), h, m);
    }

    private void notifyUpdateTimeFor24Format() {
        DateTimeUtil.changeSystemTime(getActivity(), currentHour, currentMinute);
    }

    private void init() {
        initTime();
        // initMinute();
        // initTimeFormat();
        initData();
    }

    private void initTime() {
        //  is24Format = SettingPreferencesUtil.getTimeFormat24(getActivity());
        Calendar c = Calendar.getInstance();
        if (is24Format) {
            currentHour = c.get(Calendar.HOUR_OF_DAY);
        } else {
            currentHour = c.get(Calendar.HOUR);
        }

        currentMinute = c.get(Calendar.MINUTE);
        isAM = c.get(Calendar.AM_PM) == 0 ? true : false;//0是am,1是
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        this.handler = new MessageHandler(this);
    }

    private void initData() {


        //初始化 小时列表
        setHour.setDisplayedValues(TFTCookerConstant.Oven_Hour_GEAR_LIST);
        setHour.setOnValueChangedListener(this);
        setHour.setOnBottomViewChangeListener(this);
        setHour.setMaxValue(TFTCookerConstant.Oven_Hour_GEAR_LIST.length - 1);
        setHour.setMinValue(0);
        setHour.setValue(currentHour);// 从数据库读取到的值
        //   calAndUpdateFanGearValue(TFTOvenConstant.HOOD_FAN_DEFAULT_GEAR_VALUE);
        setHour.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        Typeface typeface1 = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        setHour.setContentTextTypeface(typeface1);
        setHour.setHintTextTypeface(typeface1);


        //初始化 分钟列表
        setMin.setDisplayedValues(TFTCookerConstant.Oven_Minute_GEAR_LIST);
        setMin.setOnValueChangedListener(this);
        setMin.setOnBottomViewChangeListener(this);
        setMin.setMaxValue(TFTCookerConstant.Oven_Minute_GEAR_LIST.length - 1);
        setMin.setMinValue(0);
        setMin.setValue(currentMinute);// 从数据库读取到的值
        //   calAndUpdateFanGearValue(TFTOvenConstant.HOOD_FAN_DEFAULT_GEAR_VALUE);
        setMin.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        Typeface typeface2 = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        setMin.setContentTextTypeface(typeface2);
        setMin.setHintTextTypeface(typeface2);
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

    @OnClick({R.id.ok, R.id.ivOk, R.id.back, R.id.tv_title})
    public void onClick(View view) {
        SendSystemSettingOrder order;
        switch (view.getId()) {
            case R.id.ok:// Setting_time_complete
            case R.id.ivOk:
                saveAndQuit(getArgAppStart());
                break;
            case R.id.back:
            case R.id.tv_title:
                if (getArgAppStart() != TFTCookerConstant.APP_START_NONE) {
                    order = new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_date);
                    order.setParamL(getArgAppStart());
                    EventBus.getDefault().post(order);
                } else {
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));  // 点击 back 按钮
                }
                break;
        }
    }

    private void saveAndQuit(int mode) {
        SendSystemSettingOrder order = new SendSystemSettingOrder(TFTCookerConstant.Setting_time_complete);
        order.setParamL(mode);
        EventBus.getDefault().post(order);
        back.setEnabled(false);
        ok.setEnabled(false);
        setHour.disable();
        setMin.disable();
        if (is24Format) {
            notifyUpdateTimeFor24Format();
        } else {
            notifyUpdateTime();
        }
        handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        if (picker == setHour) {
            currentHour = newVal;
        }
        if (picker == setMin) {
            currentMinute = newVal;
        }

    }

    @Override
    public void onBottomViewChange(boolean visible) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            init();
            switch (getArgAppStart()) {
                case TFTCookerConstant.APP_START_FIRST_TIME:
                case TFTCookerConstant.APP_START:
                    back.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_F3);
                    checkIdleThread = CheckIdleThread.start(checkIdleThread, CheckIdleThread.DURATION_FIVE_MINUTES);
                    checkIdleThread.setCheckIdleThreadListener(this);
                    break;
                case TFTCookerConstant.APP_START_NONE:
                    back.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_SETTING);
                    break;
            }
        } else {
            CheckIdleThread.cancel(checkIdleThread);
        }
    }

    @Override
    public void onIdle() {
        handler.sendEmptyMessage(HANDLE_IDLE);
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentTime> master;

        private MessageHandler(SystemSettingFragmentTime master) {
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
                setHour.enable();
                setMin.enable();
                break;
            case HANDLE_IDLE:
                saveAndQuit(TFTCookerConstant.APP_START_IDLE);
                break;
        }
    }
}
