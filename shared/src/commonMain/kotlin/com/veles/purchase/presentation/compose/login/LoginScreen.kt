package com.veles.purchase.presentation.compose.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.platform.auth.GoogleSignInHelper
import com.veles.purchase.platform.auth.createGoogleSignInHelper
import com.veles.purchase.presentation.compose.GoogleButton
import com.veles.purchase.presentation.compose.Colors
import com.veles.purchase.presentation.compose.textStyle1
import com.veles.purchase.presentation.viewmodel.login.LoginViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_backq
import org.koin.compose.koinInject

/**
 * Login Screen for KMP
 * Handles Google Sign-In authentication
 *
 * @param activity Platform-specific activity (Android Activity, iOS ViewController, etc.)
 * @param serverClientId Google OAuth client ID
 */
@Composable
fun LoginScreen(
    activity: Any?,
    serverClientId: String = EnvironmentConfig.SERVER_CLIENT_ID,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()
    val googleSignInHelper: GoogleSignInHelper? = remember(activity, serverClientId) {
        createGoogleSignInHelper(activity, serverClientId)
    }
//    ConstraintLayout()
    // Navigate on success
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }
    val image = imageResource(Res.drawable.ic_backq)
    val brush = remember(image) {
        ShaderBrush(
            ImageShader(
                image,
                TileMode.Repeated,
                TileMode.Repeated
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Colors.progress,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 32.dp, vertical = 12.dp)
            ) {
                Text(
                    style = textStyle1(),
                    text = "PurchaseApp",
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                modifier = Modifier.size(96.dp),
                painter = painterResource(Res.drawable.ic_backq),
                contentDescription = "Is Image"
            )
            Spacer(modifier = Modifier.height(32.dp))
            GoogleButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign Up with Google",
                loadingText = "Creating Account...",
                isClicked = state.isLoading,
                onClicked = {
                    googleSignInHelper?.let { helper ->
                        viewModel.signInWithGoogle { helper.signIn() }
                    } ?: run {
                        viewModel.signInWithGoogle {
                            throw NotImplementedError("Google Sign-In not available on this platform")
                        }
                    }
                }
            )
        }
    }
}
