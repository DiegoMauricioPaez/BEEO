package com.beeo.app.di;

import com.beeo.app.data.local.DailyActivityDao;
import com.beeo.app.data.local.HabitAlertDao;
import com.beeo.app.data.local.LocationSampleDao;
import com.beeo.app.data.repository.ActivityRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideActivityRepositoryFactory implements Factory<ActivityRepository> {
  private final Provider<DailyActivityDao> activityDaoProvider;

  private final Provider<HabitAlertDao> alertDaoProvider;

  private final Provider<LocationSampleDao> locationDaoProvider;

  public AppModule_ProvideActivityRepositoryFactory(Provider<DailyActivityDao> activityDaoProvider,
      Provider<HabitAlertDao> alertDaoProvider, Provider<LocationSampleDao> locationDaoProvider) {
    this.activityDaoProvider = activityDaoProvider;
    this.alertDaoProvider = alertDaoProvider;
    this.locationDaoProvider = locationDaoProvider;
  }

  @Override
  public ActivityRepository get() {
    return provideActivityRepository(activityDaoProvider.get(), alertDaoProvider.get(), locationDaoProvider.get());
  }

  public static AppModule_ProvideActivityRepositoryFactory create(
      Provider<DailyActivityDao> activityDaoProvider, Provider<HabitAlertDao> alertDaoProvider,
      Provider<LocationSampleDao> locationDaoProvider) {
    return new AppModule_ProvideActivityRepositoryFactory(activityDaoProvider, alertDaoProvider, locationDaoProvider);
  }

  public static ActivityRepository provideActivityRepository(DailyActivityDao activityDao,
      HabitAlertDao alertDao, LocationSampleDao locationDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideActivityRepository(activityDao, alertDao, locationDao));
  }
}
