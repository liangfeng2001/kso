package com.ekek.tftcooker.common;

import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PaintTrackOfTouchView extends View {

    private Path path;
    private Paint paint;
    private static final int CookerView_StartX=162;
    private static final int CookerView_StopX=505;
    private static final int CookerView_StartY=90;
    private static final int CookerView_StopY=450;


    private int progress_color_Red=Color.rgb(255, 0, 255);  // 颜色
    private int progress_color_Blue=Color.rgb(20, 144, 206);  // 颜色

    private boolean action_up=false;

    public PaintTrackOfTouchView(Context context) {
            super(context);
        init(context);
    }
    public PaintTrackOfTouchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        init(context);
    }
    public PaintTrackOfTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        this.setFocusable(true);
        path=new Path();
        paint=new Paint();
    //    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setColor(progress_color_Red);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

    }
        @Override
            protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
           // canvas.drawPath(path,paint);
            if(action_up){
                action_up=false;
               // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                  path.reset();

                 // paint=new Paint();
                 // paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                 // canvas.drawPaint(paint);
                  //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            }else {
                canvas.drawPath(path,paint);
            }

        }
        public void ACTION_DOWN(float x,float y){
        if(CookerView_Clicked(x,y)){
           // path.moveTo(x,y);
        }
            path.moveTo(x-140,y-75);
        }
        public void ACTION_MOVE(float x,float y){
            if(CookerView_Clicked(x,y)){
              //  path.lineTo(x,y);
            }
            path.lineTo(x-140,y-75);
            invalidate();
        }
        public void ACTION_UP(){
           // path.close();

            invalidate();
            action_up=true;
           // paint=new Paint();
          //  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
          //  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        }
        /*@Override
        public boolean dispatchTouchEvent(MotionEvent e){
            int action=e.getAction();
            switch(action){
                case MotionEvent.ACTION_DOWN:
                path.moveTo(e.getX(),e.getY());
                break;
                case MotionEvent.ACTION_MOVE:
                path.lineTo(e.getX(),e.getY());
                invalidate();
                break;
                case MotionEvent.ACTION_UP:
                //path.close();
                invalidate();
                break;
            }
            return true;
        }*/
        private boolean CookerView_Clicked(float x, float y){
            boolean ReturnValue = false;
            if (x <= CookerView_StopX && x >=  CookerView_StartX && y <=CookerView_StopY  && y >=  CookerView_StartY) {
                ReturnValue = true;
            }
            return ReturnValue;
        }

}


