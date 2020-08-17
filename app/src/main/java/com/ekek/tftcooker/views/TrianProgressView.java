package com.ekek.tftcooker.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.utils.LogUtil;

public class TrianProgressView extends View{

    private Paint mPaintprogress,mPaitbackground;//
    Bitmap background1,background2,background3,background4,background5,background6,background7,background8,background9,background10;

    Bitmap progress1, progress2, progress3, progress4,progress5, progress6,progress7,progress8,progress9, progress10;
    boolean readyGoToWork=true;
    int  background1Width;//=background1 .getWidth();
    private Point mCenterPoint;
    static final int every=15;
    boolean canTouch=true;
    private float mMaxValue=10;    // 设置的最大值
    private float mMinValue=0.0f;
    private float mMinSelectValue=0.0f;
    private float mMaxSelectValue=10.0f;
    private float mValue;                // 当前的值
    private OnTrianProgressListener mListener;
    private int mGetNumOfProgress=0;   // 显示多少条橙色的坚条
    public TrianProgressView(Context context) {
        super(context);
    }

    public TrianProgressView(Context context, @Nullable AttributeSet attrs) {
       // super(context, attrs);
        this(context, attrs,0);
        init(context);
    }

    public TrianProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaintprogress = new Paint( );

        mPaitbackground =new Paint() ;

        mPaintprogress.setStyle(Paint.Style.FILL);//充满
        mPaintprogress.setAntiAlias(true);// 设置画笔的锯齿效果

        //  mPaintprogress.setStrokeCap(Paint.Cap.ROUND);
         background1= BitmapFactory.decodeResource(getResources(), R.mipmap.gray1);
         background2= BitmapFactory.decodeResource(getResources(), R.mipmap.gray2);
         background3= BitmapFactory.decodeResource(getResources(), R.mipmap.gray3);
         background4= BitmapFactory.decodeResource(getResources(), R.mipmap.gray4);
         background5= BitmapFactory.decodeResource(getResources(), R.mipmap.gray5);
         background6= BitmapFactory.decodeResource(getResources(), R.mipmap.gray6);
         background7= BitmapFactory.decodeResource(getResources(), R.mipmap.gray7);
         background8= BitmapFactory.decodeResource(getResources(), R.mipmap.gray8);
         background9= BitmapFactory.decodeResource(getResources(), R.mipmap.gray9);
         background10= BitmapFactory.decodeResource(getResources(), R.mipmap.gray10);

         progress1= BitmapFactory.decodeResource(getResources(), R.mipmap.orange1);
         progress2= BitmapFactory.decodeResource(getResources(), R.mipmap.orange2);
         progress3= BitmapFactory.decodeResource(getResources(), R.mipmap.orange3);
         progress4= BitmapFactory.decodeResource(getResources(), R.mipmap.orange4);
         progress5= BitmapFactory.decodeResource(getResources(), R.mipmap.orange5);
         progress6= BitmapFactory.decodeResource(getResources(), R.mipmap.orange6);
         progress7= BitmapFactory.decodeResource(getResources(), R.mipmap.orange7);
         progress8= BitmapFactory.decodeResource(getResources(), R.mipmap.orange8);
         progress9= BitmapFactory.decodeResource(getResources(), R.mipmap.orange9);
         progress10= BitmapFactory.decodeResource(getResources(), R.mipmap.orange10);
        background1Width=background1 .getWidth();

        mCenterPoint = new Point();
        mPaitbackground=new Paint(Paint .ANTI_ALIAS_FLAG |Paint .FILTER_BITMAP_FLAG ) ;
        mPaintprogress=new Paint(Paint .ANTI_ALIAS_FLAG |Paint .FILTER_BITMAP_FLAG ) ;
        mPaitbackground.setColor(Color.parseColor("#1a1a1a")) ;  // gray
        mPaintprogress.setColor(Color.parseColor("#d84824")) ;  // orange
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (readyGoToWork ) {
            final int width = getMeasuredWidth();
            final int height = getMeasuredHeight();

            // make the view the original height + indicator height size
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected  void  onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.save();
        //--------------背景------------------
        mPaitbackground.reset();
        mPaintprogress.reset();
        switch (mGetNumOfProgress){
            case 0:
                canvas .drawBitmap(background1 ,0,background10.getHeight()-background1.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 1:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 2:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 3:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 4:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 5:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 6:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 7:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 8:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaintprogress ) ;

                canvas .drawBitmap(background9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaitbackground ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 9:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(background10 ,background1Width*9+every*9,0,mPaitbackground ) ;
                break;
            case 10:
                canvas .drawBitmap(progress1 ,0,background10.getHeight()-background1.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress2 ,background1Width+every,background10.getHeight()-background2.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress3 ,background1Width*2+every*2,background10.getHeight()-background3.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress4 ,background1Width*3+every*3,background10.getHeight()-background4.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress5 ,background1Width*4+every*4,background10.getHeight()-background5.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress6 ,background1Width*5+every*5,background10.getHeight()-background6.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress7 ,background1Width*6+every*6,background10.getHeight()-background7.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress8 ,background1Width*7+every*7,background10.getHeight()-background8.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress9 ,background1Width*8+every*8,background10.getHeight()-background9.getHeight(),mPaintprogress ) ;
                canvas .drawBitmap(progress10 ,background1Width*9+every*9,0,mPaintprogress ) ;
                break;
            default:
                break;
        }
     //   canvas.restore();
        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterPoint.x = getPaddingLeft() + w / 2;
        mCenterPoint.y = getPaddingTop() + h / 2;
   //     LogUtil.d("centerpoint x is "+ mCenterPoint.x);
    //    LogUtil.d("centerpoint y is "+ mCenterPoint.y);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canTouch) return true;

        /*获取点击位置的坐标*/
        float Action_x = event.getX();
        float Action_y = event.getY();
        LogUtil.d("click x = "+Action_x);
        LogUtil.d("click y = "+Action_y);

        if (!isPointValid(Action_x,Action_y , mCenterPoint.x , mCenterPoint.y)) {
            return true;
        }
        mGetNumOfProgress=getProgressNumber(Action_x);
        doSetValue(mGetNumOfProgress,Action_x, true);
        LogUtil.d("mGetNumOfProgress is "+mGetNumOfProgress);

        /*根据坐标转换成对应的角度*/
        float get_x0 = Action_x - mCenterPoint.x;
        float get_y0 = Action_y - mCenterPoint.y;
        return true;
    }

    private boolean isPointValid(float p1x,float p1y,float p2x,float p2y) {
       /* double test = (p1x - p2x) * (p1x - p2x) + (p1y - p2y) * (p1y - p2y);
        double distance = Math.sqrt(test);
        return distance > 120 && distance < 290;  //  140   200*/
       boolean reValue=true;
       if(p1x-p2x<0&&p1y-p2y<0){  // 第二象限,点击无效
           reValue=false;
           LogUtil.d("the click 1 is "+reValue);
       }
       if(p1x<0){  // 超过第三象限
           reValue=false;
           LogUtil.d("the click 2 is "+reValue);
       }

       if(p1y>254){  // 超过第三象限
           reValue=false;
           LogUtil.d("the click 3 is "+reValue);
       }

       if(p1x>548){  //  超过第1 or 4 象限
           reValue=false;
           LogUtil.d("the click  4 is "+reValue);
       }

        if(p1y<0){  // 超过第1象限
            reValue=false;
            LogUtil.d("the click  5 is "+reValue);
        }
        LogUtil.d("the click is 6 "+reValue);
       return reValue;
    }
    /**
     * 获取最大值
     *
     * @return
     */
    public void setMaxValue(float maxValue) {
        mMaxValue = maxValue ;

    }

    /**
     * 获取要显示的橙色坚条的数量
     *
     * @return
     */
    private int getProgressNumber(float clickx){
        int num=0;
        float everyX;
       if(mMaxSelectValue>100){  //  null 设置

       }else {
           everyX=clickx*10/548;  // 548 对象的长度
           if(everyX<0.5f){  // 档位、分钟 小时 设置
               num=(int)mMinSelectValue;
           }else if(everyX>=0.5f&&everyX<1.5f){
               num=1;
           }else if(everyX>=1.5f&&everyX<2.5f){
               num=2;
           }else if(everyX>=2.5f&&everyX<3.5f){
               num=3;
           }else if(everyX>=3.5f&&everyX<4.5f){
               num=4;
           }else if(everyX>=4.5f&&everyX<5.5f){
               num=5;
           }else if(everyX>=5.5f&&everyX<6.5f){
               num=6;
           }else if(everyX>=6.5f&&everyX<7.5f){
               num=7;
           }else if(everyX>=7.5f&&everyX<8.5f){
               num=8;
           }else if(everyX>=8.5f&&everyX<9.5f){
               num=9;
           }else if(everyX>=9.5f){
                num=10;

           }
           if(num>mMaxSelectValue){
               num=(int)mMaxSelectValue;
           }
       }

        return num;
    }

    /**
     * 设置当前值
     *
     * @param value
     */
    public void setValue(float value) {
        LogUtil.d("get value from main fragment,value 1 is "+value);
        doSetValue(value, 0,false);
        if (value < mMinSelectValue) {  // 关闭 所有炉头时，滑动条不能显示零.如果屏蔽掉，则
           // value = mMinSelectValue;
        }
        if (value > mMaxSelectValue) {
          //  value = mMaxSelectValue;
        }
        if(mMaxSelectValue>10){  // 设置 分钟
            if (value < mMinSelectValue) {  // 关闭 所有炉头时，滑动条不能显示零.如果屏蔽掉，则设置定时的分钟时，不能显示1档。
                value = mMinSelectValue;
            }
            if (value > mMaxSelectValue) {
                value = mMaxSelectValue;
            }
            if(value==0){
                mGetNumOfProgress=0;
            }else if(value>0&&value<6){
                mGetNumOfProgress=1;
            }else if(value>=6&&value<12){
                mGetNumOfProgress=2;
            }else if(value>=12&&value<18){
                mGetNumOfProgress=3;
            }else if(value>=18&&value<24){
                mGetNumOfProgress=4;
            }else if(value>=24&&value<30){
                mGetNumOfProgress=5;
            }else if(value>=30&&value<36){
                mGetNumOfProgress=6;
            }else if(value>=36&&value<42){
                mGetNumOfProgress=7;
            }else if(value>=42&&value<48){
                mGetNumOfProgress=8;
            }else if(value>=48&&value<54){
                mGetNumOfProgress=9;
            }else if(value>=54&&value<60){
                mGetNumOfProgress=10;
            }

        }else {
            mGetNumOfProgress=(int)(value);
        }
     //   LogUtil.d("get value from main fragment,value 2 is "+value);
        LogUtil.d("get value from main fragment,mMinSelectValue  is "+mMinSelectValue);
    }

    public void powerOff(){
        mGetNumOfProgress=0;
    }

    public void setOnTrianProgressListener(OnTrianProgressListener listener) {
        mListener = listener;
    }

    public interface OnTrianProgressListener {
        void onTrianProgress(float value, boolean fromWidget);
    }

    private void doSetValue(float value, float slipvalue,boolean fromWidget) {

        if(fromWidget&&mMaxSelectValue>10){
            value=(float) Math.ceil(slipvalue*mMaxSelectValue/548);
            LogUtil.d("slip the trianger value is "+value);
        }else {

        }

        if (value < mMinSelectValue) {
            value = mMinSelectValue;
        }
        if (value > mMaxSelectValue) {
            value = mMaxSelectValue;
        }
        mValue=value;
        if (mListener != null){
            mListener.onTrianProgress(value, fromWidget);
        }


        //invalidate();
        //startAnimator(start, end, mAnimTime);
    }
    /**
     * 是否允许点击
     *
     * false 不允许； true 允许
     */
    public void disable() {
        canTouch = false;
    }

    public void enable() {
        canTouch = true;
    }

    public float getValue() {
        return mValue;
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
     * 获取最小值
     *
     * @return
     */
    public float getMinValue() {
        return mMinValue;
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
     * 获取最小可选值
     *
     * @return
     */
    public float getMinSelectValue() {
        return mMinSelectValue;
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
    //    LogUtil.d("Set the mMinSelectValue is "+mMinSelectValue);
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //释放资源
    }

}
