package com.attendo.data.sub

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SubjectName")
public class SubEntity(
    @ColumnInfo(name = "subject")
    private var subject: String?, @ColumnInfo(name = "present")
    private var present: Int, @ColumnInfo(name = "absent")
    private var absent: Int, @ColumnInfo(name = "total")
    private var total: Int
) {

    @PrimaryKey(autoGenerate = true)
    private var id: Int? = 0

    fun getSubject(): String? {
        return subject
    }

    fun setSubject(subject: String) {
        this.subject = subject
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getPresent(): Int {
        return present
    }

    fun setPresent(present: Int) {
        this.present = present
    }

    fun getAbsent(): Int {
        return absent
    }

    fun setAbsent(absent: Int) {
        this.absent = absent
    }

    fun getTotal(): Int {
        return total
    }

    fun setTotal(total: Int) {
        this.total = total
    }
}