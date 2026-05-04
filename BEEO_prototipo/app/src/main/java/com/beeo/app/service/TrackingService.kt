package com.beeo.app.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.beeo.app.BEEOApplication
import com.beeo.app.R
import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.data.repository.UserPreferencesRepository
import com.beeo.app.domain.model.DailyActivity
import com.beeo.app.domain.model.RoutePoint
import com.beeo.app.domain.usecase.AnalyzeHabitsUseCase
import com.beeo.app.presentation.main.MainActivity
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.sqrt

@AndroidEntryPoint
class TrackingService : LifecycleService(), SensorEventListener {

    @Inject lateinit var activityRepository: ActivityRepository
    @Inject lateinit var prefsRepository: UserPreferencesRepository
    @Inject lateinit var analyzeHabitsUseCase: AnalyzeHabitsUseCase

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sensorManager: SensorManager

    private var stepCounterSensor: Sensor? = null
    private var stepDetectorSensor: Sensor? = null
    private var initialStepCount = -1
    private var currentSteps = 0
    private var sessionSteps = 0

    private var currentLocation: Location? = null
    private var homeLocation: Location? = null
    private var locationChanges = 0
    private var lastSignificantLocation: Location? = null
    private var maxDistanceFromHome = 0f
    private var homeTimeMinutes = 0
    private var activeMinutes = 0
    private val routePoints = mutableListOf<RoutePoint>()
    private var serviceStartTime = System.currentTimeMillis()
    private var lastLocationTime = System.currentTimeMillis()
    private var lastActivitySave = System.currentTimeMillis()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { processLocation(it) }
        }
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        startForeground(NOTIFICATION_ID, buildForegroundNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        lifecycleScope.launch {
            val profile = prefsRepository.userProfile.first()
            if (profile.isSetup) {
                homeLocation = Location("home").apply {
                    latitude = profile.homeLatitude
                    longitude = profile.homeLongitude
                }
            }
        }

        startLocationUpdates()
        startStepTracking()
        schedulePeriodicAnalysis()

        return START_STICKY
    }

    @Suppress("MissingPermission")
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            LOCATION_INTERVAL_MS
        ).apply {
            setMinUpdateDistanceMeters(MIN_DISPLACEMENT_METERS)
            setWaitForAccurateLocation(false)
        }.build()

        try {
            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun startStepTracking() {
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        stepDetectorSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    private fun processLocation(location: Location) {
        val now = System.currentTimeMillis()
        currentLocation = location

        // Add to route
        routePoints.add(RoutePoint(
            latitude = location.latitude,
            longitude = location.longitude,
            timestamp = now,
            accuracy = location.accuracy
        ))

        // Save to location samples DB
        lifecycleScope.launch {
            activityRepository.saveLocationSample(
                location.latitude, location.longitude, now, location.accuracy
            )
        }

        // Check distance from home
        homeLocation?.let { home ->
            val distFromHome = home.distanceTo(location)
            if (distFromHome > maxDistanceFromHome) maxDistanceFromHome = distFromHome

            // Count time at home
            val minutesSinceLast = ((now - lastLocationTime) / 60000).toInt()
            if (distFromHome < 150f) {
                homeTimeMinutes += minutesSinceLast
            } else {
                activeMinutes += minutesSinceLast
            }
        }

        // Count significant location changes
        lastSignificantLocation?.let { last ->
            val dist = last.distanceTo(location)
            if (dist > 300f) {
                locationChanges++
                lastSignificantLocation = location
            }
        } ?: run {
            lastSignificantLocation = location
        }

        lastLocationTime = now

        // Periodic save
        if (now - lastActivitySave > SAVE_INTERVAL_MS) {
            saveCurrentActivity()
            lastActivitySave = now
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        when (event.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                val totalSteps = event.values[0].toInt()
                if (initialStepCount == -1) {
                    initialStepCount = totalSteps
                }
                sessionSteps = totalSteps - initialStepCount
                currentSteps = sessionSteps
            }
            Sensor.TYPE_STEP_DETECTOR -> {
                if (stepCounterSensor == null) currentSteps++
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun saveCurrentActivity() {
        lifecycleScope.launch {
            val today = LocalDate.now()
            val existing = activityRepository.getActivityForToday()

            val activity = DailyActivity(
                id = existing?.id ?: 0,
                date = today,
                steps = maxOf(currentSteps, existing?.steps ?: 0),
                distanceMeters = calculateDistance(),
                activeMinutes = activeMinutes,
                locationChanges = locationChanges,
                homeTimeMinutes = homeTimeMinutes,
                maxDistanceFromHome = maxDistanceFromHome,
                routePoints = routePoints.takeLast(MAX_ROUTE_POINTS),
                zones = emptyList()
            )

            activityRepository.updateTodayActivity(activity)

            // Analyze habits periodically
            val alerts = analyzeHabitsUseCase()
            alerts.forEach { alert ->
                activityRepository.saveAlert(alert)
                sendAlertNotification(alert)
            }

            // Update foreground notification
            updateForegroundNotification()
        }
    }

    private fun calculateDistance(): Float {
        if (routePoints.size < 2) return 0f
        var total = 0f
        for (i in 1 until routePoints.size) {
            val a = routePoints[i - 1]
            val b = routePoints[i]
            val results = FloatArray(1)
            Location.distanceBetween(a.latitude, a.longitude, b.latitude, b.longitude, results)
            total += results[0]
        }
        return total
    }

    private fun sendAlertNotification(alert: com.beeo.app.domain.model.HabitAlert) {
        lifecycleScope.launch {
            val notificationsEnabled = prefsRepository.notificationsEnabled.first()
            if (!notificationsEnabled) return@launch
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "alerts")
        }
        val pendingIntent = PendingIntent.getActivity(
            this, alert.id.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, BEEOApplication.CHANNEL_ALERTS)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("BEEO - Alerta de hábito")
            .setContentText(alert.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(ALERT_NOTIFICATION_ID + alert.id.toInt(), notification)
    }

    private fun updateForegroundNotification() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, buildForegroundNotification())
    }

    private fun buildForegroundNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, BEEOApplication.CHANNEL_TRACKING)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("BEEO está activo")
            .setContentText("Pasos hoy: $currentSteps · Activo: ${activeMinutes}min")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun schedulePeriodicAnalysis() {
        // Analysis happens every time we save (every SAVE_INTERVAL_MS)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        sensorManager.unregisterListener(this)
        saveCurrentActivity()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val ALERT_NOTIFICATION_ID = 2000
        const val LOCATION_INTERVAL_MS = 60_000L   // 1 minute
        const val MIN_DISPLACEMENT_METERS = 20f
        const val SAVE_INTERVAL_MS = 15 * 60_000L  // 15 minutes
        const val MAX_ROUTE_POINTS = 500

        fun start(context: Context) {
            val intent = Intent(context, TrackingService::class.java)
            context.startForegroundService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, TrackingService::class.java)
            context.stopService(intent)
        }
    }
}
