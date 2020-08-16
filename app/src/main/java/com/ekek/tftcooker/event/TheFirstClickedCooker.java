package com.ekek.tftcooker.event;

public class TheFirstClickedCooker {
    private int order=0;
    public TheFirstClickedCooker(int d){
        order =d;
    }
    public int getOrder(){
        return order ;
    }
}
