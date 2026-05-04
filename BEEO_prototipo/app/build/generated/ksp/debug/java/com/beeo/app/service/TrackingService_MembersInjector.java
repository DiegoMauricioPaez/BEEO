package com.beeo.app.service;

import com.beeo.app.data.repository.ActivityRepository;
import com.beeo.app.data.repository.UserPreferencesRepository;
import com.beeo.app.domain.usecase.AnalyzeHabitsUseCase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TrackingService_MembersInjector implements MembersInjector<TrackingService> {
  private final Provider<ActivityRepository> activityRepositoryProvider;

  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  private final Provider<AnalyzeHabitsUseCase> analyzeHabitsUseCaseProvider;

  public TrackingService_MembersInjector(Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider,
      Provider<AnalyzeHabitsUseCase> analyzeHabitsUseCaseProvider) {
    this.activityRepositoryProvider = activityRepositoryProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.analyzeHabitsUseCaseProvider = analyzeHabitsUseCaseProvider;
  }

  public static MembersInjector<TrackingService> create(
      Provider<ActivityRepository> activityRepositoryProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider,
      Provider<AnalyzeHabitsUseCase> analyzeHabitsUseCaseProvider) {
    return new TrackingService_MembersInjector(activityRepositoryProvider, prefsRepositoryProvider, analyzeHabitsUseCaseProvider);
  }

  @Override
  public void injectMembers(TrackingService instance) {
    injectActivityRepository(instance, activityRepositoryProvider.get());
    injectPrefsRepository(instance, prefsRepositoryProvider.get());
    injectAnalyzeHabitsUseCase(instance, analyzeHabitsUseCaseProvider.get());
  }

  @InjectedFieldSignature("com.beeo.app.service.TrackingService.activityRepository")
  public static void injectActivityRepository(TrackingService instance,
      ActivityRepository activityRepository) {
    instance.activityRepository = activityRepository;
  }

  @InjectedFieldSignature("com.beeo.app.service.TrackingService.prefsRepository")
  public static void injectPrefsRepository(TrackingService instance,
      UserPreferencesRepository prefsRepository) {
    instance.prefsRepository = prefsRepository;
  }

  @InjectedFieldSignature("com.beeo.app.service.TrackingService.analyzeHabitsUseCase")
  public static void injectAnalyzeHabitsUseCase(TrackingService instance,
      AnalyzeHabitsUseCase analyzeHabitsUseCase) {
    instance.analyzeHabitsUseCase = analyzeHabitsUseCase;
  }
}
