package com.example.dscumi.blockchain

// Placeholder for ZKP data
typealias ZkpProof = ByteArray
typealias FingerprintHash = ByteArray

interface BlockchainService {
    /**
     * Registers a new fingerprint hash on the blockchain.
     * This would typically involve a ZKP to prove ownership/validity without revealing raw data.
     * @param hash The fingerprint hash to register.
     * @param proof The ZKP proving the validity of the hash.
     * @return True if registration was successful, false otherwise.
     */
    suspend fun registerFingerprint(hash: FingerprintHash, proof: ZkpProof): Boolean

    /**
     * Verifies a fingerprint against the blockchain.
     * This might involve querying the blockchain and/or using ZKPs for verification.
     * @param hash The fingerprint hash to verify.
     * @param proof The ZKP for the verification process.
     * @return True if the fingerprint is verified, false otherwise.
     */
    suspend fun verifyFingerprint(hash: FingerprintHash, proof: ZkpProof): Boolean
}

