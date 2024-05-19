package com.sangtq.model.realtime

data class RealTimeWeatherDto(
    val current: Current? = null,
    val location: Location? = null
)