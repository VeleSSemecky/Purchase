package com.veles.purchase.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Android implementation using OkHttp engine
 */
actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        // JSON serialization
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        // Logging
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

        // Engine configuration
        engine {
            config {
                followRedirects(true)
            }
        }
    }
}

