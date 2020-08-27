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



    public SubEntity( @NonNull String subject, int present, int total) {

        this.subject= subject;
        this.present = present;
        this.total = total;
    }


    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }



    @ColumnInfo(name = "present")
    private int present;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @ColumnInfo(name = "total")
    private int total;
    //private int percent;


}
