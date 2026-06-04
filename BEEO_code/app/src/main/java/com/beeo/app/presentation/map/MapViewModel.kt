package com.beeo.app.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.data.repository.UserPreferencesRepository
import com.beeo.app.domain.model.DailyActivity
import com.beeo.app.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val prefsRepository: UserPreferencesRepository
) : ViewModel() {

    val recentActivities: StateFlow<List<DailyActivity>> =
        activityRepository.getRecentActivities(7)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDayIndex = MutableStateFlow(0)

    val selectedActivity: StateFlow<DailyActivity?> = combine(
        recentActivities, _selectedDayIndex
    ) { activities, index ->
        activities.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val homeLocation: StateFlow<Pair<Double, Double>?> =
        prefsRepository.userProfile
            .map { profile ->
                if (profile.isSetup && profile.homeLatitude != 0.0) {
                    Pair(profile.homeLatitude, profile.homeLongitude)
                } else null
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun selectDay(index: Int) {
        _selectedDayIndex.value = index
    }
}
