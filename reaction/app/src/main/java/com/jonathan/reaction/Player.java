package com.jonathan.reaction;

/**
 * Created by jonathan on 18/01/18.
 */

public class Player {

    private String username = "";
    private float actualscore = 0;
    private float hightscoreSpeed = 0;
    private float hightscoreAverage = 0;
    private float hightscoreStamina = 0;

    public Player(String username, float actualscore, float hightscoreSpeed, float hightscoreAverage, float hightscoreStamina) {
        this.username = username;
        this.actualscore = actualscore;
        this.hightscoreSpeed = hightscoreSpeed;
        this.hightscoreAverage = hightscoreAverage;
        this.hightscoreStamina = hightscoreStamina;
    }

    public Player() {
        this.username = "Mario";
        this.actualscore = 0;
        this.hightscoreSpeed = 0;
        this.hightscoreAverage = 0;
        this.hightscoreStamina = 0;
    }

    public float getActualscore() {
        return actualscore;
    }

    public void setActualscore(float actualscore) {
        this.actualscore = actualscore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getHightscoreSpeed() {
        return hightscoreSpeed;
    }

    public void setHightscoreSpeed(float hightscoreSpeed) {
        this.hightscoreSpeed = hightscoreSpeed;
    }

    public float getHightscoreAverage() {
        return hightscoreAverage;
    }

    public void setHightscoreAverage(float hightscoreAverage) {
        this.hightscoreAverage = hightscoreAverage;
    }

    public float getHightscoreStamina() {
        return hightscoreStamina;
    }

    public void setHightscoreStamina(float hightscoreStamina) {
        this.hightscoreStamina = hightscoreStamina;
    }


}
