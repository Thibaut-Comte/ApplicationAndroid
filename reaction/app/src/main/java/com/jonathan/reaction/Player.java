package com.jonathan.reaction;

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
    private String firstname = "";
    private String lastname = "";

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

    public Player(String username, String password, String firstname, String lastname, long hightscoreSpeed, long hightscoreAverage, long hightscoreStamina) {
        this.username = username;
        this.password = password;
        this.actualscore = "";
        this.hightscoreSpeed = hightscoreSpeed;
        this.hightscoreAverage = hightscoreAverage;
        this.hightscoreStamina = hightscoreStamina;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Player() {
        this.username = "Mario";
        this.password = "Luigi";
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public boolean checkHightscoreSpeed(long score) {
        if (score > this.hightscoreSpeed) {
            hightscoreSpeed = score;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "Player{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", actualscore='" + actualscore + '\'' +
                ", hightscoreSpeed=" + hightscoreSpeed +
                ", hightscoreAverage=" + hightscoreAverage +
                ", hightscoreStamina=" + hightscoreStamina +
                '}';
    }
}
