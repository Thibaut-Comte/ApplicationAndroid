package com.jonathan.reaction;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score extends AppCompatActivity {

    private ListView lw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lw = findViewById(R.id.lw);

        List<ScoreClass> scores = genererScores();

        ScoreAdapter adapter = new ScoreAdapter(lw.getContext(), scores);
        lw.setAdapter(adapter);
    }

    private List<ScoreClass> genererScores() {
        /*SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "error");*/
        List<ScoreClass> scores = new ArrayList<ScoreClass>();
        scores.add(new ScoreClass(Color.DKGRAY, "Jonathan", 253));
        scores.add(new ScoreClass(Color.BLUE, "Valentin", 243));
        scores.add(new ScoreClass(Color.RED, "Antoine", 234));
        scores.add(new ScoreClass(Color.GREEN, "Émillie", 231));
        scores.add(new ScoreClass(Color.BLACK, "Caroline", 230));
        scores.add(new ScoreClass(Color.BLUE, "Emmanuelle", 224));
        scores.add(new ScoreClass(Color.CYAN, "Steven", 204));
        scores.add(new ScoreClass(Color.GRAY, "Sophie", 102));
        scores.add(new ScoreClass(Color.BLUE, "Lison", 96));
        scores.add(new ScoreClass(Color.GRAY, "Willy", 88));
        scores.add(new ScoreClass(Color.GREEN, "Logan", 59));
        scores.add(new ScoreClass(Color.BLACK, "Florent", 57));
        scores.add(new ScoreClass(Color.RED, "Mathieu", 25));
        scores.add(new ScoreClass(Color.GRAY, "Tom", 23));
        scores.add(new ScoreClass(Color.YELLOW, "Lea", 16));
        scores.add(new ScoreClass(Color.CYAN, "Steve", 14));
        scores.add(new ScoreClass(Color.GREEN, "Tim", 12));
        scores.add(new ScoreClass(Color.DKGRAY, "Fleur", 9));
        scores.add(new ScoreClass(Color.BLUE, "Thomas", 7));
        scores.add(new ScoreClass(Color.RED, "Lisa", 4));


        return scores;
    }
}


