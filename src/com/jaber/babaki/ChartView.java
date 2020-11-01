package com.jaber.babaki;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
   /*
    * Designed by J. Babaki
    * 1394/03/10
    */

public class ChartView extends ImageView {

    private Paint                  paint;
    private Paint                  cpaint;
    private static final int       DATA_COUNT = 1000;
    private float                  xStep      = 5;
    // private static float[]         data       = new float[DATA_COUNT];
    static ArrayList<Float>        data       = new ArrayList<Float>();
    public static ArrayList<Float> mn         = new ArrayList<Float>();
    private static int             n          = 0;
    private static float           jump;
    private static boolean         check;
    static int                     half       = 0;
    static int                     lo         = 0;
    int                            endIndex   = 0;
    float[]                        ArrayData;


    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();

    }


    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }


    public ChartView(Context context) {
        super(context);
        initialize();
    }


    public static void generateNumber(float speed) {
        int tiger = (int) speed;

        if (tiger >= 0 && tiger <= 5) {
            tiger = 5 * 3;
        }
        else if (tiger >= 6 && tiger <= 8) {
            tiger = 8 * 3;
        }
        else if (tiger >= 9 && tiger <= 12) {
            tiger = 10 * 3;
        }
        else if (tiger >= 13 && tiger <= 15) {
            tiger = 12 * 3;
        }
        else if (tiger >= 16 && tiger <= 50) {
            tiger = 20 * 3;
        }
        else if (tiger >= 51 && tiger <= 70) {
            tiger = 25 * 3;
        }
        else if (tiger >= 70 && tiger <= 100) {
            tiger = 35 * 3;

        }
        else if (tiger >= 101 && tiger <= 200) {
            tiger = 45 * 3;

        }
        else if (tiger >= 201 && tiger <= 500) {
            tiger = 60 * 3;

        }
        else if (tiger >= 501 && tiger <= 900) {
            tiger = 80 * 3;

        }
        else {
            tiger = 100 * 2;

        }

        data.add((float) (tiger));
        data.add((float) ( -tiger));

    }


    private void initialize() {
        data.add((float) (getWidth() / 2));
        endIndex = data.size();
        paint = new Paint();
        paint.setColor(Color.parseColor("#BE5A7C"));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Style.STROKE);
        paint.setTextAlign(Align.CENTER);
        paint.setTextSize(13);

        cpaint = new Paint();
        cpaint.setColor(Color.parseColor("#696969"));
        cpaint.setAntiAlias(true);
        cpaint.setStrokeWidth(2);
        cpaint.setStyle(Style.STROKE);
        cpaint.setTextAlign(Align.CENTER);
        cpaint.setTextSize(13);

    }

    private float go = 150.0f;


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (go == 150.f) {
            jump = getHeight() / 2;
        }

        cpaint.setStrokeWidth(5);
        canvas.drawLine(getWidth() - 150, 0, getWidth() - 150, getHeight(), cpaint);
        canvas.drawCircle(getWidth(), getHeight() / 2, 150, cpaint);
        paint.setColor(Color.parseColor("#F1E9D6"));
        paint.setStrokeWidth(30);
        canvas.drawLine(0, 0, getWidth(), 0, paint);

        canvas.drawLine(0, 0, 0, getHeight(), paint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), 0, paint);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor("#BE5A7C"));
        int startIndex = 1;

        cpaint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(getWidth() - 50, getHeight() / 2, 20, cpaint);
        cpaint.setStrokeWidth(7);
        canvas.drawLine(getWidth() - 50, getHeight() / 2, getWidth() - 250, (jump), cpaint);
        cpaint.setStyle(Style.STROKE);
        cpaint.setStrokeWidth(2);
        if (go < getWidth() - 25) {
            canvas.drawLine(0, (getHeight() / 2), getWidth() - go - 4, (getHeight() / 2), paint);

        } else {
            lo = 7;
        }
        Log.i("LOG", "u2 ==  " + data.size());
        if (lo == 7) {
            if (lo == 7 && half != 6) {
                half = 6;
                endIndex = data.size();
            }
            ArrayData = new float[endIndex];
            int r = 0;
            for (int y = data.size() - endIndex; y < data.size(); y++) {
                ArrayData[r] = data.get(y);
                r++;
            }

        } else {
            ArrayData = new float[data.size()];
            for (int y = 0; y < data.size(); y++) {
                ArrayData[y] = data.get(y);
            }

        }
        for (int i = startIndex; i < ArrayData.length; i++) {
            float y = (getWidth() - 250) - ((i - 1 - startIndex) * xStep + (getWidth() - go));
            if ( !(((i - 1 - startIndex) * xStep + (getWidth() - go)) > (getWidth() - 250))) {
                canvas.drawLine((i - 1 - startIndex) * xStep + (getWidth() - go), ArrayData[i - 1] + (getHeight() / 2), (i - startIndex) * xStep + (getWidth() - go), ArrayData[i] + (getHeight() / 2), paint);
            }

        }

        move();
        move2();
    }


    public void move() {
        if (lo == 7 || lo == 6) {

        } else {
            go = go + 2.55f;

        }
        postInvalidate();

    }


    public void move2() {
        Float u = data.get(data.size() - 1);

        if (check) {
            check = false;
            jump = getHeight() / 2 - u;
        } else {
            check = true;
            jump = getHeight() / 2 + u;
        }

    }


    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }

        return true;
    }
}
