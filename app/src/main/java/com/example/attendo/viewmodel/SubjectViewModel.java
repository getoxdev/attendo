package com.example.attendo.viewmodel;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.attendo.data.SubDao;
import com.example.attendo.data.SubEntity;
import com.example.attendo.data.database.SubDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubjectViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private SubDao subDao;
    private SubDatabase subDB;
    private LiveData<List<SubEntity>> mAllSubjects;
    private SubEntity subEntity;
    private Executor mExecutor = Executors.newSingleThreadExecutor();



    public SubjectViewModel(Application application) {
        super(application);

        subDB = SubDatabase.getDatabase(application);
        subDao = subDB.SubDao();
        mAllSubjects = subDao.getAllSubjects();
    }

    public void insert(SubEntity SUBJECT) {
        new InsertAsyncTask(subDao).execute(SUBJECT);
    }

    public LiveData<List<SubEntity>> getAllSubjects() {
        return mAllSubjects;
    }

    public void update(SubEntity SUBJECT) {
        new UpdateAsyncTask(subDao).execute(SUBJECT);
    }

    public void delete(SubEntity SUBJECT) {
        new DeleteAsyncTask(subDao).execute(SUBJECT);
    }


    public void updatePresent(int p,String sub )
    {
       mExecutor.execute(new Runnable() {
           @Override
           public void run() {
//               int p = subEntity.getPresent();
//               String sub = subEntity.getSubject();
//               int p = 1;
//               String sub = "default";
               subDao.updatePresent(p,sub);

           }
       });
    }

    public void updateAbsent( int a, String sub)
    {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                int a = subEntity.getAbsent();
//                String sub = subEntity.getSubject();
//                int a= 1;
//                String sub = "default";
                subDao.updateAbsent(a,sub);
            }
        });

    }




    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class OperationsAsyncTask extends AsyncTask<SubEntity, Void, Void> {

        SubDao mAsyncTaskDao;

        OperationsAsyncTask(SubDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SubEntity... subjects) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(SubDao msubDao) {
            super(msubDao);
        }

        @Override
        protected Void doInBackground(SubEntity... subjects) {
            mAsyncTaskDao.insert(subjects[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(SubDao subDao) {
            super(subDao);
        }

        @Override
        protected Void doInBackground(SubEntity... subjects) {
            mAsyncTaskDao.update(subjects[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(SubDao subDao) {
            super(subDao);
        }

        @Override
        protected Void doInBackground(SubEntity... subjects) {
            mAsyncTaskDao.delete(subjects[0]);
            return null;
        }
    }
}

