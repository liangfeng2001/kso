package com.ekek.tftcooker.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.constants.CircleProgressConstant;
import com.ekek.tftcooker.utils.LogUtil;
import com.ekek.tftcooker.utils.MiscUtil;

/**
 * 圆形进度条，类似 QQ 健康中运动步数的 UI 控件
 * Created by littlejie on 2017/2/21.
 */

public class CircleProgress extends View {

    private static final String TAG = CircleProgress.class.getSimpleName();
    private Context mContext;

    //默认大小
    private int mDefaultSize;
    //是否开启抗锯齿
    private boolean antiAlias;
    //绘制提示
    private TextPaint mHintPaint;
    private CharSequence mHint;
    private int mHintColor;
    private float mHintSize;
    private float mHintOffset;

    //绘制单位
    private TextPaint mUnitPaint;
    private CharSequence mUnit;
    private int mUnitColor;
    private float mUnitSize;
    private float mUnitOffset;

    //绘制数值
    private TextPaint mValuePaint;
    private float mValue;
    private float mMinValue;
    private float mMaxValue;
    private float mMinSelectValue;
    private float mMaxSelectValue;
    private float mValueOffset;
    private int mPrecision;
    private String mPrecisionFormat;
    private int mValueColor;
    private float mValueSize;

    //绘制圆弧
    private Paint mArcPaint;
    private float mArcWidth;
    private float mStartAngle, mSweepAngle;
    private RectF mRectF;
    //渐变的颜色是360度，如果只显示270，那么则会缺失部分颜色
    private SweepGradient mSweepGradient;
    //private int[] mGradientColors = {Color.GREEN, Color.YELLOW, Color.RED};
    private int[] mGradientColors = {getResources().getColor(R.color.colorCircleProgress), getResources().getColor(R.color.colorCircleProgress), getResources().getColor(R.color.colorCircleProgress)};
    //当前进度，[0.0f,1.0f]
    private float mPercent;
    //动画时间
    private long mAnimTime;
    //属性动画
    private ValueAnimator mAnimator;

    //绘制背景圆弧
    private Paint mBgArcPaint;
    private int mBgArcColor;
    private float mBgArcWidth;

    //圆心坐标，半径
    private Point mCenterPoint;
    private float mRadius;
    private float mTextOffsetPercentInRadius;

    // 是否不进行Text的绘制
    private boolean mNoText;

    private OnCircleProgressListener mListener;

    private boolean canTouch = false;

    // 记录上次触屏时间，用于控制用户滑动手指时的行为
    private long mTouchTime = 0;
    // 在 touch 事件中，如果记录到的上次触屏时间距离当次小于下面这个值，
    // 我们就认为用户在滑动手指
    private static final int DURATION_TO_TELL_IF_SLIDE = 50;
    // 如果用户滑动手指时，超过这个角度，我们就可以判断，用户在0度和360度之间进行了切换
    private static final int ANGLE_DIFF = 20;

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mDefaultSize = MiscUtil.dipToPx(mContext, CircleProgressConstant.DEFAULT_SIZE);
        mAnimator = new ValueAnimator();
        mRectF = new RectF();
        mCenterPoint = new Point();
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        antiAlias = typedArray.getBoolean(R.styleable.CircleProgressBar_antiAlias, CircleProgressConstant.ANTI_ALIAS);

        mHint = typedArray.getString(R.styleable.CircleProgressBar_hint);
        mHintColor = typedArray.getColor(R.styleable.CircleProgressBar_hintColor, Color.BLACK);
        mHintSize = typedArray.getDimension(R.styleable.CircleProgressBar_hintSize, CircleProgressConstant.DEFAULT_HINT_SIZE);

        mValue = typedArray.getFloat(R.styleable.CircleProgressBar_value, CircleProgressConstant.DEFAULT_VALUE);
        mMinValue = typedArray.getFloat(R.styleable.CircleProgressBar_minValue, CircleProgressConstant.DEFAULT_MIN_VALUE);
        mMaxValue = typedArray.getFloat(R.styleable.CircleProgressBar_maxValue, CircleProgressConstant.DEFAULT_MAX_VALUE);
        mMinSelectValue = typedArray.getFloat(R.styleable.CircleProgressBar_minSelectValue, CircleProgressConstant.DEFAULT_MIN_SELECT_VALUE);
        mMaxSelectValue = typedArray.getFloat(R.styleable.CircleProgressBar_maxSelectValue, CircleProgressConstant.DEFAULT_MAX_SELECT_VALUE);

        //内容数值精度格式
        mPrecision = typedArray.getInt(R.styleable.CircleProgressBar_precision, 0);
        mPrecisionFormat = MiscUtil.getPrecisionFormat(mPrecision);
        mValueColor = typedArray.getColor(R.styleable.CircleProgressBar_valueColor, Color.BLACK);
        mValueSize = typedArray.getDimension(R.styleable.CircleProgressBar_valueSize, CircleProgressConstant.DEFAULT_VALUE_SIZE);

        mUnit = typedArray.getString(R.styleable.CircleProgressBar_unit);
        mUnitColor = typedArray.getColor(R.styleable.CircleProgressBar_unitColor, Color.BLACK);
        mUnitSize = typedArray.getDimension(R.styleable.CircleProgressBar_unitSize, CircleProgressConstant.DEFAULT_UNIT_SIZE);

        mArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_arcWidth, CircleProgressConstant.DEFAULT_ARC_WIDTH);
        mStartAngle = typedArray.getFloat(R.styleable.CircleProgressBar_startAngle, CircleProgressConstant.DEFAULT_START_ANGLE);
        mSweepAngle = typedArray.getFloat(R.styleable.CircleProgressBar_sweepAngle, CircleProgressConstant.DEFAULT_SWEEP_ANGLE);

        mBgArcColor = typedArray.getColor(R.styleable.CircleProgressBar_bgArcColor, Color.WHITE);
        mBgArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_bgArcWidth, CircleProgressConstant.DEFAULT_ARC_WIDTH);
        mTextOffsetPercentInRadius = typedArray.getFloat(R.styleable.CircleProgressBar_textOffsetPercentInRadius, 0.33f);

        //mPercent = typedArray.getFloat(R.styleable.CircleProgressBar_percent, 0);
        mAnimTime = typedArray.getInt(R.styleable.CircleProgressBar_animTime, CircleProgressConstant.DEFAULT_ANIM_TIME);

        mNoText = typedArray.getBoolean(R.styleable.CircleProgressBar_noText, false);

        int gradientArcColors = typedArray.getResourceId(R.styleable.CircleProgressBar_arcColors, 0);
        if (gradientArcColors != 0) {
            try {
                int[] gradientColors = getResources().getIntArray(gradientArcColors);
                if (gradientColors.length == 0) {//如果渐变色为数组为0，则尝试以单色读取色值
                    int color = getResources().getColor(gradientArcColors);
                    mGradientColors = new int[2];
                    mGradientColors[0] = color;
                    mGradientColors[1] = color;
                } else if (gradientColors.length == 1) {//如果渐变数组只有一种颜色，默认设为两种相同颜色
                    mGradientColors = new int[2];
                    mGradientColors[0] = gradientColors[0];
                    mGradientColors[1] = gradientColors[0];
                } else {
                    mGradientColors = gradientColors;
                }
            } catch (Resources.NotFoundException e) {
                throw new Resources.NotFoundException("the give resource not found.");
            }
        }

        typedArray.recycle();
    }

    private void initPaint() {
        mHintPaint = new TextPaint();
        // 设置抗锯齿,会消耗较大资源，绘制图形速度会变慢。
        mHintPaint.setAntiAlias(antiAlias);
        // 设置绘制文字大小
        mHintPaint.setTextSize(mHintSize);
        // 设置画笔颜色
        mHintPaint.setColor(mHintColor);
        // 从中间向两边绘制，不需要再次计算文字
        mHintPaint.setTextAlign(Paint.Align.CENTER);

        mValuePaint = new TextPaint();
        mValuePaint.setAntiAlias(antiAlias);
        mValuePaint.setTextSize(mValueSize);
        mValuePaint.setColor(mValueColor);
        // 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
        mValuePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mValuePaint.setTextAlign(Paint.Align.CENTER);

        mUnitPaint = new TextPaint();
        mUnitPaint.setAntiAlias(antiAlias);
        mUnitPaint.setTextSize(mUnitSize);
        mUnitPaint.setColor(mUnitColor);
        mUnitPaint.setTextAlign(Paint.Align.CENTER);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(antiAlias);
        mArcPaint.setColor(mGradientColors[0]);
        // 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
        mArcPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔粗细
        mArcPaint.setStrokeWidth(mArcWidth);
        // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
        // Cap.ROUND,或方形样式 Cap.SQUARE
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgArcPaint = new Paint();
        mBgArcPaint.setAntiAlias(antiAlias);
        mBgArcPaint.setColor(mBgArcColor);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mBgArcWidth);
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MiscUtil.measure(widthMeasureSpec, mDefaultSize),
                MiscUtil.measure(heightMeasureSpec, mDefaultSize));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //求圆弧和背景圆弧的最大宽度
        float maxArcWidth = Math.max(mArcWidth, mBgArcWidth);
        //求最小值作为实际值
        int minSize = Math.min(w - getPaddingLeft() - getPaddingRight() - 2 * (int) maxArcWidth,
                h - getPaddingTop() - getPaddingBottom() - 2 * (int) maxArcWidth);
        //减去圆弧的宽度，否则会造成部分圆弧绘制在外围
        mRadius = minSize / 2.0f;
        //获取圆的相关参数
        mCenterPoint.x = getPaddingLeft() + w / 2;
        mCenterPoint.y = getPaddingTop() + h / 2;
        //绘制圆弧的边界
        mRectF.left = mCenterPoint.x - mRadius - maxArcWidth / 2;
        mRectF.top = mCenterPoint.y - mRadius - maxArcWidth / 2;
        mRectF.right = mCenterPoint.x + mRadius + maxArcWidth / 2;
        mRectF.bottom = mCenterPoint.y + mRadius + maxArcWidth / 2;
        //计算文字绘制时的 baseline
        //由于文字的baseline、descent、ascent等属性只与textSize和typeface有关，所以此时可以直接计算
        //若value、hint、unit由同一个画笔绘制或者需要动态设置文字的大小，则需要在每次更新后再次计算
        mValueOffset = mCenterPoint.y + getBaselineOffsetFromY(mValuePaint);
        mHintOffset = mCenterPoint.y - mRadius * mTextOffsetPercentInRadius + getBaselineOffsetFromY(mHintPaint);
        mUnitOffset = mCenterPoint.y + mRadius * mTextOffsetPercentInRadius + getBaselineOffsetFromY(mUnitPaint);
        updateArcPaint();
    }

    private float getBaselineOffsetFromY(Paint paint) {
        return MiscUtil.measureTextHeight(paint) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawArc(canvas);
    }

    /**
     * 绘制内容文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        if (mNoText) return;

        // 计算文字宽度，由于Paint已设置为居中绘制，故此处不需要重新计算
        // float textWidth = mValuePaint.measureText(mValue.toString());
        // float x = mCenterPoint.x - textWidth / 2;
        canvas.drawText(String.format(mPrecisionFormat, mValue), mCenterPoint.x, mValueOffset, mValuePaint);

        if (mHint != null) {
            canvas.drawText(mHint.toString(), mCenterPoint.x, mHintOffset, mHintPaint);
        }

        if (mUnit != null) {
            canvas.drawText(mUnit.toString(), mCenterPoint.x, mUnitOffset, mUnitPaint);
        }
    }

    private void drawArc(Canvas canvas) {
        // 绘制背景圆弧
        // 从进度圆弧结束的地方开始重新绘制，优化性能
        canvas.save();
        float currentAngle = mSweepAngle * mPercent;
        canvas.rotate(mStartAngle, mCenterPoint.x, mCenterPoint.y);
        canvas.drawArc(mRectF, currentAngle, mSweepAngle - currentAngle + 2, false, mBgArcPaint);
        // 第一个参数 oval 为 RectF 类型，即圆弧显示区域
        // startAngle 和 sweepAngle  均为 float 类型，分别表示圆弧起始角度和圆弧度数
        // 3点钟方向为0度，顺时针递增
        // 如果 startAngle < 0 或者 > 360,则相当于 startAngle % 360
        // useCenter:如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
        canvas.drawArc(mRectF, 2, currentAngle, false, mArcPaint);
        canvas.restore();
    }

    /**
     * 更新圆弧画笔
     */
    private void updateArcPaint() {
        // 设置渐变
        //mSweepGradient = new SweepGradient(mCenterPoint.x, mCenterPoint.y, mGradientColors, null);
        //mArcPaint.setShader(mSweepGradient);
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public CharSequence getHint() {
        return mHint;
    }

    public void setHint(CharSequence hint) {
        mHint = hint;
    }

    public CharSequence getUnit() {
        return mUnit;
    }

    public void setUnit(CharSequence unit) {
        mUnit = unit;
    }

    public float getValue() {
        return mValue;
    }

    /**
     * 设置当前值
     *
     * @param value
     */
    public void setValue(float value) {
        doSetValue(value, false);
    }

    private void doSetValue(float value, boolean fromWidget) {
        if (value < mMinSelectValue) {
            value = mMinSelectValue;
        }
        if (value > mMaxSelectValue) {
            value = mMaxSelectValue;
        }
        LogUtil.d("the mMinSelectValue is "+mMinSelectValue+" the value is "+value);
        float start = mPercent;
        float end = (value - mMinValue) / (mMaxValue - mMinValue);
        if (mListener != null){
            mListener.onProgress(value, fromWidget);
        }
        //invalidate();
        startAnimator(start, end, mAnimTime);
    }

    private void startAnimator(float start, float end, long animTime) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.setDuration(animTime);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                mValue = mMinValue + mPercent * (mMaxValue - mMinValue);
                if (false) {
                    Log.d(TAG, "onAnimationUpdate: percent = " + mPercent
                            + ";currentAngle = " + (mSweepAngle * mPercent)
                            + ";value = " + mValue);
                }
                invalidate();
            }
        });
        mAnimator.start();
    }

    private int mPointAngle = 45;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canTouch) return true;

        /*获取点击位置的坐标*/
        float Action_x = event.getX();
        float Action_y = event.getY();

        if (!isPointValid(Action_x,Action_y , mCenterPoint.x , mCenterPoint.y)) {
            return true;
        }

        /*根据坐标转换成对应的角度*/
        float get_x0 = Action_x - mCenterPoint.x;
        float get_y0 = Action_y - mCenterPoint.y;

        /*01：左下角区域*/
        int pointAngle = 0;
        if(get_x0<=0&get_y0>=0){
            float tan_x = get_x0 * (-1);
            float tan_y = get_y0;
            double atan = Math.atan(tan_x / tan_y);
            pointAngle= (int) Math.toDegrees(atan)+180;
        }

        /*02：左上角区域*/
        if(get_x0<=0&get_y0<=0){
            float tan_x = get_x0 * (-1);
            float tan_y = get_y0*(-1);
            double atan = Math.atan(tan_y / tan_x);
            pointAngle= (int) Math.toDegrees(atan)+270;
        }

        /*03：右上角区域*/
        if(get_x0>=0&get_y0<=0){
            float tan_x = get_x0 ;
            float tan_y = get_y0*(-1);
            double atan = Math.atan(tan_x/ tan_y);
            pointAngle= (int) Math.toDegrees(atan);
        }

        /*04：右下角区域*/
        if(get_x0>=0&get_y0>=0){

            float tan_x = get_x0 ;
            float tan_y = get_y0;
            double atan = Math.atan(tan_y / tan_x);
            pointAngle= (int) Math.toDegrees(atan) + 90;
        }

        if ((SystemClock.elapsedRealtime() - mTouchTime < DURATION_TO_TELL_IF_SLIDE) // 认为在连续的移动中
                && (Math.abs(mPointAngle - pointAngle) > ANGLE_DIFF)) { // 变化幅度过大，说明在 0 和 360 之间发生突变
            if (mPointAngle > pointAngle) {
                mPointAngle = 360;
            } else {
                mPointAngle = 0;
            }
        } else {
            mPointAngle = pointAngle;
        }
        mTouchTime = SystemClock.elapsedRealtime();
        mPercent = mPointAngle / (float)360;

        mValue = mMinValue + mPercent * (mMaxValue - mMinValue);
       /* Log.d(TAG, "onAnimationUpdate: percent = " + mPercent
                + ";currentAngle = " + (mSweepAngle * mPercent)
                + ";value = " + mValue);*/

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Math.round(mValue) == 0) {
                mValue = 0;
            } else {
                mValue = (float) Math.ceil(mValue);
            }
        }

        doSetValue(mValue, true);

        return true;
        //return super.onTouchEvent(event);
    }

    private boolean isPointValid(float p1x,float p1y,float p2x,float p2y) {
        double test = (p1x - p2x) * (p1x - p2x) + (p1y - p2y) * (p1y - p2y);
        double distance = Math.sqrt(test);
        return distance > 120 && distance < 290;  //  140   200
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public float getMinValue() {
        return mMinValue;
    }

    /**
     * 设置最小值
     *
     * @param minValue
     */
    public void setMinValue(float minValue) {
        mMinValue = minValue < mMaxValue ? minValue : mMinValue;
        setMinSelectValue(mMinValue);
    }

    /**
     * 获取最大值
     *
     * @return
     */
    public float getMaxValue() {
        return mMaxValue;
    }

    /**
     * 设置最大值
     *
     * @param maxValue
     */
    public void setMaxValue(float maxValue) {
        mMaxValue = maxValue > mMinValue ? maxValue : mMaxValue;
        setMaxSelectValue(mMaxValue);
    }
    /**
     * 获取最小可选值
     *
     * @return
     */
    public float getMinSelectValue() {
        return mMinSelectValue;
    }

    /**
     * 设置最小可选值
     *
     * @param minSelectValue
     */
    public void setMinSelectValue(float minSelectValue) {
        if (minSelectValue < mMinValue) {
            mMinSelectValue = mMinValue;
        } else if (minSelectValue >= mMaxSelectValue) {
            // do nothing
        } else {
            mMinSelectValue = minSelectValue;
        }
    }

    /**
     * 获取最大可选值
     *
     * @return
     */
    public float getMaxSelectValue() {
        return mMaxSelectValue;
    }

    /**
     * 设置最大可选值
     *
     * @param maxSelectValue
     */
    public void setMaxSelectValue(float maxSelectValue) {
        if (maxSelectValue > mMaxValue) {
            mMaxSelectValue = mMaxValue;
        } else if (maxSelectValue <= mMinSelectValue) {
            // do nothing
        } else {
            mMaxSelectValue = maxSelectValue;
        }
    }

    /**
     * 获取精度
     *
     * @return
     */
    public int getPrecision() {
        return mPrecision;
    }

    public void setPrecision(int precision) {
        mPrecision = precision;
        mPrecisionFormat = MiscUtil.getPrecisionFormat(precision);
    }

    public int[] getGradientColors() {
        return mGradientColors;
    }

    /**
     * 设置渐变
     *
     * @param gradientColors
     */
    public void setGradientColors(int[] gradientColors) {
        mGradientColors = gradientColors;
        updateArcPaint();
    }

    public long getAnimTime() {
        return mAnimTime;
    }

    public void setAnimTime(long animTime) {
        mAnimTime = animTime;
    }

    /**
     * 重置
     */
    public void reset() {
        startAnimator(mPercent, 0.0f, 1000L);
    }

    public void setOnCircleProgressListener(OnCircleProgressListener listener) {
        mListener = listener;
    }

    public void disable() {
        canTouch = false;
    }

    public void enable() {
        canTouch = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //释放资源
    }

    public interface OnCircleProgressListener {
        void onProgress(float value, boolean fromWidget);
    }
}
