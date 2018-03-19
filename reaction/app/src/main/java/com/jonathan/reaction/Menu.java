package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


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

        final SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
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
        requestOptions.placeholder(R.drawable.placeholder   );
        requestOptions.circleCrop();


        Glide.with(getBaseContext())
                .load(profilepicture)
                .apply(requestOptions)
                .into(pp);


        //Remise à 0 pour average
        sharedPreferences.edit().putInt("Speed", 0).apply();
        sharedPreferences.edit().putInt("Average", 0).apply();
        sharedPreferences.edit().putInt("Stamina", 0).apply();
        sharedPreferences.edit().putInt("StaminaLives", 3).apply();
        sharedPreferences.edit().putString("ecran", "rouge").apply();



        // On récupère le sharedPreferences "player"
        String urlAvatar = sharedPreferences.getString("avatar", null);

        settings = findViewById(R.id.btnSettings);
        score = findViewById(R.id.btnScore);
        average = findViewById(R.id.btnAverage);
        speed = findViewById(R.id.btnSpeed);
        img = findViewById(R.id.avatar);
        stamina = findViewById(R.id.btnStamina);
        welcome = findViewById(R.id.welcome);

        welcome.setText(username);


        if (sharedPreferences.getString("Language", "English").equals("French")) {
            settings.setText("Parametres");
        }
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Menu.this, Settings.class);
                startActivity(intent);
            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ScoreSpeed.class);
                startActivity(intent);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("ruleRedirect", "Average").apply();
                Intent intent = new Intent(Menu.this, Decompte.class);
                startActivity(intent);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("ruleRedirect", "Speed").apply();
                Intent intent = new Intent(Menu.this, Decompte.class);
                startActivity(intent);
            }
        });

        stamina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("ruleRedirect", "Stamina").apply();
                Intent intent = new Intent(Menu.this, Decompte.class);
                startActivity(intent);
            }
        });


    }

    public void onBackPressed() {

    }

}
