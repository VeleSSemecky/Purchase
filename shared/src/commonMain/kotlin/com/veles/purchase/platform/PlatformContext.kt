package com.veles.purchase.platform

/**
 * Platform context abstraction for accessing platform-specific resources
 * and functionality without direct Android/iOS dependencies in common code.
 */
expect class PlatformContext

/**
 * Get string resource by key
 */
expect fun PlatformContext.getString(key: String): String

/**
 * Get string resource with arguments
 */
expect fun PlatformContext.getString(key: String, vararg args: Any): String

/**
 * Platform information
 */
expect object Platform {
    val name: String
    val version: String
}

/**
 * Check if running on Android
 */
fun Platform.isAndroid(): Boolean = name == "Android"

/**
 * Check if running on iOS
 */
fun Platform.isIOS(): Boolean = name == "iOS"


