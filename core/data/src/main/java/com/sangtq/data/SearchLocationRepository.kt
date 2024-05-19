package com.sangtq.data

import com.sangtq.model.search.SearchLocationDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface SearchLocationRepository {
    suspend fun searchLocation(
        location: String,
    ): Result<SearchLocationDto>
}

class SearchLocationRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    SearchLocationRepository {
    override suspend fun searchLocation(location: String): Result<SearchLocationDto> {
        return apiService.searchLocation(location)
    }
}