package com.veles.purchase.data.networking.interceptor

import com.veles.purchase.config.EnvironmentConfig
import java.io.IOException
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header(
                EnvironmentConfig.MESSAGE_AUTHENTICATOR_NAME,
                EnvironmentConfig.MESSAGE_AUTHENTICATOR_VALUE
            )
            .build()
        return chain.proceed(request)
    }
}
