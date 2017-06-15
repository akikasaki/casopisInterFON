package com.interfon.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import static com.interfon.android.utils.Util.dp;
import static com.interfon.android.utils.Util.sp;

public class UnderlinedTextView extends android.support.v7.widget.AppCompatTextView {

    private int borderWidthLeft = dp(4, getResources());

    private int borderWidthRight = dp(4, getResources());

    private int borderWidthTop = dp(4, getResources());

    private int borderWidthBottom = dp(4, getResources());

    private int boderColor = Color.BLACK;

    private int backgroundColor = Color.BLUE;

    private int textColor = Color.WHITE;

    private int textSize = sp(30, getResources());

    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    private int backgroundRectWidth = dp(35, getResources());

    private int backgroundRectHeight = dp(35, getResources());

    private Rect textBgRect = new Rect();


    public UnderlinedTextView(Context context) {
        this(context, null, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        backgroundPaint.setColor(backgroundColor);
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);
        textPaint.setFakeBoldText(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
//        drawText(canvas);
    }

    private void drawBackground(Canvas canvas) {
//        canvas.drawColor(boderColor);
//        int left = borderWidthLeft;
//        int top = borderWidthTop;
//        int right = borderWidthLeft + backgroundRectWidth;
//        int bottom = borderWidthTop + backgroundRectHeight;
//        textBgRect.set(left, top, right, bottom);
//        canvas.save();
//        canvas.clipRect(textBgRect, Region.Op.REPLACE);
//        canvas.drawRect(textBgRect, backgroundPaint);
//        canvas.restore();
        canvas.drawLine(0,100,0,100,textPaint);
    }

    private void drawText(Canvas canvas) {
        int bgCenterX = borderWidthLeft + backgroundRectWidth / 2;
        int bgCenterY = borderWidthTop + backgroundRectHeight / 2;
        Paint.FontMetrics metric = textPaint.getFontMetrics();
        int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
        int x = bgCenterX;
        int y = (int) (bgCenterY + textHeight / 2 - metric.descent);
        System.out.println(textHeight);
        System.out.println(y);
        System.out.println(bgCenterY);
        canvas.drawText(null, x, y, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(backgroundRectWidth + borderWidthLeft + borderWidthRight,
//                backgroundRectHeight + borderWidthTop + borderWidthBottom);
    }
}