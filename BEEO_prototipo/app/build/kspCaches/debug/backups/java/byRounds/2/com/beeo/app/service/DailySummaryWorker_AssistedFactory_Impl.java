package com.beeo.app.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DailySummaryWorker_AssistedFactory_Impl implements DailySummaryWorker_AssistedFactory {
  private final DailySummaryWorker_Factory delegateFactory;

  DailySummaryWorker_AssistedFactory_Impl(DailySummaryWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DailySummaryWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<DailySummaryWorker_AssistedFactory> create(
      DailySummaryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DailySummaryWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<DailySummaryWorker_AssistedFactory> createFactoryProvider(
      DailySummaryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DailySummaryWorker_AssistedFactory_Impl(delegateFactory));
  }
}
