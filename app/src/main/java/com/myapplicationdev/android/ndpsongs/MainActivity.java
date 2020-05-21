package com.myapplicationdev.android.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button btnInsert, btnShowList;
    EditText etTitle, etSingers, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.rbGroup);

        etTitle = findViewById(R.id.etSongTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);

        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                if(etTitle.getText().toString().length() > 0 && etSingers.getText().toString().length() > 0 && etYear.getText().toString().length() > 0){
                    String title = etTitle.getText().toString();
                    String singer = etSingers.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    int stars = Integer.parseInt(radioButton.getText().toString());
                    db.insertSong(title, singer, year, stars);
                    db.close();
                    Toast.makeText(MainActivity.this, "Inserted successfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowListActivity.class);
                startActivity(intent);
            }
        });

    }
}
