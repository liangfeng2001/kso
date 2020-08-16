package com.ekek.tftcooker.event;

public class TheLastClickedCooker {
    private int order=0;
    public TheLastClickedCooker(int d){
        order =d;
    }
    public int getOrder(){
        return order ;
    }
}
