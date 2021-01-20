package com.attendo.data.rem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface RemDao {

    @Insert
    void insert(RemEntity remEntity);

    @Update
    void update(RemEntity remEntity);

    @Delete
    void delete(RemEntity remEntity);

    @Query("SELECT * FROM reminder_table ")
    LiveData<List<RemEntity>> getAllReminder();
}
