package com.jonathan.reaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thibaut on 05/12/2017.
 */

public class ScoreAdapter extends ArrayAdapter<ScoreClass>{

    public ScoreAdapter(Context context, List<ScoreClass> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("player", Context.MODE_PRIVATE);
        String lan = sharedPreferences.getString("Language", "English");

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout,parent, false);
        }

        ScoreViewHolder viewHolder = (ScoreViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ScoreViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.score = (TextView) convertView.findViewById(R.id.score);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Player> players
        ScoreClass scoreClass = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(scoreClass.getPseudo());
        Log.e("pseudoLaBAs", scoreClass.getPseudo());
        Log.e("shared", sharedPreferences.getString("username", "undefined"));
        viewHolder.pseudo.setTextColor(Color.parseColor("#FFFFFF"));
        if (scoreClass.getPseudo().equals(sharedPreferences.getString("username", "undefined"))) {
            viewHolder.pseudo.setTextColor(Color.parseColor("#FF0000"));
            Log.e("color", "color");
            Log.e("pseudo", scoreClass.getPseudo());
            Log.e("shared", sharedPreferences.getString("username", "undefined"));

        }
        if(scoreClass.getScore() != 0)
        {
            viewHolder.score.setText(Integer.toString(scoreClass.getScore()));
        }
        else{
            if (lan.equals("French")) {
                viewHolder.score.setText("Vous n'avez pas encore jouer");
            } else {
                viewHolder.score.setText("not played yet");
            }
        }
        Picasso.with(getContext()).load(scoreClass.getUrl()).into(viewHolder.avatar);

        return convertView;
    }

    private class ScoreViewHolder{
        public TextView pseudo;
        public TextView score;
        public ImageView avatar;
    }
}
