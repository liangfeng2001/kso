package com.ekek.tftcooker.constants;

import com.ekek.tftcooker.R;

/**
 * Created by Samhung on 2018/4/19.
 */

public class TFTCookerConstant {
    public static final int BRIGHTNESS_GEAR_MAX_VALUE = 15;
    public static final int BRIGHTNESS_GEAR_STEP = 17;
    public static final int VOLUME_GEAR_MAX_VALUE = 15;
    public static final int GEAR_MAX_VALUE = 10;
    public static final int GEAR_MAX_SELECT_VALUE = 9;
    public static final int GEAR_MIN_SELECT_VALUE = 1;
    public static final int GEAR_DEFAULT_VALUE = 5;
    public static final int GEAR_Demon_VALUE = 2;
    public static final int GEAR_DEFAULT_VALUE_ZERO = 0;
    public static final int TIMER_HOUR_MAX_VALUE = 9;  // 23
    public static final int TIMER_MINUTE_MAX_VALUE = 59;
    public static final int TIMER_SECOND_MAX_VALUE = 59;
    public static final int HOOD_MAX_VALUE = 6;
    public static final int HOOD_DEFAULT_VALUE = 5;
    public static final int HOOD_LIGHT_MAX_VALUE = 5;
    public static final int CIRCLE_PROGRESS_ANIMATION_TIME = 200;
    public final static long MILLI_SECOND_IN_FUTURE = 365 * 24 * 60 * 60 * 1000;
    public final static long COUNT_DOWN_INTERVAL = 1000;
    public static final int NO_TOUCH_TIME = 1000 * 60;
    public static final int NO_TOUCH_TIME_5_SECOND = 1000 * 5;

    public static final int NO_TOUCH_TIME_3_SECOND_WHOLE = 3000;
    public static final int NO_TOUCH_TIME_3_SECOND = 2500;

    //-----------系统设置----------------------------------
    public static final int Show_Setting_time = 0;
    public static final int Show_Setting_date = 1;
    public static final int Show_Setting_language = 2;
    public static final int Show_Setting_brightness = 3;
    public static final int Show_Setting_volume = 4;
    public static final int Show_Setting_reset = 5;
    public static final int Show_Setting_power_usage = 6;
    public static final int Show_Setting_connect = 7;
    public static final int Show_Setting_connect_how_to_0 = 18;
    public static final int Show_Setting_connect_how_to_1 = 19;
    public static final int Show_Setting_connect_how_to_complete = 20;
    public static final int Show_Setting_back = 8;
    public static final int Show_Setting_panel = 17;
    public static final int Show_Setting_filter = 21;

    public static final int Setting_time_complete = 9;
    public static final int Setting_date_complete = 10;
    public static final int Setting_language_complete = 11;
    public static final int Setting_brightness_complete = 12;
    public static final int Setting_volume_complete = 13;
    public static final int Setting_reset_complete = 14;
    public static final int Setting_power_usage_complete = 15;
    public static final int Setting_connect_complete = 16;
    public static final int Setting_filter_complete = 22;
    public static final int Setting_demo_mode_toggle_req = 23;

    //分钟 0~59分
    public static final String[] Oven_Minute_GEAR_LIST = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
            "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
            "52", "53", "54", "55", "56", "57", "58", "59"};

    /**
     *
     */
    public static final String[] Oven_Minute_GEAR_LIST_STAND_BY = {"0.5", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
            "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
            "52", "53", "54", "55", "56", "57", "58", "59"};

    public static final int Oven_Minute_DEFAULT_GEAR_INDEX = 3;

    public static final int Oven_Minute_DEFAULT_GEAR_VALUE = 3;

    public static final String[] Oven_Hour_GEAR_LIST = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23"};// 小时 0~24小时

    public static final String[] Oven_Date_GEAR_LIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};// 日期 1~ 31

    public static final int[] Oven_Month_GEAR_LIST = {
            R.string.month_jan,
            R.string.month_feb,
            R.string.month_mar,
            R.string.month_apr,
            R.string.month_may,
            R.string.month_jun,
            R.string.month_jul,
            R.string.month_aug,
            R.string.month_sep,
            R.string.month_oct,
            R.string.month_nov,
            R.string.month_dec};

    public static final String[] Oven_Language_GEAR_LIST = {
            "română", "English", "Français", "Polski", "Português", "Türkçe"};
    public static final int LANGUAGE_UNDEFINED = -1;
    public static final int LANGUAGE_ROMANIAN = 0;
    public static final int LANGUAGE_ENGLISH = 1;
    public static final int LANGUAGE_FRENCH = 2;
    public static final int LANGUAGE_POLISH = 3;
    public static final int LANGUAGE_PORTUGUESE = 4;
    public static final int LANGUAGE_TURKISH = 5;

    public static final String FONT_FILE_GOOD_HOME_LIGHT = "fonts/GoodHomeLight.otf";

    //--------------------串口通信---------------------------------
    public static final byte PROTOCOL_RECEIVE_HEAD_FIRST_BYTE = 0x54;
    public static final byte PROTOCOL_RECEIVE_HEAD_FIRST_BYTE_Sended = 0x52;
    public static final byte PROTOCOL_RECEIVE_HEAD_SECOND_BYTE = 0x58;
    public static final byte PROTOCOL_RECEIVE_HEAD_THIRD_BYTE = 0x54;
    public static final byte PROTOCOL_RECEIVE_HEAD_FOURTH_BYTE = 0x3A;
    public static final byte PROTOCOL_RECEIVE_END_SECOND_LAST_BYTE = 0x0D;
    public static final byte PROTOCOL_RECEIVE_END_LAST_BYTE = 0x0A;

    public static final int PROTOCOL_RECEIVE_DATA_SIZE = 29;//17:老协议长度, 21: 协议v2 长度

    public static final int PROTOCOL_STATE_POWER_SWITCH_ON = 0x55;
    public static final int PROTOCOL_STATE_POWER_SWITCH_OFF = 0xAA;
    //------------------------数据库------------------------------
    public final static String DATABASE_NAME = "tftoventest1.db";
    public final static String DATABASE_NAME_SystemSetting = "tftovensystemsetting.db";

    public final static String DATABASE_NAME_ENCRYPTED = "tfthob.db.encrypted";

    public static final boolean DATABASE_ENCRYPTED = false;
    //-------------------------------------------------------
    //-------------------------错误代码------------------------------------
    public static final int ER03=1;
    public static final int E1=2;
    public static final int F1=3;
    public static final int F3=4;
    public static final int F4=5;
    public static final int E2=6;
    public static final int E3=7;
    public static final int E4=8;
    public static final int E5=9;
    //----------------------------------------------------------------------
    public static final String ARGUMENT_APP_START = "ApplicationStart";
    public static final int APP_START_NONE = -1;
    public static final int APP_START_FIRST_TIME = 0;
    public static final int APP_START = 1;
    public static final int APP_START_IDLE = 2;

    public static final int COOKER_MODE_NORMAL = 0x01;
    public static final int COOKER_MODE_FREE_ZONE = 0x05;
    public static final int COOKER_MODE_1E = 0x1E;
    public static final int COOKER_MODE_F1 = 0xF1;
    public static final int COOKER_MODE_F2 = 0xF2;
    public static final int COOKER_MODE_F3 = 0xF3;
    public static final int COOKER_MODE_SETTING = 0x01;
    public static final int COOKER_MODE_CLOCK_SCREEN = 0xE1;
    public static final int COOKER_MODE_BLACK_SCREEN = 0xE2;

    public static final int CLOCK_SCREEN_TIMEOUT = 2 * 60 * 1000;
    public static final int SCREEN_SLEEP_DELAY = 15 * 1000;

    public static final String BRAND_EKEK = "EKEK";
    public static final String VERSION_DISPLAY_UNKNOWN = "Unknown";
}
