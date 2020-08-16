package com.ekek.tftcooker.event;

public class Order {
    protected int order;
    protected int paramW = -1;
    protected int paramL = -1;

    public Order(int order) {
        this.order = order;
    }

    public int getOrder(){
        return order ;
    }
    public int getParamW() {
        return paramW;
    }
    public void setParamW(int paramW) {
        this.paramW = paramW;
    }
    public int getParamL() {
        return paramL;
    }
    public void setParamL(int paramL) {
        this.paramL = paramL;
    }
}
