package com.example.dscumi.biometrics

import java.security.MessageDigest

object FingerprintUtil {
    fun hashFingerprint(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
