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
    void insert(SubEntity note);

    @Query("SELECT * FROM notes")
    LiveData<List<SubEntity>> getAllNotes();

    @Query("SELECT * FROM notes WHERE id=:noteId")
    LiveData<SubEntity> getNote(String noteId);

    @Update
    void update(SubEntity note);

    @Delete
    int delete(SubEntity note);

    @Query("UPDATE notes SET present = :present+1 WHERE note = :subject")
    void updatePresent(int present, String subject);

    @Query("UPDATE notes SET absent= :absent+1 WHERE note = :subject")
    void updateAbsent(int absent, String subject);


}
