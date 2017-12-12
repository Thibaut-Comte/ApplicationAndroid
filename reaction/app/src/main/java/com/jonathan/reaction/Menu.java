package com.jonathan.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    Button settings, speed, stamina, score, average;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        settings = findViewById(R.id.btnSettings);
        score = findViewById(R.id.btnScore);
        average = findViewById(R.id.btnAverage);
        speed = findViewById(R.id.btnSpeed);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Menu.this, Settings.class);
//                startActivity(intent);
            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Score.class);
                startActivity(intent);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Menu.this, Average.class);
//                startActivity(intent);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Menu.this, Speed.class);
//                startActivity(intent);
            }
        });



    }
}
