package com.ekek.tftcooker.event;

public class HoodConnectStatusChangedEvent {

    private int hoodConnectStatus;

    public HoodConnectStatusChangedEvent(int hoodConnectStatus) {
        this.hoodConnectStatus = hoodConnectStatus;
    }

    public int getHoodConnectStatus() {
        return hoodConnectStatus;
    }

    public void setHoodConnectStatus(int hoodConnectStatus) {
        this.hoodConnectStatus = hoodConnectStatus;
    }
}
