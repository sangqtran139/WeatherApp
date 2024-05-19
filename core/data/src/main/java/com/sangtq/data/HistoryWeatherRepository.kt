package com.sangtq.data

import com.sangtq.model.history.HistoryWeatherDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface HistoryWeatherRepository {
    suspend fun getHistoryWeather(
        location: String,
        language: String,
        hour: Int? = null,
        endDateTime: String
    ): Result<HistoryWeatherDto>
}

class HistoryWeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    HistoryWeatherRepository {
    override suspend fun getHistoryWeather(
        location: String,
        language: String,
        hour: Int?,
        endDateTime: String
    ): Result<HistoryWeatherDto> {
        return apiService.getHistoryWeather(location, language, hour, endDateTime)
    }
}