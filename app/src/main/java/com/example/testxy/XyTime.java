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
//        startX：开始点X坐标
//        startY：开始点Y坐标
//        stopX：结束点X坐标
//        stopY：结束点Y坐标

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //X
        canvas.drawLine(getLeft()+80, getBottom()-30, getMeasuredWidth(),  getBottom()-30, xyPaint);
        //Y
        canvas.drawLine(getLeft()+80, getTop()+50, getLeft()+80, getBottom()-30, xyPaint);

        //已消毒 上条
        canvas.drawLine(getLeft()+80, getBottom()-120, getMeasuredWidth(),  getBottom()-120, xPaint);
        //未消毒下
        canvas.drawLine(getLeft()+80, getBottom()-50, getMeasuredWidth(),  getBottom()-50, xPaint);
        canvas.drawText("消毒中", getLeft()+40, getBottom()-115, font);
        canvas.drawText("未消毒", getLeft()+40, getBottom()-45, font);

//
        //底部时间坐标，根据每个坐标 间距30
        //初始位置，150
        int x = getLeft()+80;
        int x1 = 0;//每次改变后，x1
        int nextType = 0;//下一个标记
        int a = 0;//当前一个时间
        int b = 0;//下一个时间

        for (int i = 0; i < names.length; i++) {
            x += 120;//x结束位置
            x1 = x - 120;//x1开始位置     //140 -27
            canvas.drawText(names[i], x - 140, getBottom()-10, font);
            //这里还要增加 当前位置 X Y 线

            if (i + 1 < names.length) {
                //就取下一个
                nextType = imts[i + 1];
                //判断一下，长度大于2不然，报错。
                a = Integer.parseInt(names[i].substring(0, 2));//当前这个
                b = Integer.parseInt(names[i + 1].substring(0, 2));//下一个
                //每小时增加15px
                x+=(b-a)*15;
                Log.d("a:", a + "");
                Log.d("b:", b + "");

            } else {
                //没有下一个imts[i+1]
                nextType = imts[i];
            }

            setLine(canvas, x, x1, imts[i], nextType );
        }

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // TODO Auto-generated method stub
//        int width = 0;
//        int height = 0;
//        //获得宽度MODE
//        int modeW = MeasureSpec.getMode(widthMeasureSpec);
//        //获得宽度的值
//        if (modeW == MeasureSpec.AT_MOST) {
//            width = MeasureSpec.getSize(widthMeasureSpec);
//        }
//        if (modeW == MeasureSpec.EXACTLY) {
//            width = widthMeasureSpec;
//        }
//        if (modeW == MeasureSpec.UNSPECIFIED) {
//            width = 600;
//        }
//        //获得高度MODE
//        int modeH = MeasureSpec.getMode(height);
//        //获得高度的值
//        if (modeH == MeasureSpec.AT_MOST) {
//            height = MeasureSpec.getSize(heightMeasureSpec);
//        }
//        if (modeH == MeasureSpec.EXACTLY) {
//            height = heightMeasureSpec;
//        }
//        if (modeH == MeasureSpec.UNSPECIFIED) {
//            //ScrollView和HorizontalScrollView
//            height = 400;
//        }
//        //设置宽度和高度
//        setMeasuredDimension(width, height);
//    }


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
    public void refresh(){
        invalidate();
    }

    int statex = 0;

    //设置两点之间的线。
    private void setLine(Canvas canvas, int x, int x1, int type, int nextType) {
        // x
        // y
        Log.d("c:", "");

        if (type == 1) {//开始消毒
            canvas.drawLine(x1 + 3, getBottom()-120, x, getBottom()-120, linfont);

        } else {
            //未消毒
            canvas.drawLine(x1 + 3, getBottom()-50, x, getBottom()-50, linfont);

        }
        //2条线之间的Y线
        //判断当前这个线 跟下一条线的 也就是 type 是不是一样 ，如果是一样，就不画Y线，
        //如果不一样，就是画Y线的时候
        if (type != nextType) {
//            //画Y线
            canvas.drawLine(x, getBottom()-120, x, getBottom()-50, linfont);
        }

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
