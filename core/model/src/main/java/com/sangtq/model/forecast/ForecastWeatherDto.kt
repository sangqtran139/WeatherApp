package com.sangtq.model.forecast

data class ForecastWeatherDto(
    val current: Current? = null,
    val forecast: ForecastX? = null,
    val location: Location? = null
)