package com.beeo.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration as OsmConfig
import java.io.File
import javax.inject.Inject

@HiltAndroidApp
class BEEOApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // Configure OSMDroid
        OsmConfig.getInstance().apply {
            userAgentValue = packageName
            osmdroidTileCache = File(cacheDir, "osmdroid")
            load(this@BEEOApplication, getSharedPreferences("osmdroid", MODE_PRIVATE))
        }

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            // Tracking channel
            NotificationChannel(
                CHANNEL_TRACKING,
                "Seguimiento de actividad",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "BEEO monitorea tu actividad en segundo plano"
                manager.createNotificationChannel(this)
            }

            // Alerts channel
            NotificationChannel(
                CHANNEL_ALERTS,
                "Alertas de hábitos",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones cuando detectamos cambios en tus hábitos"
                manager.createNotificationChannel(this)
            }

            // Daily summary channel
            NotificationChannel(
                CHANNEL_SUMMARY,
                "Resumen diario",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Resumen diario de tu actividad"
                manager.createNotificationChannel(this)
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    companion object {
        const val CHANNEL_TRACKING = "beeo_tracking"
        const val CHANNEL_ALERTS = "beeo_alerts"
        const val CHANNEL_SUMMARY = "beeo_summary"
    }
}
