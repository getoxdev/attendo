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
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
      private RemDao remDao;

        private PopulateDbAsyncTask(RemDatabase db){
            remDao=db.RemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            remDao.insert(new RemEntity("9:00 AM","Maths"));
            remDao.insert(new RemEntity("10:00 AM","ECE"));
            remDao.insert(new RemEntity("11:00 AM","CSE"));
            return null;
        }
    }
}
