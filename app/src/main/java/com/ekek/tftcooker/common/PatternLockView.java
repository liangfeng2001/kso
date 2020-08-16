package com.ekek.tftcooker.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.listener.PatternLockViewListener;
import com.ekek.tftcooker.utils.LogUtil;
import com.ekek.tftcooker.utils.ResourceUtils;
import com.ekek.tftcooker.utils.PatternLockUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static com.ekek.tftcooker.common.PatternLockView.AspectRatio.ASPECT_RATIO_SQUARE;
import static com.ekek.tftcooker.common.PatternLockView.AspectRatio.ASPECT_RATIO_WIDTH_BIAS;
import static com.ekek.tftcooker.common.PatternLockView.PatternViewMode.AUTO_DRAW;
import static com.ekek.tftcooker.common.PatternLockView.PatternViewMode.CORRECT;
import static com.ekek.tftcooker.common.PatternLockView.PatternViewMode.WRONG;
import static com.ekek.tftcooker.common.PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS;


/**
 * Displays a powerful, customizable and Material Design complaint pattern lock in the screen which
 * can be used to lock any Activity or Fragment from the user
 */
public class PatternLockView extends View {

    private static final int DOT_OFFSET_SEGMENT_WIDTH = 4;

    private int offer_x=165;
    private int offer_y=75;
    /**
     * Represents the aspect ratio for the View
     */
    @IntDef({ASPECT_RATIO_SQUARE, ASPECT_RATIO_WIDTH_BIAS, ASPECT_RATIO_HEIGHT_BIAS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AspectRatio {
        // Width and height will be same. Minimum of width and height
        int ASPECT_RATIO_SQUARE = 0;
        // Width will be fixed. The height will be the minimum of width and height
        int ASPECT_RATIO_WIDTH_BIAS = 1;
        // Height will be fixed. The width will be the minimum of width and height
        int ASPECT_RATIO_HEIGHT_BIAS = 2;
    }

    /**
     * Represents the different modes in which this view can be represented
     */
    @IntDef({CORRECT, AUTO_DRAW, WRONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PatternViewMode {
        /**
         * This state represents a correctly drawn pattern by the user. The color of the path and
         * the dots both would be changed to this color.
         * <p>
         * (NOTE - Consider showing this state in a friendly color)
         */
        int CORRECT = 0;
        /**
         * Automatically draw the pattern for demo or tutorial purposes.
         */
        int AUTO_DRAW = 1;
        /**
         * This state represents a wrongly drawn pattern by the user. The color of the path and
         * the dots both would be changed to this color.
         * <p>
         * (NOTE - Consider showing this state in an attention-seeking color)
         */
        int WRONG = 2;
    }

    private static final int DEFAULT_PATTERN_DOT_COUNT = 3;
    private static final boolean PROFILE_DRAWING = false;

    /**
     * The time (in millis) spend in animating each circle of a lock pattern if
     * the animating mode is set. The entire animation should take this constant
     * the length of the pattern to complete.
     */
    private static final int MILLIS_PER_CIRCLE_ANIMATING = 700;

    // Amount of time (in millis) spent to animate a dot
    private static final int DEFAULT_DOT_ANIMATION_DURATION = 190;
    // Amount of time (in millis) spent to animate a path ends
    private static final int DEFAULT_PATH_END_ANIMATION_DURATION = 100;
    // This can be used to avoid updating the display for very small motions or noisy panels
    private static final float DEFAULT_DRAG_THRESHOLD = 0.0f;

    private DotState[][] mDotStates;
    private int mPatternSize;
    private boolean mDrawingProfilingStarted = false;
    private long mAnimatingPeriodStart;
    private float mHitFactor = 0.8f;

    // Made static so that the static inner class can use it
    private static int sDotCount;
    private int[] sDotsIgnored;
    private DotOffset[] sDotOffsets;

    private boolean mAspectRatioEnabled;
    private int mAspectRatio;
    private int mNormalStateColor;
    private int mWrongStateColor;
    private int mCorrectStateColor;
    private int mPathWidth;
    private int mDotNormalSize;
    private int mDotSelectedSize;
    private int mDotAnimationDuration;
    private int mPathEndAnimationDuration;

    private Paint mDotPaint;
    private Paint mPathPaint;
    private Paint mDotBgPaint;

    private Bitmap dotBitmap;

    private List<PatternLockViewListener> mPatternListeners;
    // The pattern represented as a list of connected {@link Dot}
    private ArrayList<Dot> mPattern;

    /**
     * Lookup table for the dots of the pattern we are currently drawing.
     * This will be the dots of the complete pattern unless we are animating,
     * in which case we use this to hold the dots we are drawing for the in
     * progress animation.
     */
    private boolean[][] mPatternDrawLookup;

    private PointF mInProgress = new PointF(-1,-1);

    private int mPatternViewMode = CORRECT;
    private boolean mInStealthMode = false;
    private boolean mEnableHapticFeedback = true;
    private boolean mPatternInProgress = false;

    private float mViewWidth;
    private float mViewHeight;

    private final Path mCurrentPath = new Path();
    private final Rect mInvalidate = new Rect();
    private final Rect mTempInvalidateRect = new Rect();

    private Interpolator mFastOutSlowInInterpolator;
    private Interpolator mLinearOutSlowInInterpolator;

    private boolean secondFingerDown = false;
    private boolean actionInterrupted = false;

    public PatternLockView(Context context) {
        this(context, null);
        // super(context);
    }



    public PatternLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // this(context,attrs ,0);
        // this(context, attrs,0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.viewmodule_PatternLockView);
        try {
            sDotCount = typedArray.getInt(R.styleable.viewmodule_PatternLockView_viewmodule_dotCount,
                    DEFAULT_PATTERN_DOT_COUNT);
            String dotsIgnored = typedArray.getString(R.styleable.viewmodule_PatternLockView_viewmodule_ignored_dots);
            sDotsIgnored = this.parseDotsIgnoredString(dotsIgnored);
            String dotOffsets = typedArray.getString(R.styleable.viewmodule_PatternLockView_viewmodule_dot_offsets);
            sDotOffsets = this.parseDotOffsetsString(dotOffsets);
            mAspectRatioEnabled = typedArray.getBoolean(R.styleable.viewmodule_PatternLockView_viewmodule_aspectRatioEnabled,
                    false);
            mAspectRatio = typedArray.getInt(R.styleable.viewmodule_PatternLockView_viewmodule_aspectRatio,
                    ASPECT_RATIO_SQUARE);
            mPathWidth = (int) typedArray.getDimension(R.styleable.viewmodule_PatternLockView_viewmodule_pathWidth,
                    ResourceUtils.getDimensionInPx(getContext(), R.dimen.viewmodule_pattern_lock_path_width));
            mNormalStateColor = typedArray.getColor(R.styleable.viewmodule_PatternLockView_viewmodule_normalStateColor,
                    ResourceUtils.getColor(getContext(), R.color.viewmodule_white));
            mCorrectStateColor = typedArray.getColor(R.styleable.viewmodule_PatternLockView_viewmodule_correctStateColor,
                    ResourceUtils.getColor(getContext(), R.color.viewmodule_white));
            mWrongStateColor = typedArray.getColor(R.styleable.viewmodule_PatternLockView_viewmodule_wrongStateColor,
                    ResourceUtils.getColor(getContext(), R.color.viewmodule_pomegranate));
            mDotNormalSize = (int) typedArray.getDimension(R.styleable.viewmodule_PatternLockView_viewmodule_dotNormalSize,
                    ResourceUtils.getDimensionInPx(getContext(), R.dimen.viewmodule_pattern_lock_dot_size));
            mDotSelectedSize = (int) typedArray.getDimension(R.styleable
                            .viewmodule_PatternLockView_viewmodule_dotSelectedSize,
                    ResourceUtils.getDimensionInPx(getContext(), R.dimen.viewmodule_pattern_lock_dot_selected_size));
            mDotAnimationDuration = typedArray.getInt(R.styleable.viewmodule_PatternLockView_viewmodule_dotAnimationDuration,
                    DEFAULT_DOT_ANIMATION_DURATION);
            mPathEndAnimationDuration = typedArray.getInt(R.styleable.viewmodule_PatternLockView_viewmodule_pathEndAnimationDuration,
                    DEFAULT_PATH_END_ANIMATION_DURATION);
        } finally {
            typedArray.recycle();
        }

        // The pattern will always be symmetrical
        mPatternSize = sDotCount * sDotCount;
        mPattern = new ArrayList<>(mPatternSize);
        mPatternDrawLookup = new boolean[sDotCount][sDotCount];

        setDotStates();

        mPatternListeners = new ArrayList<>();

        if (dotBitmap == null) {
            dotBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_lock_select_pattern_dot);
        }

        initView();
    }

    private void initView() {
        // setClickable(true);

        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setDither(true);
        mPathPaint.setColor(mNormalStateColor);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeWidth(mPathWidth);

        mDotPaint = new Paint();  // 画时显示的环
        mDotPaint.setAntiAlias(true);
        mDotPaint.setDither(true);

        mDotBgPaint = new Paint();
        mDotBgPaint.setStrokeWidth(6);//6
        mDotBgPaint.setColor(Color.BLACK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && !isInEditMode()) {
            mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(
                    getContext(), android.R.interpolator.fast_out_slow_in);
            mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(
                    getContext(), android.R.interpolator.linear_out_slow_in);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mAspectRatioEnabled) {
            return;
        }

        int oldWidth = resolveMeasured(widthMeasureSpec, getSuggestedMinimumWidth());
        int oldHeight = resolveMeasured(heightMeasureSpec, getSuggestedMinimumHeight());

        int newWidth;
        int newHeight;
        switch (mAspectRatio) {
            case ASPECT_RATIO_SQUARE:
                newWidth = newHeight = Math.min(oldWidth, oldHeight);
                break;
            case ASPECT_RATIO_WIDTH_BIAS:
                newWidth = oldWidth;
                newHeight = Math.min(oldWidth, oldHeight);
                break;

            case ASPECT_RATIO_HEIGHT_BIAS:
                newWidth = Math.min(oldWidth, oldHeight);
                newHeight = oldHeight;
                break;

            default:
                throw new IllegalStateException("Unknown aspect ratio");
        }
        setMeasuredDimension(newWidth, newHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isEnabled() || (getVisibility() != View.VISIBLE)) return;
        if (isSecondFingerDown()) {
            return;
        }

        ArrayList<Dot> pattern = mPattern;
        int patternSize = pattern.size();
        boolean[][] drawLookupTable = mPatternDrawLookup;

        if (mPatternViewMode == AUTO_DRAW) {
            int oneCycle = (patternSize + 1) * MILLIS_PER_CIRCLE_ANIMATING;
            int spotInCycle = (int) (SystemClock.elapsedRealtime() - mAnimatingPeriodStart)
                    % oneCycle;
            int numCircles = spotInCycle / MILLIS_PER_CIRCLE_ANIMATING;

            clearPatternDrawLookup();
            for (int i = 0; i < numCircles; i++) {
                Dot dot = pattern.get(i);
                drawLookupTable[dot.mRow][dot.mColumn] = true;
            }

            boolean needToUpdateInProgressPoint = numCircles > 0
                    && numCircles < patternSize;

            if (needToUpdateInProgressPoint) {
                float percentageOfNextCircle = ((float) (spotInCycle % MILLIS_PER_CIRCLE_ANIMATING))
                        / MILLIS_PER_CIRCLE_ANIMATING;

                Dot currentDot = pattern.get(numCircles - 1);
                PointF center = getCenter(currentDot.mRow, currentDot.mColumn);
                Dot nextDot = pattern.get(numCircles);
                PointF centerNextDot = getCenter(nextDot.mRow, nextDot.mColumn);
                float dx = percentageOfNextCircle
                        * (centerNextDot.x - center.x);
                float dy = percentageOfNextCircle
                        * (centerNextDot.y - center.y);
                mInProgress = center;
                mInProgress.x = center.x + dx;
                mInProgress.y = center.y + dy;
            }
            invalidate();
        }

        Path currentPath = mCurrentPath;
        currentPath.rewind();

        // Draw the path of the pattern (unless we are in stealth mode)
        boolean drawPath = !mInStealthMode;
        if (drawPath) {
            mPathPaint.setColor(getCurrentColor(true));

            boolean anyCircles = false;
            float lastX = 0f;
            float lastY = 0f;
            int dotCounter = -1;
            for (int i = 0; i < patternSize; i++) {
                Dot dot = pattern.get(i);
                if (ignoreDot(dot.mRow, dot.mColumn)) {
                    continue;
                }

                // Only draw the part of the pattern stored in
                // the lookup table (this is only different in case
                // of animation)
                if (!drawLookupTable[dot.mRow][dot.mColumn]) {
                    break;
                }
                anyCircles = true;

                PointF center = getCenter(dot.mRow, dot.mColumn);
                dotCounter++;
                if (dotCounter != 0) {
                    DotState state = mDotStates[dot.mRow][dot.mColumn];
                    currentPath.rewind();
                    currentPath.moveTo(lastX , lastY);
                    if (state.mLineEndX != Float.MIN_VALUE
                            && state.mLineEndY != Float.MIN_VALUE) {
                        currentPath.lineTo(state.mLineEndX, state.mLineEndY);
                    } else {
                        currentPath.lineTo(center.x , center.y);
                    }
                    canvas.drawPath(currentPath, mPathPaint);
                }
                lastX = center.x;
                lastY = center.y;
            }

            // Draw last in progress section
            if ((mPatternInProgress || mPatternViewMode == AUTO_DRAW)
                    && anyCircles) {
                currentPath.rewind();

                currentPath.moveTo(lastX, lastY);
                currentPath.lineTo(mInProgress.x, mInProgress.y);

                mPathPaint.setAlpha((int) (calculateLastSegmentAlpha(
                        mInProgress.x, mInProgress.y, lastX, lastY) * 255f));
                canvas.drawPath(currentPath, mPathPaint);
            }
        }

        // Draw the dots
        for (int i = 0; i < sDotCount; i++) {
            for (int j = 0; j < sDotCount; j++) {
                DotState dotState = mDotStates[i][j];
                PointF center = getCenter(i, j);
                float size = dotState.mSize * dotState.mScale;
                float translationY = dotState.mTranslateY;
                if (!ignoreDot(i, j)) {
                    drawCircle(canvas, (int) center.x, (int) center.y + translationY,size, drawLookupTable[i][j], dotState.mAlpha);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        int adjustedWidth = width - getPaddingLeft() - getPaddingRight();

        mViewWidth = adjustedWidth / (float) sDotCount;

        int adjustedHeight = height - getPaddingTop() - getPaddingBottom();
        mViewHeight = adjustedHeight / (float) sDotCount;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState,
                PatternLockUtils.patternToString(this, mPattern),
                mPatternViewMode, mInStealthMode,
                mEnableHapticFeedback);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setPattern(CORRECT,
                PatternLockUtils.stringToPattern(this, savedState.getSerializedPattern()));
        mPatternViewMode = savedState.getDisplayMode();
        mInStealthMode = savedState.isInStealthMode();
        mEnableHapticFeedback = savedState.isTactileFeedbackEnabled();
    }

   /* @Override
    public boolean onHoverEvent(MotionEvent event) {
        if (((AccessibilityManager) getContext().getSystemService(
                Context.ACCESSIBILITY_SERVICE)).isTouchExplorationEnabled()) {
            final int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_HOVER_ENTER:
                    event.setAction(MotionEvent.ACTION_DOWN);
                    break;
                case MotionEvent.ACTION_HOVER_MOVE:
                    event.setAction(MotionEvent.ACTION_MOVE);
                    break;
                case MotionEvent.ACTION_HOVER_EXIT:
                    event.setAction(MotionEvent.ACTION_UP);
                    break;
            }
         //   onTouchEvent(event);
            event.setAction(action);
        }
        return super.onHoverEvent(event);
    }*/

    public void ACTION_DOWN(MotionEvent event) {
        if (!isEnabled() || (getVisibility() != View.VISIBLE)) return;
        secondFingerDown = false;
        actionInterrupted = false;
        handleActionDown(event);
    }

    public void ACTION_MOVE(MotionEvent event) {
        if (!isEnabled() || (getVisibility() != View.VISIBLE)) {
            if (!actionInterrupted) {
                actionInterrupted = true;
                resetPattern();
            }
            return;
        }
        if (!actionInterrupted) {
            handleActionMove(event);
        }
    }

    public void ACTION_UP(MotionEvent event) {
        if (!isEnabled() || (getVisibility() != View.VISIBLE)) {
            if (!actionInterrupted) {
                actionInterrupted = true;
                resetPattern();
            }
            return;
        }
        if (!actionInterrupted) {
            handleActionUp(event);
        }
    }

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(event);
                return true;
            case MotionEvent.ACTION_UP:
                handleActionUp(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                return true;
            case MotionEvent.ACTION_CANCEL:
                mPatternInProgress = false;
                resetPattern();
                notifyPatternCleared();

                if (PROFILE_DRAWING) {
                    if (mDrawingProfilingStarted) {
                        Debug.stopMethodTracing();
                        mDrawingProfilingStarted = false;
                    }
                }
                return true;
        }
        return false;
    }
*/

    /**
     * Returns the list of dots in the current selected pattern. This list is independent of the
     * internal pattern dot list
     */
    @SuppressWarnings("unchecked")
    public List<Dot> getPattern() {
        return (List<Dot>) mPattern.clone();
    }

    @PatternViewMode
    public int getPatternViewMode() {
        return mPatternViewMode;
    }

    public boolean isInStealthMode() {
        return mInStealthMode;
    }

    public boolean isTactileFeedbackEnabled() {
        return mEnableHapticFeedback;
    }

    public int getDotCount() {
        return sDotCount;
    }

    public boolean isAspectRatioEnabled() {
        return mAspectRatioEnabled;
    }

    @AspectRatio
    public int getAspectRatio() {
        return mAspectRatio;
    }

    public int getNormalStateColor() {
        return mNormalStateColor;
    }

    public int getWrongStateColor() {
        return mWrongStateColor;
    }

    public int getCorrectStateColor() {
        return mCorrectStateColor;
    }

    public int getPathWidth() {
        return mPathWidth;
    }

    public int getDotNormalSize() {
        return mDotNormalSize;
    }

    public int getDotSelectedSize() {
        return mDotSelectedSize;
    }

    public int getPatternSize() {
        return mPatternSize;
    }

    public int getDotAnimationDuration() {
        return mDotAnimationDuration;
    }

    public int getPathEndAnimationDuration() {
        return mPathEndAnimationDuration;
    }

    /**
     * Set the pattern explicitly rather than waiting for the user to input a
     * pattern. You can use this for help or demo purposes
     *
     * @param patternViewMode The mode in which the pattern should be displayed
     * @param pattern         The pattern
     */
    public void setPattern(@PatternViewMode int patternViewMode, List<Dot> pattern) {
        mPattern.clear();
        mPattern.addAll(pattern);
        clearPatternDrawLookup();
        for (Dot dot : pattern) {
            mPatternDrawLookup[dot.mRow][dot.mColumn] = true;
        }
        setViewMode(patternViewMode);
    }

    /**
     * Set the display mode of the current pattern. This can be useful, for
     * instance, after detecting a pattern to tell this view whether change the
     * in progress result to correct or wrong.
     */
    public void setViewMode(@PatternViewMode int patternViewMode) {
        mPatternViewMode = patternViewMode;
        if (patternViewMode == AUTO_DRAW) {
            if (mPattern.size() == 0) {
                throw new IllegalStateException(
                        "you must have a pattern to "
                                + "animate if you want to set the display mode to animate");
            }
            mAnimatingPeriodStart = SystemClock.elapsedRealtime();
            final Dot first = mPattern.get(0);
            mInProgress = getCenter(first.mRow, first.mColumn);
            clearPatternDrawLookup();
        }
        invalidate();
    }

    public void setDotCount(int dotCount) {
        sDotCount = dotCount;
        mPatternSize = sDotCount * sDotCount;
        mPattern = new ArrayList<>(mPatternSize);
        mPatternDrawLookup = new boolean[sDotCount][sDotCount];

        setDotStates();

        requestLayout();
        invalidate();
    }

    public void setAspectRatioEnabled(boolean aspectRatioEnabled) {
        mAspectRatioEnabled = aspectRatioEnabled;
        requestLayout();
    }

    public void setAspectRatio(@AspectRatio int aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    public void setNormalStateColor(@ColorInt int normalStateColor) {
        mNormalStateColor = normalStateColor;
    }

    public void setWrongStateColor(@ColorInt int wrongStateColor) {
        mWrongStateColor = wrongStateColor;
    }

    public void setCorrectStateColor(@ColorInt int correctStateColor) {
        mCorrectStateColor = correctStateColor;
    }

    public void setPathWidth(@Dimension int pathWidth) {
        mPathWidth = pathWidth;

        initView();
        invalidate();
    }

    public void setDotNormalSize(@Dimension int dotNormalSize) {
        mDotNormalSize = dotNormalSize;
        setDotStates();

        invalidate();
    }

    public void setDotSelectedSize(@Dimension int dotSelectedSize) {
        mDotSelectedSize = dotSelectedSize;
    }

    public void setDotAnimationDuration(int dotAnimationDuration) {
        mDotAnimationDuration = dotAnimationDuration;
        invalidate();
    }

    public void setPathEndAnimationDuration(int pathEndAnimationDuration) {
        mPathEndAnimationDuration = pathEndAnimationDuration;
    }

    /**
     * Set whether the View is in stealth mode. If {@code true}, there will be
     * no visible feedback (path drawing, dot animating, etc) as the user enters the pattern
     */
    public void setInStealthMode(boolean inStealthMode) {
        mInStealthMode = inStealthMode;
    }

    public void setTactileFeedbackEnabled(boolean tactileFeedbackEnabled) {
        mEnableHapticFeedback = tactileFeedbackEnabled;
    }

    public void setEnableHapticFeedback(boolean enableHapticFeedback) {
        mEnableHapticFeedback = enableHapticFeedback;
    }

    public void addPatternLockListener(PatternLockViewListener patternListener) {
        mPatternListeners.add(patternListener);
    }

    public void removePatternLockListener(PatternLockViewListener patternListener) {
        mPatternListeners.remove(patternListener);
    }

    public void clearPattern() {
        resetPattern();
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.max(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }

    private void notifyPatternProgress() {
        sendAccessEvent("Dot added to pattern");
        notifyListenersProgress(mPattern);
    }

    private void notifyPatternStarted() {
        sendAccessEvent("Pattern drawing started");
        notifyListenersStarted();
    }

    private void notifyPatternDetected() {
        sendAccessEvent("Pattern drawing completed");
        notifyListenersComplete(mPattern);
    }

    private void notifyPatternCleared() {
        sendAccessEvent("Pattern cleared");
        notifyListenersCleared();
    }

    private void resetPattern() {
        mPattern.clear();
        clearPatternDrawLookup();
        mPatternViewMode = CORRECT;
        invalidate();
    }

    private void notifyListenersStarted() {
        for (PatternLockViewListener patternListener : mPatternListeners) {
            if (patternListener != null) {
                patternListener.onStarted();
            }
        }
    }

    private void notifyListenersProgress(List<Dot> pattern) {
        for (PatternLockViewListener patternListener : mPatternListeners) {
            if (patternListener != null) {
                patternListener.onProgress(pattern);
            }
        }
    }

    private void notifyListenersComplete(List<Dot> pattern) {
        for (PatternLockViewListener patternListener : mPatternListeners) {
            if (patternListener != null) {
                patternListener.onComplete(pattern);
            }
        }
    }

    private void notifyListenersCleared() {
        for (PatternLockViewListener patternListener : mPatternListeners) {
            if (patternListener != null) {
                patternListener.onCleared();
            }
        }
    }

    private void clearPatternDrawLookup() {
        for (int i = 0; i < sDotCount; i++) {
            for (int j = 0; j < sDotCount; j++) {
                mPatternDrawLookup[i][j] = false;
            }
        }
    }

    /**
     * Determines whether the point x, y will add a new point to the current
     * pattern (in addition to finding the dot, also makes heuristic choices
     * such as filling in gaps based on current pattern).
     *
     * @param x The x coordinate
     * @param y The y coordinate
     */
    private Dot detectAndAddHit(float x, float y) {
        final Dot dot = checkForNewHit(x, y);
        if (dot != null) {
            // Check for gaps in existing pattern
            Dot fillInGapDot = null;
            final ArrayList<Dot> pattern = mPattern;
            if (!pattern.isEmpty()) {
                Dot lastDot = pattern.get(pattern.size() - 1);
                int dRow = dot.mRow - lastDot.mRow;
                int dColumn = dot.mColumn - lastDot.mColumn;

                int fillInRow = lastDot.mRow;
                int fillInColumn = lastDot.mColumn;

                if (Math.abs(dRow) == 2 && Math.abs(dColumn) != 1) {
                    fillInRow = lastDot.mRow + ((dRow > 0) ? 1 : -1);
                }

                if (Math.abs(dColumn) == 2 && Math.abs(dRow) != 1) {
                    fillInColumn = lastDot.mColumn + ((dColumn > 0) ? 1 : -1);
                }

                fillInGapDot = Dot.of(fillInRow, fillInColumn);
            }

            if (fillInGapDot != null
                    && !mPatternDrawLookup[fillInGapDot.mRow][fillInGapDot.mColumn]) {
                addCellToPattern(fillInGapDot);
            }

            addCellToPattern(dot);
            if (mEnableHapticFeedback) {
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                        HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING
                                | HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
            }
            return dot;
        }
        return null;
    }

    private void addCellToPattern(Dot newDot) {
        if (ignoreDot(newDot.mRow, newDot.mColumn)) {
            return;
        }
        mPatternDrawLookup[newDot.mRow][newDot.mColumn] = true;
        mPattern.add(newDot);
        if (!mInStealthMode) {
            startDotSelectedAnimation(newDot);
        }
        notifyPatternProgress();
    }

    private void startDotSelectedAnimation(Dot dot) {
        final DotState dotState = mDotStates[dot.mRow][dot.mColumn];
        startSizeAnimation(mDotNormalSize, mDotSelectedSize, mDotAnimationDuration,
                mLinearOutSlowInInterpolator, dotState, new Runnable() {

                    @Override
                    public void run() {
                        startSizeAnimation(mDotSelectedSize, mDotNormalSize, mDotAnimationDuration,
                                mFastOutSlowInInterpolator, dotState, null);
                    }
                });
        PointF center = getCenter(dot.mRow, dot.mColumn);
        startLineEndAnimation(dotState, mInProgress.x, mInProgress.y,
                center.x, center.y);
    }

    private void startLineEndAnimation(final DotState state,
                                       final float startX, final float startY, final float targetX,
                                       final float targetY) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = (Float) animation.getAnimatedValue();
                state.mLineEndX = (1 - t) * startX + t * targetX;
                state.mLineEndY = (1 - t) * startY + t * targetY;
                invalidate();
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                state.mLineAnimator = null;
            }

        });
        valueAnimator.setInterpolator(mFastOutSlowInInterpolator);
        valueAnimator.setDuration(mPathEndAnimationDuration);
        valueAnimator.start();
        state.mLineAnimator = valueAnimator;
    }

    private void startSizeAnimation(float start, float end, long duration,
                                    Interpolator interpolator, final DotState state,
                                    final Runnable endRunnable) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                state.mSize = (Float) animation.getAnimatedValue();
                invalidate();
            }

        });
        if (endRunnable != null) {
            valueAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (endRunnable != null) {
                        endRunnable.run();
                    }
                }
            });
        }
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * Helper method to map a given x, y to its corresponding cell
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return
     */
    private Dot checkForNewHit(float x, float y) {
        Dot dotHit = getDotHit(x, y);
        if (dotHit == null) {
            return null;
        }

        if (mPatternDrawLookup[dotHit.getRow()][dotHit.getColumn()]) {
            return null;
        }
        return dotHit;
    }

    private Dot getDotHit(float x, float y) {
        float hitSize = mViewHeight * mHitFactor;
        for (int row = 0; row < sDotCount; row++) {
            for (int col = 0; col < sDotCount; col++) {
                PointF p = getCenter(row, col);
                if (x >= p.x - hitSize
                        && x <= p.x + hitSize
                        && y >= p.y - hitSize
                        && y <= p.y + hitSize) {
                    return Dot.of(row, col);
                }
            }
        }
        return null;
    }

    private void handleActionMove(MotionEvent event) {
        float radius = mPathWidth;
        int historySize = event.getHistorySize();
        mTempInvalidateRect.setEmpty();
        boolean invalidateNow = false;
        for (int i = 0; i < historySize + 1; i++) {
            float x = i < historySize ? event.getHistoricalX(i) : event
                    .getX();
            float y = i < historySize ? event.getHistoricalY(i) : event
                    .getY();
            x=x-offer_x; // 修正
            y=y-offer_y;
            Dot hitDot = detectAndAddHit(x, y);
            int patternSize = mPattern.size();
            if (hitDot != null && patternSize == 1) {
                mPatternInProgress = true;
                notifyPatternStarted();
            }
            // Note current x and y for rubber banding of in progress patterns
            float dx = Math.abs(x - mInProgress.x);
            float dy = Math.abs(y - mInProgress.y);
            if (dx > DEFAULT_DRAG_THRESHOLD || dy > DEFAULT_DRAG_THRESHOLD) {
                invalidateNow = true;
            }

            if (mPatternInProgress && patternSize > 0) {
                final ArrayList<Dot> pattern = mPattern;
                final Dot lastDot = pattern.get(patternSize - 1);

                PointF lastCellCenter = getCenter(lastDot.mRow, lastDot.mColumn);

                // Adjust for drawn segment from last cell to (x,y). Radius
                // accounts for line width.
                float left = Math.min(lastCellCenter.x, x) - radius;
                float right = Math.max(lastCellCenter.x, x) + radius;
                float top = Math.min(lastCellCenter.y, y) - radius;
                float bottom = Math.max(lastCellCenter.y, y) + radius;

                // Invalidate between the pattern's new cell and the pattern's
                // previous cell
                if (hitDot != null) {
                    float width = mViewWidth * 0.5f;
                    float height = mViewHeight * 0.5f;
                    PointF hitCellCenter = getCenter(hitDot.mRow, hitDot.mColumn);

                    left = Math.min(hitCellCenter.x - width, left);
                    right = Math.max(hitCellCenter.x + width, right);
                    top = Math.min(hitCellCenter.y - height, top);
                    bottom = Math.max(hitCellCenter.y + height, bottom);
                }

                // Invalidate between the pattern's last cell and the previous
                // location
                mTempInvalidateRect.union(Math.round(left), Math.round(top),
                        Math.round(right), Math.round(bottom));
            }
        }
        mInProgress.x = event.getX()-offer_x;
        mInProgress.y = event.getY()-offer_y;

        // To save updates, we only invalidate if the user moved beyond a
        // certain amount.
        if (invalidateNow) {
            mInvalidate.union(mTempInvalidateRect);
            invalidate(mInvalidate);
            mInvalidate.set(mTempInvalidateRect);
        }
    }

    private void sendAccessEvent(String value) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setContentDescription(value);
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
            setContentDescription(null);
        } else {
            announceForAccessibility(value);
        }
    }

    private void handleActionUp(MotionEvent event) {
        if (isSecondFingerDown()) {
            return;
        }

        // Report pattern detected
        if (!mPattern.isEmpty()) {
            mPatternInProgress = false;
            cancelLineAnimations();
            notifyPatternDetected();
            invalidate();
        }
        if (PROFILE_DRAWING) {
            if (mDrawingProfilingStarted) {
                Debug.stopMethodTracing();
                mDrawingProfilingStarted = false;
            }
        }
    }

    private void cancelLineAnimations() {
        for (int i = 0; i < sDotCount; i++) {
            for (int j = 0; j < sDotCount; j++) {
                DotState state = mDotStates[i][j];
                if (state.mLineAnimator != null) {
                    state.mLineAnimator.cancel();
                    state.mLineEndX = Float.MIN_VALUE;
                    state.mLineEndY = Float.MIN_VALUE;
                }
            }
        }
    }

    private void handleActionDown(MotionEvent event) {
        resetPattern();
        float x = event.getX()-offer_x;  // 修正，布局已经位移
        float y = event.getY()-offer_y;
        Dot hitDot = detectAndAddHit(x, y);
        if (hitDot != null) {
            mPatternInProgress = true;
            mPatternViewMode = CORRECT;
            notifyPatternStarted();
        } else {
            mPatternInProgress = false;
            notifyPatternCleared();
        }
        if (hitDot != null) {
            PointF start = getCenter(hitDot.mRow, hitDot.mColumn);

            float widthOffset = mViewWidth / 2f;
            float heightOffset = mViewHeight / 2f;

            invalidate((int) (start.x - widthOffset),
                    (int) (start.y - heightOffset),
                    (int) (start.x + widthOffset), (int) (start.y + heightOffset));
        }
        mInProgress.x = x;
        mInProgress.y = y;
        if (PROFILE_DRAWING) {
            if (!mDrawingProfilingStarted) {
                Debug.startMethodTracing("PatternLockDrawing");
                mDrawingProfilingStarted = true;
            }
        }
    }

    private PointF getCenter(int row, int col) {
        PointF r = new PointF();
        r.x = getPaddingLeft() + col * mViewWidth + mViewWidth / 2f;
        r.y = getPaddingTop() + row * mViewHeight + mViewHeight / 2f;
        DotOffset offset = getDotOffset(row, col);
        if (offset != null) {
            r.x += offset.offsetH;
            r.y += offset.offsetW;
        }
        return r;
    }

    private DotOffset getDotOffset(int row, int col) {
        for (DotOffset offset: sDotOffsets) {
            if (row == offset.row && col == offset.col) {
                return offset;
            }
        }
        return null;
    }

    private float calculateLastSegmentAlpha(float x, float y, float lastX,
                                            float lastY) {
        float diffX = x - lastX;
        float diffY = y - lastY;
        float dist = (float) Math.sqrt(diffX * diffX + diffY * diffY);
        float fraction = dist / mViewWidth;
        return Math.min(1f, Math.max(0f, (fraction - 0.3f) * 4f));
    }

    private int getCurrentColor(boolean partOfPattern) {
        if (!partOfPattern || mInStealthMode || mPatternInProgress) {
            return mNormalStateColor;
        } else if (mPatternViewMode == WRONG) {
            return mWrongStateColor;
        } else if (mPatternViewMode == CORRECT
                || mPatternViewMode == AUTO_DRAW) {
            return mCorrectStateColor;
        } else {
            throw new IllegalStateException("Unknown view mode " + mPatternViewMode);
        }
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY,
                            float size, boolean partOfPattern, float alpha) {


        mDotPaint.setColor(getCurrentColor(partOfPattern));
        mDotPaint.setAlpha((int) (alpha * 255));
        if (partOfPattern) {
            canvas.drawBitmap(dotBitmap,centerX - dotBitmap.getWidth() / 2, centerY - dotBitmap.getHeight() / 2,null);
        }
    }

    private void setDotStates() {
        if ((mDotStates == null)
                || (mDotStates.length != sDotCount)
                || (mDotStates[0].length != sDotCount)) {
            mDotStates = new DotState[sDotCount][sDotCount];
        }
        for (int i = 0; i < sDotCount; i++) {
            for (int j = 0; j < sDotCount; j++) {
                mDotStates[i][j] = new DotState();
                mDotStates[i][j].mSize = mDotNormalSize;
            }
        }
    }
    private int[] parseDotsIgnoredString(String value) {
        if (value == null) {
            return new int[0];
        }
        String[] dots = value.trim().split(",");
        int[] result = new int[dots.length];
        for (int i = 0; i < dots.length; i++) {
            try {
                int x = Integer.parseInt(dots[i]);
                result[i] = x;
            } catch (NumberFormatException ex) {
                // set to -1, means this value is invalid
                result[i] = -1;
            }
        }
        return result;
    }
    private DotOffset[] parseDotOffsetsString(String value) {
        if (value == null || value.trim().equals("")) {
            return new DotOffset[0];
        }
        String[] items = value.trim().split(",");
        if (items.length % DOT_OFFSET_SEGMENT_WIDTH != 0) {
            return new DotOffset[0];
        }
        int len = items.length / DOT_OFFSET_SEGMENT_WIDTH;
        DotOffset[] result = new DotOffset[len];
        for (int loop = 0; loop < len; loop ++) {
            try {
                int row = Integer.parseInt(items[DOT_OFFSET_SEGMENT_WIDTH * loop]);
                int col = Integer.parseInt(items[DOT_OFFSET_SEGMENT_WIDTH * loop + 1]);
                int offsetH = Integer.parseInt(items[DOT_OFFSET_SEGMENT_WIDTH * loop + 2]);
                int offsetW = Integer.parseInt(items[DOT_OFFSET_SEGMENT_WIDTH * loop + 3]);
                result[loop] = new DotOffset(row, col, offsetH, offsetW);
            } catch (NumberFormatException ex) {
                LogUtil.e(ex.getMessage());
                // set to null, means this value is invalid
                result[loop] = null;
            }
        }
        return result;
    }
    private boolean ignoreDot(int row, int col) {
        for (int i = 0; i < sDotsIgnored.length; i++) {
            if (sDotsIgnored[i] == row * sDotCount + col) {
                return true;
            }
        }
        return false;
    }

    public boolean isSecondFingerDown() {
        return secondFingerDown;
    }

    public void setSecondFingerDown(boolean secondFingerDown) {
        this.secondFingerDown = secondFingerDown;
    }

    /**
     * Represents a cell in the matrix of the pattern view
     */
    public static class Dot implements Parcelable {

        private int mRow;
        private int mColumn;
        private static Dot[][] sDots;

        static {
            sDots = new Dot[sDotCount][sDotCount];

            // Initializing the dots
            for (int i = 0; i < sDotCount; i++) {
                for (int j = 0; j < sDotCount; j++) {
                    sDots[i][j] = new Dot(i, j);
                }
            }
        }

        private Dot(int row, int column) {
            checkRange(row, column);
            this.mRow = row;
            this.mColumn = column;
        }

        /**
         * Gets the identifier of the dot. It is counted from left to right, top to bottom of the
         * matrix, starting by zero
         */
        public int getId() {
            return mRow * sDotCount + mColumn;
        }

        public int getRow() {
            return mRow;
        }

        public int getColumn() {
            return mColumn;
        }

        /**
         * @param row    The mRow of the cell.
         * @param column The mColumn of the cell.
         */
        public static synchronized Dot of(int row, int column) {
            checkRange(row, column);
            return sDots[row][column];
        }

        /**
         * Gets a cell from its identifier
         */
        public static synchronized Dot of(int id) {
            return of(id / sDotCount, id % sDotCount);
        }

        private static void checkRange(int row, int column) {
            if (row < 0 || row > sDotCount - 1) {
                throw new IllegalArgumentException("mRow must be in range 0-"
                        + (sDotCount - 1));
            }
            if (column < 0 || column > sDotCount - 1) {
                throw new IllegalArgumentException("mColumn must be in range 0-"
                        + (sDotCount - 1));
            }
        }

        @Override
        public String toString() {
            return "(Row = " + mRow + ", Col = " + mColumn + ")";
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Dot)
                return mColumn == ((Dot) object).mColumn
                        && mRow == ((Dot) object).mRow;
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            int result = mRow;
            result = 31 * result + mColumn;
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mColumn);
            dest.writeInt(mRow);
        }

        public static final Creator<Dot> CREATOR = new Creator<Dot>() {

            public Dot createFromParcel(Parcel in) {
                return new Dot(in);
            }

            public Dot[] newArray(int size) {
                return new Dot[size];
            }
        };

        private Dot(Parcel in) {
            mColumn = in.readInt();
            mRow = in.readInt();
        }
    }

    /**
     * The parcelable for saving and restoring a lock pattern view
     */
    private static class SavedState extends BaseSavedState {

        private final String mSerializedPattern;
        private final int mDisplayMode;
        private final boolean mInStealthMode;
        private final boolean mTactileFeedbackEnabled;

        /**
         * Constructor called from {@link PatternLockView#onSaveInstanceState()}
         */
        private SavedState(Parcelable superState, String serializedPattern,
                           int displayMode, boolean inStealthMode,
                           boolean tactileFeedbackEnabled) {
            super(superState);

            mSerializedPattern = serializedPattern;
            mDisplayMode = displayMode;
            mInStealthMode = inStealthMode;
            mTactileFeedbackEnabled = tactileFeedbackEnabled;
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);

            mSerializedPattern = in.readString();
            mDisplayMode = in.readInt();
            mInStealthMode = (Boolean) in.readValue(null);
            mTactileFeedbackEnabled = (Boolean) in.readValue(null);
        }

        public String getSerializedPattern() {
            return mSerializedPattern;
        }

        public int getDisplayMode() {
            return mDisplayMode;
        }

        public boolean isInStealthMode() {
            return mInStealthMode;
        }

        public boolean isTactileFeedbackEnabled() {
            return mTactileFeedbackEnabled;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(mSerializedPattern);
            dest.writeInt(mDisplayMode);
            dest.writeValue(mInStealthMode);
            dest.writeValue(mTactileFeedbackEnabled);
        }

        @SuppressWarnings("unused")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public static class DotState {
        float mScale = 1.0f;
        float mTranslateY = 0.0f;
        float mAlpha = 1.0f;
        float mSize;
        float mLineEndX = Float.MIN_VALUE;
        float mLineEndY = Float.MIN_VALUE;
        ValueAnimator mLineAnimator;
    }

    class DotOffset {
        private int row;
        private int col;
        private int offsetH;
        private int offsetW;
        DotOffset(int row, int col, int offsetH, int offsetW) {
            this.row = row;
            this.col = col;
            this.offsetH = offsetH;
            this.offsetW = offsetW;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getOffsetH() {
            return offsetH;
        }

        public int getOffsetW() {
            return offsetW;
        }
    }
}
