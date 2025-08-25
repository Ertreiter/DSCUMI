package com.example.dscumi.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun canAuthenticate(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
    ) == BiometricManager.BIOMETRIC_SUCCESS
}

fun showBiometricPrompt(
    activity: FragmentActivity,
    title: String = "Verify Fingerprint",
    subtitle: String = "",
    description: String = "",
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(activity)
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subtitle)
        .setDescription(description)
        .setNegativeButtonText("Cancel")
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errString.toString())
            }
        })

    biometricPrompt.authenticate(promptInfo)
}
