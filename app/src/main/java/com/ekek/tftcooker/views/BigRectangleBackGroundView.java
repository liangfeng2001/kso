package com.ekek.tftcooker.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.ekek.tftcooker.R;

import butterknife.ButterKnife;

public class BigRectangleBackGroundView extends LinearLayout {

    public BigRectangleBackGroundView(Context context) {
        super(context);
    //    LayoutInflater.from(context).inflate(R.layout.layout_big_background_cooker_view, this);
    }
    public BigRectangleBackGroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_big_background_cooker_view, this);
        ButterKnife.bind(this, this);
    }

}
