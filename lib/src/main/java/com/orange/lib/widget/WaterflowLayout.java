package com.orange.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class WaterflowLayout extends ViewGroup {
    private List<Integer> mLineHeights = new ArrayList<>();
    private List<List<View>> mLineViews = new ArrayList<>();

    public WaterflowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curTop = 0, curLeft = 0;
        int left, top, right, bottom;
        for (int i = 0; i < mLineViews.size(); i++) {
            List<View> lineView = mLineViews.get(i);
            for (int j = 0; j < lineView.size(); j++) {
                View item = lineView.get(j);
                MarginLayoutParams lp = (MarginLayoutParams) item.getLayoutParams();
                left = curLeft + lp.leftMargin;
                top = curTop + lp.topMargin;
                right = left + item.getMeasuredWidth();
                bottom = top + item.getMeasuredHeight();
                item.layout(left, top, +right, bottom);
                curLeft += lp.leftMargin + item.getMeasuredWidth() + lp.rightMargin;
            }
            curTop += mLineHeights.get(i);
            curLeft = 0;
        }
        mLineViews.clear();
        mLineHeights.clear();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //size
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int measureWidth = 0, measureHeight = 0;

        if (MeasureSpec.EXACTLY == widthMode && MeasureSpec.EXACTLY == heightMode) {
            measureWidth = widthSize;
            measureHeight = heightSize;
        } else {
            int iWidht, iHeight = 0, iLineHeight = 0, iLineWidth = 0;
            int count = getChildCount();
            List<View> lineView = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                View childAt = getChildAt(i);
                measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                iWidht = childAt.getMeasuredWidth();
                iHeight = childAt.getMeasuredHeight();
                MarginLayoutParams lp = (MarginLayoutParams) childAt.getLayoutParams();
                boolean nextLine = false;
                if (iLineWidth > widthSize) {
                    nextLine = true;
                    //换行
                    mLineHeights.add(iLineHeight);
                    measureHeight += iLineHeight;
                    measureWidth = Math.max(iLineWidth, measureWidth);

                    iLineHeight = 0;
                    iLineWidth = 0;

                    mLineViews.add(lineView);
                    lineView = new ArrayList<>();
                    lineView.add(childAt);
                } else {
                    lineView.add(childAt);
                    iLineHeight = Math.max(iLineHeight, lp.topMargin + lp.bottomMargin + iHeight);
                    iLineWidth += lp.leftMargin + lp.rightMargin + iWidht;
                    if (i == count - 1 && !nextLine) {
                        mLineHeights.add(iLineHeight);
                        mLineViews.add(lineView);
                        measureHeight += iLineHeight;
                        measureWidth = Math.max(iLineWidth, measureWidth);
                    }
                    nextLine = false;
                }
            }
        }

        System.out.println("WaterflowLayout.onMeasure-measureWidth: " + measureWidth + ", measureHeightResult: " + measureHeight);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
