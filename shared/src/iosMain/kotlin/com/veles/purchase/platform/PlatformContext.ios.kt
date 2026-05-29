package com.veles.purchase.platform

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice

/**
 * iOS implementation of PlatformContext
 * Using NSBundle for resource access
 */
actual class PlatformContext

/**
 * Get string resource by key from iOS Bundle
 */
actual fun PlatformContext.getString(key: String): String = NSBundle.mainBundle.localizedStringForKey(key, key, null)

/**
 * Get string resource with arguments (iOS doesn't have direct equivalent)
 */
actual fun PlatformContext.getString(key: String, vararg args: Any): String {
    val format = NSBundle.mainBundle.localizedStringForKey(key, key, null)
    // TODO: Implement string formatting for iOS
    return format
}

/**
 * iOS platform information
 */
actual object Platform {
    actual val name: String = "iOS"
    actual val version: String = UIDevice.currentDevice.systemVersion
}
