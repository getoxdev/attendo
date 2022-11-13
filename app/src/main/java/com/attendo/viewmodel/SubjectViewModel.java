package com.attendo.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.attendo.data.database.SubDatabase;
import com.attendo.data.sub.SubDao;
import com.attendo.data.sub.SubEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SubjectViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private SubDao subDao;

    private SubDatabase subDB;
    public LiveData<List<SubEntity>> mAllSubjects;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public SubjectViewModel(Application application) {
        super(application);

        subDB = SubDatabase.getInstance(getApplication());
        subDao = subDB.SubDao();

        mAllSubjects = subDao.getAllSubjects();
    }

    public void insertSubject(SubEntity subject)
    {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                subDB.SubDao().insert(subject);
            }
        });
    }


    public void deleteSubject(SubEntity subEntity)
    {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                subDB.SubDao().delete(subEntity);
            }
        });

    }

    public LiveData<List<SubEntity>> getAllSubjects() {
        return mAllSubjects;
    }


    public void updatePresent( int p, int id )
    {
       mExecutor.execute(new Runnable() {
           @Override
           public void run() {
               subDao.updatePresent(p,id);
           }
       });
    }

    public void updateAbsent( int a,  int id)
    {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                subDao.updateAbsent(a,id);
            }
        });
    }

    public void updateTotal( int tot,  int id)
    {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                subDao.updateTotal(tot,id);
            }
        });
    }
    public void updateSubject(String sub,int id)
    {
        mExecutor.execute((new Runnable() {
            @Override
            public void run() {
                subDao.updateSubject(sub,id);
            }
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }
}

