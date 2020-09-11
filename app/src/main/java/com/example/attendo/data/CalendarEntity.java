package com.example.attendo.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "calendar")
public class CalendarEntity {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "subject")
    private String subject;

    public CalendarEntity( Date date, String subject) {
        this.date = date;
        this.subject = subject;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



}
