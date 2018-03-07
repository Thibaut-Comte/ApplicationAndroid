package com.jonathan.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    private TextView tvlogin, tvpw, error;
    private EditText login, pw, confirmpw;
    private Button create;

    private Database DataB = new Database();

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
                FirebaseDatabase DB = FirebaseDatabase.getInstance();
                final DatabaseReference DBRef = DB.getReference("users");

                boolean loginLibre = true;
                DataB.RecupDB();
                for(int i=0;i<DataB.players.size();i++) {
                    if (login.getText().toString().equals(DataB.players.get(i).getUsername())) {
                        loginLibre = false;
                    }
                }
                if(loginLibre){
                    if (login.getText().toString().length() > 0 && pw.getText().toString().length() > 0) {
                        if (pw.getText().toString().equals(confirmpw.getText().toString())) {
                            player = new Player("", pw.getText().toString());
                            DBRef.child(login.getText().toString()).setValue(player);
                            Toast.makeText(CreateAccount.this, "Compte créer " + player.getUsername(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CreateAccount.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            error.setText("Mot de passe non identique");
                        }
                    } else {
                        error.setText("Veuillez renseigner les deux champs svp");
                    }
                } else{
                    error.setText("Ce username est déja pris");
                }
            }
        });
    }
}
