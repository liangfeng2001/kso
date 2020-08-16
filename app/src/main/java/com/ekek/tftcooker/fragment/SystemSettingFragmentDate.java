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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SystemSettingFragmentDate extends BaseFragment
        implements NumberPickerView.OnValueChangeListener,
        NumberPickerView.OnBottomViewChangeListener,
        CheckIdleThread.CheckIdleThreadListener {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.set_day)
    NumberPickerView setDay;
    @BindView(R.id.FramelayoutTemp_FrameLayout)
    FrameLayout FramelayoutTempFrameLayout;
    @BindView(R.id.set_month)
    NumberPickerView setMonth;
    @BindView(R.id.FramelayoutTemp_FrameLayout2)
    FrameLayout FramelayoutTempFrameLayout2;
    @BindView(R.id.set_year)
    NumberPickerView setYear;
    @BindView(R.id.FramelayoutTemp_FrameLayout3)
    FrameLayout FramelayoutTempFrameLayout3;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder;

    private int currentYear, currentDay, currentMonth;
    List<String> monthList;
    //private ArrayWheelAdapter daysAdapter;
    private static final int SetLanguage = 0;
    private static final int SetDate = 1;
    private static final int SetTime = 2;
    private static final int GoBackToSetting = 3;

    private static final int HANDLE_OK_BUTTON = 0;
    private static final int HANDLE_IDLE = 1;

    private MessageHandler handler;
    private CheckIdleThread checkIdleThread;

    @Override
    public int initLayout() {
        return R.layout.system_setting_date_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_date);
    }

    private void initData() {

        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(cal.YEAR);
        currentMonth = cal.get(cal.MONTH) + 1;
        currentDay = cal.get(cal.DATE);

        //初始化 日列表

        setDay.setDisplayedValues(TFTCookerConstant.Oven_Date_GEAR_LIST);
        setDay.setOnValueChangedListener(this);
        setDay.setOnBottomViewChangeListener(this);
        setDay.setMaxValue(TFTCookerConstant.Oven_Date_GEAR_LIST.length - 1);
        setDay.setMinValue(0);
        setDay.setValue(currentDay - 1);// 从数据库读取到的值
        //   calAndUpdateFanGearValue(TFTOvenConstant.HOOD_FAN_DEFAULT_GEAR_VALUE);
        setDay.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        Typeface typeface1 = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        setDay.setContentTextTypeface(typeface1);
        setDay.setHintTextTypeface(typeface1);


        //初始化 月列表
        setMonth.setDisplayedValues(getDisplayValues(TFTCookerConstant.Oven_Month_GEAR_LIST));
        setMonth.setOnValueChangedListener(this);
        setMonth.setOnBottomViewChangeListener(this);
        setMonth.setMaxValue(TFTCookerConstant.Oven_Month_GEAR_LIST.length - 1);
        setMonth.setMinValue(0);
        setMonth.setValue(currentMonth - 1);// 从数据库读取到的值
        //   calAndUpdateFanGearValue(TFTOvenConstant.HOOD_FAN_DEFAULT_GEAR_VALUE);
        setMonth.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        Typeface typeface2 = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        setMonth.setContentTextTypeface(typeface2);
        setMonth.setHintTextTypeface(typeface2);


        //初始化 年列表
        setYear.setDisplayedValues(getYears(1970, 2071));
        setYear.setOnValueChangedListener(this);
        setYear.setOnBottomViewChangeListener(this);
        setYear.setMaxValue(getYears(1970, 2071).length - 1);
        setYear.setMinValue(0);
        setYear.setValue(currentYear - 1970);// 从数据库读取到的值
        //   calAndUpdateFanGearValue(TFTOvenConstant.HOOD_FAN_DEFAULT_GEAR_VALUE);
        setYear.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        Typeface typeface3 = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        setYear.setContentTextTypeface(typeface3);
        setYear.setHintTextTypeface(typeface3);
    }


    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        if (picker == setYear) {
            currentYear = newVal + 1970;
        }
        if (picker == setMonth) {
            currentMonth = newVal + 1;
        }
        if (picker == setDay) {
            currentDay = newVal + 1;
        }
        updateDaysUI();
    }

    private void updateDaysUI() {
        ArrayList<String> getDays = DateTimeUtil.getDays(currentYear, currentMonth);
        if (currentDay > getDays.size()) {
            currentDay = getDays.size();
        }
        setDay.setValue(currentDay - 1);// 重新初始化时显示的数据
    }

    @Override
    public void onBottomViewChange(boolean visible) {

    }

    private void notifyUpdateDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int day = cal.get(cal.DATE);
        if (year == currentYear && month == currentMonth && day == currentDay) {
        } else {
           DateTimeUtil.changeSystemDate(getActivity(), currentYear, currentMonth, currentDay);
        }
    }

    private String[] getDisplayValues(int[] strResources) {
        String[] result = new String[strResources.length];
        for (int loop = 0; loop < strResources.length; loop++) {
            result[loop] = getActivity().getResources().getString(strResources[loop]);
        }
        return result;
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
        SendSystemSettingOrder order;
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                if (getArgAppStart() == TFTCookerConstant.APP_START_FIRST_TIME) {
                    order = new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_language);
                    order.setParamL(getArgAppStart());
                    EventBus.getDefault().post(order);
                } else if (getArgAppStart() == TFTCookerConstant.APP_START) {
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_back));
                } else {
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));
                }
                break;
            case R.id.ok:
            case R.id.ivOk:
                saveAndQuit(getArgAppStart());
                break;
        }
    }

    private void saveAndQuit(int mode) {
        SendSystemSettingOrder order = new SendSystemSettingOrder(TFTCookerConstant.Setting_date_complete);
        order.setParamL(mode);
        EventBus.getDefault().post(order);
        back.setEnabled(false);
        ok.setEnabled(false);
        setMonth.disable();
        setDay.disable();
        setYear.disable();
        notifyUpdateDate();
        handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
    }

    public static String[] getYears(int startYear, int endYear) {
        String[] years = new String[endYear - startYear];
        int j = 0;
        for (int i = startYear; i < endYear; i++) {
            years[j] = (String.format("%s", i));
            j++;
        }
        return years;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
            switch (getArgAppStart()) {
                case TFTCookerConstant.APP_START_FIRST_TIME:
                    back.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setEnabled(true);
                    TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_F2);
                    checkIdleThread = CheckIdleThread.start(checkIdleThread, CheckIdleThread.DURATION_FIVE_MINUTES);
                    checkIdleThread.setCheckIdleThreadListener(this);
                    break;
                case TFTCookerConstant.APP_START:
                    back.setVisibility(View.GONE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setEnabled(false);
                    TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_F2);
                    checkIdleThread = CheckIdleThread.start(checkIdleThread, CheckIdleThread.DURATION_FIVE_MINUTES);
                    checkIdleThread.setCheckIdleThreadListener(this);
                    break;
                case TFTCookerConstant.APP_START_NONE:
                    back.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setEnabled(true);
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
        private final WeakReference<SystemSettingFragmentDate> master;

        private MessageHandler(SystemSettingFragmentDate master) {
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
                setMonth.enable();
                setDay.enable();
                setYear.enable();
                break;
            case HANDLE_IDLE:
                saveAndQuit(TFTCookerConstant.APP_START_IDLE);
                break;
        }
    }
}
