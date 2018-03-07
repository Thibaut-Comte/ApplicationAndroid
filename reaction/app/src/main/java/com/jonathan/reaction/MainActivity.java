package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    LoginManager loginManager;
    boolean loggedIn;
    SharedPreferences sharedPreferences;
    private TextView tvlogin, tvpw, error;
    private EditText login, pw;
    private Button connection, create;

    protected Player player = new Player("Billy", "test");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvlogin = findViewById(R.id.tvlogin);
        tvpw = findViewById(R.id.tvpw);
        error = findViewById(R.id.error);
        login = findViewById(R.id.login);
        pw = findViewById(R.id.pw);
        connection = findViewById(R.id.connection);
        create = findViewById(R.id.createAccount);

        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);

        //Intent intent = new Intent(MainActivity.this, Menu.class);
        //startActivity(intent);

        //Nouvelle clé de Hash car celle générée par console ne fonctionne qu'une seule fois ensuite celle générée par le code
        // ci-dessous va prendre le relais
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.jonathan.reaction",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }


        sharedPreferences.edit().putString("username", player.getUsername()).apply();

        /* Intent i = new Intent(MainActivity.this, Menu.class);
        startActivity(i); */

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().length() > 0 && pw.getText().toString().length() > 0)
                {
                    if (login.getText().toString().equals(player.getUsername()) && pw.getText().toString().equals(player.getPassword()))
                    {
                        //Ajout au sharedPreferences "player" de la valeur player.getUsername() à la clé "username"
                        player = new Player(login.getText().toString(), pw.getText().toString());
                        sharedPreferences.edit().putString("username", "Paul").apply();
                        sharedPreferences.edit().putString("avatarP", "https://www.image.ie/images/no-image.png").apply();
                        sharedPreferences.edit().putInt("scoreP", 49).apply();
                        Intent i = new Intent(MainActivity.this, Menu.class);
                        startActivity(i);
                    }
                    else
                    {
                        error.setText("Login ou mot de passe incorrect");
                    }
                }
                else
                {
                    error.setText("Veuillez renseigner les deux champs svp");
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent account = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(account);
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userID = loginResult.getAccessToken().getUserId();
                String imgUrl = "http://graph.facebook.com/"+userID+"/picture?type=large";
                sharedPreferences.edit().putString("username", "Bill").apply();
                sharedPreferences.edit().putString("avatarP", "https://demo.phpgang.com/crop-images/demo_files/pool.jpg").apply();
                sharedPreferences.edit().putInt("scoreP", 52).apply();
                Intent i = new Intent(MainActivity.this, Menu.class);
                startActivity(i);


            }

            @Override
            public void onCancel() {
                Log.i("Cancel","true");
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
