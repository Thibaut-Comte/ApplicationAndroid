package com.jonathan.reaction;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jonathan on 18/01/18.
 */

public class Player {

    private String username = "";
    private String password = "";
    private String actualscore = "";
    private long hightscoreSpeed = 0;
    private long hightscoreAverage = 0;
    private long hightscoreStamina = 0;
    private String url = "";

    public Player(String username, String password, String actualscore, long hightscoreSpeed, long hightscoreAverage, long hightscoreStamina, String url) {
        this.username = username;
        this.password = password;
        this.actualscore = actualscore;
        this.hightscoreSpeed = hightscoreSpeed;
        this.hightscoreAverage = hightscoreAverage;
        this.hightscoreStamina = hightscoreStamina;
        this.url = url;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.actualscore = "";
        this.hightscoreSpeed = 0;
        this.hightscoreAverage = 0;
        this.hightscoreStamina = 0;
    }

    public Player() {
        this.username = "";
        this.password = "";
        this.actualscore = "";
        this.hightscoreSpeed = 0;
        this.hightscoreAverage = 0;
        this.hightscoreStamina = 0;
        this.url = "";
    }

    public String getActualscore() {
        return actualscore;
    }

    public void setActualscore(String actualscore) {
        this.actualscore = actualscore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getHightscoreSpeed() {
        return hightscoreSpeed;
    }

    public void setHightscoreSpeed(int hightscoreSpeed) {
        this.hightscoreSpeed = hightscoreSpeed;
    }

    public long getHightscoreAverage() {
        return hightscoreAverage;
    }

    public void setHightscoreAverage(int hightscoreAverage) {
        this.hightscoreAverage = hightscoreAverage;
    }

    public long getHightscoreStamina() {
        return hightscoreStamina;
    }

    public void setHightscoreStamina(long hightscoreStamina) {
        this.hightscoreStamina = hightscoreStamina;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean checkHightscore(long score, String modeJeu) {
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");
        boolean newHS = false;
        switch(modeJeu) {
            case "speed":
                if (score < this.hightscoreSpeed || this.hightscoreSpeed == 0) {
                    Log.e("debug","new HS Speed");
                    DBRef.child(username).child("hightscoreSpeed").setValue(score);
                    newHS = true;
                }
                break;
            case "stamina":
                if (score > this.hightscoreStamina || this.hightscoreStamina == 0) {
                    Log.e("debug","new HS Stamina");
                    DBRef.child(username).child("hightscoreStamina").setValue(score);
                    newHS = true;
                }
                break;
            case "average":
                if (score < this.hightscoreAverage || this.hightscoreAverage == 0) {
                    Log.e("debug","new HS Average");
                    DBRef.child(username).child("hightscoreAverage").setValue(score);
                    newHS = true;
                }
                break;
        }
        return newHS;
    }

    @Override
    public String toString() {
        return "Player{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", actualscore='" + actualscore + '\'' +
                ", hightscoreSpeed=" + hightscoreSpeed +
                ", hightscoreAverage=" + hightscoreAverage +
                ", hightscoreStamina=" + hightscoreStamina +
                ", url=" + url +
                '}';
    }
}
