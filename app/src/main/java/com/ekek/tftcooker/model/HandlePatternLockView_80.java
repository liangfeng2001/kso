package com.ekek.tftcooker.model;

import com.ekek.tftcooker.event.ClearTouchEvent;
import com.ekek.tftcooker.event.TheFirstClickedCooker;
import com.ekek.tftcooker.event.TheLastClickedCooker;

import org.greenrobot.eventbus.EventBus;

public class HandlePatternLockView_80 extends  HandlePatternLockView{
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

    public  HandlePatternLockView_80(String iv){
        super(iv);
    }
    @Override
    public boolean HandleInputFromPatternLockView() {
        switch (inputValue) {
            case "06":// 左下 左上
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Left)); // 告知  ,已经选择了  up_left 和 down_left
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "60":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_And_Down_Left)); // 告知  ,已经选择了  up_left 和 down_left
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;

            case "18":// 右上 右下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Right));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;

            case "81":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Down_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;

            case "01":// 左上 右上 滑动
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Up_Left_Slip)); // 右上、左上  滑动
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "10":
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_And_Up_Left_Slip));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "68":// 左下 右下   滑动
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_And_Down_Left_Slip)); // 右下、左下  滑动
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "86":
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_And_Down_Left_Slip));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;

            case "16":  // 右上、中间、左下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Down_Left_Middle));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "61":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Down_Left_Middle));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "08":// 左上、中间、右下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Right_Middle));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "80":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Right_Middle));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "106": // 左上、左下、右上
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "601":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "061":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "160":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "068":// 左上、左下、右下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "860":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "806":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "608":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "810":// 右上、右下、左上
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "018":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "180":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "081":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "186":// 右上、右下、左下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left)); // 右上、右下、左下
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "681":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left)); // 右上、右下、左下
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "816":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "618":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0681"://左上、左下、右下、右上 无区
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "1860":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0618":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8160":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "1806":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6081":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6018":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8106":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;

            case "1680":// 全部，有区
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0861":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "0168":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8610":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "1086":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6801":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //     IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6108":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8016":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;

            case "1608":// 全部，左边无区 =38;
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "8061":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "8601":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "1068":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;

            case "0816":// 全部，右边无区
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6180":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6810":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0186":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "086":// 左上、中间、右下、左下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "680":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "168":// 右上、中间、左下、右下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "861":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "610":// 左下、中间、右上、左上
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "016":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "801":// 右下、中间、左上、右上
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "108":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
        }
        return true;
    }
}
