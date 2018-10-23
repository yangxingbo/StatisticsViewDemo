package com.bob.view;

import android.app.Activity;
import android.os.Bundle;

import com.bob.view.widget.NumberRunTextView;
import com.bob.view.widget.StatisticsView;

/**
 * Create by bob on 2018/10/18
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((StatisticsView) findViewById(R.id.sv_person_count)).setColors(R.array.colorArray).setAngleValue(120).startAnim();
        ((NumberRunTextView) findViewById(R.id.tv_person_count)).setIntValue(250).startAnim();
    }
}
