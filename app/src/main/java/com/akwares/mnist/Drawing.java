package com.akwares.mnist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ak on 22/02/18.
 */

public class Drawing extends View {

    private Paint paint;
    private Path path;

    public Drawing(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    private void initialize(){
        paint = new Paint();
        path = new Path();


        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(40f);
    }

    public void clear(){
        initialize();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xpos = event.getX();
        float ypos = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xpos, ypos);
                return true;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(xpos, ypos);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }
}
