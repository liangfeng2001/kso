package com.ekek.tftcooker.model;

import com.ekek.tftcooker.event.ClearTouchEvent;
import com.ekek.tftcooker.event.TheFirstClickedCooker;
import com.ekek.tftcooker.event.TheLastClickedCooker;

import org.greenrobot.eventbus.EventBus;

public class HandlePatternLockView_90 extends HandlePatternLockView {
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

    public HandlePatternLockView_90(String iv) {
        super(iv);
    }

    @Override
    public boolean  HandleInputFromPatternLockView(){

        switch (inputValue) {
            case "036":  // 左下 左上
            case "06":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Left)); // 告知  ,已经选择了  up_left 和 down_left
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "630":
            case "60":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Left)); // 告知  ,已经选择了  up_left 和 down_left
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "258":  // 右上 右下
            case "28":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Right));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "852":
            case "82":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "034":
            case "014":
            case "04": // 左上、中间
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left__Middle_Slip)); // 左上、中间 滑动
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "410":
            case "430":
            case "40":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left__Middle_Slip));// 左上、中间 滑动
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "46": // 左下、中间
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Slip)); // 左下、中间 滑动
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "64":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Slip));// 左下、中间 滑动
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "24": // 右上、中间
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Slip));  // 右上、中间  滑动
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "42":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Slip));// 右上、中间  滑动
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "48":// 右下、中间
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Slip)); // 右下、中间  滑动
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "84":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Slip));// 右下、中间  滑动
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "012":// 左上 右上 滑动
            case "02":
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Up_Left_Slip)); // 右上、左上  滑动
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "210":
            case "20":
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Up_Left_Slip));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "678":// 左下 右下   滑动
            case "68":
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_And_Down_Left_Slip)); // 右下、左下  滑动
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "876":
            case "86":
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_And_Down_Left_Slip));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "046":// 左上、中间、左下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Midlle_Down_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "640":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Midlle_Down_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0364":  // 左上、左下、中间
            case "064":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Middle));// 左上、左下、中间
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6304":
            case "604":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "4036":
            case "406":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Middle));// 左上、左下、中间
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "4630":
            case "460":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Middle));// 左上、左下、中间
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "248": // 右上、中间、右下
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Right)); // 已经选择了  up_right , down_right ,middle 等 3个框
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "842":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Right)); // 已经选择了  up_right , down_right ,middle 等 3个框
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "8524":// 右上、右下、中间
            case "824":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle)); // 已经选择了  up_right , down_right ,middle 等 3个框
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "4258":
            case "428":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle)); // 已经选择了  up_right , down_right ,middle 等 3个框
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "2584":
            case "284":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle)); // 已经选择了  up_right , down_right ,middle 等 3个框
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "4852":
            case "482":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle)); // 已经选择了  up_right , down_right ,middle 等 3个框
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "4012": // 左上、中间、右上
            case "402":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Middle));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "4210":
            case "420":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Middle));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "0124":
            case "024":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Middle));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "2104":
            case "204":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "042":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "240":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "648": // 左下、中间、右下
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Down_Right_Middle));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "846":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Down_Right_Middle));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "4678":
            case "468":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Down_Right_Middle));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "4876":
            case "486":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Down_Right_Middle));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "8764":
            case "864":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Down_Right_Middle));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "6784":
            case "684":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Down_Right_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "246":  // 右上、中间、左下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Down_Left_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "642":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Down_Left_Middle));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "048":// 左上、中间、右下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Right_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "840":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Right_Middle));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "421036": // 左下、左上、中间、右上
            case "4206":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "630124":
            case "6024":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "03642":
            case "0642":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "63042":
            case "6042":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "24630":
            case "2460":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "24036":
            case "2406":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "463012":
            case "4602":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "210364":
            case "2064":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Up_Right));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "403678": // 左下、左上、中间、右下
            case "4068":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "876304":
            case "8604":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "84036":
            case "8406":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "63048":
            case "6048":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "03648":
            case "0648":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "84630":
            case "8460":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "487630":
            case "4860":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "036784":
            case "0684":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Up_Left_Middle_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "467852":// 右下、右上、中间、左下
            case "4682":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "678524":
            case "6824":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "258764":
            case "2864":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "425876":
            case "4286":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
           //     IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "85246":
            case "8246":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "64258":
            case "6428":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "25846":
            case "2846":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "64852":
            case "6482":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Down_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "485210":  // 右下、右上、中间、左上
            case "4820":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "852104":
            case "8204":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "012584":
            case "0284":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "401258":
            case "4028":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "04258":
            case "0428":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "85240":
            case "8240":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "04852":
            case "0482":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "25840":
            case "2840":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Up_Right_Middle_Up_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "21036":  // 左上、左下、右上
            case "206":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "63012":
            case "602":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "03678": // 左上、左下、右下
            case "068":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "87630":
            case "860":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "85210":  // 右上、右下、左上
            case "820":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "01258":
            case "028":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "25876":// 右上、右下、左下
            case "286":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left)); // 右上、右下、左下
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "67852":
            case "682":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left)); // 右上、右下、左下
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0367852": //左上、左下、右下、右上 无区
            case "0682":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "2587630":
            case "2860":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6301258":
            case "6028":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8521036":
            case "8206":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "0125876"://左上、右上、右下、左下
            case "0286":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right_Down_Left)); //左上、右上、右下、左下
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6785210":
            case "6820":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right_Down_Left)); //左上、右上、右下、左下
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "2103678"://右上、左上、左下、右下
            case "2068":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left_Down_Right)); //右上、左上、左下、右下
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "8763012":
            case "8602":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left_Down_Right)); //右上、左上、左下、右下
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "6304852": // 全部  无区
            case "60482":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
             //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "2584036":
            case "28406":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0364258":
            case "06428":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8524630":
            case "82460":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2584630":
            case "28460":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0364852":
            case "06482":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "40367852":
            case "40682":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "25876304":
            case "28604":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "48521036":
            case "48206":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "63012584":
            case "60284":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
           //     IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6304258":
            case "60428":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8524036":
            case "82406":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "46301258":
            case "46028":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "85210364":
            case "82064":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "42587630":
            case "42860":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "03678524":
            case "06824":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "246780":  // 全部，有区
            case "24680":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "048762":
            case "04862":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "0124678":
            case "02468":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8764210":
            case "86420":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2104876":
            case "20486":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6784012":
            case "68402":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
           //     IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "642108":
            case "64208":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "840126":
            case "84026":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2104678":
            case "20468":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "8764012":
            case "86402":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "0124876":
            case "02486":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6784210":
            case "68420":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "246308": // 全部，左边无区 =38;
            case "24608":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "840362":
            case "84062":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "48763012":
            case "48602":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "21036784":
            case "20684":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6301248":
            case "60248":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8421036":
            case "84206":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "0367842":
            case "06842":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "2487630":
            case "24860":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "2103648":
            case "20648":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "8463012":
            case "84602":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "8763042":
            case "86042":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2403678":
            case "24068":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "42103678":
            case "42068":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "87630124":
            case "86024":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "048526": // =39; // 全部，右边无区
            case "04826":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "642580":
            case "64280":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "46785210":
            case "46820":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "01258764":
            case "02864":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6401258":
            case "64028":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8521046":
            case "82046":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2587640":
            case "28640":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0467852":
            case "04682":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "0125846":
            case "02846":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6485210":
            case "64820":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "40125876":
            case "40286":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Middle));
                break;
            case "67852104":
            case "68204":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Middle));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6785240":
            case "68240":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
            //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0425876":
            case "04286":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "04876": // 左上、中间、右下、左下
            case "0486":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8640":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "0468":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "67840":
            case "6840":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "24678": // 右上、中间、左下、右下
            case "2468":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6842":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "2486":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "87642":
            case "8642":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
             //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "64210":// 左下、中间、右上、左上
            case "6420":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "01246":
            case "0246":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "2046":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
               // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6402":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "84012":// 右下、中间、左上、右上
            case "8402":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "21048":
            case "2048":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
              //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "8420":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "0248":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
        }



        return true;
    }
}
