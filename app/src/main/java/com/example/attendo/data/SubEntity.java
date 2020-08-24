package com.example.attendo.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class SubEntity {

    @NonNull
    public String getId() {
        return id;
    }

    @PrimaryKey
    @NonNull
    private String id;


    @NonNull
    public String getNote() {
        return note;
    }

    @NonNull
    @ColumnInfo(name = "note")
    private String note;

//    public Note(@NonNull String id, @NonNull String subName) {
//        this.id = id;
//        this.subName = subName;
//    }

    public SubEntity(@NonNull String id, @NonNull String note, int present, int absent) {
        this.id = id;
        this.note= note;
        this.present = present;
        this.absent = absent;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    @ColumnInfo(name = "present")
    private int present;

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    @ColumnInfo(name = "absent")
    private int absent;
    //private int percent;

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
