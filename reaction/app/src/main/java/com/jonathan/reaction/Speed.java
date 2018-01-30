package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Speed extends AppCompatActivity{

    TextView mTvTime;
    TextView mTvTime2;
    RelativeLayout mlw;
    Player player = new Player();

    int i = 0;

    private Context mContext;
    private Chronometer mChronometer;
    private Thread mThreadChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(150);

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

        Thread vibrateThread= new Thread(new Runnable() {
            public void run() {
                try {
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    while(i < 10){
                        v.vibrate(150);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        v.vibrate(150);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                }
                catch (Throwable t) {
                    Log.i("Vibration", "Thread  exception "+t);
                }
            }
        });

        vibrateThread.start();

    }


    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTime.setText(time);

                mlw.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        mChronometer.stop();
                        return true;//always return true to consume event
                    }
                });

                player.setActualscore(time);
                mTvTime2.setText(player.getActualscore());
            }
        });
    }



}
