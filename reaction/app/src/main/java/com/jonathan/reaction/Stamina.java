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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Stamina extends AppCompatActivity {

    //init des données membres
    TextView mTvTime;
    RelativeLayout mlw;
    Player player = new Player();
    ImageView hearth, hearth1, hearth2;

    boolean kill = false;//true si app pause
    boolean touch = false;//true si touch de l'écran
    boolean game = false;//true si gagner
    boolean sound = false;//true si son joué

    long nombreAleatoire, nombreAleatoireMax = 0;
    long losetime = 0;
    long[] losetimetab = new long[]{
            750, 500, 450, 400, 375, 350
    };
    int lvl = 0;
    int life = 0;
    boolean vibrateTest = true;

    long score = 0; //score du joueur

    private Context mContext;
    private Chronometer mChronometer;
    private Thread mThreadChrono;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamina);
        mContext = this;
        mTvTime = (TextView) findViewById(R.id.textchrono);
        mlw = (RelativeLayout) findViewById(R.id.lw);
        hearth = (ImageView) findViewById(R.id.heatrh);
        hearth1 = (ImageView) findViewById(R.id.heatrh1);
        hearth2 = (ImageView) findViewById(R.id.heatrh2);
        String time = "";

        if (mChronometer == null) {
            mChronometer = new Chronometer(mContext);
            mThreadChrono = new Thread(mChronometer);
            mThreadChrono.start();
            mChronometer.start();
            mChronometer.setmode("Stamina");
        }


        //Génrère un rand entre 8000 et 2000
        nombreAleatoireMax = 2000 + (long) (Math.random() * ((8000 - 2001) + 1));
        nombreAleatoire = 2000 + (long) (Math.random() * ((nombreAleatoireMax - 2000) + 1));


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

        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        life = sharedPreferences.getInt("StaminaLives", 0);
        vibrateTest = sharedPreferences.getBoolean("vibrate", true);

        if (life == 3) {
            hearth.setVisibility(View.VISIBLE);
            hearth1.setVisibility(View.VISIBLE);
            hearth2.setVisibility(View.VISIBLE);
        } else if (life == 2) {
            hearth.setVisibility(View.VISIBLE);
            hearth1.setVisibility(View.VISIBLE);
            hearth2.setVisibility(View.INVISIBLE);
        }else if (life == 1){
            if (vibrateTest) {
                //On start le thread vibration à la fin du oncreate
                vibrateThread.start();
            }
            hearth.setVisibility(View.VISIBLE);
            hearth1.setVisibility(View.INVISIBLE);
            hearth2.setVisibility(View.INVISIBLE);
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

                lvl = sharedPreferences.getInt("Stamina", 0);
                if (lvl < 6) {
                    losetime = (losetimetab[lvl]) + nombreAleatoire;
                } else {
                    losetime = (losetimetab[5] - (10 * (lvl - 5))) + nombreAleatoire;
                }


                if (since > nombreAleatoire) {
                    mlw.setBackgroundColor(Color.GREEN);
                    sharedPreferences.edit().putString("ecran", "vert").apply();
                }
                if (since > losetime) {
                    mlw.setBackgroundColor(Color.BLUE);
                    sharedPreferences.edit().putString("ecran", "rouge").apply();
                }
                //Stop du chrono quand on touche l'écran
                mlw.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        mChronometer.stop();
                        touch = true;
                        if (since < nombreAleatoire || losetime < since) {
                            //lose
                            final MediaPlayer OOFSound = MediaPlayer.create(Stamina.this, R.raw.death);

                            //mlw.setBackgroundColor(Color.RED);
                            sharedPreferences.edit().putString("ecran", "rouge").apply();
                            if (!sound) {
                                OOFSound.start();
                                sound = true;
                                Intent i = new Intent(Stamina.this, EndGameStamina.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(i);
                            }
                        }
                        if (losetime > since && since > nombreAleatoire) {
                            //win
                            score = since - nombreAleatoire;
                            sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
                            sharedPreferences.edit().putLong("score", score).apply();
                            if (!sound) {
                                sound = true;
                                Intent i = new Intent(Stamina.this, EndGameStamina.class);
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