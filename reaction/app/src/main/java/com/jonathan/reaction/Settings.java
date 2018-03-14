package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    private Switch sound, vibrate;
    private SharedPreferences sharedPreferences;
    private Boolean soundTest, vibrateTest;
    private Button logout;
    private CallbackManager callbackManager;
    private Profile p;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logout = findViewById(R.id.logout);

        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
        soundTest = sharedPreferences.getBoolean("sound", true);
        vibrateTest = sharedPreferences.getBoolean("vibrate", true);


        sound = findViewById(R.id.sound);
        vibrate = findViewById(R.id.vibrate);

        //Met les boutons sur on ou off suivant les valeurs en mémoire
        if (!soundTest) {
            sound.setChecked(false);
        }

        if (!vibrateTest) {
            vibrate.setChecked(false);
        }

        //Assignation de on ou off pour le reste de l'appli
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
                if (sound.isChecked())
                {
                    sharedPreferences.edit().putBoolean("sound", true).apply();
                } else if (!sound.isChecked())
                {
                    sharedPreferences.edit().putBoolean("sound", false).apply();
                }
            }
        });


        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);
                if (vibrate.isChecked())
                {
                    sharedPreferences.edit().putBoolean("vibrate", true).apply();
                } else if (!vibrate.isChecked())
                {
                    sharedPreferences.edit().putBoolean("vibrate", false).apply();
                }
            }
        });

        p = Profile.getCurrentProfile();

        if (p == null)
        {
            logout.setVisibility(View.INVISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginManager.getInstance().logOut();
                Intent i = new Intent(Settings.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    //Action de l'appui du bouton "physique" retour
    public void onBackPressed()
    {
        Intent i = new Intent(Settings.this, Menu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
