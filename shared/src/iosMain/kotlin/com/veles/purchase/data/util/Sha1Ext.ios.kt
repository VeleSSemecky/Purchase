package com.veles.purchase.data.util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_SHA1
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH

@OptIn(ExperimentalForeignApi::class)
actual fun sha1Hex(input: String): String {
    val inputBytes = input.encodeToByteArray()
    val result = UByteArray(CC_SHA1_DIGEST_LENGTH.toInt())
    inputBytes.usePinned { inputPinned ->
        result.usePinned { resultPinned ->
            CC_SHA1(inputPinned.addressOf(0), inputBytes.size.toUInt(), resultPinned.addressOf(0))
        }
    }
    return result.joinToString("") { it.toString(16).padStart(2, '0') }
}
