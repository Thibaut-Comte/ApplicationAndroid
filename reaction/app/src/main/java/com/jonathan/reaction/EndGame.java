package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

private TextView score, result;
private Button rejouer, menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String victoire = sharedPreferences.getString("victoire", "error system");


        //Gestion du résultat de l'utilisateur
        if (sharedPreferences.getString("ecran", "").equals("vert"))
        {
            result.setText(R.string.victoire);
            score.setText(""+sharedPreferences.getLong("score", 0));
        } else if (sharedPreferences.getString("ecran", "").equals("rouge"))
        {
            result.setText(R.string.defaite);
        }


        //Redirection pour rejouer ou retourner au menu
        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGame.this, Speed.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGame.this, Menu.class);
                startActivity(i);
            }
        });

    }
}
