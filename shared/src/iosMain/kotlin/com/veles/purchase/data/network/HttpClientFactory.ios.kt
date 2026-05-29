package com.veles.purchase.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * iOS implementation using Darwin engine (NSURLSession)
 */
actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
    // JSON serialization
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    // Logging
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.INFO
    }

    // Engine configuration
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
