package com.ekek.tftcooker.event;

public class CookerHighTempOrder extends Order {
    public static final int ORDER_HIGH_TEMP = 0;

    public CookerHighTempOrder() {
        this(ORDER_HIGH_TEMP);
    }
    public CookerHighTempOrder(int order) {
        super(order);
    }
}
