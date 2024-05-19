package com.sangtq.data

import com.sangtq.model.sport.SportsDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface SportsInformationRepository {
    suspend fun searchLocation(
        location: String,
    ): Result<SportsDto>
}

class SportsInformationRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    SportsInformationRepository {
    override suspend fun searchLocation(location: String): Result<SportsDto> {
        return apiService.getComingSports(location)
    }
}