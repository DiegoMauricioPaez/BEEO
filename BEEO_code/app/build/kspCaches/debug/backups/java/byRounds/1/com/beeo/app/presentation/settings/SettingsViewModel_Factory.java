package com.beeo.app.presentation.settings;

import android.content.Context;
import com.beeo.app.data.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<UserPreferencesRepository> prefsRepositoryProvider;

  public SettingsViewModel_Factory(Provider<Context> contextProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(contextProvider.get(), prefsRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<UserPreferencesRepository> prefsRepositoryProvider) {
    return new SettingsViewModel_Factory(contextProvider, prefsRepositoryProvider);
  }

  public static SettingsViewModel newInstance(Context context,
      UserPreferencesRepository prefsRepository) {
    return new SettingsViewModel(context, prefsRepository);
  }
}
