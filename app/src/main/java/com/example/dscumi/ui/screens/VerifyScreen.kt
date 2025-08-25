// app/src/main/java/com/example/dscumi/ui/screens/VerifyScreen.kt
package com.example.dscumi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.dscumi.biometrics.VerificationStore
import com.example.dscumi.biometrics.showBiometricPrompt
import com.example.dscumi.biometrics.submitToBlockchain
import com.example.dscumi.blockchain.ZkSyncClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun VerifyScreen() {
    val context = LocalContext.current
    val verificationStore = remember { VerificationStore(context) }
    val activity = context as? FragmentActivity
    val coroutineScope = rememberCoroutineScope() // ✅ FIX

    var status by remember { mutableStateOf("Please choose an action") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = status, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Register Button
            Button(onClick = {
                if (activity != null) {
                    showBiometricPrompt(
                        activity = activity,
                        title = "Register Fingerprint",
                        onSuccess = {
                            val hash = verificationStore.registerFingerprint()
                            coroutineScope.launch { // ✅ coroutine needed
                                val success = submitToBlockchain(hash)
                                status = if (success) {
                                    "✅ Registered & stored on blockchain!"
                                } else {
                                    "❌ Failed to submit to blockchain"
                                }
                            }
                        },
                        onError = { err -> status = "Error: $err" }
                    )
                } else {
                    status = "Biometric only works in real device/activity"
                }
            }) {
                Text("Register")
            }

            // Verify Button
            Button(onClick = {
                if (activity != null) {
                    showBiometricPrompt(
                        activity = activity,
                        title = "Verify Fingerprint",
                        onSuccess = {
                            coroutineScope.launch { // ✅ coroutine needed
                                val success = verificationStore.verifyFingerprint()
                                status = if (success) {
                                    "✅ Verified (hash exists on blockchain)"
                                } else {
                                    "❌ Verification failed"
                                }
                            }
                        },
                        onError = { err -> status = "Error: $err" }
                    )
                } else {
                    status = "Biometric only works in real device/activity"
                }
            }) {
                Text("Verify")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewVerifyScreen() {
    VerifyScreen()
}
