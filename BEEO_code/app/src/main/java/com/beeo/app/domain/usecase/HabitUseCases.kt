package com.beeo.app.domain.usecase

import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.data.repository.UserPreferencesRepository
import com.beeo.app.domain.model.*
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class AnalyzeHabitsUseCase @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val prefsRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(): List<HabitAlert> {
        val alerts = mutableListOf<HabitAlert>()
        val profile = prefsRepository.userProfile.first()
        val today = LocalDate.now()

        // Get last 14 days of data
        val recentActivities = activityRepository
            .getRecentActivities(14).first()

        if (recentActivities.size < 3) return emptyList()

        val todayActivity = recentActivities.firstOrNull { it.date == today }
        val last7Days = recentActivities.filter {
            !it.date.isBefore(today.minusDays(7))
        }
        val prev7Days = recentActivities.filter {
            it.date.isBefore(today.minusDays(7)) &&
            !it.date.isBefore(today.minusDays(14))
        }

        // ── Check for step decrease ──────────────────────────────────────────
        if (last7Days.size >= 3 && prev7Days.size >= 3) {
            val currentAvgSteps = last7Days.map { it.steps }.average()
            val prevAvgSteps = prev7Days.map { it.steps }.average()
            val baselineSteps = profile.baselineSteps.toDouble()

            val dropPercent = if (prevAvgSteps > 0) {
                ((prevAvgSteps - currentAvgSteps) / prevAvgSteps) * 100
            } else 0.0

            when {
                dropPercent >= 50 -> alerts.add(
                    HabitAlert(
                        timestamp = System.currentTimeMillis(),
                        type = AlertType.STEPS_DECREASED,
                        message = "⚠️ Tus pasos han bajado un ${dropPercent.toInt()}% esta semana. " +
                                  "Antes caminabas ~${prevAvgSteps.toInt()} pasos y ahora ~${currentAvgSteps.toInt()}.",
                        severity = AlertSeverity.CRITICAL
                    )
                )
                dropPercent >= 25 -> alerts.add(
                    HabitAlert(
                        timestamp = System.currentTimeMillis(),
                        type = AlertType.STEPS_DECREASED,
                        message = "📉 Tus pasos han reducido un ${dropPercent.toInt()}% vs la semana pasada.",
                        severity = AlertSeverity.WARNING
                    )
                )
            }

            // Check against personal baseline
            if (currentAvgSteps < baselineSteps * 0.5) {
                alerts.add(
                    HabitAlert(
                        timestamp = System.currentTimeMillis(),
                        type = AlertType.ROUTINE_CHANGE,
                        message = "🏠 Tu actividad está muy por debajo de tu nivel habitual de ${profile.baselineSteps} pasos.",
                        severity = AlertSeverity.WARNING
                    )
                )
            }
        }

        // ── Check for prolonged sedentary time ──────────────────────────────
        todayActivity?.let { activity ->
            if (activity.homeTimeMinutes > 600) { // more than 10 hours at home
                alerts.add(
                    HabitAlert(
                        timestamp = System.currentTimeMillis(),
                        type = AlertType.SEDENTARY_TOO_LONG,
                        message = "🛋️ Llevas más de ${activity.homeTimeMinutes / 60} horas sin salir de casa hoy.",
                        severity = if (activity.homeTimeMinutes > 900) AlertSeverity.CRITICAL else AlertSeverity.WARNING
                    )
                )
            }
        }

        // ── Check for outdoor activity absence ──────────────────────────────
        val consecutiveDaysHome = recentActivities
            .sortedByDescending { it.date }
            .takeWhile { it.locationChanges <= 1 && it.maxDistanceFromHome < 200f }
            .size

        if (consecutiveDaysHome >= 3) {
            alerts.add(
                HabitAlert(
                    timestamp = System.currentTimeMillis(),
                    type = AlertType.NO_OUTDOOR_ACTIVITY,
                    message = "🌿 Llevas $consecutiveDaysHome días sin salir de tu zona habitual. ¡Un paseo puede hacerte bien!",
                    severity = AlertSeverity.WARNING
                )
            )
        }

        // ── Isolation warning ────────────────────────────────────────────────
        if (consecutiveDaysHome >= 7) {
            alerts.add(
                HabitAlert(
                    timestamp = System.currentTimeMillis(),
                    type = AlertType.ISOLATION_WARNING,
                    message = "💙 Una semana sin salir. Tu bienestar importa — considera comunicarte con alguien cercano.",
                    severity = AlertSeverity.CRITICAL
                )
            )
        }

        // ── Positive: steps improving ────────────────────────────────────────
        if (last7Days.size >= 3 && prev7Days.size >= 3) {
            val currentAvg = last7Days.map { it.steps }.average()
            val prevAvg = prev7Days.map { it.steps }.average()
            val improvePct = if (prevAvg > 0) ((currentAvg - prevAvg) / prevAvg) * 100 else 0.0
            if (improvePct >= 20) {
                alerts.add(
                    HabitAlert(
                        timestamp = System.currentTimeMillis(),
                        type = AlertType.POSITIVE_PROGRESS,
                        message = "🎉 ¡Excelente! Tus pasos aumentaron un ${improvePct.toInt()}% esta semana. ¡Sigue así!",
                        severity = AlertSeverity.INFO
                    )
                )
            }
        }

        return alerts
    }
}

class GetWeeklyStatsUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(): List<WeeklyStats> {
        val activities = activityRepository.getRecentActivities(28).first()
        if (activities.isEmpty()) return emptyList()

        val grouped = activities.groupBy { activity ->
            val dayOfWeek = activity.date.dayOfWeek.value
            activity.date.minusDays((dayOfWeek - 1).toLong())
        }

        return grouped.entries
            .sortedByDescending { it.key }
            .take(4)
            .map { (weekStart, days) ->
                val avgSteps = days.map { it.steps }.average().toInt()
                val avgActive = days.map { it.activeMinutes }.average().toInt()
                val avgDist = days.map { it.distanceMeters }.average().toFloat()

                WeeklyStats(
                    weekStart = weekStart,
                    weekEnd = weekStart.plusDays(6),
                    avgSteps = avgSteps,
                    avgActiveMinutes = avgActive,
                    avgDistanceMeters = avgDist,
                    totalLocationChanges = days.sumOf { it.locationChanges },
                    trend = calculateTrend(days.map { it.steps })
                )
            }
    }

    private fun calculateTrend(steps: List<Int>): ActivityTrend {
        if (steps.size < 2) return ActivityTrend.STABLE
        val first = steps.take(steps.size / 2).average()
        val second = steps.takeLast(steps.size / 2).average()
        val change = if (first > 0) ((second - first) / first) * 100 else 0.0
        return when {
            change >= 15 -> ActivityTrend.IMPROVING
            change <= -40 -> ActivityTrend.CRITICAL_DECLINE
            change <= -15 -> ActivityTrend.DECLINING
            else -> ActivityTrend.STABLE
        }
    }
}
