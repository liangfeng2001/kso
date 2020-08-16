package com.ekek.tftcooker.event;

public class PowerOffAllOrder extends Order {
    public static final int ORDER_POWER_OFF = 0;

    public PowerOffAllOrder(int order) {
        super(order);
    }
}
