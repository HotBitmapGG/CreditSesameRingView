package io.netopen.hotbitmapgg.view;

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

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by hcc on 16/9/1 22:21
 * 100332338@qq.com
 * <p/>
 * 仿芝麻信用的圆环实现(旧版)
 */
public class CreditSesameRingView extends View
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
            "350"
    };
    //, "很差", "150"

    //中间进度颜色
    private static final int GREEN_COLOR = 0xFF06C494;

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
    private float mStartAngle = 115f;

    // 圆环结束角度
    private float mEndAngle = 230f;

    // 指针全部进度
    private float mTotalAngle = 240f;

    // 指针当前进度
    private float mCurrentAngle = 0f;

    // View默认宽高值
    private int defaultSize;

    // 最小数字
    private int mMinNum = 350;

    // 最大数字
    private int mMaxNum = 950;

    private String sesameLevel = "";

    private String evaluationTime = "";

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
        mMiddleRingPaint.setStrokeWidth(5);
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
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setTextSize(30);

        //中间文字画笔设置
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);

        //中间圆环进度画笔设置
        mMiddleProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleProgressPaint.setColor(GREEN_COLOR);
        mMiddleProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mMiddleProgressPaint.setStrokeWidth(5);
        mMiddleProgressPaint.setStyle(Paint.Style.STROKE);

        //指针图片画笔
        mPointerBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerBitmapPaint.setColor(GREEN_COLOR);

        //获取指针图片
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pointer);
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
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
        canvas.rotate(140);
        canvas.drawArc(mOuterArc, -mStartAngle, -mEndAngle, false, mGradientRingPaint);

        //绘制内圈圆形
        canvas.drawArc(mInnerArc, -mStartAngle, -mEndAngle, false, mInnerRingPaint);
        canvas.drawArc(mMiddleArc, -mStartAngle, -mEndAngle, false, mMiddleRingPaint);
        canvas.restore();

        //绘制刻度线
        int dst = (int) (2 * radius - mGradientRingPaint.getStrokeWidth());
        for (int i = 0; i <= 50; i++)
        {
            canvas.save();
            canvas.rotate(-(-10 + 4 * i), radius, radius);
            if (i % 10 == 0)
            {
                canvas.drawLine(dst, radius, 2 * radius, radius, mBigCalibrationPaint);
            } else
            {
                canvas.drawLine(dst, radius, 2 * radius, radius, mSmallCalibrationPaint);
            }
            canvas.restore();
        }


        //绘制圆弧上的文字
        for (int i = 0; i <= 10; i++)
        {
            canvas.save();
            canvas.rotate(-(-10 + 20 * i - 88), radius, radius);
            canvas.drawText(text[i], radius - 10, radius * 3 / 16, mTextPaint);
            canvas.restore();
        }

        //绘制Logo
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
        canvas.drawText(sesameLevel, radius, radius + 160, mCenterTextPaint);

        //绘制评估时间
        mCenterTextPaint.setColor(Color.GRAY);
        mCenterTextPaint.setTextSize(30);
        canvas.drawText(evaluationTime, radius, radius + 205, mCenterTextPaint);

        //绘制中间进度和指针图片
        canvas.save();
        canvas.translate(radius, radius);
        canvas.rotate(270);
        canvas.drawArc(mMiddleProgressArc, -mStartAngle, mCurrentAngle, false, mMiddleProgressPaint);
        canvas.rotate(68 + mCurrentAngle);
        @SuppressLint("DrawAllocation") Matrix matrix = new Matrix();
        matrix.preTranslate(-oval4 - mBitmapWidth * 3 / 8, -mBitmapHeight / 2);
        canvas.drawBitmap(mBitmap, matrix, mPointerBitmapPaint);
        canvas.restore();
    }


    /**
     * 设置芝麻信用数据
     *
     * @param num
     */
    public void setSesameData(int num)
    {

        if (num <= 350)
        {
            mMaxNum = num;
            mTotalAngle = 0f;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (num <= 550)
        {
            mMaxNum = num;
            mTotalAngle = (num - 350) * 80 / 400f;
            sesameLevel = "信用良好";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (num <= 700)
        {
            mMaxNum = num;
            mTotalAngle = (num - 550) * 120 / 150f + 65;
            sesameLevel = "信用优秀";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (num <= 950)
        {
            mMaxNum = num;
            mTotalAngle = (num - 700) * 40 / 250f + 185;
            sesameLevel = "信用极好";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else
        {
            mTotalAngle = 240f;
        }

        startRotateAnim();
    }


    /**
     * 开始指针旋转动画
     */
    public void startRotateAnim()
    {

        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        mAngleAnim.setInterpolator(new BounceInterpolator());
        mAngleAnim.setDuration(3000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAngleAnim.start();


        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(3000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }


    /**
     * dp2px
     *
     * @param values
     * @return
     */
    public int dp2px(int values)
    {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentTime()
    {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd");
        return format.format(new Date());
    }
}
