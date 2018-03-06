package com.jonathan.reaction;

/**
 * Created by thibaut on 05/12/2017.
 */

public class ScoreClass {

    private String url;
    private String pseudo;
    private int score;

    public ScoreClass(String url, String pseudo, int score) {
        this.url = url;
        this.pseudo = pseudo;
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
