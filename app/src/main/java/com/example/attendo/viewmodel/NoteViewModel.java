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

public class NoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private SubDao noteDao;
    private SubDatabase noteDB;
    private LiveData<List<SubEntity>> mAllNotes;
    private SubEntity note;

    public NoteViewModel(Application application) {
        super(application);

        noteDB = SubDatabase.getDatabase(application);
        noteDao = noteDB.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    public void insert(SubEntity note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    public LiveData<List<SubEntity>> getAllNotes() {
        return mAllNotes;
    }

    public void update(SubEntity note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(SubEntity note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }


//    public void updatepresent(int p,String sub )
//    {
//        p= note.getPresent();
//        sub = note.getNote();
//        noteDao.updatePresent(p,sub);
//    }
//
//    public void updateAbsent(int a, String sub)
//    {
//        a = note.getAbsent();
//        sub = note.getNote();
//        noteDao.updateAbsent(a,sub);
//    }
//
//


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
        protected Void doInBackground(SubEntity... notes) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(SubDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(SubEntity... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(SubDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(SubEntity... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(SubDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(SubEntity... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }
}

