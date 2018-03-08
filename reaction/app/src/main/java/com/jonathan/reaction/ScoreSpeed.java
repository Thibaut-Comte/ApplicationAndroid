package com.jonathan.reaction;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreSpeed extends AppCompatActivity {

    private ListView lw;

    Button speed, stamina, average;

    Database DataB = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        speed = findViewById(R.id.speed);
        stamina = findViewById(R.id.stamina);
        average = findViewById(R.id.average);

        DataB.RecupDB();

        DataB.Recup("hellboy");

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speed = new Intent(ScoreSpeed.this, ScoreSpeed.class);
                startActivity(speed);
            }
        });

        stamina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stamina = new Intent(ScoreSpeed.this, ScoreStamina.class);
                startActivity(stamina);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent average = new Intent(ScoreSpeed.this, ScoreAverage.class);
                startActivity(average);
            }
        });

        lw = findViewById(R.id.lw);

        List<ScoreClass> scores = genererScores();

        ScoreAdapter adapter = new ScoreAdapter(lw.getContext(), scores);
        lw.setAdapter(adapter);
    }

    public void onBackPressed()
    {
        Intent i = new Intent(ScoreSpeed.this, Menu.class);
        startActivity(i);
    }

    private List<ScoreClass> genererScores() {
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String name = sharedPreferences.getString("username", "undefined");
        String imgAvatar = sharedPreferences.getString("avatarP", "https://demo.phpgang.com/crop-images/demo_files/pool.jpg");
        int scoreP = sharedPreferences.getInt("scoreP", 0);

        List<ScoreClass> scores = new ArrayList<ScoreClass>();
        scores.add(new ScoreClass("https://demo.phpgang.com/crop-images/demo_files/pool.jpg", "Thibaut", 253));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Jonathan", 243));
        scores.add(new ScoreClass("https://cloud.netlifyusercontent.com/assets/344dbf88-fdf9-42bb-adb4-46f01eedd629/68dd54ca-60cf-4ef7-898b-26d7cbe48ec7/10-dithering-opt.jpg", "Antoine", 234));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Émillie", 231));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Caroline", 230));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Emmanuelle", 224));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Steven", 204));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Sophie", 102));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Lison", 96));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Willy", 88));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Logan", 59));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Florent", 57));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Mathieu", 25));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Tom", 23));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Lea", 16));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Steve", 14));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Tim", 12));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Fleur", 9));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Thomas", 7));
        scores.add(new ScoreClass("http://www.voyage-sponsorise.com/wp-content/uploads/2017/09/image-slider2.jpg", "Lisa", 4));

        scores.add(new ScoreClass(imgAvatar, name, scoreP));

        return scores;
    }
}


