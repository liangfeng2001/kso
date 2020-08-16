package com.ekek.tftcooker.event;

public class ShowTimerCompleteOrder {

    public static final int ORDER_DISP_TIMER_COMPLETE_PANEL = 1;
    public static final int ORDER_DO_UNLOCK = 2;
    public static final int ORDER_NO_STAND_BY = 3;
    public static final int ORDER_RESUME_STAND_BY = 4;
    public static final int ORDER_DO_PAUSE = 5;
    public static final int ORDER_DO_LOCK = 6;
    public static final int ORDER_PAUSE_TIMEOUT = 11;

    private int order = 0;

    public ShowTimerCompleteOrder(int d) {
        order = d;
    }

    public int getOrder() {
        return order;
    }
}
