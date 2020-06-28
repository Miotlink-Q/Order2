package com.order.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

public class DrawImageView extends android.support.v7.widget.AppCompatImageView {

    private Context mContext=null;
    public DrawImageView(Context context) {
        super(context);
        mContext=context;
    }

    Paint paint = new Paint();
    {
        paint.setAntiAlias(true);//用于防止边缘的锯齿
        paint.setColor(Color.WHITE);//设置颜色
        paint.setStrokeWidth(5f);//设置空心矩形边框的宽度
        paint.setTextSize(40f);
        paint.setAlpha(1000);//设置透明度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paintText = new Paint();
        paintText.setColor(Color.WHITE);//设置颜色
        paintText.setTextSize(30f);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setAlpha(1000);//设置透明度
        String text="请面向屏幕进行识别";
        canvas.drawText(text, (getRight())/2, getTop()+30, paintText);

    }

}
