package com.ekek.tftcooker.event;

public class ClearTouchEvent {
    private int order=0;
    public ClearTouchEvent(int d){
        order =d;
    }
    public int getOrder(){
        return order ;
    }
}
