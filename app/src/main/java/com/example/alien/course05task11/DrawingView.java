package com.example.alien.course05task11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private int mWidthSpecSize;
    private int mHeightSpecSize;
    private GestureDetector mGestureDetector;
    private Paint mMainPaint;
    private Line mLastLine;
    private List<Line> mAllLines;

    public DrawingView(Context context) {
        super(context);
        init(context, null);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mLastLine = new Line();
        mAllLines = new ArrayList<>();

        mMainPaint = new Paint();
        mMainPaint.setColor(Color.BLACK);
        mMainPaint.setStyle(Paint.Style.STROKE);
        mMainPaint.setStrokeWidth(10);

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                if (!mLastLine.isEmpty()) {
                    mAllLines.add(new Line(mLastLine));
                }
                mLastLine.clear();
                mLastLine.addPoint(e);
                mLastLine.setPaint(mMainPaint);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mLastLine.addPoint(e2);
                invalidate();
                return true;
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidthSpecSize, mHeightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Line line : mAllLines) {
            line.drawLine(canvas);
        }
        mLastLine.drawLine(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class Line {
        private List<Point> mPoints;
        private Paint mPaint;

        public Line() {
            mPoints = new ArrayList<>();
        }

        public Line(Line line) {
            mPoints = new ArrayList<>(line.getPoints());
            mPaint = new Paint(line.getPaint());
        }

        public Line(List<Point> points, Paint paint) {
            this();
            mPoints = points;
            mPaint = paint;
        }

        public List<Point> getPoints() {
            return mPoints;
        }

        public void setPoints(List<Point> points) {
            mPoints = points;
        }

        public Paint getPaint() {
            return mPaint;
        }

        public void setPaint(Paint paint) {
            mPaint = paint;
        }

        public boolean isEmpty() {
            return mPoints.isEmpty();
        }

        public void clear() {
            mPoints.clear();
        }

        private void addPoint(MotionEvent event) {
            mPoints.add(new Point(
                    (int) event.getAxisValue(MotionEvent.AXIS_X),
                    (int) event.getAxisValue(MotionEvent.AXIS_Y))
            );
        }

        private void drawLine(Canvas canvas) {
            for (int i = 1, size = mPoints.size(); i < size; i++) {
                canvas.drawLine(mPoints.get(i - 1).x, mPoints.get(i - 1).y, mPoints.get(i).x, mPoints.get(i).y, mMainPaint);
            }
        }
    }
}
