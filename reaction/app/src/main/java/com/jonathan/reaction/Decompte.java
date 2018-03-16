package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Decompte extends AppCompatActivity {

    private TextView compteur;
    private SharedPreferences sharedPreferences;
    private int cpt = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decompte);
        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String mode = sharedPreferences.getString("ruleRedirect", "error");

        compteur = findViewById(R.id.cpt);

        compteur.setText(""+cpt);

        t.start();

    }

    //@Override
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
            String mode = sharedPreferences.getString("ruleRedirect", "error");

            //decompte
        do {
            try {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        compteur.setText(""+cpt);
                    }
                });
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cpt -= 1;
        } while (cpt >= 1);

            switch (mode) {
                case "Average" :
                    Intent average = new Intent(Decompte.this, Average.class);
                    average.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(average);
                    break;
                case "Speed" :
                    Intent speed = new Intent(Decompte.this, Speed.class);
                    speed.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(speed);
                    break;
                case "Stamina" :
                    Intent stamina = new Intent(Decompte.this, Stamina.class);
                    stamina.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(stamina);
                    break;
                default:
                    //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    });

    public void onBackPressed() {

    }

}
