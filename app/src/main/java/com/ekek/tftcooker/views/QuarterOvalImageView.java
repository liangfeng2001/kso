package com.ekek.tftcooker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ekek.tftcooker.R;

import static android.graphics.Region.Op.INTERSECT;

/**
 * Created by Casper Lee on 2018/11/22.
 */
public class QuarterOvalImageView extends AppCompatImageView {

    // Constants
    public static final int VERTEX_POSITION_TOP_LEFT = 0;
    public static final int VERTEX_POSITION_TOP_RIGHT = 1;
    public static final int VERTEX_POSITION_BOTTOM_LEFT = 2;
    public static final int VERTEX_POSITION_BOTTOM_RIGHT = 3;
    public static final int VERTEX_POSITION_DEFAULT = 0;


    // Fields
    private Context context;
    private int vertexPosition;
    private Path path;
    private RectF bounds = new RectF();
    private Region region = new Region();

    // 3 points, Clockwise
    private int point0[];
    private int point1[];
    private int point2[];

    // Constructors
    /**
     * Initialize a new instance of the class QuarterOvalImageView.
     * @param context context
     */
    public QuarterOvalImageView(Context context) {
        super(context);
        initFields(context, null);
    }

    /**
     * Initialize a new instance of the class QuarterOvalImageView.
     * @param context context
     * @param attrs attributes
     */
    public QuarterOvalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(context, attrs);
    }

    /**
     * Initialize a new instance of the class QuarterOvalImageView.
     * @param context context
     * @param attrs attributes
     * @param defStyleAttr defStyleAttr
     */
    public QuarterOvalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFields(context, attrs);
    }


    // Override functions
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.point0 = null;
        RectF rectF = null;
        float startAngle = 0;
        float sweepAngle = 0;

        switch (vertexPosition) {
            case VERTEX_POSITION_TOP_LEFT:
                this.point0 = new int[] { 0, h };
                this.point1 = new int[] { 0, 0 };
                this.point2 = new int[] { w, 0 };
                rectF = new RectF(-w, -h, w, h);
                startAngle = 0.0f;
                sweepAngle = 90.0f;
                break;
            case VERTEX_POSITION_TOP_RIGHT:
                this.point0 = new int[] { 0, 0 };
                this.point1 = new int[] { w, 0 };
                this.point2 = new int[] { w, h };
                rectF = new RectF(0, -h, 2 * w, h);
                startAngle = 90.0f;
                sweepAngle = 90.0f;
                break;
            case VERTEX_POSITION_BOTTOM_LEFT:
                this.point0 = new int[] { w, h };
                this.point1 = new int[] { 0, h };
                this.point2 = new int[] { 0, 0 };
                rectF = new RectF(-w, 0, w, 2 * h);
                startAngle = 270.0f;
                sweepAngle = 90.0f;
                break;
            case VERTEX_POSITION_BOTTOM_RIGHT:
                this.point0 = new int[] { w, 0 };
                this.point1 = new int[] { w, h };
                this.point2 = new int[] { 0, h };
                rectF = new RectF(0, 0, 2 * w, 2 * h);
                startAngle = 180.0f;
                sweepAngle = 90.0f;
                break;
        }
        if (this.point0 != null) {
            path.moveTo(point0[0], point0[1]);
            path.lineTo(point1[0], point1[1]);
            path.lineTo(point2[0], point2[1]);
            path.arcTo(rectF, startAngle, sweepAngle);
            path.close();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path, INTERSECT);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        path.computeBounds(bounds, true);
        region.set(
                (int)bounds.left,
                (int)bounds.top,
                (int)bounds.right,
                (int)bounds.bottom);
        region.setPath(path, region);
        boolean ct =  region.contains((int)event.getX(), (int)event.getY());
        return ct && super.onTouchEvent(event);
    }


    // Public functions
    /**
     * set the value for vertexPosition
     * @param value new vertex position:
     *              0 VERTEX_POSITION_TOP_LEFT
     *              1 VERTEX_POSITION_TOP_RIGHT
     *              2 VERTEX_POSITION_BOTTOM_LEFT
     *              3 VERTEX_POSITION_BOTTOM_RIGHT
     */
    public void setVertexPosition(int value) {
        switch (value) {
            case VERTEX_POSITION_TOP_LEFT:
            case VERTEX_POSITION_TOP_RIGHT:
            case VERTEX_POSITION_BOTTOM_LEFT:
            case VERTEX_POSITION_BOTTOM_RIGHT:
                vertexPosition = value;
                postInvalidate();
                break;
        }
    }


    // Private functions
    /**
     * initialize all the fields of the class
     * @param context context
     * @param attrs attributes
     */
    private void initFields(Context context, AttributeSet attrs) {
        this.context = context;
        this.path = new Path();
        initAttrs(attrs);
    }

    /**
     * initialize all the attributes of the view
     * @param attrs attributes
     */
    private void initAttrs(AttributeSet attrs) {
        if (attrs == null) {
            vertexPosition = VERTEX_POSITION_DEFAULT;
        } else {
            TypedArray typedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.QuarterOvalImageView);
            vertexPosition = typedArray.getInt(
                    R.styleable.QuarterOvalImageView_vertex_position,
                    VERTEX_POSITION_DEFAULT);
        }
    }
}
