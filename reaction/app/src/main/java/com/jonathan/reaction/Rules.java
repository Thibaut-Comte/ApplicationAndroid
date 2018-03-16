package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rules extends AppCompatActivity {

    private Button btn;
    private TextView speedR, staminaR, averageR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String language = sharedPreferences.getString("Language", "English");

        speedR = findViewById(R.id.speedR);
        staminaR = findViewById(R.id.staminaR);
        averageR = findViewById(R.id.averageR);

        if (language.equals("French"))
        {
            speedR.setText("Dès que l'écran passe vert, appuyez le plus vite possible !");
            averageR.setText("Dès que l'écran passe vert, appuyez le plus vite possible ! Recommencez 10 fois pour avoir votre temps de réaction moyen !");
            staminaR.setText("Dès que l'écran passe vert, appuyez sur l'écran avant qui'il ne devienne bleu ! Faites le le plus vite possible, vous n'aurez que 3 vies !");
        }

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Rules.this, Menu.class);
                startActivity(i);
            }
        });

    }
}
