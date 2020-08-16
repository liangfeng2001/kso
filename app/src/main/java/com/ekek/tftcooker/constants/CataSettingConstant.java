package com.ekek.tftcooker.constants;

public class CataSettingConstant {

    public static final int DEFAULT_ALARM_CLOCK_HOUR = 1;

    public static final int DEFAULT_ALARM_CLOCK_MINUTE = 0;

    public final static String DATABASE_NAME = "setting.db";

    public final static String DATABASE_NAME_ENCRYPTED = "setting.db.encrypted";

    public static final boolean DATABASE_ENCRYPTED = false;

    public static final String DEFAULT_THEME = "skin_black.skin";
    public static final String DEFAULT_BRIGHTNESS = "" + 8 * TFTCookerConstant.BRIGHTNESS_GEAR_STEP;
    public static final int DEFAULT_LANGUAGE = TFTCookerConstant.LANGUAGE_ENGLISH;

    public static final String SOUND_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String SOUND_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_SOUND_SWITCH_STATUS = SOUND_SWITCH_STATUS_OPEN;

    public static final String STABLISH_CONNECTION_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String STABLISH_CONNECTION_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_STABLISH_CONNECTION_SWITCH_STATUS = STABLISH_CONNECTION_SWITCH_STATUS_OPEN;

    public static final String HOOD_OPTIONS_AUTO_MODE_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String HOOD_OPTIONS_AUTO_MODE_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_HOOD_OPTIONS_AUTO_MODE_SWITCH_STATUS = HOOD_OPTIONS_AUTO_MODE_SWITCH_STATUS_OPEN;

    public static final String POWER_LIMIT_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String POWER_LIMIT_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_POWER_LIMIT_SWITCH_STATUS = POWER_LIMIT_SWITCH_STATUS_OPEN;

    public static final String CLICK_SOUND_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String CLICK_SOUND_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_CLICK_SOUND_SWITCH_STATUS = CLICK_SOUND_SWITCH_STATUS_OPEN;

    public static final String INCREASE_VOLUME_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String INCREASE_VOLUME_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_INCREASE_VOLUME_SWITCH_STATUS = INCREASE_VOLUME_SWITCH_STATUS_OPEN;

    public static final String LOGO_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String LOGO_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_LOGO_SWITCH_STATUS = LOGO_SWITCH_STATUS_OPEN;

    public static final String HIBERNATION_MODE_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String HIBERNATION_MODE_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_HIBERNATION_MODE_SWITCH_STATUS = HIBERNATION_MODE_SWITCH_STATUS_OPEN;

    public static final String TOTAL_TURN_OFF_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String TOTAL_TURN_OFF_SWITCH_STATUS_OPEN = "OPEN";
    public static final String DEFAULT_TOTAL_TURN_OFF_SWITCH_STATUS = TOTAL_TURN_OFF_SWITCH_STATUS_OPEN;
    public static final int DEFAULT_ACTIVATION_TIME = 5 * 60;
    public static final int DEFAULT_TOTAL_TURN_OFF_TIME = 6 * 60 * 60;

    public static final long DEFAULT_FILTER_CARBON_TOTAL_WORKING_TIME = 0L;
    public static final long DEFAULT_FILTER_ALUMINIUM_TOTAL_WORKING_TIME = 0L;

    public static final String HIBERNATION_FORMAT_DATE_SWITCH_STATUS_CLOSE = "CLOSE";
    public static final String HIBERNATION_FORMAT_DATE_SWITCH_STATUS_OPEN = "OPEN";
    public static final String HIBERNATION_FORMAT_DATE_SWITCH_STATUS = HIBERNATION_FORMAT_DATE_SWITCH_STATUS_OPEN;

    public static final String HIBERNATION_FORMAT_HOUR_FORMAT_DIGITAL = "DIGITAL";
    public static final String HIBERNATION_FORMAT_HOUR_FORMAT_ANALOGIC = "ANALOGIC";
    public static final String HIBERNATION_FORMAT_HOUR_FORMAT = HIBERNATION_FORMAT_HOUR_FORMAT_DIGITAL;

    public static final String DEFAULT_THE_FIRST_TIME_SWITCH_ON_HOB="NOT OK";

    public static final String THE_FIRST_TIME_SWITCH_ON_HOB="OK";

    public static final int DEFAULT_ALARM_VOLUME_LEVEL = 8;
    public static final int DEFAULT_ALARM_DURATION = 2 * 60;
    public static final int DEFAULT_ALARM_POSTPONE_DURATION = 10 * 60;


    public static final int TIME_FORMAT_12 = 0;
    public static final int TIME_FORMAT_24 = 1;
    public static final int DEFAULT_TIME_24_FORMAT = TIME_FORMAT_24;

    public static final int HOOD_OPTIONS_AUTO_MODE_INTENSE = 0;
    public static final int HOOD_OPTIONS_AUTO_MODE_ECOLOGIC = 1;
    public static final int DEFAULT_HOOD_OPTIONS_AUTO_MODE = HOOD_OPTIONS_AUTO_MODE_INTENSE;

    public static final int POWER_LIMIT_LEVEL_2800 = 0;
    public static final int POWER_LIMIT_LEVEL_3300 = 1;
    public static final int POWER_LIMIT_LEVEL_4500 = 2;
    public static final int POWER_LIMIT_LEVEL_5700 = 3;
    public static final int DEFAULT_POWER_LIMIT_LEVEL = POWER_LIMIT_LEVEL_5700;

    public static final String DEFAULT_SOUND_LEVEL = "10";

    public static final String POWER_LIMIT_LOW = "Low";
    public static final String POWER_LIMIT_MEDIUM = "Medium";
    public static final String POWER_LIMIT_HIGH = "High";
    public static final String DEFAULT_POWER_LIMIT = POWER_LIMIT_MEDIUM;

    public static final String HOOD_MODE_MANUAL = "MANUAL";
    public static final String HOOD_MODE_AUTOMATIC = "AUTOMATIC";
    public static final String DEFAULT_HOOD_MODE = HOOD_MODE_MANUAL;

    public static final String HOOD_LEVEL_LOW = "Low";
    public static final String HOOD_LEVEL_MEDIUM = "Medium";
    public static final String HOOD_LEVEL_HIGH = "High";
    public static final String DEFAULT_HOOD_LEVEL = HOOD_LEVEL_MEDIUM;

    public static final String SECURITY_MODE_PIN = "PIN";
    public static final String SECURITY_MODE_PATTERN = "PATTERN";
    public static final String SECURITY_MODE_PRESS_UNLOCK = "PRESS_UNLOCK";
    public static final String DEFAULT_SECURITY_MODE = SECURITY_MODE_PRESS_UNLOCK;

    public static final int HOOD_NOT_CONNECTED = 0;
    public static final int HOOD_CONNECTED = 1;
    public static final int DEFAULT_HOOD_CONNECT_STATUS = HOOD_NOT_CONNECTED;

    public static final int FILTER_NONE = -1;
    public static final int FILTER_CARBON_150 = 0;
    public static final long FILTER_CARBON_CHANGE_DURATION = 150 * 60 * 60 * 1000;
    public static final int FILTER_ALUMINIUM_50 = 1;
    public static final long FILTER_ALUMINIUM_CLEAN_DURATION = 150 * 60 * 60 * 1000;
    public static final int DEFAULT_FILTER = FILTER_NONE;

    public static final int HOB_NOT_PAUSED = 0;
    public static final int HOB_PAUSED = 1;
    public static final int DEFAULT_HOB_PAUSE_STATUS = HOB_NOT_PAUSED;

    public static final int REQUIRE_HOOD_CONNECTION_NO = 0;
    public static final int REQUIRE_HOOD_CONNECTION_YES = 1;
    public static final int DEFAULT_REQUIRE_HOOD_CONNECTION = REQUIRE_HOOD_CONNECTION_NO;

    public static final int ENTERED_CLOCK_SCREEN_NO = 0;
    public static final int ENTERED_CLOCK_SCREEN_YES = 1;
    public static final int DEFAULT_ENTERED_CLOCK_SCREEN = ENTERED_CLOCK_SCREEN_NO;

    public static final int DEMONSTRATION_MODE_NO = 0;
    public static final int DEMONSTRATION_MODE_YES = 1;
    public static final int DEFAULT_DEMONSTRATION_MODE = DEMONSTRATION_MODE_NO;

    public static final int DEBUG_MODE_NO = 0;
    public static final int DEBUG_MODE_YES = 1;
    public static final int DEFAULT_DEBUG_MODE = DEBUG_MODE_NO;
}
