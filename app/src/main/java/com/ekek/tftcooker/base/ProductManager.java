package com.ekek.tftcooker.base;

import com.ekek.hardwaremodule.constants.CookerConstant;
import com.ekek.tftcooker.MainActivity;
import com.ekek.tftcooker.common.PatternLockView;
import com.ekek.tftcooker.fragment.CookerPanelFragment60;
import com.ekek.tftcooker.fragment.CookerPanelFragment80;
import com.ekek.tftcooker.fragment.CookerPanelFragment8060;
import com.ekek.tftcooker.fragment.CookerPanelFragment90;
import com.ekek.tftcooker.fragment.CookerPanelFragmentAS;
import com.ekek.tftcooker.fragment.SystemSettingFragment;
import com.ekek.tftcooker.fragment.SystemSettingFragmentAS;
import com.ekek.tftcooker.fragment.SystemSettingFragmentBase;
import com.ekek.tftcooker.model.HandlePatternLockView;
import com.ekek.tftcooker.model.HandlePatternLockView_80;
import com.ekek.tftcooker.model.HandlePatternLockView_90;
import com.ekek.tftcooker.model.HandlePatternLockView_AS;
import com.ekek.tftcooker.model.ICookerPowerDataEx;
import com.ekek.tftcooker.model.KSO60CookerPowerDataEx;
import com.ekek.tftcooker.model.KSO80CookerPowerDataEx;
import com.ekek.tftcooker.model.KSO90CookerPowerDataEx;
import com.ekek.tftcooker.model.KSOASCookerPowerDataEx;

public class ProductManager {

    public static final int KF60 = 0;
    public static final int KF80 = 1;
    public static final int KF90 = 2;
    public static final int ASKF = 3;
    public static final int KF8060 = 4;
    public static final int PRODUCT_TYPE = KF90;
    public static boolean IS_TI = true;

    public static final int CUSTOMER_KSO=0;
    public static final int CUSTOMER_AAUK_ICON=1;
    public static final int CUSTOMER_AAUK_FORZA=2;
    public static final int CUSTOMER_CARINY=3;

    public static int CUSTOMER_NAME=CUSTOMER_CARINY;

    static final String MODEL_INFO_UNKNOWN = "Unknown";
    static final String MODEL_INFO_TI_POSTFIX = " TI";
    static final String MODEL_INFO_60 = "IN5.T 03 MCP.KT 01";
    static final String MODEL_INFO_60_TI = MODEL_INFO_60 + MODEL_INFO_TI_POSTFIX;
    static final String MODEL_INFO_80 = "IN5.T 04 MCP.KT 02";
    static final String MODEL_INFO_80_TI = MODEL_INFO_80 + MODEL_INFO_TI_POSTFIX;
    static final String MODEL_INFO_90 = "IN5.T 05 MCP.KT 03";
    static final String MODEL_INFO_90_TI = MODEL_INFO_90 + MODEL_INFO_TI_POSTFIX;
    static final String MODEL_INFO_AS = "IN5.T 06 MCP.KT 04";
    static final String MODEL_INFO_AS_TI = MODEL_INFO_AS + MODEL_INFO_TI_POSTFIX;
    static final String MODEL_INFO_8060 = MODEL_INFO_80;
    static final String MODEL_INFO_8060_TI = MODEL_INFO_80_TI;

    static final String MODEL_INFO_AAUK_ICON_60 = "IN5.T 03 MCP.MT 01";
    static final String MODEL_INFO_AAUK_ICON_60_TI = MODEL_INFO_AAUK_ICON_60 + MODEL_INFO_TI_POSTFIX;

    static final String MODEL_INFO_AAUK_ICON_80 = "IN5.T 04 MCP.MT 02";
    static final String MODEL_INFO_AAUK_ICON_80_TI = MODEL_INFO_AAUK_ICON_80 + MODEL_INFO_TI_POSTFIX;

    static final String MODEL_INFO_AAUK_ICON_90 = "IN5.T 05 MCP.MT 03";
    static final String MODEL_INFO_AAUK_ICON_90_TI = MODEL_INFO_AAUK_ICON_90 + MODEL_INFO_TI_POSTFIX;

    static final String MODEL_INFO_AAUK_ICON_AS = "IN5.T 06 MCP.MT 04";
    static final String MODEL_INFO_AAUK_ICON_AS_TI = MODEL_INFO_AAUK_ICON_AS + MODEL_INFO_TI_POSTFIX;

    static final String MODEL_INFO_AAUK_FORZA_60 = "IN5.T 03 MCP.MT 04";
    static final String MODEL_INFO_AAUK_FORZA_60_TI = MODEL_INFO_AAUK_FORZA_60 + MODEL_INFO_TI_POSTFIX;

    static final String MODEL_INFO_CARINY_60 = "IN5.T 05 MCP.MT 05";
    static final String MODEL_INFO_CARINY_60_TI = MODEL_INFO_CARINY_60 + MODEL_INFO_TI_POSTFIX;

    private static ICookerPowerDataEx powerData60 = new KSO60CookerPowerDataEx();
    private static ICookerPowerDataEx powerData80 = new KSO80CookerPowerDataEx();
    private static ICookerPowerDataEx powerData90 = new KSO90CookerPowerDataEx();
    private static ICookerPowerDataEx powerDataAS = new KSOASCookerPowerDataEx();
    private static ICookerPowerDataEx powerData8060 = new KSO80CookerPowerDataEx();

    public static int getCookerType() {
        switch (PRODUCT_TYPE) {
            case KF90:
                return CookerConstant.COOKER_TYPE_KSO_90;
            case ASKF:
                return CookerConstant.COOKER_TYPE_KSO_AS;
            case KF60:
                return CookerConstant.COOKER_TYPE_KSO_60;
            case KF80:
            case KF8060:
                return CookerConstant.COOKER_TYPE_KSO_80;
        }

        return CookerConstant.COOKER_TYPE_KSO_90;
    }

    public static CookerPanelFragment createCookerPanelFragment() {
        switch (PRODUCT_TYPE) {
            case KF90:
                return new CookerPanelFragment90();
            case ASKF:
                return new CookerPanelFragmentAS();
            case KF60:
                return new CookerPanelFragment60();
            case KF80:
                return new CookerPanelFragment80();
            case KF8060:
                return new CookerPanelFragment8060();
        }
        return null;
    }
    public static SystemSettingFragmentBase createSystemSettingFragment() {
        switch (PRODUCT_TYPE) {
            case KF90:
                return new SystemSettingFragment();
            case ASKF:
                return new SystemSettingFragmentAS();
            case KF60:
                return new SystemSettingFragment();
            case KF80:
                return new SystemSettingFragment();
            case KF8060:
                return new SystemSettingFragment();
        }
        return null;
    }

    public static PatternLockView getPatternLockView(MainActivity master) {
        switch (PRODUCT_TYPE) {
            case KF90:
                return master.getPlvTF90();
            case ASKF:
                return master.getPlvASKF();
            case KF60:
                return master.getPlvKF60();
            case KF80:
                return master.getPlvKF80();
            case KF8060:
                return master.getPlvKF8060();
        }
        return master.getPlvTF90();
    }

    public static HandlePatternLockView getHandlePatternLockView(String iv) {
        switch (PRODUCT_TYPE) {
            case KF90:
                return new HandlePatternLockView_90(iv);
            case ASKF:
                return new HandlePatternLockView_AS(iv);
            case KF60:
                return new HandlePatternLockView_AS(iv);
            case KF80:
                return new HandlePatternLockView_80(iv);
            case KF8060:
                return new HandlePatternLockView_80(iv);
        }
        return null;
    }

    public static ICookerPowerDataEx getCookerPowerData() {
        switch (PRODUCT_TYPE) {
            case KF60:
                return powerData60;
            case KF80:
                return powerData80;
            case KF90:
                return powerData90;
            case ASKF:
                return powerDataAS;
            case KF8060:
                return powerData8060;
        }
        return null;
    }

    public static String getModelInfo() {
        String returnValu="";
        switch(CUSTOMER_NAME){
            case CUSTOMER_KSO:
                switch (PRODUCT_TYPE) {
                    case KF60:
                        returnValu= IS_TI ? MODEL_INFO_60_TI : MODEL_INFO_60;
                        break;
                    case KF80:
                        returnValu= IS_TI ? MODEL_INFO_80_TI : MODEL_INFO_80;
                        break;
                    case KF90:
                        returnValu= IS_TI ? MODEL_INFO_90_TI : MODEL_INFO_90;
                        break;
                    case ASKF:
                        returnValu= IS_TI ? MODEL_INFO_AS_TI : MODEL_INFO_AS;
                        break;
                    case KF8060:
                        returnValu= IS_TI ? MODEL_INFO_8060_TI : MODEL_INFO_8060;
                        break;
                    default:
                        returnValu= MODEL_INFO_UNKNOWN;
                        break;
                }
                break;
            case CUSTOMER_AAUK_ICON:
                switch (PRODUCT_TYPE) {
                    case KF60:
                        returnValu= IS_TI ? MODEL_INFO_AAUK_ICON_60_TI : MODEL_INFO_AAUK_ICON_60;
                        break;
                    case KF80:
                        returnValu= IS_TI ? MODEL_INFO_AAUK_ICON_80_TI : MODEL_INFO_AAUK_ICON_80;
                        break;
                    case KF90:
                        returnValu= IS_TI ? MODEL_INFO_AAUK_ICON_90_TI : MODEL_INFO_AAUK_ICON_90;
                        break;
                    case ASKF:
                        break;
                    default:
                        returnValu= MODEL_INFO_UNKNOWN;
                        break;
                }
                break;
            case CUSTOMER_AAUK_FORZA:
                switch (PRODUCT_TYPE) {
                    case KF60:
                        returnValu= IS_TI ? MODEL_INFO_AAUK_FORZA_60_TI : MODEL_INFO_AAUK_FORZA_60;
                        break;
                    case KF80:
                        break;
                    case KF90:

                        break;
                    case ASKF:

                        break;
                    default:
                        returnValu= MODEL_INFO_UNKNOWN;
                        break;
                }
                break;
            case CUSTOMER_CARINY:
                switch (PRODUCT_TYPE) {
                    case KF60:
                        break;
                    case KF80:
                        break;
                    case KF90:
                        returnValu= IS_TI ? MODEL_INFO_CARINY_60_TI : MODEL_INFO_CARINY_60;
                        break;
                    case ASKF:

                        break;
                    default:
                        returnValu= MODEL_INFO_UNKNOWN;
                        break;
                }
                break;
            default :
                returnValu= MODEL_INFO_UNKNOWN;
                break;
        }
        return returnValu;
    }
    public static boolean isKF60(){
        return PRODUCT_TYPE == KF60;
    }
    public static boolean isKF80(){
        return PRODUCT_TYPE == KF80;
    }
    public static boolean isKF90(){
        return PRODUCT_TYPE == KF90;
    }
    public static boolean isASKF(){
        return PRODUCT_TYPE == ASKF;
    }
    public static boolean isKF8060(){
        return PRODUCT_TYPE == KF8060;
    }
}
