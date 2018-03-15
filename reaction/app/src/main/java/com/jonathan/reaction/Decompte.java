package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Decompte extends AppCompatActivity implements Runnable {

    private TextView compteur;
    private SharedPreferences sharedPreferences;
    private int cpt = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String mode = sharedPreferences.getString("ruleRedirect", "error");

        compteur = findViewById(R.id.cpt);

        run();



    }

    @Override
    public void run() {

        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String mode = sharedPreferences.getString("ruleRedirect", "error");

        do {
            try {
                compteur.setText(""+cpt);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cpt -= 1;
        } while (cpt > 0);

        switch (mode) {
            case "Average" :
                Intent average = new Intent(Decompte.this, Average.class);
                startActivity(average);
                break;
            case "Speed" :
                Intent speed = new Intent(Decompte.this, Speed.class);
                startActivity(speed);
                break;
            case "Stamina" :
                Intent stamina = new Intent(Decompte.this, Stamina.class);
                startActivity(stamina);
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
