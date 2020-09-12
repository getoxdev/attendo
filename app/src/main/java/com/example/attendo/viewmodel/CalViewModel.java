package com.example.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.attendo.data.CalendarDao;
import com.example.attendo.data.CalendarEntity;
import com.example.attendo.data.database.SubDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CalViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private CalendarDao calendarDao;
    private SubDatabase subDB;
    private Executor mExecutor = Executors.newSingleThreadExecutor();


    public CalViewModel(@NonNull Application application) {
        super(application);
        subDB = SubDatabase.getInstance(getApplication());
        calendarDao = subDB.calendarDao();
    }

    public LiveData<List<CalendarEntity>> getSubjectbyDate(Date date){
        return calendarDao.getSubjectByDate(date);
    }

    public LiveData<List<String>> getitem()
    {
        return calendarDao.getitem();



    }

    public List<String> getSub() {
         List<String> strings = new  ArrayList<String>();

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<String> subject = calendarDao.getsub();
                strings.addAll(subject);

                Log.e("info78", String.valueOf(strings));

            }
        });
        Log.e("info", String.valueOf(strings));
        return strings;



    }





    public void insertDate(CalendarEntity calendarEntity)
    {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                calendarDao.insertDate(calendarEntity);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

}