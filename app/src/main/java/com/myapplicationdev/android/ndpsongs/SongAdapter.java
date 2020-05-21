package com.myapplicationdev.android.ndpsongs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class SongAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Song> songs;
    int resource;
    ImageView[] imageViews;

    public SongAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.songs = songs;
        this.resource = resource;
    }


    public void addAll(ArrayList<Song> collection) {
        songs.addAll(collection);
    }


    @Override
    public void clear() {
        songs.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        //Match the UI components with Java variables

        Song song = songs.get(position);
        String title = song.getTitle();
        String singer = song.getSingers();
        String year = song.getYear()+"";
        int stars = song.getStars();


        imageViews = new ImageView[5];

        imageViews[0] = rowView.findViewById(R.id.ivStar1);
        imageViews[1] = rowView.findViewById(R.id.ivStar2);
        imageViews[2] = rowView.findViewById(R.id.ivStar3);
        imageViews[3] = rowView.findViewById(R.id.ivStar4);
        imageViews[4] = rowView.findViewById(R.id.ivStar5);

        TextView tvTitle = rowView.findViewById(R.id.tvTitle);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        TextView tvSingers = rowView.findViewById(R.id.tvSingers);

        //Check if the property for starts >= 5, if so, "light" up the stars
        for(int i = 0; i < stars; i++){
            imageViews[i].setImageResource(android.R.drawable.btn_star_big_on);
        }

        tvTitle.setText(title);
        tvSingers.setText(singer);
        tvYear.setText(year);

        return rowView;
    }

}
