package com.beeo.app.di;

import com.beeo.app.data.local.BEEODatabase;
import com.beeo.app.data.local.HabitAlertDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideHabitAlertDaoFactory implements Factory<HabitAlertDao> {
  private final Provider<BEEODatabase> dbProvider;

  public AppModule_ProvideHabitAlertDaoFactory(Provider<BEEODatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public HabitAlertDao get() {
    return provideHabitAlertDao(dbProvider.get());
  }

  public static AppModule_ProvideHabitAlertDaoFactory create(Provider<BEEODatabase> dbProvider) {
    return new AppModule_ProvideHabitAlertDaoFactory(dbProvider);
  }

  public static HabitAlertDao provideHabitAlertDao(BEEODatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideHabitAlertDao(db));
  }
}
