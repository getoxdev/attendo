package com.attendo.data.rem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
public class RemEntity {
    public RemEntity(String time, String label) {
        this.time = time;
        this.label = label;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "label")
    private String label;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getLabel() {
        return label;
    }

    public void setId(int id) {
        this.id = id;
    }
}
