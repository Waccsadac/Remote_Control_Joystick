package com.waccsadac.remotecontroljoystick.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Joystick extends View {
    private float outerX, outerY,innerX, innerY,outerRad,innerRad;
    Paint outerPaint, innerPaint;
    boolean pressed;
    public OnChangeStrategy onChangeStrategy;
    private void ini() {
        outerX = innerX = Math.min(getWidth(),getHeight()) / 2;
        outerY = innerY = Math.min(getWidth(),getHeight()) / 2;
        innerRad = Math.min(getWidth(),getHeight()) / 10;
        outerRad = Math.min(getWidth(),getHeight()) / 3;
        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //innerPaint.setColor(16738740);
        innerPaint.setColor(Color.WHITE);
        //outerPaint.setColor(8388736);
        outerPaint.setColor(Color.RED);


    }
    public Joystick(Context context) {
        super(context);
        ini();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        outerX = innerX = w / 2;
        outerY = innerY = h / 2;
        innerRad = Math.min(w,h) / 10;
        outerRad = Math.min(w,h)     / 3;
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ini();
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ini();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //innerPaint.setColor(16738740);
        //outerPaint.setColor(8388736);
        innerPaint.setColor(Color.WHITE);
        outerPaint.setColor(Color.RED);
        canvas.drawCircle(
                outerX,
                outerY,
                outerRad,
                outerPaint
        );

        canvas.drawCircle(
                innerX,
                innerY,
                innerRad,
                innerPaint
        );
    }

    public double calc_dist(double start_x, double start_y, double end_x, double end_y) {
        return Math.sqrt(Math.pow(start_x - end_x, 2) + Math.pow(start_y - end_y, 2));
    }

    public boolean isPressed(double touch_x, double touch_y) {
        return calc_dist(
                touch_x, touch_y,
                outerX, outerY) <= outerRad;
    }

    public void setPosition(float x, float y, boolean trigger) {
        if (!isPressed(x,y)){
            float delta_x = x - outerX;
            float delta_y = y - outerY;
            double dist = Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2));
            innerX = outerX + (float) (delta_x / dist) * outerRad;
            innerY = outerY + (float) (delta_y / dist) * outerRad;
        } else {
            innerX = x;
            innerY = y;
        }
        postInvalidate();
        if(trigger) {
            onChangeStrategy.onChange((innerX - outerX)/outerRad,
                    (innerY - outerY)/outerRad);
        }
    }

    public void reset(boolean trigger){
        innerX = outerX;
        innerY = outerY;
        postInvalidate();
        if(trigger) {
            onChangeStrategy.onChange(0,0);
        }
    }

    public float getOuterRad(){
        return outerRad;
    }

    public float getOuterX(){
        return outerX;
    }

    public float getOuterY(){
        return outerX;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_UP:
                pressed = false;
                reset(true);
                return true;
            case MotionEvent.ACTION_DOWN:
                pressed = isPressed(event.getX(), event.getY());
            case MotionEvent.ACTION_MOVE:
                if(isPressed(event.getX(), event.getY()) || pressed) {

                    setPosition(event.getX(),
                            event.getY(), false);

                    onChangeStrategy.onChange((innerX - outerX)/outerRad,
                            (innerY - outerY)/outerRad);
                    Log.d("innerX2",Float.toString(innerX));
                    Log.d("innerX3",Float.toString(outerX));
                    Log.d("innerX4",Float.toString(outerRad));
                }
                return true;
        }
        return super.onTouchEvent(event);
    }
}
