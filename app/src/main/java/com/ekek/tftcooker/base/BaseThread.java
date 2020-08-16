package com.ekek.tftcooker.base;

import com.ekek.tftcooker.common.Logger;

public abstract class BaseThread extends Thread {

    // Fields
    private boolean cancelTask = false;
    protected int userParamInt;
    protected Object userParamObj;

    // Abstract functions
    protected abstract boolean started();
    protected abstract boolean performTaskInLoop();
    protected abstract void finished();

    // Constructors
    public BaseThread() {
        this(0, null);
    }
    public BaseThread(int userParamInt, Object userParamObj) {
        this.userParamInt = userParamInt;
        this.userParamObj = userParamObj;
    }

    // Override functions
    @Override
    public void run() {
        try {
            if (!started()) {
                return;
            }
            while (!cancelTask) {
                if (!performTaskInLoop()) {
                    break;
                }
                Thread.sleep(50);
            }
            finished();
        } catch (InterruptedException e) {
            Logger.getInstance().e(e.getMessage());
        }
    }

    // Public functions
    public static void cancel(BaseThread thread) {
        if (thread != null) {
            if (thread.isAlive()) {
                thread.setCancelTask(true);
                try {
                    thread.join(2000);
                } catch (InterruptedException e) {
                    Logger.getInstance().e(e);
                }
            }
        }
    }

    // Properties
    public void setCancelTask(boolean cancelTask) {
        this.cancelTask = cancelTask;
    }
}
