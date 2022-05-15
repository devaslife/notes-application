package com.example.note.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.note.Models.Notes;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database; // i need to make instance form RoomDB class
    private static String DATABASE_NAME = "NoteApp"; // make the name for the database

    // this method to get the instance from database
    public synchronized static RoomDB getInstance(Context context) {
        // i need test if i have instance of database or not
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MainDAO mainDAO();
}
