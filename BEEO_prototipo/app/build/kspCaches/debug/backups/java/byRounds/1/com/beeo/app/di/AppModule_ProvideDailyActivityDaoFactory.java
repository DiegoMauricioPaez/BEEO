package com.beeo.app.di;

import com.beeo.app.data.local.BEEODatabase;
import com.beeo.app.data.local.DailyActivityDao;
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
public final class AppModule_ProvideDailyActivityDaoFactory implements Factory<DailyActivityDao> {
  private final Provider<BEEODatabase> dbProvider;

  public AppModule_ProvideDailyActivityDaoFactory(Provider<BEEODatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DailyActivityDao get() {
    return provideDailyActivityDao(dbProvider.get());
  }

  public static AppModule_ProvideDailyActivityDaoFactory create(Provider<BEEODatabase> dbProvider) {
    return new AppModule_ProvideDailyActivityDaoFactory(dbProvider);
  }

  public static DailyActivityDao provideDailyActivityDao(BEEODatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDailyActivityDao(db));
  }
}
