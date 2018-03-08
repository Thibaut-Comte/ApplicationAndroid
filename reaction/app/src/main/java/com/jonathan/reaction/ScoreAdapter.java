package com.jonathan.reaction;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
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
        viewHolder.score.setText(Integer.toString(scoreClass.getScore()));
        Picasso.with(getContext()).load(scoreClass.getUrl()).into(viewHolder.avatar);

        return convertView;
    }

    private class ScoreViewHolder{
        public TextView pseudo;
        public TextView score;
        public ImageView avatar;
    }
}
