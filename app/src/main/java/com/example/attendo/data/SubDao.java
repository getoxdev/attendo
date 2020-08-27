package com.example.attendo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubDao {

    @Insert
    void insert(SubEntity SUBJECT);

    @Query("SELECT * FROM SubjectName")
    LiveData<List<SubEntity>> getAllSubjects();

    @Query("SELECT * FROM SubjectName WHERE id=:subId")
    LiveData<SubEntity> getSUBJECT(String subId);

    @Update
    void update(SubEntity SUBJECT);

    @Delete
    int delete(SubEntity SUBJECT);

    @Query("UPDATE SubjectName SET present = :present+1 WHERE id = :id")
    void updatePresent(int present, int id);

    @Query("UPDATE SubjectName SET absent= :absent+1 WHERE id = :id")
    void updateAbsent(int absent, int id);

}
