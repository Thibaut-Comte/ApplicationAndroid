package com.jonathan.reaction;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    private Switch sound, vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        sound = findViewById(R.id.sound);
        vibrate = findViewById(R.id.vibrate);

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

    }
}
