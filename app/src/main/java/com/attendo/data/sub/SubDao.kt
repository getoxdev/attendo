package com.attendo.data.sub

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao

interface SubDao {

    @Insert
    fun insert(SUBJECT: SubEntity?)

    @Query("SELECT * FROM SubjectName")
    fun getAllSubjects(): LiveData<List<SubEntity?>?>?

    @Delete
    fun delete(SUBJECT: SubEntity?): Int

    @Query("UPDATE SubjectName SET present = :present WHERE id = :id")
    fun updatePresent(present: Int, id: Int)

    @Query("UPDATE SubjectName SET absent = :absent WHERE id = :id")
    fun updateAbsent(absent: Int, id: Int)


    @Query("UPDATE SubjectName SET total= :total WHERE id = :id")
    fun updateTotal(total: Int, id: Int)

    @Query("UPDATE SubjectName SET subject = :subject WHERE id = :id")
    fun updateSubject(subject: String?, id: Int)


}