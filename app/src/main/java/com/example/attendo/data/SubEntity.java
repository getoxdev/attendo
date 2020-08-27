package com.example.attendo.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SubjectName")
public class SubEntity {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;


    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NonNull
    @ColumnInfo(name = "subject")
    private String subject;



    public SubEntity(@NonNull String id, @NonNull String subject, int present, int absent) {

        this.subject= subject;
        this.present = present;
        this.absent = absent;
    }


    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    @ColumnInfo(name = "present")
    private int present;


    @ColumnInfo(name = "absent")
    private int absent;
    //private int percent;


}
