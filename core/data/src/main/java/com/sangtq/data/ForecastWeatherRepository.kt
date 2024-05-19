package com.sangtq.data

import com.sangtq.model.forecast.ForecastWeatherDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface ForecastWeatherRepository {
    suspend fun getForecastWeather(
        location: String,
        days: Int?,
        language: String,
        dateTime: String
    ): Result<ForecastWeatherDto>
}

class ForecastWeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    ForecastWeatherRepository {
    override suspend fun getForecastWeather(
        location: String,
        days: Int?,
        language: String,
        dateTime: String
    ): Result<ForecastWeatherDto> {
        return apiService.getForecastWeather(location, days, language, dateTime)
    }
}