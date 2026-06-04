package com.beeo.app.presentation.main;

import com.beeo.app.data.repository.AuthRepository;
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
  private final Provider<AuthRepository> authRepositoryProvider;

  public SplashActivity_MembersInjector(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  public static MembersInjector<SplashActivity> create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new SplashActivity_MembersInjector(authRepositoryProvider);
  }

  @Override
  public void injectMembers(SplashActivity instance) {
    injectAuthRepository(instance, authRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.beeo.app.presentation.main.SplashActivity.authRepository")
  public static void injectAuthRepository(SplashActivity instance, AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }
}
