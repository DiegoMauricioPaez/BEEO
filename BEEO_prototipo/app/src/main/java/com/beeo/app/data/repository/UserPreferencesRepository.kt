package com.beeo.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.beeo.app.domain.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "beeo_prefs")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val BASELINE_STEPS = intPreferencesKey("baseline_steps")
        val BASELINE_ACTIVE_MINUTES = intPreferencesKey("baseline_active_minutes")
        val HOME_LAT = doublePreferencesKey("home_lat")
        val HOME_LNG = doublePreferencesKey("home_lng")
        val HOME_RADIUS = floatPreferencesKey("home_radius")
        val IS_SETUP = booleanPreferencesKey("is_setup")
        val TRACKING_ENABLED = booleanPreferencesKey("tracking_enabled")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val LAST_ANALYSIS_TIMESTAMP = longPreferencesKey("last_analysis_timestamp")
    }

    val userProfile: Flow<UserProfile> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { prefs ->
            UserProfile(
                baselineSteps = prefs[Keys.BASELINE_STEPS] ?: 8000,
                baselineActiveMinutes = prefs[Keys.BASELINE_ACTIVE_MINUTES] ?: 30,
                homeLatitude = prefs[Keys.HOME_LAT] ?: 0.0,
                homeLongitude = prefs[Keys.HOME_LNG] ?: 0.0,
                homeRadiusMeters = prefs[Keys.HOME_RADIUS] ?: 100f,
                isSetup = prefs[Keys.IS_SETUP] ?: false
            )
        }

    val trackingEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[Keys.TRACKING_ENABLED] ?: true }

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[Keys.NOTIFICATIONS_ENABLED] ?: true }

    suspend fun saveHomeLocation(lat: Double, lng: Double) {
        context.dataStore.edit { prefs ->
            prefs[Keys.HOME_LAT] = lat
            prefs[Keys.HOME_LNG] = lng
            prefs[Keys.IS_SETUP] = true
        }
    }

    suspend fun saveBaselineSteps(steps: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.BASELINE_STEPS] = steps
        }
    }

    suspend fun setTrackingEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TRACKING_ENABLED] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun updateLastAnalysisTimestamp() {
        context.dataStore.edit { prefs ->
            prefs[Keys.LAST_ANALYSIS_TIMESTAMP] = System.currentTimeMillis()
        }
    }

    suspend fun markSetupComplete(baselineSteps: Int, homeLatitude: Double, homeLongitude: Double) {
        context.dataStore.edit { prefs ->
            prefs[Keys.BASELINE_STEPS] = baselineSteps
            prefs[Keys.HOME_LAT] = homeLatitude
            prefs[Keys.HOME_LNG] = homeLongitude
            prefs[Keys.IS_SETUP] = true
        }
    }
}
