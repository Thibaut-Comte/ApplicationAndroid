package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

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

        Intent intent = new Intent(MainActivity.this, Menu.class);
        startActivity(intent);

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
                        sharedPreferences.edit().putString("avatarP", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14732274_1482356538458018_1995408217779014922_n.jpg?oh=fe46f9b9783c8bf4ac586fcee33aabf0&oe=5B052DB9").apply();
                        sharedPreferences.edit().putInt("scoreSpeed", 49).apply();
                        sharedPreferences.edit().putInt("scoreStamina", 59).apply();
                        sharedPreferences.edit().putInt("scoreAverage", 75).apply();
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
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                //Assignation du profile facebook dans l'objet Profile pour pouvoir utiliser ses attributs
                Profile p = Profile.getCurrentProfile();

                String userID = loginResult.getAccessToken().getUserId();
                Log.e("id", userID);
                Log.e("token", ""+loginResult.getAccessToken().getToken());
                sharedPreferences.edit().putString("username", p.getName()).apply();
                sharedPreferences.edit().putString("avatarP", p.getProfilePictureUri(200, 200).toString()).apply();
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

        loginButton.setReadPermissions("email", "publish_actions");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
