package com.beeo.app.data.repository

import com.beeo.app.data.local.*
import com.beeo.app.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepository @Inject constructor(
    private val activityDao: DailyActivityDao,
    private val alertDao: HabitAlertDao,
    private val locationDao: LocationSampleDao
) {

    // ─── Activity ──────────────────────────────────────────────────────────────

    fun getAllActivities(): Flow<List<DailyActivity>> =
        activityDao.getAllActivities().map { list -> list.map { it.toDomain() } }

    fun getRecentActivities(limit: Int = 30): Flow<List<DailyActivity>> =
        activityDao.getRecentActivities(limit).map { list -> list.map { it.toDomain() } }

    suspend fun getActivityForToday(): DailyActivity? {
        val today = LocalDate.now().toEpochDay()
        return activityDao.getActivityByDate(today)?.toDomain()
    }

    suspend fun getLatestActivity(): DailyActivity? =
        activityDao.getLatestActivity()?.toDomain()

    suspend fun saveActivity(activity: DailyActivity) {
        activityDao.insertActivity(activity.toEntity())
    }

    suspend fun updateTodayActivity(activity: DailyActivity) {
        val existing = activityDao.getActivityByDate(activity.date.toEpochDay())
        if (existing != null) {
            activityDao.updateActivity(activity.toEntity().copy(id = existing.id))
        } else {
            activityDao.insertActivity(activity.toEntity())
        }
    }

    fun getActivitiesInRange(start: LocalDate, end: LocalDate): Flow<List<DailyActivity>> =
        activityDao.getActivitiesInRange(start.toEpochDay(), end.toEpochDay())
            .map { list -> list.map { it.toDomain() } }

    suspend fun getAverageSteps(daysBack: Int = 7): Float {
        val end = LocalDate.now().toEpochDay()
        val start = LocalDate.now().minusDays(daysBack.toLong()).toEpochDay()
        return activityDao.getAverageSteps(start, end) ?: 0f
    }

    suspend fun getDataCount(): Int = activityDao.getCount()

    // ─── Alerts ────────────────────────────────────────────────────────────────

    fun getAllAlerts(): Flow<List<HabitAlert>> =
        alertDao.getAllAlerts().map { list -> list.map { it.toDomain() } }

    fun getUnreadAlerts(): Flow<List<HabitAlert>> =
        alertDao.getUnreadAlerts().map { list -> list.map { it.toDomain() } }

    fun getUnreadCount(): Flow<Int> = alertDao.getUnreadCount()

    suspend fun saveAlert(alert: HabitAlert) {
        alertDao.insertAlert(alert.toEntity())
    }

    suspend fun markAlertAsRead(id: Long) = alertDao.markAsRead(id)

    suspend fun markAllAlertsRead() = alertDao.markAllAsRead()

    // ─── Location Samples ──────────────────────────────────────────────────────

    suspend fun saveLocationSample(lat: Double, lng: Double, timestamp: Long, accuracy: Float) {
        val epochDay = LocalDate.now().toEpochDay()
        locationDao.insertSample(
            LocationSampleEntity(
                latitude = lat,
                longitude = lng,
                timestamp = timestamp,
                accuracy = accuracy,
                dateEpochDay = epochDay
            )
        )
    }

    suspend fun getLocationSamplesForDate(date: LocalDate): List<RoutePoint> {
        return locationDao.getSamplesForDay(date.toEpochDay()).map {
            RoutePoint(it.latitude, it.longitude, it.timestamp, it.accuracy)
        }
    }

    suspend fun cleanOldData(keepDays: Int = 90) {
        val cutoff = LocalDate.now().minusDays(keepDays.toLong()).toEpochDay()
        locationDao.deleteOldSamples(cutoff)
    }

    // ─── Mappers ───────────────────────────────────────────────────────────────

    private fun DailyActivityEntity.toDomain() = DailyActivity(
        id = id,
        date = LocalDate.ofEpochDay(dateEpochDay),
        steps = steps,
        distanceMeters = distanceMeters,
        activeMinutes = activeMinutes,
        locationChanges = locationChanges,
        homeTimeMinutes = homeTimeMinutes,
        maxDistanceFromHome = maxDistanceFromHome,
        routePoints = routePoints,
        zones = zones
    )

    private fun DailyActivity.toEntity() = DailyActivityEntity(
        id = id,
        dateEpochDay = date.toEpochDay(),
        steps = steps,
        distanceMeters = distanceMeters,
        activeMinutes = activeMinutes,
        locationChanges = locationChanges,
        homeTimeMinutes = homeTimeMinutes,
        maxDistanceFromHome = maxDistanceFromHome,
        routePoints = routePoints,
        zones = zones
    )

    private fun HabitAlertEntity.toDomain() = HabitAlert(
        id = id,
        timestamp = timestamp,
        type = AlertType.valueOf(type),
        message = message,
        severity = AlertSeverity.valueOf(severity),
        isRead = isRead
    )

    private fun HabitAlert.toEntity() = HabitAlertEntity(
        id = id,
        timestamp = timestamp,
        type = type.name,
        message = message,
        severity = severity.name,
        isRead = isRead
    )
}
