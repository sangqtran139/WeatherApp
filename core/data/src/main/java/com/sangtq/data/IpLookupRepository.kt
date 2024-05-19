package com.sangtq.data

import com.sangtq.model.iplookup.IpLookUpDto
import com.sangtq.network.ApiService
import javax.inject.Inject

interface IpLookupRepository {
    suspend fun getLocationByIpLookup(
        location: String,
    ): Result<IpLookUpDto>
}

class IpLookupRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    IpLookupRepository {
    override suspend fun getLocationByIpLookup(location: String): Result<IpLookUpDto> {
        return apiService.getLocationByIpLookup(location)
    }

}