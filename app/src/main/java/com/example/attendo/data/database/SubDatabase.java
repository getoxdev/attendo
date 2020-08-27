package com.example.attendo.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.attendo.data.SubEntity;
import com.example.attendo.data.SubDao;

@Database(entities = SubEntity.class, version = 2)
public abstract class SubDatabase extends RoomDatabase {

    public abstract SubDao SubDao();

    private static volatile SubDatabase SubRoomInstance;

    public static SubDatabase getDatabase(final Context context) {
        if (SubRoomInstance == null) {
            synchronized (SubDatabase.class) {
                if (SubRoomInstance == null) {
                    SubRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            SubDatabase.class, "subject_database")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return SubRoomInstance;
    }
}
