package com.jonathan.reaction;

import android.content.Context;

public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLS_TO_HOURS = 3600000;


    Context mContext;

    long mStartTime;
    boolean mIsRunning;

    public Chronometer(Context context) {
        mContext = context;
    }

    public Chronometer(Context context, long startTime) {
        this(context);
        mStartTime = startTime;
    }


    public void start() {
        if (mStartTime == 0) {
            mStartTime = System.currentTimeMillis();
        }
        mIsRunning = true;
    }


    public void stop() {
        mIsRunning = false;
    }
    public boolean isRunning() {
        return mIsRunning;
    }
    public long getStartTime() {
        return mStartTime;
    }


    @Override
    public void run() {
        while (mIsRunning) {

            long since = System.currentTimeMillis() - mStartTime;

            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLS_TO_HOURS)));
            int millis = (int) since % 1000;

            ((Speed) mContext).updateTimerText(String.format("%02d:%02d:%02d:%03d"
                    , hours, minutes, seconds, millis), since);

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}