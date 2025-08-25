package com.example.dscumi.biometrics

import java.io.File

fun submitToBlockchain(hash: String): Boolean {
    return try {
        val process = ProcessBuilder("node", "zk-verifier/submitFingerprint.js", hash)
            .redirectErrorStream(true)
            .directory(File("/data/data/com.example.dscumi/files")) // Use app-internal location or asset-extracted script
            .start()

        val output = process.inputStream.bufferedReader().readText()
        process.waitFor()

        output.contains("✅") || output.contains("success", ignoreCase = true)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
