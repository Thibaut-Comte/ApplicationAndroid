package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;


public class Menu extends AppCompatActivity {

    Button settings, speed, stamina, score, average;

    ImageView pp;

    ImageView img;

    Database DataB = new Database();

    Player player = new Player();

    TextView welcome;

    String shaone = "chaine a transformer en sha1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String profilepicture = sharedPreferences.getString("avatarP", "http://");
        String username = sharedPreferences.getString("username", "undefined");

//        // hashage du mdp
//        try {
//            Log.e("debug","SHA1 : "+DataB.sha(shaone));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }


        pp = findViewById(R.id.pp);

        //Création et assignation de l'image d'accueil avec ses spécificités
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();
        requestOptions.override(500, 500);
        requestOptions.circleCrop();

        Glide.with(getBaseContext())
                .load(profilepicture)
                .apply(requestOptions)
                .into(pp);


        //Remise à 0 pour average
        sharedPreferences.edit().putInt("Average", 0).apply();
        sharedPreferences.edit().putInt("Stamina", 0).apply();
        sharedPreferences.edit().putInt("StaminaLives", 3).apply();


        // On récupère le sharedPreferences "player"
        String test2 = sharedPreferences.getString("username", null);
        String urlAvatar = sharedPreferences.getString("avatar", null);
        Toast.makeText(this, "Bonjour "+test2, Toast.LENGTH_LONG).show();

        settings = findViewById(R.id.btnSettings);
        score = findViewById(R.id.btnScore);
        average = findViewById(R.id.btnAverage);
        speed = findViewById(R.id.btnSpeed);
        img = findViewById(R.id.avatar);
        stamina = findViewById(R.id.btnStamina);
        welcome = findViewById(R.id.welcome);


        welcome.setText(username);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ScoreSpeed.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Average.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Speed.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        stamina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Stamina.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        Context context = getApplicationContext();
        CharSequence text = "Welcome back "+player.getUsername();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }

}
