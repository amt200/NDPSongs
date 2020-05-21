package com.myapplicationdev.android.ndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    ListView listView;
    Button btnShowSongs;
    ArrayList<Song> songArrayList;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        listView = findViewById(R.id.lv);
        btnShowSongs = findViewById(R.id.btnShow);

        final DBHelper db = new DBHelper(ShowListActivity.this);

        songArrayList = db.getAllSongs();
        songAdapter = new SongAdapter(ShowListActivity.this, R.layout.row, songArrayList);

        listView.setAdapter(songAdapter);
        songAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = (Song) parent.getItemAtPosition(position);
                if(song != null){
                    Intent intent = new Intent(ShowListActivity.this, EditActivity.class);
                    intent.putExtra("songData", song);
                    startActivityForResult(intent, 100);
                }
            }
        });

        btnShowSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songAdapter.clear();
                songAdapter.addAll(db.get5StarSongs());
                songAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final DBHelper db = new DBHelper(ShowListActivity.this);
        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
            if (requestCode == 100) {
                // Get data passed back from 2nd activity
                songAdapter.clear();
                ArrayList<Song> songs = db.getAllSongs();
                songAdapter.addAll(songs);
                songAdapter.notifyDataSetChanged();
            }
        }
    }
}
