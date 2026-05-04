package com.beeo.app.data.local

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.beeo.app.domain.model.RoutePoint

// ─── Type Converters ───────────────────────────────────────────────────────────

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromRoutePoints(value: List<RoutePoint>?): String {
        return gson.toJson(value ?: emptyList<RoutePoint>())
    }

    @TypeConverter
    fun toRoutePoints(value: String): List<RoutePoint> {
        val type = object : TypeToken<List<RoutePoint>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value ?: emptyList<String>())
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}

// ─── Entities ──────────────────────────────────────────────────────────────────

@Entity(tableName = "daily_activity")
data class DailyActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateEpochDay: Long,       // LocalDate.toEpochDay()
    val steps: Int,
    val distanceMeters: Float,
    val activeMinutes: Int,
    val locationChanges: Int,
    val homeTimeMinutes: Int,
    val maxDistanceFromHome: Float,
    val routePoints: List<RoutePoint>,
    val zones: List<String>
)

@Entity(tableName = "habit_alert")
data class HabitAlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val type: String,
    val message: String,
    val severity: String,
    val isRead: Boolean = false
)

@Entity(tableName = "location_sample")
data class LocationSampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val accuracy: Float,
    val dateEpochDay: Long
)
