package com.example.attendo.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.attendo.data.SubEntity;
import com.example.attendo.data.SubDao;

@Database(entities = {SubEntity.class}, version = 3)
public abstract class SubDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Subject.db";
    public static volatile SubDatabase instance;
    private static final Object object = new Object();
    public abstract SubDao SubDao();

    public static SubDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            synchronized (object)
            {
                if(instance==null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(),SubDatabase.class,DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
