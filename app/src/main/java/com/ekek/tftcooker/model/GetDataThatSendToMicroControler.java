package com.ekek.tftcooker.model;

import android.content.Context;
import android.os.SystemClock;

import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.ProductManager;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.DatabaseHelper;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.entity.TFTDataModel;
import com.ekek.tftcooker.event.ShowFilterWarningOrder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class GetDataThatSendToMicroControler {

    private static final long SAVE_INTERVAL = 5 * 1000;

    private Context context;
    private static boolean lastEnteredClockScreen = false;
    private static boolean clockScreenTimeout = false;
    private static boolean hoodWorking = false;
    private static long hoodWorkingStart;
    private static long hoodWorkingSinceSaved;
    public static long filterCarbonTotalTime;
    public static long filterAluminiumTotalTime;
    public static boolean showingConfirmDialog;

    public GetDataThatSendToMicroControler(Context context) {
        this.context = context;
    }

    //----------串口-头字节 和尾字节-----------------------------------


    public AllCookerDataEx getCookerSendCommandData(AnalysisSerialData serialData) {
        List<TFTDataModel> datas = DatabaseHelper.GetCookersData();
        Boolean paused = SettingPreferencesUtil.getHobPauseStatus(context);
        Boolean requiringHoodConnection = SettingPreferencesUtil.getRequireHoodConnection(context);
        Boolean enteredClockScreen = SettingPreferencesUtil.getEnteredClockScreen(context);
        if (enteredClockScreen && !lastEnteredClockScreen) {
            TFTCookerApplication.getInstance().resetLastTimeEnteredClockScreen();
        }
        lastEnteredClockScreen = enteredClockScreen;

        AllCookerDataEx result = new AllCookerDataEx();
        if (datas.size() != 0) {
            int settingMode = TFTCookerApplication.getInstance().getCookerSettingMode();
            if (requiringHoodConnection) {
                result.setaMode(TFTCookerConstant.COOKER_MODE_1E);
            } else if (enteredClockScreen) {
                long duration = SystemClock.elapsedRealtime() - TFTCookerApplication.getInstance().getLastTimeEnteredClockScreen();
                if (duration >= TFTCookerConstant.CLOCK_SCREEN_TIMEOUT) {
                    result.setaMode(TFTCookerConstant.COOKER_MODE_BLACK_SCREEN);
                    clockScreenTimeout = true;
                }  else {
                    result.setaMode(TFTCookerConstant.COOKER_MODE_CLOCK_SCREEN);
                }
            } else if (settingMode != TFTCookerApplication.DEFAULT_SETTING_MODE) {
                result.setaMode(settingMode);
            } else {

                // enteredClockScreen == false，但 isPoweredOn 也是 false，
                // 则意味着是用户手动按的物理关机键
                if (!TFTCookerApplication.getInstance().isPoweredOn()) {
                    long duration = SystemClock.elapsedRealtime() - TFTCookerApplication.getInstance().getLastTimePoweredOff();
                    if (duration >= TFTCookerConstant.SCREEN_SLEEP_DELAY) {
                        result.setaMode(TFTCookerConstant.COOKER_MODE_BLACK_SCREEN);
                        clockScreenTimeout = false;
                    } else if (clockScreenTimeout) {
                        result.setaMode(TFTCookerConstant.COOKER_MODE_BLACK_SCREEN);
                    } else {
                        result.setaMode(TFTCookerConstant.COOKER_MODE_CLOCK_SCREEN);
                    }
                } else {
                    result.setaMode(datas.get(0).getCookerModel_a());
                }
            }
            if (!enteredClockScreen) {
                TFTCookerApplication.getInstance().setInitializeProcessTimeout(false);
            }

            result.setaFireValue(paused || serialData.isaError() ? 0 : datas.get(0).getCookerFireGear_a());
            result.setaTempValue(datas.get(0).getCookerTemperature_a());

            result.setbMode(datas.get(0).getCookerModel_b());
            result.setbFireValue(paused || serialData.isbError() ? 0 : datas.get(0).getCookerFireGear_b());
            result.setbTempValue(datas.get(0).getCookerTemperature_b());

            result.setcMode(datas.get(0).getCookerModel_c());
            result.setcFireValue(paused || serialData.iscError() ? 0 : datas.get(0).getCookerFireGear_c());
            result.setcTempValue(datas.get(0).getCookerTemperature_c());

            result.setdMode(datas.get(0).getCookerModel_d());
            result.setdFireValue(paused || serialData.isdError() ? 0 : datas.get(0).getCookerFireGear_d());
            result.setdTempValue(datas.get(0).getCookerTemperature_d());

            result.seteMode(datas.get(0).getCookerModel_e());
            result.seteFireValue(paused || serialData.iseError() ? 0 : datas.get(0).getCookerFireGear_e());
            result.seteTempValue(datas.get(0).getCookerTemperature_e());

            result.setfMode(datas.get(0).getCookerModel_f());
            result.setfFireValue(paused || serialData.isfError() ? 0 : datas.get(0).getCookerFireGear_f());
            result.setfTempValue(datas.get(0).getCookerTemperature_f());

            if (result.getaFireValue() > 0 || serialData.isaError() ||
                    result.getbFireValue() > 0 || serialData.isbError() ||
                    result.getcFireValue() > 0 || serialData.iscError() ||
                    result.getdFireValue() > 0 || serialData.isdError() ||
                    result.geteFireValue() > 0 || serialData.iseError() ||
                    result.getfFireValue() > 0 || serialData.isfError()) {
                TFTCookerApplication.getInstance().updateLatestTouchTime();
            }

            result.setBluetoothTempValue(0);
            int fanGear = datas.get(0).getFanGear();
            int status = SettingPreferencesUtil.getHoodConnectStatus(context);
            if (status == CataSettingConstant.HOOD_CONNECTED) {
                fanGear = fanGear | 0x40;
            }

            if (!TFTCookerApplication.getInstance().isPoweredOn()) {
                // 关机状态下发送 0
                fanGear = 0;
            }

            result.setHoodValue(fanGear);
            int realGear = fanGear & 0x0F;
            if (realGear > 0 && realGear != 0x0F) {
                TFTCookerApplication.getInstance().updateLatestTouchTime();

                if (ProductManager.isASKF()) {
                    if (!hoodWorking) {
                        // Start working
                        hoodWorking = true;
                        hoodWorkingStart = SystemClock.elapsedRealtime();
                        hoodWorkingSinceSaved = hoodWorkingStart;
                        filterCarbonTotalTime = SettingPreferencesUtil.getFilterCarbonWorkingTotal(context);
                        filterAluminiumTotalTime = SettingPreferencesUtil.getFilterAluminiumWorkingTotal(context);
                    } else {
                        // Continue working
                        long elapsed = SystemClock.elapsedRealtime() - hoodWorkingStart;
                        long elapsedSinceSaved = SystemClock.elapsedRealtime() - hoodWorkingSinceSaved;
                        if (elapsedSinceSaved >= SAVE_INTERVAL) {
                            addFilterWorkingTotal(elapsed);
                            hoodWorkingSinceSaved = SystemClock.elapsedRealtime();
                        }
                    }
                }
            } else {
                if (ProductManager.isASKF()) {
                    if (hoodWorking) {
                        // Stop working
                        long elapsed = SystemClock.elapsedRealtime() - hoodWorkingStart;
                        addFilterWorkingTotal(elapsed);
                        hoodWorking = false;
                    }
                }
            }

            int lightGear = datas.get(0).getLightGear_blue();
            if (!TFTCookerApplication.getInstance().isPoweredOn()) {
                // 关机状态下发送 0
                lightGear = 0;
            }

            result.setLightValue(lightGear);
            realGear = lightGear & 0x0F;
            if (realGear > 0) {
                TFTCookerApplication.getInstance().updateLatestTouchTime();
            }
        }

        return result;
    }

    private void addFilterWorkingTotal(long milliseconds) {
        if (showingConfirmDialog) {
            return;
        }
        int filter = SettingPreferencesUtil.getFilter(context);
        long newValue;
        switch (filter) {
            case CataSettingConstant.FILTER_ALUMINIUM_50:
                newValue = filterAluminiumTotalTime + milliseconds;
                SettingPreferencesUtil.saveFilterAluminiumWorkingTotal(context, newValue);
                if (newValue > CataSettingConstant.FILTER_ALUMINIUM_CLEAN_DURATION) {
                    EventBus.getDefault().post(new ShowFilterWarningOrder(ShowFilterWarningOrder.ORDER_CLEAN_ALUMINIUM));
                    showingConfirmDialog = true;
                }
                break;
            case CataSettingConstant.FILTER_CARBON_150:
                newValue = filterCarbonTotalTime + milliseconds;
                SettingPreferencesUtil.saveFilterCarbonWorkingTotal(context, newValue);
                if (newValue > CataSettingConstant.FILTER_CARBON_CHANGE_DURATION) {
                    EventBus.getDefault().post(new ShowFilterWarningOrder(ShowFilterWarningOrder.ORDER_CHANGE_CARBON));
                    showingConfirmDialog = true;
                }
                break;
        }
    }
}