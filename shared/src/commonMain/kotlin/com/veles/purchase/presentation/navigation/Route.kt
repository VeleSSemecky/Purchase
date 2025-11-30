package com.veles.purchase.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes using kotlinx-serialization
 *
 * This replaces the old Navigation Component SafeArgs approach
 * with a more Kotlin-idiomatic solution for KMP.
 */
@Serializable
sealed class Route {

    /**
     * Main/Home screen route
     */
    @Serializable
    data object Main : Route()

    /**
     * Collection routes
     */
    @Serializable
    sealed class Collection : Route() {
        @Serializable
        data object List : Collection()

        @Serializable
        data class Edit(val collectionId: String = "") : Collection()

        @Serializable
        data class Category(val collectionId: String) : Collection()

        @Serializable
        data class History(val collectionId: String) : Collection()
    }

    /**
     * Purchase routes
     */
    @Serializable
    sealed class Purchase : Route() {
        @Serializable
        data class List(val collectionId: String) : Purchase()

        @Serializable
        data class Detail(val purchaseId: String, val collectionId: String) : Purchase()

        @Serializable
        data class Edit(
            val purchaseId: String? = null,
            val collectionId: String
        ) : Purchase()

        @Serializable
        data class History(val collectionId: String) : Purchase()
    }

    /**
     * SKU routes
     */
    @Serializable
    sealed class Sku : Route() {
        @Serializable
        data object List : Sku()

        @Serializable
        data class Detail(val skuId: String) : Sku()

        @Serializable
        data class Edit(val skuId: String? = null) : Sku()

        @Serializable
        data class Statistics(val year: Int, val month: Int) : Sku()
    }

    /**
     * Settings routes
     */
    @Serializable
    sealed class Settings : Route() {
        @Serializable
        data object Main : Settings()

        @Serializable
        data object Purchase : Settings()

        @Serializable
        data object Appearance : Settings()
    }

    /**
     * Auth routes
     */
    @Serializable
    sealed class Auth : Route() {
        @Serializable
        data object Login : Auth()

        @Serializable
        data object Biometric : Auth()
    }
}


