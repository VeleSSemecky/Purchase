package com.veles.purchase.data.storage

import dev.gitlive.firebase.storage.Data

expect fun ByteArray.toStorageData(): Data

/**
 * Detects MIME type from image magic bytes.
 * Supports JPEG, PNG, WebP, GIF. Falls back to "image/jpeg".
 */
fun ByteArray.detectImageMimeType(): String = when {
    size >= 3
        && this[0] == 0xFF.toByte()
        && this[1] == 0xD8.toByte()
        && this[2] == 0xFF.toByte() -> "image/jpeg"

    size >= 4
        && this[0] == 0x89.toByte()
        && this[1] == 0x50.toByte() // P
        && this[2] == 0x4E.toByte() // N
        && this[3] == 0x47.toByte() // G
        -> "image/png"

    size >= 12
        && this[0] == 0x52.toByte() // R
        && this[1] == 0x49.toByte() // I
        && this[2] == 0x46.toByte() // F
        && this[3] == 0x46.toByte() // F
        && this[8] == 0x57.toByte() // W
        && this[9] == 0x45.toByte() // E
        && this[10] == 0x42.toByte() // B
        && this[11] == 0x50.toByte() // P
        -> "image/webp"

    size >= 6
        && this[0] == 0x47.toByte() // G
        && this[1] == 0x49.toByte() // I
        && this[2] == 0x46.toByte() // F
        -> "image/gif"

    else -> "image/jpeg"
}
