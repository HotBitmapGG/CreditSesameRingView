package io.netopen.hotbitmapgg.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by 11 on 2016/8/31.
 * <p/>
 * 仿蚂蚁聚宝的圆环实现
 */
public class CreditSesameRingView extends View implements View.OnClickListener
{

    private Paint mPaint;

    // 渐变色环颜色
    private final int[] mColors = new int[]{
            0xFFFF0000, 0xFFFFFF00, 0xff00ff00, 0xff00ffff, 0xff0000ff, 0xffff00ff
    };

    private static final String[] text = {
            "950", "极好",
            "700", "优秀",
            "650", "良好", "600",
            "中等", "550", "较差",
            "350", "很差", "150"
    };

    //bitmap的宽高
    private int bitmapWidth, bitmapHeight;

    private Paint paintGap1;

    private Paint paintGap2;

    private Paint paintMiddleCircle;

    private Paint paintInnerCircle;

    private Paint paintText;

    private Paint paintMiddleArc;

    private Paint paintBitmap;

    private int width;

    private int height;

    //中心点x
    private int CENTER_X;

    private PaintFlagsDrawFilter pfd;

    private RectF oval;


    private Bitmap bitmap;

    private float r1;

    private RectF arc;

    private ValueAnimator animator;

    private Paint centerTextPaint;

    private RectF arc1;

    private RectF arc2;

    private float mStartAngle = 120f;

    private float mEndAngle = 240f;

    private static final int GREEN_COLOR = 0xff00d4af;

    private float totalRotateAngle = 200f;

    private float currentRotateAngle = 0f;

    //默认宽高值
    private int defaultSize;


    public CreditSesameRingView(Context context)
    {

        this(context, null);
    }

    public CreditSesameRingView(Context context, AttributeSet attrs)
    {

        this(context, attrs, 0);
    }

    public CreditSesameRingView(Context context, AttributeSet attrs, int defStyleAttr)
    {

        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {


        //初始化默认宽高值
        defaultSize = dp2px(250);

        //初始化渐变
        Shader mShader = new SweepGradient(0, 0, mColors, null);

        pfd = new PaintFlagsDrawFilter
                (0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        // 最外层圆环渐变画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(mShader);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);

        //圆环辅助线画笔
        paintGap1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintGap2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintGap1.setColor(Color.WHITE);
        paintGap1.setStrokeWidth(1);
        paintGap2.setColor(Color.WHITE);
        paintGap2.setStrokeWidth(3);


        //中间圆画笔
        paintMiddleCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMiddleCircle.setStyle(Paint.Style.STROKE);
        paintMiddleCircle.setStrokeCap(Paint.Cap.ROUND);
        paintMiddleCircle.setStrokeWidth(4);
        paintMiddleCircle.setColor(Color.GRAY);
        //内虚线圆画笔
        paintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintInnerCircle.setStyle(Paint.Style.STROKE);
        paintInnerCircle.setStrokeCap(Paint.Cap.ROUND);
        paintInnerCircle.setStrokeWidth(4);
        paintInnerCircle.setColor(Color.GRAY);
        //设置虚线
        PathEffect mPathEffect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paintInnerCircle.setPathEffect(mPathEffect);

        //圆弧文本画笔
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(25);

        //中间文字画布
        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setTextAlign(Paint.Align.CENTER);


        //绿色圆弧画笔
        paintMiddleArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMiddleArc.setColor(GREEN_COLOR);
        paintMiddleArc.setStrokeCap(Paint.Cap.ROUND);
        paintMiddleArc.setStrokeWidth(4);
        paintMiddleArc.setStyle(Paint.Style.STROKE);

        //绿色圆弧上的小坐标图片画笔
        paintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
        //获取图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location1_03);
        bitmapHeight = bitmap.getHeight();
        bitmapWidth = bitmap.getWidth();

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量宽度
        if (widthMode == MeasureSpec.AT_MOST)
            width = defaultSize;
        else
            width = widthSize;

        //测量高度
        if (heightMode == MeasureSpec.AT_MOST)
            height = defaultSize;
        else
            height = heightSize;

        //设置测量的宽高值
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);


        width = w;
        height = h;
        //中心点坐标
        CENTER_X = width / 2;

        float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f;
        oval = new RectF(-r, -r, r, r);

        r1 = CENTER_X * 6 / 8;
        arc = new RectF(-r1, -r1, r1, r1);


        float r2 = CENTER_X * 5 / 8;
        float r3 = CENTER_X * 3 / 4;

        arc1 = new RectF(-r2, -r2, r2, r2);
        arc2 = new RectF(-r3, -r3, r3, r3);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        //设置画布绘图无锯齿
        canvas.setDrawFilter(pfd);

        canvas.save();
        canvas.translate(width / 2, height / 2);
        //画最外层的渐变圆环
        canvas.rotate(150);
        canvas.drawArc(oval, -mStartAngle, -mEndAngle, false, mPaint);
        canvas.restore();
        //绘制分割线
        int a = (int) (2 * CENTER_X - mPaint.getStrokeWidth());
        for (int i = 0; i <= 60; i++)
        {
            canvas.save();
            //每次旋转4度绘制分割线
            canvas.rotate(-(-30 + 4 * i), CENTER_X, CENTER_X);
            if (i % 10 == 0)
            {
                //分为6个区块进行绘制 每个区有10个小间隔
                canvas.drawLine(a, CENTER_X, 2 * CENTER_X, CENTER_X, paintGap2);
            } else
            {
                canvas.drawLine(a, CENTER_X, 2 * CENTER_X, CENTER_X, paintGap1);
            }
            canvas.restore();
        }


        //绘制内圈圆形
        canvas.save();
        canvas.translate(CENTER_X, CENTER_X);
        canvas.rotate(150);
        canvas.drawArc(arc1, -mStartAngle, -mEndAngle, false, paintInnerCircle);
        canvas.drawArc(arc2, -mStartAngle, -mEndAngle, false, paintMiddleCircle);
        canvas.restore();

        //绘制圆弧上的文字
        for (int i = 0; i <= 12; i++)
        {
            canvas.save();
            canvas.rotate(-(-30 + 20 * i - 88), CENTER_X, CENTER_X);
            canvas.drawText(text[i], CENTER_X - 10, CENTER_X * 3 / 16, paintText);
            canvas.restore();
        }

        //绘制圆中心数字文字
        //绘制logo
        centerTextPaint.setTextSize(30);
        centerTextPaint.setColor(Color.GRAY);
        canvas.drawText("BETA", CENTER_X, CENTER_X - 130, centerTextPaint);
        //绘制信用分数
        centerTextPaint.setColor(GREEN_COLOR);
        centerTextPaint.setTextSize(200);
        centerTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(String.valueOf(num), CENTER_X, CENTER_X + 70, centerTextPaint);
        //绘制信用级别
        centerTextPaint.setColor(GREEN_COLOR);
        centerTextPaint.setTextSize(80);
        canvas.drawText("信用良好", CENTER_X, CENTER_X + 160, centerTextPaint);
        //绘制评估时间
        centerTextPaint.setColor(Color.GRAY);
        centerTextPaint.setTextSize(30);
        canvas.drawText("评估时间:2016-8-31", CENTER_X, CENTER_X + 205, centerTextPaint);


        canvas.save();
        canvas.translate(CENTER_X, CENTER_X);
        canvas.rotate(270);
        canvas.drawArc(arc, -mStartAngle, currentRotateAngle, false, paintMiddleArc);
        canvas.rotate(60 + currentRotateAngle);
        @SuppressLint("DrawAllocation") Matrix matrix = new Matrix();
        matrix.preTranslate(-r1 - bitmapWidth * 3 / 8, -bitmapHeight / 2);
        canvas.drawBitmap(bitmap, matrix, paintBitmap);
        canvas.restore();
    }

    int num = 150;

    public void startRotateAnim()
    {

        animator = ValueAnimator.ofFloat(currentRotateAngle, totalRotateAngle);
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                currentRotateAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {

                super.onAnimationEnd(animation);
            }
        });
        animator.start();


        ValueAnimator valueAnimator = ValueAnimator.ofInt(num, 700);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                num = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    public void onClick(View view)
    {

        startRotateAnim();
    }


    public int dp2px(int values)
    {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }
}
