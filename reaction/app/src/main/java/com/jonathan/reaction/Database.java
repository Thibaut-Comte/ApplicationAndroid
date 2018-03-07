package com.jonathan.reaction;

import android.content.Context;
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

public class Database {
    ArrayList<Player> players = new ArrayList<Player>();

    void Database() {}

    public void Recup(Context context, String key)
    {
        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        final DatabaseReference DBRef = DB.getReference("users");

        final Context contexte = context;
        final DatabaseReference DBRef2 = DBRef.child(key);
        DBRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.print(dataSnapshot.getValue());
                Player player = dataSnapshot.getValue(Player.class);
                String text = "";

                if(player.getFirstname().equals("") && player.getLastname().equals("")) {
                    text = "Bonjour "+dataSnapshot.getKey();
                }
                else
                {
                    text = "Bonjour "+player.getLastname()+" "+player.getFirstname();
                }
                int duration = Toast.LENGTH_LONG;
                //Context context = getApplicationContext();
                Toast toast = Toast.makeText(contexte, text, duration);
                toast.show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void RecupDB()
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
                    Log.e("debug","key:"+obj.getKey());
                    Log.e("debug",""+obj.getValue());
                    Player truc = obj.getValue(Player.class);
                    truc.setUsername(obj.getKey());
                    //users.put(obj.getKey(),obj.getValue(Player.class));
                    //Player Nplay = new Player(obj.getKey(),truc.getPassword(),
                    //truc.getHightscoreSpeed(),truc.getHightscoreAverage(),truc.getHightscoreStamina(),
                    //truc.getFirstname(),truc.getLastname());
                    players.add(truc);
                    Log.e("debug","Nouveau player ajoutée à la liste");
                    //Log.e("debug","classTruc:"+truc.getClass());
                    Log.e("debug","value "+truc.toString());
                    Log.e("debug","nbObjallUser : "+players.size());
                    Log.e("debug","NewPlayer : "+truc.toString());
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
}
