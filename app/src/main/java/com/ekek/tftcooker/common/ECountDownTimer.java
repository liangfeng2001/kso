package com.ekek.tftcooker.common;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

public abstract class ECountDownTimer {
    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;
    private long mStartTime;
    private long loop = 0;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public ECountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final ECountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStartTime = SystemClock.elapsedRealtime();
        mStopTimeInFuture = mStartTime + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    private Handler mHandler = new MessageHandler(this);

    private static class MessageHandler extends Handler {
        private final WeakReference<ECountDownTimer> master;
        private MessageHandler(ECountDownTimer master) {
            this.master = new WeakReference<>(master);
        }
        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
    private void handleMessage(Message msg) {
        synchronized (ECountDownTimer.this) {
            if (mCancelled) {
                return;
            }

            final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

            if (millisLeft <= 0) {
                onFinish();
            } else {
                onTick(millisLeft);
                loop++;
                long targetDuration = mStartTime + mCountdownInterval * loop;
                long delay = targetDuration - SystemClock.elapsedRealtime();

                if (millisLeft < mCountdownInterval) {
                    // special case: user's onTick took more than interval to
                    // complete, trigger onFinish without delay
                    if (delay < 0) delay = 0;
                } else {
                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;
                }

                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG), delay);
            }
        }
    }
}
