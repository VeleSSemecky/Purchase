package com.veles.purchase.platform

import android.content.Context

/**
 * Android implementation of PlatformContext
 */
actual class PlatformContext(val context: Context)

/**
 * Get string resource by key (using resource identifier)
 */
actual fun PlatformContext.getString(key: String): String {
    val resourceId = context.resources.getIdentifier(key, "string", context.packageName)
    return if (resourceId != 0) {
        context.getString(resourceId)
    } else {
        key // Return key if resource not found
    }
}

/**
 * Get string resource with arguments
 */
actual fun PlatformContext.getString(key: String, vararg args: Any): String {
    val resourceId = context.resources.getIdentifier(key, "string", context.packageName)
    return if (resourceId != 0) {
        context.getString(resourceId, *args)
    } else {
        key
    }
}

/**
 * Android platform information
 */
actual object Platform {
    actual val name: String = "Android"
    actual val version: String = android.os.Build.VERSION.RELEASE
}
