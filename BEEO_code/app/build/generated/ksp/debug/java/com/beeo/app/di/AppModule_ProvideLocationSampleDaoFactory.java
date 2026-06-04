package com.beeo.app.di;

import com.beeo.app.data.local.BEEODatabase;
import com.beeo.app.data.local.LocationSampleDao;
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
public final class AppModule_ProvideLocationSampleDaoFactory implements Factory<LocationSampleDao> {
  private final Provider<BEEODatabase> dbProvider;

  public AppModule_ProvideLocationSampleDaoFactory(Provider<BEEODatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LocationSampleDao get() {
    return provideLocationSampleDao(dbProvider.get());
  }

  public static AppModule_ProvideLocationSampleDaoFactory create(
      Provider<BEEODatabase> dbProvider) {
    return new AppModule_ProvideLocationSampleDaoFactory(dbProvider);
  }

  public static LocationSampleDao provideLocationSampleDao(BEEODatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLocationSampleDao(db));
  }
}
