package com.ekek.tftcooker.time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.ekek.tftcooker.R;
import com.ekek.tftcooker.constants.TFTCookerConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DigitalClockView extends View {
    private static final int DEFAULT_TIME_TEXT_SIZE = 20;
    private static final int DEFAULT_DATE_TEXT_SIZE = 10;
    private static final boolean DEFAULT_DATE_VIEW_VISIBILITY = true;
    private static final String ACTION_TIME_DATE = Intent.ACTION_TIME_TICK;
    private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
    private static final String ACTION_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;
    private float timeTextSize,dateTextSize;
    private int timeTextColor,dateTextColor;
    private Paint timePaint,datePaint;
    private String[] weeks, days, months;
    private boolean isShowDate = DEFAULT_DATE_VIEW_VISIBILITY;
    private boolean isTimeReceiverRegister = false;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_TIME_DATE) || action.equals(ACTION_TIME_CHANGED) || action.equals(ACTION_TIMEZONE_CHANGED))  {
                notifyUpdateUI();
            }
        }
    };
    public DigitalClockView(Context context) {
        super(context);

    }

    public DigitalClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.viewmodule_DigitalClockView);
        timeTextSize = 160.0f;//a.getDimension(R.styleable.viewmodule_DigitalClockView_viewmodule_digitalTimeTextSize, DEFAULT_TIME_TEXT_SIZE);
        dateTextSize =50.0f;// a.getDimension(R.styleable.viewmodule_DigitalClockView_viewmodule_digitalDateTextSize, DEFAULT_DATE_TEXT_SIZE);
        timeTextColor = a.getColor(R.styleable.viewmodule_DigitalClockView_viewmodule_digitalTimeTextColor, Color.WHITE);
        dateTextColor = a.getColor(R.styleable.viewmodule_DigitalClockView_viewmodule_digitalDateTextColor, Color.WHITE);
        a.recycle();

        initResource();
        initPaint();
        registerTimeReceiver();
    }


    private void initPaint() {
        timePaint = new Paint();
        timePaint.setTextSize(timeTextSize);
        timePaint.setColor(timeTextColor);
        Typeface timeTypeface = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        timePaint.setTypeface(timeTypeface);
        timePaint.setTextAlign(Paint.Align.CENTER);
        timePaint.setAntiAlias(true);

        datePaint = new Paint();
        datePaint.setTextSize(dateTextSize);
        datePaint.setColor(dateTextColor);
        Typeface dateTypeface = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        datePaint.setTypeface(dateTypeface);
        datePaint.setTextAlign(Paint.Align.CENTER);
        datePaint.setAntiAlias(true);
    }

    private void initResource() {
        Resources res = getResources();
        weeks = res.getStringArray(R.array.viewmodule_week);
        days = res.getStringArray(R.array.viewmodule_day);
        months = res.getStringArray(R.array.viewmodule_month);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Calendar c = Calendar.getInstance();
        if (isShowDate) {
            canvas.drawText(getTime(c), getWidth() / 2  , getHeight() / 2 , timePaint);
        } else {
            canvas.drawText(getTime(c), getWidth() / 2  , getHeight() / 2 + 50, timePaint);
        }

        if (isShowDate) {
            canvas.drawText(getDate(c), getWidth() / 2  , getHeight() / 2 + 80, datePaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unRegisterTimeReceiver();
    }

    private String getTime(Calendar c) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String timeStr = format.format(c.getTime());
        return timeStr;

    }

    private String getDate(Calendar c) {
        String week = weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
        String day = days[c.get(Calendar.DAY_OF_MONTH) - 1];
        String month = months[c.get(Calendar.MONTH)];
        String dateStr = week + " " + day + " " + month;

        return dateStr;
    }

    private void notifyUpdateUI() {
        invalidate();
    }

    private void registerTimeReceiver() {
        if (isTimeReceiverRegister)return;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIME_DATE);
        filter.addAction(ACTION_TIME_CHANGED);
        filter.addAction(ACTION_TIMEZONE_CHANGED);
        getContext().registerReceiver(receiver, filter);
        isTimeReceiverRegister = true;

    }

    private void unRegisterTimeReceiver() {
        if (isTimeReceiverRegister) {
            getContext().unregisterReceiver(receiver);
            isTimeReceiverRegister = false;
        }
    }

    public void setDateVisibility(boolean isShow) {
        isShowDate = isShow;
        notifyUpdateUI();
    }

    public void refreshText() {
        initResource();
        invalidate();
    }
}
