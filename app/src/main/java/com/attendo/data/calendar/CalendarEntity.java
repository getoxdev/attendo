package com.attendo.data.calendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calendar")
public class CalendarEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int Id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "subject")
    private String subject;

    public CalendarEntity(String date, String subject) {
        this.date = date;
        this.subject = subject;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
