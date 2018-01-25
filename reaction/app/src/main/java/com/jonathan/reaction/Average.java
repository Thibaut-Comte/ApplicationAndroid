package com.jonathan.reaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Average extends AppCompatActivity {


    private Boolean bool = true;
    private RelativeLayout rl;
    private int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average);

        Button mBtn_color = findViewById(R.id.btn_color);
        Button mBtn_trg = findViewById(R.id.btn_trg);
        Button mBtn_vrr = findViewById(R.id.btn_vrr);

        rl = findViewById(R.id.layoutgene);

        mBtn_trg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setBackgroundColor(Color.GREEN);
                if (bool){
                    bool = false;
                }
                else{
                    bool = true;
                }
            }
        });
        mBtn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bool){
                    rl.setBackgroundColor(Color.RED);
                }
                else{
                    rl.setBackgroundColor(Color.WHITE);

                }
            }
        });

        mBtn_vrr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                while(i < 10){
                    v.vibrate(150);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        });

    }
}
