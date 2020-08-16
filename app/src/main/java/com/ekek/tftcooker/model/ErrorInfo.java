package com.ekek.tftcooker.model;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.constants.TFTCookerConstant;

public  class ErrorInfo {

    public ErrorInfo() {

    }
    public int getTitle(int ev) {
        int reture_value=0;
        switch (ev){
            case TFTCookerConstant.ER03:
                reture_value= R.string.err_er03_title;
                break;
            case TFTCookerConstant.E1:
                reture_value= R.string.err_e1_title;
                break;
            case TFTCookerConstant.E2:
                reture_value= R.string.err_e2_title;
                break;
            case TFTCookerConstant.E3:
                reture_value= R.string.err_e3_title;
                break;
            case TFTCookerConstant.E4:
                reture_value= R.string.err_e4_title;
                break;
            case TFTCookerConstant.E5:
                reture_value= R.string.err_e5_title;
                break;
            case TFTCookerConstant.F1:
                reture_value= R.string.err_f1_title;
                break;
            case TFTCookerConstant.F3:
                reture_value= R.string.err_f3_title;
                break;
            case TFTCookerConstant.F4:
                reture_value= R.string.err_f4_title;
                break;
        }
        return reture_value;
    }

    public int getContent(int ev) {
        int reture_value=0;
        switch (ev){
            case TFTCookerConstant.ER03:
                reture_value= R.string.err_er03_content;
                break;
            case TFTCookerConstant.E1:
                reture_value= R.string.err_e1_content;
                break;
            case TFTCookerConstant.E2:
                reture_value= R.string.err_e2_content;
                break;
            case TFTCookerConstant.E3:
                reture_value= R.string.err_e3_content;
                break;
            case TFTCookerConstant.E4:
                reture_value= R.string.err_e4_content;
                break;
            case TFTCookerConstant.E5:
                reture_value= R.string.err_e5_content;
                break;
            case TFTCookerConstant.F1:
                reture_value= R.string.err_f1_content;
                break;
            case TFTCookerConstant.F3:
                reture_value= R.string.err_f3_content;
                break;
            case TFTCookerConstant.F4:
                reture_value= R.string.err_f4_content;
                break;
        }
        return reture_value;
    }
}
