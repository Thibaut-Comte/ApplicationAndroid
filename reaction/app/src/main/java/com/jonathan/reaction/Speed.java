package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Speed extends AppCompatActivity {

    TextView mTvTime;
    TextView mTvTime2;
    RelativeLayout mlw;

    private Context mContext;
    private Chronometer mChronometer;
    private Thread mThreadChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        mContext = this;

        mTvTime = (TextView) findViewById(R.id.textchrono);
        mTvTime2 = (TextView) findViewById(R.id.tv2);
        mlw = (RelativeLayout) findViewById(R.id.lw);

        if(mChronometer == null){
            mChronometer = new Chronometer(mContext);
            mThreadChrono = new Thread(mChronometer);
            mThreadChrono.start();
            mChronometer.start();
        }

        mlw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                mChronometer.stop();
                return true;//always return true to consume event
            }
        });
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTime.setText(time);
            }
        });
    }
}
