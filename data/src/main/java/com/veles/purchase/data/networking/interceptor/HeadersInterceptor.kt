package com.veles.purchase.data.networking.interceptor

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("Accept", "*/*")
            .header("Accept-Encoding", "gzip, deflate, br")
            .header("Connection", "keep-alive")
            .header("Cache-Control", "max-age=0")
            .build()
        return chain.proceed(request)
    }
}
