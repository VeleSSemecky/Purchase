//[presentation](../../../index.md)/[com.veles.purchase.presentation.di.module](../index.md)/[NetworkModule](index.md)/[provideOkHttpClient](provide-ok-http-client.md)

# provideOkHttpClient

[androidJvm]\

@Singleton

@Provides

fun [provideOkHttpClient](provide-ok-http-client.md)(authInterceptor: [AuthInterceptor](../../../../data/data/com.veles.purchase.data.networking.interceptor/-auth-interceptor/index.md), httpLoggingInterceptor: HttpLoggingInterceptor, headersInterceptor: [HeadersInterceptor](../../../../data/data/com.veles.purchase.data.networking.interceptor/-headers-interceptor/index.md)): OkHttpClient
