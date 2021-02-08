package com.attendo.data.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.attendo.data.rem.RemDao;
import com.attendo.data.rem.RemEntity;

@Database(entities = RemEntity.class,version = 1)
public abstract class RemDatabase extends RoomDatabase {

    private static RemDatabase instance;

    public abstract RemDao RemDao();

    public static synchronized RemDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    RemDatabase.class,"reminder_databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
