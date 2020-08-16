package com.ekek.tftcooker.event;

import android.content.Context;

import com.ekek.tftcooker.R;

public class ShowErrorOrder extends Order {

    public static final int ORDER_NO_ERROR = 0;
    public static final int ORDER_E1 = 1;
    public static final int ORDER_E2 = 2;
    public static final int ORDER_E3 = 3;
    public static final int ORDER_E4 = 4;
    public static final int ORDER_E5 = 5;
    public static final int ORDER_F1 = 6;
    public static final int ORDER_F3 = 7;
    public static final int ORDER_F4 = 8;
    public static final int ORDER_ER03 = 9;

    private String errorCookers;

    public ShowErrorOrder(int order) {
        super(order);
        errorCookers = "";
    }
    public ShowErrorOrder(int order, String errorCookers) {
        super(order);
        this.errorCookers = errorCookers;
    }

    public String getErrorTitle(Context context) {
        switch (order) {
            case ORDER_E1:
                return String.format(
                        context.getResources().getString(R.string.err_e1_title),
                        errorCookers);
            case ORDER_E2:
                return String.format(
                        context.getResources().getString(R.string.err_e2_title),
                        errorCookers);
            case ORDER_E3:
                return String.format(
                        context.getResources().getString(R.string.err_e3_title),
                        errorCookers);
            case ORDER_E4:
                return String.format(
                        context.getResources().getString(R.string.err_e4_title),
                        errorCookers);
            case ORDER_E5:
                return String.format(
                        context.getResources().getString(R.string.err_e5_title),
                        errorCookers);
            case ORDER_F1:
                return String.format(
                        context.getResources().getString(R.string.err_f1_title),
                        errorCookers);
            case ORDER_F3:
                return String.format(
                        context.getResources().getString(R.string.err_f3_title),
                        errorCookers);
            case ORDER_F4:
                return String.format(
                        context.getResources().getString(R.string.err_f4_title),
                        errorCookers);
            case ORDER_ER03:
                return context.getResources().getString(R.string.err_er03_title);
            default:
                return "";
        }
    }
    public String getErrorContent(Context context) {
        switch (order) {
            case ORDER_E1:
                return context.getResources().getString(R.string.err_e1_content);
            case ORDER_E2:
                return context.getResources().getString(R.string.err_e2_content);
            case ORDER_E3:
                return context.getResources().getString(R.string.err_e3_content);
            case ORDER_E4:
                return context.getResources().getString(R.string.err_e4_content);
            case ORDER_E5:
                return context.getResources().getString(R.string.err_e5_content);
            case ORDER_F1:
                return context.getResources().getString(R.string.err_f1_content);
            case ORDER_F3:
                return context.getResources().getString(R.string.err_f3_content);
            case ORDER_F4:
                return context.getResources().getString(R.string.err_f4_content);
            case ORDER_ER03:
                return context.getResources().getString(R.string.err_er03_content);
            default:
                return "";
        }
    }
}
