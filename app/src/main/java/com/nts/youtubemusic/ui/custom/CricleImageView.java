package com.nts.youtubemusic.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bumptech.glide.Glide;

public class CricleImageView extends androidx.appcompat.widget.AppCompatImageView {

    public RectF rect;
    public Path path;
    public int image;

    private final float radius = 100.0f;

    private void init() {
        path = new Path();

    }

    public void setImage(int image) {
        this.image = image;
    }


    public CricleImageView(Context context) {
        super(context);
        init();
    }

    public CricleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CricleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public void showImage(){
        Glide.with(getContext()).load(image).into(this);
    }


    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CCW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    float dX, dY;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = this.getX() - event.getRawX();
                dY = this.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                this.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
            default:
                return false;
        }
        return true;
    }
}

