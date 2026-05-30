package com.veles.purchase.platform.media

import androidx.compose.runtime.Composable

/**
 * Returns a launcher function that opens a platform-specific media picker.
 * The [onResult] callback is called with the selected image as [ByteArray].
 * Multiple images can be selected (callback is called once per image).
 */
@Composable
expect fun rememberMediaPickerLauncher(onResult: (ByteArray) -> Unit): () -> Unit
