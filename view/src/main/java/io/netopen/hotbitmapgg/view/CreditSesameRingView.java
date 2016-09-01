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
 * Created by hcc on 2016/8/31.
 * <p/>
 * 仿芝麻信用的圆环实现
 */
public class CreditSesameRingView extends View implements View.OnClickListener
{

    // 最外层圆环渐变色环颜色
    private final int[] mColors = new int[]{
            0xFFFF0000,
            0xFFFFFF00,
            0xFF00FF00,
            0xFF00FFFF,
            0xFF0000FF,
            0xFFFF00FF
    };

    //圆环的信用等级文字
    private static final String[] text = {
            "950", "极好",
            "700", "优秀",
            "650", "良好",
            "600", "中等",
            "550", "较差",
            "350", "很差",
            "150"
    };

    //中间进度颜色
    private static final int GREEN_COLOR = 0xFF00D4AF;

    // View宽度
    private int width;

    // View高度
    private int height;

    // 圆环半径
    private int radius;

    // 指针图片
    private Bitmap mBitmap;

    // 指针图片宽度
    private int mBitmapWidth;

    // 指针图片高度
    private int mBitmapHeight;

    // 最外层渐变圆环画笔
    private Paint mGradientRingPaint;

    // 大刻度画笔
    private Paint mSmallCalibrationPaint;

    // 小刻度画笔
    private Paint mBigCalibrationPaint;

    // 中间进度圆环画笔
    private Paint mMiddleRingPaint;

    // 内虚线圆环画笔
    private Paint mInnerRingPaint;

    // 外层圆环文本画笔
    private Paint mTextPaint;

    // 中间进度圆环画笔
    private Paint mMiddleProgressPaint;

    // 指针图片画笔
    private Paint mPointerBitmapPaint;

    //中间文本画笔
    private Paint mCenterTextPaint;

    // 绘制外层圆环的矩形
    private RectF mOuterArc;

    // 绘制内层圆环的矩形
    private RectF mInnerArc;

    // 绘制中间圆环的矩形
    private RectF mMiddleArc;

    // 中间进度圆环的值
    private float oval4;

    // 绘制中间进度圆环的矩形
    private RectF mMiddleProgressArc;

    // 圆环起始角度
    private float mStartAngle = 120f;

    // 圆环结束角度
    private float mEndAngle = 240f;

    // 指针全部进度
    private float mTotalAngle = 240f;

    // 指针当前进度
    private float mCurrentAngle = 0f;

    // View默认宽高值
    private int defaultSize;

    // 最小数字
    private int mMinNum = 150;

    // 最大数字
    private int mMaxNum = 950;

    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;


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

    /**
     * 初始化
     */
    private void init()
    {
        //设置默认宽高值
        defaultSize = dp2px(250);

        //初始化圆环渐变色渲染
        Shader mShader = new SweepGradient(0, 0, mColors, null);

        //设置图片线条的抗锯齿
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter
                (0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        //最外层圆环渐变画笔设置
        mGradientRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientRingPaint.setShader(mShader);
        mGradientRingPaint.setStrokeCap(Paint.Cap.SQUARE);
        mGradientRingPaint.setStyle(Paint.Style.STROKE);
        mGradientRingPaint.setStrokeWidth(40);

        //最外层圆环刻度画笔设置
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setStrokeWidth(1);
        mBigCalibrationPaint.setColor(Color.WHITE);
        mBigCalibrationPaint.setStrokeWidth(3);

        //中间圆环画笔设置
        mMiddleRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleRingPaint.setStyle(Paint.Style.STROKE);
        mMiddleRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mMiddleRingPaint.setStrokeWidth(4);
        mMiddleRingPaint.setColor(Color.GRAY);

        //内层圆环画笔设置
        mInnerRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerRingPaint.setStyle(Paint.Style.STROKE);
        mInnerRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerRingPaint.setStrokeWidth(4);
        mInnerRingPaint.setColor(Color.GRAY);
        PathEffect mPathEffect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mInnerRingPaint.setPathEffect(mPathEffect);

        //外层圆环文本画笔设置
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(25);

        //中间文字画笔设置
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);

        //中间圆环进度画笔设置
        mMiddleProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleProgressPaint.setColor(GREEN_COLOR);
        mMiddleProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mMiddleProgressPaint.setStrokeWidth(4);
        mMiddleProgressPaint.setStyle(Paint.Style.STROKE);

        //指针图片画笔
        mPointerBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //获取指针图片
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pointer);
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(resolveMeasure(widthMeasureSpec, defaultSize),
                resolveMeasure(heightMeasureSpec, defaultSize));
    }


    /**
     * 根据传入的值进行测量
     *
     * @param measureSpec
     * @param defaultSize
     */
    public int resolveMeasure(int measureSpec, int defaultSize)
    {

        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec))
        {

            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;

            case MeasureSpec.AT_MOST:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                break;

            default:
                result = defaultSize;
        }

        return result;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);

        //确定View宽高
        width = w;
        height = h;
        //圆环半径
        radius = width / 2;
        // 外层圆环
        float oval1 = radius - mGradientRingPaint.getStrokeWidth() * 0.5f;
        mOuterArc = new RectF(-oval1, -oval1, oval1, oval1);
        //中间和内层圆环
        float oval2 = radius * 5 / 8;
        float oval3 = radius * 3 / 4;
        mInnerArc = new RectF(-oval2, -oval2, oval2, oval2);
        mMiddleArc = new RectF(-oval3, -oval3, oval3, oval3);
        //中间进度圆环
        oval4 = radius * 6 / 8;
        mMiddleProgressArc = new RectF(-oval4, -oval4, oval4, oval4);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        //设置画布绘图无锯齿
        canvas.setDrawFilter(mPaintFlagsDrawFilter);

        canvas.save();
        canvas.translate(width / 2, height / 2);
        //画最外层的渐变圆环
        canvas.rotate(150);
        canvas.drawArc(mOuterArc, -mStartAngle, -mEndAngle, false, mGradientRingPaint);
        canvas.restore();
        //绘制刻度线
        int dst = (int) (2 * radius - mGradientRingPaint.getStrokeWidth());
        for (int i = 0; i <= 60; i++)
        {
            canvas.save();
            //每次旋转4度绘制分割线
            canvas.rotate(-(-30 + 4 * i), radius, radius);
            if (i % 10 == 0)
            {
                //分为6个区块进行绘制 每个区有10个小间隔
                canvas.drawLine(dst, radius, 2 * radius, radius, mBigCalibrationPaint);
            } else
            {
                canvas.drawLine(dst, radius, 2 * radius, radius, mSmallCalibrationPaint);
            }
            canvas.restore();
        }


        //绘制内圈圆形
        canvas.save();
        canvas.translate(radius, radius);
        canvas.rotate(150);
        canvas.drawArc(mInnerArc, -mStartAngle, -mEndAngle, false, mInnerRingPaint);
        canvas.drawArc(mMiddleArc, -mStartAngle, -mEndAngle, false, mMiddleRingPaint);
        canvas.restore();

        //绘制圆弧上的文字
        for (int i = 0; i <= 12; i++)
        {
            canvas.save();
            canvas.rotate(-(-30 + 20 * i - 88), radius, radius);
            canvas.drawText(text[i], radius - 10, radius * 3 / 16, mTextPaint);
            canvas.restore();
        }

        //绘制圆中心数字文字
        //绘制logo
        mCenterTextPaint.setTextSize(30);
        mCenterTextPaint.setColor(Color.GRAY);
        canvas.drawText("BETA", radius, radius - 130, mCenterTextPaint);
        //绘制信用分数
        mCenterTextPaint.setColor(GREEN_COLOR);
        mCenterTextPaint.setTextSize(200);
        mCenterTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(String.valueOf(mMinNum), radius, radius + 70, mCenterTextPaint);
        //绘制信用级别
        mCenterTextPaint.setColor(GREEN_COLOR);
        mCenterTextPaint.setTextSize(80);
        canvas.drawText("信用良好", radius, radius + 160, mCenterTextPaint);
        //绘制评估时间
        mCenterTextPaint.setColor(Color.GRAY);
        mCenterTextPaint.setTextSize(30);
        canvas.drawText("评估时间:2016-8-31", radius, radius + 205, mCenterTextPaint);


        canvas.save();
        canvas.translate(radius, radius);
        canvas.rotate(270);
        canvas.drawArc(mMiddleProgressArc, -mStartAngle, mCurrentAngle, false, mMiddleProgressPaint);
        canvas.rotate(60 + mCurrentAngle);
        @SuppressLint("DrawAllocation") Matrix matrix = new Matrix();
        matrix.preTranslate(-oval4 - mBitmapWidth * 3 / 8, -mBitmapHeight / 2);
        canvas.drawBitmap(mBitmap, matrix, mPointerBitmapPaint);
        canvas.restore();
    }


    public void startRotateAnim()
    {

        ValueAnimator mAnimator = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setDuration(3000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {

                super.onAnimationEnd(animation);
            }
        });
        mAnimator.start();


        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMinNum, mMaxNum);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mMinNum = (int) valueAnimator.getAnimatedValue();
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
