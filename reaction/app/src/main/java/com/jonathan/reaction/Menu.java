package com.jonathan.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.*;

import java.util.Arrays;


public class Menu extends AppCompatActivity {

    Button settings, speed, stamina, score, average;
    LoginButton loginButton;
    CallbackManager callbackManager;
    LoginManager loginManager;
    boolean loggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        if (loggedIn) {
            loggedIn = AccessToken.getCurrentAccessToken() == null;
        }


        settings = findViewById(R.id.btnSettings);
        score = findViewById(R.id.btnScore);
        average = findViewById(R.id.btnAverage);
        speed = findViewById(R.id.btnSpeed);

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        //Connexion Facebook
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view) {
                ;
            }
        });
        loginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.i("connexion", "true");
                    }

                    @Override
                    public void onCancel() {
                        Log.i("connexion", "cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("connexion", "false");
                    }
                });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Menu.this, Settings.class);
//                startActivity(intent);
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
//                Intent intent = new Intent(Menu.this, Average.class);
//                startActivity(intent);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Speed.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
