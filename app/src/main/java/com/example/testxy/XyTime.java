package com.example.testxy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XyTime extends View {
    float x = 0, y = 0;
    float initialX, initialY;
    float downX, downY;
    int longX = 0;

    public XyTime(Context context) {
        super(context);
        initView();
    }

    public XyTime(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public XyTime(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public XyTime(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();

    }

    //背景的颜色
    public Paint xyPaint = new Paint();
    public Paint xPaint = new Paint();
    public Paint font = new Paint();
    public Paint zzcPaint = new Paint();
    public Paint linfont = new Paint();

    public void initView() {
        xyPaint.setColor(ContextCompat.getColor(getContext(), R.color.linexy));
        xyPaint.setStrokeWidth(1f);
        xyPaint.setStrokeCap(Paint.Cap.ROUND);
        xyPaint.setAntiAlias(true);
        xPaint.setColor(ContextCompat.getColor(getContext(), R.color.linexy));
        xPaint.setStrokeWidth(1f);
        font.setTextSize(12f);
        font.setColor(ContextCompat.getColor(getContext(), R.color.color_CRDFFB));
        font.setAntiAlias(true);
        linfont.setTextSize(12f);
        linfont.setStrokeWidth(3f);
        linfont.setColor(ContextCompat.getColor(getContext(), R.color.color_11E1FF));
        linfont.setStrokeCap(Paint.Cap.ROUND);
        linfont.setAntiAlias(true);
        zzcPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_023682F));
        zzcPaint.setStrokeCap(Paint.Cap.ROUND);
        zzcPaint.setAntiAlias(true);
//        startX：开始点X坐标
//        startY：开始点Y坐标
//        stopX：结束点X坐标
//        stopY：结束点Y坐标

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = (int) x;  //滑动平移的距离
        //X
        canvas.drawLine(getLeft() + 80, getBottom() - 30, getMeasuredWidth(), getBottom() - 30, xyPaint);
        //Y
        canvas.drawLine(getLeft() + 80, getTop() + 50, getLeft() + 80, getBottom() - 30, xyPaint);

        //已消毒 上条
        canvas.drawLine(getLeft() + 80, getBottom() - 120, getMeasuredWidth(), getBottom() - 120, xPaint);
        //未消毒下
        canvas.drawLine(getLeft() + 80, getBottom() - 50, getMeasuredWidth(), getBottom() - 50, xPaint);


//
        //底部时间坐标，根据每个坐标 间距30
        //初始位置，150
        int x = getLeft() + 80 + left;
        int x1 = 0;//每次改变后，x1
        int nextType = 0;//下一个标记
        int a = 0;//当前一个时间
        int b = 0;//下一个时间

        for (int i = 0; i < names.length; i++) {
            x += 90;//x结束位置
            x1 = x - 90;//x1开始位置     //140 -27

            canvas.drawText(names[i], x - 110, getBottom() - 10, font);

            //这里还要增加 当前位置 X Y 线

            if (i + 1 < names.length) {
                //就取下一个
                nextType = imts[i + 1];
                //判断一下，长度大于2不然，报错。
                a = Integer.parseInt(names[i].substring(0, 2));//当前这个
                b = Integer.parseInt(names[i + 1].substring(0, 2));//下一个
                //每小时增加15px
//                x+=(b-a)*15;
                Log.d("a:", a + "");
                Log.d("b:", b + "");

            } else {
                //没有下一个imts[i+1]
                nextType = imts[i];
            }
            if (i == names.length - 1)
                longX = x;
            setLine(canvas, x, x1, imts[i], nextType);
        }
        
        canvas.drawRect(new RectF(0, 0, getLeft()+78, getBottom() - 40), zzcPaint); //添加遮罩层 当滑动到左边 遮罩的用处 记得改成跟背景色一样的
        canvas.drawText("消毒中", getLeft() + 40, getBottom() - 115, font);
        canvas.drawText("未消毒", getLeft() + 40, getBottom() - 45, font);
    }


    private String[] names = {};
    private int[] imts = {};

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public int[] getImts() {
        return imts;
    }

    public void setImts(int[] imts) {
        this.imts = imts;
    }

    public void refresh() {
        invalidate();
    }


    //设置两点之间的线。
    private void setLine(Canvas canvas, int x, int x1, int type, int nextType) {
        // x
        // y
        Log.d("c:", "");

        if (type == 1) {//开始消毒
            canvas.drawLine(x1 + 3, getBottom() - 120, x, getBottom() - 120, linfont);
        } else {
            //未消毒
            canvas.drawLine(x1 + 3, getBottom() - 50, x, getBottom() - 50, linfont);
        }
        //2条线之间的Y线
        //判断当前这个线 跟下一条线的 也就是 type 是不是一样 ，如果是一样，就不画Y线，
        //如果不一样，就是画Y线的时候

        if (type != nextType) {
//            //画Y线
            canvas.drawLine(x, getBottom() - 120, x, getBottom() - 50, linfont);
        }

    }

    @Override
    public void scrollTo(int x, int y) {
        Log.e("LargeView", "scroll");
        super.scrollTo(x, y);

    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialX = x;
                initialY = y;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (longX >= getMeasuredWidth()) {
                    //滑动距离判断
                    if ((initialX + event.getX() - downX) <= 0 &&(initialX + event.getX() - downX)>=-(longX - getMeasuredWidth()+(getLeft() + 140)))
                        x = initialX + event.getX() - downX;
                    y = initialY + event.getY() - downY;
                    postInvalidate();
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }


    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

