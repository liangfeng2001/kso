package com.ekek.tftcooker.model;

import com.ekek.tftcooker.event.ClearTouchEvent;
import com.ekek.tftcooker.event.TheFirstClickedCooker;
import com.ekek.tftcooker.event.TheLastClickedCooker;

import org.greenrobot.eventbus.EventBus;

public class HandlePatternLockView_60 extends  HandlePatternLockView{
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

    public  HandlePatternLockView_60(String iv){
        super(iv);
    }
    @Override
    public boolean HandleInputFromPatternLockView() {
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

            case "26":  // 右上、中间、左下
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Down_Left_Middle));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "62":
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
            case "062":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "260":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Up_Left_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
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
            case "280":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "082":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Up_Right_Down_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
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
            case "826":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "628":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Ddwn_Right_Down_Left));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0367852": //左上、左下、右下、右上 无区
            case "0682":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "2860":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0628":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8260":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2806":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6082":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6028":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8206":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Down_Left_Down_Right_Up_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;

            case "2680":// 全部，有区
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "0862":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "0268":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "8620":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2086":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "6802":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //     IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6208":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "8026":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_ANY));
                //    IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;

            case "2608":// 全部，左边无区 =38;
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "8062":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "8602":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "2068":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_LEFT_NONE));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;

            case "048526": // =39; // 全部，右边无区
            case "0826":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "6280":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "6820":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "0286":
                EventBus.getDefault().post(new ClearTouchEvent(ALL_ALL_NONE_RIGHT));
                // IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "04876": // 左上、中间、右下、左下
            case "086":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "67840":
            case "680":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Left_Middle_Down_Right_Down_Left));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "24678": // 右上、中间、左下、右下
            case "268":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
            case "87642":
            case "862":
                EventBus.getDefault().post(new ClearTouchEvent(Up_Right_Middle_Down_Left_Down_Right));
                //   IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "64210":// 左下、中间、右上、左上
            case "620":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Left));
                break;
            case "01246":
            case "026":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Left_Middle_Up_Right_Up_Left));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Left));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Left));
                break;
            case "84012":// 右下、中间、左上、右上
            case "802":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Up_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Down_Right));
                break;
            case "21048":
            case "208":
                EventBus.getDefault().post(new ClearTouchEvent(Down_Right_Middle_Up_Left_Up_Right));
                //  IsLinkMode = true;
                EventBus.getDefault().post(new TheLastClickedCooker(COOKER_ID_Down_Right));
                EventBus.getDefault().post(new TheFirstClickedCooker(COOKER_ID_Up_Right));
                break;
        }
        return true;
    }
}
