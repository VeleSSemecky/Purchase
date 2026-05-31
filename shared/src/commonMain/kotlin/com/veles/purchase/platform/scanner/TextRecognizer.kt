package com.veles.purchase.platform.scanner

interface TextRecognizer {
    suspend fun recognizeText(imageBytes: ByteArray): List<String>
}
