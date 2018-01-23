package com.jonathan.reaction;

import android.content.Context;

/**
 * Created by jonathan on 09/01/18.
 */

public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;
    private String mTime;


    private Context mContext;
    private long mStarTime;

    private boolean mIsRunning;

    public Chronometer(Context mContext) {
        this.mContext = mContext;
    }

    public void start() {
        mStarTime = System.currentTimeMillis();

    }

    @Override
    public void run() {

        while (mIsRunning) {

            long since = System.currentTimeMillis() - mStarTime;

            int seconds = (int) ((since / 1000) % 60);
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLIS_TO_HOURS)) % 24);
            int millis = (int) since % 1000;


            mTime = hours+":"+minutes+":"+seconds+":"+millis;
            ((Speed) mContext).updateTimerText(mTime);

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
