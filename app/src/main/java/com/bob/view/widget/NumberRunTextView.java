package com.bob.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bob.view.R;

/**
 * Create by bob on 2018/10/18
 */
public class NumberRunTextView extends TextView implements ValueAnimator.AnimatorUpdateListener {

    private String mValueType;
    private String INTEGER = "INTEGER";
    private String FLOAT = "FLOAT";
    private ValueAnimator intAnimator;

    public NumberRunTextView(Context context) {
        this(context, null);
    }

    public NumberRunTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public NumberRunTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumberRunTextView);
        int duration = ta.getInt(R.styleable.NumberRunTextView_nt_duration, 1000);
        ta.recycle();
        intAnimator = new ValueAnimator();
        intAnimator.setDuration(duration);//一秒内完成动画
        intAnimator.addUpdateListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        String valueStr;
        if (INTEGER.equals(mValueType)) {
            valueStr = String.valueOf(animation.getAnimatedValue());
        } else if (FLOAT.equals(mValueType)) {
            valueStr = String.format("%.2f", animation.getAnimatedValue());
        } else {
            throw new IllegalArgumentException("valueStr is null");
        }
        setText(valueStr);
    }

    /**
     * 设置动画时长
     *
     * @param duration 毫秒
     * @return
     */
    public NumberRunTextView setDuration(int duration) {
        intAnimator.setDuration(duration);//一秒内完成动画
        return this;
    }

    /**
     * 设置整数值
     *
     * @param value
     * @return
     */
    public NumberRunTextView setIntValue(int value) {
        intAnimator.setIntValues(value);
        this.mValueType = INTEGER;
        return this;
    }

    /**
     * 设置浮点数据值
     *
     * @param value
     * @return
     */
    public NumberRunTextView setFloatValue(float value) {
        intAnimator.setFloatValues(value);
        this.mValueType = FLOAT;
        return this;
    }

    /**
     * 开始执行动画
     *
     * @return
     */
    public void startAnim() {
        intAnimator.start();
    }
}