package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameAverage extends AppCompatActivity {

    private TextView score, result, Twtry;
    private Button rejouer, menu;
    private int nmbr = 0;
    private long Ascore = 0;
    private long Mscore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);
        Twtry = findViewById(R.id.Twtry);

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
                result.setText(R.string.victoire);
                Mscore = sharedPreferences.getLong("score", 0);
                Ascore = Ascore*(nmbr - 1);
                Ascore = (Ascore + Mscore) / nmbr;
                score.setText(""+Mscore);
                //Twtry.setText(""+nmbr+"score: "+Ascore); //crash Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
                sharedPreferences.edit().putLong("Ascore", Ascore).apply();
            }else if(nmbr == 10)
            {
                //Si 10 try on été fais
                //Twtry.setText("Fin");
                result.setText("Your average score is: "+Ascore);

            }

        } else if (sharedPreferences.getString("ecran", "").equals("rouge"))
        {
            result.setText(R.string.defaite);
            sharedPreferences.edit().putInt("Average", -1).apply();

        }


        //Redirection pour rejouer ou retourner au menu
        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameAverage.this, Average.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameAverage.this, Menu.class);
                startActivity(i);
            }
        });

    }
}
