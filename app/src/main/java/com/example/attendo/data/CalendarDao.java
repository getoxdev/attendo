package com.example.attendo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface CalendarDao {

    @Query("SELECT * FROM calendar")
    LiveData<List<CalendarEntity>> getSubjectByDate();

    @Insert
    void insertDate(CalendarEntity calendarEntity);


    @Query("SELECT DISTINCT subject from calendar")
    LiveData<List<CalendarEntity>> getitem();











}
