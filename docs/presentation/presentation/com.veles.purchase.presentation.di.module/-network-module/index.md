//[presentation](../../../index.md)/[com.veles.purchase.presentation.di.module](../index.md)/[NetworkModule](index.md)

# NetworkModule

[androidJvm]\
@Module

object [NetworkModule](index.md)

## Functions

| Name | Summary |
|---|---|
| [provideBaseRetrofit](provide-base-retrofit.md) | [androidJvm]<br>@Singleton<br>@Provides<br>fun [provideBaseRetrofit](provide-base-retrofit.md)(httpClient: OkHttpClient, exceptionFactory: [ExceptionFactory](../../../../data/data/com.veles.purchase.data.networking.errorhandling/-exception-factory/index.md), gson: Gson): Retrofit |
| [provideExceptionFactory](provide-exception-factory.md) | [androidJvm]<br>@Singleton<br>@Provides<br>fun [provideExceptionFactory](provide-exception-factory.md)(): [ExceptionFactory](../../../../data/data/com.veles.purchase.data.networking.errorhandling/-exception-factory/index.md) |
| [provideHttpLoggingInterceptor](provide-http-logging-interceptor.md) | [androidJvm]<br>@Singleton<br>@Provides<br>fun [provideHttpLoggingInterceptor](provide-http-logging-interceptor.md)(logger: [Logger](../../../../domain/domain/com.veles.purchase.domain.core.loger/-logger/index.md)): HttpLoggingInterceptor |
| [provideMapperGson](provide-mapper-gson.md) | [androidJvm]<br>@Provides<br>@Singleton<br>fun [provideMapperGson](provide-mapper-gson.md)(): Gson |
| [provideNotificationMessageService](provide-notification-message-service.md) | [androidJvm]<br>@Singleton<br>@Provides<br>fun [provideNotificationMessageService](provide-notification-message-service.md)(retrofit: Retrofit): [NotificationMessageService](../../../../data/data/com.veles.purchase.data.networking.service.message/-notification-message-service/index.md) |
| [provideOkHttpClient](provide-ok-http-client.md) | [androidJvm]<br>@Singleton<br>@Provides<br>fun [provideOkHttpClient](provide-ok-http-client.md)(authInterceptor: [AuthInterceptor](../../../../data/data/com.veles.purchase.data.networking.interceptor/-auth-interceptor/index.md), httpLoggingInterceptor: HttpLoggingInterceptor, headersInterceptor: [HeadersInterceptor](../../../../data/data/com.veles.purchase.data.networking.interceptor/-headers-interceptor/index.md)): OkHttpClient |
