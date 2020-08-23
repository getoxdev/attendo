package com.example.attendo.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.attendo.data.SubEntity;
import com.example.attendo.data.SubDao;

@Database(entities = SubEntity.class, version = 2)
public abstract class SubDatabase extends RoomDatabase {

    public abstract SubDao noteDao();

    private static volatile SubDatabase noteRoomInstance;

    public static SubDatabase getDatabase(final Context context) {
        if (noteRoomInstance == null) {
            synchronized (SubDatabase.class) {
                if (noteRoomInstance == null) {
                    noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            SubDatabase.class, "note_database")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return noteRoomInstance;
    }
}
