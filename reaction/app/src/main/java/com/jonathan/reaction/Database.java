package com.jonathan.reaction;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Thomas on 06/03/2018.
 */

public class Database implements Runnable {
    ArrayList<Player> players = new ArrayList<Player>();
    Player user = new Player();

    void Database() {}

    public void Recup(String key) // récupération d'un child particulier
    {
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        final DatabaseReference DBRef2 = DBRef.child(key);
        DBRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.print(dataSnapshot.getValue());
                Player player = dataSnapshot.getValue(Player.class);
                String text = "Bonjour "+dataSnapshot.getKey();

                Log.e("debug",text);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void RecupDB() // pour les scores
    {
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.print(dataSnapshot.getValue());
//                ArrayList Ids = new ArrayList();
                Iterable<DataSnapshot> Ids = dataSnapshot.getChildren();
                players.clear();
//                Log.e("reaction",""+Ids.getClass());
//                Log.e("reaction",""+Ids);
                for ( DataSnapshot obj : Ids) {
                    //Log.e("debug","key:"+obj.getKey());
                    //Log.e("debug",""+obj.getValue());
                    Player truc = obj.getValue(Player.class);
                    truc.setUsername(obj.getKey());

                    Log.e("debug","Nouveau player ajoutée à la liste");
                    Log.e("debug","nbObjallUser : "+players.size());
                    Log.e("debug","NewPlayer : "+truc.toString());
                    truc.setPassword("");
                    players.add(truc);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void isUsernameExist(final Context context, final String userName, final String pw) {
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> Ids = dataSnapshot.getChildren();
                boolean found = false;
                for ( DataSnapshot obj : Ids) {
                    Log.e("debug", userName + " " + obj.getKey());
                    if(userName.equals(obj.getKey())) {
                        Log.e("debug", "Trouvé");
                        found = true;
                    }
                }
                if (!found) {
                    Player player = new Player("", pw);
                    DBRef.child(userName).setValue(player);
                    Toast.makeText(context, "Compte créer " + player.getUsername(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, Menu.class);
                    context.startActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    String sha(String input) throws NoSuchAlgorithmException {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return "fail";
    }

    @Override
    public void run() {

    }
}
