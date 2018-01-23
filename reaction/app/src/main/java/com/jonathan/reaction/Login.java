package com.jonathan.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private TextView tvlogin, tvpw, error;
    private EditText login, pw;
    private Button connection;

    Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvlogin = findViewById(R.id.tvlogin);
        tvpw = findViewById(R.id.tvpw);
        error = findViewById(R.id.error);
        login = findViewById(R.id.login);
        pw = findViewById(R.id.pw);
        connection = findViewById(R.id.connection);


        Intent i = new Intent(Login.this, Menu.class);
        startActivity(i);

//        connection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (login.getText().toString().equals(player.getUsername())&& pw.getText().toString().equals(player.getPassword())) {
//                    Intent i = new Intent(Login.this, Menu.class);
//                    startActivity(i);
//                }
//                else {
//                    error.setText("Login ou mot de passe incorrect");
//                }
//            }
//        });
    }
}
