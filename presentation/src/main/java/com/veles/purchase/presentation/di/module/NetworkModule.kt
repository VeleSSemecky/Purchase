package com.veles.purchase.presentation.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.data.networking.errorhandling.ErrorsCallAdapterFactory
import com.veles.purchase.data.networking.errorhandling.ExceptionFactory
import com.veles.purchase.data.networking.interceptor.AuthInterceptor
import com.veles.purchase.data.networking.interceptor.HeadersInterceptor
import com.veles.purchase.data.networking.service.message.NotificationMessageService
import com.veles.purchase.domain.core.loger.Logger
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    private const val READ_TIMEOUT = 2
    private const val WRITE_TIMEOUT = 1

    @Provides
    @Singleton
    fun provideMapperGson(): Gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: HeadersInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headersInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MINUTES)
        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MINUTES)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(logger: Logger): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> logger.i("REST_LOGGER", message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideExceptionFactory(): ExceptionFactory = ExceptionFactory()

    @Singleton
    @Provides
    fun provideBaseRetrofit(
        httpClient: OkHttpClient,
        exceptionFactory: ExceptionFactory,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EnvironmentConfig.MESSAGE_API)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ErrorsCallAdapterFactory(exceptionFactory, gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideNotificationMessageService(retrofit: Retrofit): NotificationMessageService {
        return retrofit.create(NotificationMessageService::class.java)
    }
}
