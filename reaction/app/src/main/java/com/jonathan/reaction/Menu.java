package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;

import java.util.Arrays;


public class Menu extends AppCompatActivity {

    Button settings, speed, stamina, score, average;


    Player player = new Player();


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
                Intent intent = new Intent(Menu.this, Average.class);
                startActivity(intent);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Speed.class);
                startActivity(intent);
            }
        });

        Context context = getApplicationContext();
        CharSequence text = "Welcom back "+player.getUsername();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }
}
