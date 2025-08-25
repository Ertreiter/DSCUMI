// app/src/main/java/com/example/dscumi/blockchain/ZkSyncClient.kt
package com.example.dscumi.blockchain

import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric

class ZkSyncClient(
    private val rpcUrl: String = "https://sepolia.era.zksync.dev",
    private val contractAddress: String
) {
    private val web3j: Web3j = Web3j.build(HttpService(rpcUrl))

    /**
     * Calls isRegistered(bytes32) -> bool
     * @param fingerprintHashHex 0x-prefixed 32-byte hex string
     */
    fun isRegistered(fingerprintHashHex: String): Boolean {
        val hashNo0x = Numeric.cleanHexPrefix(fingerprintHashHex)
        val bytes32 = Bytes32(Numeric.hexStringToByteArray(hashNo0x))
        val function = Function(
            "isRegistered",
            listOf(bytes32),
            listOf(object : TypeReference<Bool>() {})
        )
        val data = FunctionEncoder.encode(function)
        val callTx = Transaction.createEthCallTransaction(
            null, // from (not required)
            contractAddress,
            data
        )
        val response = web3j.ethCall(callTx, DefaultBlockParameterName.LATEST).send()
        val valueHex = response.value // 0x... boolean as 32 bytes
        // web3j can decode, but we can just check the last byte != 0
        val raw = Numeric.cleanHexPrefix(valueHex)
        if (raw.length < 64) return false
        val lastByte = raw.substring(raw.length - 2)
        return lastByte != "00"
    }

    /**
     * Optional: helper to ABI-encode register(bytes32) for WalletConnect later.
     */
    fun buildRegisterData(fingerprintHashHex: String): String {
        val hashNo0x = Numeric.cleanHexPrefix(fingerprintHashHex)
        val bytes32 = Bytes32(Numeric.hexStringToByteArray(hashNo0x))
        val function = Function(
            "register",
            listOf(bytes32),
            emptyList()
        )
        return FunctionEncoder.encode(function) // pass as "data" in eth_sendTransaction via WalletConnect
    }
}
