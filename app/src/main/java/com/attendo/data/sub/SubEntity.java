package com.attendo.data.sub;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SubjectName")
public class SubEntity
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo(name = "subject")
    private String subject;

    @ColumnInfo(name = "present")
    private int present;

    @ColumnInfo(name="absent")
    private int absent;

    @ColumnInfo(name = "total")
    private int total;

    public SubEntity( @NonNull String subject, int present,int absent, int total) {
        this.subject= subject;
        this.present = present;
        this.total = total;
        this.absent = absent;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
