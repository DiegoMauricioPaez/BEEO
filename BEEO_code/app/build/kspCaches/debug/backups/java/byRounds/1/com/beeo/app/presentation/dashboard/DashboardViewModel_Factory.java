package com.beeo.app.presentation.dashboard;

import com.beeo.app.data.repository.ActivityRepository;
import com.beeo.app.data.repository.UserPreferencesRepository;
import com.beeo.app.domain.usecase.AnalyzeHabitsUseCase;
import com.beeo.app.domain.usecase.GetWeeklyStatsUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  private final Provider<AnalyzeHabitsUseCase> analyzeHabitsUseCaseProvider;

  private final Provider<GetWeeklyStatsUseCase> getWeeklyStatsUseCaseProvider;

  public DashboardViewModel_Factory(Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider,
      Provider<AnalyzeHabitsUseCase> analyzeHabitsUseCaseProvider,
      Provider<GetWeeklyStatsUseCase> getWeeklyStatsUseCaseProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.analyzeHabitsUseCaseProvider = analyzeHabitsUseCaseProvider;
    this.getWeeklyStatsUseCaseProvider = getWeeklyStatsUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(activityRepositoryProvider.get(), prefsRepositoryProvider.get(), analyzeHabitsUseCaseProvider.get(), getWeeklyStatsUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider,
      Provider<AnalyzeHabitsUseCase> analyzeHabitsUseCaseProvider,
      Provider<GetWeeklyStatsUseCase> getWeeklyStatsUseCaseProvider) {
    return new DashboardViewModel_Factory(activityRepositoryProvider, prefsRepositoryProvider, analyzeHabitsUseCaseProvider, getWeeklyStatsUseCaseProvider);
  }

  public static DashboardViewModel newInstance(ActivityRepository activityRepository,
      UserPreferencesRepository prefsRepository, AnalyzeHabitsUseCase analyzeHabitsUseCase,
      GetWeeklyStatsUseCase getWeeklyStatsUseCase) {
    return new DashboardViewModel(activityRepository, prefsRepository, analyzeHabitsUseCase, getWeeklyStatsUseCase);
  }
}
