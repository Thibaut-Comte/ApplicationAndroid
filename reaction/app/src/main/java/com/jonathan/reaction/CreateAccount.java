package com.jonathan.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                if (login.getText().toString().length() > 0 && pw.getText().toString().length() > 0) {
                    if (pw.getText().toString().equals(confirmpw.getText().toString())) {

                        FirebaseDatabase DB = FirebaseDatabase.getInstance();
                        final DatabaseReference DBRef = DB.getReference("users");

                        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> Ids = dataSnapshot.getChildren();
                                boolean found = false;
                                String userName = login.getText().toString();
                                String pwd = pw.getText().toString();

                                for ( DataSnapshot obj : Ids) {
                                    Log.e("debug", userName + " " + obj.getKey());
                                    if(userName.equals(obj.getKey())) {
                                        Log.e("debug", "Trouvé");
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    Player player = new Player("", pwd);
                                    DBRef.child(userName).setValue(player);
                                    Toast.makeText(getBaseContext(), "Compte créer " + player.getUsername(), Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getBaseContext(), Menu.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    error.setText("ce username existe déja");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

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
