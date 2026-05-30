package com.veles.purchase.data.util

actual fun sha1Hex(input: String): String {
    val digest = java.security.MessageDigest.getInstance("SHA-1")
    val bytes = digest.digest(input.toByteArray(Charsets.UTF_8))
    return bytes.joinToString("") { "%02x".format(it) }
}
