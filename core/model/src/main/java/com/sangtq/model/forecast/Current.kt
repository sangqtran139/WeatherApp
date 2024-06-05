package com.sangtq.model.forecast

data class Current(
    val cloud: Long,
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val humidity: Long,
    val is_day: Long,
    val last_updated: String,
    val last_updated_epoch: Long,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Long,
    val temp_c: Long,
    val temp_f: Double,
    val uv: Long,
    val vis_km: Long,
    val vis_miles: Long,
    val wind_degree: Long,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
)