package com.example.attendo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface CalendarDao {
    @Query("SELECT * FROM calendar where date=:date")
    LiveData<List<CalendarEntity>> getSubjectByDate(Date date);



}
