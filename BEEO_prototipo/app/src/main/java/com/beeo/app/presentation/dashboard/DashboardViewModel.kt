package com.beeo.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.data.repository.UserPreferencesRepository
import com.beeo.app.domain.model.*
import com.beeo.app.domain.usecase.AnalyzeHabitsUseCase
import com.beeo.app.domain.usecase.GetWeeklyStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val prefsRepository: UserPreferencesRepository,
    private val analyzeHabitsUseCase: AnalyzeHabitsUseCase,
    private val getWeeklyStatsUseCase: GetWeeklyStatsUseCase
) : ViewModel() {

    val recentActivities: StateFlow<List<DailyActivity>> =
        activityRepository.getRecentActivities(14)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayActivity: StateFlow<DailyActivity?> = recentActivities.map { list ->
        list.firstOrNull { it.date == LocalDate.now() }
            ?: generateDemoActivityIfEmpty(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _weeklyStats = MutableStateFlow<List<WeeklyStats>>(emptyList())
    val weeklyStats: StateFlow<List<WeeklyStats>> = _weeklyStats

    val userProfile: StateFlow<UserProfile> =
        prefsRepository.userProfile
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    init {
        loadWeeklyStats()
        seedDemoDataIfEmpty()
    }

    private fun loadWeeklyStats() {
        viewModelScope.launch {
            _weeklyStats.value = getWeeklyStatsUseCase()
        }
    }

    // Seed demo data so app has something to show right away
    private fun seedDemoDataIfEmpty() {
        viewModelScope.launch {
            val count = activityRepository.getDataCount()
            if (count == 0) {
                val today = LocalDate.now()
                val demoData = listOf(
                    DailyActivity(date = today, steps = 3200, distanceMeters = 2400f, activeMinutes = 28, locationChanges = 2, homeTimeMinutes = 780, maxDistanceFromHome = 1200f),
                    DailyActivity(date = today.minusDays(1), steps = 4100, distanceMeters = 3100f, activeMinutes = 35, locationChanges = 3, homeTimeMinutes = 720, maxDistanceFromHome = 1800f),
                    DailyActivity(date = today.minusDays(2), steps = 5500, distanceMeters = 4100f, activeMinutes = 48, locationChanges = 4, homeTimeMinutes = 660, maxDistanceFromHome = 2300f),
                    DailyActivity(date = today.minusDays(3), steps = 6200, distanceMeters = 4700f, activeMinutes = 55, locationChanges = 5, homeTimeMinutes = 640, maxDistanceFromHome = 2800f),
                    DailyActivity(date = today.minusDays(4), steps = 7800, distanceMeters = 5900f, activeMinutes = 68, locationChanges = 6, homeTimeMinutes = 580, maxDistanceFromHome = 3200f),
                    DailyActivity(date = today.minusDays(5), steps = 9200, distanceMeters = 6900f, activeMinutes = 78, locationChanges = 7, homeTimeMinutes = 520, maxDistanceFromHome = 4100f),
                    DailyActivity(date = today.minusDays(6), steps = 10500, distanceMeters = 7900f, activeMinutes = 92, locationChanges = 8, homeTimeMinutes = 460, maxDistanceFromHome = 4800f),
                    DailyActivity(date = today.minusDays(7), steps = 11200, distanceMeters = 8400f, activeMinutes = 98, locationChanges = 9, homeTimeMinutes = 440, maxDistanceFromHome = 5200f),
                    DailyActivity(date = today.minusDays(8), steps = 10800, distanceMeters = 8100f, activeMinutes = 95, locationChanges = 8, homeTimeMinutes = 460, maxDistanceFromHome = 5000f),
                    DailyActivity(date = today.minusDays(9), steps = 9600, distanceMeters = 7200f, activeMinutes = 84, locationChanges = 7, homeTimeMinutes = 500, maxDistanceFromHome = 4500f),
                    DailyActivity(date = today.minusDays(10), steps = 8900, distanceMeters = 6700f, activeMinutes = 76, locationChanges = 7, homeTimeMinutes = 530, maxDistanceFromHome = 4200f),
                    DailyActivity(date = today.minusDays(11), steps = 10100, distanceMeters = 7600f, activeMinutes = 88, locationChanges = 8, homeTimeMinutes = 470, maxDistanceFromHome = 4700f),
                    DailyActivity(date = today.minusDays(12), steps = 11500, distanceMeters = 8600f, activeMinutes = 101, locationChanges = 9, homeTimeMinutes = 430, maxDistanceFromHome = 5300f),
                    DailyActivity(date = today.minusDays(13), steps = 12000, distanceMeters = 9000f, activeMinutes = 105, locationChanges = 10, homeTimeMinutes = 410, maxDistanceFromHome = 5600f)
                )
                demoData.forEach { activityRepository.saveActivity(it) }
                loadWeeklyStats()
            }
        }
    }

    private fun generateDemoActivityIfEmpty(list: List<DailyActivity>): DailyActivity? {
        return list.firstOrNull()
    }
}
