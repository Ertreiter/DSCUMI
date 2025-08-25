// app/src/main/java/com/example/dscumi/biometrics/VerificationStore.kt
package com.example.dscumi.biometrics

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest
import java.util.UUID

val Context.dataStore by preferencesDataStore(name = "verification_store")

object VerificationKeys {
    val FINGERPRINT_HASH = stringPreferencesKey("fingerprint_hash")
}

class VerificationStore(private val context: Context) {

    private fun generatePseudoFingerprint(): String {
        // TODO: replace with real template derivation later
        return UUID.randomUUID().toString()
    }

    private fun sha256Hex(text: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(text.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    /** The hash we store locally (hex string without 0x) */
    private fun fuzzyExtract(template: String): String = sha256Hex(template)

    /** Register locally and return the on-chain bytes32 (0x + 32 bytes) */
    fun registerFingerprint(): String {
        val template = generatePseudoFingerprint()
        val hex = fuzzyExtract(template) // 64 hex chars
        val bytes32Hex = "0x$hex" // already 32 bytes because SHA-256
        runBlocking {
            context.dataStore.edit { it[VerificationKeys.FINGERPRINT_HASH] = hex }
        }
        return bytes32Hex
    }

    fun getStoredHashBytes32(): String? {
        val stored = runBlocking { context.dataStore.data.first()[VerificationKeys.FINGERPRINT_HASH] }
        return stored?.let { "0x$it" }
    }

    fun isRegisteredLocally(): Boolean {
        val stored = runBlocking { context.dataStore.data.first()[VerificationKeys.FINGERPRINT_HASH] }
        return stored != null
    }

    /** Local “did the user just authenticate” — your existing flow */
    fun verifyFingerprint(): Boolean {
        val stored = runBlocking { context.dataStore.data.first()[VerificationKeys.FINGERPRINT_HASH] }
        return !stored.isNullOrEmpty()
    }
}
