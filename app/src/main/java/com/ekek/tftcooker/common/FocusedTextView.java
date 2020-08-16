package com.ekek.tftcooker.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

//import com.hoob.xlw.ovenpanel.constants.TFTOvenConstant;
//import com.hoob.xlw.ovenpanel.database.SettingPreferencesUtil;

public class FocusedTextView extends AppCompatTextView {

    public FocusedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        return result;
    }

    @Override
    public boolean performClick() {
        boolean result = super.performClick();
        return result;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
/*        String aLanguage= SettingPreferencesUtil.getDefaultLanguage(getContext());
        switch (aLanguage) {
            case TFTOvenConstant.LANGUAGE_MAGYAR:  // 马札儿语
                setMeasuredDimension(width + 10, height);
                break;
        }*/
    }
}
