package com.ekek.tftcooker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ekek.tftcooker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;

public class ToggleButtonView extends LinearLayout implements GestureDetector.OnGestureListener {

    // Widgets
    Unbinder unbinder;
    private View rootView;
    @BindView(R.id.ivOn)
    ImageView ivOn;
    @BindView(R.id.ivOff)
    ImageView ivOff;

    // Constants
    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 200;

    // Fields
    private Context context;
    private MyOnClickListener onClickListener;
    private GestureDetector gestureDetector;
    private OnToggleListener listener;
    private boolean switchedOn;

    // Constructors
    public ToggleButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.view_toggle_button, this);
        unbinder = ButterKnife.bind(rootView);
        initialize(attrs);
    }

    // Interfaces
    public interface OnToggleListener {
        void onToggle(ToggleButtonView sender, boolean on);
    }

    // Override functions
    @OnTouch({R.id.ivOn, R.id.ivOff})
    public boolean onTouch(View view, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    // Functions for GestureDetector.OnGestureListener
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(velocityX) < FLING_MIN_VELOCITY) {
            return false;
        }

        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {
            ivOn_Click();
            return true;
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE) {
            ivOff_Click();
            return true;
        }
        return false;
    }

    // Private functions
    private void initialize(@Nullable AttributeSet attrs) {

        initFields();
        initWidgets(attrs);
    }
    private void initFields() {
        onClickListener = new MyOnClickListener();
        gestureDetector = new GestureDetector(context, this);
    }
    private void initWidgets(@Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleButtonView);
        int clickOnImage = typedArray.getInt(R.styleable.ToggleButtonView_image_on, R.mipmap.click_on);
        int clickOffImage = typedArray.getInt(R.styleable.ToggleButtonView_image_off, R.mipmap.click_off);
        boolean checked = typedArray.getBoolean(R.styleable.ToggleButtonView_checked, false);
        typedArray.recycle();

        ivOn.setImageResource(clickOnImage);
        ivOff.setImageResource(clickOffImage);

        ivOn.setOnClickListener(onClickListener);
        ivOff.setOnClickListener(onClickListener);

        toggle(checked);
    }
    private void ivOn_Click() {
        toggle(false);
        if (listener != null) {
            listener.onToggle(this,false);
        }
    }
    private void ivOff_Click() {
        toggle(true);
        if (listener != null) {
            listener.onToggle(this,true);
        }
    }
    private void toggle(boolean on) {
        ivOn.setVisibility(on ? VISIBLE: GONE);
        ivOff.setVisibility(on ? GONE: VISIBLE);
    }

    // Properties
    public void setListener(OnToggleListener listener) {
        this.listener = listener;
    }
    public boolean isSwitchedOn() {
        return switchedOn;
    }
    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn = switchedOn;
        toggle(switchedOn);
    }

    // Classes
    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivOn:
                    ivOn_Click();
                    break;
                case R.id.ivOff:
                    ivOff_Click();
                    break;
            }
        }
    }
}
