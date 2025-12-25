package com.veles.purchase.domain.model.cryptography

class CiphertextWrapper(
    val ciphertext: ByteArray,
    val initializationVector: ByteArray
) {
    companion object {
        val EMPTY = CiphertextWrapper(
            emptyList<Byte>().toByteArray(),
            emptyList<Byte>().toByteArray()
        )
    }
}
