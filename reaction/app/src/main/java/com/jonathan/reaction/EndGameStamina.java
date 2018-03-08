package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameStamina extends AppCompatActivity {

    private TextView score, result, twlvl;
    private Button rejouer, menu;
    private int lvl = 0;
    private int lives = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_stamina);

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);
        twlvl = findViewById(R.id.lvl);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String victoire = sharedPreferences.getString("victoire", "error system");


        //Gestion du résultat de l'utilisateur
        if (sharedPreferences.getString("ecran", "").equals("vert")) {
            result.setText(R.string.victoire);
            score.setText("" + sharedPreferences.getLong("score", 0));
            lvl = sharedPreferences.getInt("Stamina", 0);
            lvl = lvl + 1;
            sharedPreferences.edit().putInt("Stamina", lvl).apply();
            twlvl.setText("" + lvl);
        } else if (sharedPreferences.getString("ecran", "").equals("rouge")) {
            lives = sharedPreferences.getInt("StaminaLives", lives);
            if (lives == 0) {
                //Lose
                sharedPreferences.edit().putInt("StaminaLives", 0).apply();
                result.setText(R.string.defaite);
            }
            if (lives > 0) {
                // - une vie
                lives = lives - 1;
                sharedPreferences.edit().putInt("StaminaLives", lives).apply();
                result.setText("You just lot one life, only " + lives + " left");
            }
            lvl = sharedPreferences.getInt("Stamina", 0);
            score.setText("" + lvl);
        }


        //Redirection pour rejouer ou retourner au menu
        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameStamina.this, Stamina.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameStamina.this, Menu.class);
                startActivity(i);
            }
        });

    }

    public void onBackPressed() {
        Intent i = new Intent(EndGameStamina.this, Menu.class);
        startActivity(i);
    }
}
