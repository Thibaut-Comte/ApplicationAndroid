package com.jonathan.reaction;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Speed extends AppCompatActivity {


    Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        btn1 = findViewById(R.id.btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.realtive);
                rl.setBackgroundColor(Color.RED);
            }
        });
    }
}
