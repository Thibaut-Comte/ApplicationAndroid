package com.jonathan.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private TextView tvlogin, tvpw, error;
    private EditText login, pw;
    private Button connection, create;

    SharedPreferences sharedPreferences;

    protected Player player = new Player("Billy", "test");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // On récupère d'un sharedPreferences "player"
        sharedPreferences = getBaseContext().getSharedPreferences("player", MODE_PRIVATE);


        tvlogin = findViewById(R.id.tvlogin);
        tvpw = findViewById(R.id.tvpw);
        error = findViewById(R.id.error);
        login = findViewById(R.id.login);
        pw = findViewById(R.id.pw);
        connection = findViewById(R.id.connection);
        create = findViewById(R.id.createAccount);

        sharedPreferences.edit().putString("username", player.getUsername()).apply();

        Intent i = new Intent(Login.this, Menu.class);
        startActivity(i);

//        connection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (login.getText().toString().length() > 0 && pw.getText().toString().length() > 0)
//                {
//                    if (login.getText().toString().equals(player.getUsername()) && pw.getText().toString().equals(player.getPassword()))
//                    {
//                        //Ajout au sharedPreferences "player" de la valeur player.getUsername() à la clé "username"
        //player = new Player(login.getText().toString(), pw.getText().toString());
//                        sharedPreferences.edit().putString("username", player.getUsername()).apply();
//
//                        Intent i = new Intent(Login.this, Menu.class);
//                        startActivity(i);
//                    }
//                    else
//                    {
//                        error.setText("Login ou mot de passe incorrect");
//                    }
//                }
//                else
//                {
//                    error.setText("Veuillez renseigner les deux champs svp");
//                }
//            }
//        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent account = new Intent(Login.this, CreateAccount.class);
                startActivity(account);
            }
        });
    }
}
