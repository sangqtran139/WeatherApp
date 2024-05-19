package com.sangtq.network

import okhttp3.Interceptor
import okhttp3.Response

class BaseHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            header("AppId", "1")
            header("Platform", "Android")
            header("X-RapidAPI-Key", BaseURL.apiKey)
            header("X-RapidAPI-Host", BaseURL.host)
        }.build()
        return chain.proceed(request)
    }
}