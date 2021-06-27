package com.waccsadac.remotecontroljoystick.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Joystick extends View {
    private float outerX, outerY,innerX, innerY,outerRad,innerRad;
    Paint outerPaint, innerPaint;
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

    public void setPosition(float x, float y) {
        if (isPressed(x,y)){
            float delta_x = x - outerX;
            float delta_y = y - outerY;
            double dist = Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2));
            innerX = innerY + (float) (delta_x / dist) * outerRad;
            innerY = outerY + (float) (delta_y / dist) * outerRad;
        } else {
            innerY = x;
            innerY = y;
        }
        postInvalidate();
    }

    public void reset(){
        innerX = outerX;
        innerY = outerY;
        postInvalidate();
    }


}
