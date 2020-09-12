package com.ekek.tftcooker;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekek.hardwaremodule.entity.CookerHardwareResponse;
import com.ekek.hardwaremodule.serial.InductionCookerHardwareManager;
import com.ekek.tftcooker.base.BaseActivity;
import com.ekek.tftcooker.base.BaseThread;
import com.ekek.tftcooker.base.CookerPanelFragment;
import com.ekek.tftcooker.base.ProductManager;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.common.PatternLockView;
import com.ekek.tftcooker.common.SoundUtil;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.CookerEventDBHelper;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.entity.CookerEvent;
import com.ekek.tftcooker.event.BluetoothEvent;
import com.ekek.tftcooker.event.ClearTouchEvent;
import com.ekek.tftcooker.event.CookerHighTempOrder;
import com.ekek.tftcooker.event.DebugInfoEvent;
import com.ekek.tftcooker.event.PowerOffAllOrder;
import com.ekek.tftcooker.event.PowerSwitchEvent;
import com.ekek.tftcooker.event.SendSystemSettingOrder;
import com.ekek.tftcooker.event.ShowErrorOrder;
import com.ekek.tftcooker.event.ShowFilterWarningOrder;
import com.ekek.tftcooker.event.ShowNotificationScreenOrder;
import com.ekek.tftcooker.event.ShowTimerCompleteOrder;
import com.ekek.tftcooker.event.TheLastClickedCooker;
import com.ekek.tftcooker.event.WorkModeChangedEvent;
import com.ekek.tftcooker.fragment.CookerIntroFragment;
import com.ekek.tftcooker.fragment.SystemSettingFragmentBase;
import com.ekek.tftcooker.fragment.SystemSettingFragmentBrightness;
import com.ekek.tftcooker.fragment.SystemSettingFragmentConnect;
import com.ekek.tftcooker.fragment.SystemSettingFragmentConnectHowTo0;
import com.ekek.tftcooker.fragment.SystemSettingFragmentConnectHowTo1;
import com.ekek.tftcooker.fragment.SystemSettingFragmentDate;
import com.ekek.tftcooker.fragment.SystemSettingFragmentFilter;
import com.ekek.tftcooker.fragment.SystemSettingFragmentLanguage;
import com.ekek.tftcooker.fragment.SystemSettingFragmentPower;
import com.ekek.tftcooker.fragment.SystemSettingFragmentReset;
import com.ekek.tftcooker.fragment.SystemSettingFragmentSound;
import com.ekek.tftcooker.fragment.SystemSettingFragmentTime;
import com.ekek.tftcooker.fragment.SystemSettingFragmentUpgrade;
import com.ekek.tftcooker.listener.PatternLockViewListener;
import com.ekek.tftcooker.model.AllCookerDataEx;
import com.ekek.tftcooker.model.AnalysisSerialData;
import com.ekek.tftcooker.model.GetDataThatSendToMicroControler;
import com.ekek.tftcooker.model.HandlePatternLockView;
import com.ekek.tftcooker.utils.BrightnessUtil;
import com.ekek.tftcooker.utils.DateTimeUtil;
import com.ekek.tftcooker.utils.LogUtil;
import com.ekek.tftcooker.utils.PatternLockUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.ekek.tftcooker.utils.ResourceUtils;
import com.ekek.tftcooker.utils.SoundManager;
import com.ekek.tftcooker.views.ConfirmDialog;
import com.ekek.tftcooker.views.QuarterOvalImageView;

public class MainActivity extends BaseActivity
        implements CookerIntroFragment.OnHobIntroFragmentListener,
        PatternLockViewListener,
        ConfirmDialog.OnConfirmedListener,
        InductionCookerHardwareManager.OnInductionCookerHardwareManagerListener{
    private static final int FRAGMENT_SHOW_COOKER_PANEL = 1;
    private static final int FRAGMENT_SHOW_COOKER_PANEL_Intro = 3;

    private static final int System_setting_panel = 4;
    private static final int System_setting_time = 5;
    private static final int System_setting_date = 6;
    private static final int System_setting_language = 7;
    private static final int System_setting_brightness = 8;
    private static final int System_setting_volume = 9;
    private static final int System_setting_reset = 10;
    private static final int System_setting_power_usage = 11;
    private static final int System_setting_connect = 12;
    private static final int System_setting_connect_how_to_0 = 13;
    private static final int System_setting_connect_how_to_1 = 14;
    private static final int System_setting_filter = 15;
    private static final int System_setting_upgrade = 16;

    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.ivMenu)
    QuarterOvalImageView ivMenu;
    @BindView(R.id.ib_menu)
    ImageButton ibMenu;
    @BindView(R.id.ivPowerOff)
    QuarterOvalImageView ivPowerOff;
    @BindView(R.id.ib_power_off)
    ImageButton ibPowerOff;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.gou)
    ImageView gou;

    @BindView(R.id.IV_background_pause)
    ImageView IVBackgroundPause;
    @BindView(R.id.framelayout_background_pause)
    FrameLayout framelayoutBackgroundPause;
    @BindView(R.id.IV_background_lock)
    TextView IVBackgroundLock;
    @BindView(R.id.IV_background_lock_countdown)
    TextView tvBackgroundLockCountDown;
    @BindView(R.id.framelayout_background_lock)
    FrameLayout framelayoutBackgroundLock;
    @BindView(R.id.tv_content_system)
    TextView tvContentSystem;
    @BindView(R.id.gou_system)
    ImageView gouSystem;
    @BindView(R.id.background_system)
    FrameLayout backgroundSystem;
    @BindView(R.id.tv_content_timer)
    TextView tvContentTimer;
    @BindView(R.id.background_timer)
    FrameLayout backgroundTimer;

    @BindView(R.id.flErrorMessagePanel)
    FrameLayout flErrorMessagePanel;
    @BindView(R.id.tvErrorMessageTitle)
    TextView tvErrorMessageTitle;
    @BindView(R.id.tvErrorMessageContent)
    TextView tvErrorMessageContent;

    @BindView(R.id.pattern_lock_view)
    PatternLockView plvTF90;
    @BindView(R.id.plvASKF)
    PatternLockView plvASKF;
    @BindView(R.id.plvKF60)
    PatternLockView plvKF60;
    @BindView(R.id.plvKF80)
    PatternLockView plvKF80;
    @BindView(R.id.plvKF8060)
    PatternLockView plvKF8060;

    @BindView(R.id.clMain)
    ConstraintLayout clMain;

    @BindView(R.id.llDebugInfo)
    LinearLayout llDebugInfo;
    @BindView(R.id.tvPowerSignal)
    TextView tvPowerSignal;
    @BindView(R.id.tvDataReceivedA)
    TextView tvDataReceivedA;
    @BindView(R.id.tvDataReceivedB)
    TextView tvDataReceivedB;
    @BindView(R.id.tvDataReceivedC)
    TextView tvDataReceivedC;
    @BindView(R.id.tvDataReceivedD)
    TextView tvDataReceivedD;
    @BindView(R.id.tvDataReceivedE)
    TextView tvDataReceivedE;
    @BindView(R.id.tvDataReceivedF)
    TextView tvDataReceivedF;

    @BindView(R.id.tvDebugInfoA)
    TextView tvDebugInfoA;
    @BindView(R.id.tvDebugInfoB)
    TextView tvDebugInfoB;
    @BindView(R.id.tvDebugInfoC)
    TextView tvDebugInfoC;
    @BindView(R.id.tvDebugInfoD)
    TextView tvDebugInfoD;
    @BindView(R.id.tvDebugInfoE)
    TextView tvDebugInfoE;
    @BindView(R.id.tvDebugInfoF)
    TextView tvDebugInfoF;
    @BindView(R.id.tvDebugInfoBluetooth)
    TextView tvDebugInfoBluetooth;
    @BindView(R.id.tvDebugInfoHood)
    TextView tvDebugInfoHood;
    @BindView(R.id.tvDebugInfoLight)
    TextView tvDebugInfoLight;

    private PatternLockView plvCurrent;
    private Fragment currentShowFragment;

    private CookerPanelFragment mCookerPanelFragment;

    private CookerIntroFragment mCookerIntroFragment;
    private SystemSettingFragmentBase mSystemSettingFragment;
    private SystemSettingFragmentTime mSystemSettingFragmentTime;
    private SystemSettingFragmentDate mSystemSettingFragmentDate;
    private SystemSettingFragmentLanguage mSystemSettingFragmentLanguage;
    private SystemSettingFragmentPower mSystemSettingFragmentPower;
    private SystemSettingFragmentReset mSystemSettingFragmentReset;
    private SystemSettingFragmentConnect mSystemSettingFragmentConnect;
    private SystemSettingFragmentConnectHowTo0 mSystemSettingFragmentConnectHowTo0;
    private SystemSettingFragmentConnectHowTo1 mSystemSettingFragmentConnectHowTo1;
    private SystemSettingFragmentBrightness mSystemSettingFragmentBrightness;
    private SystemSettingFragmentSound mSystemSettingFragmentSound;
    private SystemSettingFragmentFilter mSystemSettingFragmentFilter;
    private SystemSettingFragmentUpgrade mSystemSettingFragmentUpgrade;
    private InductionCookerHardwareManager mInductionCookerHardwareManager;
    private GetDataThatSendToMicroControler mGetDataThatSendToMicroControler;
    private HandlePatternLockView mHandlePatternLockView;

    private static final int HANDLER_COOKER_Timer_Complete = 1;
    private static final int HANDLER_COOKER_Systen_Setting_Complete = 4;
    private static final int HANDLER_STAND_BY_TRIGGERED = 5;
    private static final int HANDLER_COOKER_UNLOCK = 6;
    private static final int HANDLER_COOKER_UNLOCK_SEND_ORDER = 7;
    private static final int HANDLER_TOUCH_LOCK_SCREEN = 10;
    private static final int HANDLER_REQ_LANGUAGE_SET = 12;
    private static final int HANDLER_REQ_DATE_SET = 13;
    private static final int HANDLER_ERROR_MESSAGE_DISAPEARED = 14;
    private static final int HANDLER_PANEL_HIGH_TEMP_DETECT = 15;

    private static final int PANEL_HIGH_TEMP_YES = 1;
    private static final int PANEL_HIGH_TEMP_NO = 0;

    private static final int CookerView_Up_Left_StartX = 162;  // 左上方的
    private static final int CookerView_Up_Left_StopX = 307;
    private static final int CookerView_Up_Left_StartY = 98;
    private static final int CookerView_Up_Left_StopY = 269;

    private static final int CookerView_Down_Left_StartX = 162;// 左下方的
    private static final int CookerView_Down_Left_StopX = 307;
    private static final int CookerView_Down_Left_StartY = 306;
    private static final int CookerView_Down_Left_StopY = 450;

    private static final int CookerView_Middle_StartX = 320; // 中间的
    private static final int CookerView_Middle_StopX = 480;
    private static final int CookerView_Middle_StartY = 210;
    private static final int CookerView_Middle_StopY = 360;

    private static final int CookerView_Up_Right_StartX = 505;  // 右上方的
    private static final int CookerView_Up_Right_StopX = 639;
    private static final int CookerView_Up_Right_StartY = 98;
    private static final int CookerView_Up_Right_StopY = 269;

    private static final int CookerView_Up_Right_80_StartX = 370;  // 右上方的 80
    private static final int CookerView_Up_Right_80_StopX = 560;
    private static final int CookerView_Up_Right_80_StartY = 179;
    private static final int CookerView_Up_Right_80_StopY = 382;

    private static final int CookerView_Down_Right_80_StartX = 560;// 右下方的 80
    private static final int CookerView_Down_Right_80_StopX = 649;
    private static final int CookerView_Down_Right_80_StartY = 341;
    private static final int CookerView_Down_Right_80_StopY = 443;

    private static final int CookerView_Down_Right_StartX = 505;// 右下方的
    private static final int CookerView_Down_Right_StopX = 639;
    private static final int CookerView_Down_Right_StartY = 306;
    private static final int CookerView_Down_Right_StopY = 450;

    private static final int Lock_StartX = 564;
    private static final int Lock_StopX = 722;
    private static final int Lock_StartY = 215;
    private static final int Lock_StopY = 466;

    private static final int Pause_StarX = 578;
    private static final int Pause_StopX = 710;
    private static final int Pause_StarY = 239;
    private static final int Pause_StopY = 474;

    private boolean inside_flag = false;
    private boolean Lock_Click_Flag = false;
    private boolean Lock_Send_Order_Flag = false;
    private boolean Pause_Click_Flag = false;

    private boolean CookerView_Up_Left_Clicked_Double_Touch = false; // 左上方 是否被同时点击
    private boolean CookerView_Down_Left_Clicked_Double_Touch = false; // 左下方 是否被同时点击
    private boolean CookerView_Down_Right_Clicked_Double_Touch = false; // 右下方 是否被同时点击
    private boolean CookerView_Up_Right_Clicked_Double_Touch = false; // 右上方 是否被同时点击
    private boolean CookerView_Middle_Clicked_Double_Touch = false; // 中间 是否被同时点击


    private static final int Up_Left_And_Down_Left = 1;
    private static final int Up_Left_And_Down_Right = 2;
    private static final int Up_Left_And_Down_Left_SameTime = 33;
    private static final int Up_Right_And_Down_Right_SameTime = 34;


    private static final int Up_Right_And_Down_Right = 9;// 右上、右下
    private static final int Up_Right_And_Down_Left = 10;// 右上、左下

    private static final int Up_Right_And_Up_Left = 21;// 右上、左上 同时
    private static final int Up_Right_And_Up_Left_Slip = 46;// 右上、左上  滑动
    private static final int Down_Right_And_Down_Left = 22;// 右下、左下  同时
    private static final int Down_Right_And_Down_Left_Slip = 47;// 右下、左下  滑动

    private static final int Up_Left__Middle = 24;// 左上、中间 同时
    private static final int Down_Left_Middle = 25;// 左下、中间 同时
    private static final int Up_Right_Middle = 26;// 右上、中间  同时
    private static final int Down_Right_Middle = 27;// 右下、中间  同时

    private static final int Up_Left__Middle_Slip = 48;// 左上、中间 滑动
    private static final int Down_Left_Middle_Slip = 49;// 左下、中间 滑动
    private static final int Up_Right_Middle_Slip = 50;// 右上、中间  滑动
    private static final int Down_Right_Middle_Slip = 51;// 右下、中间  滑动

    private static final int Up_Left_Down_Right_Middle = 11;// 左上、中间、右下
    private static final int Up_Right_Down_Left_Middle = 12;// 右上、中间、左下
    private static final int Up_Left_Up_Right_Middle = 13;// 左上、中间、右上
    private static final int Down_Left_Down_Right_Middle = 14;// 左下、中间、右下

    private static final int Up_Left_Down_Left_Middle = 15;// 左上、左下、中间
    private static final int Up_Left_Midlle_Down_Left = 44;//  左上、中间、左下
    private static final int Down_Right_Middle_Up_Right = 45;//右上、中间、右下
    private static final int Down_Right_Up_Right_Middle = 3; // 右上、右下、中间

    private static final int Up_Right_Up_Left_Down_Left = 28; // 右上、左上、左下
    private static final int Up_Right_Ddwn_Right_Down_Left = 29; // 右上、右下、左下
    private static final int Up_Left_Down_Left_Down_Right = 30; // 左上、左下、右下
    private static final int Up_Left_Up_Right_Down_Right = 31; // 左上、右上、右下

    private static final int Up_Left_Down_Left_Down_Right_Up_Right = 32;//左上、左下、右下、右上

    private static final int Up_Left_Up_Right_Down_Right_Down_Left = 35;//左上、右上、右下、左下
    private static final int Up_Right_Up_Left_Down_Left_Down_Right = 36;//右上、左上、左下、右下


    private static final int Down_Right_Up_Right_Middle_Down_Left = 16;// 右下、右上、中间、左下
    private static final int Down_Right_Up_Right_Middle_Up_Left = 17; // 右下、右上、中间、左上
    private static final int Down_Left_Up_Left_Middle_Up_Right = 18; // 左下、左上、中间、右上
    private static final int Down_Left_Up_Left_Middle_Down_Right = 19; // 左下、左上、中间、右下

    private static final int Up_Left_Middle_Down_Right_Down_Left = 40; // 左上、中间、右下、左下
    private static final int Up_Right_Middle_Down_Left_Down_Right = 41; // 右上、中间、左下、右下
    private static final int Down_Left_Middle_Up_Right_Up_Left = 42; // 左下、中间、右上、左上
    private static final int Down_Right_Middle_Up_Left_Up_Right = 43; // 右下、中间、左上、右上

    private static final int COOKER_ID_Up_Left = 0;     //up left
    private static final int COOKER_ID_Down_Left = 1;   // down left
    private static final int COOKER_ID_Middle = 2;          // middle
    private static final int COOKER_ID_Up_Right = 3;     // up right
    private static final int COOKER_ID_Down_Right = 4;   // down right

    private static final int ALL_ALL = 20; //全部
    private static final int ALL_ALL_ANY = 37; //全部 有区
    private static final int ALL_ALL_LEFT_NONE = 38; // 全部，左边无区
    private static final int ALL_ALL_NONE_RIGHT = 39; // 全部，右边无区

    private static final int TimerIsUp_5_Second = 100;

    private static final int Lock_All_Button = 4;
    private static final int UnLock_All_Button = 5;
    private static final int Power_Off_ALL = 6;
    private static final int Pause_Recover = 7;
    private static final int UnLock_Click = 8;

    private boolean OneMinuteIsDoneFlag = true;
    private boolean IsLinkMode = false;
    private boolean StatCountDown_5_Second = true;

    private boolean secondFingerDown = false;

    private MessageHandler handler;
    private int powerOffSignalReceived = 0;
    private int powerOnSignalReceived = 0;

    private int appStartTag = TFTCookerConstant.APP_START_NONE;

    // 面板是否处于高温状态
    private boolean panelOverTemperature = false;
    private long panelOverTemperatureStart;
    private long panelOverTemperatureSinceSaved;
    private CookerEvent panelOverTemperatureEvent = null;
    private static final long PANEL_OVER_TEMPERATURE_MIN_DURATION = 5 * 1000;
    private static final String PANEL_OVER_TEMPERATURE = "PANEL_OVER_TEMPERATURE";

    private long longClickStart = 0;
    private boolean showingErrER03 = false;
    private static final long LONG_CLICK_DURATION_ER03 = 10 * 1000;  // 手指长按屏幕10秒钟

    private static final int RED_LIGHT = 0xffFF0000;
    private static final int RED_DARK = 0xffaa0000;
    private ValueAnimator colorAnim;
    private PowerManager.WakeLock wakeLock;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                requestWakeLock();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                disableBT();
                releaseWakeLock();
            }
        }
    };

    private CheckScreenTouchThread checkScreenTouchThread = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);

        handler = new MessageHandler(this);
        plvCurrent = ProductManager.getPatternLockView(this);
        initPatternView();
        initSystemParams();
        setupViews();
        registerEventBus();
        hideBottomUIMenu();
        TFTCookerApplication.getInstance().setInitializeProcessComplete(false);
        initSerial();
        registerBroadcast();
    }


    private void requestWakeLock() {
        if (wakeLock == null) {
            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TFTCookerApplication:GTermWakelock");
                wakeLock.acquire();
                Logger.getInstance().i("requestWakeLock()");
            }
        }
        TFTCookerApplication.getInstance().updateLatestTouchTime();
        TFTCookerApplication.getInstance().resetLastTimeEnteredClockScreen();
        TFTCookerApplication.getInstance().resetLastTimePoweredOff();
        TFTCookerApplication.getInstance().resetLastTimePoweredOn();
    }

    private void releaseWakeLock() {
        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
            Logger.getInstance().i("releaseWakeLock()");
        }
        TFTCookerApplication.getInstance().updateLatestTouchTime();
        TFTCookerApplication.getInstance().resetLastTimeEnteredClockScreen();
        TFTCookerApplication.getInstance().resetLastTimePoweredOff();
        TFTCookerApplication.getInstance().resetLastTimePoweredOn();
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
    }

    private void unregisterBroadcast() {
        unregisterReceiver(receiver);
    }

    private void disableBT() {
        android.bluetooth.BluetoothManager bluetoothManager = (android.bluetooth.BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter.isEnabled()) {
            bluetoothManager.getAdapter().disable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
        // 关闭硬件并释放资源
        mInductionCookerHardwareManager.recyle();
        unregisterEventBus();
    }

    private void initSerial() {
        mInductionCookerHardwareManager = InductionCookerHardwareManager
                .init(this)
                .setSerailPortParameter()
                .setCookerType(ProductManager.getCookerType())
                .setCookerHardwareManagerListener(this)
                .setDebug(true)
                .build();
    }

    private void initPatternView() {
        plvCurrent.setVisibility(View.VISIBLE);
        plvCurrent.setEnabled(true);
        plvCurrent.addPatternLockListener(this);
        plvCurrent.clearPattern();
    }

    @SuppressLint("SetTextI18n")
    private void CheckLockTouchScreen() {
        if (TFTCookerApplication.getInstance().CheckLockTouch() && Lock_Click_Flag) {
            handler.sendEmptyMessageDelayed(HANDLER_COOKER_UNLOCK, 100);
        } else {
            if (inside_flag) {
                IVBackgroundLock.setVisibility(View.INVISIBLE);
                tvBackgroundLockCountDown.setVisibility(View.VISIBLE);
                int seconds = TFTCookerApplication.getInstance().unlockTimeCountDown();
                tvBackgroundLockCountDown.setText("" + (TFTCookerConstant.NO_TOUCH_TIME_3_SECOND_WHOLE / 1000 - seconds));
            } else {
                IVBackgroundLock.setVisibility(View.VISIBLE);
                tvBackgroundLockCountDown.setVisibility(View.INVISIBLE);
            }
            handler.sendEmptyMessageDelayed(HANDLER_TOUCH_LOCK_SCREEN, 500);
        }

    }

    private void doStandBy() {
        if (!OneMinuteIsDoneFlag) {
            showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);
            if (Pause_Click_Flag) {
                // 在暂停状态
                EventBus.getDefault().post(new ClearTouchEvent(Pause_Recover));// 暂停恢复了
                hidePauseBackGround();
                SettingPreferencesUtil.saveHobPauseStatus(this, false);
                Pause_Click_Flag = false;
            }
            Show_Power_Off_Button(false);
            OneMinuteIsDoneFlag = true;
            enablePatternLockView(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (appStartTag == TFTCookerConstant.APP_START_NONE) {
            playAudioOnOff(this, 1);
        }

        TFTCookerApplication.getInstance().initCountTimer();
        Logger.getInstance().i("onResume()");
        requestWakeLock();
        panelOverTemperature = false;
        TFTCookerApplication.getInstance().updateLatestTouchTime();
        TFTCookerApplication.getInstance().resetLastTimeEnteredClockScreen();
        TFTCookerApplication.getInstance().resetLastTimePoweredOff();
        TFTCookerApplication.getInstance().resetLastTimePoweredOn();
        mInductionCookerHardwareManager.requestResumeHardware();
        BaseThread.cancel(checkScreenTouchThread);
        checkScreenTouchThread = new CheckScreenTouchThread();
        checkScreenTouchThread.start();

        hideBottomUIMenu();
        switch (appStartTag) {
            case TFTCookerConstant.APP_START_FIRST_TIME:
                handler.sendEmptyMessageDelayed(HANDLER_REQ_LANGUAGE_SET, 1);
                break;
            case TFTCookerConstant.APP_START:
                handler.sendEmptyMessageDelayed(HANDLER_REQ_DATE_SET, 1);
                break;
        }
        if (appStartTag != TFTCookerConstant.APP_START_NONE) {
            int brightness = Integer.parseInt(SettingPreferencesUtil.getDefaultBrightness(this));
            BrightnessUtil.changeAppBrightness(this, brightness);
            int volume = Integer.parseInt(SettingPreferencesUtil.getDefaultSound(this));
            SoundUtil.setSystemVolume(this, volume);
            DateTimeUtil.changeSystemDate(this, 2019, 1, 1);
            DateTimeUtil.changeSystemTime(this, 0, 0);
            appStartTag = TFTCookerConstant.APP_START_NONE;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TFTCookerApplication.getInstance().cancelCountTimer();
        BaseThread.cancel(checkScreenTouchThread);
        releaseWakeLock();
        mInductionCookerHardwareManager.requestPauseHardware();
        checkPanelOverTemperature(false);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    @SuppressLint("ObsoleteSdkInt")
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
            Log.d("hideBottomUIMenu", "hideBottomUIMenu <19");
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            Log.d("hideBottomUIMenu", "hideBottomUIMenu >19");
        }
    }

    @Override
    public int initLyaout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getContainerID() {
        return R.id.content;
    }

    private void initSystemParams() {
        int language = SettingPreferencesUtil.getDefaultLanguage(this);
        if (language == TFTCookerConstant.LANGUAGE_UNDEFINED) {
            language = TFTCookerConstant.LANGUAGE_ENGLISH;
            appStartTag = TFTCookerConstant.APP_START_FIRST_TIME;
        } else {
            appStartTag = TFTCookerConstant.APP_START;
        }
        onLanguageChanged(language);
    }
    private void setupViews() {
        showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);
        showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL);

        tvContentSystem.setTypeface(TFTCookerApplication.goodHomeLight);
        tvContentTimer.setTypeface(TFTCookerApplication.goodHomeLight);
        tvBackgroundLockCountDown.setTypeface(TFTCookerApplication.goodHomeLight);
    }
    private void playAudioOnOff(final Context context, final long delay) {
        Thread playSound = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Logger.getInstance().e(ex);
                }

                SoundManager.getInstance(context).playAlarm(R.raw.on_off, false);
            }
        });
        playSound.start();
    }
    private void showFragment_Single(int index) {
        showFragment_Single(index, null);
    }
    private void showFragment_Single(int index, Bundle arguments) {
        switch (index) {
            case FRAGMENT_SHOW_COOKER_PANEL:
                if (mCookerPanelFragment == null) {
                    mCookerPanelFragment = ProductManager.createCookerPanelFragment();
                    fragments.add(mCookerPanelFragment);
                }
                currentShowFragment = mCookerPanelFragment;
                TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerApplication.DEFAULT_SETTING_MODE);
                break;
            case FRAGMENT_SHOW_COOKER_PANEL_Intro:
                if (mCookerIntroFragment == null) {
                    mCookerIntroFragment = new CookerIntroFragment();
                    fragments.add(mCookerIntroFragment);
                }
                currentShowFragment = mCookerIntroFragment;
                TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerApplication.DEFAULT_SETTING_MODE);

                if (!TFTCookerApplication.getInstance().isInitializeProcessComplete()) {
                    TFTCookerApplication.getInstance().setInitializeProcessComplete(true);
                    playAudioOnOff(this, 50);
                }
                break;
            case System_setting_panel:
                if (mSystemSettingFragment == null) {
                    mSystemSettingFragment = ProductManager.createSystemSettingFragment();
                    fragments.add(mSystemSettingFragment);
                }
                currentShowFragment = mSystemSettingFragment;
                TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_SETTING);
                break;
            case System_setting_time:  // 设置 时间 ，系统设置
                if (mSystemSettingFragmentTime == null) {
                    mSystemSettingFragmentTime = new SystemSettingFragmentTime();
                    fragments.add(mSystemSettingFragmentTime);
                }
                currentShowFragment = mSystemSettingFragmentTime;
                break;
            case System_setting_date:// 设置 日期 ，系统设置
                if (mSystemSettingFragmentDate == null) {
                    mSystemSettingFragmentDate = new SystemSettingFragmentDate();
                    fragments.add(mSystemSettingFragmentDate);
                }
                currentShowFragment = mSystemSettingFragmentDate;
                break;
            case System_setting_language:// 设置 语言 ，系统设置
                if (mSystemSettingFragmentLanguage == null) {
                    mSystemSettingFragmentLanguage = new SystemSettingFragmentLanguage();
                    fragments.add(mSystemSettingFragmentLanguage);
                }
                currentShowFragment = mSystemSettingFragmentLanguage;
                break;
            case System_setting_power_usage:// 设置 功率 ，系统设置
                if (mSystemSettingFragmentPower == null) {
                    mSystemSettingFragmentPower = new SystemSettingFragmentPower();
                    fragments.add(mSystemSettingFragmentPower);
                }
                currentShowFragment = mSystemSettingFragmentPower;
                break;
            case System_setting_reset:              //      默认设置  ，系统设置
                if (mSystemSettingFragmentReset == null) {
                    mSystemSettingFragmentReset = new SystemSettingFragmentReset();
                    fragments.add(mSystemSettingFragmentReset);
                }
                currentShowFragment = mSystemSettingFragmentReset;
                break;
            case System_setting_connect:   //      连接设置  ，系统设置
                if (mSystemSettingFragmentConnect == null) {
                    mSystemSettingFragmentConnect = new SystemSettingFragmentConnect();
                    fragments.add(mSystemSettingFragmentConnect);
                }
                currentShowFragment = mSystemSettingFragmentConnect;
                break;
            case System_setting_connect_how_to_0:   //      连接设置  ，系统设置，帮助页
                if (mSystemSettingFragmentConnectHowTo0 == null) {
                    mSystemSettingFragmentConnectHowTo0 = new SystemSettingFragmentConnectHowTo0();
                    fragments.add(mSystemSettingFragmentConnectHowTo0);
                }
                currentShowFragment = mSystemSettingFragmentConnectHowTo0;
                break;
            case System_setting_connect_how_to_1:   //      连接设置  ，系统设置，帮助页
                if (mSystemSettingFragmentConnectHowTo1 == null) {
                    mSystemSettingFragmentConnectHowTo1 = new SystemSettingFragmentConnectHowTo1();
                    fragments.add(mSystemSettingFragmentConnectHowTo1);
                }
                currentShowFragment = mSystemSettingFragmentConnectHowTo1;
                break;
            case System_setting_brightness:   //      亮度设置  ，系统设置
                if (mSystemSettingFragmentBrightness == null) {
                    mSystemSettingFragmentBrightness = new SystemSettingFragmentBrightness();
                    fragments.add(mSystemSettingFragmentBrightness);
                }
                currentShowFragment = mSystemSettingFragmentBrightness;
                break;
            case System_setting_volume:
                if (mSystemSettingFragmentSound == null) {
                    mSystemSettingFragmentSound = new SystemSettingFragmentSound();
                    fragments.add(mSystemSettingFragmentSound);
                }
                currentShowFragment = mSystemSettingFragmentSound;
                break;
            case System_setting_filter:  // 风机 过滤方式
                if (mSystemSettingFragmentFilter == null) {
                    mSystemSettingFragmentFilter = new SystemSettingFragmentFilter();
                    fragments.add(mSystemSettingFragmentFilter);
                }
                currentShowFragment = mSystemSettingFragmentFilter;
                break;
            case System_setting_upgrade:
                if (mSystemSettingFragmentUpgrade == null) {
                    mSystemSettingFragmentUpgrade = new SystemSettingFragmentUpgrade();
                    fragments.add(mSystemSettingFragmentUpgrade);
                }
                currentShowFragment = mSystemSettingFragmentUpgrade;
                break;
            default:
                return;
        }

        currentShowFragment.setArguments(arguments);
        showFragment(currentShowFragment);
    }

    @Override
    public void showFragment(Fragment fragment) {

        super.showFragment(fragment);
    }

    @Override
    public void onHobIntroFragmentFinish() {
        if (powerOffSignalReceived != 0) {
            // 关机状态下不让点击
            return;
        }

        showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL);

        Show_Power_Off_Button(true);
        if (Lock_Click_Flag) {
            // 在锁定的状态
            framelayoutBackgroundLock.setVisibility(View.VISIBLE);
            enablePatternLockView(false);
            EventBus.getDefault().post(new ClearTouchEvent(Lock_All_Button));
        } else {
            framelayoutBackgroundLock.setVisibility(View.INVISIBLE);
            enableUI(true);
        }
    }


    private void Show_Power_Off_Button(boolean flag) {
        // PowerOff 按钮隐藏，不给用户使用
        ibPowerOff.setVisibility(View.INVISIBLE);
        ivPowerOff.setVisibility(View.INVISIBLE);

        if (flag) {
            ibMenu.setVisibility(View.VISIBLE);
            ivMenu.setVisibility(View.VISIBLE);
            OneMinuteIsDoneFlag = false;
        } else {
            ibMenu.setVisibility(View.INVISIBLE);
            ivMenu.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WorkModeChangedEvent event) {
        int order = event.getOrder();
        switch (order) {
            case WorkModeChangedEvent.ORDER_CHANGED_TO_HOB:
                Logger.getInstance().i("ORDER_CHANGED_TO_HOB");
                enablePatternLockView(true);
                break;
            case WorkModeChangedEvent.ORDER_CHANGED_TO_HOOD:
                Logger.getInstance().i("ORDER_CHANGED_TO_HOOD");
                if (ProductManager.PRODUCT_TYPE != ProductManager.ASKF) {
                    enablePatternLockView(false);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DebugInfoEvent event) {
        if (event.isInDebugMode()) {
            llDebugInfo.setVisibility(View.VISIBLE);
            tvPowerSignal.setText(event.getPowerSignal());

            tvDataReceivedA.setText(event.getDataReceivedA());
            tvDataReceivedB.setText(event.getDataReceivedB());
            tvDataReceivedC.setText(event.getDataReceivedC());
            tvDataReceivedD.setText(event.getDataReceivedD());
            tvDataReceivedE.setText(event.getDataReceivedE());
            tvDataReceivedF.setText(event.getDataReceivedF());

            tvDebugInfoA.setText(event.getDebugInfoA());
            tvDebugInfoB.setText(event.getDebugInfoB());
            tvDebugInfoC.setText(event.getDebugInfoC());
            tvDebugInfoD.setText(event.getDebugInfoD());
            tvDebugInfoE.setText(event.getDebugInfoE());
            tvDebugInfoF.setText(event.getDebugInfoF());
            tvDebugInfoBluetooth.setText(event.getDebugInfoBluetooth());
            tvDebugInfoHood.setText(event.getDebugInfoHood());
            tvDebugInfoLight.setText(event.getDebugInfoLight());
        } else {
            llDebugInfo.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CookerHighTempOrder event){
        int order = event.getOrder();
        switch (order) {
            case CookerHighTempOrder.ORDER_HIGH_TEMP:
                if (currentShowFragment == mCookerIntroFragment) {
                    onHobIntroFragmentFinish();
                }
                break;
        }
    }

    private ConfirmDialog filterWarningDialog = null;
    private ConfirmDialog demoModeDialog = null;
    private long filterWarningDialogShown = 0L;
    private static final long WARING_INTERVAL = 24 * 60 * 60 * 1000;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShowFilterWarningOrder order) {
        if (filterWarningDialogShown != 0
                && SystemClock.elapsedRealtime() - filterWarningDialogShown < WARING_INTERVAL) {
            return;
        }

        if (filterWarningDialog == null) {
            filterWarningDialog = new ConfirmDialog(this);
            filterWarningDialog.setListener(this);
        }


        switch (order.getOrder()) {
            case ShowFilterWarningOrder.ORDER_CHANGE_CARBON:
                filterWarningDialog.setMessage(R.string.msg_carbon_filter_warn);
                break;
            case ShowFilterWarningOrder.ORDER_CLEAN_ALUMINIUM:
                filterWarningDialog.setMessage(R.string.msg_aluminium_filter_warn);
                break;
        }
        if (!filterWarningDialog.isShowing()) {
            filterWarningDialog.show();
            filterWarningDialogShown = SystemClock.elapsedRealtime();
        }
    }

    private int errorId = -1;
    private String errorTitle = "";
    private Map<Integer, Boolean> errorMessageShown = new HashMap<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShowErrorOrder event) {
        int order = event.getOrder();
        if (order == ShowErrorOrder.ORDER_NO_ERROR) {
            hideErrorMessage(false);
            errorMessageShown.clear();
        } else {
            showErrorMessage(
                    order,
                    event.getErrorTitle(this),
                    event.getErrorContent(this));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShowTimerCompleteOrder event) {
        int order;
        order = event.getOrder();
        switch (order) {
            case ShowTimerCompleteOrder.ORDER_DISP_TIMER_COMPLETE_PANEL:
                Logger.getInstance().i("ORDER_DISP_TIMER_COMPLETE_PANEL");
                ShowTimerCompleteNotificationScreen(
                        ResourceUtils.getString(this, R.string.msg_timer_complete));
                EventBus.getDefault().post(new ClearTouchEvent(Lock_All_Button)); //  发送 命令，锁键
                enablePatternLockView(false);
                break;
            case ShowTimerCompleteOrder.ORDER_DO_UNLOCK:
                Logger.getInstance().i("ORDER_DO_UNLOCK");
                // 恢复点击 菜单 和 开关按钮
                ibMenu.setEnabled(true);
                ivMenu.setEnabled(true);
                ibPowerOff.setEnabled(true);
                ivPowerOff.setEnabled(true);
                enablePatternLockView(true);
                break;
            case ShowTimerCompleteOrder.ORDER_NO_STAND_BY:
                // 点击了 屏幕上的按钮，除了 stop 键
                OneMinuteIsDoneFlag = true;
                break;
            case ShowTimerCompleteOrder.ORDER_RESUME_STAND_BY:
                // 点击了 stop 键
                if (OneMinuteIsDoneFlag) {
                    TFTCookerApplication.getInstance().updateLatestTouchTime();
                }
                OneMinuteIsDoneFlag = false;
                break;
            case ShowTimerCompleteOrder.ORDER_DO_PAUSE:
                Logger.getInstance().i("ORDER_DO_PAUSE");
                // 点击了 pause 键
                showPauseBackGround();
                enablePatternLockView(false);
                SettingPreferencesUtil.saveHobPauseStatus(this, true);
                Pause_Click_Flag = true;
                break;
            case ShowTimerCompleteOrder.ORDER_DO_LOCK:
                Logger.getInstance().i("ORDER_DO_LOCK");
                // 点击了 lock 键
                ibMenu.setEnabled(false);
                ivMenu.setEnabled(false);
                showLockBackGround();
                enablePatternLockView(false);
                Lock_Click_Flag = true;
                break;
            case 8:  // 在联动模式下， 可以5 秒计时。此时，处在 hob模式
                StatCountDown_5_Second = true;
                break;
            case 9:  // 在联动模式下，不可以5 秒计时。此时，不处在hob模式
                StatCountDown_5_Second = false;
                break;
            case 10: //已经 关闭  了 联动模式
                IsLinkMode = false;
                break;
            case ShowTimerCompleteOrder.ORDER_PAUSE_TIMEOUT: // pause 超时，全部炉头已关闭
                Logger.getInstance().i("ORDER_PAUSE_TIMEOUT");
                pauseRecovered();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PowerSwitchEvent event) {
        if (event.isOn()) {
            Logger.getInstance().i("onMessageEvent(PowerSwitchEvent on)");
            TFTCookerApplication.getInstance().resetLastTimePoweredOn();
        } else {
            Logger.getInstance().i("onMessageEvent(PowerSwitchEvent off)");
            TFTCookerApplication.getInstance().resetLastTimePoweredOff();
            this.onClick(ibPowerOff);
            if (!TFTCookerApplication.getInstance().isInitializeProcessTimeout()) {
                SoundManager.getInstance(this).playAlarm(R.raw.on_off, false);
            }
        }
    }

    private void showLockBackGround() {
        framelayoutBackgroundLock.setVisibility(View.VISIBLE);
        IVBackgroundLock.setVisibility(View.VISIBLE);
        tvBackgroundLockCountDown.setVisibility(View.INVISIBLE);
    }

    private boolean CheckTheLockButton(float x, float y) {
        boolean v = false;
        if (Lock_StartX <= x && x <= Lock_StopX && Lock_StartY <= y && y <= Lock_StopY) {
            v = true;
        }
        return v;
    }

    private boolean CheckThePauseButton(float x, float y) {
        boolean v = false;
        if (Pause_StarX <= x && x <= Pause_StopX && Pause_StarY <= y && y <= Pause_StopY) {
            v = true;
        }
        return v;
    }

    private void showPauseBackGround() {
        framelayoutBackgroundPause.setVisibility(View.VISIBLE);
        ibMenu.setEnabled(false);
        ivMenu.setEnabled(false);
    }
    private void hidePauseBackGround() {
        framelayoutBackgroundPause.setVisibility(View.INVISIBLE);
        ibMenu.setEnabled(true);
        ivMenu.setEnabled(true);
    }

    private void ShowTimerCompleteNotificationScreen(String s) {  // 定时的，消息

        ibPowerOff.setEnabled(false);
        ivPowerOff.setEnabled(false);
        ibMenu.setEnabled(false);
        ivMenu.setEnabled(false);
        backgroundTimer.setVisibility(View.VISIBLE);
        tvContentTimer.setText(s);
        gou.setVisibility(View.VISIBLE);
    }

    private void ShowSystemSettingNotificationScreen(String s) {
        ShowSystemSettingNotificationScreen(s, null, 2500);
    }
    private void ShowSystemSettingNotificationScreen(String s, Message msg, int delay) {

        ShowSystemSettingNotificationScreen(s, msg, delay, true);
    }
    private void ShowSystemSettingNotificationScreen(int s, Message msg, int delay) {

        ShowSystemSettingNotificationScreen(s, msg, delay, true);
    }
    private void ShowSystemSettingNotificationScreen(
            String s,
            final Message msg,
            final int delay,
            boolean autoClose) {

        ibPowerOff.setEnabled(false);
        ivPowerOff.setEnabled(false);
        backgroundSystem.setVisibility(View.VISIBLE);
        tvContentSystem.setText(s);
        if (autoClose) {
            gouSystem.setVisibility(View.INVISIBLE);
            if (msg == null) {
                handler.sendEmptyMessageDelayed(HANDLER_COOKER_Systen_Setting_Complete, delay);
            } else {
                handler.sendMessageDelayed(msg, delay);
            }
        } else {
            gouSystem.setVisibility(View.VISIBLE);
            backgroundSystem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msg == null) {
                        handler.sendEmptyMessageDelayed(HANDLER_COOKER_Systen_Setting_Complete, delay);
                    } else {
                        handler.sendMessageDelayed(msg, delay);
                    }
                }
            });
            gouSystem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msg == null) {
                        handler.sendEmptyMessageDelayed(HANDLER_COOKER_Systen_Setting_Complete, delay);
                    } else {
                        handler.sendMessageDelayed(msg, delay);
                    }
                }
            });
        }

    }
    private void ShowSystemSettingNotificationScreen(
            int s,
            Message msg,
            int delay,
            boolean autoClose) {

        ibPowerOff.setEnabled(false);
        ivPowerOff.setEnabled(false);
        backgroundSystem.setVisibility(View.VISIBLE);
        tvContentSystem.setText(s);
        if (msg == null) {
            handler.sendEmptyMessageDelayed(HANDLER_COOKER_Systen_Setting_Complete, delay);
        } else {
            handler.sendMessageDelayed(msg, delay);
        }
    }

    private void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @OnClick({
            R.id.ib_menu,
            R.id.ib_power_off,
            R.id.IV_background_pause,
            R.id.background_timer,
            R.id.gou,
            R.id.ivMenu,
            R.id.ivPowerOff,
            R.id.flErrorMessagePanel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMenu:
            case R.id.ib_menu:   // 显示 系统设置 界面
                showFragment_Single(System_setting_panel);
                ibMenu.setVisibility(View.INVISIBLE);
                ivMenu.setVisibility(View.INVISIBLE);
                enablePatternLockView(false);
                break;
            case R.id.ivPowerOff:
            case R.id.ib_power_off:
                // mCookerIntroFragment = null;
                enablePatternLockView(false);
                showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);

                Show_Power_Off_Button(false);
                flErrorMessagePanel.setVisibility(View.INVISIBLE);
                //    Lock_Click_Flag = false;

                EventBus.getDefault().post(new ClearTouchEvent(Power_Off_ALL));// 关闭所有炉头
                if (Pause_Click_Flag) {
                    // 在暂停状态
                    EventBus.getDefault().post(new ClearTouchEvent(Pause_Recover));// 暂停恢复了
                    framelayoutBackgroundPause.setVisibility(View.INVISIBLE);
                    SettingPreferencesUtil.saveHobPauseStatus(this, false);
                    Pause_Click_Flag = false;
                }

                framelayoutBackgroundLock.setVisibility(View.INVISIBLE);

                break;
            case R.id.IV_background_pause:
                pauseRecovered();
                break;
            case R.id.background_timer:
            case R.id.gou:  // 点击那个勾
                handler.sendEmptyMessageDelayed(HANDLER_COOKER_Timer_Complete, 100);
                // 在 enableUI(true); 里会关闭音效，所以需要先 Enable，如果是童锁状态，才再次禁止操作
                enableUI(true);
                if (Lock_Click_Flag) {
                    enableUI(false);
                }
                break;
            case R.id.flErrorMessagePanel:
                hideErrorMessage(false);
                errorMessageShown.put(Integer.valueOf(errorId), true);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShowNotificationScreenOrder order) {
        switch (order.getOrder()) {
            case ShowNotificationScreenOrder.ORDER_SHOW_SCREEN:
                ShowSystemSettingNotificationScreen(order.getMessage(), null, order.getLastSeconds() * 1000);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendSystemSettingOrder event) {
        int order;
        order = event.getOrder();
        switch (order) {
            case TFTCookerConstant.Show_Setting_back:
                Logger.getInstance().i("Show_Setting_back");
                showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL);
                ibMenu.setVisibility(View.VISIBLE);
                ivMenu.setVisibility(View.VISIBLE);
                enablePatternLockView(true);
                if (flErrorMessagePanel.getVisibility() == View.VISIBLE) {
                    enableUI(false);
                }
                break;
            case TFTCookerConstant.Show_Setting_panel:
                Logger.getInstance().i("Show_Setting_panel");
                showFragment_Single(System_setting_panel);
                enablePatternLockView(false);
                break;
            case TFTCookerConstant.Show_Setting_time:
                Logger.getInstance().i("Show_Setting_time");
                if (event.getParamL() != -1) {
                    Bundle args = new Bundle();
                    args.putInt(TFTCookerConstant.ARGUMENT_APP_START, event.getParamL());
                    showFragment_Single(System_setting_time, args);  // 显示 设置 日期界面
                } else {
                    showFragment_Single(System_setting_time);  // 显示 设置 日期界面
                }
                break;
            case TFTCookerConstant.Show_Setting_date:
                Logger.getInstance().i("Show_Setting_date");
                if (event.getParamL() != -1) {
                    Bundle args = new Bundle();
                    args.putInt(TFTCookerConstant.ARGUMENT_APP_START, event.getParamL());
                    showFragment_Single(System_setting_date, args);  // 显示 设置 日期界面
                } else {
                    showFragment_Single(System_setting_date);  // 显示 设置 日期界面
                }
                break;
            case TFTCookerConstant.Show_Setting_language:
                Logger.getInstance().i("Show_Setting_language");
                if (event.getParamL() != -1) {
                    Bundle args = new Bundle();
                    args.putInt(TFTCookerConstant.ARGUMENT_APP_START, event.getParamL());
                    showFragment_Single(System_setting_language, args);  // 显示 设置 日期界面
                } else {
                    showFragment_Single(System_setting_language);  // 显示 设置 日期界面
                }
                break;
            case TFTCookerConstant.Show_Setting_power_usage:// 显示 设置 功率界面
                Logger.getInstance().i("Show_Setting_power_usage");
                showFragment_Single(System_setting_power_usage);
                break;
            case TFTCookerConstant.Show_Setting_reset:// 显示 默认设置 界面
                Logger.getInstance().i("Show_Setting_reset");
                showFragment_Single(System_setting_reset);
                break;
            case TFTCookerConstant.Show_Setting_connect:// 显示 连接 界面
                Logger.getInstance().i("Show_Setting_connect");
                showFragment_Single(System_setting_connect);
                break;
            case TFTCookerConstant.Show_Setting_connect_how_to_0:
                Logger.getInstance().i("Show_Setting_connect_how_to_0");
                showFragment_Single(System_setting_connect_how_to_0);
                break;
            case TFTCookerConstant.Show_Setting_connect_how_to_1:
                Logger.getInstance().i("Show_Setting_connect_how_to_1");
                showFragment_Single(System_setting_connect_how_to_1);
                break;
            case TFTCookerConstant.Show_Setting_connect_how_to_complete:
                Logger.getInstance().i("Show_Setting_connect_how_to_complete");
                showFragment_Single(System_setting_connect);
                mSystemSettingFragmentConnect.handler.sendEmptyMessage(2);
                break;
            case TFTCookerConstant.Show_Setting_brightness:// 显示 设置亮度 界面
                Logger.getInstance().i("Show_Setting_brightness");
                showFragment_Single(System_setting_brightness);
                break;
            case TFTCookerConstant.Show_Setting_volume:  // 显示设置 音量界面
                Logger.getInstance().i("Show_Setting_volume");
                showFragment_Single(System_setting_volume);
                break;
            case TFTCookerConstant.Show_Setting_filter: // 显示风机过滤的方式
                Logger.getInstance().i("Show_Setting_filter");
                showFragment_Single(System_setting_filter);
                break;
            case TFTCookerConstant.Setting_time_complete:
                Logger.getInstance().i("Setting_time_complete");
                if (event.getParamL() == TFTCookerConstant.APP_START_IDLE) {
                    showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);
                    TFTCookerApplication.getInstance().setInitializeProcessComplete(true);
                    TFTCookerApplication.getInstance().setInitializeProcessTimeout(true);
                } else if (event.getParamL() != TFTCookerConstant.APP_START_NONE) {
                    Message msg = new Message();
                    msg.what = HANDLER_COOKER_Systen_Setting_Complete;
                    if (event.getParamL() == TFTCookerConstant.APP_START_FIRST_TIME
                            && ProductManager.isASKF()) {
                        msg.arg1 = System_setting_filter;
                    } else {
                        msg.arg1 = FRAGMENT_SHOW_COOKER_PANEL_Intro;
                    }
                    msg.arg2 = event.getParamL();
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(this, R.string.msg_time_set),
                            msg,
                            1000);
                } else {
                    ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_time_set));
                }
                break;
            case TFTCookerConstant.Setting_date_complete:
                Logger.getInstance().i("Setting_date_complete");
                if (event.getParamL() == TFTCookerConstant.APP_START_IDLE) {
                    showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);
                    TFTCookerApplication.getInstance().setInitializeProcessComplete(true);
                    TFTCookerApplication.getInstance().setInitializeProcessTimeout(true);
                } else if (event.getParamL() != TFTCookerConstant.APP_START_NONE) {
                    Message msg = new Message();
                    msg.what = HANDLER_COOKER_Systen_Setting_Complete;
                    msg.arg1 = System_setting_time;
                    msg.arg2 = event.getParamL();
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(this, R.string.msg_date_set),
                            msg,
                            1000);
                } else {
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(this, R.string.msg_date_set));
                }
                break;
            case TFTCookerConstant.Setting_language_complete:
                Logger.getInstance().i("Setting_language_complete");
                onLanguageChanged(event.getParamW());
                int mode = event.getParamL();
                if (mode == TFTCookerConstant.APP_START_IDLE) {
                    showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);
                    TFTCookerApplication.getInstance().setInitializeProcessComplete(true);
                    TFTCookerApplication.getInstance().setInitializeProcessTimeout(true);
                } else if (mode == TFTCookerConstant.APP_START_FIRST_TIME) {
                    Message msg = new Message();
                    msg.what = HANDLER_COOKER_Systen_Setting_Complete;
                    msg.arg1 = System_setting_date;
                    msg.arg2 = event.getParamL();
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(this, R.string.msg_language_set),
                            msg,
                            1000);
                } else {
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(this, R.string.msg_language_set));
                }
                break;
            case TFTCookerConstant.Setting_brightness_complete:
                Logger.getInstance().i("Setting_brightness_complete");
                ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_brightness_set));
                break;
            case TFTCookerConstant.Setting_reset_complete:
                Logger.getInstance().i("Setting_reset_complete");
                ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_default_restored));
                break;
            case TFTCookerConstant.Setting_power_usage_complete:
                Logger.getInstance().i("Setting_power_usage_complete");
                ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_stand_by_set));
                break;
            case TFTCookerConstant.Setting_connect_complete:
                Logger.getInstance().i("Setting_connect_complete");
                ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_hood_connected));
                break;
            case TFTCookerConstant.Setting_volume_complete:
                Logger.getInstance().i("Setting_volume_complete");
                ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_volume_set));
                break;
            case TFTCookerConstant.Setting_filter_complete:
                Logger.getInstance().i("Setting_filter_complete");
                if (event.getParamL() == TFTCookerConstant.APP_START_IDLE) {
                    showFragment_Single(FRAGMENT_SHOW_COOKER_PANEL_Intro);
                    TFTCookerApplication.getInstance().setInitializeProcessComplete(true);
                    TFTCookerApplication.getInstance().setInitializeProcessTimeout(true);
                } else if (event.getParamL() == TFTCookerConstant.APP_START_FIRST_TIME
                        && ProductManager.isASKF()) {
                    Message msg = new Message();
                    msg.what = HANDLER_COOKER_Systen_Setting_Complete;
                    msg.arg1 = FRAGMENT_SHOW_COOKER_PANEL_Intro;
                    msg.arg2 = event.getParamL();
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(this, R.string.msg_filter_set),
                            msg,
                            1000);
                } else {
                    ShowSystemSettingNotificationScreen(ResourceUtils.getString(this, R.string.msg_filter_set));
                }
                break;
            case TFTCookerConstant.Setting_demo_mode_toggle_req:
                Logger.getInstance().i("Setting_demo_mode_toggle_req");
                boolean on = event.getParamW() == 1 ? true : false;
                if (demoModeDialog == null) {
                    demoModeDialog = new ConfirmDialog(this);
                    demoModeDialog.setListener(this);
                }
                demoModeDialog.setMessage(on ? R.string.msg_confirm_normal_mode : R.string.msg_confirm_demo_mode);
                demoModeDialog.setParamW(on ? 1 : 0);
                if (!demoModeDialog.isShowing()) {
                    demoModeDialog.show();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BluetoothEvent event) {
        switch (event.getOrder()) {
            case BluetoothEvent.ORDER_FAILED:
                Logger.getInstance().e("BluetoothEvent.ORDER_FAILED");
                break;
            case BluetoothEvent.ORDER_START:
                EventBus.getDefault().post(new ClearTouchEvent(Power_Off_ALL));
                Bundle bundle = new Bundle();
                bundle.putInt(SystemSettingFragmentUpgrade.KEY_TOTAL, event.getwParam());
                bundle.putString(SystemSettingFragmentUpgrade.KEY_VERSION, event.getsParam());
                showFragment_Single(System_setting_upgrade, bundle);
                Logger.getInstance().i("BluetoothEvent.ORDER_START " + event.getwParam() + " of " + event.getsParam());
                break;
            case BluetoothEvent.ORDER_FINISHED:
                Logger.getInstance().i("BluetoothEvent.ORDER_FINISHED");
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        TFTCookerApplication.getInstance().updateLatestTouchTime();
        if (currentShowFragment != mCookerPanelFragment) {
            return super.dispatchTouchEvent(ev);
        }
        float x, y;

        int actionType = ev.getActionMasked();
        if (!Lock_Click_Flag) {
            switch (actionType) {
                case MotionEvent.ACTION_DOWN:  // 第一个点被按下
                    plvCurrent.setSecondFingerDown(false);
                    secondFingerDown = false;
                    x = ev.getX();
                    y = ev.getY();

                    if (CookerView_Up_Left_Clicked(x, y)) {
                        CookerView_Up_Left_Clicked_Double_Touch = true;
                    } else if (CookerView_Down_Right_Clicked(x, y)) {
                        CookerView_Down_Right_Clicked_Double_Touch = true;
                    } else if (CookerView_Down_Left_Clicked(x, y)) {
                        CookerView_Down_Left_Clicked_Double_Touch = true;
                    } else if (CookerView_Up_Right_Clicked(x, y)) {
                        CookerView_Up_Right_Clicked_Double_Touch = true;
                    } else if (CookerView_Middle_Clicked(x, y)) {
                        CookerView_Middle_Clicked_Double_Touch = true;
                    }

                    break;
                case MotionEvent.ACTION_POINTER_DOWN:// 非第一个点被按下
                    plvCurrent.setSecondFingerDown(true);
                    secondFingerDown = true;
                    x = ev.getX();
                    y = ev.getY();

                    if (CookerView_Up_Left_Clicked(x, y)) {
                        CookerView_Up_Left_Clicked_Double_Touch = true;
                    } else if (CookerView_Down_Right_Clicked(x, y)) {
                        CookerView_Down_Right_Clicked_Double_Touch = true;
                    } else if (CookerView_Down_Left_Clicked(x, y)) {
                        CookerView_Down_Left_Clicked_Double_Touch = true;
                    } else if (CookerView_Up_Right_Clicked(x, y)) {
                        CookerView_Up_Right_Clicked_Double_Touch = true;
                    } else if (CookerView_Middle_Clicked(x, y)) {
                        CookerView_Middle_Clicked_Double_Touch = true;
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:  // 非最后一个点被释放
                    plvCurrent.setSecondFingerDown(true);
                    secondFingerDown = true;
                    x = ev.getX();
                    y = ev.getY();
                    if (CookerView_Up_Left_Clicked(x, y)) {
                        CookerView_Up_Left_Clicked_Double_Touch = true;
                    } else if (CookerView_Down_Right_Clicked(x, y)) {
                        CookerView_Down_Right_Clicked_Double_Touch = true;
                    } else if (CookerView_Down_Left_Clicked(x, y)) {
                        CookerView_Down_Left_Clicked_Double_Touch = true;
                    } else if (CookerView_Up_Right_Clicked(x, y)) {
                        CookerView_Up_Right_Clicked_Double_Touch = true;
                    } else if (CookerView_Middle_Clicked(x, y)) {
                        CookerView_Middle_Clicked_Double_Touch = true;
                    }
                    break;
                case MotionEvent.ACTION_UP: // 最后一个点被释放
                    x = ev.getX();
                    y = ev.getY();
                    if (secondFingerDown) {
                        if (CookerView_Up_Left_Clicked_Double_Touch) {
                            if (CookerView_Down_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Right)); // 已经选择了  up_left , down_right  等 2个框

                                IsLinkMode = true;
                            } else if (CookerView_Up_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Up_Left));

                                IsLinkMode = true;
                            } else if (CookerView_Down_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Left_SameTime));
                                IsLinkMode = true;
                            } else if (CookerView_Middle_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Left__Middle));
                                IsLinkMode = true;
                            }

                        } else if (CookerView_Down_Right_Clicked_Double_Touch) {
                            if (CookerView_Up_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Right)); // 已经选择了  up_left , down_right  等 2个框
                                IsLinkMode = true;
                            } else if (CookerView_Down_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_And_Down_Left));
                                IsLinkMode = true;
                            } else if (CookerView_Up_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Right_SameTime));
                                IsLinkMode = true;
                            } else if (CookerView_Middle_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle));
                                IsLinkMode = true;
                            }

                        } else if (CookerView_Down_Left_Clicked_Double_Touch) {
                            if (CookerView_Up_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Left)); // 已经选择了  up_left , down_right  等 2个框
                                IsLinkMode = true;
                            } else if (CookerView_Down_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_And_Down_Left));
                                IsLinkMode = true;
                            } else if (CookerView_Up_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Left_SameTime));
                                IsLinkMode = true;
                            } else if (CookerView_Middle_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle));
                                IsLinkMode = true;
                            }
                        } else if (CookerView_Up_Right_Clicked_Double_Touch) {
                            if (CookerView_Down_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Left)); // 已经选择了  up_left , down_right  等 2个框
                                IsLinkMode = true;
                            } else if (CookerView_Up_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Up_Left));
                                IsLinkMode = true;
                            } else if (CookerView_Down_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Right_SameTime));
                                IsLinkMode = true;
                            } else if (CookerView_Middle_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle));
                                IsLinkMode = true;
                            }
                        } else if (CookerView_Middle_Clicked_Double_Touch) {
                            if (CookerView_Down_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle)); // 已经选择了  up_left , down_right  等 2个框
                                IsLinkMode = true;
                            } else if (CookerView_Up_Left_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Left__Middle));
                                IsLinkMode = true;
                            } else if (CookerView_Down_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle));
                                IsLinkMode = true;
                            } else if (CookerView_Up_Right_Clicked(x, y)) {
                                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));  //Up_Left_And_Down_Letf_SameTime
                                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle));
                                IsLinkMode = true;
                            }
                        }
                    }

                    CookerView_Down_Right_Clicked_Double_Touch = false;
                    CookerView_Up_Left_Clicked_Double_Touch = false;
                    CookerView_Down_Left_Clicked_Double_Touch = false;
                    CookerView_Up_Right_Clicked_Double_Touch = false;
                    CookerView_Middle_Clicked_Double_Touch = false;
                    break;
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            longClickStart = SystemClock.elapsedRealtime();
            showingErrER03 = false;

            x = ev.getX();
            y = ev.getY();

            TFTCookerApplication.getInstance().updateLatestLockTouchTime();

            //----------按钮轨迹--------------
            if (currentShowFragment == mCookerPanelFragment) {
                plvCurrent.ACTION_DOWN(ev);
            }
            //---------------------------------

            //---------------检测lock 按钮---------------------------------
            if (CheckTheLockButton(x, y) && Lock_Click_Flag) {
                inside_flag = true;
                handler.sendEmptyMessageDelayed(HANDLER_TOUCH_LOCK_SCREEN, 100);
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (SystemClock.elapsedRealtime() - longClickStart >= LONG_CLICK_DURATION_ER03) {
                ShowErrorOrder order = new ShowErrorOrder(ShowErrorOrder.ORDER_ER03);
                showErrorMessage(
                        order.getOrder(),
                        order.getErrorTitle(this),
                        order.getErrorContent(this));
                showingErrER03 = true;
            }
            //----------按钮轨迹--------------
            if (currentShowFragment == mCookerPanelFragment) {
                plvCurrent.ACTION_MOVE(ev);
            }
            //---------------------------------
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (showingErrER03) {
                hideErrorMessage(true);
            }
            x = ev.getX();
            y = ev.getY();
//            LogUtil.d("x is = "+x+"; y is = "+y);
            TFTCookerApplication.getInstance().updateLatestLockTouchTime();

            //----------按钮轨迹--------------
            if (currentShowFragment == mCookerPanelFragment) {
                plvCurrent.ACTION_UP(ev);
            }
            //---------------------------------

            //---------------检测lock 按钮---------------------------------
            if (inside_flag) {
                inside_flag = false;
                if (Lock_Send_Order_Flag) {
                    Lock_Send_Order_Flag = false;
                    Lock_Click_Flag = false;
                    handler.sendEmptyMessageDelayed(HANDLER_COOKER_UNLOCK_SEND_ORDER, 300);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean CookerView_Up_Left_Clicked(float x, float y) {  // 判断 左上方的框 是否 被点击

        boolean ReturnValue = false;

        if (x <= CookerView_Up_Left_StopX && x >= CookerView_Up_Left_StartX && y <= CookerView_Up_Left_StopY && y >= CookerView_Up_Left_StartY) {
            ReturnValue = true;
        }

        return ReturnValue;
    }

    private boolean CookerView_Down_Left_Clicked(float x, float y) {  // 判断 左下方的框 是否 被点击

        boolean ReturnValue = false;

        if (x <= CookerView_Down_Left_StopX && x >= CookerView_Down_Left_StartX && y <= CookerView_Down_Left_StopY && y >= CookerView_Down_Left_StartY) {
            ReturnValue = true;
        }

        return ReturnValue;
    }

    private boolean CookerView_Middle_Clicked(float x, float y) {  // 判断 中间圈圈的框 是否 被点击

        boolean ReturnValue = false;

        if (x <= CookerView_Middle_StopX && x >= CookerView_Middle_StartX && y <= CookerView_Middle_StopY && y >= CookerView_Middle_StartY) {
            ReturnValue = true;
        }

        return ReturnValue;
    }


    private boolean CookerView_Up_Right_Clicked(float x, float y) {  // 判断  右上方的框 是否 被点击

        boolean ReturnValue = false;
        if(ProductManager.isKF80()) {  // 是 80 类型
            if (x <= CookerView_Up_Right_80_StopX && x >= CookerView_Up_Right_80_StartX && y <= CookerView_Up_Right_80_StopY && y >= CookerView_Up_Right_80_StartY) {
                ReturnValue = true;
            }
        }else {
            if (x <= CookerView_Up_Right_StopX && x >= CookerView_Up_Right_StartX && y <= CookerView_Up_Right_StopY && y >= CookerView_Up_Right_StartY) {
                ReturnValue = true;
            }
        }


        return ReturnValue;
    }

    private boolean CookerView_Down_Right_Clicked(float x, float y) {  // 判断 右下方的框是否 被点击

        boolean ReturnValue = false;
        if(ProductManager.isKF80()){  // 是 80 类型
            if (x <= CookerView_Down_Right_80_StopX && x >= CookerView_Down_Right_80_StartX && y <= CookerView_Down_Right_80_StopY && y >= CookerView_Down_Right_80_StartY) {
                ReturnValue = true;
            }
        }else {
            if (x <= CookerView_Down_Right_StopX && x >= CookerView_Down_Right_StartX && y <= CookerView_Down_Right_StopY && y >= CookerView_Down_Right_StartY) {
                ReturnValue = true;
            }
        }


        return ReturnValue;
    }


    @Override
    public void onStarted() {
    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        String s = PatternLockUtils.patternToString(plvCurrent, pattern);
        plvCurrent.clearPattern();
        mHandlePatternLockView = ProductManager.getHandlePatternLockView(s);
        IsLinkMode = mHandlePatternLockView.HandleInputFromPatternLockView();
    }


    @Override
    public void onCleared() {
    }

    private void checkPanelOverTemperature(boolean value) {
        if (value) {
            if (!panelOverTemperature) {
                // 新收到高温信号
                panelOverTemperature = true;
                panelOverTemperatureStart = SystemClock.elapsedRealtime();
                panelOverTemperatureSinceSaved = panelOverTemperatureStart;
                panelOverTemperatureEvent = new CookerEvent();
                panelOverTemperatureEvent.setCreatedAt(panelOverTemperatureStart);
                panelOverTemperatureEvent.setDescription(null);
                panelOverTemperatureEvent.setName(PANEL_OVER_TEMPERATURE);
                panelOverTemperatureEvent.setParamLA(0L);
            } else {
                // 持续收到高温信号
                long elapsed = SystemClock.elapsedRealtime() - panelOverTemperatureStart;
                long elapsedSinceSaved = SystemClock.elapsedRealtime() - panelOverTemperatureSinceSaved;
                if (elapsedSinceSaved > PANEL_OVER_TEMPERATURE_MIN_DURATION) {
                    panelOverTemperatureEvent.setParamLA(elapsed);
                    CookerEventDBHelper.saveCookerEvent(this, panelOverTemperatureEvent);
                    panelOverTemperatureSinceSaved = SystemClock.elapsedRealtime();
                    TFTCookerApplication.getInstance().updateLatestTouchTime();
                    Message msg = new Message();
                    msg.what = HANDLER_PANEL_HIGH_TEMP_DETECT;
                    msg.arg1 = PANEL_HIGH_TEMP_YES;
                    handler.sendMessage(msg);
                }
            }
        } else {
            if (panelOverTemperature) {
                // 高温信号解除
                panelOverTemperature = false;
                Message msg = new Message();
                msg.what = HANDLER_PANEL_HIGH_TEMP_DETECT;
                msg.arg1 = PANEL_HIGH_TEMP_NO;
                handler.sendMessage(msg);
                long elapsed = SystemClock.elapsedRealtime() - panelOverTemperatureStart;
                if (elapsed > PANEL_OVER_TEMPERATURE_MIN_DURATION) {
                    panelOverTemperatureEvent.setParamLA(elapsed);
                    CookerEventDBHelper.saveCookerEvent(this, panelOverTemperatureEvent);
                }
            }
        }
    }

    private void enablePatternLockView(boolean enabled) {
        plvCurrent.clearPattern();
        if (enabled) {
            plvCurrent.setVisibility(View.VISIBLE);
            plvCurrent.setEnabled(true);
        } else {
            plvCurrent.setVisibility(View.INVISIBLE);
            plvCurrent.setEnabled(false);
        }
    }

    private void onLanguageChanged(int aLanguage) {
        Resources r = this.getResources();
        final Configuration c = r.getConfiguration();
        DisplayMetrics dm = r.getDisplayMetrics();
        switch (aLanguage) {
            case TFTCookerConstant.LANGUAGE_ROMANIAN:
                c.locale = new Locale("ro");
                break;
            case TFTCookerConstant.LANGUAGE_ENGLISH:
                c.locale = Locale.ENGLISH;
                break;
            case TFTCookerConstant.LANGUAGE_FRENCH:
                c.locale = Locale.FRENCH;
                break;
            case TFTCookerConstant.LANGUAGE_POLISH:
                c.locale = new Locale("pl");
                break;
            case TFTCookerConstant.LANGUAGE_PORTUGUESE:
                c.locale = new Locale("pt");
                break;
            case TFTCookerConstant.LANGUAGE_TURKISH:
                c.locale = new Locale("tr");
                break;
            default:
                c.locale = Locale.getDefault();
                break;
        }
        r.updateConfiguration(c, dm);
        for (Fragment fragment: fragments) {
            fragment.onConfigurationChanged(r.getConfiguration());
        }
    }

    private void enableUI(boolean enabled) {
        if (enabled) {
            EventBus.getDefault().post(new ClearTouchEvent(UnLock_All_Button));
        } else {
            EventBus.getDefault().post(new ClearTouchEvent(Lock_All_Button));
        }

        if (enabled && mCookerPanelFragment.isInHobStatus()) {
            enablePatternLockView(true);
        } else {
            enablePatternLockView(false);
        }
        ibPowerOff.setEnabled(enabled);
        ivPowerOff.setEnabled(enabled);
        ibMenu.setEnabled(enabled);
        ivMenu.setEnabled(enabled);
    }

    private void pauseRecovered() {
        Pause_Click_Flag = false;
        framelayoutBackgroundPause.setVisibility(View.INVISIBLE);
        SettingPreferencesUtil.saveHobPauseStatus(this, false);
        enablePatternLockView(true);
        EventBus.getDefault().post(new ClearTouchEvent(Pause_Recover));// 暂停恢复了
        ibMenu.setEnabled(true);
        ivMenu.setEnabled(true);
        ibPowerOff.setEnabled(true);
        ivPowerOff.setEnabled(true);
    }

    private void showErrorMessage(int id, String title, String content) {
        TFTCookerApplication.getInstance().updateLatestTouchTime();
        if (flErrorMessagePanel.getVisibility() != View.VISIBLE
                || errorId != id
                || !errorTitle.equals(title)) {
            if (!errorShownAndCancelled(id)) {
                errorTitle = title;
                enableUI(false);
                tvErrorMessageTitle.setText(errorTitle);
                tvErrorMessageContent.setText(content);
                flErrorMessagePanel.setVisibility(View.VISIBLE);
                errorId = id;
            }
        }
    }
    private void hideErrorMessage(boolean reqPowerOff) {
        if (flErrorMessagePanel.getVisibility() == View.VISIBLE) {
            flErrorMessagePanel.setVisibility(View.INVISIBLE);

            Message msg = new Message();
            msg.what = HANDLER_ERROR_MESSAGE_DISAPEARED;
            msg.arg1 = reqPowerOff ? 1 : 0;
            handler.sendMessageDelayed(msg, 200);
        }
    }
    private boolean errorShownAndCancelled(int errorId) {
        Integer error = Integer.valueOf(errorId);
        if (!errorMessageShown.containsKey(error)) {
            errorMessageShown.put(error, false);
        }
        return errorMessageShown.get(error);
    }

    private void setAnim() {
        if (colorAnim != null) {
            return;
        }

        colorAnim = ObjectAnimator.ofInt(clMain,"backgroundColor", RED_DARK, RED_LIGHT);
        colorAnim.setDuration(700);
        colorAnim.setEvaluator(new ArgbEvaluator()); colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);

        colorAnim.start();
        SoundManager.getInstance(this).playAlarm(R.raw.ntc_alarm, true);
    }

    private void cancelAnim() {
        if (colorAnim != null) {
            colorAnim.cancel();
            clMain.clearAnimation();
            clMain.setBackgroundResource(R.mipmap.wallpaper);
            colorAnim = null;
            SoundManager.getInstance(this).stopAlarm();
        }
    }

    @Override
    public void onCookerHardwareResponse(CookerHardwareResponse cookerHardwareResponse) {

    }

    @Override
    public void onCookerHardwareResponseRawData(byte[] bytes, int i) {
        if (mInductionCookerHardwareManager == null) {
            return;
        }

        AnalysisSerialData serialData = new AnalysisSerialData();
        serialData.setData(bytes);
        if (!serialData.isLegalData()) {
            return;
        }

        if (TFTCookerApplication.getInstance().isInitializeProcessComplete()) {
            boolean powerStatus = serialData.isPoweredOn();
            TFTCookerApplication.getInstance().setPoweredOn(powerStatus);
            powerOffSignalReceived = powerStatus ? 0 : powerOffSignalReceived + 1;
            powerOnSignalReceived = !powerStatus ? 0 : powerOnSignalReceived + 1;
            if (powerOffSignalReceived > 9) powerOffSignalReceived = 9;
            if (powerOnSignalReceived > 9) powerOnSignalReceived = 9;

            if (powerOffSignalReceived == 1) {
                checkPanelOverTemperature(false);
                EventBus.getDefault().post(new PowerSwitchEvent(false));
            }
            if (powerOnSignalReceived == 1) {
                checkPanelOverTemperature(false);
                EventBus.getDefault().post(new PowerSwitchEvent(true));
            }

            checkPanelOverTemperature(serialData.isPanelOverTemperature());

            EventBus.getDefault().post(serialData);
        }

        if (mGetDataThatSendToMicroControler == null) {
            mGetDataThatSendToMicroControler = new GetDataThatSendToMicroControler(this);
        }

        AllCookerDataEx dataToSend = mGetDataThatSendToMicroControler.getCookerSendCommandData(serialData);
        if (mCookerPanelFragment != null) {
            if (mCookerPanelFragment.leftCookersUnited()) {
                if (dataToSend.getaMode() == TFTCookerConstant.COOKER_MODE_NORMAL) {
                    dataToSend.setaMode(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
                }
                dataToSend.setbMode(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
                if (serialData.isaError() || serialData.isbError()) {
                    dataToSend.setaFireValue(0);
                    dataToSend.setbFireValue(0);
                }
            }
            if (mCookerPanelFragment.rightCookersUnited()) {
                if (ProductManager.PRODUCT_TYPE == ProductManager.KF90) {
                    dataToSend.seteMode(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
                    dataToSend.setfMode(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
                    if (serialData.iseError() || serialData.isfError()) {
                        dataToSend.seteFireValue(0);
                        dataToSend.setfFireValue(0);
                    }
                } else {
                    dataToSend.setcMode(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
                    dataToSend.setdMode(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
                    if (serialData.iscError() || serialData.isdError()) {
                        dataToSend.setcFireValue(0);
                        dataToSend.setdFireValue(0);
                    }
                }
            }
        }

        DebugInfoEvent debugInfo = new DebugInfoEvent();
        debugInfo.setInDebugMode(TFTCookerApplication.getInstance().isInDebugMode());
        debugInfo.setDataReceived(serialData);
        AllCookerDataEx demoData = dataToSend.clone();

        if (TFTCookerApplication.getInstance().isInDemonstrationMode()) {

            demoData.setaFireValue(0);
            demoData.setbFireValue(0);
            demoData.setcFireValue(0);
            demoData.setdFireValue(0);
            demoData.seteFireValue(0);
            demoData.setfFireValue(0);
            mInductionCookerHardwareManager.requestSendCookerDataForDemoMode(
                    dataToSend,
                    demoData,
                    true);

        } else {
            if (dataToSend.getaMode() != TFTCookerConstant.COOKER_MODE_FREE_ZONE
                    && dataToSend.getbMode() != TFTCookerConstant.COOKER_MODE_FREE_ZONE) {

                int aFireValue = dataToSend.getaFireValue();
                int bFireValue = dataToSend.getbFireValue();

                if (aFireValue == 10) {
                    if (bFireValue > 0 && bFireValue < 7) {
                        demoData.setaFireValue(9);
                    }
                } else if (aFireValue == 9) {
                    if (bFireValue > 0 && bFireValue < 8) {
                        demoData.setaFireValue(8);
                    }
                }

                if (bFireValue == 10) {
                    if (aFireValue > 0 && aFireValue < 8) {
                        demoData.setbFireValue(9);
                    }
                }
            }
            if (ProductManager.isKF90()) {
                if (dataToSend.geteMode() != TFTCookerConstant.COOKER_MODE_FREE_ZONE
                        && dataToSend.getfMode() != TFTCookerConstant.COOKER_MODE_FREE_ZONE) {

                    int eFireValue = dataToSend.geteFireValue();
                    int fFireValue = dataToSend.getfFireValue();

                    if (fFireValue == 10) {
                        if (eFireValue > 0 && eFireValue < 7) {
                            demoData.setfFireValue(9);
                        }
                    } else if (fFireValue == 9) {
                        if (eFireValue > 0 && eFireValue < 8) {
                            demoData.setfFireValue(8);
                        }
                    }

                    if (eFireValue == 10) {
                        if (fFireValue > 0 && fFireValue < 8) {
                            demoData.seteFireValue(9);
                        }
                    }
                }
            } else if (ProductManager.isKF60() || ProductManager.isASKF()) {
                if (dataToSend.getcMode() != TFTCookerConstant.COOKER_MODE_FREE_ZONE
                        && dataToSend.getdMode() != TFTCookerConstant.COOKER_MODE_FREE_ZONE) {

                    int cFireValue = dataToSend.getcFireValue();
                    int dFireValue = dataToSend.getdFireValue();

                    if (dFireValue == 10) {
                        if (cFireValue > 0 && cFireValue < 7) {
                            demoData.setdFireValue(9);
                        }
                    } else if (dFireValue == 9) {
                        if (cFireValue > 0 && cFireValue < 8) {
                            demoData.setdFireValue(8);
                        }
                    }

                    if (cFireValue == 10) {
                        if (dFireValue > 0 && dFireValue < 8) {
                            demoData.setcFireValue(9);
                        }
                    }
                }
            }

            mInductionCookerHardwareManager.requestSendCookerDataForDemoMode(
                    dataToSend,
                    demoData,
                    true);
        }

        debugInfo.setAllCookerDataEx(demoData);
        EventBus.getDefault().post(debugInfo);
        Logger.getInstance().d("onCookerHardwareResponseRawData: " + debugInfo.toString());
    }

    @Override
    public void onConfirm(ConfirmDialog dialog, int action) {
        if (dialog == filterWarningDialog) {
            switch (action) {
                case ConfirmDialog.ACTION_OK:
                    int filter = SettingPreferencesUtil.getFilter(this);
                    if (filter == CataSettingConstant.FILTER_CARBON_150) {
                        SettingPreferencesUtil.saveFilterCarbonWorkingTotal(this, 0);
                        GetDataThatSendToMicroControler.filterCarbonTotalTime = 0;
                    } else if (filter == CataSettingConstant.FILTER_ALUMINIUM_50) {
                        SettingPreferencesUtil.saveFilterAluminiumWorkingTotal(this, 0);
                        GetDataThatSendToMicroControler.filterAluminiumTotalTime = 0;
                    }

                    break;
                case ConfirmDialog.ACTION_CANCEL:
                    break;
            }
            filterWarningDialog.dismiss();
            filterWarningDialogShown = 0;
            GetDataThatSendToMicroControler.showingConfirmDialog = false;
            hideBottomUIMenu();
        } else if (dialog == demoModeDialog) {
            boolean demoModeEnabled = demoModeDialog.getParamW() == 1;
            switch (action) {
                case ConfirmDialog.ACTION_OK:
                    demoModeEnabled = !demoModeEnabled;

                    SettingPreferencesUtil.saveDemonstrationMode(this, demoModeEnabled);
                    TFTCookerApplication.getInstance().setInDemonstrationMode(demoModeEnabled);
                    ShowSystemSettingNotificationScreen(
                            ResourceUtils.getString(
                                    this,
                                    demoModeEnabled ? R.string.msg_demo_mode_activated : R.string.msg_normal_mode_activated),
                            null,
                            0,
                            false);
                    break;
                case ConfirmDialog.ACTION_CANCEL:
                    break;
            }
            demoModeDialog.dismiss();
        }
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<MainActivity> master;

        private MessageHandler(MainActivity master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
    private void handleMessage(Message msg) {
        if (isPaused) {
            return;
        }

        Bundle arguments;
        switch (msg.what) {
            case HANDLER_COOKER_UNLOCK:
                Logger.getInstance().i("HANDLER_COOKER_UNLOCK");
                if (inside_flag) {
                    framelayoutBackgroundLock.setVisibility(View.INVISIBLE);
                    Lock_Send_Order_Flag = true;
                }
                break;

            case HANDLER_COOKER_UNLOCK_SEND_ORDER:
                Logger.getInstance().i("HANDLER_COOKER_UNLOCK_SEND_ORDER");
                EventBus.getDefault().post(new ClearTouchEvent(UnLock_Click));// 解锁了
                Lock_Click_Flag = false;
                break;

            case HANDLER_COOKER_Timer_Complete:   // 完成显示透明屏幕
                Logger.getInstance().i("HANDLER_COOKER_Timer_Complete");
                backgroundTimer.setVisibility(View.INVISIBLE);
                break;

            case HANDLER_COOKER_Systen_Setting_Complete:  //  // 透明屏幕消失
                Logger.getInstance().i("HANDLER_COOKER_Systen_Setting_Complete");
                if (currentShowFragment == mSystemSettingFragmentConnect
                        || currentShowFragment == mSystemSettingFragmentBrightness
                        || currentShowFragment == mSystemSettingFragmentDate
                        || currentShowFragment == mSystemSettingFragmentLanguage
                        || currentShowFragment == mSystemSettingFragmentPower
                        || currentShowFragment == mSystemSettingFragmentReset
                        || currentShowFragment == mSystemSettingFragmentSound
                        || currentShowFragment == mSystemSettingFragmentTime
                        || currentShowFragment == mSystemSettingFragmentFilter) {
                    if (msg.arg1 > 0) {
                        Bundle args = new Bundle();
                        args.putInt(TFTCookerConstant.ARGUMENT_APP_START, msg.arg2);
                        showFragment_Single(msg.arg1, args);
                        if (msg.arg1 == FRAGMENT_SHOW_COOKER_PANEL) {
                            ibMenu.setVisibility(View.VISIBLE);
                            ivMenu.setVisibility(View.VISIBLE);
                            enablePatternLockView(true);
                            if (flErrorMessagePanel.getVisibility() == View.VISIBLE) {
                                enableUI(false);
                            }
                        }
                    } else {
                        showFragment_Single(System_setting_panel);
                    }
                }
                backgroundSystem.setVisibility(View.INVISIBLE);
                ibPowerOff.setEnabled(true);
                ivPowerOff.setEnabled(true);
                break;
            case HANDLER_STAND_BY_TRIGGERED:
                Logger.getInstance().i("HANDLER_STAND_BY_TRIGGERED");
                doStandBy();
                break;
            case HANDLER_TOUCH_LOCK_SCREEN:
                CheckLockTouchScreen();
                break;
            case HANDLER_REQ_LANGUAGE_SET:
                Logger.getInstance().i("HANDLER_REQ_LANGUAGE_SET");
                TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_F1);
                arguments = new Bundle();
                arguments.putInt(TFTCookerConstant.ARGUMENT_APP_START, TFTCookerConstant.APP_START_FIRST_TIME);
                showFragment_Single(System_setting_language, arguments);
                ibMenu.setVisibility(View.INVISIBLE);
                ivMenu.setVisibility(View.INVISIBLE);
                enablePatternLockView(false);
                break;
            case HANDLER_REQ_DATE_SET:
                Logger.getInstance().i("HANDLER_REQ_DATE_SET");
                TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_F2);
                arguments = new Bundle();
                arguments.putInt(TFTCookerConstant.ARGUMENT_APP_START, TFTCookerConstant.APP_START);
                showFragment_Single(System_setting_date, arguments);
                ibMenu.setVisibility(View.INVISIBLE);
                ivMenu.setVisibility(View.INVISIBLE);
                enablePatternLockView(false);
                break;
            case HANDLER_ERROR_MESSAGE_DISAPEARED:
                Logger.getInstance().i("HANDLER_ERROR_MESSAGE_DISAPEARED");
                if(  !Lock_Click_Flag ){
                    enableUI(true);
                }

                if (msg.arg1 == 1) {
                    PowerOffAllOrder order = new PowerOffAllOrder(PowerOffAllOrder.ORDER_POWER_OFF);
                    EventBus.getDefault().post(order);
                }
                break;
            case HANDLER_PANEL_HIGH_TEMP_DETECT:
                if (msg.arg1 == PANEL_HIGH_TEMP_YES) {
                    setAnim();
                    PowerOffAllOrder order = new PowerOffAllOrder(PowerOffAllOrder.ORDER_POWER_OFF);
                    EventBus.getDefault().post(order);
                } else if  (msg.arg1 == PANEL_HIGH_TEMP_NO) {
                    cancelAnim();
                }
                break;
        }
    }

    public PatternLockView getPlvTF90() {
        return plvTF90;
    }

    public PatternLockView getPlvASKF() {
        return plvASKF;
    }

    public PatternLockView getPlvKF60() {
        return plvKF60;
    }
    public PatternLockView getPlvKF80() {
        return plvKF80;
    }
    public PatternLockView getPlvKF8060() {
        return plvKF8060;
    }

    public InductionCookerHardwareManager getmInductionCookerHardwareManager() {
        return mInductionCookerHardwareManager;
    }

    private class CheckScreenTouchThread extends BaseThread {

        @Override
        protected boolean started() {
            return true;
        }

        @Override
        protected boolean performTaskInLoop() {
            if (!TFTCookerApplication.getInstance().isInitializeProcessComplete()) {
                return true;
            }

            if (TFTCookerApplication.getInstance().checkNoTouch()) {
                // Stand by 时间到
                handler.sendEmptyMessage(HANDLER_STAND_BY_TRIGGERED);
            }
            if (TFTCookerApplication.getInstance().chechNoTochTheUI()
                    && IsLinkMode
                    && StatCountDown_5_Second) {
                //  发送 命令，解除 所有的 联动 模式
                EventBus.getDefault().post(new ClearTouchEvent(TimerIsUp_5_Second));
            }

            return true;
        }

        @Override
        protected void finished() {
        }
    }


}
