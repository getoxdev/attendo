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

public class CalViewModel extends AndroidViewModel
{
    private CalendarDao calendarDao;
    private SubDatabase subDB;
    private Executor mExecutor = Executors.newSingleThreadExecutor();
    public LiveData<List<String>> strings;

    public CalViewModel(@NonNull Application application)
    {
        super(application);
        subDB = SubDatabase.getInstance(getApplication());
        calendarDao = subDB.calendarDao();
    }

    public LiveData<List<String>> getSub(String subDate)
    {
        return calendarDao.getsub(subDate);
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
}
