package com.sangtq.data.di

import com.sangtq.data.AstronomyRepository
import com.sangtq.data.AstronomyRepositoryImpl
import com.sangtq.data.ForecastWeatherRepository
import com.sangtq.data.ForecastWeatherRepositoryImpl
import com.sangtq.data.HistoryWeatherRepository
import com.sangtq.data.HistoryWeatherRepositoryImpl
import com.sangtq.data.IpLookupRepository
import com.sangtq.data.IpLookupRepositoryImpl
import com.sangtq.data.CurrentWeatherRepository
import com.sangtq.data.CurrentWeatherRepositoryImpl
import com.sangtq.data.SearchLocationRepository
import com.sangtq.data.SearchLocationRepositoryImpl
import com.sangtq.data.SportsInformationRepository
import com.sangtq.data.SportsInformationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideCurrentWeatherRepo(impl: CurrentWeatherRepositoryImpl): CurrentWeatherRepository

    @Binds
    fun provideForecastWeatherRepo(impl: ForecastWeatherRepositoryImpl): ForecastWeatherRepository

    @Binds
    fun provideIpLookupRepo(impl: IpLookupRepositoryImpl): IpLookupRepository

    @Binds
    fun provideAstronomyRepo(impl: AstronomyRepositoryImpl): AstronomyRepository

    @Binds
    fun provideHistoryWeatherRepo(impl: HistoryWeatherRepositoryImpl): HistoryWeatherRepository

    @Binds
    fun provideSearchLocationRepo(impl: SearchLocationRepositoryImpl): SearchLocationRepository

    @Binds
    fun provideSportsInformationRepo(impl: SportsInformationRepositoryImpl): SportsInformationRepository
}