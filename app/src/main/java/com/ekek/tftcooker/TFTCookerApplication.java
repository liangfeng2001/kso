package com.ekek.tftcooker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.PowerManager;
import android.os.SystemClock;

import com.ekek.tftcooker.common.BluetoothHelper;
import com.ekek.tftcooker.common.ECountDownTimer;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.DatabaseHelper;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.entity.DaoMaster;
import com.ekek.tftcooker.entity.DaoSession;
import com.ekek.tftcooker.event.TimeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.database.Database;

import java.util.Stack;

/**
 * Created by Samhung on 2018/4/19.
 */

public class TFTCookerApplication extends Application {
    public static final int DEFAULT_SETTING_MODE = -1;
    private static final String DATABASE_PASSWORD = "super-secret";
    private static final String WAKE_LOCK_TAG = "TFTCookerWakelock";
    private static TFTCookerApplication singleton;
    private PowerManager.WakeLock wakeLock;
    private long latestTouchTime = SystemClock.elapsedRealtime();
    private long latestLockTime = SystemClock.elapsedRealtime();
    private long lastTimeEnteredClockScreen = SystemClock.elapsedRealtime();
    private long lastTimePoweredOn = SystemClock.elapsedRealtime();
    private long lastTimePoweredOff = SystemClock.elapsedRealtime();
    private ECountDownTimer countDownTimer;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoSession daoSession;
    public static Typeface goodHomeLight;
    private boolean hoodConnected = false;
    private boolean poweredOn = true;
    private int cookerSettingMode = DEFAULT_SETTING_MODE;
    // 默认是 True，在 MainActivity onCreate 里，才会设置回 false，
    // 在 MainActivity onCreate 之前已经 show 过一次 FRAGMENT_SHOW_COOKER_PANEL_Intro
    // 第二次显示 Intro 页面时，可以认为是开机初始化完成，为了判断是否是第二次显示，这里需要设置为 True
    // 这样，第一次显示时 是 True，第二次显示时就是 False，第三次及以后都会是 True，就可以区分出第二次了
    private boolean initializeProcessComplete = true;
    private boolean initializeProcessTimeout = false;

    private MyUncaughtExceptionHandler myUncaughtExceptionHandler;
    private boolean inDemonstrationMode;
    private boolean inDebugMode;
    private boolean hoodAuto;

    private BluetoothHelper bluetoothHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static TFTCookerApplication getInstance() {
        return singleton;
    }

    private void init() {
        singleton = this;
        Logger.initializeLoggingSystem(getFilesDir().getParentFile().getAbsolutePath());
        myUncaughtExceptionHandler = new MyUncaughtExceptionHandler(
                this.getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(myUncaughtExceptionHandler);
        goodHomeLight = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        initDatabase();
        bluetoothHelper = new BluetoothHelper(getApplicationContext());
    }

    private void initDatabase() {
        helper = new DaoMaster.DevOpenHelper(this, TFTCookerConstant.DATABASE_ENCRYPTED ? TFTCookerConstant.DATABASE_NAME_ENCRYPTED : TFTCookerConstant.DATABASE_NAME);
        Database db = TFTCookerConstant.DATABASE_ENCRYPTED ? helper.getEncryptedWritableDb(DATABASE_PASSWORD) : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        //    SettingDbHelper.saveTemperatureSensorStatus(-1);
        DatabaseHelper.ResetCookersData();
        //  SecurityDBUtil.initSecurityDatabase(getApplicationContext());
        SettingPreferencesUtil.saveRequireHoodConnection(getApplicationContext(), false);
        SettingPreferencesUtil.saveEnteredClockScreen(getApplicationContext(), false);
        inDemonstrationMode = SettingPreferencesUtil.getDemonstrationMode(getApplicationContext());
        inDebugMode = SettingPreferencesUtil.getDebugMode(getApplicationContext());

        int status = SettingPreferencesUtil.getHoodConnectStatus(getApplicationContext());
        hoodAuto =  (status == CataSettingConstant.HOOD_CONNECTED);
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public void initCountTimer() {
        cancelCountTimer();
        countDownTimer = new ECountDownTimer(TFTCookerConstant.MILLI_SECOND_IN_FUTURE, TFTCookerConstant.COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (EventBus.getDefault().hasSubscriberForEvent(TimeEvent.class)) {
                    TimeEvent event = new TimeEvent();
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();

    }

    public void cancelCountTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    private void requestWakeLock() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, WAKE_LOCK_TAG);
        wakeLock.acquire();
    }

    private void releaseWakeLock() {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    public synchronized void updateLatestTouchTime() {
        latestTouchTime = SystemClock.elapsedRealtime();
    }

    public synchronized void resetLastTimeEnteredClockScreen() {
        lastTimeEnteredClockScreen = SystemClock.elapsedRealtime();
    }

    public long getLastTimePoweredOn() {
        return lastTimePoweredOn;
    }

    public synchronized void resetLastTimePoweredOn() {
        this.lastTimePoweredOn = SystemClock.elapsedRealtime();
    }

    public long getLastTimePoweredOff() {
        return lastTimePoweredOff;
    }

    public synchronized void resetLastTimePoweredOff() {
        this.lastTimePoweredOff = SystemClock.elapsedRealtime();
    }

    public void updateLatestLockTouchTime() {
        latestLockTime = SystemClock.elapsedRealtime();
    }

    public boolean checkNoTouch() {//no touch : true , have touch : false
        return checkNoTouch(1000 * SettingPreferencesUtil.getActivationTime(getApplicationContext()));
    }
    public boolean checkNoTouch(long duration) {
        return SystemClock.elapsedRealtime() - latestTouchTime >= duration;
    }

    public boolean chechNoTochTheUI() {
        return SystemClock.elapsedRealtime() - latestTouchTime >= TFTCookerConstant.NO_TOUCH_TIME_5_SECOND;
    }

    public boolean CheckLockTouch() {
        return SystemClock.elapsedRealtime() - latestLockTime >= TFTCookerConstant.NO_TOUCH_TIME_3_SECOND;
    }

    public int unlockTimeCountDown() {
        long v = SystemClock.elapsedRealtime() - latestLockTime;
        return (int) (v / 1000);
    }

    public boolean isHoodConnected() {
        return hoodConnected;
    }

    public void setHoodConnected(boolean hoodConnected) {
        this.hoodConnected = hoodConnected;
    }

    public boolean isPoweredOn() {
        return poweredOn;
    }

    public void setPoweredOn(boolean poweredOn) {
        this.poweredOn = poweredOn;
    }

    public int getCookerSettingMode() {
        return cookerSettingMode;
    }

    public void setCookerSettingMode(int cookerSettingMode) {
        this.cookerSettingMode = cookerSettingMode;
    }
    public boolean isInitializeProcessComplete() {
        return initializeProcessComplete;
    }
    public void setInitializeProcessComplete(boolean initializeProcessComplete) {
        this.initializeProcessComplete = initializeProcessComplete;
    }
    public boolean isInitializeProcessTimeout() {
        return initializeProcessTimeout;
    }
    public void setInitializeProcessTimeout(boolean initializeProcessTimeout) {
        this.initializeProcessTimeout = initializeProcessTimeout;
    }

    public boolean isInDemonstrationMode() {
        return inDemonstrationMode;
    }

    public void setInDemonstrationMode(boolean inDemonstrationMode) {
        this.inDemonstrationMode = inDemonstrationMode;
    }

    public boolean isInDebugMode() {
        return inDebugMode;
    }

    public void setInDebugMode(boolean inDebugMode) {
        this.inDebugMode = inDebugMode;
    }

    public boolean isHoodAuto() {
        return hoodAuto;
    }

    public void setHoodAuto(boolean hoodAuto) {
        this.hoodAuto = hoodAuto;
    }

    public long getLastTimeEnteredClockScreen() {
        return lastTimeEnteredClockScreen;
    }

    public BluetoothHelper getBluetoothHelper() {
        return bluetoothHelper;
    }

    public void setLastTimeEnteredClockScreen(long lastTimeEnteredClockScreen) {
        this.lastTimeEnteredClockScreen = lastTimeEnteredClockScreen;
    }

    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        private Thread.UncaughtExceptionHandler defaultHandler;
        private Context context;

        public MyUncaughtExceptionHandler(Context context) {
            this.context = context;
            this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        }
        @Override
        public void uncaughtException(Thread thread, Throwable e) {
            Logger.getInstance().e(e);
            if (defaultHandler != null) {
                defaultHandler.uncaughtException(thread, e);
            }
        }
    }
}
