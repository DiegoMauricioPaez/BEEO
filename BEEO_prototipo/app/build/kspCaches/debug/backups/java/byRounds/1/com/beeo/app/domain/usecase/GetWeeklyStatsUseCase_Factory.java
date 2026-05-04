package com.beeo.app.domain.usecase;

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
public final class GetWeeklyStatsUseCase_Factory implements Factory<GetWeeklyStatsUseCase> {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  public GetWeeklyStatsUseCase_Factory(Provider<ActivityRepository> activityRepositoryProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
  }

  @Override
  public GetWeeklyStatsUseCase get() {
    return newInstance(activityRepositoryProvider.get());
  }

  public static GetWeeklyStatsUseCase_Factory create(
      Provider<ActivityRepository> activityRepositoryProvider) {
    return new GetWeeklyStatsUseCase_Factory(activityRepositoryProvider);
  }

  public static GetWeeklyStatsUseCase newInstance(ActivityRepository activityRepository) {
    return new GetWeeklyStatsUseCase(activityRepository);
  }
}
