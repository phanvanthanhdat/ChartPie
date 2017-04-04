package com.example.chartpei;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baohp on 17/03/2017.
 */

public class cutomview extends View {
    Paint mPaint;
    int width;
    int height;
    int radius;
    float size;
    float centerWidth;
    float centerHeight;
    List<DataChart> mList;
    float sizeBFottom;
    int radiusStart = 270;
    int sizeWidthSub;
    int sizeHeightSub;
    String noData = "No Data";
    String mUnit = "";
    int margin;

    public cutomview(Context context) {
        super(context);
    }

    public cutomview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mList = new ArrayList<>();
    }

    public cutomview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public cutomview(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public List<DataChart> getmList() {
        return mList;
    }

    public void setmList(List<DataChart> mList) {
        this.mList = mList;
        if (mList.size() == 3) {
            mUnit = "通";
        } else {
            mUnit = "pt";
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeBFottom = (float) (h * 0.17);
        width = w;
        margin = (int) (h * 0.09);
        height = (int) (h - sizeBFottom) - 2 * margin;


        centerHeight = height / 2 + margin;
        centerWidth = w / 2;
        if (w > height) {
            size = height;
            sizeWidthSub = (int) (width - size);
            sizeHeightSub = 0;
        } else {
            size = w;
            sizeWidthSub = 0;
            sizeHeightSub = height - w;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rect = new RectF(0, 0, width, width);
        Rect bounds = new Rect();
        Rect bounds1 = new Rect();
        if (mList.size() > 0) {

            mPaint.setColor(mList.get(mList.size() - 1).getColor());
            canvas.drawCircle(centerWidth, centerHeight, (float) ((size / 2) - size * (mList.size() - 1) * 0.06), mPaint);
            canvas.save();
            canvas.restore();
            radiusStart = 270;
            int n = mList.size() - 1;


            mPaint.setColor(Color.BLACK);
            rect.set(0, height + 2 * margin, width, height + 2 * margin + sizeBFottom);
            canvas.drawRect(rect, mPaint);
            canvas.save();
            canvas.restore();


            for (int i = 0; i < n + 1; i++) {
                DataChart data = mList.get(i);
                if (i != n) {
                    mPaint.setColor(data.getColor());
                    rect.set((float) (sizeWidthSub / 2 + size * i * 0.06), (float) (sizeHeightSub / 2 + size * i * 0.06 + margin), (float) (size + sizeWidthSub / 2 - size * i * 0.06), (float) (size + margin + sizeHeightSub / 2 - size * i * 0.06));
                    canvas.drawArc(rect, radiusStart, (float) data.getPercent360(), true, mPaint);
                    canvas.save();
                    canvas.restore();
                }

                if (data.getPercenter() >= 10) {
                    float radius = 0;
                    for (int m = 0; m < i; m++) {
                        radius = (float) mList.get(m).getPercent360() + radius;

                    }
                    radius = (float) (data.getPercent360() / 2) + radius;
                    float R = ((float) (size + sizeWidthSub / 2 - size * 0 * 0.06) - (float) (sizeWidthSub / 2 + size * 0 * 0.06)) / 2;
                    float C = (float) (((R * 2 * 3.14 * 0.6)));
                    C = (float) ((C * data.getPercent360()) / 360);
                    float C1 = R;
                    if (R > C) {
                        C1 = C;
                    }

                    mPaint.setColor(Color.WHITE);
                    String name = data.getPercenter() + "";

                    if (C1 == R) {
                        C = (float) (C1 / (name.length() + 0.5) * 3 / 5);
                    } else {
                        C = (float) (C1 / (name.length() + 0.5) * 0.8);
                    }
                    mPaint.setTextSize(C);
                    mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    mPaint.getTextBounds(name, 0, name.length(), bounds);

                    float a1 = centerWidth;
                    float b = centerHeight;
                    float x = width / 2;
                    float y = margin + sizeHeightSub + size / 4;
                    mPaint.setTextSize(C);
                    canvas.drawText(name, getX(a1, b, x, y, radius) - bounds.width() / 2, getY(a1, b, x, y, radius), mPaint);
                    canvas.save();
                    canvas.restore();
                    mPaint.setTextSize((float) (C * 0.65));
                    canvas.drawText("%", 0, 1, getX(a1, b, x, y, radius) + bounds.width() / 2, getY(a1, b, x, y, radius), mPaint);
                    canvas.save();
                    canvas.restore();


                    String name1 = "(" + data.getPoint() + mUnit + ")";
                    mPaint.setTextSize((float) (C * 0.6));

                    mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    mPaint.getTextBounds(name1, 0, name1.length(), bounds);


                    float x1 = width / 2;
                    float y1 = margin + sizeHeightSub + size / 4;

                    canvas.drawText(name1, getX(a1, b, x1, y1, radius) - bounds.width() / 2, getY(a1, b, x, y, radius) + bounds.height() + 5, mPaint);
                    canvas.save();
                    canvas.restore();


                }

                radiusStart += data.getPercent360();
            }


            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize((float) ((float) (sizeBFottom * 3 / 19)));
            String name = mList.get(0).getDisription() + ":";
            mPaint.getTextBounds(name, 0, name.length(), bounds);
            int widthtext = bounds.width();
            canvas.drawText(name, (float) (width * 0.09), (float) (height + sizeBFottom * 8 / 19 + 2 * margin), mPaint);
            canvas.save();
            canvas.restore();


            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize((float) (sizeBFottom * 4.5 / 19));
            name = mList.get(0).getPercenter() + "";
            canvas.drawText(name, (float) (width * 0.09 + widthtext + 10), (float) (height + sizeBFottom * 8 / 19 + 2 * margin), mPaint);
            mPaint.getTextBounds(name, 0, name.length(), bounds1);
            canvas.save();
            canvas.restore();


            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize((float) (sizeBFottom * 3 / 19));
            name = "% (" + mList.get(0).getPoint() + mUnit + ")";
            canvas.drawText(name, (float) (width * 0.09 + bounds1.width() + widthtext + 10), (float) (height + sizeBFottom * 8 / 19 + 2 * margin), mPaint);
            canvas.save();
            canvas.restore();


            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize((float) (sizeBFottom * 3 / 19));
            name = mList.get(1).getDisription() + ": ";
            mPaint.getTextBounds(name, 0, name.length(), bounds);
            int widthtext1 = bounds.width();
            canvas.drawText(name, (float) (width * 0.09), (float) (height + sizeBFottom * 14 / 19 + 2 * margin), mPaint);
            canvas.save();
            canvas.restore();

            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize((float) (sizeBFottom * 4.5 / 19));
            name = mList.get(1).getPercenter() + "";
            mPaint.getTextBounds(name, 0, name.length(), bounds1);
            canvas.drawText(name, (float) (width * 0.09 + widthtext1 + 10), (float) (height + sizeBFottom * 14 / 19 + 2 * margin), mPaint);
            canvas.save();
            canvas.restore();


            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize((float) (sizeBFottom * 3 / 19));
            name = "% (" + mList.get(1).getPoint() + mUnit + ")";
            canvas.drawText(name, (float) (width * 0.09 + bounds1.width() + widthtext1 + 10), (float) (height + sizeBFottom * 14 / 19 + 2 * margin), mPaint);
            canvas.save();
            canvas.restore();


            mPaint.setColor(mList.get(0).getColor());
            canvas.drawCircle((float) (width * 0.05), (float) (height + sizeBFottom * 8 / 19 + 2 * margin - (float) (sizeBFottom * 4 / 50)), (float) (sizeBFottom * 3.5 / 50), mPaint);
            canvas.save();
            canvas.restore();

            mPaint.setColor(mList.get(1).getColor());
            canvas.drawCircle((float) (width * 0.05), (float) (height + sizeBFottom * 14 / 19 + 2 * margin - (float) (sizeBFottom * 4 / 50)), (float) (sizeBFottom * 3.5 / 50), mPaint);
            canvas.save();
            canvas.restore();
            if (mList.size() == 3) {
                mPaint.setColor(Color.WHITE);
                mPaint.setTextSize((float) (sizeBFottom * 3 / 19));
                name = mList.get(2).getDisription() + ":";
                mPaint.getTextBounds(name, 0, name.length(), bounds);
                int widthtext2 = bounds.width();
                canvas.drawText(name, (float) (width * 0.09) + width / 2, (float) (height + sizeBFottom * 8 / 19 + 2 * margin), mPaint);
                canvas.save();
                canvas.restore();


                mPaint.setTextSize((float) (sizeBFottom * 4.5 / 19));
                name = mList.get(2).getPercenter() + "";
                mPaint.getTextBounds(name, 0, 4, bounds1);
                canvas.drawText(name, (float) (width * 0.09) + width / 2 + widthtext2, (float) (height + sizeBFottom * 8 / 19 + 2 * margin), mPaint);
                canvas.save();
                canvas.restore();


                mPaint.setTextSize((float) (sizeBFottom * 3 / 19));
                name = "% (" + mList.get(2).getPoint() + mUnit + ")";
                mPaint.getTextBounds(name, 0, name.length(), bounds);
                canvas.drawText(name, (float) (width * 0.09) + width / 2 + bounds1.width() + widthtext2, (float) (height + sizeBFottom * 8 / 19 + 2 * margin), mPaint);
                canvas.save();
                canvas.restore();


                mPaint.setColor(mList.get(2).getColor());
                canvas.drawCircle((float) (width * 0.05) + width / 2, (float) (height + sizeBFottom * 8 / 19 + 2 * margin - (float) (sizeBFottom * 4 / 50)), (float) (sizeBFottom * 4 / 50), mPaint);
                canvas.save();
                canvas.restore();
            }

        } else {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize((float) (size * 0.1));
            mPaint.getTextBounds(noData, 0, noData.length(), bounds);
            canvas.drawText(noData, width / 2 - bounds.width() / 2, (height + sizeBFottom) / 2 + bounds.height() / 2 + margin, mPaint);

        }
    }

    private Bitmap maskingImage(Bitmap s1, Bitmap s2) {
        Bitmap original = s1;
        Bitmap mask = s2;
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ///paint.setAlpha((int) (0.7 * 255));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

    private float getX(float a, float b, float x, float y, double alpha) {
        return (float) ((x - a) * Math.cos(Math.toRadians(alpha)) - (y - b) * Math.sin(Math.toRadians(alpha)) + a);
    }

    private float getY(float a, float b, float x, float y, double alpha) {
        return (float) ((x - a) * Math.sin(Math.toRadians(alpha)) + (y - b) * Math.cos(Math.toRadians(alpha)) + b);
    }

    private Bitmap drawPoly(Point[] points) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);

        if (points.length < 2) {
            return null;
        }

        Paint polyPaint = new Paint();
        polyPaint.setAntiAlias(true);
        polyPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        polyPaint.setStyle(Paint.Style.FILL);

        Path polyPath = new Path();
        polyPath.moveTo(points[0].x, points[0].y);
        int i, len;
        len = points.length;
        for (i = 0; i < len; i++) {
            polyPath.lineTo(points[i].x, points[i].y);
        }
        polyPath.lineTo(points[0].x, points[0].y);

        tempCanvas.drawPath(polyPath, polyPaint);
        tempCanvas.drawBitmap(bitmap, 0, 0, null);

        return bitmap;
    }
}
