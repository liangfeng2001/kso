package com.ekek.tftcooker.event;

public class WorkModeChangedEvent {
    public static final int ORDER_CHANGED_TO_HOB = 0;
    public static final int ORDER_CHANGED_TO_HOOD = 1;

    private int order;
    public WorkModeChangedEvent(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }
}
