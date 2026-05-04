package com.beeo.app;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class BEEOApplication_MembersInjector implements MembersInjector<BEEOApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public BEEOApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<BEEOApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new BEEOApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(BEEOApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.beeo.app.BEEOApplication.workerFactory")
  public static void injectWorkerFactory(BEEOApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
