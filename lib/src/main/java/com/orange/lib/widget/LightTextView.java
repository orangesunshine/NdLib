package com.orange.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class LightTextView extends AppCompatTextView {
    private Matrix mMatrix;
    private LinearGradient mLinearGradient;
    private float mTransaction;
    private float DELTA = 20;
    private float xOffset;

    public LightTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mMatrix = new Matrix();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextPaint paint = getPaint();
        float size = paint.measureText(getText().toString());
        xOffset = size / getText().length() * 5;
        mTransaction = xOffset;
        mLinearGradient = new LinearGradient(-xOffset, 0, 0, 0, new int[]{Color.WHITE, Color.RED, Color.WHITE}, null, Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTransaction < xOffset || mTransaction + DELTA > getWidth())
            DELTA = -DELTA;
        mTransaction += DELTA;
        mMatrix.setTranslate(mTransaction, 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(100);
    }
}
