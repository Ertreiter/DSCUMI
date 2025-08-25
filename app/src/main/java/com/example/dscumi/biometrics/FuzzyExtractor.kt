package com.example.dscumi.biometrics

// A generic representation for fingerprint data (e.g., could be a ByteArray of features)
typealias FingerprintData = ByteArray
// Represents the secret key derived from the fingerprint
typealias SecretKey = ByteArray
// Represents the public helper data used to reconstruct the key
typealias HelperData = ByteArray

interface FuzzyExtractor {
    /**
     * Generates a secret key and public helper data from the given fingerprint data.
     * This is typically done during an enrollment phase.
     *
     * @param fingerprintInput The processed fingerprint data.
     * @return A Pair containing the generated SecretKey and HelperData, or null if generation fails.
     */
    fun generate(fingerprintInput: FingerprintData): Pair<SecretKey, HelperData>?

    /**
     * Attempts to reconstruct the secret key from a new fingerprint reading and the stored helper data.
     * This is typically done during a verification phase.
     *
     * @param fingerprintInput The new processed fingerprint data.
     * @param helperData The public helper data generated during enrollment.
     * @return The reconstructed SecretKey if successful, or null if reconstruction fails (e.g., fingerprints don't match sufficiently).
     */
    fun reconstruct(fingerprintInput: FingerprintData, helperData: HelperData): SecretKey?
}
