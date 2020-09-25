package com.attendo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SubDao {

    @Insert
    void insert(SubEntity SUBJECT);

    @Query("SELECT * FROM SubjectName")
    LiveData<List<SubEntity>> getAllSubjects();

    @Delete
    int delete(SubEntity SUBJECT);

    @Query("UPDATE SubjectName SET present = :present WHERE id = :id")
    void updatePresent(int present, int id);

    @Query("UPDATE SubjectName SET absent = :absent WHERE id = :id")
    void updateAbsent(int absent, int id);


    @Query("UPDATE SubjectName SET total= :total WHERE id = :id")
    void updateTotal(int total, int id);

    @Query("UPDATE SubjectName SET subject = :subject WHERE id = :id")
    void updateSubject(String subject,int id);



}
