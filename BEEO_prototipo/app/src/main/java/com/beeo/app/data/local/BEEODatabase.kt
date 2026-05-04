package com.beeo.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        DailyActivityEntity::class,
        HabitAlertEntity::class,
        LocationSampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BEEODatabase : RoomDatabase() {
    abstract fun dailyActivityDao(): DailyActivityDao
    abstract fun habitAlertDao(): HabitAlertDao
    abstract fun locationSampleDao(): LocationSampleDao
}
