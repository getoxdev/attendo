package com.example.attendo.data;

import android.icu.text.Replaceable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface CalendarDao {

    @Query("SELECT * FROM calendar where date=:date")
    LiveData<List<CalendarEntity>> getSubjectByDate(Date date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDate(CalendarEntity calendarEntity);


    @Query("SELECT DISTINCT subject from calendar ORDER BY subject")
    LiveData<List<String>> getitem();

    @Query("SELECT DISTINCT subject FROM calendar")
    List<String> getsub();














}
