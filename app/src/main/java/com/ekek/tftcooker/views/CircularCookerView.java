package com.ekek.tftcooker.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ekek.tftcooker.R;

/**
 * Created by Samhung on 2018/4/14.
 */
public class CircularCookerView extends CookerView {

    public CircularCookerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void doSetBackGroundNone() {
        llCircularView.setBackground(getResources().getDrawable(R.drawable.shape_circular_corner_black));
    }

    @Override
    protected void doSetBackGroundGray() {
        llCircularView.setBackground(getResources().getDrawable(R.drawable.shape_circular_corner_gray));
    }

    @Override
    protected void doSetBackGroundBlue() {
        llCircularView.setBackground(getResources().getDrawable(R.drawable.shape_circular_corner_blue));
    }
}
