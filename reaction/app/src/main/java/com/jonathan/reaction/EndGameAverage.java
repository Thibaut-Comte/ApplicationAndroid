package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.w3c.dom.Text;

public class EndGameAverage extends AppCompatActivity {

    private TextView score, result, share;
    private Button rejouer, menu;
    private int nmbr = 0;
    private long Ascore = 0;
    private long Mscore = 0;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private View main;
    private Database DataB = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_average);

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);
        share = findViewById(R.id.shareA);
        main = findViewById(R.id.mainA);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String victoire = sharedPreferences.getString("victoire", "error system");
        String lan = sharedPreferences.getString("Language", "English");


        //Gestion du résultat de l'utilisateur
        if (sharedPreferences.getString("ecran", "").equals("rouge")) {
            if (lan.equals("French")) {
                result.setText("Oh non, vous avez perdu. Rejouer ?");
            } else {
                result.setText(R.string.defaite);
            }
            sharedPreferences.edit().putInt("Average", 0).apply();
        } else if (sharedPreferences.getString("ecran", "").equals("vert")) {
            nmbr = sharedPreferences.getInt("Average", 0);
            if (nmbr < 10) {
                // Si on est en dessous des 10 try
                nmbr = nmbr + 1;
                Ascore = sharedPreferences.getLong("Ascore", 0);
                sharedPreferences.edit().putInt("Average", nmbr).apply();
                Mscore = sharedPreferences.getLong("score", 0);
                Ascore = Ascore * (nmbr - 1);
                Ascore = (Ascore + Mscore) / nmbr;
                score.setText("" + Mscore);
                if (lan.equals("French")) {
                    result.setText("Partie : "+ nmbr + " sur 10. Moyenne : "+ Ascore);
                } else {
                    result.setText("Game: " + nmbr + " of 10   Average: " + Ascore);
                }
                sharedPreferences.edit().putLong("Ascore", Ascore).apply();
            }
            if (nmbr == 10) {
                //Si 10 try on été fais
                if (lan.equals("French")) {
                    result.setText("Votre moyenne est de : "+ Ascore);
                } else {
                    result.setText("Your average score is: " + Ascore);
                }
                sharedPreferences.edit().putInt("Average", 0).apply();
                //check si hightscore et mise en BDD
                DataB.user = new Player(sharedPreferences.getString("username",""),"","",0,sharedPreferences.getLong("hsAv",0),0);
                DataB.user.checkHightscore(Ascore, "average");
            }

        }


        //Redirection pour rejouer ou retourner au menu
        if (lan.equals("French")) {
            rejouer.setText("Continuer");
        }
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

        Profile p = Profile.getCurrentProfile();

        if (p == null)
        {
            share.setVisibility(View.INVISIBLE);
        }
        if (lan.equals("French")) {
            share.setText("Partager");
        }
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = Screenshot.takeScreenShotRootView(main);

                if (shareDialog.canShow(SharePhotoContent.class)) {

                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(b)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(content);
                }
            }

        });

    }

    public void onBackPressed() {
        Intent i = new Intent(EndGameAverage.this, Menu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
