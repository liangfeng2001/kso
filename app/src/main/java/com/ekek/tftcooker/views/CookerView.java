package com.ekek.tftcooker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.event.CookerValueChanged;
import com.ekek.tftcooker.event.NoPanDetectedEvent;
import com.ekek.tftcooker.event.SendPauseClickOrder;
import com.ekek.tftcooker.event.ShowTimerCompleteOrder;
import com.ekek.tftcooker.event.TimeEvent;
import com.ekek.tftcooker.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class CookerView extends LinearLayout {

    protected static final int COOKER_STATUS_OPEN = 0;
    protected static final int COOKER_STATUS_OPEN_WITH_TIMER = 1;
    protected static final int COOKER_STATUS_CLOSE = 2;
    protected static final int HANDLER_COUNT_DOWN_TIMER = 0;
    protected static final int HANDLER_COUNT_DOWN_FOR_GEAR_B = 1;

    protected static final int COUNT_DOWN_TIME_FOR_GEAR_B = 4; //定时5分钟  4
    protected static final int COUNT_DOWN_TIME_SECOND_FOR_GEAR_B = 60; //定时5分钟

    protected static final int DURATION_UPDATE_NO_PAN_UI = 5 * 1000;
    protected static final int DURATION_POWER_OFF_NO_PAN = 60 * 1000;

    protected static final int Gear_Value_1 = 1;
    protected static final int Gear_Value_2 = 2;
    protected static final int Gear_Value_3 = 3;
    protected static final int Gear_Value_4 = 4;
    protected static final int Gear_Value_5 = 5;
    protected static final int Gear_Value_6 = 6;
    protected static final int Gear_Value_7 = 7;
    protected static final int Gear_Value_8 = 8;
    protected static final int Gear_Value_9 = 9;

    protected static final int CountDown_Hours_6 = 6 * 60 * 60 + 8;  // 6 * 60 * 60
    protected static final int CountDown_Hours_3 = 3 * 60 * 60 + 8; // 3 * 60 * 60
    protected static final int CountDown_Hours_2 = 2 * 60 * 60 + 8; // 2 * 60 * 60
    protected static final int CountDown_Hours_1_5 = (int) (1.5 * 60 * 60) + 8; // (int) (1.5 * 60 * 60)
    protected static final int CountDown_Minutes_10 = 10 * 60;
    protected static final int CountDown_Minutes_20 = 20 * 60;

    public static final int set_to_stop_timeUp = 0;
    public static final int set_to_alert_timeUp = 1;

    public static final int COUNT_DOWN_STATE9_IN_PROGRESS = 0;
    public static final int COUNT_DOWN_STATE9_LEVEL_1 = 1;
    public static final int COUNT_DOWN_STATE9_LEVEL_2 = 2;
    public static final int COUNT_DOWN_STATE9_POWERED_OFF = 3;

    @BindView(R.id.tv_gear)
    TextView tvGear;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.ll_circular_view)
    LinearLayout llCircularView;

    protected Context mContext;
    protected MessageHandler handler;
    protected OnCircularCookerViewListener mListener;

    protected int CountDown_Number_1_2_3 = 0;
    protected int CountDown_Number_4_5_6 = 0;
    protected int CountDown_Number_7_8 = 0;
    protected int CountDown_Number_9 = 0;
    protected boolean downTo9AfterBoost = false;

    protected int cookerStatus = COOKER_STATUS_CLOSE;
    protected int gear = 0, hour = 0, minute = 0, second = 0;
    protected int gearSetValue = 0, hourSetValue = 0, minuteSetValue = 0, secondSetValue = 0;

    protected int Stop_to_count_down = 0;  // 是否 停止 倒计时，检测 暂停按钮是否被按下

    protected boolean isNeedToCountDownForGearB = false;
    protected boolean isNeedToCountDown = false;

    protected boolean TheGearValueIsZero = false;
    protected int CountValueAfterTheGearIsZero = 0;

    protected int currentSecondForGearB = COUNT_DOWN_TIME_SECOND_FOR_GEAR_B;
    protected int currentCntForGearB = 0;

    protected boolean canTouch = false;
    protected boolean isAlertMode = false;

    protected boolean BoostWorkingStatus = false;

    protected long noPanDetectedTime = 0;
    protected float timerTextSize;
    public int countdownState9;

    public CookerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_circular_cooker_view, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CookerView);
        timerTextSize = typedArray.getFloat(R.styleable.CookerView_TimerTextSize, -1);
        ButterKnife.bind(this, this);
        mContext = context;
        handler = new MessageHandler(this);
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 是否点击了 暂停按钮
    public void onMessageEvent(SendPauseClickOrder event) {
        Stop_to_count_down = event.getOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TimeEvent event) {
         if (Stop_to_count_down == 0) {  // 没有点击 暂停按钮
            if (isNeedToCountDownForGearB) {  //  大 B 倒计时
                handler.sendEmptyMessage(HANDLER_COUNT_DOWN_FOR_GEAR_B);
            }
            if (isNeedToCountDown) {  // 定时器 倒计时
                handler.sendEmptyMessageDelayed(HANDLER_COUNT_DOWN_TIMER, 10);
            }
        }
        if (TheGearValueIsZero) {
            CountValueAfterTheGearIsZero++;
            if (CountValueAfterTheGearIsZero >= 7) {
                CountValueAfterTheGearIsZero = 0;
                TheGearValueIsZero = false;
                SetBackGroundGray();
            }
        }
        doPowerOffWhenTimeisUp();  // 检测 时间，定时关炉头 2018年12月5日8:59:14
    }

    @OnClick({R.id.ll_circular_view})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_circular_view:
                Power_On();
                break;
        }
    }

    public void Power_On() {
        if (!canTouch || ViewUtils.isShade(this)) return;
        if (cookerStatus == COOKER_STATUS_CLOSE) {
            powerOn();
        } else {
            if (mListener != null) mListener.onCookerSelected(gear);
            noPanDetectedTime = 0;
            updateGearView();
        }
    }

    public void setOnCircularCookerViewListener(OnCircularCookerViewListener listener) {
        mListener = listener;
    }
    public void powerOff() {
        if (cookerStatus != COOKER_STATUS_CLOSE) {
            doPowerOff();
        }
    }

    public String getTimeHourMinute() {
        String s = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        return s;
    }

    public void SetTvGearVisible(boolean flag) {
        if (cookerStatus != COOKER_STATUS_CLOSE) {
            if (flag) {
                tvGear.setVisibility(View.VISIBLE);
            } else {
                tvGear.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void SetAllTvVisible(boolean flag) {

        tvTimer.setVisibility(View.INVISIBLE);
        tvGear.setVisibility(INVISIBLE);
    }

    public void setGear(int gear) {
        if (gearSetValue != gear) {
            this.gear = gear;
            gearSetValue = gear;
            updateGearView();
            if (gear == 10) {
                currentCntForGearB = COUNT_DOWN_TIME_FOR_GEAR_B;
                currentSecondForGearB = COUNT_DOWN_TIME_SECOND_FOR_GEAR_B;
                mListener.onUpdateCountDownGearB(String.format("%02d", currentCntForGearB) + ":" + String.format("%02d", currentSecondForGearB));
                registerEventBus();
                startCountDownForGearB();
                BoostWorkingStatus = true;
                SetBackGroundBlue();
            } else {
                currentCntForGearB = 0;
                currentSecondForGearB = COUNT_DOWN_TIME_SECOND_FOR_GEAR_B;
                currentSecondForGearB = 0;
                stopCountDownForGearB();
                BoostWorkingStatus = false;
                if (gear == 0) {
                    TheGearValueIsZero = true;
                } else {
                    TheGearValueIsZero = false;
                    CountValueAfterTheGearIsZero = 0;
                    SetBackGroundBlue();
                }
            }
            if (isNeedToCountDown) {
                int maxHour = getMaxHours(gear);
                if (hour > maxHour) {
                    hour = maxHour;
                    hourSetValue = hour;
                    if (gear >= 9) {
                        minute = 30;
                    } else {
                        minute = 0;
                    }
                    minuteSetValue = minute;
                    second = 0;
                    secondSetValue = 0;
                }
            }
        }
    }

    public void doPowerOff() {
        doPowerOff(true);
    }
    public void doPowerOff(boolean enableEvent) {
        cookerStatus = COOKER_STATUS_CLOSE;
        stopCountDown();
        stopCountDownForGearB();
        resetValue();
        updateUI();
        noPanDetectedTime = 0;
        updateGearView();

        if (enableEvent) {
            mListener.onCookerPowerOff();
            Logger.getInstance().d("mListener.onCookerPowerOff()", true);
        }
    }

    public void SetBackGroundGray() {
        if (cookerStatus != COOKER_STATUS_CLOSE) {
            doSetBackGroundGray();
        }
    }
    public void SetBackGroundBlue() {
        if (cookerStatus != COOKER_STATUS_CLOSE) {
            doSetBackGroundBlue();
        }
    }
    public void ResetTimerInformation() {
        stopCountDown();
        // stopCountDownForGearB();
        hour = 0;
        hourSetValue = 0;
        minute = 0;
        minuteSetValue = 0;
        second = 0;
        secondSetValue = 0;
        // BoostWorkingStatus = false;
        tvTimer.setVisibility(GONE);
    }
    public void setTimer(int h, int m) {
        //   if (hour != h || minute != m) { // 更新 2018年11月22日14:49:13 复制时会 重新 设置时间值
        isAlertMode = false;
        this.hour = h;
        this.minute = m;
        this.second = 0;
        hourSetValue = h;
        minuteSetValue = m;
        secondSetValue = 0;
        updateTimerView();
        startCountDown();
        registerEventBus();
        //    }
    }
    public int getGearValue() {
        return this.gear;
    }
    public void disableCookerTouch() {
        canTouch = false;
    }

    public void enabletCookerTouch() {
        canTouch = true;
    }
    public boolean getTimerMode() {
        return isAlertMode;
    }
    public int getTimerHour() {
        return hour;
    }

    public int getTimerMinute() {
        return minute;
    }
    public void SetStopCountDownTimer() {
        stopCountDown();
        hour = 0;
        minute = 0;
        second = 0;
        tvTimer.setVisibility(GONE);
    }
    public String getTimeForGearB() {
        String s = String.format("%02d", currentCntForGearB) + ":" + String.format("%02d", currentSecondForGearB);
        return s;
    }
    public boolean isSetTimerToDefault() {
        boolean isSetTimerToDefault = true;
        if (hourSetValue != 0 || minuteSetValue != 0) {
            if (hour != 0 || minute != 0) {
                isSetTimerToDefault = false;
            }
        }

        return isSetTimerToDefault;
    }
    public void setTimerForAlert(int h, int m) {
        //  if (hour != h || minute != m) { // 更新 2018年11月22日14:49:13 复制时会 重新 设置时间值
        isAlertMode = true;
        this.hour = h;
        this.minute = m;
        this.second = 0;
        hourSetValue = h;
        minuteSetValue = m;
        secondSetValue = 0;
        updateTimerView();
        startCountDown();
        registerEventBus();
        // }
    }
    public void powerOn() {
        noPanDetectedTime = 0;
        updateGearView();
        if (cookerStatus == COOKER_STATUS_CLOSE) {
            cookerStatus = COOKER_STATUS_OPEN;
            setGear(0);
            updateUI();
            mListener.onCookerPowerOn();
        }
    }
    public void CheckRemovingPan(boolean pan) {
        if (pan || (Stop_to_count_down == 1)) {
            // 有锅
            updateGearView();
            long duration = SystemClock.elapsedRealtime() - noPanDetectedTime;
            if (duration >= DURATION_UPDATE_NO_PAN_UI && duration < DURATION_POWER_OFF_NO_PAN) {
                EventBus.getDefault().post(new NoPanDetectedEvent(
                        this.getId(),
                        NoPanDetectedEvent.EVENT_TYPE_PAN_IS_BACK));
            }
            noPanDetectedTime = 0;
        } else {
            // 无锅
            if (noPanDetectedTime == 0) {
                // 刚从有锅变成无锅时
                noPanDetectedTime = SystemClock.elapsedRealtime();
            }
            long duration = SystemClock.elapsedRealtime() - noPanDetectedTime;
            if (duration >= DURATION_UPDATE_NO_PAN_UI && duration < DURATION_POWER_OFF_NO_PAN) {
                tvGear.setText("?");
                EventBus.getDefault().post(new NoPanDetectedEvent(
                        this.getId(),
                        NoPanDetectedEvent.EVENT_TYPE_SET_UI));
            } else if (duration >= DURATION_POWER_OFF_NO_PAN) {
                noPanDetectedTime = 0;
                EventBus.getDefault().post(new NoPanDetectedEvent(
                        this.getId(),
                        NoPanDetectedEvent.EVENT_TYPE_POWER_OFF));
            }
        }
    }
    public void showError(int errorMessage) {
        if (errorMessage == 0) {
            updateUI();
            updateGearView();
        } else {
            tvGear.setVisibility(VISIBLE);
            tvGear.setText(errorMessage);
        }
    }

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this))  {
            EventBus.getDefault().unregister(this);
        }
    }

    protected abstract void doSetBackGroundNone();
    protected abstract void doSetBackGroundGray();
    protected abstract void doSetBackGroundBlue();

    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_COUNT_DOWN_TIMER:
                if (second != 59) {
                    second++;
                } else {
                    second = 0;
                    if (minute != 0) minute--;
                    if (hour != 0) {
                        if (minute == 0) {
                            minute = 59;
                            if (hour != 0) hour--;

                        }
                    }
                }

                tvTimer.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));

                if (hour == 0 && minute == 0 && second == 0) {
                    if (isAlertMode) {    //  set to alart  完成了
                        cookerStatus = COOKER_STATUS_OPEN;
                        stopCountDown();
                        tvTimer.setVisibility(GONE);
                        EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_DISP_TIMER_COMPLETE_PANEL)); // 显示 透明 框
                        mListener.onTimerIsUp(set_to_alert_timeUp);
                    } else {   // set to stop  完成了
                        doPowerOff(false);
                        EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_DISP_TIMER_COMPLETE_PANEL)); // 显示 透明 框
                        mListener.onTimerIsUp(set_to_stop_timeUp);
                    }
                }
                break;
            case HANDLER_COUNT_DOWN_FOR_GEAR_B:
                if (currentSecondForGearB != 0) {
                    currentSecondForGearB--;
                } else {
                    if (currentCntForGearB != 0) {
                        currentCntForGearB--;
                        currentSecondForGearB = COUNT_DOWN_TIME_SECOND_FOR_GEAR_B;
                    }
                }

                if (currentCntForGearB == 0 && currentSecondForGearB == 0) {
                    stopCountDownForGearB();
                    mListener.onSyncGearValue(9);
                    gear = 9;
                    downTo9AfterBoost = true;
                    updateGearView();
                    BoostWorkingStatus = false;
                } else {
                    mListener.onUpdateCountDownGearB(String.format("%02d", currentCntForGearB) + ":" + String.format("%02d", currentSecondForGearB));
                }
                break;
        }
    }
    protected void updateUI() {
        switch (cookerStatus) {
            case COOKER_STATUS_CLOSE:
                tvGear.setVisibility(GONE);
                tvTimer.setVisibility(GONE);
                doSetBackGroundNone();
                break;
            case COOKER_STATUS_OPEN:
                tvGear.setVisibility(VISIBLE);
                tvTimer.setVisibility(GONE);
                doSetBackGroundBlue();
                break;
            case COOKER_STATUS_OPEN_WITH_TIMER:
                tvGear.setVisibility(VISIBLE);
                tvTimer.setVisibility(VISIBLE);
                doSetBackGroundBlue();
                break;
        }
    }
    protected void updateGearView() {
        if (gear == 10) {
            tvGear.setText(R.string.title_boost_short);
        } else if (gear == 11) {
            tvGear.setText(R.string.title_h);
        } else if (gear == 0) {
            tvGear.setText("");
        } else {
            tvGear.setText(String.valueOf(gear));
        }

    }
    protected void stopCountDown() {
        isNeedToCountDown = false;
    }
    protected void stopCountDownForGearB() {
        isNeedToCountDownForGearB = false;
    }
    protected void resetValue() {
        gear = 0;
        gearSetValue = 0;
        hour = 0;
        hourSetValue = 0;
        minute = 0;
        minuteSetValue = 0;
        second = 0;
        secondSetValue = 0;
        BoostWorkingStatus = false;
        CountDown_Number_1_2_3 = 0;
        CountDown_Number_4_5_6 = 0;
        CountDown_Number_7_8 = 0;
        CountDown_Number_9 = 0;
        downTo9AfterBoost = false;
    }
    protected void startCountDownForGearB() {
        currentSecondForGearB = COUNT_DOWN_TIME_SECOND_FOR_GEAR_B;
        if (currentCntForGearB > 0) isNeedToCountDownForGearB = true;
        else if (currentCntForGearB == 0 && currentSecondForGearB != 0)
            isNeedToCountDownForGearB = true;
    }
    protected void updateTimerView() {
        cookerStatus = COOKER_STATUS_OPEN_WITH_TIMER;
        updateUI();
        int imgSize = 30;
        if (timerTextSize > 0) {
            tvTimer.setTextSize(timerTextSize);
            imgSize = (int)timerTextSize;
        }
        tvTimer.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
        Drawable drawable = this.getResources().getDrawable(R.mipmap.set_to_alert_normal_small);
        if (isAlertMode){ //  set to alart
            drawable.setBounds(0, 0, imgSize, imgSize);
            tvTimer.setCompoundDrawablePadding(4);
        }else {   // set to stop
            drawable = this.getResources().getDrawable(R.mipmap.set_to_stop_normal_small);
            drawable.setBounds(0, 0, imgSize, imgSize);
            tvTimer.setCompoundDrawablePadding(5);
        }
        tvTimer.setCompoundDrawables(null, null, null, drawable);

    }
    protected void startCountDown() {
        if (hour != 0 || minute != 0 || second != 0) {
            isNeedToCountDown = true;
        }
    }

    private void doPowerOffWhenTimeisUp() {
        if (gear >= 0) {
            switch (gear) {
                case Gear_Value_1:
                case Gear_Value_2:
                case Gear_Value_3:
                    downTo9AfterBoost = false;
                    CountDown_Number_1_2_3++;
                    if (CountDown_Number_1_2_3 > CountDown_Hours_6) {
                        CountDown_Number_1_2_3 = 0;
                        powerOff();
                    }
                    CountDown_Number_4_5_6 = 0;
                    CountDown_Number_7_8 = 0;
                    CountDown_Number_9 = 0;
                    break;

                case Gear_Value_4:
                case Gear_Value_5:
                case Gear_Value_6:
                    downTo9AfterBoost = false;
                    CountDown_Number_4_5_6++;
                    if (CountDown_Number_4_5_6 > CountDown_Hours_3) {
                        CountDown_Number_4_5_6 = 0;
                        powerOff();
                    }
                    CountDown_Number_1_2_3 = 0;
                    CountDown_Number_7_8 = 0;
                    CountDown_Number_9 = 0;
                    break;
                case Gear_Value_7:
                case Gear_Value_8:
                    downTo9AfterBoost = false;
                    CountDown_Number_7_8++;
                    if (CountDown_Number_7_8 > CountDown_Hours_2) {
                        CountDown_Number_7_8 = 0;
                        powerOff();
                    }
                    CountDown_Number_1_2_3 = 0;
                    CountDown_Number_4_5_6 = 0;
                    CountDown_Number_9 = 0;
                    break;
                case Gear_Value_9:
                    CountDown_Number_9++;
                    int countDownHours = CountDown_Hours_1_5;
                    if (downTo9AfterBoost) {
                        countDownHours -= COUNT_DOWN_TIME_FOR_GEAR_B * 60 + COUNT_DOWN_TIME_SECOND_FOR_GEAR_B;
                    }

                    if (CountDown_Number_9 > countDownHours) {
                        CountDown_Number_9 = 0;
                        powerOff();
                        countdownState9 = COUNT_DOWN_STATE9_POWERED_OFF;
                    } else if (CountDown_Number_9 > CountDown_Minutes_20) {
                        if (countdownState9 != COUNT_DOWN_STATE9_LEVEL_2) {
                            countdownState9 = COUNT_DOWN_STATE9_LEVEL_2;
                            EventBus.getDefault().post(new CookerValueChanged(CookerValueChanged.ORDER_COOKER_VALUE_CHANGED));
                        }
                    } else if (CountDown_Number_9 > CountDown_Minutes_10) {
                        if (countdownState9 != COUNT_DOWN_STATE9_LEVEL_1) {
                            countdownState9 = COUNT_DOWN_STATE9_LEVEL_1;
                            EventBus.getDefault().post(new CookerValueChanged(CookerValueChanged.ORDER_COOKER_VALUE_CHANGED));
                        }
                    } else {
                        countdownState9 = COUNT_DOWN_STATE9_IN_PROGRESS;
                    }
                    CountDown_Number_1_2_3 = 0;
                    CountDown_Number_4_5_6 = 0;
                    CountDown_Number_7_8 = 0;
                    break;
            }
        } else {
            CountDown_Number_1_2_3 = 0;
            CountDown_Number_4_5_6 = 0;
            CountDown_Number_7_8 = 0;
            CountDown_Number_9 = 0;
        }
    }
    private void init() {
        registerEventBus();
        Typeface timeTypeface = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        tvGear.setTypeface(timeTypeface);
        tvTimer.setTypeface(timeTypeface);
        updateUI();
    }
    private int getMaxHours(int gear) {
        int hMax;
        if (gear >= 9) {
            hMax = 1;
        } else if (gear >= 7) {
            hMax = 2;
        } else if (gear >= 4) {
            hMax = 3;
        } else {
            hMax = 6;
        }

        return hMax;
    }

    public void SetBoostWorkingStatus(boolean v) {
        if (cookerStatus != COOKER_STATUS_CLOSE) {
            BoostWorkingStatus = v;
        }
    }
    public void ShowHighTempStatus(boolean status) {
        if (gear > 0) {
            return;
        }

        int visible = tvGear.getVisibility();
        if (status) {
            if (visible != View.VISIBLE) {
                tvGear.setVisibility(View.VISIBLE);
                doSetBackGroundGray();
                tvGear.setText(R.string.title_h);
            }
        } else {
            if (visible == View.VISIBLE) {
                tvGear.setVisibility(GONE);
                doSetBackGroundNone();
            }
        }
    }
    public boolean GetBoostWorkingStatus() {
        return BoostWorkingStatus;
    }
    public int getCountdownState9() {
        return countdownState9;
    }

    public static class MessageHandler extends Handler {
        private final WeakReference<CookerView> master;

        private MessageHandler(CookerView master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
    public interface OnCircularCookerViewListener {

        void onCookerPowerOn();
        void onCookerPowerOff();
        void onCookerSelected(int value);
        void onUpdateCountDownGearB(String message);
        void onSyncGearValue(int value);
        void onTimerIsUp(int v);
    }
}
