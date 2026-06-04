package com.beeo.app.presentation.notifications;

import com.beeo.app.data.repository.ActivityRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AlertsViewModel_Factory implements Factory<AlertsViewModel> {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  public AlertsViewModel_Factory(Provider<ActivityRepository> activityRepositoryProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
  }

  @Override
  public AlertsViewModel get() {
    return newInstance(activityRepositoryProvider.get());
  }

  public static AlertsViewModel_Factory create(
      Provider<ActivityRepository> activityRepositoryProvider) {
    return new AlertsViewModel_Factory(activityRepositoryProvider);
  }

  public static AlertsViewModel newInstance(ActivityRepository activityRepository) {
    return new AlertsViewModel(activityRepository);
  }
}
