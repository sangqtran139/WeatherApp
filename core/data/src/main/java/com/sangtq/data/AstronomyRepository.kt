package com.sangtq.data

import com.sangtq.model.astronomy.AstronomyDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface AstronomyRepository {
    suspend fun getAstronomy(
        location: String,
        dateTime: String
    ): Result<AstronomyDto>
}

class AstronomyRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    AstronomyRepository {
    override suspend fun getAstronomy(location: String, dateTime: String): Result<AstronomyDto> {
        return apiService.getAstronomy(location, dateTime)
    }
}