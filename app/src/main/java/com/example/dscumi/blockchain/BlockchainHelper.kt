package com.example.dscumi.blockchain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object BlockchainHelper {
    private const val RPC_URL = "https://sepolia.era.zksync.dev"
    private const val CONTRACT_ADDRESS = "0x6A03438B55D2fF39Ac07798F8603ff90F621d46a"

    // ✅ Free view call → verifyFingerprint
    suspend fun verifyFingerprint(hash: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // selector for verifyFingerprint(bytes32) = keccak256("verifyFingerprint(bytes32)")[:4]
                val methodId = "0x1f4cc4c7"
                val paddedHash = hash.removePrefix("0x").padStart(64, '0')
                val data = methodId + paddedHash

                val payload = """
                    {
                      "jsonrpc":"2.0",
                      "id":1,
                      "method":"eth_call",
                      "params":[
                        {
                          "to":"$CONTRACT_ADDRESS",
                          "data":"$data"
                        },
                        "latest"
                      ]
                    }
                """.trimIndent()

                val conn = (URL(RPC_URL).openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    doOutput = true
                    setRequestProperty("Content-Type", "application/json")
                    outputStream.write(payload.toByteArray())
                }

                val response = conn.inputStream.bufferedReader().readText()
                Log.d("BlockchainHelper", "Response: $response")

                val resultHex = JSONObject(response).getString("result")
                // "0x1" = true, "0x0" = false
                resultHex.lowercase() == "0x1"
            } catch (e: Exception) {
                Log.e("BlockchainHelper", "Error checking fingerprint", e)
                false
            }
        }
    }
}
