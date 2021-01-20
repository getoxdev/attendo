package com.attendo.ui.main.drawers.reminder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.attendo.data.database.RemDatabase;
import com.attendo.data.rem.RemDao;
import com.attendo.data.rem.RemEntity;

import java.util.List;

public class RemRepository {
    private RemDao remDao;
    private LiveData<List<RemEntity>> allReminders;

    public RemRepository(Application application){
        RemDatabase remDatabase=RemDatabase.getInstance(application);
        remDao=remDatabase.RemDao() ;
        allReminders=remDao.getAllReminder();
    }
    public void insert(RemEntity remEntity){
        new InsertRemAsyncTask(remDao).execute(remEntity);

    }

    public void update(RemEntity remEntity){
      new UpdateRemAsyncTask(remDao).execute(remEntity);
    }

    public void delete(RemEntity remEntity){
        new DeleteRemAsyncTask(remDao).execute(remEntity);
    }

    public LiveData<List<RemEntity>> getAllReminders(){
        return allReminders;
    }

    private static class InsertRemAsyncTask extends AsyncTask<RemEntity,Void,Void>{
       private RemDao remDao;

       private InsertRemAsyncTask(RemDao remDao){
           this.remDao=remDao;
       }
        @Override
        protected Void doInBackground(RemEntity... remEntities) {
           remDao.insert(remEntities[0]);
            return null;
        }
    }
    private static class UpdateRemAsyncTask extends AsyncTask<RemEntity,Void,Void> {
        private RemDao remDao;

        private UpdateRemAsyncTask(RemDao remDao) {
            this.remDao = remDao;
        }

        @Override
        protected Void doInBackground(RemEntity... remEntities) {
            remDao.update(remEntities[0]);
            return null;
        }
    }
        private static class DeleteRemAsyncTask extends AsyncTask<RemEntity,Void,Void>{
            private RemDao remDao;

            private DeleteRemAsyncTask(RemDao remDao){
                this.remDao=remDao;
            }
            @Override
            protected Void doInBackground(RemEntity... remEntities) {
                remDao.delete(remEntities[0]);
                return null;
            }
    }
}
