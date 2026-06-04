package com.beeo.app.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.beeo.app.data.repository.ActivityRepository;
import com.beeo.app.data.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DailySummaryWorker_Factory {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  public DailySummaryWorker_Factory(Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
  }

  public DailySummaryWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, activityRepositoryProvider.get(), prefsRepositoryProvider.get());
  }

  public static DailySummaryWorker_Factory create(
      Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    return new DailySummaryWorker_Factory(activityRepositoryProvider, prefsRepositoryProvider);
  }

  public static DailySummaryWorker newInstance(Context appContext, WorkerParameters workerParams,
      ActivityRepository activityRepository, UserPreferencesRepository prefsRepository) {
    return new DailySummaryWorker(appContext, workerParams, activityRepository, prefsRepository);
  }
}
