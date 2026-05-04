package com.beeo.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.data.repository.UserPreferencesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            TrackingService.start(context)
            scheduleDailyWorker(context)
        }
    }

    private fun scheduleDailyWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<DailySummaryWorker>(
            24, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_summary",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}

@HiltWorker
class DailySummaryWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val activityRepository: ActivityRepository,
    private val prefsRepository: UserPreferencesRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            activityRepository.cleanOldData(90)
            prefsRepository.updateLastAnalysisTimestamp()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
