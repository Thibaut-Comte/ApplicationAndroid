package com.jonathan.reaction;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Speed extends AppCompatActivity {

    Button btn1;
    TextView textchrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        btn1 = findViewById(R.id.btn);
        textchrono = findViewById(R.id.textchrono);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textchrono.setText("OK");
            }
        });
    }
}
