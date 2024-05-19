package com.sangtq.data

import com.sangtq.model.realtime.RealTimeWeatherDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(location: String): Result<RealTimeWeatherDto>
}

class CurrentWeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) : CurrentWeatherRepository {

    override suspend fun getCurrentWeather(location: String): Result<RealTimeWeatherDto> {
        return apiService.getRealTimeWeather(location = location)
    }
}