package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

public class EndGameStamina extends AppCompatActivity {

    private TextView score, result, twlvl;
    private Button rejouer, menu, share;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private int lvl = 0;
    private int lives = 0;
    private View main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_stamina);
        main = findViewById(R.id.mainS);

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);
        twlvl = findViewById(R.id.lvl);
        share = findViewById(R.id.shareS);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

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
            if (lives == 1) {
                //Lose
                sharedPreferences.edit().putInt("StaminaLives", 0).apply();
                twlvl.setText("Game Over");
                result.setText(R.string.defaite);
                rejouer.setVisibility(View.INVISIBLE);
                //Check si hightscore et mise en BDD
            }
            if (lives > 1) {
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
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGameStamina.this, Menu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

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
        Intent i = new Intent(EndGameStamina.this, Menu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
