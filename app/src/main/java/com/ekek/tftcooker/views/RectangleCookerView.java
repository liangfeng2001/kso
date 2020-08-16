package com.ekek.tftcooker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ekek.tftcooker.R;

/**
 * Created by Samhung on 2018/4/14.
 */

public class RectangleCookerView extends CookerView {

    public static final int SHAPE_TOP_ROUNDED = 0;
    public static final int SHAPE_BOTTOM_ROUNDED = 1;

    private int bgShape;

    public RectangleCookerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RectangleCookerView);
        bgShape = typedArray.getInt(R.styleable.RectangleCookerView_b_shape, 0);
    }

    @Override
    protected void updateUI() {
        super.updateUI();
        switch (cookerStatus) {
            case COOKER_STATUS_CLOSE:
                SetTvGearVisible(false);
                break;
        }
    }

    @Override
    protected void doSetBackGroundNone() {
        llCircularView.setBackground(null);
    }

    @Override
    protected void doSetBackGroundGray() {  // 设置背景是 灰色
        if (bgShape == SHAPE_TOP_ROUNDED) {
            llCircularView.setBackground(getResources().getDrawable(R.mipmap.rect_cooker_background_top_rounded_gray));
        } else if (bgShape == SHAPE_BOTTOM_ROUNDED) {
            llCircularView.setBackground(getResources().getDrawable(R.mipmap.rect_cooker_background_bottom_rounded_gray));
        }
    }

    @Override
    protected void doSetBackGroundBlue() {
        if (bgShape == SHAPE_TOP_ROUNDED) {
            llCircularView.setBackground(getResources().getDrawable(R.mipmap.rect_cooker_background_top_rounded_blue));
        } else if (bgShape == SHAPE_BOTTOM_ROUNDED) {
            llCircularView.setBackground(getResources().getDrawable(R.mipmap.rect_cooker_background_bottom_rounded_blue));
        }
    }
}