package com.ekek.tftcooker.database;

import android.content.Context;
import android.util.Log;

import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.SettingDatabaseConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.entity.SettingPreferences;
import com.ekek.tftcooker.entity.SettingPreferencesDao;

public class SettingPreferencesUtil {

    //default theme
    public static void saveDefaultTheme(Context context, String name) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_THEME)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null, SettingDatabaseConstant.DEFAULT_THEME,name);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(name);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultTheme(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_THEME)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_THEME;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default brightness
    public static void saveDefaultBrightness(Context context,String brightness) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_BRIGHTNESS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_BRIGHTNESS,brightness);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(brightness);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultBrightness(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_BRIGHTNESS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_BRIGHTNESS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default language
    public static void saveDefaultLanguage(Context context, int language) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties
                        .Item
                        .eq(SettingDatabaseConstant.DEFAULT_LANGUAGE))
                .build()
                .unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.DEFAULT_LANGUAGE,
                    "" + language);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter("" + language);
            dao.update(settingPreferences);
        }
    }

    public static int getDefaultLanguage(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties
                        .Item
                        .eq(SettingDatabaseConstant.DEFAULT_LANGUAGE))
                .build()
                .unique();
        if (settingPreferences == null) {
            return TFTCookerConstant.LANGUAGE_UNDEFINED;
        }else {
            return Integer.parseInt(settingPreferences.getParameter());
        }
    }

    //default sound switch status
    public static void saveSoundSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.SOUND_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.SOUND_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getSoundSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.SOUND_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_SOUND_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default sound
    public static void saveDefaultSound(Context context,String level) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_SOUND)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_SOUND,level);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(level);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultSound(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_SOUND)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_SOUND_LEVEL;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default power limit
    public static void saveDefaultPowerLimit(Context context,String level) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_POWER_LIMIT)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_POWER_LIMIT,level);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(level);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultPowerLimit(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_POWER_LIMIT)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_POWER_LIMIT;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default hood mode
    public static void saveDefaultHoodMode(Context context,String mode) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_HOOD_MODE)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_HOOD_MODE,mode);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(mode);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultHoodMode(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_HOOD_MODE)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_HOOD_MODE;
        }else {
            return settingPreferences.getParameter();
        }
    }


    //default hood level
    public static void saveDefaultHoodLevel(Context context,String level) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_HOOD_LEVEL)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_HOOD_LEVEL,level);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(level);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultHoodLevel(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_HOOD_LEVEL)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_HOOD_LEVEL;
        }else {
            return settingPreferences.getParameter();
        }
    }


    //default security mode
    public static void saveDefaultSecurityMode(Context context,String mode) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_SECURITY_MODE)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_SECURITY_MODE,mode);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(mode);
            dao.update(settingPreferences);
        }
    }

    public static String getDefaultSecurityMode(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_SECURITY_MODE)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_SECURITY_MODE;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //security pin password
    public static void saveSecurityPinPassword(Context context,String pwd) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.SECURITY_PIN_PASSWORD)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.SECURITY_PIN_PASSWORD,pwd);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(pwd);
            dao.update(settingPreferences);
        }
    }

    public static String getSecurityPinPassword(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.SECURITY_PIN_PASSWORD)).build().unique();
        if (settingPreferences == null) {
            return "";
        }else {
            return settingPreferences.getParameter();
        }
    }


    //security pattern password
    public static void saveSecurityPatternPassword(Context context,String pwd) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.SECURITY_PATTERN_PASSWORD)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.SECURITY_PATTERN_PASSWORD,pwd);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(pwd);
            dao.update(settingPreferences);
        }
    }

    public static String getSecurityPatternPassword(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.SECURITY_PATTERN_PASSWORD)).build().unique();
        if (settingPreferences == null) {
            return "";
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default hood options switch status
    public static void saveStablishConnectionSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.STABLISH_CONNECTION)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.STABLISH_CONNECTION,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getStablishConnectionSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.STABLISH_CONNECTION)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_STABLISH_CONNECTION_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default hood options switch status
    public static void saveHoodOptionsAutoModeSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HOOD_OPTIONS_AUTO_MODE_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.HOOD_OPTIONS_AUTO_MODE_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getHoodOptionsAutoModeSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HOOD_OPTIONS_AUTO_MODE_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_HOOD_OPTIONS_AUTO_MODE_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default hood options auto mode
    public static void saveHoodOptionsAutoMode(Context context,int mode) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HOOD_OPTIONS_AUTO_MODE)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.HOOD_OPTIONS_AUTO_MODE,String.valueOf(mode));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(mode));
            dao.update(settingPreferences);
        }
    }

    public static int getHoodOptionsAutoMode(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HOOD_OPTIONS_AUTO_MODE)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_HOOD_OPTIONS_AUTO_MODE;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    //default hood options switch status
    public static void savePowerLimitSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.POWER_LIMIT_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.POWER_LIMIT_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getPowerLimitSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.POWER_LIMIT_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_POWER_LIMIT_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default power limit level
    public static void savePowerLimitLevel(Context context,int level) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.POWER_LIMIT_POWER_LEVEL)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.POWER_LIMIT_POWER_LEVEL,String.valueOf(level));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(level));
            dao.update(settingPreferences);
        }
    }

    public static int getPowerLimitLevel(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.POWER_LIMIT_POWER_LEVEL)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_POWER_LIMIT_LEVEL;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    //default click sound switch status
    public static void saveClickSoundSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.CLICK_SOUND_SWITCH)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.CLICK_SOUND_SWITCH,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getClickSoundSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.CLICK_SOUND_SWITCH)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_CLICK_SOUND_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default click sound switch status
    public static void saveTimeFormat24(Context context,int timeFormat) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.TIME_FORMAT_24)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.TIME_FORMAT_24,String.valueOf(timeFormat));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(timeFormat));
            dao.update(settingPreferences);
        }
    }

    public static boolean getTimeFormat24(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.TIME_FORMAT_24)).build().unique();
        if (settingPreferences == null) {
            return true;
        }else {
            int timeFormat = Integer.valueOf(settingPreferences.getParameter());
            return (timeFormat == CataSettingConstant.DEFAULT_TIME_24_FORMAT);
        }
    }


    //default increase volume switch status
    public static void saveIncreaseVolumeSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.INCREASE_VOLUME_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.INCREASE_VOLUME_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getIncreaseVolumeSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.INCREASE_VOLUME_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_INCREASE_VOLUME_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //default alarm volume level
    public static void saveAlarmVolumeLevel(Context context,int level) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ALARM_VOLUME_LEVEL)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.ALARM_VOLUME_LEVEL,String.valueOf(level));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(level));
            dao.update(settingPreferences);
        }
    }

    public static int getAlarmVolumeLevel(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ALARM_VOLUME_LEVEL)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_ALARM_VOLUME_LEVEL;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    //default alarm duration
    public static void saveAlarmDuration(Context context,int duration) {//秒
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ALARM_DURATION)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.ALARM_VOLUME_LEVEL,String.valueOf(duration));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(duration));
            dao.update(settingPreferences);
        }
    }

    public static int getAlarmDuration(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ALARM_DURATION)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_ALARM_DURATION;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    //default alarm duration
    public static void saveAlarmPostponeDuration(Context context,int duration) {//秒
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ALARM_POSTPONE_DURATION)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.ALARM_POSTPONE_DURATION,String.valueOf(duration));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(duration));
            dao.update(settingPreferences);
        }
    }

    public static int getAlarmPostponeDuration(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ALARM_POSTPONE_DURATION)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_ALARM_POSTPONE_DURATION;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    //logo switch status
    public static void saveLogoSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.LOGO_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.LOGO_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getLogoSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.LOGO_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_LOGO_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //hibernation mode switch status
    public static void saveHibernationModeSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HIBERNATION_MODE_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.HIBERNATION_MODE_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getHibernationModeSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HIBERNATION_MODE_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_HIBERNATION_MODE_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //total turn off switch status
    public static void saveTotalTurnOffSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.TOTAL_TURN_OFF_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.TOTAL_TURN_OFF_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getTotalTurnOffSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.TOTAL_TURN_OFF_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_TOTAL_TURN_OFF_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //activation time duration
    public static void saveActivationTime(Context context,int duration) {//秒
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ACTIVATION_TIME)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.ACTIVATION_TIME,String.valueOf(duration));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(duration));
            dao.update(settingPreferences);
        }
    }
    public static int getActivationTime(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.ACTIVATION_TIME)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_ACTIVATION_TIME;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    /**
     * save the total time when the Filter Carbon is working
     * @param context the context
     * @param duration time in milliseconds
     */
    public static synchronized void saveFilterCarbonWorkingTotal(
            Context context,
            long duration) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.FILTER_CARBON_TOTAL_WORKING_TIME))
                .build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.FILTER_CARBON_TOTAL_WORKING_TIME,
                    String.valueOf(duration));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(duration));
            dao.update(settingPreferences);
        }
    }

    /**
     * get the total time when the Filter Carbon is working
     * @param context context
     * @return time in milliseconds
     */
    public static long getFilterCarbonWorkingTotal(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.FILTER_CARBON_TOTAL_WORKING_TIME))
                .build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_FILTER_CARBON_TOTAL_WORKING_TIME;
        }else {
            return Long.valueOf(settingPreferences.getParameter());
        }
    }

    /**
     * save the total time when the Filter Aluminium is working
     * @param context context
     * @param duration time in milliseconds
     */
    public static synchronized void saveFilterAluminiumWorkingTotal(
            Context context,
            long duration) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.FILTER_ALUMINIUM_TOTAL_WORKING_TIME))
                .build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.FILTER_ALUMINIUM_TOTAL_WORKING_TIME,
                    String.valueOf(duration));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(duration));
            dao.update(settingPreferences);
        }
    }

    /**
     * get the total time when the Filter Aluminium is working
     * @param context context
     * @return time in milliseconds
     */
    public static long getFilterAluminiumWorkingTotal(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.FILTER_ALUMINIUM_TOTAL_WORKING_TIME))
                .build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_FILTER_ALUMINIUM_TOTAL_WORKING_TIME;
        }else {
            return Long.valueOf(settingPreferences.getParameter());
        }
    }

    //total turn off time
    public static void saveTotalTurnOffTime(Context context,int duration) {//秒
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.TOTAL_TURN_OFF_TIME)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.TOTAL_TURN_OFF_TIME,String.valueOf(duration));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(String.valueOf(duration));
            dao.update(settingPreferences);
        }
    }

    public static int getTotalTurnOffTime(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.TOTAL_TURN_OFF_TIME)).build().unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_TOTAL_TURN_OFF_TIME;
        }else {
            return Integer.valueOf(settingPreferences.getParameter());
        }
    }

    //hibernation Hour format switch status
    public static void saveHibernationFormatDateSwitchStatus(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.INTRO_DATE_SWITCH_STATUS)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.INTRO_DATE_SWITCH_STATUS,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getHibernationFormatDateSwitchStatus(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.INTRO_DATE_SWITCH_STATUS)).build().unique();
        if (settingPreferences == null) {

            return CataSettingConstant.HIBERNATION_FORMAT_DATE_SWITCH_STATUS;
        }else {
            return settingPreferences.getParameter();
        }
    }

    //hibernation Hour format
    public static void saveHibernationHourFormat(Context context,String status) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HOUR_FORMAT)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.HOUR_FORMAT,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static String getHibernationHourFormat(Context context) {
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.HOUR_FORMAT)).build().unique();
        if (settingPreferences == null) {

            return CataSettingConstant.HIBERNATION_FORMAT_HOUR_FORMAT;
        }else {
            return settingPreferences.getParameter();
        }
    }
    public static String getTheFirstTimeSwitchOnHob(Context context){

        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_THE_FIRST_TIME)).build().unique();
        if (settingPreferences == null) {

            return CataSettingConstant.DEFAULT_THE_FIRST_TIME_SWITCH_ON_HOB;
        }else {
            return settingPreferences.getParameter();
        }

    }

    public static void saveTheFirstTimeSwitchOnHob(Context context,String status) {//
        SettingPreferencesDao dao = SettingDatabaseHelper.getInstance(context).getDaoSession().getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao.queryBuilder().where(SettingPreferencesDao.Properties.Item.eq(SettingDatabaseConstant.DEFAULT_THE_FIRST_TIME)).build().unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(null,SettingDatabaseConstant.DEFAULT_THE_FIRST_TIME,status);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(status);
            dao.update(settingPreferences);
        }
    }

    public static void saveHoodConnectStatus(Context context, int status) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.HOOD_CONNECT_STATUS))
                .build()
                .unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.HOOD_CONNECT_STATUS,
                    Integer.toString(status));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(Integer.toString(status));
            dao.update(settingPreferences);
        }

        TFTCookerApplication.getInstance().setHoodConnected(status == CataSettingConstant.HOOD_CONNECTED);
    }

    public static int getFilter(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.FILTER))
                .build()
                .unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_FILTER;
        }else {
            return Integer.parseInt(settingPreferences.getParameter());
        }
    }

    public static void saveFilter(Context context, int filter) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.FILTER))
                .build()
                .unique();

        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.FILTER,
                    Integer.toString(filter));
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(Integer.toString(filter));
            dao.update(settingPreferences);
        }
    }

    public static int getHoodConnectStatus(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.HOOD_CONNECT_STATUS))
                .build()
                .unique();
        int result;
        if (settingPreferences == null) {
            result = CataSettingConstant.DEFAULT_HOOD_CONNECT_STATUS;
        }else {
            result = Integer.parseInt(settingPreferences.getParameter());
        }
        TFTCookerApplication.getInstance().setHoodConnected(result == CataSettingConstant.HOOD_CONNECTED);
        return result;
    }

    public static void saveHobPauseStatus(Context context, boolean paused) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.HOB_PAUSE_STATUS))
                .build()
                .unique();

        String value = paused ? "1" : "0";
        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.HOB_PAUSE_STATUS,
                    value);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(value);
            dao.update(settingPreferences);
        }
    }

    public static Boolean getHobPauseStatus(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.HOB_PAUSE_STATUS))
                .build()
                .unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_HOOD_CONNECT_STATUS == CataSettingConstant.HOB_PAUSED;
        }else {
            return Integer.parseInt(settingPreferences.getParameter()) == CataSettingConstant.HOB_PAUSED;
        }
    }
    public static void saveRequireHoodConnection(Context context, boolean require) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.REQUIRE_HOOD_CONNECTION))
                .build()
                .unique();

        String value = require ? "1" : "0";
        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.REQUIRE_HOOD_CONNECTION,
                    value);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(value);
            dao.update(settingPreferences);
        }
    }

    public static Boolean getRequireHoodConnection(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.REQUIRE_HOOD_CONNECTION))
                .build()
                .unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_REQUIRE_HOOD_CONNECTION == CataSettingConstant.REQUIRE_HOOD_CONNECTION_YES;
        }else {
            return Integer.parseInt(settingPreferences.getParameter()) == CataSettingConstant.REQUIRE_HOOD_CONNECTION_YES;
        }
    }
    private static boolean enteredClockScreen =
            CataSettingConstant.DEFAULT_ENTERED_CLOCK_SCREEN == CataSettingConstant.ENTERED_CLOCK_SCREEN_YES;
    public static void saveEnteredClockScreen(Context context, boolean entered) {
        if (entered == enteredClockScreen) {
            return;
        }

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.ENTERED_CLOCK_SCREEN))
                .build()
                .unique();

        String value = entered ? "1" : "0";
        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.ENTERED_CLOCK_SCREEN,
                    value);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(value);
            dao.update(settingPreferences);
        }
        enteredClockScreen = entered;
    }

    public static Boolean getEnteredClockScreen(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.ENTERED_CLOCK_SCREEN))
                .build()
                .unique();
        if (settingPreferences == null) {
            enteredClockScreen = CataSettingConstant.DEFAULT_ENTERED_CLOCK_SCREEN == CataSettingConstant.ENTERED_CLOCK_SCREEN_YES;
        }else {
            enteredClockScreen = Integer.parseInt(settingPreferences.getParameter()) == CataSettingConstant.ENTERED_CLOCK_SCREEN_YES;
        }
        return enteredClockScreen;
    }

    public static void saveDemonstrationMode(Context context, boolean active) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.DEMONSTRATION_MODE))
                .build()
                .unique();

        String value = active ? "1" : "0";
        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.DEMONSTRATION_MODE,
                    value);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(value);
            dao.update(settingPreferences);
        }
    }

    public static Boolean getDemonstrationMode(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.DEMONSTRATION_MODE))
                .build()
                .unique();
        if (settingPreferences == null) {
            return CataSettingConstant.DEFAULT_DEMONSTRATION_MODE == CataSettingConstant.DEMONSTRATION_MODE_YES;
        }else {
            return Integer.parseInt(settingPreferences.getParameter()) == CataSettingConstant.DEMONSTRATION_MODE_YES;
        }
    }

    public static void saveDebugMode(Context context, boolean active) {
        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.DEBUG_MODE))
                .build()
                .unique();

        String value = active ? "1" : "0";
        if (settingPreferences == null) {
            settingPreferences = new SettingPreferences(
                    null,
                    SettingDatabaseConstant.DEBUG_MODE,
                    value);
            dao.insertOrReplace(settingPreferences);
        }else {
            settingPreferences.setParameter(value);
            dao.update(settingPreferences);
        }
        if (active) {
            Logger.getInstance().setLoggingLevel(Log.DEBUG);
        } else {
            Logger.getInstance().resetLoggingLevel();
        }
    }

    public static Boolean getDebugMode(Context context) {

        SettingPreferencesDao dao = SettingDatabaseHelper
                .getInstance(context)
                .getDaoSession()
                .getSettingPreferencesDao();
        SettingPreferences settingPreferences = dao
                .queryBuilder()
                .where(SettingPreferencesDao
                        .Properties.Item
                        .eq(SettingDatabaseConstant.DEBUG_MODE))
                .build()
                .unique();
        Boolean result;
        if (settingPreferences == null) {
            result = CataSettingConstant.DEFAULT_DEBUG_MODE == CataSettingConstant.DEBUG_MODE_YES;
        }else {
            result = Integer.parseInt(settingPreferences.getParameter()) == CataSettingConstant.DEBUG_MODE_YES;
        }
        if (result) {
            Logger.getInstance().setLoggingLevel(Log.DEBUG);
        } else {
            Logger.getInstance().resetLoggingLevel();
        }
        return result;
    }
}
