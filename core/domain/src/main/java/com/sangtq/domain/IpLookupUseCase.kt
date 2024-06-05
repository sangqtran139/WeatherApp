package com.sangtq.domain

import com.sangtq.data.IpLookupRepository
import javax.inject.Inject

class IpLookupUseCase @Inject constructor(private val repository: IpLookupRepository) {

    suspend fun getLocationByIpLookup(location: String) = repository.getLocationByIpLookup(location)
}