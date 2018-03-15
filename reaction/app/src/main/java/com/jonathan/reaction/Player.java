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

    public Player(String username, String password, String actualscore, long hightscoreSpeed, long hightscoreAverage, long hightscoreStamina) {
        this.username = username;
        this.password = password;
        this.actualscore = actualscore;
        this.hightscoreSpeed = hightscoreSpeed;
        this.hightscoreAverage = hightscoreAverage;
        this.hightscoreStamina = hightscoreStamina;

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

    public void checkHightscore(long score, String modeJeu) {
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");
        switch(modeJeu) {
            case "speed":
                if (score < this.hightscoreSpeed || this.hightscoreSpeed == 0) {
                    DBRef.child(username).child("hightscoreSpeed").setValue(score);
                }
                break;
            case "stamina":
                if (score > this.hightscoreStamina || this.hightscoreStamina == 0) {
                    DBRef.child(username).child("hightscoreStamina").setValue(score);
                }
                break;
            case "average":
                if (score < this.hightscoreAverage || this.hightscoreStamina == 0) {
                    DBRef.child(username).child("hightscoreAverage").setValue(score);
                }
                break;
        }
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
                '}';
    }
}
