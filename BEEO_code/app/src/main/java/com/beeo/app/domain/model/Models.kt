package com.beeo.app.domain.model

import java.time.LocalDate

data class DailyActivity(
    val id: Long = 0,
    val date: LocalDate,
    val steps: Int,
    val distanceMeters: Float,
    val activeMinutes: Int,
    val locationChanges: Int,
    val homeTimeMinutes: Int,
    val maxDistanceFromHome: Float,
    val routePoints: List<RoutePoint> = emptyList(),
    val zones: List<String> = emptyList()
)

data class RoutePoint(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val accuracy: Float = 0f
)

data class HabitAlert(
    val id: Long = 0,
    val timestamp: Long,
    val type: AlertType,
    val message: String,
    val severity: AlertSeverity,
    val isRead: Boolean = false
)

enum class AlertType {
    STEPS_DECREASED,
    SEDENTARY_TOO_LONG,
    NO_OUTDOOR_ACTIVITY,
    ROUTINE_CHANGE,
    ISOLATION_WARNING,
    POSITIVE_PROGRESS
}

enum class AlertSeverity {
    INFO, WARNING, CRITICAL
}

data class WeeklyStats(
    val weekStart: LocalDate,
    val weekEnd: LocalDate,
    val avgSteps: Int,
    val avgActiveMinutes: Int,
    val avgDistanceMeters: Float,
    val totalLocationChanges: Int,
    val trend: ActivityTrend
)

enum class ActivityTrend {
    IMPROVING, STABLE, DECLINING, CRITICAL_DECLINE
}

data class UserProfile(
    val baselineSteps: Int = 8000,
    val baselineActiveMinutes: Int = 30,
    val homeLatitude: Double = 0.0,
    val homeLongitude: Double = 0.0,
    val homeRadiusMeters: Float = 100f,
    val isSetup: Boolean = false
)
