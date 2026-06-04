package com.beeo.app.data.repository;

import com.beeo.app.data.local.DailyActivityDao;
import com.beeo.app.data.local.HabitAlertDao;
import com.beeo.app.data.local.LocationSampleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ActivityRepository_Factory implements Factory<ActivityRepository> {
  private final Provider<DailyActivityDao> activityDaoProvider;

  private final Provider<HabitAlertDao> alertDaoProvider;

  private final Provider<LocationSampleDao> locationDaoProvider;

  public ActivityRepository_Factory(Provider<DailyActivityDao> activityDaoProvider,
      Provider<HabitAlertDao> alertDaoProvider, Provider<LocationSampleDao> locationDaoProvider) {
    this.activityDaoProvider = activityDaoProvider;
    this.alertDaoProvider = alertDaoProvider;
    this.locationDaoProvider = locationDaoProvider;
  }

  @Override
  public ActivityRepository get() {
    return newInstance(activityDaoProvider.get(), alertDaoProvider.get(), locationDaoProvider.get());
  }

  public static ActivityRepository_Factory create(Provider<DailyActivityDao> activityDaoProvider,
      Provider<HabitAlertDao> alertDaoProvider, Provider<LocationSampleDao> locationDaoProvider) {
    return new ActivityRepository_Factory(activityDaoProvider, alertDaoProvider, locationDaoProvider);
  }

  public static ActivityRepository newInstance(DailyActivityDao activityDao, HabitAlertDao alertDao,
      LocationSampleDao locationDao) {
    return new ActivityRepository(activityDao, alertDao, locationDao);
  }
}
