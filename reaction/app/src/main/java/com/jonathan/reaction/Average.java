package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Average extends AppCompatActivity {

    //init des données membres
    TextView mTvTime, lvl, guide;
    RelativeLayout mlw;
    Player player = new Player();

    boolean kill = false;//true si app pause
    boolean touch = false;//true si touch de l'écran
    boolean game = false;//true si gagner
    boolean sound = false;//true si son joué

    long nombreAleatoire, nombreAleatoireMax = 0;

    long score = 0; //score du joueur

    int nmbr = 0;

    private Context mContext;
    private Chronometer mChronometer;
    private Thread mThreadChrono;
    private SharedPreferences sharedPreferences;
    private Boolean vibrateTest, soundTest;
    private  String lan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        vibrateTest = sharedPreferences.getBoolean("vibrate", true);
        soundTest = sharedPreferences.getBoolean("sound", true);
        lan = sharedPreferences.getString("Language", "English");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average);
        mContext = this;
        mTvTime = (TextView) findViewById(R.id.textchrono);
        guide = (TextView) findViewById(R.id.guide3);
        mlw = (RelativeLayout) findViewById(R.id.lw);
        lvl = (TextView) findViewById(R.id.twlvl);
        String time = "";

        if (lan.equals("French")) {
            guide.setText("Pret ?");
        }

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
            mChronometer.setmode("Average");

        }


        //Génrère un rand entre 8000 et 2000
        nombreAleatoireMax = 2000 + (long) (Math.random() * ((8000 - 2001) + 1));
        nombreAleatoire = 2000 + (long) (Math.random() * ((nombreAleatoireMax - 2000) + 1));

        nmbr = sharedPreferences.getInt("Average", 0);
        if (lan.equals("French")) {
            lvl.setText("" +nmbr+ "sur 10");
        } else {
            lvl.setText("" + nmbr + " of 10");
        }


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

        if (vibrateTest) {
            //On start le thread vibration à la fin du oncreate
            //vibrateThread.start(); Désa par choix
        }

    }


    /**
     * début thread chrono
     */
    public void updateTimerText(final String time, final long since) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //Affichage sur l'écran
                mTvTime.setText(time);
                sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);

                if (since > nombreAleatoire) {
                    mlw.setBackgroundColor(Color.GREEN);
                    if (lan.equals("French")) {
                        guide.setText("Maintenant !");
                    } else {
                        guide.setText("NOW !");
                    }
                    sharedPreferences.edit().putString("ecran", "vert").apply();

                }
                //Stop du chrono quand on touche l'écran
                mlw.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        mChronometer.stop();
                        touch = true;
                        if (since < nombreAleatoire) {

                            //mlw.setBackgroundColor(Color.RED);
                            sharedPreferences.edit().putString("ecran", "rouge").apply();
                            if (!sound) {
                                if (sound) {
                                    sound = true;
                                }
                                Intent i = new Intent(Average.this, EndGameAverage.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                            }
                        }
                        if (since > nombreAleatoire) {
                            //mlw.setBackgroundColor(Color.BLUE);
                            score = since - nombreAleatoire;
                            sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
                            sharedPreferences.edit().putLong("score", score).apply();
                            if (!sound) {
                                sound = true;
                                Intent i = new Intent(Average.this, EndGameAverage.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                            }
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
    public void onBackPressed() {}

}