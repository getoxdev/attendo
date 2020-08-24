package com.example.attendo.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.attendo.data.SubDao;
import com.example.attendo.data.SubEntity;
import com.example.attendo.data.database.SubDatabase;

public class EditSubjectViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private SubDao SubDao;
    private SubDatabase db;

    public EditSubjectViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = SubDatabase.getDatabase(application);
        SubDao = db.SubDao();
    }

    public LiveData<SubEntity> getNote(String subId) {
        return SubDao.getSUBJECT(subId);
    }
}
