package com.veles.purchase.presentation.compose.purchase.biometric

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.platform.biometric.BiometricAuthenticator
import com.veles.purchase.platform.biometric.BiometricResult
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.mvvm.purchase.biometric.BiometricViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Biometric Authentication Screen
 *
 * Migrated from: BiometricComposeFragment.kt
 *
 * Features:
 * - Check biometric availability
 * - Trigger biometric authentication
 * - Show authentication result
 * - Status indicators
 *
 * Phase 2.11 - Biometric screen migration
 * FIXED: Now using custom components matching pattern exactly
 */

// Biometric-specific error color
private val ErrorRed = Color(0xFFE53935)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricScreen(
    biometricAuthenticator: BiometricAuthenticator,
    onNavigateBack: () -> Unit = {},
    viewModel: BiometricViewModel = koinViewModel(
        parameters = { parametersOf(biometricAuthenticator) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            BiometricToolbar(onNavigateBack = onNavigateBack)
        },
        containerColor = Colors.surface
    ) { paddingValues ->
        BiometricContent(
            paddingValues = paddingValues,
            uiState = uiState,
            onAuthenticateClick = { viewModel.authenticate() },
            onDismissDialog = { viewModel.dismissResultDialog() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BiometricToolbar(onNavigateBack: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "Biometric Authentication",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.colorPrimary
        )
    )
}

@Composable
private fun BiometricContent(
    paddingValues: PaddingValues,
    uiState: com.veles.purchase.presentation.mvvm.purchase.biometric.BiometricUiState,
    onAuthenticateClick: () -> Unit,
    onDismissDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Fingerprint Emoji
        Text(
            text = "🔐",
            fontSize = 96.sp,
            color = if (uiState.isBiometricAvailable) Colors.gr else Color.Gray
        )

        // Title
        Text(
            text = "Biometric Login",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // Status Cards
        StatusCard(
            title = "Biometric Available",
            status = uiState.isBiometricAvailable,
            description = if (uiState.isBiometricAvailable)
                "Device supports biometric authentication"
            else
                "Biometric hardware not available"
        )

        StatusCard(
            title = "Biometric Enrolled",
            status = uiState.isBiometricEnrolled,
            description = if (uiState.isBiometricEnrolled)
                "Fingerprint/Face ID is registered"
            else
                "No biometric data enrolled"
        )

        Spacer(modifier = Modifier.weight(1f))

        // Authenticate Button
        Button(
            onClick = onAuthenticateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = uiState.isBiometricAvailable && uiState.isBiometricEnrolled && !uiState.isAuthenticating,
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.gr,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (uiState.isAuthenticating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Authenticate",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Result Dialog
    if (uiState.showResultDialog && uiState.authenticationResult != null) {
        AuthenticationResultDialog(
            result = uiState.authenticationResult,
            onDismiss = onDismissDialog
        )
    }
}

@Composable
private fun StatusCard(
    title: String,
    status: Boolean,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Colors.colorAccent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status Indicator
            Box(
                modifier = Modifier
                    .size(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(12.dp),
                    shape = RoundedCornerShape(6.dp),
                    color = if (status) Colors.gr else ErrorRed
                ) {}
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun AuthenticationResultDialog(
    result: BiometricResult,
    onDismiss: () -> Unit
) {
    val (title, message, isSuccess) = when (result) {
        is BiometricResult.Success -> Triple(
            "Authentication Successful",
            "You have been authenticated successfully!",
            true
        )
        is BiometricResult.Error -> Triple(
            "Authentication Failed",
            "Error: ${result.message}",
            false
        )
        is BiometricResult.Cancelled -> Triple(
            "Authentication Cancelled",
            "You cancelled the authentication process.",
            false
        )
        is BiometricResult.NotAvailable -> Triple(
            "Not Available",
            "Biometric authentication is not available on this device.",
            false
        )
        is BiometricResult.NotEnrolled -> Triple(
            "Not Enrolled",
            "No biometric data is enrolled. Please register your fingerprint or face in device settings.",
            false
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = if (isSuccess) Colors.gr else ErrorRed
            )
        },
        text = {
            Text(
                text = message,
                color = Color.White
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    color = Colors.gr
                )
            }
        },
        containerColor = Colors.colorAccent
    )
}