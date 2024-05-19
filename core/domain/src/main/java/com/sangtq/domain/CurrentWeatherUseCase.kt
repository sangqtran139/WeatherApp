package com.sangtq.domain

import com.sangtq.data.CurrentWeatherRepository
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(private val repository: CurrentWeatherRepository) {

    suspend fun getCurrentWeather(location: String) = repository.getCurrentWeather(location)

}