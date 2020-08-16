package com.ekek.tftcooker.event;

public class SendPauseClickOrder {
    private int order=0;
    public SendPauseClickOrder(int d){
        order =d;
    }
    public int getOrder(){
        return order ;
    }
}
