package com.ekek.tftcooker.base;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.PowerOffAllOrder;
import com.ekek.tftcooker.event.PowerSwitchEvent;
import com.ekek.tftcooker.event.ShowErrorOrder;
import com.ekek.tftcooker.event.ShowTimerCompleteOrder;
import com.ekek.tftcooker.event.TimeEvent;
import com.ekek.tftcooker.model.AnalysisSerialData;
import com.ekek.tftcooker.model.CookerDataA;
import com.ekek.tftcooker.model.CookerDataB;
import com.ekek.tftcooker.model.CookerDataC;
import com.ekek.tftcooker.model.CookerDataD;
import com.ekek.tftcooker.model.CookerDataE;
import com.ekek.tftcooker.model.CookerDataF;
import com.ekek.tftcooker.utils.ViewUtils;
import com.ekek.tftcooker.views.CookerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class CookerPanelFragment extends Fragment {
    public static final int WORK_MODE_HOB = 0;
    public static final int WORK_MODE_HOOD = 1;
    public static final int WORK_MODE_SET_TIMER = 2;
    public static final int WORK_MODE_SET_LIGHT = 3;
    public static final int WORK_MODE_SET_TIMER_MINUTE = 4;
    public static final int WORK_MODE_SET_TIMER_HOUR = 5;

    public static final int COOKER_ID_Up_Left = 0;      //up left
    public static final int COOKER_ID_Down_Left = 1;    // down left
    public static final int COOKER_ID_Middle = 2;       // middle
    public static final int COOKER_ID_Up_Right = 3;     // up right
    public static final int COOKER_ID_Down_Right = 4;   // down right
    public static final int COOKER_ID_NONE = 5;         // none

    public static final int COOKER_ID_Up_Left_Down_Left = 6;   // 左上，左下
    public static final int COOKER_ID_Up_Right_Down_Right = 7;   // 右上、右下
    public static final int COOKER_ID_Up_Left_Down_Right = 8; // 左上、右下
    public static final int COOKER_ID_Up_Right_Down_Left = 9; // 右上、左下

    public static final int COOKER_ID_Up_Right_Up_Left = 10; // 右上、左上 同时点击
    public static final int COOKER_ID_Down_Right_Down_Left = 11; // 右下、左下 同时点击

    public static final int COOKER_ID_Up_Left__Middle = 12; // 左上、中间 同时点击
    public static final int COOKER_ID_Down_Left_Middle = 13; // 左下、中间 同时点击
    public static final int COOKER_ID_Up_Right_Middle = 14; // 右上、中间 同时点击
    public static final int COOKER_ID_Down_Right_Middle = 15; // 右下、中间 同时点击

    public static final int COOKER_ID_Up_Left_Down_Right_Middle = 16;// 左上、中间、右下
    public static final int COOKER_ID_Up_Right_Down_Left_Middle = 17;// 右上、中间、左下
    public static final int COOKER_ID_Up_Left_Up_Right_Middle = 18;// 左上、中间、右上
    public static final int COOKER_ID_Down_Left_Down_Right_Middle = 19;// 左下、中间、右下
    public static final int COOKER_ID_Up_Left_Down_Left_Middle = 20;//  左上、左下、中间
    public static final int COOKER_ID_Down_Right_Up_Right_Middle = 21;// 右上、右下、中间

    public static final int COOKER_ID_Down_Right_Up_Right_Middle_Down_Left = 22;// 右下、右上、中间、左下
    public static final int COOKER_ID_Down_Right_Up_Right_Middle_Up_Left = 23; // 右下、右上、中间、左上
    public static final int COOKER_ID_Down_Left_Up_Left_Middle_Up_Right = 24; // 左下、左上、中间、右上
    public static final int COOKER_ID_Down_Left_Up_Left_Middle_Down_Right = 25; // 左下、左上、中间、右下

    public static final int COOKER_ID_All = 26; //全部
    public static final int COOKER_ID_Up_Right_Up_Left_Down_Left = 27; //左上、左下 、右上
    public static final int COOKER_ID_Up_Right_Down_Right_Down_Left = 28; // 右上、右下、左下

    public static final int COOKER_ID_Up_Left_Down_Left_Down_Right = 29; // 左上、左下、右下
    public static final int COOKER_ID_Up_Left_Up_Right_Down_Right = 30; // 右上、右下、左上

    public static final int COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right = 31; //左上、左下、右下、右上

    public static final int COOKER_ID_Up_Left_And_Down_Letf_SameTime = 33; // 左上，左下  同时点击
    public static final int COOKER_ID_Up_Right_And_Down_Right_SameTime = 34; // 右上、右下 同时点击

    public static final int COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left = 35;//左上、右上、右下、左下
    public static final int COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right = 36;//右上、左上、左下、右下

    public static final int COOKER_ID_ALL_ALL_ANY = 37;//全部 有区
    public static final int COOKER_ID_ALL_ALL_LEFT_NONE = 38;//全部 左边无区
    public static final int COOKER_ID_ALL_ALL_NONE_RIGHT = 39;//全部 右边无区

    public static final int COOKER_ID_Up_Left_Middle_Down_Right_Down_Left = 40; // 左上、中间、右下、左下
    public static final int COOKER_ID_Up_Right_Middle_Down_Left_Down_Right = 41; // 右上、中间、左下、右下
    public static final int COOKER_ID_Down_Left_Middle_Up_Right_Up_Left = 42; // 左下、中间、右上、左上
    public static final int COOKER_ID_Down_Right_Middle_Up_Left_Up_Right = 43; // 右下、中间、左上、右上

    public static final int COOKER_ID_Up_Left_Midlle_Down_Left = 44; //左上、中间、左下
    public static final int COOKER_ID_Down_Right_Middle_Up_Right = 45; //右上、中间、右下

    public static final int COOKER_ID_Up_Right_And_Up_Left_Slip = 46;  // 右上、左上  滑动
    public static final int COOKER_ID_Down_Right_And_Down_Left_Slip = 47; // 右下、左下  滑动

    public static final int COOKER_ID_Up_Left__Middle_Slip = 48;// 左上、中间 滑动
    public static final int COOKER_ID_Down_Left_Middle_Slip = 49;// 左下、中间 滑动
    public static final int COOKER_ID_Up_Right_Middle_Slip = 50;// 右上、中间  滑动
    public static final int COOKER_ID_Down_Right_Middle_Slip = 51;// 右下、中间  滑动
//---------------------------------------------------------------------------------------------------------------
    public static final int Up_Left_And_Down_Letf = 1;// 左上，左下
    public static final int Up_Left_And_Down_Right = 2;// 左上、右下
    public static final int Up_Left_And_Down_Letf_SameTime = 33; // 左上，左下  同时
    public static final int Up_Right_And_Down_Right_SameTime = 34;// 右上、右下  同时
    public static final int Up_Right_And_Down_Right = 9;// 右上、右下
    public static final int Up_Right_And_Down_Left = 10;// 右上、左下

    public static final int Up_Right_And_Up_Left = 21;// 右上、左上 同时
    public static final int Up_Right_And_Up_Left_Slip = 46;// 右上、左上  滑动
    public static final int Down_Right_And_Down_Left = 22;// 右下、左下  同时
    public static final int Down_Right_And_Down_Left_Slip = 47;// 右下、左下  滑动

    public static final int Up_Left__Middle = 24;// 左上、中间  同时
    public static final int Down_Left_Middle = 25;// 左下、中间  同时
    public static final int Up_Right_Middle = 26;// 右上、中间  同时
    public static final int Down_Right_Middle = 27;// 右下、中间  同时

    public static final int Up_Left__Middle_Slip = 48;// 左上、中间 滑动
    public static final int Down_Left_Middle_Slip = 49;// 左下、中间 滑动
    public static final int Up_Right_Middle_Slip = 50;// 右上、中间  滑动
    public static final int Down_Right_Middle_Slip = 51;// 右下、中间  滑动

    public static final int Up_Left_Down_Right_Middle = 11;// 左上、中间、右下
    public static final int Up_Right_Down_Left_Middle = 12;// 右上、中间、左下
    public static final int Up_Left_Up_Right_Middle = 13;// 左上、中间、右上
    public static final int Down_Left_Down_Right_Middle = 14;// 左下、中间、右下

    public static final int Up_Left_Down_Left_Middle = 15;//  左上、左下、中间
    public static final int Up_Left_Midlle_Down_Left = 44;//  左上、中间、左下
    public static final int Down_Right_Middle_Up_Right = 45;//右上、中间、右下
    public static final int Down_Right_Up_Right_Middle = 3; // 右上、右下、中间

    public static final int Up_Right_Up_Left_Down_Left = 28; // 右上、左上、左下
    public static final int Up_Right_Ddwn_Right_Down_Left = 29; // 右上、右上、左下
    public static final int Up_Left_Down_Left_Down_Right = 30; // 左上、左下、右下
    public static final int Up_Left_Up_Right_Down_Right = 31; // 左上、右上、右下

    public static final int Up_Left_Down_Left_Down_Right_Up_Right = 32;//左上、左下、右下、右上

    public static final int Up_Left_Up_Right_Down_Right_Down_Left = 35;//左上、右上、右下、左下
    public static final int Up_Right_Up_Left_Down_Left_Down_Right = 36;//右上、左上、左下、右下


    public static final int Down_Right_Up_Right_Middle_Down_Left = 16;// 右下、右上、中间、左下
    public static final int Down_Right_Up_Right_Middle_Up_Left = 17; // 右下、右上、中间、左上
    public static final int Down_Left_Up_Left_Middle_Up_Right = 18; // 左下、左上、中间、右上
    public static final int Down_Left_Up_Left_Middle_Down_Right = 19; // 左下、左上、中间、右下

    public static final int Up_Left_Middle_Down_Right_Down_Left = 40; // 左上、中间、右下、左下
    public static final int Up_Right_Middle_Down_Left_Down_Right = 41; // 右上、中间、左下、右下
    public static final int Down_Left_Middle_Up_Right_Up_Left = 42; // 左下、中间、右上、左上
    public static final int Down_Right_Middle_Up_Left_Up_Right = 43; // 右下、中间、左上、右上

    public static final int ALL_ALL = 20; //全部

    public static final int ALL_ALL_ANY = 37; //全部 有区
    public static final int ALL_ALL_LEFT_NONE = 38; // 全部，左边无区
    public static final int ALL_ALL_NONE_RIGHT = 39; // 全部，右边无区

    public static final int Lock_All_Button = 4;
    public static final int UnLock_All_Button = 5;
    public static final int Power_Off_ALL = 6;
    public static final int Pause_Recover = 7;
    public static final int UnLock_Click = 8;
    public static final int Check_Removing_Pan = 99;
    public static final int TimerIsUp_5_Second = 100;
    public static final int The_Total_Cookers = 55; // 模式的总数量 2018年12月3日11:00:44

    public static final int ColorOfBoostButton_colorOrange = 1;
    public static final int ColorOfBoostButton_colorWhite = 2;

    protected static final int HANDLER_COOKER_Timer_Complete = 1;
    protected static final int HANDLER_COOKER_SELECT = 2;

    protected static final int HANDLER_TOUCH_SCREEN = 5;
    protected static final int HANDLER_TOUCH_SELECT_COOKER = 6;
    protected static final int HANDLER_TIMER_IS_UP = 7;
    protected static final int HANDLER_TOUCH_SCREEN_ONE_MINUTE = 8;
    protected static final int HANDLER_TIMER_DOING = 9;
    protected static final int HANDLER_UPDATE_CIRCLE_PROGRESS = 10;
    protected static final int HANDLER_HOOD_NUMBER_BLINK = 11;
    protected static final int UPDATE_CIRCLE_PROGRESS_DELAY = 12;
    protected static final int UPDATE_CIRCLE_PROGRESS_WHEN_POWER_OFF = 13;
    protected static final int HANDLER_SET_HOOD_AUTO = 14;
    protected static final int HANDLER_ADJUST_LEVEL = 15;
    protected static final int HANDLER_HOOD_GEAR_CHANGED = 16;
    protected static final int HANDLER_HOOD_GEAR_BLINK = 17;
    protected static final int HANDLER_HOOD_TIMER_FINISHED = 18;
    protected static final int HANDLER_PAUSE_SHOW_TEXT = 19;
    protected static final int HANDLER_POWER_OFF_FROM_ERROR03=20;

    protected static final int LIGHT_MODE_OFF = 0;
    protected static final int LIGHT_MODE_LIGHT_Orange = 1;
    protected static final int LIGHT_MODE_LIGHT_Blue = 3;


    protected static final int cooker_color_blue = Color.rgb(216, 72, 36);
    protected static final int cooker_color_gray = Color.rgb(58, 60, 61);

    protected static final int DURATION_TO_SHUT_DOWN_AUDIO = 60 * 1000;

    protected static final String TIMER_ZERO_STRING = "00:00";
    protected static final String HOUR_FORMAT = "%01d";
    protected static final String MINUTE_FORMAT = "%02d";
    protected static final String NO_PAN_DISPLAY_TEXT = "?";

    protected static int DURATION_FOR_POWER_OFF_WHEN_PAUSE = 10 * 60;

    protected static int ERR_F1_BIT = 0x01;
    protected static int ERR_E3_BIT = 0x02;
    protected static int ERR_E4_BIT = 0x04;
    protected static int ERR_E1_BIT = 0x08;
    protected static int ERR_E2_BIT = 0x10;
    protected static int ERR_F4_BIT = 0x20;
    protected static int ERR_F3_BIT = 0x40;
    protected static int ERR_E5_BIT = 0x80;

    protected static final int DURATION_ERROR_DETECT = 10 * 1000;
    public static final int DURATION_FIVE_MINUTES = 5 * 60 * 1000;
    public static final int DURATION_TWO_MINUTES = 2 * 60 * 1000;

    protected int workMode = WORK_MODE_HOB;
    protected int currentCooker = COOKER_ID_NONE;
    protected int hoodGear = 0;

    protected int lightMode = LIGHT_MODE_OFF;
    protected int lastLightModeUsed = LIGHT_MODE_LIGHT_Orange;
    protected int lightGear_blue = 2; // 更新 2018年11月9日10:55:56
    protected int lastLightGearBlueUsed = 2;
    protected int lightGear_orange = 3; // 更新 2018年11月24日10:22:07
    protected int lightCearValue_sended = 0;  // 更新 2018年11月29日19:42:18
    protected int hoodGear_sended = 0; // 更新 2018年12月4日17:28:39

    //1
    protected boolean click_set_to_alert_flag_Up_Left_Down_Left = false;  // 左下 左上
    protected boolean click_set_to_alert_flag_Up_Right_Down_Right = false;  // 右上 右下
    //2
    protected boolean click_set_to_alert_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
    protected boolean click_set_to_alert_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
    protected boolean click_set_to_alert_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
    protected boolean click_set_to_alert_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
    //3
    protected boolean click_set_to_alert_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
    protected boolean click_set_to_alert_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
    //4
    protected boolean click_set_to_alert_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
    protected boolean click_set_to_alert_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
    protected boolean click_set_to_alert_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
    //5
    protected boolean click_set_to_alert_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
    protected boolean click_set_to_alert_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
    protected boolean click_set_to_alert_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
    protected boolean click_set_to_alert_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
    //6
    protected boolean click_set_to_alert_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
    protected boolean click_set_to_alert_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
    protected boolean click_set_to_alert_flag_All = false;//全部

    //-------------------------------------------------------------------------------------------------------------------
    //1
    protected boolean click_set_to_stop_flag_Up_Left_Down_Left = false;  // 左下 左上
    protected boolean click_set_to_stop_flag_Up_Right_Down_Right = false;  // 右上 右下
    //2
    protected boolean click_set_to_stop_flag_Up_Right_Up_Left_Down_Left = false;  //左上、左下 、右上
    protected boolean click_set_to_stop_flag_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
    protected boolean click_set_to_stop_flag_Up_Left_Up_Right_Down_Right = false; // 右上、右下、左上
    protected boolean click_set_to_stop_flag_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
    //3
    protected boolean click_set_to_stop_flag_Up_Left_Down_Left_Middle = false;//  左上、左下、中间
    protected boolean click_set_to_stop_flag_Down_Right_Up_Right_Middle = false;// 右上、右下、中间
    //4
    protected boolean click_set_to_stop_flag_Up_Left_Up_Right_Down_Right_Down_Left = false;//左上、右上、右下、左下
    protected boolean click_set_to_stop_flag_Up_Right_Up_Left_Down_Left_Down_Right = false;//右上、左上、左下、右下
    protected boolean click_set_to_stop_flag_Up_Left_Down_Left_Down_Right_Up_Right = false;  //左上、左下、右下、右上
    //5
    protected boolean click_set_to_stop_flag_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
    protected boolean click_set_to_stop_flag_Down_Right_Up_Right_Middle_Up_Left = false;// 右下、右上、中间、左上
    protected boolean click_set_to_stop_flag_Down_Left_Up_Left_Middle_Up_Right = false;// 左下、左上、中间、右上
    protected boolean click_set_to_stop_flag_Down_Left_Up_Left_Middle_Down_Right = false;// 左下、左上、中间、右下
    //6
    protected boolean click_set_to_stop_flag_ALL_ALL_LEFT_NONE = false;//全部 左边无区
    protected boolean click_set_to_stop_flag_ALL_ALL_NONE_RIGHT = false;//全部 右边无区
    protected boolean click_set_to_stop_flag_All = false;//全部
    //-------------------------------------------------------------------------------------------------------------------

    protected boolean Select_CookerView_Up_Left_Down_Left = false;  // 左上，左下  滑动
    protected boolean Select_CookerView_Up_Right_Down_Right = false;  // 右上、右下 滑动

    protected boolean Select_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下
    protected boolean Select_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下

    protected boolean Select_CookerView_Up_Left_Down_Right = false;// 左上、右下  同时

    protected boolean Select_CookerView_Up_Right_Down_Left = false; // 右上、左下  同时

    protected boolean Select_CookerView_Up_Right_Up_Left = false;// 右上、左上   同时
    protected boolean Select_CookerView_Down_Right_Down_Left = false;// 左下、右下  同时

    protected boolean Select_CookerView_Up_Right_And_Up_Left_Slip = false;  // 右上、左上  滑动
    protected boolean Select_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动

    protected boolean Select_CookerView_Up_Left__Middle = false;// 左上、中间 同时
    protected boolean Select_CookerView_Down_Left_Middle = false;// 左下、中间 同时
    protected boolean Select_CookerView_Up_Right_Middle = false;// 右上、中间 同时
    protected boolean Select_CookerView_Down_Right_Middle = false;// 右下、中间 同时

    protected boolean Select_CookerView_Up_Left__Middle_Slip = false;// 左上、中间 滑动
    protected boolean Select_CookerView_Down_Left_Middle_Slip = false;// 左下、中间 滑动
    protected boolean Select_CookerView_Up_Right_Middle_Slip = false;// 右上、中间 滑动
    protected boolean Select_CookerView_Down_Right_Middle_Slip = false;// 右下、中间 滑动

    protected boolean Select_CookerView_Up_Left_Down_Right_Middle = false;// 左上、中间、右下
    protected boolean Select_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
    protected boolean Select_CookerView_Up_Left_Up_Right_Middle = false;// 左上、中间、右上
    protected boolean Select_CookerView_Down_Left_Down_Right_Middle = false;// 左下、中间、右下

    protected boolean Select_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
    protected boolean Select_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间

    protected boolean Select_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
    protected boolean Select_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下

    protected boolean Select_CookerView_Up_Right_Up_Left_Down_Left = false; //左上、左下 、右上
    protected boolean Select_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下

    protected boolean Select_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
    protected boolean Select_CookerView_Up_Left_Up_Right_Down_Right = false;    // 右上、右下、左上

    protected boolean Select_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false;//左上、左下、右下、右上

    protected boolean Select_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
    protected boolean Select_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下

    protected boolean Select_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
    protected boolean Select_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
    protected boolean Select_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
    protected boolean Select_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下

    protected boolean Select_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; //  左上、中间、右下、左下
    protected boolean Select_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; //  右上、中间、左下、右下
    protected boolean Select_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; //  左下、中间、右上、左上
    protected boolean Select_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; //  右下、中间、左上、右上

    protected boolean Select_CookerView_All = false;//全部
    protected boolean Select_CookerView_ALL_ALL_ANY = false;//全部 有区
    protected boolean Select_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
    protected boolean Select_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区

    protected boolean ReadyToSelect_onCookerSelected = true;

    protected boolean cookerViewUpLeft_boost_flag = false;// 左上
    protected boolean cookerViewDownLeft_boost_flag = false;// 左下
    protected boolean cookerViewMiddle_boost_flag = false;// 中间
    protected boolean cookerViewUpRight_boost_flag = false; // 右上
    protected boolean cookerViewDownRight_boost_flag = false;// 右下

    protected boolean boost_flag_CookerView_Up_Left_Down_Left = false;  // 左上，左下
    protected boolean boost_flag_CookerView_Up_Right_Down_Right = false;  // 右上、右下

    protected boolean boost_flag_CookerView_Up_Left_Down_Left_SameTime = false;  // 左上，左下 同时
    protected boolean boost_flag_CookerView_Up_Right_Down_Right_SameTime = false;  // 右上、右下 同时


    protected boolean boost_flag_CookerView_Up_Left_Down_Right = false;// 左上、右下
    protected boolean boost_flag_CookerView_Up_Right_Down_Left = false; // 右上、左下

    protected boolean boost_flag_CookerView_Up_Right_Up_Left = false;// 右上、左上
    protected boolean boost_flag_CookerView_Down_Right_Down_Left = false;// 左下、右下

    protected boolean boost_flag_CookerView_Up_Right_And_Up_Left_Slip = false;// 右上、左上  滑动
    protected boolean boost_flag_CookerView_Down_Right_And_Down_Left_Slip = false; // 右下、左下  滑动


    protected boolean boost_flag_CookerView_Up_Left__Middle = false;// 左上、中间 同时
    protected boolean boost_flag_CookerView_Down_Left_Middle = false;// 左下、中间 同时
    protected boolean boost_flag_CookerView_Up_Right_Middle = false;// 右上、中间 同时
    protected boolean boost_flag_CookerView_Down_Right_Middle = false;// 右下、中间 同时

    protected boolean boost_flag_CookerView_Up_Left__Middle_Slip = false;// 左上、中间  滑动
    protected boolean boost_flag_CookerView_Down_Left_Middle_Slip = false;// 左下、中间  滑动
    protected boolean boost_flag_CookerView_Up_Right_Middle_Slip = false;// 右上、中间  滑动
    protected boolean boost_flag_CookerView_Down_Right_Middle_Slip = false;// 右下、中间  滑动

    protected boolean boost_flag_CookerView_Up_Left_Down_Right_Middle = false;// 左上、中间、右下
    protected boolean boost_flag_CookerView_Up_Right_Down_Left_Middle = false;// 右上、中间、左下
    protected boolean boost_flag_CookerView_Up_Left_Up_Right_Middle = false;// 左上、中间、右上
    protected boolean boost_flag_CookerView_Down_Left_Down_Right_Middle = false;// 左下、中间、右下

    protected boolean boost_flag_CookerView_Up_Left_Down_Left_Middle = false;// 左上、左下、中间
    protected boolean boost_flag_CookerView_Down_Right_Up_Right_Middle = false;// 右上、右下、中间

    protected boolean boost_flag_CookerView_Up_Left_Midlle_Down_Left = false;//左上、中间、左下
    protected boolean boost_flag_CookerView_Down_Right_Middle_Up_Right = false;//右上、中间、右下


    protected boolean boost_flag_CookerView_Up_Right_Down_Right_Down_Left = false; // 右上、右下、左下
    protected boolean boost_flag_CookerView_Up_Right_Down_Right_Up_Left = false; // 右上、右下、左上

    protected boolean boost_flag_CookerView_Up_Left_Down_Left_Down_Right = false; // 左上、左下、右下
    protected boolean boost_flag_CookerView_Up_Left_Down_Left_Up_Right = false; // 左上、左下、右上

    protected boolean boost_flag_CookerView_Up_Left_Down_Left_Down_Right_Up_Right = false; // 左上、左下、右下、右上
    protected boolean boost_flag_CookerView_Up_Left_Up_Right_Down_Right_Down_Left = false; //左上、右上、右下、左下
    protected boolean boost_flag_CookerView_Up_Right_Up_Left_Down_Left_Down_Right = false; //右上、左上、左下、右下

    protected boolean boost_flag_CookerView_Down_Right_Up_Right_Middle_Down_Left = false;// 右下、右上、中间、左下
    protected boolean boost_flag_CookerView_Down_Right_Up_Right_Middle_Up_Left = false; // 右下、右上、中间、左上
    protected boolean boost_flag_CookerView_Down_Left_Up_Left_Middle_Up_Right = false; // 左下、左上、中间、右上
    protected boolean boost_flag_CookerView_Down_Left_Up_Left_Middle_Down_Right = false; // 左下、左上、中间、右下

    protected boolean boost_flag_CookerView_Up_Left_Middle_Down_Right_Down_Left = false; // 左上、中间、右下、左下
    protected boolean boost_flag_CookerView_Up_Right_Middle_Down_Left_Down_Right = false; // 右上、中间、左下、右下
    protected boolean boost_flag_CookerView_Down_Left_Middle_Up_Right_Up_Left = false; // 左下、中间、右上、左上
    protected boolean boost_flag_CookerView_Down_Right_Middle_Up_Left_Up_Right = false; // 右下、中间、左上、右上

    protected boolean boost_flag_CookerView_All = false;//全部
    protected boolean boost_flag_CookerView_ALL_ANY = false;//全部 有区
    protected boolean boost_flag_CookerView_ALL_ALL_LEFT_NONE = false;//全部 左边无区
    protected boolean boost_flag_CookerView_ALL_ALL_NONE_RIGHT = false;//全部 右边无区

    protected Unbinder unbinder;
    protected MessageHandler handler;
    protected ActiveCookers activeCookers = new ActiveCookers();

    protected boolean click_pause_flag = false;
    protected int pause_count_down = DURATION_FOR_POWER_OFF_WHEN_PAUSE;

    protected CookerDataA cdA = new CookerDataA();
    protected CookerDataA cdAFree = new CookerDataA(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
    protected CookerDataB cdB = new CookerDataB();
    protected CookerDataB cdBFree = new CookerDataB(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
    protected CookerDataC cdC = new CookerDataC();
    protected CookerDataC cdCFree = new CookerDataC(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
    protected CookerDataD cdD = new CookerDataD();
    protected CookerDataD cdDFree = new CookerDataD(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
    protected CookerDataE cdE = new CookerDataE();
    protected CookerDataE cdEFree = new CookerDataE(TFTCookerConstant.COOKER_MODE_FREE_ZONE);
    protected CookerDataF cdF = new CookerDataF();
    protected CookerDataF cdFFree = new CookerDataF(TFTCookerConstant.COOKER_MODE_FREE_ZONE);

    protected long e1DetectedTime = 0;
    protected long e2DetectedTime = 0;
    protected long e3DetectedTime = 0;
    protected long e4DetectedTime = 0;
    protected long e5DetectedTime = 0;
    protected long f1DetectedTime = 0;
    protected long f3DetectedTime = 0;
    protected long f4DetectedTime = 0;

    protected HoodShutDownBlinkThread hoodShutDownBlinkThread = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), container, false);
        initAllViews(view);

        unbinder = ButterKnife.bind(this, view);
        handler = new MessageHandler(this);
        initialize();
        initTouchScreenValue();
        registerEventBus();
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        unregisterEventBus();
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshTextWhenLanguageChanged(newConfig.locale);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TimeEvent event) {
        if (click_pause_flag) {
            pause_count_down--;
            if (pause_count_down == 0) {
                powerOffAllCookers();
                setPauseFlag(false);
                EventBus.getDefault().post(new ShowTimerCompleteOrder(ShowTimerCompleteOrder.ORDER_PAUSE_TIMEOUT));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PowerOffAllOrder order) {
        switch (order.getOrder()) {
            case PowerOffAllOrder.ORDER_POWER_OFF:
                powerOffAllCookers();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PowerSwitchEvent event) {

        // 关机开机时，均刷新 hoodAuto，是因为在开机时，处理本事件会有些许延时，
        // 如果关机时不把 hoodAuto 刷回正确值，那么开机时，就会有一两次发送手动0档给下位机
        int status = SettingPreferencesUtil.getHoodConnectStatus(getActivity());
        TFTCookerApplication.getInstance().setHoodAuto((status == CataSettingConstant.HOOD_CONNECTED));
        setAutoUI();

        if (event.isOn()) {
            if (TFTCookerApplication.getInstance().isHoodAuto()) {
                startHoodShutDownBlinkThread(
                        CookerPanelFragment.DURATION_FIVE_MINUTES);
            }
        }
    }

    protected void initAllViews(View rootView) {
        List<View> allChildrenViews = ViewUtils.getAllChildrenViews(rootView);
        for (View v: allChildrenViews) {
            v.setSoundEffectsEnabled(false);
            if (v instanceof TextView) {
                ((TextView)v).setTypeface(TFTCookerApplication.goodHomeLight);
            }
        }
    }
    protected void initTouchScreenValue() {
        handler.sendEmptyMessageDelayed(HANDLER_TOUCH_SCREEN, 1000);
    }
    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void setPauseFlag(boolean value) {
        click_pause_flag = value;
        pause_count_down = DURATION_FOR_POWER_OFF_WHEN_PAUSE;
    }

    protected void checkErrors(AnalysisSerialData event) {
        if (event.isPoweredOn()) {
            int checkBits = getCookersBits();

            boolean e1 = event.checkError(checkBits, AnalysisSerialData.ERR_E1);
            String e1Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_E1);
            if (!e1) {
                e1DetectedTime = 0;
            } else {
                if (e1DetectedTime == 0) {
                    e1DetectedTime = SystemClock.elapsedRealtime();
                    e1 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - e1DetectedTime;
                    e1 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean e2 = event.checkError(checkBits, AnalysisSerialData.ERR_E2);
            String e2Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_E2);
            if (!e2) {
                e2DetectedTime = 0;
            } else {
                if (e2DetectedTime == 0) {
                    e2DetectedTime = SystemClock.elapsedRealtime();
                    e2 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - e2DetectedTime;
                    e2 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean e3 = event.checkError(checkBits, AnalysisSerialData.ERR_E3);
            String e3Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_E3);
            if (!e3) {
                e3DetectedTime = 0;
            } else {
                if (e3DetectedTime == 0) {
                    e3DetectedTime = SystemClock.elapsedRealtime();
                    e3 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - e3DetectedTime;
                    e3 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean e4 = event.checkError(checkBits, AnalysisSerialData.ERR_E4);
            String e4Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_E4);
            if (!e4) {
                e4DetectedTime = 0;
            } else {
                if (e4DetectedTime == 0) {
                    e4DetectedTime = SystemClock.elapsedRealtime();
                    e4 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - e4DetectedTime;
                    e4 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean e5 = event.checkError(checkBits, AnalysisSerialData.ERR_E5);
            String e5Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_E5);
            if (!e5) {
                e5DetectedTime = 0;
            } else {
                if (e5DetectedTime == 0) {
                    e5DetectedTime = SystemClock.elapsedRealtime();
                    e5 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - e5DetectedTime;
                    e5 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean f1 = event.checkError(checkBits, AnalysisSerialData.ERR_F1);
            String f1Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_F1);
            if (!f1) {
                f1DetectedTime = 0;
            } else {
                if (f1DetectedTime == 0) {
                    f1DetectedTime = SystemClock.elapsedRealtime();
                    f1 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - f1DetectedTime;
                    f1 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean f3 = event.checkError(checkBits, AnalysisSerialData.ERR_F3);
            String f3Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_F3);
            if (!f3) {
                f3DetectedTime = 0;
            } else {
                if (f3DetectedTime == 0) {
                    f3DetectedTime = SystemClock.elapsedRealtime();
                    f3 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - f3DetectedTime;
                    f3 = duration > DURATION_ERROR_DETECT;
                }
            }

            boolean f4 = event.checkError(checkBits, AnalysisSerialData.ERR_F4);
            String f4Cookers = event.getErrorCookers(checkBits, AnalysisSerialData.ERR_F4);
            if (!f4) {
                f4DetectedTime = 0;
            } else {
                if (f4DetectedTime == 0) {
                    f4DetectedTime = SystemClock.elapsedRealtime();
                    f4 = false;
                } else {
                    long duration = SystemClock.elapsedRealtime() - f4DetectedTime;
                    f4 = duration > DURATION_ERROR_DETECT;
                }
            }

            if (e1) {
                handleCookerErrors(
                        R.string.err_e1,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_E1));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_E1, e1Cookers));
            } else if (e2) {
                handleCookerErrors(
                        R.string.err_e2,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_E2));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_E2, e2Cookers));
            } else if (e3) {
                handleCookerErrors(
                        R.string.err_e3,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_E3));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_E3, e3Cookers));
            } else if (e4) {
                handleCookerErrors(
                        R.string.err_e4,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_E4));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_E4, e4Cookers));
            } else if (e5) {
                handleCookerErrors(
                        R.string.err_e5,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_E5));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_E5, e5Cookers));
            } else if (f1) {
                handleCookerErrors(
                        R.string.err_f1,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_F1));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_F1, f1Cookers));
            } else if (f3) {
                handleCookerErrors(
                        R.string.err_f3,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_F3));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_F3, f3Cookers));
            } else if (f4) {
                handleCookerErrors(
                        R.string.err_f4,
                        event.getErrorCookerBits(checkBits, AnalysisSerialData.ERR_F4));
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_F4, f4Cookers));
            } else {
                handleCookerErrors(0,0);
                EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_NO_ERROR));
            }
        } else {
            handleCookerErrors(0,0);
            EventBus.getDefault().post(new ShowErrorOrder(ShowErrorOrder.ORDER_NO_ERROR));
        }
    }

    protected int getRefinedGearValue(CookerView cookerView) {
        int result = cookerView.getGearValue();
        if (result == 9) {
            switch (cookerView.getCountdownState9()) {
                case CookerView.COUNT_DOWN_STATE9_LEVEL_1:
                    result = 11;
                    break;
                case CookerView.COUNT_DOWN_STATE9_LEVEL_2:
                    result = 12;
                    break;
            }
        }
        return result;
    }

    protected String getCookerIDStr(int cookerId) {
        switch (cookerId) {
            case COOKER_ID_Up_Left:
                return "COOKER_ID_Up_Left";
            case COOKER_ID_Down_Left:
                return "COOKER_ID_Down_Left";
            case COOKER_ID_Middle:
                return "COOKER_ID_Middle";
            case COOKER_ID_Up_Right:
                return "COOKER_ID_Up_Right";
            case COOKER_ID_Down_Right:
                return "COOKER_ID_Down_Right";
            case COOKER_ID_NONE:
                return "COOKER_ID_NONE";

            case COOKER_ID_Up_Left_Down_Left:
                return "COOKER_ID_Up_Left_Down_Left";
            case COOKER_ID_Up_Right_Down_Right:
                return "COOKER_ID_Up_Right_Down_Right";
            case COOKER_ID_Up_Left_Down_Right:
                return "COOKER_ID_Up_Left_Down_Right";
            case COOKER_ID_Up_Right_Down_Left:
                return "COOKER_ID_Up_Right_Down_Left";

            case COOKER_ID_Up_Right_Up_Left:
                return "COOKER_ID_Up_Right_Up_Left";
            case COOKER_ID_Down_Right_Down_Left:
                return "COOKER_ID_Down_Right_Down_Left";

            case COOKER_ID_Up_Left__Middle:
                return "COOKER_ID_Up_Left__Middle";
            case COOKER_ID_Down_Left_Middle:
                return "COOKER_ID_Down_Left_Middle";
            case COOKER_ID_Up_Right_Middle:
                return "COOKER_ID_Up_Right_Middle";
            case COOKER_ID_Down_Right_Middle:
                return "COOKER_ID_Down_Right_Middle";

            case COOKER_ID_Up_Left_Down_Right_Middle:
                return "COOKER_ID_Up_Left_Down_Right_Middle";
            case COOKER_ID_Up_Right_Down_Left_Middle:
                return "COOKER_ID_Up_Right_Down_Left_Middle";
            case COOKER_ID_Up_Left_Up_Right_Middle:
                return "COOKER_ID_Up_Left_Up_Right_Middle";
            case COOKER_ID_Down_Left_Down_Right_Middle:
                return "COOKER_ID_Down_Left_Down_Right_Middle";
            case COOKER_ID_Up_Left_Down_Left_Middle:
                return "COOKER_ID_Up_Left_Down_Left_Middle";
            case COOKER_ID_Down_Right_Up_Right_Middle:
                return "COOKER_ID_Down_Right_Up_Right_Middle";

            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:
                return "COOKER_ID_Down_Right_Up_Right_Middle_Down_Left";
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:
                return "COOKER_ID_Down_Right_Up_Right_Middle_Up_Left";
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:
                return "COOKER_ID_Down_Left_Up_Left_Middle_Up_Right";
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right:
                return "COOKER_ID_Down_Left_Up_Left_Middle_Down_Right";

            case COOKER_ID_All:
                return "COOKER_ID_All";
            case COOKER_ID_Up_Right_Up_Left_Down_Left:
                return "COOKER_ID_Up_Right_Up_Left_Down_Left";
            case COOKER_ID_Up_Right_Down_Right_Down_Left:
                return "COOKER_ID_Up_Right_Down_Right_Down_Left";

            case COOKER_ID_Up_Left_Down_Left_Down_Right:
                return "COOKER_ID_Up_Left_Down_Left_Down_Right";
            case COOKER_ID_Up_Left_Up_Right_Down_Right:
                return "COOKER_ID_Up_Left_Up_Right_Down_Right";

            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:
                return "COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right";

            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:
                return "COOKER_ID_Up_Left_And_Down_Letf_SameTime";
            case COOKER_ID_Up_Right_And_Down_Right_SameTime:
                return "COOKER_ID_Up_Right_And_Down_Right_SameTime";

            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left:
                return "COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left";
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right:
                return "COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right";

            case COOKER_ID_ALL_ALL_ANY:
                return "COOKER_ID_ALL_ALL_ANY";
            case COOKER_ID_ALL_ALL_LEFT_NONE:
                return "COOKER_ID_ALL_ALL_LEFT_NONE";
            case COOKER_ID_ALL_ALL_NONE_RIGHT:
                return "COOKER_ID_ALL_ALL_NONE_RIGHT";

            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:
                return "COOKER_ID_Up_Left_Middle_Down_Right_Down_Left";
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:
                return "COOKER_ID_Up_Right_Middle_Down_Left_Down_Right";
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left:
                return "COOKER_ID_Down_Left_Middle_Up_Right_Up_Left";
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right:
                return "COOKER_ID_Down_Right_Middle_Up_Left_Up_Right";

            case COOKER_ID_Up_Left_Midlle_Down_Left:
                return "COOKER_ID_Up_Left_Midlle_Down_Left";
            case COOKER_ID_Down_Right_Middle_Up_Right:
                return "COOKER_ID_Down_Right_Middle_Up_Right";

            case COOKER_ID_Up_Right_And_Up_Left_Slip:
                return "COOKER_ID_Up_Right_And_Up_Left_Slip";
            case COOKER_ID_Down_Right_And_Down_Left_Slip:
                return "COOKER_ID_Down_Right_And_Down_Left_Slip";

            case COOKER_ID_Up_Left__Middle_Slip:
                return "COOKER_ID_Up_Left__Middle_Slip";
            case COOKER_ID_Down_Left_Middle_Slip:
                return "COOKER_ID_Down_Left_Middle_Slip";
            case COOKER_ID_Up_Right_Middle_Slip:
                return "COOKER_ID_Up_Right_Middle_Slip";
            case COOKER_ID_Down_Right_Middle_Slip:
                return "COOKER_ID_Down_Right_Middle_Slip";
        }
        return "" + cookerId;
    }

    protected int getAutoHoodGearLevel(int gear) {
        int result;
        switch (gear) {
            case 2:
                result = 1;
                break;
            case 4:
                result = 2;
                break;
            case 5:
                result = 3;
                break;
            case 6:
                result = 4;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    protected int getAutoHoodLevel() {
        int totalPower = getTotalPower();
        if (totalPower > 32) {
            return 6;
        } else if (totalPower > 24) {
            return 5;
        } else if (totalPower > 8) {
            return 4;
        } else if (totalPower > 0) {
            return 2;
        } else {
            return 0;
        }
    }

    protected boolean isCookerSelected(int cookerId) {
        switch (currentCooker) {
            case COOKER_ID_Up_Left:
                return cookerId == COOKER_ID_Up_Left;
            case COOKER_ID_Down_Left:
                return cookerId == COOKER_ID_Down_Left;
            case COOKER_ID_Middle:
                return cookerId == COOKER_ID_Middle;
            case COOKER_ID_Up_Right:
                return cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Down_Right:
                return cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_And_Down_Letf_SameTime:// 左上，左下 同时
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left;
            case COOKER_ID_Up_Right_And_Down_Right_SameTime: // 右上、右下 同时点击
                return cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Down_Left:// 左上，左下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left;
            case COOKER_ID_Up_Right_Down_Right:  // 右上、右下
                return cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Down_Right: // 左上、右下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Right_Down_Left: // 右上、左下
                return cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Left;
            case COOKER_ID_Up_Right_And_Up_Left_Slip:  // 右上、左上  滑动
            case COOKER_ID_Up_Right_Up_Left:// 右上、左上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Down_Right_And_Down_Left_Slip: // 右下、左下  滑动
            case COOKER_ID_Down_Right_Down_Left:// 左下、右下
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left__Middle_Slip:// 左上、中间 滑动
            case COOKER_ID_Up_Left__Middle:// 左上、中间
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Middle;
            case COOKER_ID_Down_Left_Middle_Slip:// 左下、中间 滑动
            case COOKER_ID_Down_Left_Middle:// 左下、中间
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle;
            case COOKER_ID_Up_Right_Middle_Slip:// 右上、中间  滑动
            case COOKER_ID_Up_Right_Middle:// 右上、中间
                return cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Middle;
            case COOKER_ID_Down_Right_Middle_Slip:// 右下、中间  滑动
            case COOKER_ID_Down_Right_Middle:// 右下、中间
                return cookerId == COOKER_ID_Down_Right
                        || cookerId == COOKER_ID_Middle;
            case COOKER_ID_Up_Right_Up_Left_Down_Left: //左上、左下 、右上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Up_Left_Down_Left_Down_Right: // 左上、左下、右下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Up_Right_Down_Right_Down_Left://左上、右上、右下、左下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Right_Up_Left_Down_Left_Down_Right://右上、左上、左下、右下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Down_Left_Down_Right_Up_Right:  //左上、左下、右下、右上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Up_Right_Down_Right: // 右上、右下、左上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Right_Down_Right_Down_Left: // 右上、右下、左下
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Down_Right_Middle:// 左上、中间、右下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Right_Down_Left_Middle:// 右上、中间、左下
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Up_Left_Up_Right_Middle:// 左上、中间、右上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Down_Left_Down_Right_Middle:// 左下、中间、右下
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Midlle_Down_Left://左上、中间、左下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle;
            case COOKER_ID_Up_Left_Down_Left_Middle:// 左上、左下、中间
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle;
            case COOKER_ID_Down_Right_Middle_Up_Right://右上、中间、右下
                return cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Down_Right_Up_Right_Middle:// 右上、右下、中间
                return cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Down_Right_Up_Right_Middle_Down_Left:// 右下、右上、中间、左下
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Down_Right_Up_Right_Middle_Up_Left:// 右下、右上、中间、左上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Down_Left_Up_Left_Middle_Up_Right:// 左下、左上、中间、右上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Down_Left_Up_Left_Middle_Down_Right: // 左下、左上、中间、右下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Left_Middle_Down_Right_Down_Left:// 左上、中间、右下、左下
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Up_Right_Middle_Down_Left_Down_Right:// 右上、中间、左下、右下
                return cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_Down_Left_Middle_Up_Right_Up_Left: // 左下、中间、右上、左上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right;
            case COOKER_ID_Down_Right_Middle_Up_Left_Up_Right: // 右下、中间、左上、右上
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_ALL_ALL_LEFT_NONE://全部 左边无区
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left;
            case COOKER_ID_ALL_ALL_NONE_RIGHT://全部 右边无区
                return cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_All://全部
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
            case COOKER_ID_ALL_ALL_ANY:    //全部 有区
                return cookerId == COOKER_ID_Up_Left
                        || cookerId == COOKER_ID_Down_Left
                        || cookerId == COOKER_ID_Middle
                        || cookerId == COOKER_ID_Up_Right
                        || cookerId == COOKER_ID_Down_Right;
        }
        return false;
    }

    protected int getHoodAutoShutdownDelay(int gear) {
        switch (gear) {
            case 6:
                return DURATION_FIVE_MINUTES + 5 * DURATION_TWO_MINUTES;
            case 5:
                return DURATION_FIVE_MINUTES + 4 * DURATION_TWO_MINUTES;
            case 4:
                return DURATION_FIVE_MINUTES + 3 * DURATION_TWO_MINUTES;
            case 3:
                return DURATION_FIVE_MINUTES + 2 * DURATION_TWO_MINUTES;
            case 2:
                return DURATION_FIVE_MINUTES + DURATION_TWO_MINUTES;
            case 1:
                return DURATION_FIVE_MINUTES;
        }
        return 0;
    }
    public void startHoodShutDownBlinkThread(int duration) {
        cancelHoodShutDownBlinkThread();
        hoodShutDownBlinkThread = new HoodShutDownBlinkThread(duration);
        hoodShutDownBlinkThread.start();
    }
    public void startHoodShutDownBlinkThread() {
        startHoodShutDownBlinkThread(DURATION_FIVE_MINUTES);
    }
    public void cancelHoodShutDownBlinkThread() {
        if (hoodShutDownBlinkThread != null) {
            if (hoodShutDownBlinkThread.isAlive()) {
                hoodShutDownBlinkThread.setCancelTask(true);
                try {
                    hoodShutDownBlinkThread.join(2000);
                } catch (InterruptedException e) {
                    Logger.getInstance().e(e);
                }
            }
            hoodShutDownBlinkThread = null;
        }
    }

    protected abstract int initLayout();
    protected abstract void initialize();
    protected abstract void handleMessage(Message msg);
    protected abstract void refreshTextWhenLanguageChanged(Locale locale);
    protected abstract void powerOffAllCookers();
    protected abstract void handleCookerErrors(int errorMessage, int cookerBits);
    protected abstract int getTotalPower();
    protected abstract void setAutoUI();
    public abstract int getCookersBits();
    public abstract boolean leftCookersUnited();
    public abstract boolean rightCookersUnited();

    public int getWorkMode() {
        return workMode;
    }
    public boolean isInHobStatus() {
        return workMode == WORK_MODE_HOB
                || workMode == WORK_MODE_SET_TIMER
                || workMode == WORK_MODE_SET_TIMER_MINUTE
                || workMode == WORK_MODE_SET_TIMER_HOUR;
    }
    public boolean isInHoodStatus() {
        return workMode == WORK_MODE_HOOD
                || workMode == WORK_MODE_SET_LIGHT;
    }

    public static class MessageHandler extends Handler {
        private final WeakReference<CookerPanelFragment> master;

        public MessageHandler(CookerPanelFragment master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
    protected class ActiveCookers extends ArrayList<Integer> {
        public void push(int cooker) {
            add(cooker);
        }

        public void pop(int cooker) {
            for (int i = 0; i < size(); i++) {
                if (get(i) == cooker) {
                    remove(i);
                    break;
                }
            }
        }

        public int pop() {
            if (size() > 0) {
                int cooker = get(size() - 1);
                remove(size() - 1);
                return cooker;
            }
            throw new EmptyStackException();
        }

        public void bringToTop(int cooker) {
            pop(cooker);
            add(cooker);
        }
    }
    protected class HoodShutDownBlinkThread extends BaseThread {

        // Constants
        private int BLINK_INTERVAL = 500;

        // Fields
        private int duration;
        private long startTime;
        private long lastBlinkTime;

        // Constructors
        public HoodShutDownBlinkThread(int duration) {
            this.duration = duration;
        }

        // Override functions
        @Override
        protected boolean started() {
            startTime = SystemClock.elapsedRealtime();
            lastBlinkTime = startTime;
            return true;
        }
        @Override
        protected boolean performTaskInLoop() {

            if (SystemClock.elapsedRealtime() - startTime > duration) {
                return false;
            }

            if (SystemClock.elapsedRealtime() - lastBlinkTime >= BLINK_INTERVAL) {
                lastBlinkTime = SystemClock.elapsedRealtime();
                Message msg = new Message();
                msg.what = HANDLER_HOOD_NUMBER_BLINK;
                msg.arg1 = 0;
                handler.sendMessage(msg);
            }

            return true;
        }
        @Override
        protected void finished() {
            Message msg = new Message();
            msg.what = HANDLER_HOOD_NUMBER_BLINK;
            msg.arg1 = 1;
            handler.sendMessage(msg);
        }
    }
}
