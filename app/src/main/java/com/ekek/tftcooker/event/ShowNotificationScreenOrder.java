package com.ekek.tftcooker.event;

public class ShowNotificationScreenOrder extends Order {

    public static final int ORDER_SHOW_SCREEN = 1;

    private int message;
    private int lastSeconds;

    public ShowNotificationScreenOrder(int message, int lastSeconds) {
        super(ORDER_SHOW_SCREEN);
        this.message = message;
        this.lastSeconds = lastSeconds;
    }

    public int getMessage() {
        return message;
    }
    public int getLastSeconds() {
        return lastSeconds;
    }
}
