package com.sangtq.network

import okhttp3.Interceptor
import okhttp3.Response

class BaseHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            header("AppId", "1")
            header("Platform", "Android")
            header("x-rapidapi-key", BaseURL.apiKey)
            header("x-rapidapi-host", BaseURL.host)
        }.build()
        return chain.proceed(request)
    }
}