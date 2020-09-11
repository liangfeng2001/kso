package com.ekek.tftcooker.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.CookerPanelFragment;
import com.ekek.tftcooker.base.ProductManager;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.DatabaseHelper;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.ClearTouchEvent;
import com.ekek.tftcooker.event.CookerHighTempOrder;
import com.ekek.tftcooker.event.CookerValueChanged;
import com.ekek.tftcooker.event.HoodConnectStatusChangedEvent;
import com.ekek.tftcooker.event.NoPanDetectedEvent;
import com.ekek.tftcooker.event.SendPauseClickOrder;
import com.ekek.tftcooker.event.ShowNotificationScreenOrder;
import com.ekek.tftcooker.event.ShowTimerCompleteOrder;
import com.ekek.tftcooker.event.TheFirstClickedCooker;
import com.ekek.tftcooker.event.TheLastClickedCooker;
import com.ekek.tftcooker.event.WorkModeChangedEvent;
import com.ekek.tftcooker.model.AllCookerDataEx;
import com.ekek.tftcooker.model.AnalysisSerialData;
import com.ekek.tftcooker.model.ICookerPowerDataEx;
import com.ekek.tftcooker.utils.LogUtil;
import com.ekek.tftcooker.utils.SoundManager;
import com.ekek.tftcooker.utils.SoundUtil;
import com.ekek.tftcooker.utils.ViewUtils;
import com.ekek.tftcooker.views.BigRectangleBackGroundView;
import com.ekek.tftcooker.views.CircleProgress;
import com.ekek.tftcooker.views.CircularCookerView;
import com.ekek.tftcooker.views.CookerView;
import com.ekek.tftcooker.views.RectangleCookerView;
import com.ekek.tftcooker.views.TrianProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CookerPanelFragment60 extends CookerPanelFragment implements CircleProgress.OnCircleProgressListener, TrianProgressView.OnTrianProgressListener {

    @BindView(R.id.FrameLayout_Button_B10)
    FrameLayout FrameLayoutButtonB10;
    @BindView(R.id.tv_hob_B11)
    TextView tvHobB11;
    @BindView(R.id.tv_hood_B11)
    TextView tvHoodB11;
    @BindView(R.id.FrameLayout_Button_B11)
    FrameLayout FrameLayoutButtonB11;
    @BindView(R.id.tv_hob_c12)
    TextView tvHobC12;
    @BindView(R.id.tv_hood_c12)
    TextView tvHoodC12;
    @BindView(R.id.FrameLayout_Button_C12)
    FrameLayout FrameLayoutButtonC12;
    @BindView(R.id.tv_timer_operate_B18)
    TextView tvTimerOperateB18;
    @BindView(R.id.FrameLayout_Button_B18)
    FrameLayout FrameLayoutButtonB18;
    @BindView(R.id.tv_Left_middle_gear_when_timer)
    TextView tvLeftMiddleGearWhenTimer;
    @BindView(R.id.tv_Left_middle_clock_when_timer)
    TextView tvLeftMiddleClockWhenTimer;
    @BindView(R.id.tv_Right_middle_gear_when_timer)
    TextView tvRightMiddleGearWhenTimer;
    @BindView(R.id.tv_Right_middle_clock_when_timer)
    TextView tvRightMiddleClockWhenTimer;
    @BindView(R.id.left_line_long)
    View leftLineLong;
    @BindView(R.id.right_line_long)
    View rightLineLong;
    @BindView(R.id.trian_progress)
    TrianProgressView trianProgress;
    @BindView(R.id.trian_progress2)
    TrianProgressView trianProgress2;
    @BindView(R.id.tv_timer_hint)
    TextView tvTimerHint;
    Unbinder unbinder1;
    private boolean BoostIsWorking = false;
    private SoundManager mSoundManager;

    @BindView(R.id.circle_progress)
    CircleProgress circleProgress;
    @BindView(R.id.circle_progress_2)
    CircleProgress circleProgress2;
    Unbinder unbinder;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_value_hint) // level 与 B 倒计时
            TextView tvValueHint;
    @BindView(R.id.tvCookwareNotDetected)
    TextView tvCookwareNotDetected;
    @BindView(R.id.tv_pause)
    TextView tvPause;
    @BindView(R.id.tv_timer_operate)
    TextView tvTimer;
    @BindView(R.id.tv_lock)
    TextView tvLock;
    @BindView(R.id.tv_lock_m)
    TextView tvLockM;
    @BindView(R.id.tv_boost)
    TextView tvBoost;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.tv_hob)
    TextView tvHob;
    @BindView(R.id.tv_hood)
    TextView tvHood;
    @BindView(R.id.tv_auto)
    TextView tvAuto;
    @BindView(R.id.tv_light)
    TextView tvLight;
    @BindView(R.id.tv_hood_stop)
    TextView tvHoodStop;
    @BindView(R.id.tv_timer_set_to_stop)
    TextView tvTimerSetToStop;
    @BindView(R.id.tv_timer_set_to_alert)
    TextView tvTimerSetToAlert;
    @BindView(R.id.tv_timer_cancel)
    TextView tvTimerCancel;
    @BindView(R.id.ll_timer_operate)
    LinearLayout llTimerOperate;
    @BindView(R.id.tv_timer_hour)
    TextView tvTimerHour;
    @BindView(R.id.tv_timer_minute)
    TextView tvTimerMinute;
    @BindView(R.id.ll_timer_area)
    LinearLayout llTimerArea;
    @BindView(R.id.auto_flag)
    ImageView autoFlag;


    @BindView(R.id.cooker_view_n)
    BigRectangleBackGroundView cookerViewN;
    @BindView(R.id.cooker_view_up_left)
    RectangleCookerView cookerViewUpLeft;
    @BindView(R.id.up_left_cooker_framelayout)
    FrameLayout upLeftCookerFramelayout;
    @BindView(R.id.cooker_view_down_left)
    RectangleCookerView cookerViewDownLeft;
    @BindView(R.id.down_left_cooker_framelayout)
    FrameLayout downLeftCookerFramelayout;
    @BindView(R.id.cooker_view_nn)
    BigRectangleBackGroundView cookerViewNn;
    @BindView(R.id.cooker_view_up_right)
    RectangleCookerView cookerViewUpRight;
    @BindView(R.id.up_right_cooker_framelayout)
    FrameLayout upRightCookerFramelayout;
    @BindView(R.id.cooker_view_down_right)
    RectangleCookerView cookerViewDownRight;
    @BindView(R.id.down_right_cooker_framelayout)
    FrameLayout downRightCookerFramelayout;
    @BindView(R.id.tv_Left_middle_gear)
    TextView tvLeftMiddleGear;
    @BindView(R.id.tv_Right_middle_gear)
    TextView tvRightMiddleGear;

    private int timerMinute = 0, timerHour = 0;
    private boolean click_set_to_alert_flag = false;
    private boolean click_set_to_stop_flag = false;
    private boolean click_lock_flag = false;

    private int TheLastClickedCooker = 0;  // 最后 松开点击时的炉头
    private int TheFirstClickedCooker = 0;  // 第一次点击的炉头

    private static final int DoAllStop = 1;
    private static final int DoTimerStop = 2;
    private boolean ClickTheHoobButton = false;
    private boolean CanNotTouchTheUIWhenPlayAlarm = false;

    private int MessageFromTouch = 0;
    private int UpdateCircleProgressValue = 0;
    private boolean ReadyToSetTimer = false;
    private static final int mode_set_to_alart = 1;
    private static final int mode_set_to_stop = 2;
    private boolean IsLightBlue = true;  // true 当前是蓝色灯； false 当前是橙色灯

    private boolean noPanLeftCookers = false;
    private boolean noPanRightCookers = false;

    private boolean doingPowerOffCooker = false;
    private static final float mSetPauseSize=35.0f;
    private boolean mPowerOffFromE03=false;

    @Override
    protected int initLayout() {
        return R.layout.fragment_cooker_panel_fragment60;
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        ViewUtils.refreshText(tvValueHint);
        ViewUtils.refreshText(tvCookwareNotDetected);
        ViewUtils.refreshText(tvTimerHint);
    }

    @Override
    protected void powerOffAllCookers() {
        mPowerOffFromE03=true;
        cookerViewUpLeft.powerOff();
        cookerViewDownLeft.powerOff();
        cookerViewUpRight.powerOff();
        cookerViewDownRight.powerOff();
    }

    @Override
    protected void handleCookerErrors(int errorMessage, int cookerBits) {
        if (leftCookersUnited()) {
            if (AnalysisSerialData.checkCookerA(cookerBits)
                    || AnalysisSerialData.checkCookerB(cookerBits)) {
                if (errorMessage > 0) {
                    tvLeftMiddleGear.setText(errorMessage);
                    tvLeftMiddleGearWhenTimer.setText(errorMessage);
                }
            }
        } else {
            if (AnalysisSerialData.checkCookerA(cookerBits)) {
                cookerViewDownLeft.showError(errorMessage);
            }
            if (AnalysisSerialData.checkCookerB(cookerBits)) {
                cookerViewUpLeft.showError(errorMessage);
            }
        }

        if (rightCookersUnited()) {
            if (AnalysisSerialData.checkCookerC(cookerBits)
                    || AnalysisSerialData.checkCookerD(cookerBits)) {
                if (errorMessage > 0) {
                    tvRightMiddleGear.setText(errorMessage);
                    tvRightMiddleGearWhenTimer.setText(errorMessage);
                }
            }
        } else {
            if (AnalysisSerialData.checkCookerC(cookerBits)) {
                cookerViewUpRight.showError(errorMessage);
            }
            if (AnalysisSerialData.checkCookerD(cookerBits)) {
                cookerViewDownRight.showError(errorMessage);
            }
        }
    }

    @Override
    public int getCookersBits() {
        return AnalysisSerialData.COOKER_A_BIT
                | AnalysisSerialData.COOKER_B_BIT
                | AnalysisSerialData.COOKER_C_BIT
                | AnalysisSerialData.COOKER_D_BIT;
    }

    private void ChechTouchScreen() {
        if (currentCooker >= 6 && currentCooker <= 55) {  // 此时处在联动模式
            if (workMode == WORK_MODE_HOB && BoostIsWorking == false && click_set_to_stop_flag == false && click_set_to_alert_flag == false) {  // 可以 5秒计数
                EventBus.getDefault().post(new ShowTimerCompleteOrder(8)); // 可以计数
            } else {   // 不可以 5 秒 计数
                EventBus.getDefault().post(new ShowTimerCompleteOrder(9)); // 不可以计数
            }
        }
        handler.sendEmptyMessageDelayed(HANDLER_TOUCH_SCREEN, 1000);
        handler.sendEmptyMessageDelayed(HANDLER_TOUCH_SCREEN_ONE_MINUTE, 1000);
    }

    @Override
    protected void initialize() {
        tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
        tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
        tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
        tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);

        ViewUtils.setText(tvValueHint, R.string.hint_level);
        ViewUtils.setText(tvCookwareNotDetected, R.string.msg_cookware_not_detected);

        circleProgress.setAnimTime(TFTCookerConstant.CIRCLE_PROGRESS_ANIMATION_TIME);
        switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO, false);
        circleProgress.setOnCircleProgressListener(this);
        trianProgress.setMinSelectValue(TFTCookerConstant.GEAR_MIN_SELECT_VALUE);
        trianProgress.setMaxSelectValue(TFTCookerConstant.GEAR_MAX_SELECT_VALUE);
        trianProgress.setOnTrianProgressListener(this);
        enableAllCookersTouch();
        initGigRectangleUI();

        /* 左上方的的圈圈     */
        cookerViewUpLeft.setOnCircularCookerViewListener(new CircularCookerView.OnCircularCookerViewListener() {
            @Override
            public void onCookerPowerOn() {
                doPowerOnCooker(COOKER_ID_Up_Left);  //COOKER_ID_Up_Left
                //  upLeftCookerFramelayout.setScaleX(1.0f);
                // upLeftCookerFramelayout.setScaleY(1.0f);
                // cookerViewUpLeft_boost_flag=false;
            }

            @Override
            public void onCookerPowerOff() {
                doPowerOffCooker(COOKER_ID_Up_Left, DoAllStop);
            }

            @Override
            public void onCookerSelected(int value) {
                //  upLeftCookerFramelayout.setScaleX(0.98f);
                // upLeftCookerFramelayout.setScaleY(0.98f);
                doSelectCooker(COOKER_ID_Up_Left, value);
            }

            @Override
            public void onUpdateCountDownGearB(String message) {
                updateCountDownGearBMessage(COOKER_ID_Up_Left, message);
            }

            @Override
            public void onSyncGearValue(int value) {
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                doSyncGearValue(value);

            }

            @Override
            public void onTimerIsUp(int v) {
                if (v == CookerView.set_to_stop_timeUp) {
                    doPowerOffCooker(COOKER_ID_Up_Left, DoTimerStop);
                }
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                mSoundManager.playAlarm();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_IS_UP, DURATION_TO_SHUT_DOWN_AUDIO);
                //   LockAllUI(false );
                CanNotTouchTheUIWhenPlayAlarm = true;
            }
        });
        /*左下方的圈圈*/
        cookerViewDownLeft.setOnCircularCookerViewListener(new CircularCookerView.OnCircularCookerViewListener() {
            @Override
            public void onCookerPowerOn() {
                doPowerOnCooker(COOKER_ID_Down_Left);
            }

            @Override
            public void onCookerPowerOff() {
                doPowerOffCooker(COOKER_ID_Down_Left, DoAllStop);
            }

            @Override
            public void onCookerSelected(int value) {
                doSelectCooker(COOKER_ID_Down_Left, value);
            }

            @Override
            public void onUpdateCountDownGearB(String message) {
                updateCountDownGearBMessage(COOKER_ID_Down_Left, message);
            }

            @Override
            public void onSyncGearValue(int value) {
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                doSyncGearValue(value);
            }

            @Override
            public void onTimerIsUp(int v) {
                if (v == CookerView.set_to_stop_timeUp) {
                    doPowerOffCooker(COOKER_ID_Down_Left, DoTimerStop);
                }
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                mSoundManager.playAlarm();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_IS_UP, DURATION_TO_SHUT_DOWN_AUDIO);
                CanNotTouchTheUIWhenPlayAlarm = true;
            }
        });

        /*右上方的圈圈*/
        cookerViewUpRight.setOnCircularCookerViewListener(new CircularCookerView.OnCircularCookerViewListener() {
            @Override
            public void onCookerPowerOn() {
                doPowerOnCooker(COOKER_ID_Up_Right);
            }

            @Override
            public void onCookerPowerOff() {
                doPowerOffCooker(COOKER_ID_Up_Right, DoAllStop);
            }

            @Override
            public void onCookerSelected(int value) {
                //  upRightCookerFramelayout.setScaleX(0.98f);
                //  upRightCookerFramelayout.setScaleY(0.98f);
                doSelectCooker(COOKER_ID_Up_Right, value);
            }

            @Override
            public void onUpdateCountDownGearB(String message) {
                updateCountDownGearBMessage(COOKER_ID_Up_Right, message);
            }

            @Override
            public void onSyncGearValue(int value) {
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                doSyncGearValue(value);
            }

            @Override
            public void onTimerIsUp(int v) {
                if (v == CookerView.set_to_stop_timeUp) {
                    doPowerOffCooker(COOKER_ID_Up_Right, DoTimerStop);
                }
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                mSoundManager.playAlarm();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_IS_UP, DURATION_TO_SHUT_DOWN_AUDIO);
                CanNotTouchTheUIWhenPlayAlarm = true;
            }
        });

        /*右下方的圈圈*/
        cookerViewDownRight.setOnCircularCookerViewListener(new CircularCookerView.OnCircularCookerViewListener() {
            @Override
            public void onCookerPowerOn() {
                doPowerOnCooker(COOKER_ID_Down_Right);
            }

            @Override
            public void onCookerPowerOff() {
                doPowerOffCooker(COOKER_ID_Down_Right, DoAllStop);
            }

            @Override
            public void onCookerSelected(int value) {
                // downRightCookerFramelayout.setScaleX(0.98f);
                //  downRightCookerFramelayout.setScaleY(0.98f);
                doSelectCooker(COOKER_ID_Down_Right, value);
            }

            @Override
            public void onUpdateCountDownGearB(String message) {
                updateCountDownGearBMessage(COOKER_ID_Down_Right, message);
            }

            @Override
            public void onSyncGearValue(int value) {  // value = 9 的时候 使用
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                doSyncGearValue(value);
                setBoostIsWorking(false);
            }

            @Override
            public void onTimerIsUp(int v) {
                if (v == CookerView.set_to_stop_timeUp) {
                    doPowerOffCooker(COOKER_ID_Down_Right, DoTimerStop);
                }
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                mSoundManager.playAlarm();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_IS_UP, DURATION_TO_SHUT_DOWN_AUDIO);
                CanNotTouchTheUIWhenPlayAlarm = true;
            }
        });

        switchWorkMode(workMode);
        initSound();
    }

    private void initSound() {
        SoundUtil.init(getContext());
        mSoundManager = SoundManager.getInstance(getContext());
    }


    private void doLock() {
        LockAllUI(false);
        tvLock.setTextColor(getResources().getColor(R.color.colorOrange));
        tvLockM.setTextColor(getResources().getColor(R.color.colorOrange));
        Drawable dw = getResources().getDrawable(R.mipmap.lock_press);
        dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
        tvLock.setCompoundDrawables(null, dw, null, null);
        tvLockM.setCompoundDrawables(null, dw, null, null);
        EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_DO_LOCK)); // 显示Lock大按钮
    }

    private void doUnLock() {

        LockAllUI(true);
        click_lock_flag = false;

        tvLock.setTextColor(getResources().getColor(R.color.colorWhite));
        tvLockM.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable dw = getResources().getDrawable(R.mipmap.lock_normal);
        dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
        tvLock.setCompoundDrawables(null, dw, null, null);
        tvLockM.setCompoundDrawables(null, dw, null, null);
        EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_DO_UNLOCK));
    }

    private void doSyncGearValue(int value) {

        if (cookerViewUpLeft_boost_flag) {
            if (cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
                cookerViewUpLeft.SetBoostWorkingStatus(false);
                cookerViewUpLeft_boost_flag = false;
                if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left) {
                    updateCircleProgressValue(value);
                }
            }
        }

        if (cookerViewDownLeft_boost_flag && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewDownLeft_boost_flag = false;
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Left) {
                updateCircleProgressValue(value);
            }
        }

        if (cookerViewUpRight_boost_flag && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewUpRight_boost_flag = false;
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right) {
                updateCircleProgressValue(value);
            }
        }

        if (cookerViewDownRight_boost_flag && cookerViewDownRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownRight_boost_flag = false;
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Right) {
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Left_SameTime && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);// 左上，左下 同时
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Left_SameTime = false;
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_And_Down_Letf_SameTime) {// 左上，左下  同时点击
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Down_Right_SameTime && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);// 右上、右下 同时点击
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Down_Right_SameTime = false;
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_And_Down_Right_SameTime) { // 右上、右下 同时点击
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Left && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);// 左上，左下
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Left = false;
            setHobGearText(tvLeftMiddleGear, 9);
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Down_Left) {// 左上，左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Down_Right && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Down_Right = false;  // 右上、右下
            setHobGearText(tvRightMiddleGear, 9);
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Down_Right) {// 右上、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Right && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Right = false;// 左上、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Down_Right) { // 左上、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Down_Left && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Down_Left = false; // 右上、左下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Down_Left) {// 右上、左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Up_Left && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Up_Left = false;// 右上、左上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Up_Left) {// 右上、左上 同时点击
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Right_Down_Left && cookerViewDownRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Right_Down_Left = false;// 左下、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Right_Down_Left) {// 右下、左下 同时点击
                updateCircleProgressValue(value);
            }
        }
        if (boost_flag_CookerView_Up_Right_And_Up_Left_Slip && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_And_Up_Left_Slip = false;// 右上、左上  滑动
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_And_Up_Left_Slip) {  // 右上、左上  滑动
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Right_And_Down_Left_Slip && cookerViewDownRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Right_And_Down_Left_Slip = false;// 右下、左下  滑动
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Right_And_Down_Left_Slip) {// 右下、左下  滑动
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);//左上、左下、右下、右上
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right) {//左上、左下、右下、右上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left) {//左上、右上、右下、左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right) {//右上、左上、左下、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Left_Up_Right && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Left_Up_Right = false; // 左上、左下、右上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Up_Left_Down_Left) {// 左上、左下 、右上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Left_Down_Right = false;// 左上、左下、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Down_Left_Down_Right) {// 左上、左下、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Down_Right_Up_Left && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Down_Right_Up_Left = false; // 右上、右下、左上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Up_Right_Down_Right) {// 右上、右下、左上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Down_Right_Down_Left && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Down_Right_Down_Left) {// 右上、右下、左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Down_Right_Middle && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Down_Right_Middle = false;// 左上、中间、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Down_Right_Middle) {// 左上、中间、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Down_Left_Middle && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Down_Left_Middle) {// 右上、中间、左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left && cookerViewDownRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Right_Up_Right_Middle_Down_Left) {// 右下、右上、中间、左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left && cookerViewDownRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Right_Up_Right_Middle_Up_Left) {// 右下、右上、中间、左上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Left_Up_Left_Middle_Up_Right) {// 左下、左上、中间、右上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Left_Up_Left_Middle_Down_Right) {// 左下、左上、中间、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left && cookerViewUpLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; // 左上、中间、右下、左下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Left_Middle_Down_Right_Down_Left) {// 左上、中间、右下、左下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right && cookerViewUpRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; // 右上、中间、左下、右下
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Up_Right_Middle_Down_Left_Down_Right) { // 右上、中间、左下、右下
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; // 左下、中间、右上、左上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Left_Middle_Up_Right_Up_Left) {// 左下、中间、右上、左上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right && cookerViewDownRight.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownRight.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; // 右下、中间、左上、右上
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_Down_Right_Middle_Up_Left_Up_Right) {// 右下、中间、左上、右上
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_All && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_All = false;//全部
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_All) {
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_ALL_ANY && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_ALL_ANY = false;//全部 有区
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_ALL_ALL_ANY) {
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_ALL_ALL_LEFT_NONE && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_ALL_ALL_LEFT_NONE) {// 全部，左边无区
                updateCircleProgressValue(value);
            }
        }

        if (boost_flag_CookerView_ALL_ALL_NONE_RIGHT && cookerViewDownLeft.getTimeForGearB().equals(TIMER_ZERO_STRING)) {
            cookerViewDownLeft.SetBoostWorkingStatus(false);
            cookerViewUpLeft.SetBoostWorkingStatus(false);
            cookerViewUpRight.SetBoostWorkingStatus(false);
            cookerViewDownRight.SetBoostWorkingStatus(false);
            boost_flag_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
            if (workMode == WORK_MODE_HOB && currentCooker == COOKER_ID_ALL_ALL_NONE_RIGHT) { // 全部，右边无区
                updateCircleProgressValue(value);
            }
        }
    }

    private void updateCountDownGearBMessage(int id, String message) {
        if (workMode == WORK_MODE_HOB) {
            if (isCookerSelected(id)) {
                ViewUtils.setText(tvValueHint, message);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unregisterEventBus();

    }

    //circle progress
    @Override
    public void onProgress(float value, boolean fromWidget) {
        Logger.getInstance().i("onProgress(" + value + "," + fromWidget + ")");
        int iValue = (int) Math.ceil(value);
        if (Math.round(value) == 0) {
            iValue = 0;
        }

        if (workMode == WORK_MODE_HOB) {// 炉灶 模式
            if (!doingPowerOffCooker) {

                // 在关闭无区时，两个炉头会依次关闭，关闭第一个炉头后，会自动选中第二个，
                // 此时执行到此处时，可能串口数据还没来得及同步，导致 InductionCookerHardwareManager
                // 返回错误的 newValue
                // 因为是在进行关闭炉头的操作，并未重新开启新的炉头，故此处也不需要进行询问
                int newValue = getMaxHobLevel(currentCooker, iValue);
                if (newValue < iValue) {
                    Message msg = new Message();
                    msg.what = HANDLER_ADJUST_LEVEL;
                    msg.arg1 = newValue;
                    handler.sendMessageDelayed(msg, 200);
                    return;
                }
            }

            updateCookerValue(iValue);
            setHoodAuto();
            this.SaveAllCookersData();
        } else if (workMode == WORK_MODE_HOOD) { // 风机模式
            if (hoodGear == iValue) {  // 值没有改变，说明 没有点击 圈圈。

            } else {
                if (TFTCookerApplication.getInstance().isHoodAuto()) {
                    TFTCookerApplication.getInstance().setHoodAuto(false);
                }
                hoodGear = iValue;
                setAutoUI();
                this.SaveAllCookersData();
            }
        } else if (workMode == WORK_MODE_SET_LIGHT) {
            lightGear_blue = iValue;
            SetLightSendedGear(iValue);
            tvValue.setText(String.valueOf(lightGear_blue));
        } else if (workMode == WORK_MODE_SET_TIMER_HOUR) {
            timerHour = iValue;
            tvTimerHour.setText(String.format(HOUR_FORMAT, timerHour));
            if (timerHour == this.getMaxHours()) {
                int maxMinutes = this.getMaxMinutes();
                if (maxMinutes == TFTCookerConstant.TIMER_MINUTE_MAX_VALUE) {
                    timerMinute = 0;
                    tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
                } else if (timerMinute > maxMinutes) {
                    timerMinute = maxMinutes;
                    tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
                }
            } else if ((timerHour == 0) && (timerMinute == 0)) {
                timerMinute = 1;
                tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
            }

        } else if (workMode == WORK_MODE_SET_TIMER_MINUTE) {
            timerMinute = iValue;
            tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
            if (timerHour == this.getMaxHours()) {
                int maxMinutes = this.getMaxMinutes();
                if (maxMinutes == TFTCookerConstant.TIMER_MINUTE_MAX_VALUE) {
                    if (timerMinute != 0) {
                        timerHour--;
                        tvTimerHour.setText(String.format(HOUR_FORMAT, timerHour));
                    }
                }
            }
        }
    }

    @OnClick({R.id.tv_pause, R.id.tv_timer_operate, R.id.tv_hood_B11, R.id.tv_hob_c12, R.id.tv_hood_c12, R.id.tv_timer_operate_B18, R.id.tv_hob_B11, R.id.tv_lock, R.id.tv_lock_m, R.id.tv_boost, R.id.tv_stop, R.id.tv_hob, R.id.tv_hood, R.id.tv_auto, R.id.tv_light, R.id.tv_hood_stop, R.id.tv_timer_set_to_stop, R.id.tv_timer_set_to_alert, R.id.tv_timer_cancel, R.id.tv_timer_hour, R.id.tv_timer_minute})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pause:
                doPause();
                LockAllUI(false);
                setPauseFlag(true);
                EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_DO_PAUSE)); // 显示停止大按钮
                break;
            case R.id.tv_timer_operate:  // 定时
            case R.id.tv_timer_operate_B18:
                EventBus.getDefault().post(new ShowTimerCompleteOrder(9)); // 不可以计数 5 秒，关闭联动模式
                displayNoPanUI(false);
                switchTimerMode();
                ReadyToSetTimer = true;
                break;
            case R.id.tv_lock:      // 锁定
            case R.id.tv_lock_m:
                click_lock_flag = true;
                doLock();
                break;
            case R.id.tv_boost:  // 升温，
                setBoostIsWorking(true);
                EventBus.getDefault().post(new ShowTimerCompleteOrder(9)); // 不可以计数 5 秒，关闭联动模式
                doSwitchBoostMode();
                break;
            case R.id.tv_stop:  // 所有 停止 工作
                displayNoPanUI(false);
                doStopCooker();
                handler.sendEmptyMessageDelayed(HANDLER_TOUCH_SCREEN_ONE_MINUTE, 10);
                break;
            case R.id.tv_hob:  // 炉头
            case R.id.tv_hob_B11:
            case R.id.tv_hob_c12:
                doSwitchHobMode();
                handler.sendEmptyMessageDelayed(HANDLER_TOUCH_SCREEN_ONE_MINUTE, 10);
                break;
            case R.id.tv_hood:  // 风机
            case R.id.tv_hood_c12:
            case R.id.tv_hood_B11:
                if (!TFTCookerApplication.getInstance().isHoodConnected()) {
                    EventBus.getDefault().post(new ShowNotificationScreenOrder(
                            R.string.msg_enable_hood_connection,
                            3));
                    return;
                }
                displayNoPanUI(false);
                restoreSelectRange();
                EventBus.getDefault().post(new ShowTimerCompleteOrder(9)); // 不可以计数 5 秒，关闭联动模式
                doSwitchHoodMode();
                EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_NO_STAND_BY)); // 点击了 风机设置，不能退到睡眠模式
                ClickTheHoobButton = true;
                break;
            case R.id.tv_auto:  // 自动模式
                doAuto();
                break;
            case R.id.tv_light:
                doLight();
                break;
            case R.id.tv_hood_stop:
                doHoodStop();
                break;
            case R.id.tv_timer_set_to_stop:  // 定时，关
                doSetToStop();
                click_set_to_stop_flag = true;
                click_set_to_alert_flag = false;
                ReadyToSetTimer = false;
                break;
            case R.id.tv_timer_set_to_alert:  //  定时开始 计时
                doSetToAlert();
                click_set_to_alert_flag = true;
                click_set_to_stop_flag = false;
                ReadyToSetTimer = false;
                break;
            case R.id.tv_timer_cancel:   // 恢复上一个界面
                doCancelTimer();
                ReadyToSetTimer = false;
                break;
            case R.id.tv_timer_hour:
                workMode = WORK_MODE_SET_TIMER_HOUR;
                switchTimerSetMode(workMode);
                break;
            case R.id.tv_timer_minute:
                workMode = WORK_MODE_SET_TIMER_MINUTE;
                switchTimerSetMode(workMode);
                break;
        }
    }

    private void HideTvLeftMiddleGearWhenTimerAndClockWhenTimer() {
        if(mPowerOffFromE03){
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);
        }else {
            tvLeftMiddleGear.setVisibility(View.VISIBLE);
        }
        tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
        tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
        leftLineLong.setBackgroundColor(Color.WHITE);

        handler.sendEmptyMessageDelayed(HANDLER_POWER_OFF_FROM_ERROR03,1000 );
    }

    private void HideTvRightMiddleGearWhenTimerAndClockWhenTimer() {
        if(mPowerOffFromE03){
            tvRightMiddleGear.setVisibility(View.INVISIBLE);
        }else {
            tvRightMiddleGear.setVisibility(View.VISIBLE);
        }
        tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
        tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
        rightLineLong.setBackgroundColor(Color.WHITE);

        handler.sendEmptyMessageDelayed(HANDLER_POWER_OFF_FROM_ERROR03,1000 );
    }

    private void HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop() {
        tvRightMiddleGear.setVisibility(View.INVISIBLE);
        tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
        tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
        rightLineLong.setBackgroundColor(Color.WHITE);

        cookerViewUpRight.SetTvGearVisible(true);
        cookerViewDownRight.SetTvGearVisible(true);
        cookerViewUpRight.SetBackGroundGray();
        cookerViewDownRight.SetBackGroundGray();
    }

    private void HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop() {
        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
        tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
        tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
        leftLineLong.setBackgroundColor(Color.WHITE);

        cookerViewUpLeft.SetTvGearVisible(true);
        cookerViewDownLeft.SetTvGearVisible(true);
        cookerViewUpLeft.SetBackGroundGray();
        cookerViewDownLeft.SetBackGroundGray();
    }

    private void CheckEveryCookerOfTimerStatus() {
        if (click_set_to_alert_flag_Up_Left_Down_Left) { // 左上 左下
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) { // 闹铃定时结束
                click_set_to_alert_flag_Up_Left_Down_Left = false;
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
               // LogUtil.d("show the close timer ");
            }
        }
        if (click_set_to_stop_flag_Up_Left_Down_Left) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下
            }
        }
        if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left) { //左上、左下 、右上
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) { // 闹铃定时结束
                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Right_Up_Left_Down_Left = false; //左上、左下 、右上
            }
        }
        if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
            }
        }
        if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right) {// 左上、左下、右下
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) { // 闹铃定时结束
                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_alert_flag_Up_Left_Down_Left_Middle) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {// 闹铃定时结束
                click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Left_Down_Left_Middle) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间;
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
            }
        }
        if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
            }
        }
        if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上
            }
        }
        if (click_set_to_alert_flag_All) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_All = false;//全部
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_All) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_All = false;//全部
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_All = false;//全部
            }
        }

        if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
            }
        }
        if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下
            }
        }
        if (click_set_to_alert_flag_ALL_ALL_LEFT_NONE) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_ALL_ALL_LEFT_NONE) {
            tvLeftMiddleClockWhenTimer.setText(cookerViewUpLeft.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvLeftMiddleClockWhenTimer);
            if (cookerViewUpLeft.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
            }
        }
        if (click_set_to_alert_flag_Up_Right_Down_Right) {  // 右上 右下
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Up_Right_Down_Right = false;
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Right_Down_Right) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
            }

        }
        if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;    // 右上、右下、左上
            }
        }
        if (click_set_to_alert_flag_Up_Right_Down_Right_Down_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Right_Down_Right_Down_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
            }
        }
        if (click_set_to_alert_flag_Down_Right_Up_Right_Middle) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Down_Right_Up_Right_Middle) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
            }
        }

        if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下

            }
        }
        if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
            }
        }
        if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
            }
        }
        if (click_set_to_alert_flag_ALL_ALL_NONE_RIGHT) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_alert(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
            }
        }
        if (click_set_to_stop_flag_ALL_ALL_NONE_RIGHT) {
            tvRightMiddleClockWhenTimer.setText(cookerViewUpRight.getTimeHourMinute());
            setTimerIconWhenFreeCookers_set_to_stop(tvRightMiddleClockWhenTimer);
            if (cookerViewUpRight.getTimeHourMinute().equals(TIMER_ZERO_STRING)) {
                click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
            }
        }
        handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 1000);
    }

    private void ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer() {
        cookerViewUpLeft.SetAllTvVisible(true);
        cookerViewDownLeft.SetAllTvVisible(true);
        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
        tvLeftMiddleGearWhenTimer.setVisibility(View.VISIBLE);
        tvLeftMiddleClockWhenTimer.setVisibility(View.VISIBLE);
        leftLineLong.setBackgroundColor(Color.BLACK);
    }

    private void ShowTvRightMiddleGearWhenTimerAndClockWhenTimer() {
        cookerViewUpRight.SetAllTvVisible(true);
        cookerViewDownRight.SetAllTvVisible(true);
        tvRightMiddleGear.setVisibility(View.INVISIBLE);
        tvRightMiddleClockWhenTimer.setVisibility(View.VISIBLE);
        tvRightMiddleGearWhenTimer.setVisibility(View.VISIBLE);
        rightLineLong.setBackgroundColor(Color.BLACK);
    }

    private void doSetToAlert() {
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left:
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Middle:
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right:
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right:
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);

                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Left_Down_Left = true;
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Right_Down_Right = true;
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                //   cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = true;
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = true;
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = true;//左上、右上、右下、左下
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);


                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = true; //右上、左上、左下、右下
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = true;  //左上、左下、右下、右上
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = true; // 右上、右下、左上
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                //cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                //   cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                click_set_to_alert_flag_Up_Left_Down_Left_Middle = true;//  左上、左下、中间
                click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上

                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                click_set_to_alert_flag_Down_Right_Up_Right_Middle = true;// 右上、右下、中间
                click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间

                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = true;// 右下、右上、中间、左下
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = true;// 右下、右上、中间、左上
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = true;// 左下、左上、中间、右上
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = true;// 左下、左上、中间、右下
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                //   cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_All://全部
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                //  cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);

                click_set_to_alert_flag_All = true;//全部
                click_set_to_stop_flag_All = false;//全部
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                cookerViewDownLeft.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpLeft.setTimerForAlert(timerHour, timerMinute);
                // cookerViewMiddle.setTimerForAlert(timerHour, timerMinute);
                cookerViewDownRight.setTimerForAlert(timerHour, timerMinute);
                cookerViewUpRight.setTimerForAlert(timerHour, timerMinute);
                break;

        }

        doSwitchHobMode();
        switchOffTimerMode(true);
    }

    private void doSetToStop() {
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left:
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Middle:
                //   cookerViewMiddle.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right:
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right:
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);

                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
               /* cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewMiddle.setTimer(timerHour, timerMinute);*/
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
         /*       cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewMiddle.setTimer(timerHour, timerMinute);*/
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
             /*   cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewMiddle.setTimer(timerHour, timerMinute);*/
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
            /*    cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewMiddle.setTimer(timerHour, timerMinute);*/
                break;

            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = true;//右上、左上、左下、右下
                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = true;//左上、右上、右下、左下
                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下

                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上:
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = true;  //左上、左下、右下、右上
                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = true;  //左上、左下 、右上
                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下
                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = true; // 右上、右下、左上
                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //   cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                // cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //    cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //   cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Up_Left_Down_Left_Middle = true;//  左上、左下、中间
                click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                //   cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Down_Right_Up_Right_Middle = true;// 右上、右下、中间
                click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                //   cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = true;// 右下、右上、中间、左下
                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                //cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = true;// 右下、右上、中间、左上
                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = true;// 左下、左上、中间、右上
                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //   cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = true;// 左下、左上、中间、右下
                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                // cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_All://全部
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                click_set_to_stop_flag_All = true;//全部
                click_set_to_alert_flag_All = false;//全部
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                ShowTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                ShowTvRightMiddleGearWhenTimerAndClockWhenTimer();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING, 100);
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                cookerViewDownLeft.setTimer(timerHour, timerMinute);
                cookerViewUpLeft.setTimer(timerHour, timerMinute);
                //  cookerViewMiddle.setTimer(timerHour, timerMinute);
                cookerViewDownRight.setTimer(timerHour, timerMinute);
                cookerViewUpRight.setTimer(timerHour, timerMinute);
                break;
        }

        doSwitchHobMode();
        switchOffTimerMode(true);
    }

    private void RecoverWhenClickCancelTimer() {
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                cookerViewUpLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Left:
                cookerViewDownLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Middle:
                //   cookerViewMiddle.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right:
                cookerViewUpRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Right:
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                cookerViewUpLeft.SetStopCountDownTimer();
                //   cookerViewMiddle.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                cookerViewDownLeft.SetStopCountDownTimer();
                //   cookerViewMiddle.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                cookerViewUpRight.SetStopCountDownTimer();
                //     cookerViewMiddle.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                cookerViewDownRight.SetStopCountDownTimer();
                //    cookerViewMiddle.SetStopCountDownTimer();
                break;

            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上:
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                cookerViewUpLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                cookerViewUpLeft.SetStopCountDownTimer();
                //  cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                cookerViewUpRight.SetStopCountDownTimer();
                // cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                cookerViewUpLeft.SetStopCountDownTimer();
                //    cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                cookerViewDownLeft.SetStopCountDownTimer();
                //cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                cookerViewUpLeft.SetStopCountDownTimer();
                //    cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                cookerViewUpLeft.SetStopCountDownTimer();
                //  cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                cookerViewDownRight.SetStopCountDownTimer();
                // cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                cookerViewDownRight.SetStopCountDownTimer();
                //cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                //   cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                // cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                //  cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                //  cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                cookerViewUpLeft.SetStopCountDownTimer();
                //cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                cookerViewUpRight.SetStopCountDownTimer();
                //   cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                cookerViewDownLeft.SetStopCountDownTimer();
                //  cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                cookerViewDownRight.SetStopCountDownTimer();
                //   cookerViewMiddle.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                //    cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                //     cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_All://全部
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                //  cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                click_set_to_stop_flag_All = false;//全部
                click_set_to_alert_flag_All = false;//全部
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                cookerViewDownLeft.SetStopCountDownTimer();
                cookerViewUpLeft.SetStopCountDownTimer();
                // cookerViewMiddle.SetStopCountDownTimer();
                cookerViewDownRight.SetStopCountDownTimer();
                cookerViewUpRight.SetStopCountDownTimer();
                break;
        }
    }

    private void doCancelTimer() {
        doSwitchHobMode();
        switchOffTimerMode(true);
        RecoverWhenClickCancelTimer();
    }

    private void switchTimerSetMode(int mode) {
        int maxHour = this.getMaxHours();
        int maxMinutes = this.getMaxMinutes();
        if (timerHour > maxHour) {
            timerHour = maxHour;
            tvTimerHour.setText(String.format(HOUR_FORMAT, timerHour));
            if (maxMinutes != 59) {
                timerMinute = maxMinutes;
                tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
            }
        }
        if (mode == WORK_MODE_SET_TIMER_HOUR) {
            tvTimerHour.setTextColor(Color.parseColor("#d84824"));
            tvTimerHour.setTextSize(getResources().getDimension(R.dimen.timer_text_selected));
            tvTimerMinute.setTextColor(Color.WHITE);
            tvTimerMinute.setTextSize(getResources().getDimension(R.dimen.timer_text));

            switchCircleProgressMode(maxHour, maxHour, 0, 0, timerHour, true, true);
        } else if (mode == WORK_MODE_SET_TIMER_MINUTE) {
            tvTimerHour.setTextColor(Color.WHITE);
            tvTimerHour.setTextSize(getResources().getDimension(R.dimen.timer_text));
            tvTimerMinute.setTextColor(Color.parseColor("#d84824"));
            tvTimerMinute.setTextSize(getResources().getDimension(R.dimen.timer_text_selected));
            if (timerMinute > maxMinutes) {
                timerMinute = maxMinutes;
            }
            if (timerHour == 0) {
                switchCircleProgressMode(maxMinutes, maxMinutes, 0, 1, timerMinute, true, true);
            } else {
                switchCircleProgressMode(maxMinutes, maxMinutes, 0, 0, timerMinute, true, true);
            }

        }
    }

    private int getMaxHours() {
        int gear = getCurrentCookerGearValue();
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

    private int getMaxMinutes() {
        int gear = getCurrentCookerGearValue();
        int mMax = TFTCookerConstant.TIMER_MINUTE_MAX_VALUE;
        if (gear >= 9) {
            mMax = 30;
        }
        return timerHour > 0 ? mMax : TFTCookerConstant.TIMER_MINUTE_MAX_VALUE;
    }

    private void initGigRectangleUI() {

  /*      upLeftCookerFramelayout.setScaleX(0.98f);
        upLeftCookerFramelayout.setScaleY(0.98f);

        downLeftCookerFramelayout.setScaleX(0.98f);
        downLeftCookerFramelayout.setScaleY(0.98f);

        upRightCookerFramelayout.setScaleX(0.98f);
        upRightCookerFramelayout.setScaleY(0.98f);

        downRightCookerFramelayout.setScaleX(0.98f);
        downRightCookerFramelayout.setScaleY(0.98f);*/

        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
        tvRightMiddleGear.setVisibility(View.INVISIBLE);

    }

    private void switchTimerMode() {
        restoreSelectRange();
        if (workMode == WORK_MODE_HOB) {
            workMode = WORK_MODE_SET_TIMER_MINUTE;//WORK_MODE_SET_TIMER_HOUR;
            llTimerOperate.setVisibility(View.VISIBLE);
            ShowSetTimerButton(true);
            tvTimer.setTextColor(getResources().getColor(R.color.colorBlue));
            Drawable dw = getResources().getDrawable(R.mipmap.timer_press);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvTimer.setCompoundDrawables(null, dw, null, null);
            ViewUtils.setText(tvValueHint, R.string.hint_timer);
            ViewUtils.setText(tvTimerHint, R.string.hint_timer);
            tvValueHint.setVisibility(View.INVISIBLE);
            tvTimerHint.setVisibility(View.VISIBLE);
            tvValue.setVisibility(View.INVISIBLE);
            llTimerArea.setVisibility(View.VISIBLE);

            if (checkCookerTimerHaveValue()) {
                timerHour = 0;
                timerMinute = 0;
            } else {
                timerHour = getCurrentCookerTimerHour();
                timerMinute = getCurrentCookerTimerMinute();
            }
            tvTimerHour.setText(String.format(HOUR_FORMAT, timerHour));
            tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
            LogUtil.d("THE timerMinute IS " + timerMinute);
            switchTimerSetMode(workMode);
        } else {

        }
    }

    private void ShowSetTimerButton(boolean v) {
        if (v) {
            FrameLayoutButtonB18.setVisibility(View.VISIBLE);
            FrameLayoutButtonB10.setVisibility(View.INVISIBLE);
            FrameLayoutButtonB11.setVisibility(View.INVISIBLE);
        } else {
            showHobOperateMenu();
        }
    }

    private int getCurrentCookerTimerHour() {
        int hour = 0;
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Down_Left:
                hour = cookerViewDownLeft.getTimerHour();
                break;
            case COOKER_ID_Middle:
                //   hour = cookerViewMiddle.getTimerHour();
                break;
            case COOKER_ID_Up_Right:
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Down_Right:
                hour = cookerViewDownRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                hour = cookerViewDownRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                hour = cookerViewDownLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                hour = cookerViewDownRight.getTimerHour();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                hour = cookerViewUpLeft.getTimerHour();
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                hour = cookerViewDownLeft.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                //hour = cookerViewMiddle.getTimerHour();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                //  hour = cookerViewMiddle.getTimerHour();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                //hour = cookerViewMiddle.getTimerHour();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                // hour = cookerViewMiddle.getTimerHour();
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                //   hour = cookerViewMiddle.getTimerHour();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                hour = cookerViewUpRight.getTimerHour();
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                hour = cookerViewDownRight.getTimerHour();
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
            case COOKER_ID_All://全部
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                hour = cookerViewUpRight.getTimerHour();
                break;
        }
        return hour;
    }

    private int getCurrentCookerTimerMinute() {
        int minute = 0;
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Down_Left:
                minute = cookerViewDownLeft.getTimerMinute();
                break;
            case COOKER_ID_Middle:
                //    minute = cookerViewMiddle.getTimerMinute();
                break;
            case COOKER_ID_Up_Right:
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Down_Right:
                minute = cookerViewDownRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                minute = cookerViewDownRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                minute = cookerViewDownLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                minute = cookerViewDownRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                minute = cookerViewUpLeft.getTimerMinute();
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                minute = cookerViewDownLeft.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                //  minute = cookerViewMiddle.getTimerMinute();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                // minute = cookerViewMiddle.getTimerMinute();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                // minute = cookerViewMiddle.getTimerMinute();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                //  minute = cookerViewMiddle.getTimerMinute();
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                //  minute = cookerViewMiddle.getTimerMinute();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                minute = cookerViewUpRight.getTimerMinute();
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                minute = cookerViewDownRight.getTimerMinute();
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
            case COOKER_ID_All://全部
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                minute = cookerViewUpRight.getTimerMinute();
                break;
        }
        return minute;
    }

    private boolean checkCookerTimerHaveValue() {
        boolean haveValue = false;
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Left:
                haveValue = cookerViewDownLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Middle:
                //   haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right:
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right:
                haveValue = cookerViewDownRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                haveValue = cookerViewDownRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                haveValue = cookerViewDownLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                haveValue = cookerViewDownRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                haveValue = cookerViewUpLeft.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左上、左下、中间
                //  haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
            case COOKER_ID_Up_Left_Down_Left_Middle:// 右上、右下、中间
                // haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、中间、右下
                //  haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                //   haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                //   haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                //   haveValue = cookerViewMiddle.isSetTimerToDefault();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                haveValue = cookerViewDownRight.isSetTimerToDefault();
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
            case COOKER_ID_All://全部
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                haveValue = cookerViewUpRight.isSetTimerToDefault();
                break;
        }

        return haveValue;
    }

    private void switchOffTimerMode(boolean canTouch) {
        llTimerOperate.setVisibility(View.INVISIBLE);
        ShowSetTimerButton(false);
        tvTimer.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable dw = getResources().getDrawable(R.mipmap.timer_normal);
        dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
        tvTimer.setCompoundDrawables(null, dw, null, null);
        ViewUtils.setText(tvValueHint, R.string.hint_level);
        ViewUtils.setText(tvTimerHint, R.string.hint_level);
        tvValueHint.setVisibility(View.VISIBLE);
        tvTimerHint.setVisibility(View.INVISIBLE);
        tvValue.setVisibility(View.VISIBLE);
        llTimerArea.setVisibility(View.INVISIBLE);
        tvTimerHour.setTextColor(Color.RED);
        tvTimerHour.setTextSize(getResources().getDimension(R.dimen.timer_text));
        tvTimerMinute.setTextColor(Color.WHITE);
        tvTimerMinute.setTextSize(getResources().getDimension(R.dimen.timer_text));
        switchCircleProgressMode(
                TFTCookerConstant.GEAR_MAX_VALUE,
                TFTCookerConstant.GEAR_MAX_VALUE,
                TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                getCurrentCookerGearValue(),
                canTouch,
                true);
        enableAllCookersTouch();
    }

    private int getCurrentCookerGearValue() {
        return getCookerGearValue(currentCooker);
    }

    private int getCookerGearValue(int cookerId) {
        int value = 0;
        switch (cookerId) {
            case COOKER_ID_Up_Left:
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Down_Left:
                value = cookerViewDownLeft.getGearValue();
                break;
            case COOKER_ID_Middle:
                break;
            case COOKER_ID_Up_Right:
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Down_Right:
                value = cookerViewDownRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                value = cookerViewDownRight.getGearValue();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                value = cookerViewDownLeft.getGearValue();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                value = cookerViewDownRight.getGearValue();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                value = cookerViewUpLeft.getGearValue();
                break;

            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                value = cookerViewUpLeft.getGearValue();
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                value = cookerViewDownRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                // value = cookerViewMiddle.getGearValue();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                //   value = cookerViewMiddle.getGearValue();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                //   value = cookerViewMiddle.getGearValue();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                //  value = cookerViewMiddle.getGearValue();
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                //  value = cookerViewMiddle.getGearValue();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                value = cookerViewUpRight.getGearValue();
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                value = cookerViewDownRight.getGearValue();
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
            case COOKER_ID_All://全部
                value = cookerViewUpRight.getGearValue();
                break;
        }
        return value;
    }

    private void doHoodStop() {
        tvValue.setText("0");
        if (workMode == WORK_MODE_SET_LIGHT) {
            doLightWOff();
        } else {
            hoodGear = 0;
            switchCircleProgressMode(TFTCookerConstant.HOOD_MAX_VALUE, hoodGear, true);
            ViewUtils.setText(tvValueHint, R.string.hint_extraction);
            if (TFTCookerApplication.getInstance().isHoodAuto()) {
                doAuto();
            }
            SetHoodSendGear(hoodGear);
        }
    }

    private void doLight() {   // 控制灯
        if (ClickTheHoobButton) {
            ClickTheHoobButton = false;
            switch (lightMode) {
                case LIGHT_MODE_OFF:
                    lightMode = lastLightModeUsed;
                    lightGear_blue = lastLightGearBlueUsed;
                    doLightWOn();
                    break;
                case LIGHT_MODE_LIGHT_Orange:
                    doLightWOn();
                    break;
                case LIGHT_MODE_LIGHT_Blue:
                    doLightWOn();
                    break;
            }

        } else {
            switch (lightMode) {
                case LIGHT_MODE_OFF:
                    lightMode = lastLightModeUsed;
                    lightGear_blue = lastLightGearBlueUsed;
                    doLightWOn();
                    break;
                case LIGHT_MODE_LIGHT_Orange:
                    lightMode = LIGHT_MODE_LIGHT_Blue;
                    doLightWOn();
                    break;
                case LIGHT_MODE_LIGHT_Blue:
                    lightMode = LIGHT_MODE_LIGHT_Orange;
                    doLightWOn();
                    break;
            }
        }

    }

    private void doLightWOn() {
        workMode = WORK_MODE_SET_LIGHT;
        if (lastLightGearBlueUsed == 0) {
            lastLightGearBlueUsed = 2;
        }
        lightGear_blue = lastLightGearBlueUsed;
        if (lightMode == LIGHT_MODE_LIGHT_Blue) {  // 显示蓝色 light C
            tvLight.setTextColor(getResources().getColor(R.color.colorBlue));  // 显示橙色 lightW
            Drawable dw = getResources().getDrawable(R.mipmap.light_press_c);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvLight.setCompoundDrawables(null, dw, null, null);
            IsLightBlue = true;
            SetLightSendedGear(lightGear_blue);

        } else {
            tvLight.setTextColor(getResources().getColor(R.color.colorOrange));  // 显示橙色 lightW
            Drawable dw = getResources().getDrawable(R.mipmap.light_press);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvLight.setCompoundDrawables(null, dw, null, null);
            IsLightBlue = false;
            SetLightSendedGear(lightGear_blue);
        }

        lastLightModeUsed = lightMode;

        //  圈圈里面的内容不变，不用显示brightness
        ViewUtils.setText(tvValueHint, R.string.hint_brightness);
        switchCircleProgressMode(
                TFTCookerConstant.HOOD_LIGHT_MAX_VALUE,
                TFTCookerConstant.HOOD_LIGHT_MAX_VALUE,
                0,
                0,
                lightGear_blue,
                true,
                true);

    }

    private void doLightWOff() {
        lightMode = LIGHT_MODE_OFF;
        lightGear_blue = 0;
        if (workMode == WORK_MODE_SET_LIGHT) {
            switchCircleProgressMode(TFTCookerConstant.HOOD_LIGHT_MAX_VALUE, lightGear_blue, false);
            ViewUtils.setText(tvValueHint, R.string.hint_brightness);
            tvLight.setTextColor(getResources().getColor(R.color.colorWhite));
            Drawable dw = getResources().getDrawable(R.mipmap.light_normal);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvLight.setCompoundDrawables(null, dw, null, null);
        }

        SetLightSendedGear(lightGear_blue);

        if (workMode == WORK_MODE_SET_LIGHT) {
            onViewClicked(tvHood);
        }
    }

    private void doAuto() {
        TFTCookerApplication.getInstance().setHoodAuto(!TFTCookerApplication.getInstance().isHoodAuto());

        int originalGear = hoodGear;
        setAutoUI();
        if (TFTCookerApplication.getInstance().isHoodAuto() && (originalGear > 0) && (hoodGear == 0)) {
            startHoodShutDownBlinkThread(getHoodAutoShutdownDelay(originalGear));
        }
    }

    private void setHoodAuto() {
        handler.sendEmptyMessageDelayed(HANDLER_SET_HOOD_AUTO, 800);
    }

    private void doSaveHoodAuto() {
        if (TFTCookerApplication.getInstance().isHoodAuto()) {
            hoodGear = getAutoHoodLevel();
            SetHoodSendGear(hoodGear);
        }
    }

    @Override
    protected void setAutoUI() {
        if (TFTCookerApplication.getInstance().isHoodAuto()) {
            tvAuto.setTextColor(getResources().getColor(R.color.colorGreen));
            Drawable dw = getResources().getDrawable(R.mipmap.auto_press_green);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvAuto.setCompoundDrawables(null, dw, null, null);
            hoodGear = getAutoHoodLevel();
        } else {
            tvAuto.setTextColor(getResources().getColor(R.color.colorWhite));
            Drawable dw = getResources().getDrawable(R.mipmap.auto_normal);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvAuto.setCompoundDrawables(null, dw, null, null);
        }

        if (workMode == WORK_MODE_HOOD) {
            ViewUtils.setText(tvValueHint, TFTCookerApplication.getInstance().isHoodAuto() ? R.string.hint_level : R.string.hint_extraction);
        }

        SetHoodSendGear(hoodGear);
        refreshHoodCircle();
    }

    private void refreshHoodCircle() {
        if (workMode == WORK_MODE_HOOD) {
            switchCircleProgressMode(TFTCookerConstant.HOOD_MAX_VALUE, hoodGear, true, true);
            if (hoodGear == TFTCookerConstant.HOOD_MAX_VALUE) {
                tvValue.setText(R.string.title_boost_short);
            } else {
                tvValue.setText(String.valueOf(hoodGear));
            }
        }
    }

    private CharSequence valueHintBeforePaused;

    private void doPause() {
        if (click_pause_flag) {  // 暂停的符号
            EventBus.getDefault().post(new SendPauseClickOrder(0));  // 开始所有倒计时
        } else {  // 尖的 符号
            EventBus.getDefault().post(new SendPauseClickOrder(1));  // 暂停所有倒计时
        }

        valueHintBeforePaused = tvValueHint.getText();
        tvValueHint.setText(R.string.title_paused);
        setSizeWhenPause();
    }

    private void doPause_recover() {
        tvPause.setTextColor(getResources().getColor(R.color.colorWhite));
        Drawable dw = getResources().getDrawable(R.mipmap.pause_normal);
        dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
        tvPause.setCompoundDrawables(null, dw, null, null);
        // tvPause.setText(R.string.btn_pause);

        EventBus.getDefault().post(new SendPauseClickOrder(0));  // 开始所有倒计时
        LockAllUI(true);
        setPauseFlag(false);
        tvValueHint.setText(valueHintBeforePaused);
        setSizeWhenRecoverFormPause();
    }

    private void doSetBoostMode_Linked_Cooker() {
        switch (currentCooker) {
            case COOKER_ID_Up_Left:

                cookerViewUpLeft_boost_flag = true;
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Left:

                cookerViewDownLeft_boost_flag = true;
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right:

                cookerViewDownRight_boost_flag = true;
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right:

                cookerViewUpRight_boost_flag = true;
                cookerViewUpRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时

                boost_flag_CookerView_Up_Left_Down_Left_SameTime = true;
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime:// 右上、右下 同时

                boost_flag_CookerView_Up_Right_Down_Right_SameTime = true;
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下

                boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下

                boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下

                boost_flag_CookerView_Up_Left_Down_Right = true;// 左上、右下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下

                boost_flag_CookerView_Up_Right_Down_Left = true; // 右上、左下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                boost_flag_CookerView_Up_Right_Up_Left = true;// 右上、左上
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                boost_flag_CookerView_Up_Right_And_Up_Left_Slip = true;// 右上、左上  滑动
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                boost_flag_CookerView_Down_Right_And_Down_Left_Slip = true; // 右下、左下  滑动
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                boost_flag_CookerView_Down_Right_Down_Left = true;// 左下、右下
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                boost_flag_CookerView_Up_Left_Down_Left_Up_Right = true; // 左上、左下、右上

                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right = true; // 右上、右下、左下

                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;

                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下

                boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = true; //左上、右上、右下、左下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下

                boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = true; //右上、左上、左下、右下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                boost_flag_CookerView_Up_Right_Down_Right_Up_Left = true; // 右上、右下、左上

                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下

                boost_flag_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_All://全部

                boost_flag_CookerView_All = true;//全部
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                boost_flag_CookerView_ALL_ANY = true;//全部 有区
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                boost_flag_CookerView_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                boost_flag_CookerView_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
        }
    }

    private void doSwitchBoostMode() {
        int value = 0;
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                value = cookerViewUpLeft.getGearValue();
                cookerViewUpLeft_boost_flag = true;
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Left:
                value = cookerViewDownLeft.getGearValue();
                cookerViewDownLeft_boost_flag = true;
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Middle:
                // value = cookerViewMiddle.getGearValue();
                // cookerViewMiddle_boost_flag = true;
                // cookerViewMiddle.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right:
                value = cookerViewDownRight.getGearValue();
                cookerViewDownRight_boost_flag = true;
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right:
                value = cookerViewUpRight.getGearValue();
                cookerViewUpRight_boost_flag = true;
                cookerViewUpRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Left_SameTime = true;
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime:// 右上、右下 同时
                value = cookerViewDownRight.getGearValue();
                boost_flag_CookerView_Up_Right_Down_Right_SameTime = true;
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;

            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下
                value = cookerViewDownRight.getGearValue();
                boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                value = cookerViewDownRight.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Right = true;// 左上、右下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_Up_Right_Down_Left = true; // 右上、左下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Right_Up_Left = true;// 右上、左上
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Right_And_Up_Left_Slip = true;// 右上、左上  滑动
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                value = cookerViewDownRight.getGearValue();
                boost_flag_CookerView_Down_Right_And_Down_Left_Slip = true; // 右下、左下  滑动
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                value = cookerViewDownRight.getGearValue();
                boost_flag_CookerView_Down_Right_Down_Left = true;// 左下、右下
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left__Middle:// 左上、中间
             /*   value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Up_Left__Middle = true;// 左上、中间
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
               /* value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Up_Left__Middle_Slip = true;// 左上、中间  滑动
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间
             /*   value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Left_Middle = true;// 左下、中间
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
               /* value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Left_Middle_Slip = true;// 左下、中间  滑动
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间
               /* value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Up_Right_Middle = true;// 右上、中间
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
             /*   value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Up_Right_Middle_Slip = true;// 右上、中间  滑动
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间
              /*  value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Right_Middle = true;// 右下、中间
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
             /*   value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Right_Middle_Slip = true;// 右下、中间  滑动
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Left_Up_Right = true; // 左上、左下、右上

                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下

                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;

                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = true; //左上、右上、右下、左下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = true; //右上、左上、左下、右下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Right_Down_Right_Up_Left = true; // 右上、右下、左上
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下

                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Right_Middle = true;// 左上、中间、右下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                //cookerViewMiddle.SetBoostWorkingStatus(true);

                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Right_Down_Left_Middle = true;// 右上、中间、左下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                // cookerViewMiddle.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Up_Right_Middle = true;// 左上、中间、右上
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_Down_Left_Down_Right_Middle = true;// 左下、中间、右下
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle://左上、左下、中间
               /* value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Down_Left_Middle = true;// 左上、中间、左下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);*/
                //cookerViewMiddle.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
               /* value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Up_Left_Midlle_Down_Left = true;//左上、中间、左下
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
              /*  value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Right_Middle_Up_Right = true;//右上、中间、右下
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
              /*  value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Right_Up_Right_Middle = true;// 右上、中间、右下
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
              /*  value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = true;// 右下、右上、中间、左下
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
               /* value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = true; // 右下、右上、中间、左上
                cookerViewDownRight.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
            /*    value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = true; // 左下、左上、中间、右上
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);*/

                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
              /*  value = cookerViewMiddle.getGearValue();
                boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = true; // 左下、左上、中间、右下
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);*/
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                value = cookerViewUpLeft.getGearValue();
                boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = true; // 左上、中间、右下、左下

                cookerViewUpLeft.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                value = cookerViewUpRight.getGearValue();
                boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = true; // 右上、中间、左下、右下
                cookerViewUpRight.SetBoostWorkingStatus(true);
                // cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = true; // 左下、中间、右上、左上
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                // cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                value = cookerViewDownRight.getGearValue();
                boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = true; // 右下、中间、左上、右上
                cookerViewDownRight.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_All://全部
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_All = true;//全部
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_ALL_ANY = true;//全部 有区
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                // cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                value = cookerViewDownLeft.getGearValue();
                boost_flag_CookerView_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                cookerViewDownLeft.SetBoostWorkingStatus(true);
                cookerViewUpLeft.SetBoostWorkingStatus(true);
                //  cookerViewMiddle.SetBoostWorkingStatus(true);
                cookerViewUpRight.SetBoostWorkingStatus(true);
                cookerViewDownRight.SetBoostWorkingStatus(true);
                break;

        }
        if (value != 10) {
            updateCircleProgressValue(10);
        }
        ChangeTheBoostButtonColor(ColorOfBoostButton_colorOrange);
    }

    private void ChangeTheBoostButtonColor(int v) {
        Drawable dw;
        switch (v) {
            case ColorOfBoostButton_colorOrange:
                tvBoost.setTextColor(getResources().getColor(R.color.colorOrange));
                dw = getResources().getDrawable(R.mipmap.boost_press);
                dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
                tvBoost.setCompoundDrawables(null, dw, null, null);
                break;
            case ColorOfBoostButton_colorWhite:
                tvBoost.setTextColor(getResources().getColor(R.color.colorWhite));
                dw = getResources().getDrawable(R.mipmap.boost_normal);
                dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
                tvBoost.setCompoundDrawables(null, dw, null, null);
                break;
        }

    }

    private void updateCircleProgressValue(int value) {
        int currentValue = (int) circleProgress.getValue();
        circleProgress.setValue(value);
        trianProgress.setValue(value);
    }

    private void updateCookerValue(int value) {
        if (workMode == WORK_MODE_HOB) {
            if (value == 10) {
                tvValue.setText(R.string.title_boost_short);
                setBoostIsWorking(true);
                ChangeTheBoostButtonColor(ColorOfBoostButton_colorOrange);
                doSetBoostMode_Linked_Cooker();

            } else {
                tvValue.setText(String.valueOf((int) value));
                ViewUtils.setText(tvValueHint, R.string.hint_level);
                ShowAutoFlag();
                setBoostIsWorking(false);
                ChangeTheBoostButtonColor(ColorOfBoostButton_colorWhite);
                if (CheckTheBoostStatus()) {
                    doUnSetBoostMode_Linker_Cooker();
                }
            }
            if (value == 9) {  // 从 booster 到 9 level
                ChangeTheBoostButtonColor(ColorOfBoostButton_colorWhite);
                setBoostIsWorking(false);
            }
            switch (currentCooker) {
                case COOKER_ID_Up_Left:
                    cookerViewUpLeft.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Left:
                    cookerViewDownLeft.setGear(value);
                    cookerViewDownLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Middle:
                    break;
                case COOKER_ID_Up_Right:
                    cookerViewUpRight.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Right:
                    cookerViewDownRight.setGear(value);
                    cookerViewDownRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Left_Down_Left:  // 左上，左下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Left_Down_Left || click_set_to_stop_flag_Up_Left_Down_Left) {
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);

                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Right_Down_Right || click_set_to_stop_flag_Up_Right_Down_Right) {
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                    cookerViewUpRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                    cookerViewUpRight.setGear(value);
                    cookerViewUpLeft.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                case COOKER_ID_Up_Left__Middle:// 左上、中间
                 /*   cookerViewUpLeft.setGear(value);
                    cookerViewMiddle.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewMiddle.SetBackGroundBlue();*/
                    break;
                case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                case COOKER_ID_Down_Left_Middle:// 左下、中间
                    /*cookerViewDownLeft.setGear(value);
                    cookerViewMiddle.setGear(value);
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewMiddle.SetBackGroundBlue();*/
                    break;
                case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                case COOKER_ID_Up_Right_Middle:// 右上、中间
                /*    cookerViewUpRight.setGear(value);
                    cookerViewMiddle.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewMiddle.SetBackGroundBlue();*/
                    break;
                case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                case COOKER_ID_Down_Right_Middle:// 右下、中间
                  /*  cookerViewDownRight.setGear(value);
                    cookerViewMiddle.setGear(value);
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewMiddle.SetBackGroundBlue();*/
                    break;
                case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    break;
                case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.setGear(value);
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                    cookerViewUpLeft.setGear(value);
                    cookerViewUpRight.setGear(value);
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();

                    break;
                case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();

                    break;
                case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);
                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right || click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right) { //左上、左下、右下、右上
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);
                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left || click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left) {//左上、右上、右下、左下
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;

                case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right || click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right) {//右上、左上、左下、右下
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Right_Up_Left_Down_Left://左上、左下 、右上
                    cookerViewUpLeft.setGear(value);
                    cookerViewUpRight.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left || click_set_to_stop_flag_Up_Right_Up_Left_Down_Left) {//左上、左下 、右上
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Left_Down_Left_Down_Right:// 左上、左下、右下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right || click_set_to_stop_flag_Up_Left_Down_Left_Down_Right) {// 左上、左下、右下
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);
                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right || click_set_to_stop_flag_Up_Left_Up_Right_Down_Right) { // 右上、右下、左上
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Right_Down_Right_Down_Left:// 右上、右下、左下
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);
                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Right_Down_Right_Down_Left || click_set_to_stop_flag_Up_Right_Down_Right_Down_Left) {// 右上、右下、左下
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();

                    break;
                case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();

                    if (TheLastClickedCooker == COOKER_ID_Up_Left || TheLastClickedCooker == COOKER_ID_Down_Left) {

                    } else {  // 最后点击的是圈圈

                    }
                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Up_Left_Down_Left_Middle || click_set_to_stop_flag_Up_Left_Down_Left_Middle) {// 左上、左下、中间
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    break;
                case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    if (TheLastClickedCooker == COOKER_ID_Up_Right || TheLastClickedCooker == COOKER_ID_Down_Right) {

                    } else { // 最后点击的是圈圈

                    }
                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);

                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Down_Right_Up_Right_Middle || click_set_to_stop_flag_Down_Right_Up_Right_Middle) {// 右上、右下、中间
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);

                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);

                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left || click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left) {// 右下、右上、中间、左下
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewUpLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);

                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left || click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left) {// 右下、右上、中间、左上
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);

                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right || click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right) {// 左下、左上、中间、右上
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewDownRight.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    break;
                case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                    cookerViewUpRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewDownRight.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    break;
                case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpRight.setGear(value);
                    cookerViewUpLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    break;
                case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                    cookerViewDownRight.setGear(value);
                    cookerViewUpRight.setGear(value);
                    cookerViewUpLeft.setGear(value);

                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    break;
                case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                    cookerViewUpLeft.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewDownRight.setGear(value);

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();

                    cookerViewUpLeft.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);

                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right || click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right) {// 左下、左上、中间、右下
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    cookerViewDownLeft.SetTvGearVisible(false);
                    cookerViewUpLeft.SetTvGearVisible(false);

                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);

                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_ALL_ALL_LEFT_NONE || click_set_to_stop_flag_ALL_ALL_LEFT_NONE) {//全部 左边无区
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpLeft.setGear(value);

                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);

                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_ALL_ALL_NONE_RIGHT || click_set_to_stop_flag_ALL_ALL_NONE_RIGHT) {   //全部 右边无区
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_All://全部
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpLeft.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();

                    cookerViewUpRight.SetTvGearVisible(false);
                    cookerViewDownRight.SetTvGearVisible(false);
                    cookerViewDownLeft.SetTvGearVisible(false);
                    cookerViewUpLeft.SetTvGearVisible(false);

                    tvRightMiddleGear.setVisibility(View.VISIBLE);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvRightMiddleGear, value);

                    tvLeftMiddleGear.setVisibility(View.VISIBLE);
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    setHobGearText(tvLeftMiddleGear, value);
                    setHobGearText(tvLeftMiddleGearWhenTimer, value);
                    setHobGearText(tvRightMiddleGearWhenTimer, value);
                    if (click_set_to_alert_flag_All || click_set_to_stop_flag_All) {//全部
                        tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                        tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                    cookerViewUpRight.setGear(value);
                    cookerViewDownRight.setGear(value);
                    cookerViewDownLeft.setGear(value);
                    cookerViewUpLeft.setGear(value);
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    cookerViewUpLeft.SetBackGroundBlue();


                    break;
            }
            SaveAllCookersData();  // 保存 所有数据 2018年11月24日10:25:48

        } else if (workMode == WORK_MODE_HOOD) {

        } else if (workMode == WORK_MODE_SET_TIMER) {

        }
    }

    private void ShowAutoFlag() {
        if (TFTCookerApplication.getInstance().isHoodAuto()) {
            autoFlag.setVisibility(View.VISIBLE);
        } else {
            autoFlag.setVisibility(View.INVISIBLE);
        }
    }

    private void doSwitchHobMode() {
        if (workMode != WORK_MODE_HOB) {
            switchWorkMode(WORK_MODE_HOB);
            enableAllCookersTouch();
            switch (currentCooker) {
                case COOKER_ID_Up_Left:
                    doSelectCooker(COOKER_ID_Up_Left, cookerViewUpLeft.getGearValue());
                    // switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    break;
                case COOKER_ID_Down_Left:
                    doSelectCooker(COOKER_ID_Down_Left, cookerViewDownLeft.getGearValue());
                    // switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownLeft.getGearValue(), true);
                    break;
                case COOKER_ID_Middle:
                    // doSelectCooker(COOKER_ID_Middle, cookerViewMiddle.getGearValue());
                    // switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewMiddle.getGearValue(), true);
                    break;
                case COOKER_ID_Up_Right:
                    doSelectCooker(COOKER_ID_Up_Right, cookerViewUpRight.getGearValue());
                    //switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    break;
                case COOKER_ID_Down_Right:
                    doSelectCooker(COOKER_ID_Down_Right, cookerViewDownRight.getGearValue());
                    //switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownRight.getGearValue(), true);
                    break;
                case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                case COOKER_ID_Up_Left__Middle:// 左上、中间
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                case COOKER_ID_Down_Left_Middle:// 左下、中间
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                case COOKER_ID_Up_Right_Middle:// 右上、中间
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                case COOKER_ID_Down_Right_Middle:// 右下、中间
                    //  switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewMiddle.getGearValue(), true);
                    //   showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                case COOKER_ID_Up_Left_Down_Left_Down_Right:// 左上、左下、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownLeft.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                    //  switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewMiddle.getGearValue(), true);
                    //  showHobOperateMenu();
                    break;
                case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left: // 右下、右上、中间、左上
                   /* switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewMiddle.getGearValue(), true);
                    showHobOperateMenu();*/
                    break;
                case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                  /*  switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewMiddle.getGearValue(), true);
                    showHobOperateMenu();*/
                    break;
                case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewUpRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                case COOKER_ID_All://全部
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, cookerViewDownRight.getGearValue(), true);
                    showHobOperateMenu();
                    break;
                default:
                    switchCircleProgressMode(
                            TFTCookerConstant.GEAR_MAX_VALUE,
                            TFTCookerConstant.GEAR_MAX_VALUE,
                            TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                            TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                            0,
                            false,
                            true);
                    ViewUtils.setText(tvValueHint, R.string.hint_level);
                    break;
            }
        }
        ShowAutoFlag();
    }

    private void disableAllCookersTouch() {
        cookerViewUpLeft.disableCookerTouch();
        cookerViewDownLeft.disableCookerTouch();
        cookerViewUpRight.disableCookerTouch();
        cookerViewDownRight.disableCookerTouch();
    }

    private void enableAllCookersTouch() {
        cookerViewUpLeft.enabletCookerTouch();
        cookerViewDownLeft.enabletCookerTouch();
        cookerViewUpRight.enabletCookerTouch();
        cookerViewDownRight.enabletCookerTouch();
    }

    private void doSwitchHoodMode() {
        if (workMode != WORK_MODE_HOOD) {
            switchWorkMode(WORK_MODE_HOOD);
            disableAllCookersTouch();
            ViewUtils.setText(tvValueHint, R.string.hint_extraction);
            restoreSelectRange();
            setAutoUI();
            if (lightMode == LIGHT_MODE_OFF) {
                Drawable dw = getResources().getDrawable(R.mipmap.light_normal);
                dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
                tvLight.setCompoundDrawables(null, dw, null, null);
            }
        }
    }

    @Override
    protected int getTotalPower() {
        return cookerViewUpLeft.getGearValue()
                + cookerViewDownLeft.getGearValue()
                + cookerViewUpRight.getGearValue()
                + cookerViewDownRight.getGearValue();
    }

    private void doStopCooker() {

        restoreSelectRange();
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                cookerViewUpLeft.powerOff();
                cookerViewUpLeft_boost_flag = false;
                break;
            case COOKER_ID_Down_Left:
                cookerViewDownLeft.powerOff();
                cookerViewDownLeft_boost_flag = false;
                break;
            case COOKER_ID_Middle:
                break;
            case COOKER_ID_Up_Right:
                cookerViewUpRight.powerOff();
                cookerViewUpRight_boost_flag = false;
                break;
            case COOKER_ID_Down_Right:
                cookerViewDownRight.powerOff();
                cookerViewDownRight_boost_flag = false;
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击
                cookerViewUpLeft.powerOff();
                cookerViewDownLeft.powerOff();
                Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
                boost_flag_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下 同时
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                cookerViewUpRight.powerOff();
                cookerViewDownRight.powerOff();
                Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下
                boost_flag_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下 同时
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下
                boost_flag_CookerView_Up_Left_Down_Left = false;  // 左上，左下
                cookerViewUpLeft.powerOff();
                cookerViewDownLeft.powerOff();
                Select_CookerView_Up_Left_Down_Left = false;  // 显示各自的数字
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);

                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Left_Down_Left) {
                    click_set_to_alert_flag_Up_Left_Down_Left = false;// 左下 左上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left) {
                    click_set_to_stop_flag_Up_Left_Down_Left = false;// 左下 左上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下

                cookerViewUpRight.powerOff();
                cookerViewDownRight.powerOff();
                Select_CookerView_Up_Right_Down_Right = false;  // 显示各自的数字
                boost_flag_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Right_Down_Right) {
                    click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Right_Down_Right) {
                    click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                Select_CookerView_Up_Left_Down_Right = false;
                boost_flag_CookerView_Up_Left_Down_Right = false;// 左上、右下
                cookerViewUpLeft.powerOff();
                cookerViewDownRight.powerOff();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                cookerViewUpRight.powerOff();
                cookerViewDownLeft.powerOff();
                Select_CookerView_Up_Right_Down_Left = false;
                boost_flag_CookerView_Up_Right_Down_Left = false; // 右上、左下
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                Select_CookerView_Up_Right_Up_Left = false;
                boost_flag_CookerView_Up_Right_Up_Left = false;// 右上、左上
                cookerViewUpRight.powerOff();
                cookerViewUpLeft.powerOff();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
                boost_flag_CookerView_Up_Right_And_Up_Left_Slip = false;// 右上、左上  滑动
                cookerViewUpRight.powerOff();
                cookerViewUpLeft.powerOff();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                boost_flag_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                cookerViewDownLeft.powerOff();
                cookerViewDownRight.powerOff();
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                Select_CookerView_Down_Right_Down_Left = false;
                boost_flag_CookerView_Down_Right_Down_Left = false;// 左下、右下
                cookerViewDownLeft.powerOff();
                cookerViewDownRight.powerOff();
                break;

            case COOKER_ID_Up_Left__Middle:// 左上、中间
                Select_CookerView_Up_Left__Middle = false;
                boost_flag_CookerView_Up_Left__Middle = false;// 左上、中间
                //  cookerViewMiddle.powerOff();
                cookerViewUpLeft.powerOff();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                Select_CookerView_Up_Left__Middle_Slip = false;// 左上、中间 滑动
                boost_flag_CookerView_Up_Left__Middle_Slip = false;// 左上、中间  滑动
                //  cookerViewMiddle.powerOff();
                cookerViewUpLeft.powerOff();
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                Select_CookerView_Down_Left_Middle = false;
                boost_flag_CookerView_Down_Left_Middle = false;// 左下、中间
                //  cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                Select_CookerView_Down_Left_Middle_Slip = false;// 左下、中间 滑动
                boost_flag_CookerView_Down_Left_Middle_Slip = false;// 左下、中间  滑动
                // cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                Select_CookerView_Up_Right_Middle = false;
                boost_flag_CookerView_Up_Right_Middle = false;// 右上、中间
                //  cookerViewMiddle.powerOff();
                cookerViewUpRight.powerOff();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                Select_CookerView_Up_Right_Middle_Slip = false;// 右上、中间 滑动
                boost_flag_CookerView_Up_Right_Middle_Slip = false;// 右上、中间  滑动
                //    cookerViewMiddle.powerOff();
                cookerViewUpRight.powerOff();
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                Select_CookerView_Down_Right_Middle = false;
                boost_flag_CookerView_Down_Right_Middle = false;// 右下、中间
                //  cookerViewMiddle.powerOff();
                cookerViewDownRight.powerOff();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                Select_CookerView_Down_Right_Middle_Slip = false;// 右下、中间 滑动
                boost_flag_CookerView_Down_Right_Middle_Slip = false;// 右下、中间  滑动
                //    cookerViewMiddle.powerOff();
                cookerViewDownRight.powerOff();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                Select_CookerView_Up_Left_Down_Right_Middle = false;
                boost_flag_CookerView_Up_Left_Down_Right_Middle = false;// 左上、中间、右下
                cookerViewUpLeft.powerOff();
                cookerViewDownRight.powerOff();
                //    cookerViewMiddle.powerOff();
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                Select_CookerView_Up_Right_Down_Left_Middle = false;
                boost_flag_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
                cookerViewDownLeft.powerOff();
                cookerViewUpRight.powerOff();
                //    cookerViewMiddle.powerOff();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                Select_CookerView_Up_Left_Up_Right_Middle = false;
                boost_flag_CookerView_Up_Left_Up_Right_Middle = false;// 左上、中间、右上
                cookerViewUpLeft.powerOff();
                cookerViewUpRight.powerOff();
                //   cookerViewMiddle.powerOff();
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                Select_CookerView_Down_Left_Down_Right_Middle = false;
                boost_flag_CookerView_Down_Left_Down_Right_Middle = false;// 左下、中间、右下
                cookerViewDownLeft.powerOff();
                cookerViewDownRight.powerOff();
                //     cookerViewMiddle.powerOff();
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                Select_CookerView_Up_Left_Down_Left_Middle = false;
                boost_flag_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                //   cookerViewMiddle.powerOff();
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);

                if (click_set_to_alert_flag_Up_Left_Down_Left_Middle) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Middle) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                Select_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                boost_flag_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                //   cookerViewMiddle.powerOff();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                Select_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                boost_flag_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                //  cookerViewMiddle.powerOff();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                Select_CookerView_Down_Right_Up_Right_Middle = false;
                boost_flag_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                //  cookerViewMiddle.powerOff();
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false; // 左上、左下、右下、右上
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();

                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);

                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);

                if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();

                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);

                if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
                    click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
                    click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();

                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
                    click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
                    click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                Select_CookerView_Up_Right_Up_Left_Down_Left = false; //左上、左下 、右上
                boost_flag_CookerView_Up_Left_Down_Left_Up_Right = false; // 左上、左下、右上
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                cookerViewUpRight.powerOff();

                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left) {
                    click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left) {
                    click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right:// 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                cookerViewDownRight.powerOff();

                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }

                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;    // 右上、右下、左上
                boost_flag_CookerView_Up_Right_Down_Right_Up_Left = false; // 右上、右下、左上
                cookerViewUpLeft.powerOff();
                cookerViewUpRight.powerOff();
                cookerViewDownRight.powerOff();

                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right) {
                    click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right) {
                    click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }

                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left:// 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right_Down_Left = false;
                boost_flag_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                cookerViewDownLeft.powerOff();
                cookerViewUpRight.powerOff();
                cookerViewDownRight.powerOff();
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Up_Right_Down_Right_Down_Left) {
                    click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Up_Right_Down_Right_Down_Left) {
                    click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;
                boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                //  cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false;
                boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                // cookerViewMiddle.powerOff();
                cookerViewUpLeft.powerOff();
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
                boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                //   cookerViewMiddle.powerOff();
                cookerViewUpRight.powerOff();
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right) {
                    click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right) {
                    click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right:// 左下、左上、中间、右下
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                //   cookerViewMiddle.powerOff();
                cookerViewDownRight.powerOff();
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);

                if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right) {
                    click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right) {
                    click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
                boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; // 左上、中间、右下、左下
                cookerViewUpLeft.powerOff();
                //  cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                cookerViewDownRight.powerOff();
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
                boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; // 右上、中间、左下、右下
                cookerViewUpRight.powerOff();
                //   cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                cookerViewDownRight.powerOff();
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
                boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; // 左下、中间、右上、左上
                cookerViewDownLeft.powerOff();
                //   cookerViewMiddle.powerOff();
                cookerViewUpRight.powerOff();
                cookerViewUpLeft.powerOff();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
                boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; // 右下、中间、左上、右上
                cookerViewDownRight.powerOff();
                //  cookerViewMiddle.powerOff();
                cookerViewUpRight.powerOff();
                cookerViewUpLeft.powerOff();
                break;

            case COOKER_ID_All://全部
                Select_CookerView_All = false;
                boost_flag_CookerView_All = false;//全部
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                // cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_All) {
                    click_set_to_alert_flag_All = false;//全部
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_All) {
                    click_set_to_stop_flag_All = false;//全部
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                Select_CookerView_ALL_ALL_ANY = false;//全部 有区
                boost_flag_CookerView_ALL_ANY = false;//全部 有区
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                //   cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                boost_flag_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                //  cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpLeft.SetTvGearVisible(false);
                cookerViewDownLeft.SetTvGearVisible(false);
                if (click_set_to_alert_flag_ALL_ALL_LEFT_NONE) {
                    click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_ALL_ALL_LEFT_NONE) {
                    click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                    tvLeftMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvLeftMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    leftLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                boost_flag_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                cookerViewDownRight.powerOff();
                cookerViewUpRight.powerOff();
                //   cookerViewMiddle.powerOff();
                cookerViewDownLeft.powerOff();
                cookerViewUpLeft.powerOff();
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
                cookerViewUpRight.SetTvGearVisible(false);
                cookerViewDownRight.SetTvGearVisible(false);
                if (click_set_to_alert_flag_ALL_ALL_NONE_RIGHT) {
                    click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                if (click_set_to_stop_flag_ALL_ALL_NONE_RIGHT) {
                    click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                    tvRightMiddleGearWhenTimer.setVisibility(View.INVISIBLE);
                    tvRightMiddleClockWhenTimer.setVisibility(View.INVISIBLE);
                    rightLineLong.setBackgroundColor(Color.WHITE);
                }
                break;
        }
    }

    private void PowerOffAll() {
        if (workMode == WORK_MODE_SET_TIMER_MINUTE
                || workMode == WORK_MODE_SET_TIMER_HOUR
                || workMode == WORK_MODE_SET_TIMER) {
            doCancelTimer();
            ReadyToSetTimer = false;
        }

        for (int i = 0; i < The_Total_Cookers; i++) {
            currentCooker = i;
            doStopCooker();
        }
        cookerViewUpLeft.doPowerOff();
        cookerViewUpRight.doPowerOff();
        cookerViewDownLeft.doPowerOff();
        cookerViewDownRight.doPowerOff();

        switchWorkMode(WORK_MODE_HOB);
        currentCooker = COOKER_ID_NONE;

        hoodGear = 0;
        SetHoodSendGear(hoodGear);
        lightMode = LIGHT_MODE_OFF;
        lightGear_blue = 0; //
        lightGear_orange = 0; //
        lightCearValue_sended = 0;  //
        click_set_to_alert_flag = false;
        click_set_to_stop_flag = false;
        click_lock_flag = false;
        setPauseFlag(false);
        switchOffTimerMode(false);
    }

    private void SetAllCookerIsGray() {

        cookerViewUpLeft.SetBackGroundGray();
        cookerViewDownLeft.SetBackGroundGray();
        cookerViewUpRight.SetBackGroundGray();
        cookerViewDownRight.SetBackGroundGray();
        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);  // 更新 2018年11月21日9:08:07
        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
    }

    private void doPowerOnCooker(int id) {
        if (workMode == WORK_MODE_HOB || workMode == WORK_MODE_SET_TIMER_MINUTE || workMode == WORK_MODE_SET_TIMER_HOUR) {  // 处在 定时设置状态也可以选择炉头，2018年11月23日16:09:46

            if (handler.hasMessages(UPDATE_CIRCLE_PROGRESS_WHEN_POWER_OFF)) {
                handler.removeMessages(UPDATE_CIRCLE_PROGRESS_WHEN_POWER_OFF);
            }
            displayNoPanUI(false);
            if (ReadyToSelect_onCookerSelected) {
                SetAllCookerIsGray();
            }
            // doUnLinkAllCooker();
            doSelectAfterPowerOnSingleCooker(id);
            currentCooker = id;
            if (ReadyToSetTimer) {  // 此时 处在 定时设置状态

            } else {
                switchCircleProgressMode(
                        TFTCookerConstant.GEAR_MAX_VALUE,
                        TFTCookerConstant.GEAR_MAX_SELECT_VALUE,
                        TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                        TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                        TFTCookerConstant.GEAR_DEFAULT_VALUE,
                        true,
                        false);
                showHobOperateMenu();
            }
            switch (id) {
                case COOKER_ID_Up_Left:
                    cookerViewUpLeft.setGear(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    cookerViewUpLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Left:
                    cookerViewDownLeft.setGear(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    cookerViewDownLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Right:
                    cookerViewUpRight.setGear(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    cookerViewUpRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Right:
                    cookerViewDownRight.setGear(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    cookerViewDownRight.SetBackGroundBlue();
                    break;
            }

            EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_NO_STAND_BY)); // 点击了 炉头，炉头开，不能锁屏
            ChangeTheBoostButtonColor(ColorOfBoostButton_colorWhite);
            SaveAllCookersData();
            doSaveHoodAuto();
            activeCookers.push(id);
        }
        if (workMode == WORK_MODE_SET_TIMER_MINUTE || workMode == WORK_MODE_SET_TIMER_HOUR) {
            switchTimerSetMode(workMode);
        }
    }

    private void ResetValueOfFlag() {
        if (cookerViewUpLeft.getGearValue() == 0) {  // // 左上
            if (Select_CookerView_Up_Left_Down_Left || Select_CookerView_Up_Left_Down_Left_Middle || Select_CookerView_Up_Right_Up_Left_Down_Left
                    || Select_CookerView_Up_Left_Down_Left_Down_Right || Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right
                    || Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right || Select_CookerView_Down_Left_Up_Left_Middle_Up_Right
                    || Select_CookerView_Down_Left_Up_Left_Middle_Down_Right || Select_CookerView_All
                    || Select_CookerView_ALL_ALL_LEFT_NONE) {
                if (Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_All) {
                    tvRightMiddleGear.setVisibility(View.INVISIBLE);
                }
                Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
                Select_CookerView_Up_Right_Up_Left_Down_Left = false; //左上、左下 、右上
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下
                Select_CookerView_All = false;//全部
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
            }
            Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
            Select_CookerView_Up_Left_Down_Right = false;// 左上、右下  同时
            Select_CookerView_Up_Right_Up_Left = false;// 右上、左上   同时
            Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
            Select_CookerView_ALL_ALL_ANY = false;//全部 有区
            Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
            Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
            Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
        }
        if (cookerViewDownLeft.getGearValue() == 0) {// 左下
            if (Select_CookerView_Up_Left_Down_Left || Select_CookerView_Up_Left_Down_Left_Middle || Select_CookerView_Up_Right_Up_Left_Down_Left
                    || Select_CookerView_Up_Left_Down_Left_Down_Right || Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right
                    || Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right || Select_CookerView_Down_Left_Up_Left_Middle_Up_Right
                    || Select_CookerView_Down_Left_Up_Left_Middle_Down_Right || Select_CookerView_All
                    || Select_CookerView_ALL_ALL_LEFT_NONE) {
                if (Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_All) {
                    tvRightMiddleGear.setVisibility(View.INVISIBLE);
                }
                Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
                Select_CookerView_Up_Right_Up_Left_Down_Left = false; //左上、左下 、右上
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下
                Select_CookerView_All = false;//全部
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                Select_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
            }
            Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
            Select_CookerView_Up_Right_Down_Left = false; // 右上、左下  同时
            Select_CookerView_Down_Right_Down_Left = false;// 左下、右下  同时
            Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
            Select_CookerView_ALL_ALL_ANY = false;//全部 有区
            Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
            Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
            Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
        }

        if (cookerViewUpRight.getGearValue() == 0) {// 右上
            if (Select_CookerView_Up_Right_Down_Right || Select_CookerView_Down_Right_Up_Right_Middle || Select_CookerView_Up_Right_Down_Right_Down_Left ||
                    Select_CookerView_Up_Left_Up_Right_Down_Right || Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left
                    || Select_CookerView_Down_Right_Up_Right_Middle_Down_Left || Select_CookerView_Down_Right_Up_Right_Middle_Up_Left || Select_CookerView_All
                    || Select_CookerView_ALL_ALL_NONE_RIGHT) {
                if (Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_All) {
                    tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                }
                Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下 滑动
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;    // 右上、右下、左上
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
                Select_CookerView_All = false;//全部
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
            }
            Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下
            Select_CookerView_Up_Right_Down_Left = false; // 右上、左下  同时
            Select_CookerView_Up_Right_Up_Left = false;// 右上、左上   同时
            Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
            Select_CookerView_ALL_ALL_ANY = false;//全部 有区
            Select_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
            Select_CookerView_Up_Left_Up_Right_Middle = false;// 左上、中间、右上
            Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
            Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
            Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
        }
        if (cookerViewDownRight.getGearValue() == 0) {// 右下
            if (Select_CookerView_Up_Right_Down_Right || Select_CookerView_Down_Right_Up_Right_Middle || Select_CookerView_Up_Right_Down_Right_Down_Left ||
                    Select_CookerView_Up_Left_Up_Right_Down_Right || Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left
                    || Select_CookerView_Down_Right_Up_Right_Middle_Down_Left || Select_CookerView_Down_Right_Up_Right_Middle_Up_Left || Select_CookerView_All
                    || Select_CookerView_ALL_ALL_NONE_RIGHT) {
                if (Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_All) {
                    tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                }
                Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下 滑动
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;    // 右上、右下、左上
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
                Select_CookerView_All = false;//全部
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                tvRightMiddleGear.setVisibility(View.INVISIBLE);
            }
            Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下
            Select_CookerView_Up_Left_Down_Right = false;// 左上、右下  同时
            Select_CookerView_Down_Right_Down_Left = false;// 左下、右下  同时
            Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
            Select_CookerView_ALL_ALL_ANY = false;//全部 有区
            Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
            Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
            Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
        }

    }

    private void doPowerOffCooker(int cooker, int powerOffType) {

        doingPowerOffCooker = true;
        try {
            restoreSelectRange();
            ResetValueOfFlag();
            boolean cookerSelected = isCookerSelected(cooker);
            if (cookerSelected) {
                displayNoPanUI(false);
            }

            if (powerOffType == DoTimerStop && ReadyToSetTimer) {
                if (cookerSelected) {
                    // 在设置 Timer 界面，如果当前炉头之前已经设置了定时，并到期关闭了炉头，
                    // 则退出 Timer 设置界面
                    doSwitchHobMode();
                    switchOffTimerMode(true);
                    ReadyToSetTimer = false;
                }
            }
            if (cookerSelected) {
                currentCooker = COOKER_ID_NONE;
                if (!ReadyToSetTimer && workMode == WORK_MODE_HOB) {
                    switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO, false);
                }
            }
            if (workMode == WORK_MODE_HOB && powerOffType == DoAllStop) {
                showHobOperateMenu();
            }

            click_set_to_alert_flag = false;
            click_set_to_stop_flag = false;

            SaveAllCookersData();
            activeCookers.pop(cooker);

            if (activeCookers.size() > 0) {
                if (cookerSelected) {
                    int cookerId = activeCookers.pop();
                    doSelectCooker(cookerId, getCookerGearValue(cookerId));
                }
            } else {
                if (TFTCookerApplication.getInstance().isHoodAuto()) {
                    int shutDownDelay = getHoodAutoShutdownDelay(hoodGear);
                    startHoodShutDownBlinkThread(shutDownDelay);
                }
                if (workMode == WORK_MODE_HOB) {
                    showHobOperateMenu();
                }
                handler.sendEmptyMessageDelayed(UPDATE_CIRCLE_PROGRESS_WHEN_POWER_OFF, 200);
            }
        } finally {
            doingPowerOffCooker = false;
        }
    }

    private void DoAllSetGray() {
        if (ReadyToSelect_onCookerSelected) {
            cookerViewUpLeft.SetBackGroundGray();
            cookerViewDownLeft.SetBackGroundGray();
            cookerViewUpRight.SetBackGroundGray();
            cookerViewDownRight.SetBackGroundGray();
            tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);  // 更新 2018年11月14日21:05:26
            tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
        }
    }

    private void DoUnlink_SetGray_SetID(int id) {

        DoAllSetGray();
        doUnLinkAllCooker();
        currentCooker = id;
    }

    private void doOpenAgainTheTwoCookerOfLeft() {
        DoAllSetGray();
        currentCooker = COOKER_ID_Up_Left_Down_Left;
        cookerViewUpLeft.SetBackGroundBlue();
        cookerViewDownLeft.SetBackGroundBlue();
        tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
    }

    private void doOpenAgainTheTwoCookerOfRight() {
        DoAllSetGray();
        currentCooker = COOKER_ID_Up_Right_Down_Right;
        cookerViewUpRight.SetBackGroundBlue();
        cookerViewDownRight.SetBackGroundBlue();
        tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
    }

    private void doSelectCookerWhenCookerIsOpened(int id) {   // 更新 2018年11月14日10:54:01
        switch (currentCooker) {
            case COOKER_ID_Up_Left_Down_Left:// 左上 左下
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {
                    DoAllSetGray();
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                } else if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        doOpenAgainTheTwoCookerOfRight();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    DoAllSetGray();
                    currentCooker = id;
                }

                break;
            case COOKER_ID_Up_Right_Down_Right: // 右上 右下
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    DoAllSetGray();
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                } else if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        doOpenAgainTheTwoCookerOfLeft();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    DoAllSetGray();
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        doOpenAgainTheTwoCookerOfRight();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        //   tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    // tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    DoAllSetGray();
                    currentCooker = id;
                }
                // DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        doOpenAgainTheTwoCookerOfLeft();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        //   tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    //tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    DoAllSetGray();
                    currentCooker = id;
                }
                // DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Left_Down_Right = false;// 左上、右下
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Right_Down_Left = false; // 右上、左下
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Right_Up_Left = false;// 右上、左上
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Down_Right_Down_Left = false;// 左下、右下
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
                break;
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        doOpenAgainTheTwoCookerOfRight();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Up_Left__Middle = false;// 左上、中间
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        doOpenAgainTheTwoCookerOfRight();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Up_Left__Middle_Slip = false;// 左上、中间 滑动
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        doOpenAgainTheTwoCookerOfRight();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Down_Left_Middle = false;// 左下、中间
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        doOpenAgainTheTwoCookerOfRight();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Down_Left_Middle_Slip = false;// 左下、中间 滑动
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        doOpenAgainTheTwoCookerOfLeft();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Up_Right_Middle = false;// 右上、中间
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        doOpenAgainTheTwoCookerOfLeft();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Up_Right_Middle_Slip = false;// 右上、中间 滑动
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        doOpenAgainTheTwoCookerOfLeft();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Down_Right_Middle = false;// 右下、中间
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        doOpenAgainTheTwoCookerOfLeft();
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Down_Right_Middle_Slip = false;// 右下、中间 滑动
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;

                } else if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                }
                break;

            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;    // 右上、右下、左上
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                Select_CookerView_Up_Right_Up_Left_Down_Left = false; //左上、左下 、右上
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right:// 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Left_Down_Right_Middle = false;// 左上、中间、右下
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Up_Left_Up_Right_Middle = false;// 左上、中间、右上
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                DoUnlink_SetGray_SetID(id);
                Select_CookerView_Down_Left_Down_Right_Middle = false;// 左下、中间、右下
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        DoAllSetGray();
                        currentCooker = COOKER_ID_Up_Right_Down_Right;
                        cookerViewUpRight.SetBackGroundBlue();
                        cookerViewDownRight.SetBackGroundBlue();
                        tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        DoAllSetGray();
                        currentCooker = COOKER_ID_Up_Left_Down_Left;
                        cookerViewUpLeft.SetBackGroundBlue();
                        cookerViewDownLeft.SetBackGroundBlue();
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                Select_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下
                        //  DoAllSetGray();
                        currentCooker = COOKER_ID_Up_Right_Down_Right;
                        cookerViewUpRight.SetBackGroundBlue();
                        cookerViewDownRight.SetBackGroundBlue();
                        tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {
                        DoAllSetGray();
                        currentCooker = COOKER_ID_Up_Left_Down_Left;
                        cookerViewUpLeft.SetBackGroundBlue();
                        cookerViewDownLeft.SetBackGroundBlue();
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                } else {
                    DoUnlink_SetGray_SetID(id);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left: // 右下、右上、中间、左上
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
                DoUnlink_SetGray_SetID(id);
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
                DoUnlink_SetGray_SetID(id);
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
                DoUnlink_SetGray_SetID(id);
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
                DoUnlink_SetGray_SetID(id);
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {
                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;
                } else {
                    currentCooker = id;
                }
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                Select_CookerView_ALL_ALL_ANY = false;//全部 有区
                DoUnlink_SetGray_SetID(id);
                break;
            case COOKER_ID_All://全部
                Select_CookerView_All = false;//全部
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                DoAllSetGray();
                if ((id == COOKER_ID_Up_Left) || (id == COOKER_ID_Down_Left)) {

                    cookerViewUpLeft.SetBackGroundBlue();
                    cookerViewDownLeft.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    currentCooker = COOKER_ID_Up_Left_Down_Left;

                } else if ((id == COOKER_ID_Up_Right) || (id == COOKER_ID_Down_Right)) {
                    cookerViewUpRight.SetBackGroundBlue();
                    cookerViewDownRight.SetBackGroundBlue();
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                    currentCooker = COOKER_ID_Up_Right_Down_Right;
                } else {
                    currentCooker = id;
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                }
                break;
            default:
                if (id == COOKER_ID_Up_Left || id == COOKER_ID_Down_Left) {
                    if (Select_CookerView_Up_Left_Down_Left) {// 左下 左上
                        DoAllSetGray();
                        cookerViewUpLeft.SetBackGroundBlue();
                        cookerViewDownLeft.SetBackGroundBlue();
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                        currentCooker = COOKER_ID_Up_Left_Down_Left;
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        if (Select_CookerView_Up_Right_Down_Right) {// 右上 右下
                            tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                        }
                    }
                } else if (id == COOKER_ID_Up_Right || id == COOKER_ID_Down_Right) {// 右上 右下
                    if (Select_CookerView_Up_Right_Down_Right) {
                        DoAllSetGray();
                        currentCooker = COOKER_ID_Up_Right_Down_Right;
                        cookerViewUpRight.SetBackGroundBlue();
                        cookerViewDownRight.SetBackGroundBlue();
                        tvRightMiddleGear.setBackgroundColor(cooker_color_blue);
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    } else {
                        DoAllSetGray();
                        currentCooker = id;
                        if (Select_CookerView_Up_Left_Down_Left) {// 左下 左上
                            tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                        }
                    }
                } else {
                    DoAllSetGray();
                    currentCooker = id;
                    if (Select_CookerView_Up_Right_Down_Right) {// 右上 右下
                        tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                    if (Select_CookerView_Up_Left_Down_Left) {// 左下 左上
                        tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                    }
                }
                break;
        }
    }

    private void doSelectCooker(int id, int value) {
        if (workMode == WORK_MODE_HOB || workMode == WORK_MODE_SET_TIMER_MINUTE || workMode == WORK_MODE_SET_TIMER_HOUR) {  // 处在定时设置时也可选择炉头，更新 2018年11月23日16:06:01
            displayNoPanUI(false);
            doSelectAfterPowerOnSingleCooker(id);
            doSelectCookerWhenCookerIsOpened(id);

            // showHobOperateMenu();
            this.setBoostIsWorking(value == TFTCookerConstant.GEAR_MAX_VALUE);
            if (ReadyToSetTimer) {  // 当前处在 设置 定时操作状态

            } else {
                switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, value, true, true);
                showHobOperateMenu();
            }
            EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_NO_STAND_BY)); // 点击了 炉头，不能锁屏

            switch (currentCooker) {  // 先点击不变色，对方变成灰色。
                case COOKER_ID_Up_Left:
                    cookerViewUpLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Left:
                    cookerViewDownLeft.SetBackGroundBlue();
                    break;
                case COOKER_ID_Up_Right:
                    cookerViewUpRight.SetBackGroundBlue();
                    break;
                case COOKER_ID_Down_Right:
                    cookerViewDownRight.SetBackGroundBlue();
                    break;
            }

            if (workMode == WORK_MODE_SET_TIMER_MINUTE || workMode == WORK_MODE_SET_TIMER_HOUR) {
                switchTimerSetMode(workMode);
            }
            activeCookers.bringToTop(id);
        }

    }

    private void switchWorkMode(int mode) {
        switch (mode) {
            case WORK_MODE_HOB:
                workMode = WORK_MODE_HOB;
                updateHobButtonUI(true);
                updateHoodButtonUI(false);
                showHobOperateMenu();
                hideHoodOperateMenu();

                tvValueHint.setText(R.string.title_level);

                EventBus.getDefault().post(new WorkModeChangedEvent(WorkModeChangedEvent.ORDER_CHANGED_TO_HOB));
                break;
            case WORK_MODE_HOOD:
                workMode = WORK_MODE_HOOD;
                updateHobButtonUI(false);
                updateHoodButtonUI(true);
                showHoodOperateMenu();
                hideHobOperateMenu();
                SetAllCookerIsGray();

                EventBus.getDefault().post(new WorkModeChangedEvent(WorkModeChangedEvent.ORDER_CHANGED_TO_HOOD));
                break;
        }
    }

    private void updateHobButtonUI(boolean isSelected) {
        if (isSelected) {
            tvHob.setTextColor(getResources().getColor(R.color.colorBlue));
            Drawable dw = getResources().getDrawable(R.mipmap.hob_press);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvHob.setCompoundDrawables(null, dw, null, null);
        } else {
            tvHob.setTextColor(getResources().getColor(R.color.colorWhite));
            Drawable dw = getResources().getDrawable(R.mipmap.hob_normal);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvHob.setCompoundDrawables(null, dw, null, null);
        }
    }

    private void updateHoodButtonUI(boolean isSelected) {
        if (isSelected) {
            tvHood.setTextColor(getResources().getColor(R.color.colorBlue));
            Drawable dw = getResources().getDrawable(R.mipmap.hood_press);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvHood.setCompoundDrawables(null, dw, null, null);
        } else {
            tvHood.setTextColor(getResources().getColor(R.color.colorWhite));
            Drawable dw = getResources().getDrawable(R.mipmap.hood_normal);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            tvHood.setCompoundDrawables(null, dw, null, null);
        }
    }

    private void showHobOperateMenu() {
        if (currentCooker != COOKER_ID_NONE) {  // 开机并工作后
            FrameLayoutButtonB10.setVisibility(View.INVISIBLE);
            FrameLayoutButtonB11.setVisibility(View.VISIBLE);
            FrameLayoutButtonB18.setVisibility(View.INVISIBLE);
            FrameLayoutButtonC12.setVisibility(View.INVISIBLE);

        } else {    // 开机后，
            FrameLayoutButtonB10.setVisibility(View.VISIBLE);
            FrameLayoutButtonB11.setVisibility(View.INVISIBLE);
            FrameLayoutButtonB18.setVisibility(View.INVISIBLE);
            FrameLayoutButtonC12.setVisibility(View.INVISIBLE);
        }
    }

    private void hideHobOperateMenu() {
     /*   llPause.setVisibility(View.GONE);
        llTimer.setVisibility(View.GONE);
        llLock.setVisibility(View.GONE);
        llBoost.setVisibility(View.INVISIBLE);
        llStop.setVisibility(View.INVISIBLE);*/
        FrameLayoutButtonB11.setVisibility(View.INVISIBLE);
    }

    private void showHoodOperateMenu() {
        //  llAuto.setVisibility(View.VISIBLE);
        //  llLight.setVisibility(View.VISIBLE);
        // llHoodStop.setVisibility(View.VISIBLE);

        autoFlag.setVisibility(View.INVISIBLE);

        FrameLayoutButtonC12.setVisibility(View.VISIBLE);
        FrameLayoutButtonB11.setVisibility(View.INVISIBLE);
        FrameLayoutButtonB10.setVisibility(View.INVISIBLE);
        FrameLayoutButtonB18.setVisibility(View.INVISIBLE);

    }

    private void hideHoodOperateMenu() {
        FrameLayoutButtonC12.setVisibility(View.INVISIBLE);
    }

    private void switchCircleProgressMode(int maxValue, int value, boolean canTouch) {
        switchCircleProgressMode(maxValue, maxValue, 0, 0, value, canTouch, false);
    }

    private void switchCircleProgressMode(int maxValue, int value, boolean canTouch, boolean force) {
        switchCircleProgressMode(maxValue, maxValue, 0, 0, value, canTouch, force);
    }

    private void switchCircleProgressMode(
            int maxValue,
            int maxSelectValue,
            int minValue,
            int minSelectValue,
            int value,
            boolean canTouch,
            boolean force) {
        boolean rangeChanged = false;

        int max = (int) trianProgress.getMaxValue();
        if (max != maxValue || force) {
            trianProgress.setMaxValue(maxValue);
            rangeChanged = true;
        }
        int maxSelect = (int) trianProgress.getMaxSelectValue();
        if (maxSelect != maxSelectValue || force) {
            trianProgress.setMaxSelectValue(maxSelectValue);
            rangeChanged = true;
        }

        int min = (int) trianProgress.getMinValue();
        if (min != minValue || force) {
            trianProgress.setMinValue(minValue);
            rangeChanged = true;
        }
        int minSelect = (int) trianProgress.getMinSelectValue();
        if (minSelect != minSelectValue || force) {
            trianProgress.setMinSelectValue(minSelectValue);
            rangeChanged = true;
        }

        int currentValue = (int) trianProgress.getValue();
        if (currentValue != value || rangeChanged || force) trianProgress.setValue(value);

        if (canTouch) trianProgress.enable();
        else trianProgress.disable();
    }


    private void LockAllUI(boolean flag) {
        if (flag) {
            if (isInHobStatus()) {
                enableAllCookersTouch();
            }
            tvPause.setEnabled(true);
            tvTimer.setEnabled(true);
            tvBoost.setEnabled(true);
            tvStop.setEnabled(true);
            tvHob.setEnabled(true);
            tvHobC12.setEnabled(true);
            tvHobB11.setEnabled(true);
            tvHood.setEnabled(true);
            tvHoodC12.setEnabled(true);
            tvHoodB11.setEnabled(true);
            tvHoodStop.setEnabled(true);
            tvLock.setEnabled(true);
            tvLockM.setEnabled(true);
            if (workMode == WORK_MODE_HOB && getCookerViewsInProcess().size() == 0) {
                trianProgress.disable();
            } else {
                trianProgress.enable();
            }
        } else {
            disableAllCookersTouch();
            tvPause.setEnabled(false);
            tvTimer.setEnabled(false);
            tvBoost.setEnabled(false);
            tvStop.setEnabled(false);
            tvHob.setEnabled(false);
            tvHobB11.setEnabled(false);
            tvHobC12.setEnabled(false);
            tvHood.setEnabled(false);
            tvHoodStop.setEnabled(false);
            tvHoodB11.setEnabled(false);
            tvHoodC12.setEnabled(false);
            tvLock.setEnabled(false);
            tvLockM.setEnabled(false);
            trianProgress.disable();

        }
    }

    private void doUnSetBoostMode_Linker_Cooker() {
        switch (currentCooker) {   // 先
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:
                if (boost_flag_CookerView_Up_Left_Down_Left_SameTime) {
                    boost_flag_CookerView_Up_Left_Down_Left_SameTime = false;
                    cookerViewUpLeft_boost_flag = false;
                    cookerViewDownLeft_boost_flag = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
                break;

            case COOKER_ID_Up_Right_And_Down_Right_SameTime:// 右上、右下 同时点击
                if (boost_flag_CookerView_Up_Right_Down_Right_SameTime) {
                    boost_flag_CookerView_Up_Right_Down_Right_SameTime = false;
                    cookerViewUpRight_boost_flag = false;
                    cookerViewDownRight_boost_flag = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
                break;

            case COOKER_ID_Up_Left_Down_Left: // 左上，左下
                if (boost_flag_CookerView_Up_Left_Down_Left) {
                    boost_flag_CookerView_Up_Left_Down_Left = false;
                    cookerViewUpLeft_boost_flag = false;
                    cookerViewDownLeft_boost_flag = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                break;

            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下
                if (boost_flag_CookerView_Up_Right_Down_Right) {
                    cookerViewUpRight_boost_flag = false;
                    cookerViewDownRight_boost_flag = false;
                    boost_flag_CookerView_Up_Right_Down_Right = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                break;

            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                if (boost_flag_CookerView_Up_Left_Down_Right) {
                    boost_flag_CookerView_Up_Left_Down_Right = false;
                    cookerViewUpLeft_boost_flag = false;
                    cookerViewDownRight_boost_flag = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Down_Right = false;
                break;

            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                if (boost_flag_CookerView_Up_Right_Down_Left) {
                    boost_flag_CookerView_Up_Right_Down_Left = false;
                    cookerViewUpRight_boost_flag = false;   // 右上
                    cookerViewDownLeft_boost_flag = false;      //左下
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Down_Left = false;
                break;

            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                if (boost_flag_CookerView_Up_Right_Up_Left) {
                    boost_flag_CookerView_Up_Right_Up_Left = false;
                    cookerViewUpRight_boost_flag = false;   // 右上
                    cookerViewUpLeft_boost_flag = false;    // 左上
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Up_Left = false;
                break;

            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                if (boost_flag_CookerView_Down_Right_Down_Left) {
                    boost_flag_CookerView_Down_Right_Down_Left = false;
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Down_Left = false;
                break;

            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                if (boost_flag_CookerView_Up_Right_And_Up_Left_Slip) {
                    boost_flag_CookerView_Up_Right_And_Up_Left_Slip = false;// 右上、左上  滑动
                    cookerViewUpRight_boost_flag = false;   // 右上
                    cookerViewUpLeft_boost_flag = false;    // 左上
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
                break;

            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                if (boost_flag_CookerView_Down_Right_And_Down_Left_Slip) {
                    boost_flag_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft_boost_flag = false;// 左下
                    cookerViewDownRight_boost_flag = false;// 右下
                }
                Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                break;

            case COOKER_ID_Up_Left__Middle:// 左上、中间
                if (boost_flag_CookerView_Up_Left__Middle) {
                    boost_flag_CookerView_Up_Left__Middle = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left__Middle = false;//
                break;

            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                if (boost_flag_CookerView_Up_Left__Middle_Slip) {
                    boost_flag_CookerView_Up_Left__Middle_Slip = false;// 左上、中间  滑动
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left__Middle_Slip = false;// 左上、中间 滑动
                break;

            case COOKER_ID_Down_Left_Middle:// 左下、中间
                if (boost_flag_CookerView_Down_Left_Middle) {
                    boost_flag_CookerView_Down_Left_Middle = false;
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Left_Middle = false;// 左下、中间
                break;

            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                if (boost_flag_CookerView_Down_Left_Middle_Slip) {
                    boost_flag_CookerView_Down_Left_Middle_Slip = false;// 左下、中间  滑动
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Left_Middle_Slip = false;// 左下、中间 滑动
                break;

            case COOKER_ID_Up_Right_Middle:// 右上、中间
                if (boost_flag_CookerView_Up_Right_Middle) {
                    boost_flag_CookerView_Up_Right_Middle = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Middle = false;// 右上、中间
                break;

            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                if (boost_flag_CookerView_Up_Right_Middle_Slip) {
                    boost_flag_CookerView_Up_Right_Middle_Slip = false;// 右上、中间  滑动
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Middle_Slip = false;// 右上、中间 滑动
                break;

            case COOKER_ID_Down_Right_Middle:// 右下、中间
                if (boost_flag_CookerView_Down_Right_Middle) {
                    boost_flag_CookerView_Down_Right_Middle = false;
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Middle = false;// 右下、中间
                break;

            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                if (boost_flag_CookerView_Down_Right_Middle_Slip) {
                    boost_flag_CookerView_Down_Right_Middle_Slip = false;// 右下、中间  滑动
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Middle_Slip = false;// 右下、中间 滑动
                break;

            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);

                }
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                if (boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                if (boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                if (boost_flag_CookerView_Up_Left_Down_Left_Up_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Up_Right = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Up_Left_Down_Left = false;
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_Up_Left_Down_Left_Down_Right:// 左上、左下、右下
                if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Down_Right = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Down_Left_Down_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                if (boost_flag_CookerView_Up_Right_Down_Right_Up_Left) {
                    boost_flag_CookerView_Up_Right_Down_Right_Up_Left = false;
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Up_Right_Down_Right_Down_Left:// 右上、右下、左下
                if (boost_flag_CookerView_Up_Right_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Right_Down_Right_Down_Left = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Down_Right_Down_Left = false;
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                if (boost_flag_CookerView_Up_Left_Down_Right_Middle) {
                    boost_flag_CookerView_Up_Left_Down_Right_Middle = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Down_Right_Middle = false;
                break;

            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                if (boost_flag_CookerView_Up_Right_Down_Left_Middle) {
                    boost_flag_CookerView_Up_Right_Down_Left_Middle = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Down_Left_Middle = false;
                break;

            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                if (boost_flag_CookerView_Up_Left_Up_Right_Middle) {
                    boost_flag_CookerView_Up_Left_Up_Right_Middle = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Up_Right_Middle = false;
                break;

            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                if (boost_flag_CookerView_Down_Left_Down_Right_Middle) {
                    boost_flag_CookerView_Down_Left_Down_Right_Middle = false;
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Left_Down_Right_Middle = false;
                break;

            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                if (boost_flag_CookerView_Up_Left_Midlle_Down_Left) {
                    boost_flag_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Midlle_Down_Left = false;
                break;

            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                if (boost_flag_CookerView_Down_Right_Middle_Up_Right) {
                    boost_flag_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Middle_Up_Right = false;
                break;

            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                if (boost_flag_CookerView_Up_Left_Down_Left_Middle) {
                    boost_flag_CookerView_Up_Left_Down_Left_Middle = false;
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、中间、左下
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_Down_Right_Up_Right_Middle://右上、右下、中间
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、中间、右下
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = false;
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right) {
                    boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right) {
                    boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                if (boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; // 左上、中间、右下、左下
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
                break;

            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                if (boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; // 右上、中间、左下、右下
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
                break;

            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                if (boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left) {
                    boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; // 左下、中间、右上、左上

                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);

                }
                Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
                break;

            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                if (boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right) {
                    boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; // 右下、中间、左上、右上
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                }
                Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
                break;

            case COOKER_ID_All://全部
                if (boost_flag_CookerView_All) {
                    boost_flag_CookerView_All = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_All = false;//全部
                Select_CookerView_Up_Left_Down_Left = true;
                Select_CookerView_Up_Right_Down_Right = true;
                break;

            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                if (boost_flag_CookerView_ALL_ANY) {
                    boost_flag_CookerView_ALL_ANY = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_ALL_ALL_ANY = false;//全部 有区
                break;

            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                if (boost_flag_CookerView_ALL_ALL_LEFT_NONE) {
                    boost_flag_CookerView_ALL_ALL_LEFT_NONE = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                Select_CookerView_Up_Left_Down_Left = true;
                break;

            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                if (boost_flag_CookerView_ALL_ALL_NONE_RIGHT) {
                    boost_flag_CookerView_ALL_ALL_NONE_RIGHT = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(false);
                    cookerViewDownLeft.SetBoostWorkingStatus(false);
                    cookerViewDownRight.SetBoostWorkingStatus(false);
                    cookerViewUpRight.SetBoostWorkingStatus(false);
                }
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                Select_CookerView_Up_Right_Down_Right = true;
                break;
        }
    }

    private void doSelectAfterPowerOnSingleCooker(int id) {   //  相当于断开 联动        更新 2018年11月13日
        switch (currentCooker) {   // 先断开 联动
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击

                if (boost_flag_CookerView_Up_Left_Down_Left_SameTime) {
                    boost_flag_CookerView_Up_Left_Down_Left_SameTime = false;
                    cookerViewUpLeft_boost_flag = true;
                    cookerViewDownLeft_boost_flag = true;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);

                }
                Select_CookerView_Up_Left_Down_Left_SameTime = false;
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                if (boost_flag_CookerView_Up_Right_Down_Right_SameTime) {
                    boost_flag_CookerView_Up_Right_Down_Right_SameTime = false;
                    cookerViewUpRight_boost_flag = true;
                    cookerViewDownRight_boost_flag = true;
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下

                tvLeftMiddleGear.setBackgroundColor(Color.rgb(58, 60, 61));// gray

                break;
            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下

                tvRightMiddleGear.setBackgroundColor(Color.rgb(58, 60, 61)); // gray
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                if (boost_flag_CookerView_Up_Left_Down_Right) { // 左上、右下
                    boost_flag_CookerView_Up_Left_Down_Right = false;
                    cookerViewUpLeft_boost_flag = true;
                    cookerViewDownRight_boost_flag = true;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left_Down_Right = false;
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                if (boost_flag_CookerView_Up_Right_Down_Left) {
                    boost_flag_CookerView_Up_Right_Down_Left = false;
                    cookerViewUpRight_boost_flag = true;   // 右上
                    cookerViewDownLeft_boost_flag = true;      //左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Down_Left = false;
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上  同时
                if (boost_flag_CookerView_Up_Right_Up_Left) {
                    boost_flag_CookerView_Up_Right_Up_Left = false;
                    cookerViewUpRight_boost_flag = true;   // 右上
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Up_Left = false;
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下  同时
                if (boost_flag_CookerView_Down_Right_Down_Left) {
                    boost_flag_CookerView_Down_Right_Down_Left = false;
                    cookerViewDownLeft_boost_flag = true;      //左下
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Right_Down_Left = false;
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                if (boost_flag_CookerView_Up_Right_And_Up_Left_Slip) {
                    boost_flag_CookerView_Up_Right_And_Up_Left_Slip = false;// 右上、左上  滑动
                    cookerViewUpRight_boost_flag = true;   // 右上
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                if (boost_flag_CookerView_Down_Right_And_Down_Left_Slip) {
                    boost_flag_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewDownRight_boost_flag = true;// 右下
                }
                Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                if (boost_flag_CookerView_Up_Left__Middle) {
                    boost_flag_CookerView_Up_Left__Middle = false;
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left__Middle = false;//
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                if (boost_flag_CookerView_Up_Left__Middle_Slip) { // 左上、中间  滑动
                    boost_flag_CookerView_Up_Left__Middle_Slip = false;
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left__Middle_Slip = false;// 左上、中间 滑动
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                if (boost_flag_CookerView_Down_Left_Middle) {
                    boost_flag_CookerView_Down_Left_Middle = false;
                    cookerViewDownLeft_boost_flag = true;      //左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Left_Middle = false;// 左下、中间
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                if (boost_flag_CookerView_Down_Left_Middle_Slip) {
                    boost_flag_CookerView_Down_Left_Middle_Slip = false;// 左下、中间  滑动
                    cookerViewDownLeft_boost_flag = true;      //左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Left_Middle_Slip = false;// 左下、中间 滑动
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                Select_CookerView_Up_Right_Middle = false;// 右上、中间
                if (boost_flag_CookerView_Up_Right_Middle) {
                    boost_flag_CookerView_Up_Right_Middle = false;
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                if (boost_flag_CookerView_Up_Right_Middle_Slip) {
                    boost_flag_CookerView_Up_Right_Middle_Slip = false;// 右上、中间  滑动
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Middle_Slip = false;// 右上、中间 滑动
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                Select_CookerView_Down_Right_Middle = false;// 右下、中间
                if (boost_flag_CookerView_Down_Right_Middle) {
                    boost_flag_CookerView_Down_Right_Middle = false;
                    cookerViewDownRight_boost_flag = true;// 右下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                if (boost_flag_CookerView_Down_Right_Middle_Slip) {
                    boost_flag_CookerView_Down_Right_Middle_Slip = false;// 右下、中间  滑动
                    cookerViewDownRight_boost_flag = true;// 右下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Right_Middle_Slip = false;// 右下、中间 滑动
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                Select_CookerView_Up_Left_Down_Right_Middle = false;
                if (boost_flag_CookerView_Up_Left_Down_Right_Middle) {
                    boost_flag_CookerView_Up_Left_Down_Right_Middle = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    //cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                Select_CookerView_Up_Right_Up_Left_Down_Left = false;
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下

           /* cookerViewDownLeft.SetTvGearVisible(true);
            cookerViewUpLeft.SetTvGearVisible(true);
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/

                if (boost_flag_CookerView_Up_Left_Down_Left_Up_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Up_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left) {
                    click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left) {
                    click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }

                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
          /*  cookerViewDownLeft.SetTvGearVisible(true);
            cookerViewUpLeft.SetTvGearVisible(true);
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/

                if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right) {// 左上、左下、右下
                    boost_flag_CookerView_Up_Left_Down_Left_Down_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                //  Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                tvLeftMiddleGear.setBackgroundColor(Color.rgb(58, 60, 61));// gray
                tvRightMiddleGear.setBackgroundColor(Color.rgb(58, 60, 61));// gray
                if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下

                }
                if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下

          /*  cookerViewDownRight.SetTvGearVisible(true);
            cookerViewUpRight.SetTvGearVisible(true);
            tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                tvRightMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
                    click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
                    click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
            /*cookerViewDownLeft.SetTvGearVisible(true);
            cookerViewUpLeft.SetTvGearVisible(true);
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                tvLeftMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
                    click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
                    click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }

                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
           /* cookerViewDownRight.SetTvGearVisible(true);
            cookerViewUpRight.SetTvGearVisible(true);
            tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                tvRightMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Up_Right_Down_Right_Up_Left) {
                    boost_flag_CookerView_Up_Right_Down_Right_Up_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right) {
                    click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right) {
                    click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
          /*  cookerViewDownRight.SetTvGearVisible(true);
            cookerViewUpRight.SetTvGearVisible(true);
            tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                tvRightMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Up_Right_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Right_Down_Right_Down_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Right_Down_Right_Down_Left) {
                    click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Up_Right_Down_Right_Down_Left) {
                    click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                Select_CookerView_Up_Right_Down_Left_Middle = false;//
                if (boost_flag_CookerView_Up_Right_Down_Left_Middle) {
                    boost_flag_CookerView_Up_Right_Down_Left_Middle = false;
                    cookerViewUpRight_boost_flag = true; // 右上
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                Select_CookerView_Up_Left_Up_Right_Middle = false;
                if (boost_flag_CookerView_Up_Left_Up_Right_Middle) {
                    boost_flag_CookerView_Up_Left_Up_Right_Middle = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                Select_CookerView_Down_Left_Down_Right_Middle = false;
                if (boost_flag_CookerView_Down_Left_Down_Right_Middle) {
                    boost_flag_CookerView_Down_Left_Down_Right_Middle = false;
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                Select_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                if (boost_flag_CookerView_Up_Left_Midlle_Down_Left) {
                    boost_flag_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                Select_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                if (boost_flag_CookerView_Down_Right_Middle_Up_Right) {
                    boost_flag_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
            /*cookerViewUpLeft.SetTvGearVisible(true);
            cookerViewDownLeft.SetTvGearVisible(true);
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                tvLeftMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                if (boost_flag_CookerView_Up_Left_Down_Left_Middle) {
                    boost_flag_CookerView_Up_Left_Down_Left_Middle = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Middle) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_alert_flag_Up_Left_Down_Left_Middle) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
           /* cookerViewUpRight.SetTvGearVisible(true);
            cookerViewDownRight.SetTvGearVisible(true);
            tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                tvRightMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
           /* cookerViewUpRight.SetTvGearVisible(true);
            cookerViewDownRight.SetTvGearVisible(true);
            tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                tvRightMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下
           /* cookerViewUpRight.SetTvGearVisible(true);
            cookerViewDownRight.SetTvGearVisible(true);
            tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                tvRightMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下

          /*  cookerViewUpLeft.SetTvGearVisible(true);
            cookerViewDownLeft.SetTvGearVisible(true);
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                tvLeftMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right) {
                    boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right) {
                    click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right) {
                    click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
            /*cookerViewUpLeft.SetTvGearVisible(true);
            cookerViewDownLeft.SetTvGearVisible(true);
            tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                tvLeftMiddleGear.setBackgroundColor((Color.rgb(58, 60, 61)));// gray);
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right) {
                    boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right) {
                    click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right) {
                    click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
                if (boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; // 左上、中间、右下、左下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
                if (boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; // 右上、中间、左下、右下

                    cookerViewDownLeft_boost_flag = true;// 左下
                    //cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
                if (boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left) {
                    boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; // 左下、中间、右上、左上
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上

                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //    cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
                if (boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right) {
                    boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; // 右下、中间、左上、右上
                    cookerViewUpLeft_boost_flag = true;// 左上
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_All://全部
                Select_CookerView_All = false;//全部
                //  cookerViewUpLeft.SetTvGearVisible(true);
                //  cookerViewDownLeft.SetTvGearVisible(true);
                //  tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);

                // cookerViewUpRight.SetTvGearVisible(true);
                //  cookerViewDownRight.SetTvGearVisible(true);
                //  tvRightMiddleGear.setVisibility(View.INVISIBLE);
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);

                if (boost_flag_CookerView_All) {
                    boost_flag_CookerView_All = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //   cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                if (click_set_to_stop_flag_All) {
                    click_set_to_stop_flag_All = false;//全部
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下

                }
                if (click_set_to_alert_flag_All) {
                    click_set_to_alert_flag_All = false;//全部
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                Select_CookerView_ALL_ALL_ANY = false;//全部 有区

                if (boost_flag_CookerView_ALL_ANY) {
                    boost_flag_CookerView_ALL_ANY = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                //  cookerViewUpLeft.SetTvGearVisible(true);
                //  cookerViewDownLeft.SetTvGearVisible(true);
                // tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);

                if (boost_flag_CookerView_ALL_ALL_LEFT_NONE) {
                    boost_flag_CookerView_ALL_ALL_LEFT_NONE = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }

                if (click_set_to_alert_flag_ALL_ALL_LEFT_NONE) {
                    click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_stop_flag_ALL_ALL_LEFT_NONE) {
                    click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                // cookerViewUpRight.SetTvGearVisible(true);
                // cookerViewDownRight.SetTvGearVisible(true);
                // tvRightMiddleGear.setVisibility(View.INVISIBLE);
                tvRightMiddleGear.setBackgroundColor(cooker_color_gray);

                if (boost_flag_CookerView_ALL_ALL_NONE_RIGHT) {
                    boost_flag_CookerView_ALL_ALL_NONE_RIGHT = false;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }

                if (click_set_to_alert_flag_ALL_ALL_NONE_RIGHT) {
                    click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_ALL_ALL_NONE_RIGHT) {
                    click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }

                break;
            case COOKER_ID_NONE:  // 闹铃 结束 或 定时结束 后

                if (Select_CookerView_Up_Left_Down_Left) {// 左下 左上  Select_CookerView_Up_Left_Down_Lef
                    tvLeftMiddleGear.setBackgroundColor(cooker_color_gray);
                }
                if (Select_CookerView_Up_Right_Down_Right) { // 右上 右下  Select_CookerView_Up_Right_Down_Right
                    tvRightMiddleGear.setBackgroundColor(cooker_color_gray);
                }
                break;
        }

    }

    private void doUnLinkTimerOfAllCookers() {
        Logger.getInstance().d("doUnLinkTimerOfAllCookers(" + getCookerIDStr(currentCooker) + ")");
        switch (currentCooker) {   // 先断开 联动
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击

                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击

                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下
              /*  if (click_set_to_alert_flag_Up_Left_Down_Left) { // 左上 左下
                click_set_to_alert_flag_Up_Left_Down_Left = false;
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
            }
                if (click_set_to_stop_flag_Up_Left_Down_Left) {
                    click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                    HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                }*/

                break;
            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下
               /* if (click_set_to_alert_flag_Up_Right_Down_Right) {  // 右上 右下
                    click_set_to_alert_flag_Up_Right_Down_Right = false;
                    HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                }
                if (click_set_to_stop_flag_Up_Right_Down_Right) {
                    click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                    HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                }*/

                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下

                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下

                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上

                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动

                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动

                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下

                break;
            case COOKER_ID_Up_Left__Middle:// 左上、中间  同时点击

                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动

                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间

                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动

                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间

                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动

                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间

                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动

                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下

                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left) { //左上、左下 、右上
                    click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;
                    // HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上

                }
                if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left) {
                    click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                    // HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }

                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right) { //左上、左下 、、右下
                    click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                    //  HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上

                }
                if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                    // HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                if (click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上

                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
                    click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left) {
                    click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                if (click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
                    click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right) {
                    click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                if (click_set_to_alert_flag_Up_Left_Up_Right_Down_Right) {
                    click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_Up_Left_Up_Right_Down_Right) {
                    click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                if (click_set_to_alert_flag_Up_Right_Down_Right_Down_Left) {
                    click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_Up_Right_Down_Right_Down_Left) {
                    click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下

                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上

                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下

                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下

                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下

                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                if (click_set_to_alert_flag_Up_Left_Down_Left_Middle) {
                    click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_stop_flag_Up_Left_Down_Left_Middle) {
                    click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间;
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }

                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                if (click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left) {
                    click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left) {
                    click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right) {
                    click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right) {
                    click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                if (click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right) {
                    click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right) {
                    click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下

                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下

                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上

                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上

                break;

            case COOKER_ID_All://全部
                if (click_set_to_alert_flag_All) {
                    click_set_to_alert_flag_All = false;//全部
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_All) {
                    click_set_to_stop_flag_All = false;//全部
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区

                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                if (click_set_to_alert_flag_ALL_ALL_LEFT_NONE) {
                    click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                    click_set_to_alert_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                if (click_set_to_stop_flag_ALL_ALL_LEFT_NONE) {
                    click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
                    click_set_to_stop_flag_Up_Left_Down_Left = true;  // 左下 左上
                }
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                if (click_set_to_alert_flag_ALL_ALL_NONE_RIGHT) {
                    click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                    click_set_to_alert_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                if (click_set_to_stop_flag_ALL_ALL_NONE_RIGHT) {
                    click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
                    click_set_to_stop_flag_Up_Right_Down_Right = true;  // 右上 右下
                }
                break;
        }

        //handler.sendEmptyMessageDelayed(HANDLER_TIMER_DOING,1000 );
    }

    private void doUnLinkAllCooker() {
        Logger.getInstance().d("doUnLinkAllCooker(" + getCookerIDStr(currentCooker) + ")");
        switch (currentCooker) {   // 先断开 联动
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击

                if (boost_flag_CookerView_Up_Left_Down_Left_SameTime) {
                    boost_flag_CookerView_Up_Left_Down_Left_SameTime = false;
                    cookerViewUpLeft_boost_flag = true;
                    cookerViewDownLeft_boost_flag = true;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left_Down_Left_SameTime = false;
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                if (boost_flag_CookerView_Up_Right_Down_Right_SameTime) {
                    boost_flag_CookerView_Up_Right_Down_Right_SameTime = false;
                    cookerViewUpRight_boost_flag = true;
                    cookerViewDownRight_boost_flag = true;
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下
                break;
            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                if (boost_flag_CookerView_Up_Left_Down_Right) { // 左上、右下
                    boost_flag_CookerView_Up_Left_Down_Right = false;
                    cookerViewUpLeft_boost_flag = true;
                    cookerViewDownRight_boost_flag = true;
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left_Down_Right = false;

                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                if (boost_flag_CookerView_Up_Right_Down_Left) {
                    boost_flag_CookerView_Up_Right_Down_Left = false;
                    cookerViewUpRight_boost_flag = true;   // 右上
                    cookerViewDownLeft_boost_flag = true;      //左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Down_Left = false;
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                if (boost_flag_CookerView_Up_Right_Up_Left) {
                    boost_flag_CookerView_Up_Right_Up_Left = false;
                    cookerViewUpRight_boost_flag = true;   // 右上
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Up_Left = false;
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                if (boost_flag_CookerView_Up_Right_And_Up_Left_Slip) {
                    boost_flag_CookerView_Up_Right_And_Up_Left_Slip = false;// 右上、左上  滑动
                    cookerViewUpRight_boost_flag = true;   // 右上
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                if (boost_flag_CookerView_Down_Right_And_Down_Left_Slip) {
                    boost_flag_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewDownRight_boost_flag = true;// 右下
                }
                Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                if (boost_flag_CookerView_Down_Right_Down_Left) {
                    boost_flag_CookerView_Down_Right_Down_Left = false;
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewDownRight_boost_flag = true;// 右下
                }
                Select_CookerView_Down_Right_Down_Left = false;
                break;
            case COOKER_ID_Up_Left__Middle:// 左上、中间  同时点击
                if (boost_flag_CookerView_Up_Left__Middle) {
                    boost_flag_CookerView_Up_Left__Middle = false;
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left__Middle = false;//
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                if (boost_flag_CookerView_Up_Left__Middle_Slip) { // 左上、中间  滑动
                    boost_flag_CookerView_Up_Left__Middle_Slip = false;
                    cookerViewUpLeft_boost_flag = true;    // 左上
                    //cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Left__Middle_Slip = false;// 左上、中间 滑动
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                if (boost_flag_CookerView_Down_Left_Middle) {
                    boost_flag_CookerView_Down_Left_Middle = false;
                    cookerViewDownLeft_boost_flag = true;      //左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Left_Middle = false;// 左下、中间
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                if (boost_flag_CookerView_Down_Left_Middle_Slip) {
                    boost_flag_CookerView_Down_Left_Middle_Slip = false;// 左下、中间  滑动
                    cookerViewDownLeft_boost_flag = true;      //左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Left_Middle_Slip = false;// 左下、中间 滑动
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                if (boost_flag_CookerView_Up_Right_Middle) {
                    boost_flag_CookerView_Up_Right_Middle = false;
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Middle = false;// 右上、中间
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                if (boost_flag_CookerView_Up_Right_Middle_Slip) {
                    boost_flag_CookerView_Up_Right_Middle_Slip = false;// 右上、中间  滑动
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Up_Right_Middle_Slip = false;// 右上、中间 滑动
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                if (boost_flag_CookerView_Down_Right_Middle) {
                    boost_flag_CookerView_Down_Right_Middle = false;
                    cookerViewDownRight_boost_flag = true;// 右下
                    //   cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Right_Middle = false;// 右下、中间
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                if (boost_flag_CookerView_Down_Right_Middle_Slip) {
                    boost_flag_CookerView_Down_Right_Middle_Slip = false;// 右下、中间  滑动
                    cookerViewDownRight_boost_flag = true;// 右下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                Select_CookerView_Down_Right_Middle_Slip = false;// 右下、中间 滑动
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                Select_CookerView_Up_Left_Down_Right_Middle = false;
                if (boost_flag_CookerView_Up_Left_Down_Right_Middle) {
                    boost_flag_CookerView_Up_Left_Down_Right_Middle = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    //cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上
                Select_CookerView_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
              /*  cookerViewDownLeft.SetTvGearVisible(true);
                cookerViewUpLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/

                if (boost_flag_CookerView_Up_Left_Down_Left_Up_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Up_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                /*cookerViewDownLeft.SetTvGearVisible(true);
                cookerViewUpLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/

                if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right) {// 左上、左下、右下
                    boost_flag_CookerView_Up_Left_Down_Left_Down_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                /*cookerViewDownLeft.SetTvGearVisible(true);
                cookerViewUpLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);

                cookerViewDownRight.SetTvGearVisible(true);
                cookerViewUpRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                if (boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right) {
                    boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
               /* cookerViewDownRight.SetTvGearVisible(true);
                cookerViewUpRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                if (boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                /*cookerViewDownLeft.SetTvGearVisible(true);
                cookerViewUpLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                if (boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }

                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                Select_CookerView_Up_Left_Up_Right_Down_Right = false;
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
              /*  cookerViewDownRight.SetTvGearVisible(true);
                cookerViewUpRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                if (boost_flag_CookerView_Up_Right_Down_Right_Up_Left) {
                    boost_flag_CookerView_Up_Right_Down_Right_Up_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
               /* cookerViewDownRight.SetTvGearVisible(true);
                cookerViewUpRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                if (boost_flag_CookerView_Up_Right_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Right_Down_Right_Down_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewDownLeft_boost_flag = true;// 左下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                Select_CookerView_Up_Right_Down_Left_Middle = false;//
                if (boost_flag_CookerView_Up_Right_Down_Left_Middle) {
                    boost_flag_CookerView_Up_Right_Down_Left_Middle = false;
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //   cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                Select_CookerView_Up_Left_Up_Right_Middle = false;
                if (boost_flag_CookerView_Up_Left_Up_Right_Middle) {
                    boost_flag_CookerView_Up_Left_Up_Right_Middle = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    //   cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                Select_CookerView_Down_Left_Down_Right_Middle = false;
                if (boost_flag_CookerView_Down_Left_Down_Right_Middle) {
                    boost_flag_CookerView_Down_Left_Down_Right_Middle = false;
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                Select_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                if (boost_flag_CookerView_Up_Left_Midlle_Down_Left) {
                    boost_flag_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                Select_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                if (boost_flag_CookerView_Down_Right_Middle_Up_Right) {
                    boost_flag_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下
                    //cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
             /*   cookerViewUpLeft.SetTvGearVisible(true);
                cookerViewDownLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                if (boost_flag_CookerView_Up_Left_Down_Left_Middle) {
                    boost_flag_CookerView_Up_Left_Down_Left_Middle = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    //cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                /*cookerViewUpRight.SetTvGearVisible(true);
                cookerViewDownRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
               /* cookerViewUpRight.SetTvGearVisible(true);
                cookerViewDownRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
              /*  cookerViewUpRight.SetTvGearVisible(true);
                cookerViewDownRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                if (boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left) {
                    boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = false;
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    //   cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
             /*   cookerViewUpLeft.SetTvGearVisible(true);
                cookerViewDownLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right) {
                    boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //   cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
             /*   cookerViewUpLeft.SetTvGearVisible(true);
                cookerViewDownLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                if (boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right) {
                    boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
                if (boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left) {
                    boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; // 左上、中间、右下、左下
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //   cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    //    cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
                if (boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right) {
                    boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; // 右上、中间、左下、右下
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //   cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
                if (boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left) {
                    boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; // 左下、中间、右上、左上
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);

                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上
                if (boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right) {
                    boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; // 右下、中间、左上、右上
                    cookerViewUpLeft_boost_flag = true;// 左上
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                }
                break;

            case COOKER_ID_All://全部
                Select_CookerView_All = false;//全部
               /* cookerViewUpLeft.SetTvGearVisible(true);
                cookerViewDownLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);

                cookerViewUpRight.SetTvGearVisible(true);
                cookerViewDownRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动
                if (boost_flag_CookerView_All) {
                    boost_flag_CookerView_All = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                Select_CookerView_ALL_ALL_ANY = false;//全部 有区

                if (boost_flag_CookerView_ALL_ANY) {
                    boost_flag_CookerView_ALL_ANY = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    // cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //  cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
               /* cookerViewUpLeft.SetTvGearVisible(true);
                cookerViewDownLeft.SetTvGearVisible(true);
                tvLeftMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Left_Down_Left = true;  // 左上，左下  滑动

                if (boost_flag_CookerView_ALL_ALL_LEFT_NONE) {
                    boost_flag_CookerView_ALL_ALL_LEFT_NONE = false;
                    boost_flag_CookerView_Up_Left_Down_Left = true;  // 左上，左下
                    //  cookerViewMiddle_boost_flag = true;// 中间
                    cookerViewUpRight_boost_flag = true; // 右上
                    cookerViewDownRight_boost_flag = true;// 右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    // cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }

                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
              /*  cookerViewUpRight.SetTvGearVisible(true);
                cookerViewDownRight.SetTvGearVisible(true);
                tvRightMiddleGear.setVisibility(View.INVISIBLE);*/
                Select_CookerView_Up_Right_Down_Right = true;  // 右上、右下 滑动
                if (boost_flag_CookerView_ALL_ALL_NONE_RIGHT) {
                    boost_flag_CookerView_ALL_ALL_NONE_RIGHT = false;
                    cookerViewUpLeft_boost_flag = true;// 左上
                    cookerViewDownLeft_boost_flag = true;// 左下
                    //   cookerViewMiddle_boost_flag = true;// 中间
                    boost_flag_CookerView_Up_Right_Down_Right = true;  // 右上、右下
                    cookerViewUpLeft.SetBoostWorkingStatus(true);
                    cookerViewDownLeft.SetBoostWorkingStatus(true);
                    //   cookerViewMiddle.SetBoostWorkingStatus(true);
                    cookerViewDownRight.SetBoostWorkingStatus(true);
                    cookerViewUpRight.SetBoostWorkingStatus(true);
                }
                break;
        }
    }

    private void doPowerOnTheTwoCookersOfLeft(int ValueGear) {
        tvLeftMiddleGear.setBackgroundColor(cooker_color_blue);// 左边无区 开启
        setHobGearText(tvLeftMiddleGear, ValueGear);
        cookerViewUpLeft.SetBackGroundBlue();
        cookerViewDownLeft.SetBackGroundBlue();
        cookerViewUpLeft.setGear(ValueGear);
        cookerViewDownLeft.setGear(ValueGear);

        cookerViewUpLeft.SetTvGearVisible(false);
        cookerViewDownLeft.SetTvGearVisible(false);
        tvLeftMiddleGear.setVisibility(View.VISIBLE);
    }

    private void doPowerOnTheTwoCookersOfRight(int ValueGear) {
        tvRightMiddleGear.setBackgroundColor(cooker_color_blue);// 右边无区 开启
        setHobGearText(tvRightMiddleGear, ValueGear);
        cookerViewUpRight.SetBackGroundBlue();
        cookerViewDownRight.SetBackGroundBlue();
        cookerViewUpRight.setGear(ValueGear);
        cookerViewDownRight.setGear(ValueGear);

        cookerViewDownRight.SetTvGearVisible(false);
        cookerViewUpRight.SetTvGearVisible(false);
        tvRightMiddleGear.setVisibility(View.VISIBLE);
    }

    private void doUpdateCircleProgressValueAfterSelectMoreCookers() {
        if (ReadyToSetTimer) {  // 处在 定时设置状态，不更新 圆环 ，2018年11月23日16:25:10

        } else {
            updateCircleProgressValue(UpdateCircleProgressValue);
        }
    }

    private void doPowerOnTheUpLeftCooker(int ValueGear) {  // 左上 炉头 开启或选择
        cookerViewUpLeft.powerOn();
        cookerViewUpLeft.SetBackGroundBlue();
        cookerViewUpLeft.setGear(ValueGear);
        cookerViewUpLeft.SetTvGearVisible(true);
        //switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, ValueGear, true);
        UpdateCircleProgressValue = ValueGear;
        handler.sendEmptyMessageDelayed(HANDLER_UPDATE_CIRCLE_PROGRESS, UPDATE_CIRCLE_PROGRESS_DELAY);
    }

    private void doPowerOnTheDownLeftCooker(int ValueGear) { //左下 炉头 开启 或选择
        cookerViewDownLeft.powerOn();
        cookerViewDownLeft.SetBackGroundBlue();
        cookerViewDownLeft.setGear(ValueGear);
        cookerViewDownLeft.SetTvGearVisible(true);
        // switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, ValueGear, true);
        UpdateCircleProgressValue = ValueGear;
        handler.sendEmptyMessageDelayed(HANDLER_UPDATE_CIRCLE_PROGRESS, UPDATE_CIRCLE_PROGRESS_DELAY);
    }

    private void doPowerOnTheDownRightCooker(int ValueGear) { // 右下 炉头 开启或选择
        cookerViewDownRight.powerOn();
        cookerViewDownRight.SetBackGroundBlue();
        cookerViewDownRight.setGear(ValueGear);
        cookerViewDownRight.SetTvGearVisible(true);
        // switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, ValueGear, true);
        UpdateCircleProgressValue = ValueGear;
        handler.sendEmptyMessageDelayed(HANDLER_UPDATE_CIRCLE_PROGRESS, UPDATE_CIRCLE_PROGRESS_DELAY);
    }

    private void doPowerOnTheUpRightCooker(int ValueGear) { // 右上 炉头 开启或选择
        cookerViewUpRight.powerOn();
        cookerViewUpRight.SetBackGroundBlue();
        cookerViewUpRight.setGear(ValueGear);
        cookerViewUpRight.SetTvGearVisible(true);
        //  switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, ValueGear, true);
        UpdateCircleProgressValue = ValueGear;
        handler.sendEmptyMessageDelayed(HANDLER_UPDATE_CIRCLE_PROGRESS, UPDATE_CIRCLE_PROGRESS_DELAY);
    }

    private void SetWorkingTimerWhenLinkeCookers(int timermode) {
        switch (timermode) {
            case mode_set_to_alart:
                doSetToAlert();
                break;
            case mode_set_to_stop:
                doSetToStop();
                break;
            default:
                RemoveAllTimerInformationForCurrentCookers();
                break;
        }

    }

    private int getMaxHobLevel(int cookerId, int cookerGear) {
        ICookerPowerDataEx cookerPowerDataEx = ProductManager.getCookerPowerData();

        AllCookerDataEx cookerDataEx = new AllCookerDataEx();
        cookerDataEx.setaMode(leftCookersUnited() ? 5 : 1);
        cookerDataEx.setbMode(leftCookersUnited() ? 5 : 1);
        cookerDataEx.setcMode(rightCookersUnited() ? 5 : 1);
        cookerDataEx.setdMode(rightCookersUnited() ? 5 : 1);
        cookerDataEx.setaFireValue(cookerViewDownLeft.getGearValue());
        cookerDataEx.setbFireValue(cookerViewUpLeft.getGearValue());
        cookerDataEx.setcFireValue(cookerViewUpRight.getGearValue());
        cookerDataEx.setdFireValue(cookerViewDownRight.getGearValue());

        cookerPowerDataEx.updateCookerPower(cookerDataEx);

        switch (cookerId) {
            case COOKER_ID_Down_Left:
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA);
            case COOKER_ID_Up_Left:
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB);
            case COOKER_ID_Up_Right:
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdC);
            case COOKER_ID_Down_Right:
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdD);
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime: // 左上，左下 同时
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB);
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下  滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree);
            case COOKER_ID_Up_Right_And_Down_Right_SameTime:   // 右上、右下 同时点击
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdC, cdD);
            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下  滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdCFree, cdDFree);
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下  同时
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdD);
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下  同时
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdC);
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上  同时
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdC);
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdC);
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdD);
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下  同时
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdD);
            case COOKER_ID_Up_Left__Middle:// 左上、中间  同时点击bB
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB);
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB);
            case COOKER_ID_Down_Left_Middle:// 左下、中间  同时点击
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA);
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA);
            case COOKER_ID_Up_Right_Middle:// 右上、中间  同时点击
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdC);
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdC);
            case COOKER_ID_Down_Right_Middle:// 右下、中间 同时点击
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdD);
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdD);
            case COOKER_ID_Up_Right_Up_Left_Down_Left: // 右上、左上、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdC);
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdCFree, cdDFree);
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdCFree, cdDFree);
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdD);
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdCFree, cdDFree);
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB, cdCFree, cdDFree);
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdC, cdD);
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdD);
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdC);
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdC);
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdD);
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree);
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdCFree, cdDFree);
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB);
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下:
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdC, cdD);
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdCFree, cdDFree);
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdCFree, cdDFree);
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdC);
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdD);
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB, cdD);
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdC, cdD);
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB, cdC);
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdB, cdC, cdD);
            case COOKER_ID_All: //全部
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdCFree, cdDFree);
            case COOKER_ID_ALL_ALL_LEFT_NONE: //全部 左边无区
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdAFree, cdBFree, cdC, cdD);
            case COOKER_ID_ALL_ALL_NONE_RIGHT: //全部 右边无区
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB, cdCFree, cdDFree);
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                return cookerPowerDataEx.checkCookerPower(cookerGear, cdA, cdB, cdC, cdD);
        }
        return TFTCookerConstant.GEAR_MAX_VALUE;
    }

    private void doLinkEachCooker(int v, int vg, int timermode) {
        Logger.getInstance().d("doLinkEachCooker(" + getCookerIDStr(v) + "," + vg + "," + timermode + ")");
        Select_CookerView_All = false;
        switch (v) {   //  联动

            case COOKER_ID_Up_Left_And_Down_Letf_SameTime: // 左上，左下 同时
                if (!leftCookersUnited()) {
                    Select_CookerView_Up_Left_Down_Left_SameTime = true;
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    doPowerOnTheUpLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 左上 炉头
                    doPowerOnTheDownLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); //左下
                    tvLeftMiddleGear.setVisibility(View.INVISIBLE);
                    currentCooker = COOKER_ID_Up_Left_And_Down_Letf_SameTime;
                    ReadyToSelect_onCookerSelected = false;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下  滑动
                Select_CookerView_Up_Left_Down_Left = true;
                doPowerOnTheUpLeftCooker(vg); // 左上 炉头
                doPowerOnTheDownLeftCooker(vg); //左下
                doPowerOnTheTwoCookersOfLeft(vg); // 左边无区 开启
                currentCooker = COOKER_ID_Up_Left_Down_Left;
                ReadyToSelect_onCookerSelected = false;
                SetWorkingTimerWhenLinkeCookers(timermode);
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime:   // 右上、右下 同时点击
                if (!rightCookersUnited()) {
                    Select_CookerView_Up_Right_Down_Right_SameTime = true;
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下 滑动
                    doPowerOnTheDownRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右下 炉头
                    doPowerOnTheUpRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右上 炉头
                    tvRightMiddleGear.setVisibility(View.INVISIBLE);
                    currentCooker = COOKER_ID_Up_Right_And_Down_Right_SameTime;
                    ReadyToSelect_onCookerSelected = false;
                }
                break;
            case COOKER_ID_Up_Right_Down_Right:   // 右上、右下  滑动

                Select_CookerView_Up_Right_Down_Right = true;

                doPowerOnTheUpRightCooker(vg);  // 右上
                doPowerOnTheDownRightCooker(vg); // 右下 炉头
                doPowerOnTheTwoCookersOfRight(vg); // 右边无区 开启
                currentCooker = COOKER_ID_Up_Right_Down_Right;
                ReadyToSelect_onCookerSelected = false;
                SetWorkingTimerWhenLinkeCookers(timermode);
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下  同时
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {//左上、左下
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左边无区 开启
                    doPowerOnTheDownRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); //右下
                    Select_CookerView_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right; // 左上、左下、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {  //右下、右上;
                    Select_CookerView_Up_Right_Down_Right = false;
                    Select_CookerView_Up_Left_Up_Right_Down_Right = true;    // 右下、右上、左上
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    doPowerOnTheUpLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 左上
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Down_Right; // 右上、右下、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Down_Right = true;
                    doPowerOnTheUpLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 左上
                    doPowerOnTheDownRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 右下
                    currentCooker = COOKER_ID_Up_Left_Down_Right;
                    //  SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下  同时
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {//左上、左下
                    Select_CookerView_Up_Left_Down_Left = false;

                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左边无区 开启
                    doPowerOnTheUpRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右上 炉头
                    Select_CookerView_Up_Right_Up_Left_Down_Left = true; //左上、左下 、右上
                    currentCooker = COOKER_ID_Up_Right_Up_Left_Down_Left; //左上、左下 、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {  // 右上 右下
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右边无区 开启
                    doPowerOnTheDownLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左下
                    Select_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                    currentCooker = COOKER_ID_Up_Right_Down_Right_Down_Left; // 右上、右下、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Down_Left = true;
                    doPowerOnTheUpRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 右上
                    doPowerOnTheDownLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左下
                    currentCooker = COOKER_ID_Up_Right_Down_Left;
                    // SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上  同时
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左边无区 开启
                    doPowerOnTheUpRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右上 炉头
                    Select_CookerView_Up_Right_Up_Left_Down_Left = true; //左上、左下 、右上
                    currentCooker = COOKER_ID_Up_Right_Up_Left_Down_Left; //左上、左下 、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    doPowerOnTheUpLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左上 炉头
                    Select_CookerView_Up_Left_Up_Right_Down_Right = true;    // 右上、右下、左上
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Down_Right; // 右上、右下、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Up_Left = true;
                    doPowerOnTheUpLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    doPowerOnTheUpRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    currentCooker = COOKER_ID_Up_Right_Up_Left;
                    //  SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(vg);  // 左边无区 开启
                    doPowerOnTheUpRightCooker(vg); // 右上 炉头
                    Select_CookerView_Up_Right_Up_Left_Down_Left = true; //左上、左下 、右上
                    currentCooker = COOKER_ID_Up_Right_Up_Left_Down_Left; //左上、左下 、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    doPowerOnTheUpLeftCooker(vg);  // 左上 炉头
                    Select_CookerView_Up_Left_Up_Right_Down_Right = true;    // 右上、右下、左上
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Down_Right; // 右上、右下、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_And_Up_Left_Slip = true;  // 右上、左上  滑动
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    currentCooker = COOKER_ID_Up_Right_And_Up_Left_Slip;  // 右上、左上  滑动;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(vg);  // 左边无区 开启
                    doPowerOnTheDownRightCooker(vg);  // 右下 炉头
                    Select_CookerView_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right; // 左上、左下、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    doPowerOnTheDownLeftCooker(vg); //左下 炉头
                    Select_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                    currentCooker = COOKER_ID_Up_Right_Down_Right_Down_Left; // 右上、右下、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    Select_CookerView_Down_Right_And_Down_Left_Slip = true; // 右下、左下  滑动
                    currentCooker = COOKER_ID_Down_Right_And_Down_Left_Slip; // 右下、左下  滑动;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下  同时
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左边无区 开启
                    doPowerOnTheDownRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 右下 炉头
                    Select_CookerView_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right; // 左上、左下、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE);// 右边无区 开启
                    doPowerOnTheDownLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); //左下 炉头
                    Select_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                    currentCooker = COOKER_ID_Up_Right_Down_Right_Down_Left; // 右上、右下、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheDownRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    doPowerOnTheDownLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    Select_CookerView_Down_Right_Down_Left = true;
                    currentCooker = COOKER_ID_Down_Right_Down_Left; // 右下、左下 同时点击
                    //  SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left__Middle:// 左上、中间  同时点击
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左边无区 开启
                    //  doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    Select_CookerView_Up_Left_Down_Left_Middle = true;// 左上、左下、中间
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Middle;//  左上、左下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheUpLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    //  doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    Select_CookerView_Up_Left__Middle = true;
                    currentCooker = COOKER_ID_Up_Left__Middle;
                    //   SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(vg);  // 左边无区 开启
                    //   doPowerOnTheMiddleCooker(vg);

                    Select_CookerView_Up_Left_Down_Left_Middle = true;// 左上、左下、中间
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Middle;//  左上、左下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheUpLeftCooker(vg);
                    //  doPowerOnTheMiddleCooker(vg);
                    Select_CookerView_Up_Left__Middle_Slip = true;// 左上、中间 滑动
                    currentCooker = COOKER_ID_Up_Left__Middle_Slip;// 左上、中间 滑动
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间  同时点击
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 左边无区 开启
                    //    doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    Select_CookerView_Up_Left_Down_Left_Middle = true;// 左上、左下、中间
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Middle;//  左上、左下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheDownLeftCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); //左下 炉头
                    //    doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);
                    Select_CookerView_Down_Left_Middle = true; // 左下、中间 同时
                    currentCooker = COOKER_ID_Down_Left_Middle; // 左下、中间 同时点击
                    //  SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheTwoCookersOfLeft(vg);  // 左边无区 开启
                    //   doPowerOnTheMiddleCooker(vg);
                    Select_CookerView_Up_Left_Down_Left_Middle = true;// 左上、左下、中间
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Middle;//  左上、左下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheDownLeftCooker(vg); //左下 炉头
                    //   doPowerOnTheMiddleCooker(vg);
                    Select_CookerView_Down_Left_Middle_Slip = true;// 左下、中间 滑动
                    currentCooker = COOKER_ID_Down_Left_Middle_Slip;// 左下、中间 滑动
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间  同时点击
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右边无区 开启
                    //  doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 中间 炉头
                    Select_CookerView_Down_Right_Up_Right_Middle = true;// 右上、右下、中间
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle;// 右上、右下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheUpRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右上 炉头
                    //  doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 中间 炉头
                    Select_CookerView_Up_Right_Middle = true; // 右上、中间 同时
                    currentCooker = COOKER_ID_Up_Right_Middle; // 右上、中间 同时点击
                    //  SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(vg); // 右边无区 开启
                    //  doPowerOnTheMiddleCooker(vg);  // 中间 炉头
                    Select_CookerView_Down_Right_Up_Right_Middle = true;// 右上、右下、中间
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle;// 右上、右下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheUpRightCooker(vg);  // 右上 炉头
                    //  doPowerOnTheMiddleCooker(vg);   // 中间 炉头
                    Select_CookerView_Up_Right_Middle_Slip = true;// 右上、中间 滑动
                    currentCooker = COOKER_ID_Up_Right_Middle_Slip;// 右上、中间  滑动
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间 同时点击
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右边无区 开启
                    //doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 中间 炉头
                    Select_CookerView_Down_Right_Up_Right_Middle = true;// 右上、右下、中间
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle;// 右上、右下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheDownRightCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE); // 右下 炉头
                    //  doPowerOnTheMiddleCooker(TFTCookerConstant.GEAR_DEFAULT_VALUE);  // 中间 炉头
                    Select_CookerView_Down_Right_Middle = true; // 右下、中间 同时
                    currentCooker = COOKER_ID_Down_Right_Middle; // 右下、中间 同时点击
                    // SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheTwoCookersOfRight(vg); // 右边无区 开启
                    // doPowerOnTheMiddleCooker(vg); // 中间 炉头
                    Select_CookerView_Down_Right_Up_Right_Middle = true;// 右上、右下、中间
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle;// 右上、右下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheDownRightCooker(vg);  // 右下 炉头
                    //  doPowerOnTheMiddleCooker(vg);   // 中间 炉头
                    Select_CookerView_Down_Right_Middle_Slip = true;// 右下、中间 滑动
                    currentCooker = COOKER_ID_Down_Right_Middle_Slip;// 右下、中间  滑动
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: // 右上、左上、左下
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Up_Left_Down_Left = true; //左上、左下 、右上
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheUpRightCooker(vg); // 右上
                    currentCooker = COOKER_ID_Up_Right_Up_Left_Down_Left; //左上、左下 、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Up_Right_Down_Right = true;    // 右上、右下、左上
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheUpRightCooker(vg);// 右上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    doPowerOnTheUpLeftCooker(vg);// 左上
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Down_Right;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheUpRightCooker(vg);  // 右上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    doPowerOnTheDownLeftCooker(vg); // 左下
                    currentCooker = COOKER_ID_Up_Right_Down_Right_Down_Left;  // 右上、右下、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheDownRightCooker(vg); // 右下
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right; // 左上、左下、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上

                Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下
                Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                doPowerOnTheUpLeftCooker(vg);
                doPowerOnTheDownLeftCooker(vg);
                doPowerOnTheDownRightCooker(vg);
                doPowerOnTheUpRightCooker(vg);
                doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true; //左上、左下、右下、右上
                currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right; //左上、左下、右下、右上
                ReadyToSelect_onCookerSelected = false;
                SetWorkingTimerWhenLinkeCookers(timermode);
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = true; //左上、右上、右下、左下
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left;//左上、右上、右下、左下;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true;//左上、左下、右下、右上
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right;//左上、左下、右下、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = true; //右上、左上、左下、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下
                    doPowerOnTheUpLeftCooker(vg);
                    doPowerOnTheDownLeftCooker(vg);
                    doPowerOnTheDownRightCooker(vg);
                    doPowerOnTheUpRightCooker(vg);
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right;//右上、左上、左下、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                   /* doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownRightCooker(vg); // 右下*/
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    // Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right; //左上、左下、右下、右上
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true; //左上、左下、右下、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    doPowerOnTheDownRightCooker(vg); // 右下
                    //doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Down_Right = true; // 左上、左下、右下
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right; // 左上、左下、右下
                    //  Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = true; // 左下、左上、中间、右下
                    //  currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Down_Right; // 左下、左上、中间、右下;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    //    doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Left_Up_Right_Down_Right = true;    // 右上、右下、左上
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Down_Right; // 右上、右下、左上
                    //  Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = true; // 右下、右上、中间、左上
                    //  currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Up_Left; // 右下、右上、中间、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownRightCooker(vg); // 右下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    Select_CookerView_Up_Left_Down_Right_Middle = true; // 左上、中间、右下
                    currentCooker = COOKER_ID_Up_Left_Down_Right_Middle; // 左上、中间、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    //  Select_CookerView_All = true;//全部
                    // currentCooker = COOKER_ID_All;//全部
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right; //左上、左下、右下、右上
                    Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = true; //左上、左下、右下、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    Select_CookerView_Up_Right_Up_Left_Down_Left = true; //左上、左下 、右上
                    currentCooker = COOKER_ID_Up_Right_Up_Left_Down_Left; //左上、左下 、右上
                    //  Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = true; // 左下、左上、中间、右上
                    // currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Up_Right; // 左下、左上、中间、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Up_Right_Down_Right_Down_Left = true; // 右上、右下、左下
                    currentCooker = COOKER_ID_Up_Right_Down_Right_Down_Left; // 右上、右下、左下
                    // Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = true;// 右下、右上、中间、左下
                    //    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Down_Left;// 右下、右上、中间、左下;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Down_Left_Middle = true; // 右上、中间、左下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    currentCooker = COOKER_ID_Up_Right_Down_Left_Middle; // 右上、中间、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = true; // 左下、左上、中间、右上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Up_Right; // 左下、左上、中间、右上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = true; // 右下、右上、中间、左上
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Up_Left; // 右下、右上、中间、左上;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Up_Right_Middle = true; // 左上、中间、右上
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpRightCooker(vg); // 右上
                    currentCooker = COOKER_ID_Up_Left_Up_Right_Middle; // 左上、中间、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = true; // 左下、左上、中间、右下
                    doPowerOnTheDownRightCooker(vg); // 右下
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Down_Right; // 左下、左上、中间、右下;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = true;// 右下、右上、中间、左下
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Down_Left;// 右下、右上、中间、左下;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Left_Down_Right_Middle = true; // 左下、中间、右下
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownRightCooker(vg); // 右下
                    currentCooker = COOKER_ID_Down_Left_Down_Right_Middle; // 左下、中间、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                doPowerOnTheUpLeftCooker(vg);  // 左上
                doPowerOnTheDownLeftCooker(vg);  // 左下
                //  doPowerOnTheMiddleCooker(vg);// 中间
                doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                Select_CookerView_Up_Left_Down_Left_Middle = true;  // 左上、左下、中间
                currentCooker = COOKER_ID_Up_Left_Down_Left_Middle; //  左上、左下、中间
                ReadyToSelect_onCookerSelected = false;
                SetWorkingTimerWhenLinkeCookers(timermode);
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                Select_CookerView_Up_Right_Down_Right = false;
                doPowerOnTheUpRightCooker(vg); // 右上
                doPowerOnTheDownRightCooker(vg); // 右下
                // doPowerOnTheMiddleCooker(vg);// 中间
                doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                Select_CookerView_Down_Right_Up_Right_Middle = true; // 右上、右下、中间
                currentCooker = COOKER_ID_Down_Right_Up_Right_Middle; // 右上、右下、中间
                ReadyToSelect_onCookerSelected = false;
                SetWorkingTimerWhenLinkeCookers(timermode);
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    Select_CookerView_Up_Left_Down_Left_Middle = true;  // 左上、左下、中间
                    currentCooker = COOKER_ID_Up_Left_Down_Left_Middle; //  左上、左下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Midlle_Down_Left = true;//左上、中间、左下
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    currentCooker = COOKER_ID_Up_Left_Midlle_Down_Left; //左上、中间、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下:
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_Down_Right_Up_Right_Middle = true; // 右上、右下、中间
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle; // 右上、右下、中间
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Right_Middle_Up_Right = true;//右上、中间、右下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheDownRightCooker(vg); // 右下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    currentCooker = COOKER_ID_Down_Right_Middle_Up_Right; //右上、中间、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheDownRightCooker(vg); // 右下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = true; // 右下、右上、中间、左下
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheDownRightCooker(vg); // 右下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Down_Left; // 右下、右上、中间、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheDownRightCooker(vg); // 右下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = true; // 右下、右上、中间、左上
                    Select_CookerView_Up_Right_Down_Right = false;
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheDownRightCooker(vg); // 右下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Up_Left; // 右下、右上、中间、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = true;// 左下、左上、中间、右上
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Up_Right; // 左下、左上、中间、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = true;// 左下、左上、中间、右下
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Down_Right; // 左下、左上、中间、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {// 左上，左下  滑动
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = true; // 左下、左上、中间、右下
                    doPowerOnTheDownRightCooker(vg);// 右下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Down_Right; // 左下、左上、中间、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_NONE_RIGHT;//全部 右边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = true; //  左上、中间、右下、左下
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownRightCooker(vg);// 右下
                    currentCooker = COOKER_ID_Up_Left_Middle_Down_Right_Down_Left;  // 左上、中间、右下、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //    doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_LEFT_NONE;//全部 左边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = true;// 右下、右上、中间、左下
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Down_Left;// 右下、右上、中间、左下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = true; //  右上、中间、左下、右下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheDownRightCooker(vg);// 右下
                    currentCooker = COOKER_ID_Up_Right_Middle_Down_Left_Down_Right; // 右上、中间、左下、右下
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = true; // 左下、左上、中间、右上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_Down_Left_Up_Left_Middle_Up_Right; // 左下、左上、中间、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                    currentCooker = COOKER_ID_ALL_ALL_NONE_RIGHT;//全部 右边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = true; //  左下、中间、右上、左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    currentCooker = COOKER_ID_Down_Left_Middle_Up_Right_Up_Left; // 左下、中间、右上、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //    doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_LEFT_NONE;//全部 左边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = true; // 右下、右上、中间、左上
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    //   doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_Down_Right_Up_Right_Middle_Up_Left; // 右下、右上、中间、左上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = true; //  右下、中间、左上、右上
                    doPowerOnTheDownRightCooker(vg);// 右下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    currentCooker = COOKER_ID_Down_Right_Middle_Up_Left_Up_Right;  // 右下、中间、左上、右上
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_All://全部
                Select_CookerView_Up_Left_Down_Left = false;
                Select_CookerView_Up_Right_Down_Right = false;
                doPowerOnTheDownLeftCooker(vg);  // 左下
                doPowerOnTheDownRightCooker(vg);// 右下
                //  doPowerOnTheMiddleCooker(vg);// 中间
                doPowerOnTheUpLeftCooker(vg);  // 左上
                doPowerOnTheUpRightCooker(vg); // 右上
                doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                Select_CookerView_All = true;//全部
                currentCooker = COOKER_ID_All;
                ReadyToSelect_onCookerSelected = false;
                SetWorkingTimerWhenLinkeCookers(timermode);
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheDownRightCooker(vg);// 右下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All; //全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_ALL_ALL_LEFT_NONE = true; //全部 左边无区
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheDownRightCooker(vg);// 右下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_LEFT_NONE;//全部 左边无区;
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheDownRightCooker(vg);// 右下
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All; //全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    doPowerOnTheDownRightCooker(vg);// 右下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_NONE_RIGHT;//全部 右边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                if (Select_CookerView_Up_Left_Down_Left && Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    Select_CookerView_All = true;//全部
                    currentCooker = COOKER_ID_All;//全部
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Left_Down_Left) {
                    Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
                    Select_CookerView_ALL_ALL_LEFT_NONE = true;//全部 左边无区
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    //  doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfLeft(vg);// 左边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_LEFT_NONE;//全部 左边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else if (Select_CookerView_Up_Right_Down_Right) {
                    Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下
                    Select_CookerView_ALL_ALL_NONE_RIGHT = true;//全部 右边无区
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    doPowerOnTheTwoCookersOfRight(vg);// 右边无区 开启
                    currentCooker = COOKER_ID_ALL_ALL_NONE_RIGHT;//全部 右边无区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                } else {
                    Select_CookerView_ALL_ALL_ANY = true;
                    doPowerOnTheDownRightCooker(vg);// 右下
                    doPowerOnTheUpRightCooker(vg); // 右上
                    doPowerOnTheUpLeftCooker(vg);  // 左上
                    doPowerOnTheDownLeftCooker(vg);  // 左下
                    // doPowerOnTheMiddleCooker(vg);// 中间
                    currentCooker = COOKER_ID_ALL_ALL_ANY; //全部 有区
                    SetWorkingTimerWhenLinkeCookers(timermode);
                }
                ReadyToSelect_onCookerSelected = false;
                break;
        }

        handler.sendEmptyMessageDelayed(HANDLER_COOKER_SELECT, 1);// 延迟 点 时间，防止 背景很快变成灰色

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TheLastClickedCooker event) {
        TheLastClickedCooker = event.getOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TheFirstClickedCooker event) {
        TheFirstClickedCooker = event.getOrder();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CookerValueChanged event) {
        switch (event.getOrder()) {
            case CookerValueChanged.ORDER_COOKER_VALUE_CHANGED:
                SaveAllCookersData();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HoodConnectStatusChangedEvent event) {
        switch (event.getHoodConnectStatus()) {
            case CataSettingConstant.HOOD_CONNECTED:
                if (!TFTCookerApplication.getInstance().isHoodAuto()) {
                    doAuto();
                    startHoodShutDownBlinkThread();
                }
                break;
            case CataSettingConstant.HOOD_NOT_CONNECTED:
                if (TFTCookerApplication.getInstance().isHoodAuto()) {
                    doAuto();
                    SetHoodSendGear(0);
                    cancelHoodShutDownBlinkThread();
                }
                doLightWOff();
                if (isInHoodStatus()) {
                    doSwitchHobMode();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 清除所有 按键动作,接收按键信息
    public void onMessageEvent(ClearTouchEvent event) {
        int get_order_from_main_activity = event.getOrder();
        MessageFromTouch = get_order_from_main_activity;

        switch (get_order_from_main_activity) {
            case Lock_All_Button:   //  锁住所有的按钮
                LockAllUI(false);
                break;
            case UnLock_All_Button:   // 解除所的锁住的按钮
                LockAllUI(true);

                click_set_to_stop_flag = false;
                click_set_to_alert_flag = false;
                CanNotTouchTheUIWhenPlayAlarm = false;
                mSoundManager.stopAlarm();
                handler.sendEmptyMessageDelayed(HANDLER_TIMER_IS_UP, 500);
                break;
            case Power_Off_ALL:
                PowerOffAll();  // 关闭所有炉头
                break;
            case Pause_Recover:  // 暂停恢复了
                doPause_recover();
                break;
            case UnLock_Click: // 解锁了
                doUnLock();
                break;
            case Check_Removing_Pan:  // 检测 锅是否被移除

                break;
            case TimerIsUp_5_Second: // 5 秒到，要 解除 所有的联动 模式
                if (workMode == WORK_MODE_HOB && BoostIsWorking == false && click_set_to_stop_flag == false && click_set_to_alert_flag == false) {
                    EventBus.getDefault().post(new ShowTimerCompleteOrder(10)); // 通知已经 关闭  了 联动模式
                }
                break;
            default:
                handler.sendEmptyMessageDelayed(HANDLER_TOUCH_SELECT_COOKER, 1);
                break;
        }
    }

    private int SetTheTimerValueOfCookerWhenLinked() {  // 检测 炉头是 在定时 状态，用于复制
        boolean IsDoingTimer = false;
        int timerMode = 0;
        switch (TheFirstClickedCooker) {
            case COOKER_ID_Up_Left:
                if (cookerViewUpLeft.isSetTimerToDefault()) {  // 当前没有定时操作

                } else {
                    timerHour = cookerViewUpLeft.getTimerHour();//getCurrentCookerTimerHour();
                    timerMinute = cookerViewUpLeft.getTimerMinute();//getCurrentCookerTimerMinute();
                    IsDoingTimer = cookerViewUpLeft.getTimerMode();
                    if (IsDoingTimer) {
                        timerMode = mode_set_to_alart;  // 当前进行的是 闹铃定时  set to alart
                    } else {
                        timerMode = mode_set_to_stop; // 当前进行的是 关闭定时 set to stop
                    }
                }
                break;
            case COOKER_ID_Down_Left:
                if (cookerViewDownLeft.isSetTimerToDefault()) {  // 当前没有定时操作

                } else {
                    timerHour = cookerViewDownLeft.getTimerHour();//getCurrentCookerTimerHour();
                    timerMinute = cookerViewDownLeft.getTimerMinute();//getCurrentCookerTimerMinute();
                    IsDoingTimer = cookerViewDownLeft.getTimerMode();
                    if (IsDoingTimer) {
                        timerMode = mode_set_to_alart;  // 当前进行的是 闹铃定时  set to alart
                    } else {
                        timerMode = mode_set_to_stop; // 当前进行的是 关闭定时 set to stop
                    }
                }
                break;
            case COOKER_ID_Up_Right:
                if (cookerViewUpRight.isSetTimerToDefault()) {  // 当前没有定时操作

                } else {
                    timerHour = cookerViewUpRight.getTimerHour();//getCurrentCookerTimerHour();
                    timerMinute = cookerViewUpRight.getTimerMinute();//getCurrentCookerTimerMinute();
                    IsDoingTimer = cookerViewUpRight.getTimerMode();
                    if (IsDoingTimer) {
                        timerMode = mode_set_to_alart;  // 当前进行的是 闹铃定时  set to alart
                    } else {
                        timerMode = mode_set_to_stop; // 当前进行的是 关闭定时 set to stop
                    }
                }
                break;
            case COOKER_ID_Down_Right:
                if (cookerViewDownRight.isSetTimerToDefault()) {  // 当前没有定时操作

                } else {
                    timerHour = cookerViewDownRight.getTimerHour();//getCurrentCookerTimerHour();
                    timerMinute = cookerViewDownRight.getTimerMinute();//getCurrentCookerTimerMinute();
                    IsDoingTimer = cookerViewDownRight.getTimerMode();
                    if (IsDoingTimer) {
                        timerMode = mode_set_to_alart;  // 当前进行的是 闹铃定时  set to alart
                    } else {
                        timerMode = mode_set_to_stop; // 当前进行的是 关闭定时 set to stop
                    }
                }
                break;
        }
        return timerMode;
    }

    private int SetTheValueOfCookerWhenLinked() {  // 更新 2018年11月14日15:06:21  获取当前炉灶的档位值，用于复制
        int v = 0;
        switch (TheFirstClickedCooker) {
            case COOKER_ID_Up_Left:
                v = cookerViewUpLeft.getGearValue();
                if (v == 0) {
                    // switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, TFTCookerConstant.GEAR_DEFAULT_VALUE, true);
                    v = TFTCookerConstant.GEAR_DEFAULT_VALUE;
                }
                break;
            case COOKER_ID_Down_Left:
                v = cookerViewDownLeft.getGearValue();
                if (v == 0) {
                    //   switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, TFTCookerConstant.GEAR_DEFAULT_VALUE, true);
                    v = TFTCookerConstant.GEAR_DEFAULT_VALUE;
                }
                break;
            case COOKER_ID_Up_Right:
                v = cookerViewUpRight.getGearValue();
                if (v == 0) {
                    //  switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, TFTCookerConstant.GEAR_DEFAULT_VALUE, true);
                    v = TFTCookerConstant.GEAR_DEFAULT_VALUE;
                }
                break;
            case COOKER_ID_Down_Right:
                v = cookerViewDownRight.getGearValue();
                if (v == 0) {
                    //  switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, TFTCookerConstant.GEAR_DEFAULT_VALUE, true);
                    v = TFTCookerConstant.GEAR_DEFAULT_VALUE;
                }
                break;
        }
        return v;

    }

    private void doSelectEachCookerInLinkingMode(int currentCooker, int vg, int timermode) {
        if (workMode == WORK_MODE_HOB || workMode == WORK_MODE_SET_TIMER_MINUTE || workMode == WORK_MODE_SET_TIMER_HOUR) {
            SetAllCookerIsGray();
            doUnLinkTimerOfAllCookers();  // 去掉 定时联动，更新 2018年11月28日15:18:19
            doUnLinkAllCooker();
            setBoostIsWorking(vg == TFTCookerConstant.GEAR_MAX_VALUE);
            doLinkEachCooker(currentCooker, vg, timermode);

            if (ReadyToSetTimer) {  // 正在设置 定时状态
            } else {
                showHobOperateMenu();
                switchCircleProgressMode(TFTCookerConstant.GEAR_MAX_VALUE, vg, true);
            }
            EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_NO_STAND_BY)); // 点击了 炉头，不能锁屏
            ChangeTheBoostButtonColor(ColorOfBoostButton_colorWhite);
        }
    }

    private void doSelectMoreCooker() {

        switch (MessageFromTouch) {
            case Up_Right_And_Down_Right_SameTime: // 左上、右下  同时

                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_And_Down_Right_SameTime, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());
                break;
            case Up_Left_And_Down_Letf_SameTime:// 左上，左下   同时操作
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_And_Down_Letf_SameTime, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());
                break;
            case Up_Left_And_Down_Letf:    // // 左上，左下   滑动操作
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());
                break;
            case Up_Right_And_Down_Right: // 右上、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());
                break;
            case Up_Left_And_Down_Right: // // 左上、右下   ，同时点击 操作
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());
                break;
            case Up_Right_And_Down_Left: // 右上、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());
                break;
            case Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_And_Up_Left_Slip, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_And_Up_Left:// 右上、左上  同时
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Up_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_And_Down_Left_Slip, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_And_Down_Left: // 右下、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left__Middle:// 左上、中间 同时
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left__Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left__Middle_Slip: // 左上、中间 滑动
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left__Middle_Slip, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Left_Middle_Slip:// 左下、中间 滑动
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Left_Middle_Slip, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Middle_Slip:// 右上、中间  滑动
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Middle_Slip, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Middle_Slip:// 右下、中间  滑动
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Middle_Slip, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Left_Middle:// 左下、中间 同时
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Left_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Middle:// 右上、中间 同时
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Middle:// 右下、中间  同时
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Down_Right_Middle: // 左上、中间、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Down_Right_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Down_Left_Middle:// 右上、中间、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Down_Left_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Up_Right_Middle://左上、中间、右上
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Up_Right_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Left_Down_Right_Middle:// 左下、中间、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Left_Down_Right_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Down_Left_Middle: // 左上、左下、中间
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Down_Left_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Up_Right_Middle: // // 右上、右下、中间
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Up_Right_Middle, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Midlle_Down_Left://左上、中间、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Midlle_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Middle_Up_Right://右上、中间、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Middle_Up_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Up_Left_Down_Left:// 右上、左上、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Up_Left_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Ddwn_Right_Down_Left: // 右上、右上、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Down_Right_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Down_Left_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Up_Right_Down_Right:// 左上、右上、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Up_Right_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Down_Left_Down_Right_Up_Right://左上、左下、右下、右上
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Up_Right_Middle_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Up_Right_Middle_Up_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Left_Up_Left_Middle_Up_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Left_Up_Left_Middle_Down_Right:// 左下、左上、中间、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Left_Up_Left_Middle_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Left_Middle_Down_Right_Down_Left: // 左上、中间、右下、左下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Left_Middle_Down_Right_Down_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                doSelectEachCookerInLinkingMode(COOKER_ID_Up_Right_Middle_Down_Left_Down_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Left_Middle_Up_Right_Up_Left:// 左下、中间、右上、左上
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Left_Middle_Up_Right_Up_Left, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case Down_Right_Middle_Up_Left_Up_Right:// 右下、中间、左上、右上
                doSelectEachCookerInLinkingMode(COOKER_ID_Down_Right_Middle_Up_Left_Up_Right, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case ALL_ALL: //全部
                doSelectEachCookerInLinkingMode(COOKER_ID_All, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case ALL_ALL_ANY: //全部 有区
                doSelectEachCookerInLinkingMode(COOKER_ID_ALL_ALL_ANY, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case ALL_ALL_LEFT_NONE: // 全部，左边无区
                doSelectEachCookerInLinkingMode(COOKER_ID_ALL_ALL_LEFT_NONE, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
            case ALL_ALL_NONE_RIGHT: // 全部，右边无区
                doSelectEachCookerInLinkingMode(COOKER_ID_ALL_ALL_NONE_RIGHT, SetTheValueOfCookerWhenLinked(), SetTheTimerValueOfCookerWhenLinked());

                break;
        }
    }

    /**
     * 切换 Boost 工作状态，在非 Boost 状态，只允许选择 1-9 档
     *
     * @param value
     */
    private void setBoostIsWorking(boolean value) {
        BoostIsWorking = value;
        if (value) {
            restoreSelectRange();
        } else {
            restrictSelectRange();
        }
    }

    private void restoreSelectRange() {
        trianProgress.setMinSelectValue(TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO);
        trianProgress.setMaxSelectValue(TFTCookerConstant.GEAR_MAX_VALUE);
    }

    private void restrictSelectRange() {
        trianProgress.setMinSelectValue(TFTCookerConstant.GEAR_MIN_SELECT_VALUE);
        trianProgress.setMaxSelectValue(TFTCookerConstant.GEAR_MAX_SELECT_VALUE);
    }

    private Map<Integer, Integer> getCookerViewsInProcess() {
        Map<Integer, Integer> cookers = new LinkedHashMap<>();
        if (Select_CookerView_Up_Left_Down_Left) {
            if (cookerViewUpLeft.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO
                    && cookerViewDownLeft.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO) {
                cookers.put(COOKER_ID_Up_Left, cookerViewUpLeft.getGearValue());
            }
        } else {
            if (cookerViewUpLeft.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO) {
                cookers.put(COOKER_ID_Up_Left, cookerViewUpLeft.getGearValue());
            }
            if (cookerViewDownLeft.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO) {
                cookers.put(COOKER_ID_Down_Left, cookerViewDownLeft.getGearValue());
            }
        }

        if (Select_CookerView_Up_Right_Down_Right) {
            if (cookerViewUpRight.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO
                    && cookerViewDownRight.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO) {
                cookers.put(COOKER_ID_Up_Right, cookerViewUpRight.getGearValue());
            }
        } else {
            if (cookerViewUpRight.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO) {
                cookers.put(COOKER_ID_Up_Right, cookerViewUpRight.getGearValue());
            }
            if (cookerViewDownRight.getGearValue() > TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO) {
                cookers.put(COOKER_ID_Down_Right, cookerViewDownRight.getGearValue());
            }
        }

        return cookers;
    }

    private void SaveAllCookersData() {
        DatabaseHelper.SaveCookersData(0L,
                1, getRefinedGearValue(cookerViewDownLeft), 0,
                1, getRefinedGearValue(cookerViewUpLeft), 0,
                1, getRefinedGearValue(cookerViewUpRight), 0,
                1, getRefinedGearValue(cookerViewDownRight), 0,
                1, 0, 0,
                1, 0, 0,
                hoodGear_sended, lightCearValue_sended, lightGear_orange, 0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AnalysisSerialData event) {
        if (TFTCookerApplication.getInstance().isInDemonstrationMode()) return;

        CheckRemovingPan(event);
        checkCookerHighTemperature(event);
        checkErrors(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NoPanDetectedEvent event) {
        int cookerViewId = event.getCookerViewId();
        int eventType = event.getEventType();
        switch (eventType) {
            case NoPanDetectedEvent.EVENT_TYPE_SET_UI:
                if (cookerViewId == cookerViewUpLeft.getId()) {
                    if (noPanLeftCookers) {
                        tvLeftMiddleGear.setText(NO_PAN_DISPLAY_TEXT);
                        tvLeftMiddleGearWhenTimer.setText(NO_PAN_DISPLAY_TEXT);
                    }
                } else if (cookerViewId == cookerViewUpRight.getId()) {
                    if (noPanRightCookers) {
                        tvRightMiddleGear.setText(NO_PAN_DISPLAY_TEXT);
                        tvRightMiddleGearWhenTimer.setText(NO_PAN_DISPLAY_TEXT);
                    }
                } else if (cookerViewId == cookerViewDownLeft.getId()) {
                    if (noPanLeftCookers) {
                        tvLeftMiddleGear.setText(NO_PAN_DISPLAY_TEXT);
                        tvLeftMiddleGearWhenTimer.setText(NO_PAN_DISPLAY_TEXT);
                    }
                } else if (cookerViewId == cookerViewDownRight.getId()) {
                    if (noPanRightCookers) {
                        tvRightMiddleGear.setText(NO_PAN_DISPLAY_TEXT);
                        tvRightMiddleGearWhenTimer.setText(NO_PAN_DISPLAY_TEXT);
                    }
                }
                if (cookerIsActive(cookerViewId)) {
                    displayNoPanUI(true);
                }
                break;
            case NoPanDetectedEvent.EVENT_TYPE_POWER_OFF:
                if (workMode == WORK_MODE_SET_TIMER_MINUTE
                        || workMode == WORK_MODE_SET_TIMER_HOUR
                        || workMode == WORK_MODE_SET_TIMER) {
                    doCancelTimer();
                    ReadyToSetTimer = false;
                }
                doUnLinkTimerOfAllCookers();
                doUnLinkAllCooker();
                if (cookerIsActive(cookerViewId)) {
                    displayNoPanUI(false);
                }
                PowerOffWhenNoPan(cookerViewId);
                if (TFTCookerApplication.getInstance().isHoodAuto()) {
                    setAutoUI();
                }
                break;
            case NoPanDetectedEvent.EVENT_TYPE_PAN_IS_BACK:
                if (cookerIsActive(cookerViewId)) {
                    displayNoPanUI(false);
                }
                break;
        }
    }

    private boolean cookerIsActive(int id) {
        return (id == cookerViewUpLeft.getId() && isCookerSelected(COOKER_ID_Up_Left))
                || (id == cookerViewUpRight.getId() && isCookerSelected(COOKER_ID_Up_Right))
                || (id == cookerViewDownLeft.getId() && isCookerSelected(COOKER_ID_Down_Left))
                || (id == cookerViewDownRight.getId() && isCookerSelected(COOKER_ID_Down_Right));
    }

    private void displayNoPanUI(boolean noPanDetected) {
        if (workMode != WORK_MODE_HOB) {
            return;
        }
        if (noPanDetected) {
            trianProgress2.setVisibility(View.VISIBLE);
            trianProgress2.disable();
            trianProgress.setVisibility(View.INVISIBLE);
            tvValue.setVisibility(View.INVISIBLE);
            tvValueHint.setVisibility(View.INVISIBLE);
            autoFlag.setVisibility(View.INVISIBLE);
            tvCookwareNotDetected.setVisibility(View.VISIBLE);
            LogUtil.d("get the no pot");
        } else if (!noPanDetected) {
            trianProgress2.setVisibility(View.INVISIBLE);
            trianProgress.setVisibility(View.VISIBLE);
            tvValue.setVisibility(View.VISIBLE);
            tvValueHint.setVisibility(View.VISIBLE);
            if (TFTCookerApplication.getInstance().isHoodAuto()) {
                autoFlag.setVisibility(View.VISIBLE);
            }
            tvCookwareNotDetected.setVisibility(View.INVISIBLE);
        }
    }

    private void CheckRemovingPan(AnalysisSerialData event) {
        if (event.isPoweredOn()) {// 开机
            boolean hasPanA = (byte) (event.getAInfo() & 0x03) != 0x00;
            boolean hasPanB = (byte) (event.getBInfo() & 0x03) != 0x00;
            boolean hasPanC = (byte) (event.getCInfo() & 0x03) != 0x00;
            boolean hasPanD = (byte) (event.getDInfo() & 0x03) != 0x00;

            noPanLeftCookers = !hasPanA && !hasPanB;
            noPanRightCookers = !hasPanC && !hasPanD;
            if (leftCookersUnited()) {
                if (cookerViewDownLeft.getGearValue() > 0) {
                    cookerViewUpLeft.CheckRemovingPan(hasPanA || hasPanB);
                    cookerViewDownLeft.CheckRemovingPan(hasPanA || hasPanB);
                    if (hasPanA || hasPanB) {
                        setHobGearText(tvLeftMiddleGear, cookerViewUpLeft.getGearValue());
                        setHobGearText(tvLeftMiddleGearWhenTimer, cookerViewUpLeft.getGearValue());
                    }
                }
            } else {
                if (cookerViewDownLeft.getGearValue() > 0) {
                    cookerViewDownLeft.CheckRemovingPan(hasPanA);
                }
                if (cookerViewUpLeft.getGearValue() > 0) {
                    cookerViewUpLeft.CheckRemovingPan(hasPanB);
                }
            }

            if (rightCookersUnited()) {
                if (cookerViewUpRight.getGearValue() > 0) {
                    cookerViewUpRight.CheckRemovingPan(hasPanC || hasPanD);
                    cookerViewDownRight.CheckRemovingPan(hasPanC || hasPanD);
                    if (hasPanC || hasPanD) {
                        setHobGearText(tvRightMiddleGear, cookerViewUpRight.getGearValue());
                        setHobGearText(tvRightMiddleGearWhenTimer, cookerViewUpRight.getGearValue());
                    }
                }
            } else {
                if (cookerViewUpRight.getGearValue() > 0) {
                    cookerViewUpRight.CheckRemovingPan(hasPanC);
                }
                if (cookerViewDownRight.getGearValue() > 0) {
                    cookerViewDownRight.CheckRemovingPan(hasPanD);
                }
            }
        }
    }

    private void checkCookerHighTemperature(AnalysisSerialData event) {
        if (event.isPoweredOn()) {
            boolean highPanA = (byte) (event.getAInfo() & 0x08) == 0x08;
            boolean highPanB = (byte) (event.getBInfo() & 0x08) == 0x08;
            boolean highPanC = (byte) (event.getCInfo() & 0x08) == 0x08;
            boolean highPanD = (byte) (event.getDInfo() & 0x08) == 0x08;
            cookerViewDownLeft.ShowHighTempStatus(highPanA);
            cookerViewUpLeft.ShowHighTempStatus(highPanB);
            cookerViewUpRight.ShowHighTempStatus(highPanC);
            cookerViewDownRight.ShowHighTempStatus(highPanD);
            if (highPanA || highPanB || highPanC || highPanD) {
                // 有高温时，不进行待机计时
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                EventBus.getDefault().post(new CookerHighTempOrder());
            }
        }
    }

    private void SetLightSendedGear(int value) {
        if (value != 0) {
            lastLightGearBlueUsed = value;
        }
        byte tempValue = 0x00;
        tempValue = (byte) value;
        if (IsLightBlue) {
            lightCearValue_sended = tempValue | 0x80;    // 蓝色灯
        } else {
            lightCearValue_sended = tempValue | 0x00;  // 橙色灯
        }
        SaveAllCookersData();
    }

    private void setHobGearText(TextView tv, int v) {
        if (v == 10) {
            tv.setText(R.string.title_boost_short);
        } else {
            tv.setText("" + v);
        }
    }

    private void RemoveAllTimerInformationForCurrentCookers() {
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                //   cookerViewUpLeft.powerOff();
                cookerViewUpLeft_boost_flag = false;
                break;
            case COOKER_ID_Down_Left:
                //  cookerViewDownLeft.powerOff();
                cookerViewDownLeft_boost_flag = false;
                break;
            case COOKER_ID_Middle:
                //cookerViewMiddle.powerOff();
                //   cookerViewMiddle_boost_flag = false;
                break;
            case COOKER_ID_Up_Right:
                // cookerViewUpRight.powerOff();
                cookerViewUpRight_boost_flag = false;
                break;
            case COOKER_ID_Down_Right:
                // cookerViewDownRight.powerOff();
                cookerViewDownRight_boost_flag = false;

                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下  同时点击
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();

              /*  cookerViewUpLeft_boost_flag = false;// 左上
                cookerViewDownLeft_boost_flag = false;// 左下
                boost_flag_CookerView_Up_Left_Down_Left = false;  // 左上，左下*/
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                cookerViewUpRight.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                /* cookerViewUpRight_boost_flag = false; // 右上
                 cookerViewDownRight_boost_flag = false;// 右下*/
                /*   boost_flag_CookerView_Up_Right_Down_Right = false;  // 右上、右下*/
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Up_Left_Down_Left: // 左上，左下  滑动

                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();

             /*   cookerViewUpLeft_boost_flag = false;// 左上
                cookerViewDownLeft_boost_flag = false;// 左下*/
                /*   boost_flag_CookerView_Up_Left_Down_Left = false;  // 左上，左下*/
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下  滑动

                cookerViewUpRight.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight_boost_flag = false; // 右上
                cookerViewDownRight_boost_flag = false;// 右下
                /*   boost_flag_CookerView_Up_Right_Down_Right = false;  // 右上、右下*/
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                cookerViewUpRight.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();

                break;
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                cookerViewUpRight.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动

                cookerViewUpRight.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Left__Middle:// 左上、中间

                //  cookerViewMiddle.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动

                //   cookerViewMiddle.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Left_Middle:// 左下、中间

                //   cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动

                //cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Right_Middle:// 右上、中间

                // cookerViewMiddle.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动

                //   cookerViewMiddle.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_Middle:// 右下、中间

                // cookerViewMiddle.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动

                //   cookerViewMiddle.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下

                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                //   cookerViewMiddle.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //  cookerViewMiddle.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上

                cookerViewUpLeft.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //    cookerViewMiddle.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                //  cookerViewMiddle.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                //   cookerViewMiddle.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上

                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                //    cookerViewMiddle.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下

                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //   cookerViewMiddle.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间

                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //  cookerViewMiddle.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();

                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left:// 左上、左下、右上

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right:// 左上、左下、右下
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上

                cookerViewUpLeft.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left:// 右上、右下、左下
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //     cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                // cookerViewMiddle.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上

                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                // cookerViewMiddle.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right:// 左下、左上、中间、右下
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                // cookerViewMiddle.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下

                cookerViewUpLeft.ResetTimerInformation();
                //  cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下

                cookerViewUpRight.ResetTimerInformation();
                //  cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewDownRight.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                cookerViewDownLeft.ResetTimerInformation();
                //   cookerViewMiddle.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                cookerViewDownRight.ResetTimerInformation();
                //  cookerViewMiddle.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_All://全部
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //   cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区

                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                //cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                // cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                HideTvLeftMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
                click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                cookerViewDownRight.ResetTimerInformation();
                cookerViewUpRight.ResetTimerInformation();
                // cookerViewMiddle.ResetTimerInformation();
                cookerViewDownLeft.ResetTimerInformation();
                cookerViewUpLeft.ResetTimerInformation();
                HideTvRightMiddleGearWhenTimerAndClockWhenTimer();
                click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
                click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
                break;
        }
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_COOKER_Timer_Complete:
                break;
            case HANDLER_COOKER_SELECT:
                ReadyToSelect_onCookerSelected = true;
                SaveAllCookersData(); // 保存 所有数据 2018年11月24日10:25:48
                break;
            case HANDLER_TOUCH_SCREEN:  // 检测 5 秒，后，是否可以关掉 联动模式

                ChechTouchScreen();
                break;
            case HANDLER_TOUCH_SELECT_COOKER:
                if (click_lock_flag || click_pause_flag || CanNotTouchTheUIWhenPlayAlarm) {  // 在锁的状态, 在暂停的状态
                } else {
                    doSelectMoreCooker();
                }
                break;
            case HANDLER_TIMER_IS_UP:
                mSoundManager.stopAlarm();
                break;
            case HANDLER_TOUCH_SCREEN_ONE_MINUTE:  // 检测一分钟是否可以退到屏蔽状态
                if (cookerViewUpLeft.getGearValue() == 0
                        && cookerViewDownLeft.getGearValue() == 0
                        && cookerViewUpRight.getGearValue() == 0
                        && cookerViewDownRight.getGearValue() == 0) {
                    EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_RESUME_STAND_BY)); // 可以锁屏
                    LogUtil.d("go to stand by");
                } else {  // 还有炉头在工作，不能关
                    EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_NO_STAND_BY));// 炉头还在工作，不可以锁屏
                    LogUtil.d("wait stand by");
                }
                break;
            case HANDLER_TIMER_DOING:  // 定时在进行中
                CheckEveryCookerOfTimerStatus();
                break;
            case HANDLER_UPDATE_CIRCLE_PROGRESS: // 选择模式后，更新 圆环值
                doUpdateCircleProgressValueAfterSelectMoreCookers();
                break;
            case UPDATE_CIRCLE_PROGRESS_WHEN_POWER_OFF:
                if (workMode == WORK_MODE_HOB) {
                    switchCircleProgressMode(
                            TFTCookerConstant.GEAR_MAX_VALUE,
                            TFTCookerConstant.GEAR_DEFAULT_VALUE_ZERO,
                            false,
                            true);
                }
                break;
            case HANDLER_SET_HOOD_AUTO:
                doSaveHoodAuto();
                break;
            case HANDLER_ADJUST_LEVEL:
                if (msg.arg1 > 0) {
                    trianProgress.setValue(msg.arg1);
                } else {
                    onViewClicked(tvStop);
                }
                break;
            case HANDLER_HOOD_NUMBER_BLINK:
                if (activeCookers.size() > 0 || !TFTCookerApplication.getInstance().isHoodAuto()) {
                    // 如果当前开了炉头或者已经不处于 hoodAuto 的状态，则取消闪烁线程
                    cancelHoodShutDownBlinkThread();
                    if (tvValueShouldBeVisible()) {
                        tvValue.setVisibility(View.VISIBLE);
                    }
                } else if (msg.arg1 == 1) {
                    // 闪烁过程结束
                    if (tvValueShouldBeVisible()) {
                        tvValue.setVisibility(View.VISIBLE);
                    }
                } else if (workMode == WORK_MODE_HOOD) {
                    // 在烟机界面进行闪烁
                    tvValue.setVisibility(tvValue.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                } else {
                    if (tvValueShouldBeVisible()) {
                        tvValue.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case HANDLER_POWER_OFF_FROM_ERROR03:
                if(mPowerOffFromE03){
                    mPowerOffFromE03=false;
                }
                break;
        }
    }

    private void SetHoodSendGear(int value) {
        byte tempValue = (byte) value;
        if (TFTCookerApplication.getInstance().isHoodAuto()) {
            tempValue = (byte) getAutoHoodGearLevel(value);
            hoodGear_sended = tempValue | 0x00;    // 自动
        } else {
            hoodGear_sended = tempValue | 0x80;  // 手动
        }
        SaveAllCookersData();
    }

    private boolean tvValueShouldBeVisible() {
        return tvCookwareNotDetected.getVisibility() != View.VISIBLE
                && llTimerArea.getVisibility() != View.VISIBLE;
    }

    private void PowerOffWhenNoPan(int value) {
        if (value == cookerViewUpLeft.getId()) { // 左上
            if (leftCookersUnited()) {
                if (noPanLeftCookers) {
                    // 清除左边无区的定时标志
                    if (click_set_to_alert_flag_Up_Left_Down_Left || click_set_to_stop_flag_Up_Left_Down_Left) {
                        HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                        click_set_to_alert_flag_Up_Left_Down_Left = false;
                        click_set_to_stop_flag_Up_Left_Down_Left = false;
                    }
                    cookerViewUpLeft.powerOff();
                    cookerViewDownLeft.powerOff();
                }
            } else {
                cookerViewUpLeft.powerOff();
            }
        } else if (value == cookerViewDownLeft.getId()) {  // 左下
            if (leftCookersUnited()) {
                if (noPanLeftCookers) {
                    // 清除左边无区的定时标志
                    if (click_set_to_alert_flag_Up_Left_Down_Left || click_set_to_stop_flag_Up_Left_Down_Left) {
                        HideTvLeftMiddleGearWhenTimerAndClockWhenTimer_Stop();
                        click_set_to_alert_flag_Up_Left_Down_Left = false;
                        click_set_to_stop_flag_Up_Left_Down_Left = false;
                    }
                    cookerViewUpLeft.powerOff();
                    cookerViewDownLeft.powerOff();
                }
            } else {
                cookerViewDownLeft.powerOff();
            }
        } else if (value == cookerViewUpRight.getId()) {  // 右上
            if (rightCookersUnited()) {
                if (noPanRightCookers) {
                    // 清除右边无区的定时标志
                    if (click_set_to_alert_flag_Up_Right_Down_Right || click_set_to_stop_flag_Up_Right_Down_Right) {
                        HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                        click_set_to_alert_flag_Up_Right_Down_Right = false;
                        click_set_to_stop_flag_Up_Right_Down_Right = false;
                    }
                    cookerViewUpRight.powerOff();
                    cookerViewDownRight.powerOff();
                }
            } else {
                cookerViewUpRight.powerOff();
            }
        } else if (value == cookerViewDownRight.getId()) {  // 右下
            if (rightCookersUnited()) {
                if (noPanRightCookers) {
                    // 清除右边无区的定时标志
                    if (click_set_to_alert_flag_Up_Right_Down_Right || click_set_to_stop_flag_Up_Right_Down_Right) {
                        HideTvRightMiddleGearWhenTimerAndClockWhenTimer_Stop();
                        click_set_to_alert_flag_Up_Right_Down_Right = false;
                        click_set_to_stop_flag_Up_Right_Down_Right = false;
                    }
                    cookerViewUpRight.powerOff();
                    cookerViewDownRight.powerOff();
                }
            } else {
                cookerViewDownRight.powerOff();
            }
        }
    }

    @Override
    public boolean leftCookersUnited() {
        return Select_CookerView_Up_Left_Down_Left || Select_CookerView_Up_Left_Down_Left_Middle || Select_CookerView_Up_Right_Up_Left_Down_Left
                || Select_CookerView_Up_Left_Down_Left_Down_Right || Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right
                || Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right || Select_CookerView_Down_Left_Up_Left_Middle_Up_Right
                || Select_CookerView_Down_Left_Up_Left_Middle_Down_Right || Select_CookerView_All
                || Select_CookerView_ALL_ALL_LEFT_NONE;
    }

    @Override
    public boolean rightCookersUnited() {
        return Select_CookerView_Up_Right_Down_Right || Select_CookerView_Down_Right_Up_Right_Middle || Select_CookerView_Up_Right_Down_Right_Down_Left ||
                Select_CookerView_Up_Left_Up_Right_Down_Right || Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left
                || Select_CookerView_Down_Right_Up_Right_Middle_Down_Left || Select_CookerView_Down_Right_Up_Right_Middle_Up_Left || Select_CookerView_All
                || Select_CookerView_ALL_ALL_NONE_RIGHT;
    }

    private boolean CheckTheBoostStatus() {
        return boost_flag_CookerView_Up_Left_Down_Left || boost_flag_CookerView_Up_Right_Down_Right || boost_flag_CookerView_Up_Left_Down_Left_SameTime
                || boost_flag_CookerView_Up_Right_Down_Right_SameTime || boost_flag_CookerView_Up_Left_Down_Right || boost_flag_CookerView_Up_Right_Down_Left ||
                boost_flag_CookerView_Up_Right_Up_Left || boost_flag_CookerView_Down_Right_Down_Left || boost_flag_CookerView_Up_Right_And_Up_Left_Slip || boost_flag_CookerView_Down_Right_And_Down_Left_Slip
                || boost_flag_CookerView_Down_Right_And_Down_Left_Slip || boost_flag_CookerView_Up_Left__Middle || boost_flag_CookerView_Down_Left_Middle ||
                boost_flag_CookerView_Up_Right_Middle || boost_flag_CookerView_Down_Right_Middle || boost_flag_CookerView_Up_Left__Middle_Slip
                || boost_flag_CookerView_Down_Left_Middle_Slip || boost_flag_CookerView_Up_Right_Middle_Slip || boost_flag_CookerView_Down_Right_Middle_Slip ||
                boost_flag_CookerView_Up_Left_Down_Right_Middle || boost_flag_CookerView_Up_Right_Down_Left_Middle || boost_flag_CookerView_Up_Left_Up_Right_Middle
                || boost_flag_CookerView_Down_Left_Down_Right_Middle || boost_flag_CookerView_Up_Left_Down_Left_Middle || boost_flag_CookerView_Down_Right_Up_Right_Middle
                || boost_flag_CookerView_Up_Left_Midlle_Down_Left || boost_flag_CookerView_Down_Right_Middle_Up_Right || boost_flag_CookerView_Up_Right_Down_Right_Down_Left
                || boost_flag_CookerView_Up_Right_Down_Right_Up_Left || boost_flag_CookerView_Up_Left_Down_Left_Down_Right || boost_flag_CookerView_Up_Left_Down_Left_Up_Right ||
                boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right || boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left || boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right ||
                boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left || boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left || boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right ||
                boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right || boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left || boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right ||
                boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left || boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right || boost_flag_CookerView_All || boost_flag_CookerView_ALL_ANY || boost_flag_CookerView_ALL_ALL_LEFT_NONE ||
                boost_flag_CookerView_ALL_ALL_NONE_RIGHT;
    }

    private boolean currentCookerPoweredOn() {
        boolean flag = false;
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                if (cookerViewUpLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Left:

                if (cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Middle:
                break;
            case COOKER_ID_Up_Right:
                if (cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right:
                if (cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击

                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下

                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewUpLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                if (cookerViewDownRight.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                if (cookerViewUpLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                if (cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }

                break;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                if (cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                if (cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }

                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                if (cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                if (cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_All://全部
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                if (cookerViewUpLeft.getGearValue() > 0
                        || cookerViewDownLeft.getGearValue() > 0
                        || cookerViewUpRight.getGearValue() > 0
                        || cookerViewDownRight.getGearValue() > 0) {
                    flag = true;
                }
                break;
        }
        return flag;
    }

    private void setTimerIconWhenFreeCookers_set_to_stop(TextView tv) {  // set to stop

        Drawable drawable = this.getResources().getDrawable(R.mipmap.set_to_stop_normal_small);
        drawable.setBounds(0, 0, 30, 30);
        tv.setCompoundDrawablePadding(5);
        tv.setCompoundDrawables(null, null, null, drawable);
    }

    private void setTimerIconWhenFreeCookers_set_to_alert(TextView tv) {  //  set to alert

        Drawable drawable = this.getResources().getDrawable(R.mipmap.set_to_alert_normal_small);
        drawable.setBounds(0, 0, 27, 30);
        tv.setCompoundDrawablePadding(4);
        tv.setCompoundDrawables(null, null, null, drawable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onTrianProgress(float value, boolean fromWidget) {
        Logger.getInstance().i("onProgress(" + value + "," + fromWidget + ")");
        int iValue = (int) Math.ceil(value);
        if (Math.round(value) == 0) {
            iValue = 0;
        }

        if (workMode == WORK_MODE_HOB) {// 炉灶 模式
            if (!doingPowerOffCooker) {

                // 在关闭无区时，两个炉头会依次关闭，关闭第一个炉头后，会自动选中第二个，
                // 此时执行到此处时，可能串口数据还没来得及同步，导致 InductionCookerHardwareManager
                // 返回错误的 newValue
                // 因为是在进行关闭炉头的操作，并未重新开启新的炉头，故此处也不需要进行询问
                int newValue = getMaxHobLevel(currentCooker, iValue);
                if (newValue < iValue) {
                    Message msg = new Message();
                    msg.what = HANDLER_ADJUST_LEVEL;
                    msg.arg1 = newValue;
                    handler.sendMessageDelayed(msg, 200);
                    return;
                }
            }

            updateCookerValue(iValue);
            setHoodAuto();
            this.SaveAllCookersData();
        } else if (workMode == WORK_MODE_HOOD) { // 风机模式
            if (hoodGear == iValue) {  // 值没有改变，说明 没有点击 圈圈。

            } else {
                if (TFTCookerApplication.getInstance().isHoodAuto()) {
                    TFTCookerApplication.getInstance().setHoodAuto(false);
                }
                hoodGear = iValue;
                setAutoUI();
                this.SaveAllCookersData();
            }
        } else if (workMode == WORK_MODE_SET_LIGHT) {
            lightGear_blue = iValue;
            SetLightSendedGear(iValue);
            tvValue.setText(String.valueOf(lightGear_blue));
        } else if (workMode == WORK_MODE_SET_TIMER_HOUR) {
            timerHour = iValue;
            tvTimerHour.setText(String.format(HOUR_FORMAT, timerHour));
            if (timerHour == this.getMaxHours()) {
                int maxMinutes = this.getMaxMinutes();
                if (maxMinutes == TFTCookerConstant.TIMER_MINUTE_MAX_VALUE) {
                    timerMinute = 0;
                    tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
                } else if (timerMinute > maxMinutes) {
                    timerMinute = maxMinutes;
                    tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
                }
            } else if ((timerHour == 0) && (timerMinute == 0)) {
                timerMinute = 1;
                tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
            }

        } else if (workMode == WORK_MODE_SET_TIMER_MINUTE) {
            timerMinute = iValue;
            tvTimerMinute.setText(String.format(MINUTE_FORMAT, timerMinute));
            if (timerHour == this.getMaxHours()) {
                int maxMinutes = this.getMaxMinutes();
                if (maxMinutes == TFTCookerConstant.TIMER_MINUTE_MAX_VALUE) {
                    if (timerMinute != 0) {
                        timerHour--;
                        tvTimerHour.setText(String.format(HOUR_FORMAT, timerHour));
                    }
                }
            }
        }
    }

    private void setSizeWhenSetTimer(){
        int language = SettingPreferencesUtil.getDefaultLanguage(getContext());
        switch (language) {
            case TFTCookerConstant.LANGUAGE_POLISH:
                //  c.locale = new Locale("pl");
                tvValueHint.setTextSize(mSetPauseSize);
                break;

            default:
                //   c.locale = Locale.getDefault();
                break;
        }
    }

    private void setSizeWhenPause(){
        int language = SettingPreferencesUtil.getDefaultLanguage(getContext());
        switch (language) {
            case TFTCookerConstant.LANGUAGE_ROMANIAN:
                //   c.locale = new Locale("ro");
                //   tvValueHint.setTextSize(mSetPauseSize);
                break;
            case TFTCookerConstant.LANGUAGE_ENGLISH:
                //  c.locale = Locale.ENGLISH;
                break;
            case TFTCookerConstant.LANGUAGE_FRENCH:
                // c.locale = Locale.FRENCH;
                tvValueHint.setTextSize(33.0f);
                break;
            case TFTCookerConstant.LANGUAGE_POLISH:
                //  c.locale = new Locale("pl");
                tvValueHint.setTextSize(mSetPauseSize);
                break;
            case TFTCookerConstant.LANGUAGE_PORTUGUESE:
                //  c.locale = new Locale("pt");
                break;
            case TFTCookerConstant.LANGUAGE_TURKISH:
                // c.locale = new Locale("tr");
                int size= (int)tvValueHint.getTextSize();
                LogUtil.d("the text size is "+size);
                tvValueHint.setTextSize(mSetPauseSize);
                break;
            default:
                //   c.locale = Locale.getDefault();
                break;
        }

    }

    private void setSizeWhenRecoverFormPause(){
        int language = SettingPreferencesUtil.getDefaultLanguage(getContext());
        switch (language) {
            case TFTCookerConstant.LANGUAGE_ROMANIAN:
                //   c.locale = new Locale("ro");
                // tvValueHint.setTextSize(40.0f);
                break;
            case TFTCookerConstant.LANGUAGE_ENGLISH:
                //  c.locale = Locale.ENGLISH;
                break;
            case TFTCookerConstant.LANGUAGE_FRENCH:
                // c.locale = Locale.FRENCH;
                tvValueHint.setTextSize(40.0f);
                break;
            case TFTCookerConstant.LANGUAGE_POLISH:
                //  c.locale = new Locale("pl");
                tvValueHint.setTextSize(40.0f);
                break;
            case TFTCookerConstant.LANGUAGE_PORTUGUESE:
                //  c.locale = new Locale("pt");
                break;
            case TFTCookerConstant.LANGUAGE_TURKISH:
                // c.locale = new Locale("tr");
                int size= (int)tvValueHint.getTextSize();
                LogUtil.d("the text size is "+size);
                tvValueHint.setTextSize(40.0f);
                break;
            default:
                //   c.locale = Locale.getDefault();
                break;
        }
    }
}
