package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

<<<<<<< HEAD
=======
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;
import com.squareup.picasso.Picasso;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static java.security.spec.MGF1ParameterSpec.SHA1;

>>>>>>> 0b80da49c682dfe677c0a6fbc7adf53fdfa731b0

public class Menu extends AppCompatActivity {

    Button settings, speed, stamina, score, average;

    ImageView img;

    Database DataB = new Database();

    Player player = new Player();

    String shaone = "chaine a transformer en sha1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        DataB.RecupIds(DBRef);

        DataB.Recup(getApplicationContext(), DBRef, "hellboy");

        try {
            Log.e("debug","SHA1 : "+DataB.sha(shaone));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Remise à 0 pour average
        SharedPreferences SharedPreferences1 = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        SharedPreferences1.edit().putInt("Average", 0).apply();


        // On récupère le sharedPreferences "player"
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        String test2 = sharedPreferences.getString("username", null);
        String urlAvatar = sharedPreferences.getString("avatar", null);
        Toast.makeText(this, "Bonjour "+test2, Toast.LENGTH_LONG).show();

        settings = findViewById(R.id.btnSettings);
        score = findViewById(R.id.btnScore);
        average = findViewById(R.id.btnAverage);
        speed = findViewById(R.id.btnSpeed);
        img = findViewById(R.id.avatar);

<<<<<<< HEAD
=======
        //Log.i("avatar", urlAvatar);



        //Picasso.with(getBaseContext()).load(urlAvatar).into(img);

        new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(
                    JSONObject object,
                    GraphResponse response) {
                // Application code

                try {


                    if (object.has("picture")) {
                        //String profilePicUrl="http://graph.facebook.com/"+object.getString("id")+"/picture?type=large";
                        String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                        profilePicUrl = profilePicUrl.replace("\\", "");


                        Picasso.with(Menu.this)
                                .load(profilePicUrl)
                                .into(img);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


>>>>>>> 0b80da49c682dfe677c0a6fbc7adf53fdfa731b0
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Settings.class);
                startActivity(intent);
            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ScoreSpeed.class);
                startActivity(intent);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Average.class);
                startActivity(intent);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Speed.class);
                startActivity(intent);
            }
        });

        Context context = getApplicationContext();
        CharSequence text = "Welcom back "+player.getUsername();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }

}
