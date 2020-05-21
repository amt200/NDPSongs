package com.myapplicationdev.android.ndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button btnUpdate, btnDelete, btnCancel;
    EditText etID, etTitle, etSingers, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        radioGroup = findViewById(R.id.rbGroupUpdate);

        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etSongTitleUpdate);
        etSingers = findViewById(R.id.etSingersUpdate);
        etYear = findViewById(R.id.etYearUpdate);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();

        Song song = (Song) intent.getSerializableExtra("songData");

        if(song != null) {
            String id = song.getId() + "";
            String year = song.getYear()+"";
            etID.setText(id);
            etTitle.setText(song.getTitle());
            etSingers.setText(song.getSingers());
            etYear.setText(year);

            RadioButton radioButton = null;
            if(song.getStars() == 1){
                radioButton = findViewById(R.id.rbStar1);
            }
            else if(song.getStars() == 2){
                radioButton = findViewById(R.id.rbStar2);
            }
            else if(song.getStars() == 3){
                radioButton = findViewById(R.id.rbStar3);
            }
            else if(song.getStars() == 4){
                radioButton = findViewById(R.id.rbStar4);
            }
            else{
                radioButton = findViewById(R.id.rbStar5);
            }

            if(radioButton != null){
                radioButton.setChecked(true);
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                if(etTitle.getText().toString().length() > 0 && etSingers.getText().toString().length() > 0 && etYear.getText().toString().length() > 0){
                    String title = etTitle.getText().toString();
                    String singer = etSingers.getText().toString();
                    int id = Integer.parseInt(etID.getText().toString());
                    int year = Integer.parseInt(etYear.getText().toString());
                    RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    int stars = Integer.parseInt(radioButton.getText().toString());
                    Song song = new Song(id, title, singer, year, stars);
                    int rowsAffected = db.updateSong(song);
                    db.close();
                    if(rowsAffected == 1){
                        Log.d("Status", "Successfully updated.");
                    }
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                int id = Integer.parseInt(etID.getText().toString());
                int rowsAffected = db.deleteSong(id);
                db.close();
                setResult(RESULT_OK);
                if(rowsAffected == 1){
                    Log.d("Status", "Successfully deleted.");
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
