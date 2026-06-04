package com.beeo.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.beeo.app.data.local.BEEODatabase;
import com.beeo.app.data.local.DailyActivityDao;
import com.beeo.app.data.local.HabitAlertDao;
import com.beeo.app.data.local.LocationSampleDao;
import com.beeo.app.data.repository.ActivityRepository;
import com.beeo.app.data.repository.AuthRepository;
import com.beeo.app.data.repository.UserPreferencesRepository;
import com.beeo.app.di.AppModule_ProvideActivityRepositoryFactory;
import com.beeo.app.di.AppModule_ProvideAuthRepositoryFactory;
import com.beeo.app.di.AppModule_ProvideDailyActivityDaoFactory;
import com.beeo.app.di.AppModule_ProvideDatabaseFactory;
import com.beeo.app.di.AppModule_ProvideFirebaseAuthFactory;
import com.beeo.app.di.AppModule_ProvideFirestoreFactory;
import com.beeo.app.di.AppModule_ProvideHabitAlertDaoFactory;
import com.beeo.app.di.AppModule_ProvideLocationSampleDaoFactory;
import com.beeo.app.di.AppModule_ProvideUserPreferencesRepositoryFactory;
import com.beeo.app.domain.usecase.AnalyzeHabitsUseCase;
import com.beeo.app.domain.usecase.GetWeeklyStatsUseCase;
import com.beeo.app.presentation.dashboard.DashboardFragment;
import com.beeo.app.presentation.dashboard.DashboardViewModel;
import com.beeo.app.presentation.dashboard.DashboardViewModel_HiltModules_KeyModule_ProvideFactory;
import com.beeo.app.presentation.login.LoginActivity;
import com.beeo.app.presentation.login.LoginActivity_MembersInjector;
import com.beeo.app.presentation.main.MainActivity;
import com.beeo.app.presentation.main.SplashActivity;
import com.beeo.app.presentation.main.SplashActivity_MembersInjector;
import com.beeo.app.presentation.map.MapFragment;
import com.beeo.app.presentation.map.MapViewModel;
import com.beeo.app.presentation.map.MapViewModel_HiltModules_KeyModule_ProvideFactory;
import com.beeo.app.presentation.notifications.AlertsFragment;
import com.beeo.app.presentation.notifications.AlertsViewModel;
import com.beeo.app.presentation.notifications.AlertsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.beeo.app.presentation.settings.SettingsFragment;
import com.beeo.app.presentation.settings.SettingsViewModel;
import com.beeo.app.presentation.settings.SettingsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.beeo.app.service.DailySummaryWorker;
import com.beeo.app.service.DailySummaryWorker_AssistedFactory;
import com.beeo.app.service.TrackingService;
import com.beeo.app.service.TrackingService_MembersInjector;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerBEEOApplication_HiltComponents_SingletonC {
  private DaggerBEEOApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public BEEOApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements BEEOApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements BEEOApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements BEEOApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements BEEOApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements BEEOApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements BEEOApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements BEEOApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public BEEOApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends BEEOApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends BEEOApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public void injectDashboardFragment(DashboardFragment dashboardFragment) {
    }

    @Override
    public void injectMapFragment(MapFragment mapFragment) {
    }

    @Override
    public void injectAlertsFragment(AlertsFragment alertsFragment) {
    }

    @Override
    public void injectSettingsFragment(SettingsFragment settingsFragment) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends BEEOApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends BEEOApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectLoginActivity(LoginActivity loginActivity) {
      injectLoginActivity2(loginActivity);
    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public void injectSplashActivity(SplashActivity splashActivity) {
      injectSplashActivity2(splashActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AlertsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DashboardViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MapViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SettingsViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private LoginActivity injectLoginActivity2(LoginActivity instance) {
      LoginActivity_MembersInjector.injectAuthRepository(instance, singletonCImpl.provideAuthRepositoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SplashActivity injectSplashActivity2(SplashActivity instance) {
      SplashActivity_MembersInjector.injectAuthRepository(instance, singletonCImpl.provideAuthRepositoryProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends BEEOApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AlertsViewModel> alertsViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<MapViewModel> mapViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private AnalyzeHabitsUseCase analyzeHabitsUseCase() {
      return new AnalyzeHabitsUseCase(singletonCImpl.provideActivityRepositoryProvider.get(), singletonCImpl.provideUserPreferencesRepositoryProvider.get());
    }

    private GetWeeklyStatsUseCase getWeeklyStatsUseCase() {
      return new GetWeeklyStatsUseCase(singletonCImpl.provideActivityRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.alertsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.mapViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, javax.inject.Provider<ViewModel>>of("com.beeo.app.presentation.notifications.AlertsViewModel", ((Provider) alertsViewModelProvider), "com.beeo.app.presentation.dashboard.DashboardViewModel", ((Provider) dashboardViewModelProvider), "com.beeo.app.presentation.map.MapViewModel", ((Provider) mapViewModelProvider), "com.beeo.app.presentation.settings.SettingsViewModel", ((Provider) settingsViewModelProvider));
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<String, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.beeo.app.presentation.notifications.AlertsViewModel 
          return (T) new AlertsViewModel(singletonCImpl.provideActivityRepositoryProvider.get());

          case 1: // com.beeo.app.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.provideActivityRepositoryProvider.get(), singletonCImpl.provideUserPreferencesRepositoryProvider.get(), viewModelCImpl.analyzeHabitsUseCase(), viewModelCImpl.getWeeklyStatsUseCase());

          case 2: // com.beeo.app.presentation.map.MapViewModel 
          return (T) new MapViewModel(singletonCImpl.provideActivityRepositoryProvider.get(), singletonCImpl.provideUserPreferencesRepositoryProvider.get());

          case 3: // com.beeo.app.presentation.settings.SettingsViewModel 
          return (T) new SettingsViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideUserPreferencesRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends BEEOApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends BEEOApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    private AnalyzeHabitsUseCase analyzeHabitsUseCase() {
      return new AnalyzeHabitsUseCase(singletonCImpl.provideActivityRepositoryProvider.get(), singletonCImpl.provideUserPreferencesRepositoryProvider.get());
    }

    @Override
    public void injectTrackingService(TrackingService trackingService) {
      injectTrackingService2(trackingService);
    }

    @CanIgnoreReturnValue
    private TrackingService injectTrackingService2(TrackingService instance) {
      TrackingService_MembersInjector.injectActivityRepository(instance, singletonCImpl.provideActivityRepositoryProvider.get());
      TrackingService_MembersInjector.injectPrefsRepository(instance, singletonCImpl.provideUserPreferencesRepositoryProvider.get());
      TrackingService_MembersInjector.injectAnalyzeHabitsUseCase(instance, analyzeHabitsUseCase());
      return instance;
    }
  }

  private static final class SingletonCImpl extends BEEOApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<BEEODatabase> provideDatabaseProvider;

    private Provider<ActivityRepository> provideActivityRepositoryProvider;

    private Provider<UserPreferencesRepository> provideUserPreferencesRepositoryProvider;

    private Provider<DailySummaryWorker_AssistedFactory> dailySummaryWorker_AssistedFactoryProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<FirebaseFirestore> provideFirestoreProvider;

    private Provider<AuthRepository> provideAuthRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private DailyActivityDao dailyActivityDao() {
      return AppModule_ProvideDailyActivityDaoFactory.provideDailyActivityDao(provideDatabaseProvider.get());
    }

    private HabitAlertDao habitAlertDao() {
      return AppModule_ProvideHabitAlertDaoFactory.provideHabitAlertDao(provideDatabaseProvider.get());
    }

    private LocationSampleDao locationSampleDao() {
      return AppModule_ProvideLocationSampleDaoFactory.provideLocationSampleDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return ImmutableMap.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>of("com.beeo.app.service.DailySummaryWorker", ((Provider) dailySummaryWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<BEEODatabase>(singletonCImpl, 2));
      this.provideActivityRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRepository>(singletonCImpl, 1));
      this.provideUserPreferencesRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferencesRepository>(singletonCImpl, 3));
      this.dailySummaryWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<DailySummaryWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 5));
      this.provideFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 6));
      this.provideAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 4));
    }

    @Override
    public void injectBEEOApplication(BEEOApplication bEEOApplication) {
      injectBEEOApplication2(bEEOApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private BEEOApplication injectBEEOApplication2(BEEOApplication instance) {
      BEEOApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.beeo.app.service.DailySummaryWorker_AssistedFactory 
          return (T) new DailySummaryWorker_AssistedFactory() {
            @Override
            public DailySummaryWorker create(Context appContext, WorkerParameters workerParams) {
              return new DailySummaryWorker(appContext, workerParams, singletonCImpl.provideActivityRepositoryProvider.get(), singletonCImpl.provideUserPreferencesRepositoryProvider.get());
            }
          };

          case 1: // com.beeo.app.data.repository.ActivityRepository 
          return (T) AppModule_ProvideActivityRepositoryFactory.provideActivityRepository(singletonCImpl.dailyActivityDao(), singletonCImpl.habitAlertDao(), singletonCImpl.locationSampleDao());

          case 2: // com.beeo.app.data.local.BEEODatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.beeo.app.data.repository.UserPreferencesRepository 
          return (T) AppModule_ProvideUserPreferencesRepositoryFactory.provideUserPreferencesRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.beeo.app.data.repository.AuthRepository 
          return (T) AppModule_ProvideAuthRepositoryFactory.provideAuthRepository(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirestoreProvider.get());

          case 5: // com.google.firebase.auth.FirebaseAuth 
          return (T) AppModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 6: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) AppModule_ProvideFirestoreFactory.provideFirestore();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
