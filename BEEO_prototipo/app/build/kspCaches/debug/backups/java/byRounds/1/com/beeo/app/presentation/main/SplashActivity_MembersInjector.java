package com.beeo.app.presentation.main;

import com.beeo.app.data.repository.UserPreferencesRepository;
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
public final class SplashActivity_MembersInjector implements MembersInjector<SplashActivity> {
  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  public SplashActivity_MembersInjector(
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    this.prefsRepositoryProvider = prefsRepositoryProvider;
  }

  public static MembersInjector<SplashActivity> create(
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    return new SplashActivity_MembersInjector(prefsRepositoryProvider);
  }

  @Override
  public void injectMembers(SplashActivity instance) {
    injectPrefsRepository(instance, prefsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.beeo.app.presentation.main.SplashActivity.prefsRepository")
  public static void injectPrefsRepository(SplashActivity instance,
      UserPreferencesRepository prefsRepository) {
    instance.prefsRepository = prefsRepository;
  }
}
