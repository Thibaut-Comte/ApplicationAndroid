package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginManager loginManager;
    boolean loggedIn;
    SharedPreferences sharedPreferences;
    private TextView tvlogin, tvpw, error;
    private EditText login, pw;
    private Button connection, create;
    private ProgressBar spinner;

    Database DataB = new Database();

    private Profile p;

    FirebaseDatabase DB = FirebaseDatabase.getInstance();
    final DatabaseReference DBRef = DB.getReference("users");

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
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);

        p = Profile.getCurrentProfile();
        String ptmp = sharedPreferences.getString("username", null);

        if (ptmp != null && sharedPreferences.getBoolean("firstUse", true) == false)
        {
            DBRef.child(ptmp).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Player playerTemp = dataSnapshot.getValue(Player.class);
                    if(dataSnapshot.getValue(Player.class) != null)
                    {
                        sharedPreferences.edit().putLong("hsAv", playerTemp.getHightscoreAverage()).apply();
                        sharedPreferences.edit().putLong("hsSp", playerTemp.getHightscoreSpeed()).apply();
                        sharedPreferences.edit().putLong("hsSt", playerTemp.getHightscoreStamina()).apply();
                    }
                    if(p != null)
                    {
                        if(!(playerTemp.equals(p.getProfilePictureUri(500, 500).toString())))
                        {
                            DBRef.child(p.getName()).child("url").setValue(p.getProfilePictureUri(500, 500).toString());
                        }
                    }
                    String test2 = sharedPreferences.getString("username", null);
                    Toast.makeText(getBaseContext(), "Bonjour "+test2, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            Intent i = new Intent(MainActivity.this, Menu.class);
            startActivity(i);
        } else if (p != null && sharedPreferences.getBoolean("firstUse", true) == true)
        {
            loginManager.getInstance().logOut();
        }

        /*Intent intent = new Intent(MainActivity.this, Menu.class);
        startActivity(intent);*/

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

        //sharedPreferences.edit().putString("username", player.getUsername()).apply();

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), "Connexion en cours", Toast.LENGTH_SHORT).show();
                if (login.getText().toString().length() > 0 && pw.getText().toString().length() > 0)
                {
                    DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> Ids = dataSnapshot.getChildren();
                            boolean connOk = false;
                            String userName = login.getText().toString();
                            String pwd = pw.getText().toString();
                            try {
                                pwd =  DataB.sha(pwd);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }

                            for ( DataSnapshot obj : Ids) {
                                if(userName.equals(obj.getKey())) {
                                    Player playerTemp = obj.getValue(Player.class);
                                    if(pwd.equals(playerTemp.getPassword()))
                                    {
                                        connOk = true;
                                        sharedPreferences.edit().putString("username", userName).apply();
                                        sharedPreferences.edit().putLong("hsAv", playerTemp.getHightscoreAverage()).apply();
                                        sharedPreferences.edit().putLong("hsSp", playerTemp.getHightscoreSpeed()).apply();
                                        sharedPreferences.edit().putLong("hsSt", playerTemp.getHightscoreStamina()).apply();
                                        String test2 = sharedPreferences.getString("username", null);
                                        Toast.makeText(getBaseContext(), "Bonjour "+test2, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            if (connOk) {
                                if (sharedPreferences.getBoolean("firstUse", true))
                                {
                                    sharedPreferences.edit().putBoolean("firstUse", false).apply();
                                    Intent i = new Intent(MainActivity.this, Rules.class);
                                    startActivity(i);
                                } else {
                                    Intent i = new Intent(getBaseContext(), Menu.class);
                                    startActivity(i);
                                }
                            }
                            else
                            {
                                error.setText("ce couple username/password n'existe pas");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                else
                {
                    error.setText("Veuillez renseigner les deux champs svp");
                }
                spinner.setVisibility(View.GONE);
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
                p = Profile.getCurrentProfile();

                String userID = loginResult.getAccessToken().getUserId();
                Log.e("id", userID);
                Log.e("token", ""+loginResult.getAccessToken().getToken());
                sharedPreferences.edit().putString("username", p.getName()).apply();
                sharedPreferences.edit().putString("avatarP", p.getProfilePictureUri(500, 500).toString()).apply();
                DBRef.child(p.getName()).child("url").setValue(p.getProfilePictureUri(500, 500).toString());
                sharedPreferences.edit().putInt("scoreP", 52).apply();
                sharedPreferences.edit().putString("token", loginResult.getAccessToken().getToken()).apply();

                if (sharedPreferences.getBoolean("firstUse", true))
                {
                    sharedPreferences.edit().putBoolean("firstUse", false).apply();
                    Intent i = new Intent(MainActivity.this, Rules.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(MainActivity.this, Menu.class);
                    startActivity(i);
                }
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

        loginButton.setReadPermissions("email");
    }

    //Action de l'appui du bouton "physique" retour
    public void onBackPressed()
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
