package com.sangtq.domain

import com.sangtq.data.ForecastWeatherRepository
import javax.inject.Inject

class ForecastWeatherUseCase @Inject constructor(private val repository: ForecastWeatherRepository) {
    suspend fun getForecastWeather(
        location: String, days: Int?,
        language: String,
        dateTime: String
    ) = repository.getForecastWeather(location, days, language, dateTime)

}