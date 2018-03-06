package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class Menu extends AppCompatActivity {

    Button settings, speed, stamina, score, average;

    ImageView img;

    Database DB = new Database();

    HashMap<String,Player> users = new HashMap<>();

    Player player = new Player();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        RecupIds(DBRef);

        final DatabaseReference DBRef2 = DBRef.child("arsenfat");
        Recup(DBRef2);

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
                Intent intent = new Intent(Menu.this, Score.class);
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

    public void Recup(Query recup)
    {
        recup.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.print(dataSnapshot.getValue());
                player = dataSnapshot.getValue(Player.class);
                String text = "";

                if(player.getFirstname().equals("")) {}
                else
                {
                    text = "Bonjour "+player.getLastname()+" "+player.getFirstname();
                }
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void RecupIds(Query recup)

    {
        recup.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.print(dataSnapshot.getValue());
//                ArrayList Ids = new ArrayList();
                Iterable<DataSnapshot> Ids = dataSnapshot.getChildren();
                DB.players.clear();
//                Log.e("reaction",""+Ids.getClass());
//                Log.e("reaction",""+Ids);
                for ( DataSnapshot obj : Ids) {
                    Log.e("debug","key:"+obj.getKey());
                    Log.e("debug",""+obj.getValue());
                    Player truc = obj.getValue(Player.class);
                    truc.setUsername(obj.getKey());
                    //users.put(obj.getKey(),obj.getValue(Player.class));
                    //Player Nplay = new Player(obj.getKey(),truc.getPassword(),
                    //truc.getHightscoreSpeed(),truc.getHightscoreAverage(),truc.getHightscoreStamina(),
                    //truc.getFirstname(),truc.getLastname());
                    DB.players.add(truc);
                    //Log.e("debug","classTruc:"+truc.getClass());
                    Log.e("debug","value "+truc.toString());
                    Log.e("debug","nbObjallUser : "+DB.players.size());
                    Log.e("debug","NewPlayer : "+truc.toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
