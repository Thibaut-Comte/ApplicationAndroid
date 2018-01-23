package com.example.jonathan.chronometer;

import android.content.Context;

/**
 * Created by jonathan on 20/01/18.
 */

public class Chronometer implements Runnable{

    //on fais une const car opti importante vue le taux de refresh
    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;


    private String mTime;
    private Context mContext;
    private long mStartTime;

    private boolean mIsRunning;

    public Chronometer(Context mContext) {
        this.mContext = mContext;
    }

    public void start(){
        mStartTime = System.currentTimeMillis();
        mIsRunning = true;
    }

    public void stop(){
        mIsRunning = false;
    }



    @Override
    public void run() {

        while(mIsRunning){

            long since = System.currentTimeMillis() - mStartTime;

            //on cast int car since est long
            int seconds = (int) ((since / 1000) % 60);
            int minutes = (int) (((since / MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLIS_TO_HOURS))%24);
            int millis = (int) since % 1000;


            mTime = hours+":"+minutes+":"+seconds+":"+millis;
            ((MainActivity) mContext).updateTimerText(mTime);


            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
