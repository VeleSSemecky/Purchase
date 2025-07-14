package com.veles.purchase.presentation.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.login.LoginViewModel
import com.veles.purchase.presentation.presentation.mvvm.purchase.photo.PhotoPurchaseComposeViewModel

/**
 * Example Composable showing how to use Koin for ViewModel injection
 */
@Composable
fun LoginScreen() {
    // Get ViewModel from Koin - no need for ViewModelProvider
    val loginViewModel: LoginViewModel = koinViewModel()

    // Example UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Use ViewModel methods
                // loginViewModel.login()
            }
        ) {
            Text("Login")
        }
    }
}

/**
 * Example Composable for PhotoPurchase with Koin ViewModel injection
 */
@Composable
fun PhotoPurchaseScreen() {
    // Get ViewModel from Koin
    val photoViewModel: PhotoPurchaseComposeViewModel = koinViewModel()

    // Example UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Photo Purchase Screen",
            style = MaterialTheme.typography.headlineMedium
        )

        // Use ViewModel in your UI
        // val uiState by photoViewModel.uiState.collectAsState()
    }
}
