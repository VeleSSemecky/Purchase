package com.veles.purchase.data.network

import io.ktor.client.HttpClient

/**
 * Platform-specific HTTP client creation
 * Each platform provides its own optimized engine
 */
expect fun createHttpClient(): HttpClient
