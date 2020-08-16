package com.ekek.tftcooker.event;

public class CookerValueChanged extends Order {

    public static final int ORDER_COOKER_VALUE_CHANGED = 0;

    public CookerValueChanged(int order) {
        super(order);
    }
}
