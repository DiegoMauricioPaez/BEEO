package com.beeo.app.presentation.map;

import com.beeo.app.data.repository.ActivityRepository;
import com.beeo.app.data.repository.UserPreferencesRepository;
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
public final class MapViewModel_Factory implements Factory<MapViewModel> {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  public MapViewModel_Factory(Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
  }

  @Override
  public MapViewModel get() {
    return newInstance(activityRepositoryProvider.get(), prefsRepositoryProvider.get());
  }

  public static MapViewModel_Factory create(Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    return new MapViewModel_Factory(activityRepositoryProvider, prefsRepositoryProvider);
  }

  public static MapViewModel newInstance(ActivityRepository activityRepository,
      UserPreferencesRepository prefsRepository) {
    return new MapViewModel(activityRepository, prefsRepository);
  }
}
