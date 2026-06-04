package com.beeo.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyActivityDao {

    @Query("SELECT * FROM daily_activity ORDER BY dateEpochDay DESC")
    fun getAllActivities(): Flow<List<DailyActivityEntity>>

    @Query("SELECT * FROM daily_activity WHERE dateEpochDay = :epochDay LIMIT 1")
    suspend fun getActivityByDate(epochDay: Long): DailyActivityEntity?

    @Query("SELECT * FROM daily_activity ORDER BY dateEpochDay DESC LIMIT :limit")
    fun getRecentActivities(limit: Int): Flow<List<DailyActivityEntity>>

    @Query("SELECT * FROM daily_activity WHERE dateEpochDay BETWEEN :startDay AND :endDay ORDER BY dateEpochDay ASC")
    fun getActivitiesInRange(startDay: Long, endDay: Long): Flow<List<DailyActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: DailyActivityEntity)

    @Update
    suspend fun updateActivity(activity: DailyActivityEntity)

    @Query("SELECT * FROM daily_activity ORDER BY dateEpochDay DESC LIMIT 1")
    suspend fun getLatestActivity(): DailyActivityEntity?

    @Query("SELECT AVG(steps) FROM daily_activity WHERE dateEpochDay BETWEEN :startDay AND :endDay")
    suspend fun getAverageSteps(startDay: Long, endDay: Long): Float?

    @Query("SELECT COUNT(*) FROM daily_activity")
    suspend fun getCount(): Int
}

@Dao
interface HabitAlertDao {

    @Query("SELECT * FROM habit_alert ORDER BY timestamp DESC")
    fun getAllAlerts(): Flow<List<HabitAlertEntity>>

    @Query("SELECT * FROM habit_alert WHERE isRead = 0 ORDER BY timestamp DESC")
    fun getUnreadAlerts(): Flow<List<HabitAlertEntity>>

    @Insert
    suspend fun insertAlert(alert: HabitAlertEntity): Long

    @Query("UPDATE habit_alert SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE habit_alert SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("SELECT COUNT(*) FROM habit_alert WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>
}

@Dao
interface LocationSampleDao {

    @Insert
    suspend fun insertSample(sample: LocationSampleEntity)

    @Query("SELECT * FROM location_sample WHERE dateEpochDay = :epochDay ORDER BY timestamp ASC")
    suspend fun getSamplesForDay(epochDay: Long): List<LocationSampleEntity>

    @Query("SELECT * FROM location_sample WHERE dateEpochDay BETWEEN :startDay AND :endDay ORDER BY timestamp ASC")
    suspend fun getSamplesInRange(startDay: Long, endDay: Long): List<LocationSampleEntity>

    @Query("DELETE FROM location_sample WHERE dateEpochDay < :cutoffDay")
    suspend fun deleteOldSamples(cutoffDay: Long)

    @Query("SELECT COUNT(*) FROM location_sample WHERE dateEpochDay = :epochDay")
    suspend fun countSamplesForDay(epochDay: Long): Int
}
