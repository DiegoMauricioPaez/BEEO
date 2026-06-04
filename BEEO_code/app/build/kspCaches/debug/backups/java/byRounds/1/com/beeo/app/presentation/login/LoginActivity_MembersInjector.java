package com.beeo.app.presentation.login;

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
public final class LoginActivity_MembersInjector implements MembersInjector<LoginActivity> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public LoginActivity_MembersInjector(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  public static MembersInjector<LoginActivity> create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new LoginActivity_MembersInjector(authRepositoryProvider);
  }

  @Override
  public void injectMembers(LoginActivity instance) {
    injectAuthRepository(instance, authRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.beeo.app.presentation.login.LoginActivity.authRepository")
  public static void injectAuthRepository(LoginActivity instance, AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }
}
