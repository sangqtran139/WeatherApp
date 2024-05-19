package com.sangtq.model.iplookup

data class IpLookUpDto(
    val city: String,
    val continent_code: String,
    val continent_name: String,
    val country_code: String,
    val country_name: String,
    val geoname_id: Int,
    val ip: String,
    val is_eu: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val region: String,
    val type: String,
    val tz_id: String
)