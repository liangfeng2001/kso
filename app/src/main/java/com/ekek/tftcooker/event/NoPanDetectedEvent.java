package com.ekek.tftcooker.event;

public class NoPanDetectedEvent {

    public static final int EVENT_TYPE_SET_UI = 0;
    public static final int EVENT_TYPE_POWER_OFF = 1;
    public static final int EVENT_TYPE_PAN_IS_BACK = 2;

    private int cookerViewId;
    private int eventType;

    public NoPanDetectedEvent(int cookerViewId, int eventType) {
        this.cookerViewId = cookerViewId;
        this.eventType = eventType;
    }

    public int getCookerViewId() {
        return cookerViewId;
    }
    public int getEventType() {
        return eventType;
    }
}
