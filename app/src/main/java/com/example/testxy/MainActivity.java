package com.example.testxy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String[] names = {"07:00","08:00","09:30","15:30","17:00","18:00","19:30","20:30"};
    private int[] imts = {1,0,1,0,1,0,1,0,1,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       XyTime xyTime = findViewById(R.id.line_tz);
       xyTime.setNames(names);
       xyTime.setImts(imts);
       xyTime.refresh();

       double d = 0xabcad&1314L;
       Log.i("MainClass",String .format(Locale.CHINA,"%.2f%%d",d));
    }

}