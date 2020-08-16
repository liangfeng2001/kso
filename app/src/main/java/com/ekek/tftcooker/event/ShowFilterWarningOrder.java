package com.ekek.tftcooker.event;

public class ShowFilterWarningOrder extends Order {

    public static final int ORDER_CHANGE_CARBON = 1;
    public static final int ORDER_CLEAN_ALUMINIUM = 2;

    public ShowFilterWarningOrder(int order) {
        super(order);
    }
}
