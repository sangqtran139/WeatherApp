package com.sangtq.network

import com.sangtq.model.astronomy.AstronomyDto
import com.sangtq.model.forecast.ForecastWeatherDto
import com.sangtq.model.history.HistoryWeatherDto
import com.sangtq.model.iplookup.IpLookUpDto
import com.sangtq.model.realtime.RealTimeWeatherDto
import com.sangtq.model.search.SearchLocationDto
import com.sangtq.model.sport.SportsDto
import com.sangtq.model.timezone.TimeZoneDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("current.json")
    suspend fun getRealTimeWeather(
        @Query("q") location: String
    ): Result<RealTimeWeatherDto>

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") location: String,
        @Query("days") days: Int? = null,
        @Query("lang") language: String = "",
        @Query("dt") dateTime: String = "",
    ): Result<ForecastWeatherDto>

    @GET("ip.json")
    suspend fun getLocationByIpLookup(
        @Query("q") ipLookup: String,
    ): Result<IpLookUpDto>

    @GET("timezone.json")
    suspend fun getTimeZone(
        @Query("q") location: String
    ): Result<TimeZoneDto>

    @GET("astronomy.json")
    suspend fun getAstronomy(
        @Query("q") location: String,
        @Query("dt") dateTime: String = "",
    ): Result<AstronomyDto>

    @GET("history.json")
    suspend fun getHistoryWeather(
        @Query("q") location: String,
        @Query("lang") language: String = "",
        @Query("hour") hour: Int? = null,
        @Query("end_dt") endDateTime: String = "",
    ): Result<HistoryWeatherDto>

    @GET("search.json")
    suspend fun searchLocation(
        @Query("q") location: String
    ): Result<SearchLocationDto>

    @GET("sports.json")
    suspend fun getComingSports(
        @Query("q") location: String
    ): Result<SportsDto>
}