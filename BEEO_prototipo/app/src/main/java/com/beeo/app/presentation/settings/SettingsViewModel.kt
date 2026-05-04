package com.beeo.app.presentation.settings

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beeo.app.data.repository.UserPreferencesRepository
import com.beeo.app.domain.model.UserProfile
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val prefsRepository: UserPreferencesRepository
) : ViewModel() {

    val profile: StateFlow<UserProfile> =
        prefsRepository.userProfile
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    val trackingEnabled: StateFlow<Boolean> =
        prefsRepository.trackingEnabled
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val notificationsEnabled: StateFlow<Boolean> =
        prefsRepository.notificationsEnabled
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setTrackingEnabled(enabled: Boolean) {
        viewModelScope.launch { prefsRepository.setTrackingEnabled(enabled) }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch { prefsRepository.setNotificationsEnabled(enabled) }
    }

    fun setBaselineSteps(steps: Int) {
        viewModelScope.launch { prefsRepository.saveBaselineSteps(steps) }
    }

    @SuppressLint("MissingPermission")
    fun setCurrentLocationAsHome() {
        val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
        fusedLocation.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                location?.let {
                    viewModelScope.launch {
                        prefsRepository.saveHomeLocation(it.latitude, it.longitude)
                    }
                }
            }
    }
}
