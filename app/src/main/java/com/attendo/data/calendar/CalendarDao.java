package com.attendo.data.calendar;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalendarDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDate(CalendarEntity calendarEntity);

    @Query("SELECT DISTINCT subject FROM calendar WHERE date=:subDate")
    LiveData<List<String>> getsub(String subDate);
}
