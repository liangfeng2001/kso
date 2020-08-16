package com.ekek.tftcooker.time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.constants.TFTCookerConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;

public class DigitalClockViewEx extends LinearLayout {

    private static final float DEFAULT_TIME_TEXT_SIZE = 160.0f;
    private static final float DEFAULT_DATE_TEXT_SIZE = 50.0f;

    private static final String ACTION_TIME_DATE = Intent.ACTION_TIME_TICK;
    private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
    private static final String ACTION_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;

    TextView tvTime;
    TextView tvDate;

    private float timeTextSize;
    private float dateTextSize;
    private int timeTextColor;
    private int dateTextColor;
    private String[] weeks, days, months;

    public DigitalClockViewEx(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createLayout(context, attrs);
        getAttrs(attrs);
        initStringResources();
        refreshUI();
        registerTimeReceiver();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unRegisterTimeReceiver();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_TIME_DATE)
                    || action.equals(ACTION_TIME_CHANGED)
                    || action.equals(ACTION_TIMEZONE_CHANGED))  {
                refreshUI();
            }
        }
    };

    private void createLayout(Context context, AttributeSet attrs) {

        View v = inflate(context,
                R.layout.view_digital_clock_view_ex, null);
        v.setLayoutParams(new LayoutParams(context, attrs));
        tvTime = (TextView)v.findViewById(R.id.tvTime);
        tvDate = (TextView)v.findViewById(R.id.tvDate);
        this.addView(v, 0);
    }

    private void getAttrs(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DigitalClockViewEx);
        timeTextSize = typedArray.getDimension(R.styleable.DigitalClockViewEx_TimeTextSize, DEFAULT_TIME_TEXT_SIZE);
        dateTextSize = typedArray.getDimension(R.styleable.DigitalClockViewEx_DateTextSize, DEFAULT_DATE_TEXT_SIZE);
        timeTextColor = typedArray.getColor(R.styleable.DigitalClockViewEx_TimeTextColor, Color.WHITE);
        dateTextColor = typedArray.getColor(R.styleable.DigitalClockViewEx_DateTextColor, Color.WHITE);
        typedArray.recycle();

        Typeface goodHome = Typeface.createFromAsset(
                getResources().getAssets(),
                TFTCookerConstant.FONT_FILE_GOOD_HOME_LIGHT);
        tvTime.setTextSize(timeTextSize);
        tvTime.setTextColor(timeTextColor);
        tvTime.setTypeface(goodHome);
        tvTime.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        tvDate.setTextSize(dateTextSize);
        tvDate.setTextColor(dateTextColor);
        tvDate.setTypeface(goodHome);
        tvDate.setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }
    private void initStringResources() {
        Resources res = getResources();
        weeks = res.getStringArray(R.array.viewmodule_week);
        days = res.getStringArray(R.array.viewmodule_day);
        months = res.getStringArray(R.array.viewmodule_month);
    }

    private void registerTimeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIME_DATE);
        filter.addAction(ACTION_TIME_CHANGED);
        filter.addAction(ACTION_TIMEZONE_CHANGED);
        getContext().registerReceiver(receiver, filter);
    }

    private void unRegisterTimeReceiver() {
        getContext().unregisterReceiver(receiver);
    }

    private void refreshUI() {
        Calendar c = Calendar.getInstance();
        tvTime.setText(getTime(c));
        tvDate.setText(getDate(c));

    }
    private String getTime(Calendar c) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String timeStr = format.format(c.getTime());
        return timeStr;

    }

    private Spanned getDate(Calendar c) {
        String week = weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
        String day = days[c.get(Calendar.DAY_OF_MONTH) - 1];
        String month = months[c.get(Calendar.MONTH)];
        String dateStr = week + " " + day + " " + month;

        return Html.fromHtml(dateStr);
    }

    public void refreshText() {
        initStringResources();
        refreshUI();
    }
}
