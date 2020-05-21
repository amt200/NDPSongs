package com.myapplicationdev.android.ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //TODO Define the Database properties
    private static final int DATABASE_VERSION = 1;
    // Filename of the database
    private static final String DATABASE_NAME = "songs.db";

    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGER = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STAR = "stars";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO CREATE TABLE Note
        String createTableSql = "CREATE TABLE " + TABLE_SONG +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT NOT NULL,"
                + COLUMN_SINGER + " TEXT NOT NULL,"
                + COLUMN_YEAR + " INTEGER NOT NULL,"
                + COLUMN_STAR + " INTEGER NOT NULL)";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public void insertSong(String title, String singer, int year, int stars) {
        //TODO insert the data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGER, singer);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STAR, stars);

        db.insert(TABLE_SONG, null, values);
        // Close the database connection
        db.close();
    }

    public ArrayList<Song> getAllSongs() {
        //TODO return records in Java objects
        // Create an ArrayList that holds String objects
        ArrayList<Song> songs = new ArrayList<Song>();

        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STAR};

        Cursor cursor = db.query(TABLE_SONG, columns, null, null,
                null, null, null, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);

                Song song = new Song(id, title, singer, year, star);

                songs.add(song);

            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return songs;
    }


    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGER, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STAR, data.getStars());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Song> get5StarSongs(){
        ArrayList<Song> songs = new ArrayList<Song>();

        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGER, COLUMN_YEAR, COLUMN_STAR};

        String condition = COLUMN_STAR + " = ?";
        String[] args = { String.valueOf(5) };
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);

                Song song = new Song(id, title, singer, year, star);

                songs.add(song);

            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return songs;
    }

}