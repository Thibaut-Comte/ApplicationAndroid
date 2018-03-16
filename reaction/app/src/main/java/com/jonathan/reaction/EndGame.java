package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

public class EndGame extends AppCompatActivity {

private TextView score, result;
private Button rejouer, menu, share;
private ShareDialog shareDialog;
private CallbackManager callbackManager;
private View main;
private Database DataB = new Database();
private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        main = findViewById(R.id.main);
        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String lan = sharedPreferences.getString("Language", "English");

        score = findViewById(R.id.score);
        result = findViewById(R.id.result);
        rejouer = findViewById(R.id.rejouer);
        menu = findViewById(R.id.menu);
        share = findViewById(R.id.share);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);



        /*if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(""))
                    .build();
            shareDialog.show(linkContent);
        }*/

        Profile p = Profile.getCurrentProfile();

        if (p == null)
        {
            share.setVisibility(View.INVISIBLE);
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




        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String victoire = sharedPreferences.getString("victoire", "error system");


        //Gestion du résultat de l'utilisateur
        if (sharedPreferences.getString("ecran", "").equals("vert"))
        {
            if (lan.equals("French")) {
                result.setText("Bien jouer ! Voulez-vous ameliorer votre score !");
            } else {
                result.setText(R.string.victoire);
            }
            score.setText(""+sharedPreferences.getLong("score", 0));
            //Check si hight score et mise en BDD
            DataB.user = new Player(sharedPreferences.getString("username",""),"","",sharedPreferences.getLong("hsSp",0),0,0);
            DataB.user.checkHightscore(sharedPreferences.getLong("score", 0), "speed");
        } else if (sharedPreferences.getString("ecran", "").equals("rouge"))
        {
            if (lan.equals("French")) {
                result.setText("Oh non ! Vous avez perdu");
            } else {
                result.setText(R.string.defaite);
            }
        }


        if (lan.equals("French")) {
            rejouer.setText("Rejouer");
            share.setText("Partager");
        }
        //Redirection pour rejouer ou retourner au menu
        rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGame.this, Speed.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EndGame.this, Menu.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

    }

    public void onBackPressed()
    {
        Intent i = new Intent(EndGame.this, Menu.class);
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
