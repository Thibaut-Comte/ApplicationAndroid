package com.jonathan.reaction;

import android.app.PendingIntent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class Score extends AppCompatActivity {

    private ListView lw;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lw = findViewById(R.id.lw);
        tv = findViewById(R.id.title);
        List<ScoreClass> scores = genererScores();
        tv.setText("Liste des Scores");

        ScoreAdapter adapter = new ScoreAdapter(lw.getContext(), scores);
        lw.setAdapter(adapter);
    }

    private List<ScoreClass> genererScores() {
        List<ScoreClass> scores = new ArrayList<ScoreClass>();
        scores.add(new ScoreClass(Color.BLACK, "Florent", 57));
        scores.add(new ScoreClass(Color.BLUE, "Kevin", 96));
        scores.add(new ScoreClass(Color.GREEN, "Logan", 59));
        scores.add(new ScoreClass(Color.RED, "Mathieu", 25));
        scores.add(new ScoreClass(Color.GRAY, "Willy", 88));
        return scores;
    }
}


