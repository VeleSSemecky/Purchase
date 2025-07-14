package com.veles.purchase.presentation.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.networking.errorhandling.ErrorsCallAdapterFactory
import com.veles.purchase.data.networking.errorhandling.ExceptionFactory
import com.veles.purchase.data.networking.interceptor.AuthInterceptor
import com.veles.purchase.data.networking.interceptor.HeadersInterceptor
import com.veles.purchase.data.networking.service.message.NotificationMessageService
import com.veles.purchase.domain.core.loger.Logger
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Koin module for network dependencies
 * Converted from Dagger NetworkModule
 */
val networkModule = module {

    single<Gson> {
        GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    single<HttpLoggingInterceptor> {
        val logger = get<Logger>()
        HttpLoggingInterceptor { message -> logger.i("REST_LOGGER", message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single<ExceptionFactory> { ExceptionFactory() }

    single<AuthInterceptor> { AuthInterceptor() }

    single<HeadersInterceptor> { HeadersInterceptor() }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HeadersInterceptor>())
            .addInterceptor(get<AuthInterceptor>())
            .connectTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MINUTES)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(EnvironmentConfig.MESSAGE_API)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .addCallAdapterFactory(ErrorsCallAdapterFactory(get<ExceptionFactory>(), get<Gson>()))
            .build()
    }

    single<NotificationMessageService> { get<Retrofit>().create(NotificationMessageService::class.java) }
}

private const val READ_TIMEOUT = 2
private const val WRITE_TIMEOUT = 1
