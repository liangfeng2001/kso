package com.ekek.tftcooker.event;

public class PowerSwitchEvent {

    private boolean on;

    public PowerSwitchEvent(boolean on) {
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }
    public void setOn(boolean on) {
        this.on = on;
    }
}
