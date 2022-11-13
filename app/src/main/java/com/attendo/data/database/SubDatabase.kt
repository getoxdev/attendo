package com.attendo.data.database//package com.attendo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.attendo.data.DateConverter
import com.attendo.data.calendar.CalendarDao
import com.attendo.data.calendar.CalendarEntity
import com.attendo.data.sub.SubDao
import com.attendo.data.sub.SubEntity

@Database(entities = [SubEntity::class, CalendarEntity::class], version = 4)
@TypeConverters(DateConverter::class)


public abstract class SubDatabase: RoomDatabase() {



    abstract fun SubDao(): SubDao?
    abstract fun calendarDao(): CalendarDao?


    companion object {
        @kotlin.jvm.JvmField
        val DATABASE_NAME = "Subject.db"
        @kotlin.jvm.Volatile
        var instance: SubDatabase? = null
        private val `object` = Any()
        @JvmStatic
        open fun getInstance(context: Context): SubDatabase? {

            if (instance == null) {
                synchronized(`object`) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            SubDatabase::class.java, DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return instance
        }
    }


}