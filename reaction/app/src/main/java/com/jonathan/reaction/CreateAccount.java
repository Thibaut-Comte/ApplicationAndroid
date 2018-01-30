package com.jonathan.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    private TextView tvlogin, tvpw, error;
    private EditText login, pw, confirmpw;
    private Button create;


    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        tvlogin = findViewById(R.id.tvlogin);
        tvpw = findViewById(R.id.tvpw);
        error = findViewById(R.id.error);
        login = findViewById(R.id.login);
        pw = findViewById(R.id.pw);
        confirmpw = findViewById(R.id.confirmpw);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().length() > 0 && pw.getText().toString().length() > 0)
                {
                    if (pw.getText().toString().equals(confirmpw.getText().toString()))
                    {
                        player = new Player(login.getText().toString(), pw.getText().toString());
                        Toast.makeText(CreateAccount.this, "Compte créer "+player.getUsername(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(CreateAccount.this, Login.class);
                        startActivity(i);

                    } else {
                        error.setText("Mot de passe non identique");
                    }

                } else {
                    error.setText("Veuillez renseigner les deux champs svp");
                }
            }
        });

    }
}
