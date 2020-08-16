package com.ekek.tftcooker.model;

public abstract class HandlePatternLockView {

    protected String inputValue ;

    public HandlePatternLockView(String iv) {
        inputValue = iv;
    }

    public abstract boolean HandleInputFromPatternLockView();
}
