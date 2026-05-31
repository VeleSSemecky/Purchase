package com.veles.purchase.domain.model.scanner

/**
 * In-memory store for the image bytes captured during price tag scanning.
 * Scanner ViewModel writes, Edit ViewModel reads-and-clears once.
 */
class PendingPhotoStore {
    var pendingBytes: ByteArray? = null

    fun consume(): ByteArray? {
        val bytes = pendingBytes
        pendingBytes = null
        return bytes
    }
}
