package com.sangtq.weatherapp.home;

import com.sangtq.domain.ForecastWeatherUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
    "KotlinInternalInJava",
    "cast"
})
public final class WeatherHomeViewModel_Factory implements Factory<WeatherHomeViewModel> {
  private final Provider<ForecastWeatherUseCase> forecastWeatherUseCaseProvider;

  public WeatherHomeViewModel_Factory(
      Provider<ForecastWeatherUseCase> forecastWeatherUseCaseProvider) {
    this.forecastWeatherUseCaseProvider = forecastWeatherUseCaseProvider;
  }

  @Override
  public WeatherHomeViewModel get() {
    return newInstance(forecastWeatherUseCaseProvider.get());
  }

  public static WeatherHomeViewModel_Factory create(
      Provider<ForecastWeatherUseCase> forecastWeatherUseCaseProvider) {
    return new WeatherHomeViewModel_Factory(forecastWeatherUseCaseProvider);
  }

  public static WeatherHomeViewModel newInstance(ForecastWeatherUseCase forecastWeatherUseCase) {
    return new WeatherHomeViewModel(forecastWeatherUseCase);
  }
}
