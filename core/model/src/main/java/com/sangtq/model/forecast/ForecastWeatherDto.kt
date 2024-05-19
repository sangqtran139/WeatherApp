package com.sangtq.model.forecast

data class ForecastWeatherDto(
    val current: Current,
    val forecast: ForecastX,
    val location: Location
)