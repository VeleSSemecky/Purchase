package com.veles.purchase.domain.model.scanner

/**
 * In-memory hand-off store for a scanned receipt.
 *
 * The receipt scanner writes the recognised [ReceiptData] (grand total + line
 * items) plus the raw [imageBytes] of the scanned photo.
 * The SKU edit screen reads-and-clears both in one [consume] call.
 */
class PendingReceiptStore {
    var pendingReceipt: ReceiptData? = null
    /** Raw JPEG bytes of the scanned receipt image. */
    var pendingImageBytes: ByteArray? = null

    data class Payload(val receipt: ReceiptData, val imageBytes: ByteArray?)

    fun consume(): Payload? {
        val receipt = pendingReceipt ?: return null
        val img = pendingImageBytes
        pendingReceipt = null
        pendingImageBytes = null
        return Payload(receipt, img)
    }
}

