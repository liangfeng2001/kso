package com.ekek.tftcooker.common;

import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseThread;

public class CheckIdleThread extends BaseThread {

    // Constants
    public static final long DURATION_FIVE_MINUTES = 1000 * 60 * 5;

    // Fields
    private long duration;
    private CheckIdleThreadListener checkIdleThreadListener;

    // Constructors
    public CheckIdleThread(long duration) {
        this.duration = duration;
    }

    // Interfaces
    public interface CheckIdleThreadListener {
        void onIdle();
    }

    // Override parent functions
    @Override
    protected boolean started() {
        return true;
    }
    @Override
    protected boolean performTaskInLoop() {
        if (TFTCookerApplication.getInstance().checkNoTouch(duration)) {
            onIdle();
            return false;
        }
        return true;
    }
    @Override
    protected void finished() {

    }

    // Public functions
    public static CheckIdleThread start(CheckIdleThread thread, long duration) {
        cancel(thread);
        thread = new CheckIdleThread(duration);
        thread.start();
        return thread;
    }

    // Private functions
    private void onIdle() {
        if (checkIdleThreadListener != null) {
            checkIdleThreadListener.onIdle();
        }
    }

    // Properties
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public void setCheckIdleThreadListener(CheckIdleThreadListener checkIdleThreadListener) {
        this.checkIdleThreadListener = checkIdleThreadListener;
    }
}
