package com.attendo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.attendo.data.calendar.CalendarDao;
import com.attendo.data.calendar.CalendarEntity;
import com.attendo.data.database.SubDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CalViewModel extends AndroidViewModel
{
    private CalendarDao calendarDao;
    private SubDatabase subDB;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

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
jj
