package com.sangtq.data

import com.sangtq.model.timezone.TimeZoneDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface TimeZoneRepository {
    suspend fun getTimeZone(
        location: String,
    ): Result<TimeZoneDto>
}

class TimeZoneRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    TimeZoneRepository {
    override suspend fun getTimeZone(location: String): Result<TimeZoneDto> {
        return apiService.getTimeZone(location)
    }
}