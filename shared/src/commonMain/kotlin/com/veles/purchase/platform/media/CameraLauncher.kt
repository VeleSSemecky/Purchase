package com.veles.purchase.platform.media

import androidx.compose.runtime.Composable

/**
 * Returns a launcher function that opens the device camera.
 * The [onResult] callback is called with the captured image as [ByteArray] (JPEG).
 * On iOS the launcher is a no-op if the device has no camera (e.g. simulator).
 */
@Composable
expect fun rememberCameraLauncher(onResult: (ByteArray) -> Unit): () -> Unit
