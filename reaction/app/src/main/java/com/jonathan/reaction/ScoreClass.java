package com.jonathan.reaction;

/**
 * Created by thibaut on 05/12/2017.
 */

public class ScoreClass {

    private int color;
    private String pseudo;
    private int score;

    public ScoreClass(int color, String pseudo, int score) {
        this.color = color;
        this.pseudo = pseudo;
        this.score = score;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
