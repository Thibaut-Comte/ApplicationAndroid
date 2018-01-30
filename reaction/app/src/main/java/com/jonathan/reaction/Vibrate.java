package com.jonathan.reaction;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;


public class Vibrate extends AppCompatActivity implements Runnable{

    private int i=0;

    public Vibrate() {
    }

    @Override
    public void run() {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
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


}