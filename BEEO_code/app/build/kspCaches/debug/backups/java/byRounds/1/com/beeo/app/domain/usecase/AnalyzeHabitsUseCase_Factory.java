package com.beeo.app.domain.usecase;

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
public final class AnalyzeHabitsUseCase_Factory implements Factory<AnalyzeHabitsUseCase> {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  public AnalyzeHabitsUseCase_Factory(Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
  }

  @Override
  public AnalyzeHabitsUseCase get() {
    return newInstance(activityRepositoryProvider.get(), prefsRepositoryProvider.get());
  }

  public static AnalyzeHabitsUseCase_Factory create(
      Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    return new AnalyzeHabitsUseCase_Factory(activityRepositoryProvider, prefsRepositoryProvider);
  }

  public static AnalyzeHabitsUseCase newInstance(ActivityRepository activityRepository,
      UserPreferencesRepository prefsRepository) {
    return new AnalyzeHabitsUseCase(activityRepository, prefsRepository);
  }
}
