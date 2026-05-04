package com.beeo.app.di

import android.content.Context
import androidx.room.Room
import com.beeo.app.data.local.*
import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.data.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BEEODatabase =
        Room.databaseBuilder(context, BEEODatabase::class.java, "beeo_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideDailyActivityDao(db: BEEODatabase): DailyActivityDao = db.dailyActivityDao()
    @Provides fun provideHabitAlertDao(db: BEEODatabase): HabitAlertDao = db.habitAlertDao()
    @Provides fun provideLocationSampleDao(db: BEEODatabase): LocationSampleDao = db.locationSampleDao()

    @Provides
    @Singleton
    fun provideActivityRepository(
        activityDao: DailyActivityDao,
        alertDao: HabitAlertDao,
        locationDao: LocationSampleDao
    ): ActivityRepository = ActivityRepository(activityDao, alertDao, locationDao)

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        @ApplicationContext context: Context
    ): UserPreferencesRepository = UserPreferencesRepository(context)
}
