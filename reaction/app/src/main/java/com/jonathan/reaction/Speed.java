package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ²Speed extends AppCompatActivity {

    //init des données membres
    TextView mTvTime;
    TextView mTvTime2;
    RelativeLayout mlw;
    Player player = new Player();

    boolean kill = false;//true si app pause
    boolean touch = false;//true si touch de l'écran
    boolean game = false;//true si gagner

    long nombreAleatoire = 0;

    long score = 0; //score du joueur

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
        mTvTime2 = (TextView) findViewById(R.id.tv2);
        mTvTime = (TextView) findViewById(R.id.textchrono);
        mlw = (RelativeLayout) findViewById(R.id.lw);
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String time = "";

/*
        time = sharedPreferences.getString("actualscore", "Pas cool");
        if (sharedPreferences.contains("actualscore")) {
            mTvTime2.setText(time);

        } else {
            mTvTime2.setText("");
        }
*/
        if (mChronometer == null) {
            mChronometer = new Chronometer(mContext);
            mThreadChrono = new Thread(mChronometer);
            mThreadChrono.start();
            mChronometer.start();
        }


        //Génrère un rand entre 5000 et 10 000
        nombreAleatoire = 2000 + (long) (Math.random() * ((8000 - 2000) + 1));


        //Thread pour la vibration
        Thread vibrateThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    //While qui stop quand l'apli pause
                    while (!kill && !touch) {
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
                    }
                } catch (Throwable t) {
                    //affichage en log de l'erreur de vibration
                    Log.i("Vibration", "Thread  exception " + t);
                }
            }
        });

        //On start le thread vibration à la fin du oncreate
        vibrateThread.start();

    }


    /**
     * début thread chorono
     */
    public void updateTimerText(final String time, final long since) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //Affichage sur l'écran
                mTvTime.setText(time);

                if (since > nombreAleatoire) {
                    mlw.setBackgroundColor(Color.GREEN);
                }
                //Stop du chrono quand on touche l'écran
                mlw.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        mChronometer.stop();
                        touch = true;
                        if (since < nombreAleatoire) {
                            mlw.setBackgroundColor(Color.RED);
                        }
                        if (since > nombreAleatoire) {
                            mlw.setBackgroundColor(Color.BLUE);
                            score = since - nombreAleatoire;
                            mTvTime2.setText(""+score);
                        }
                        return true;//always return true to consume event
                    }
                });

                //On met le score dans sharedpreferences pour l'utiliser partout
                SharedPreferences sharedPreferences1 = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
                sharedPreferences1.edit().putString("actualscore", time).apply();
            }
        });
    }

    /**
     * On indique au thread par le bool kill que l'apli est shutdown
     */
    protected void onPause() {
        super.onPause();
        kill = true;
    }

}
