package com.beeo.app.service;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = DailySummaryWorker.class
)
public interface DailySummaryWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.beeo.app.service.DailySummaryWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      DailySummaryWorker_AssistedFactory factory);
}
