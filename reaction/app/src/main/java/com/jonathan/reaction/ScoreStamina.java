package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreStamina extends AppCompatActivity {

    private ListView lw;
    Button speed, stamina, average;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        speed = findViewById(R.id.speed);
        stamina = findViewById(R.id.stamina);
        average = findViewById(R.id.average);
        spinner = findViewById(R.id.progressBar);

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScoreStamina.this, ScoreSpeed.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        stamina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScoreStamina.this, ScoreStamina.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScoreStamina.this, ScoreAverage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        lw = findViewById(R.id.lw);

        List<ScoreClass> scores = genererScores();
    }

    public void onBackPressed()
    {
        Intent i = new Intent(ScoreStamina.this, Menu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    private List<ScoreClass> genererScores() {
        spinner.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        final String name = sharedPreferences.getString("username", "undefined");
        final String imgAvatar = sharedPreferences.getString("avatarP", "https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png");
        int scoreP = sharedPreferences.getInt("scoreSpeed", 0);

        final List<ScoreClass> scores = new ArrayList<ScoreClass>();
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> Ids = dataSnapshot.getChildren();
                for ( DataSnapshot obj : Ids) {
                    Player pTemp = obj.getValue(Player.class);

                    if(pTemp.getHightscoreStamina() != 0) {
                        if(name.equals(obj.getKey()))
                        {
                            scores.add(new ScoreClass(imgAvatar, name, (int) pTemp.getHightscoreStamina()));
                        }
                        else
                        {
                            scores.add(new ScoreClass("https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png", obj.getKey(), (int) pTemp.getHightscoreStamina()));
                        }
                    }
                }
                Collections.sort(scores,new Comparator<ScoreClass>() {
                    @Override
                    public int compare(ScoreClass scoreClass, ScoreClass t1) {
                        if(scoreClass.getScore() < t1.getScore())
                        {
                            return 1;
                        }
                        return -1;
                    }
                });
                ScoreAdapter adapter = new ScoreAdapter(lw.getContext(), scores);
                while(scores.size() > 10)
                {
                    scores.remove(10);
                }
                boolean find = false;
                for(int i=0;i<scores.size();i++)
                {
                    if (scores.get(i).getPseudo().equals(name))
                    {
                        find = true;
                    }
                }
                if(!find)
                {
                    scores.add(new ScoreClass(imgAvatar,name,0));
                }
                spinner.setVisibility(View.GONE);
                lw.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return scores;
    }
}


