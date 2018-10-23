package com.bob.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.bob.view.R;

/**
 * Create by bob on 2018/10/18
 */
public class StatisticsView extends View implements ValueAnimator.AnimatorUpdateListener {

    private int angle;//角度值
    private int mRadian;  //弧度为值
    private Paint mPaint;
//            mPointerPaint;//画笔
    private int[] mColors = {0xffDF0D30, 0xffF69729, 0xffD4D03B, 0xff3AEC26, 0xff5AFFEF};//弧形线段默认颜色
    private int mStrokeWidth;// 半圆形边缘宽度
    private int mPointerWidth;//指针宽度
    private int currentNum;//记录值动画执行过程中的跑动值
    private int duration;//动画时长
    private int mColor;

    public StatisticsView(Context context) {
        this(context, null);
    }

    public StatisticsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Statistics_View, defStyle, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.Statistics_View_radian) {
                mRadian = array.getInt(attr, 180);
            } else if (attr == R.styleable.Statistics_View_strokeWidth) {
                mStrokeWidth = array.getInt(attr, 5);
            } else if (attr == R.styleable.Statistics_View_pointerWidth) {
                mPointerWidth = array.getInt(attr, 5);
            } else if (attr == R.styleable.Statistics_View_sv_duration) {
                duration = array.getInt(attr, 1000);
            } else if (attr == R.styleable.Statistics_View_pointerColor) {
                mColor = array.getColor(attr, getResources().getColor(R.color.color_red));
            }
        }
        array.recycle();
        initPaint();
    }

    private void initPaint() {
//        mPointerPaint = new Paint();
//        mPointerPaint.setColor(mColor);
//        mPointerPaint.setAntiAlias(true);
//        mPointerPaint.setStyle(Paint.Style.FILL);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //设置圆形边缘平滑填充
        mPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 设置指针角度值
     *
     * @param value
     * @return
     */
    public StatisticsView setAngleValue(int value) {
        this.angle = value;
        return this;
    }

    /**
     * 颜色资源数组
     *
     * @param colorArrayId
     * @return
     */
    public StatisticsView setColors(int colorArrayId) {
        this.mColors = getContext().getResources().getIntArray(colorArrayId);
        return this;
    }

    /**
     * 设置动画时长
     *
     * @param duration 毫秒
     * @return
     */
    public StatisticsView setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置弧度值
     */
    public StatisticsView setRadian(int radian) {
        this.mRadian = radian;
        return this;
    }

//    /**
//     * 设置指针和指针中心圆点颜色
//     *
//     * @param resColor
//     * @return
//     */
//    public StatisticsView setPointerAndArcColor(int resColor) {
//        int color = getResources().getColor(resColor);
//        mPointerPaint.setColor(color);
//        mPaint.setColor(color);
//        return this;
//    }
//
//    /**
//     * 设置指针中心圆点颜色
//     *
//     * @param resColor
//     * @return
//     */
//    public StatisticsView setPointerArcColor(int resColor) {
//        int color = getResources().getColor(resColor);
//        mPointerPaint.setColor(color);
//        return this;
//    }

    /**
     * 设置指针颜色
     *
     * @param resColor
     * @return
     */
    public StatisticsView setPointerColor(int resColor) {
        int color = getResources().getColor(resColor);
        mPaint.setColor(color);
        return this;
    }

    /**
     * 设置半圆环厚度
     *
     * @param strokeWidth
     * @return
     */
    public StatisticsView setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        return this;
    }

    /**
     * 刷新View
     *
     * @return
     */
    public StatisticsView refresh() {
        invalidate();
        return this;
    }

    /**
     * 开始执行动画
     *
     * @return
     */
    public void startAnim() {
        ValueAnimator intAnimator = new ValueAnimator().ofInt(0, angle);//设置起始值到终点值
        intAnimator.setDuration(duration);//一秒内完成动画
        intAnimator.addUpdateListener(this);
        intAnimator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        currentNum = (int) animation.getAnimatedValue();
        StatisticsView.this.postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resultWidth;

        // 获取宽度测量规格中的mode
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        // 获取宽度测量规格中的size
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        //如果是明确值
        if (modeWidth == MeasureSpec.EXACTLY) {
            //直接赋值指定,如300dp
            resultWidth = sizeWidth;
        } else {//如果值是wrap或者match_parent
            // 设置padding值
            resultWidth = getPaddingLeft() + getMeasuredWidth() + getPaddingRight();
            //wrap类型
            if (modeWidth == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, sizeWidth);
            }
        }

        int resultHeight;
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (modeHeight == MeasureSpec.EXACTLY) {
            resultHeight = sizeHeight;
        } else {
            resultHeight = getPaddingTop() + sizeHeight + getPaddingBottom();
            if (modeHeight == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, sizeHeight);
            }
        }
        // 测量尺寸
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制弧形线段连接成半圆
        drawSemicircle(canvas);
        //绘制指针
        drawPointer(canvas);
    }

    /**
     * 画指针
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {

        int height = getHeight();
        int width = getWidth();

        //指针起始中心点:取x轴宽的一半为,Y轴高的顶点,得到半圆的中心位置
        float startX = width / 2;
        float startY = height;

        //设置指针线条的宽度和颜色
        mPaint.setStrokeWidth(mPointerWidth);
        mPaint.setColor(mColor);

        //指针起始结束点:停留在x,Y轴的另一顶点位置为:x轴-半圆的宽度
        float stopX = mStrokeWidth * 3;
        float stopY = height;

        // 调试指针角度的值
        canvas.rotate(currentNum, startX, startY);
        // 绘制指针
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);

//        绘制指针点心圆点
//        canvas.drawCircle(startX, height, stopX, mPointerPaint);
    }

    /**
     * 画半圆
     *
     * @param canvas
     */
    private void drawSemicircle(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
        //为了避免边缘被遮挡,设置left,top,right三边的padding为5
        float padding = 5;

        float left = padding;
        float top = padding;
        float right = width - padding;
        float bottom = height * 2;

        //设置半圆宽度
        mPaint.setStrokeWidth(mStrokeWidth);
        RectF rectF = new RectF(left, top, right, bottom);
        //设置半圆弧形线段渲染颜色,dis_move=180/5=36
        int dis_move = mRadian / mColors.length;
        for (int i = 0; i < mColors.length; i++) {
            mPaint.setColor(mColors[i]);//红
            //在第一条线段起,每下一条接着上一条结束位置处渲染开始
            int startAngle = mRadian + (dis_move * i);
            canvas.drawArc(rectF, startAngle, dis_move, false, mPaint);
        }
    }
}
