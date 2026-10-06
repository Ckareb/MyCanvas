package com.example.mycanvas.ui.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mycanvas.model.Stroke;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {
    private Paint paint;

    private Path path;

    private List<Stroke> strokes = new ArrayList<>();

    private int colorCanvas = Color.BLACK;

    private int colorBrush = Color.WHITE;

    private float lastX;

    private float lastY;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(colorCanvas);
        strokes.forEach(stroke -> {
            paint.setColor(stroke.getColor());
            paint.setStrokeWidth(stroke.getWidth());

            canvas.drawPath(
                    stroke.getPath(),
                    paint
            );
        });

        if (path != null) {
            paint.setColor(colorBrush);
            paint.setStrokeWidth(10);

            canvas.drawPath(
                    path,
                    paint
            );
        }
    }

    public void setColorCanvas(int colorCanvas) {
        this.colorCanvas = colorCanvas;
        invalidate();
    }


    public void setColorBrush(int colorBrush) {
        this.colorBrush = colorBrush;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();

        float y = event.getY();

        float width = 5 + event.getSize() * 100;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(x, y);

                lastX = x;
                lastY = y;

                invalidate();

                return true;

            case MotionEvent.ACTION_MOVE:
                path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);

                lastX = x;
                lastY = y;

                invalidate();

                return true;

            case MotionEvent.ACTION_UP:
                strokes.add(
                        new Stroke(
                                path,
                                colorBrush,
                                width
                        )
                );

                path = null;

                invalidate();

                return true;
        }

        return true;
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);

        draw(canvas);

        return bitmap;
    }

    public void clear() {
        strokes.clear();
        invalidate();
    }
}
