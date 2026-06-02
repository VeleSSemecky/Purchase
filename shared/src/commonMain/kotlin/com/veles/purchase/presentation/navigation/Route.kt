package com.veles.purchase.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes using kotlinx-serialization
 *
 * This replaces the old Navigation Component SafeArgs approach
 * with a more Kotlin-idiomatic solution for KMP.
 */
@Serializable
sealed class Route : NavKey {

    /**
     * Login screen route
     */
    @Serializable
    data object Login : Route()

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

        @Serializable
        data class Members(val collectionId: String, val selectedIds: kotlin.collections.List<String>) : Collection()
    }

    /**
     * Purchase routes
     */
    @Serializable
    sealed class Purchase : Route() {
        @Serializable
        data class List(val collectionId: String) : Purchase()

        @Serializable
        data class Edit(val purchaseId: String? = null, val collectionId: String, val prefillName: String? = null, val prefillPrice: String? = null) : Purchase()

        @Serializable
        data class PhotoViewer(val photoUri: String) : Purchase()

        @Serializable
        data class Scanner(val collectionId: String) : Purchase()

        @Serializable
        data class History(val collectionId: String) : Purchase()

        @Serializable
        data class Later(val collectionId: String) : Purchase()
    }

    /**
     * SKU routes
     */
    @Serializable
    sealed class Sku : Route() {
        @Serializable
        data object List : Sku()

        @Serializable
        data class Edit(
            val skuId: String? = null,
            val prefillName: String? = null,
            val prefillPrice: String? = null,
            val prefillCategory: String? = null
        ) : Sku()

        @Serializable
        data object Statistics : Sku()

        @Serializable
        data object Scanner : Sku()
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
        data object Biometric : Auth()
    }
}
