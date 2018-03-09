package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndGameAverage extends AppCompatActivity {

    private TextView score, result;
    private Button rejouer, menu;
    private int nmbr = 0;
    private long Ascore = 0;
    private long Mscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_average);

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String victoire = sharedPreferences.getString("victoire", "error system");


        //Gestion du résultat de l'utilisateur
        if (sharedPreferences.getString("ecran", "").equals("vert"))
        {
            nmbr = sharedPreferences.getInt("Average", 0);
            if (nmbr < 10) {
                // Si on est en dessous des 10 try
                nmbr = nmbr + 1;
                Ascore = sharedPreferences.getLong("Ascore", 0);
                sharedPreferences.edit().putInt("Average", nmbr).apply();
                Mscore = sharedPreferences.getLong("score", 0);
                Ascore = Ascore*(nmbr - 1);
                Ascore = (Ascore + Mscore) / nmbr;
                score.setText(""+Mscore);
                result.setText("Game: "+nmbr+" of 10   Average: "+Ascore);
                sharedPreferences.edit().putLong("Ascore", Ascore).apply();
            } if(nmbr == 10)
            {
                //Si 10 try on été fais
                result.setText("Your average score is: "+Ascore);
                sharedPreferences.edit().putInt("Average", 0).apply();
                //check si hightscore et mise en BDD
            }

        } else if (sharedPreferences.getString("ecran", "").equals("rouge"))
        {
            result.setText(R.string.defaite);
            sharedPreferences.edit().putInt("Average", 0).apply();
        }


        //Redirection pour rejouer ou retourner au menu
        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameAverage.this, Average.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameAverage.this, Menu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

    }

    public void onBackPressed()
    {
        Intent i = new Intent(EndGameAverage.this, Menu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }
}
