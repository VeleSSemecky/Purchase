package com.veles.purchase.presentation.compose.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veles.purchase.config.EnvironmentConfig
import com.veles.purchase.platform.auth.GoogleSignInHelper
import com.veles.purchase.platform.auth.createGoogleSignInHelper
import com.veles.purchase.presentation.compose.GoogleButton
import com.veles.purchase.presentation.viewmodel.login.LoginViewModel
import com.veles.purchase.shared.resources.Res
import com.veles.purchase.shared.resources.ic_backq
import com.veles.purchase.shared.resources.ic_purchase_collections
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

private val BrandGreen = Color(0xFF38A186)
private val CardBackground = Color(0xFF1C1C1C)
private val DarkOverlay = Color(0xCC111111)

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

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onLoginSuccess()
    }

    val bgImage = imageResource(Res.drawable.ic_backq)
    val bgBrush = remember(bgImage) {
        ShaderBrush(ImageShader(bgImage, TileMode.Repeated, TileMode.Repeated))
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ── 1. Tiled pattern background ────────────────────────────────────
        Box(modifier = Modifier.fillMaxSize().background(bgBrush))

        // ── 2. Dark overlay — improves readability over the pattern ─────────
        Box(modifier = Modifier.fillMaxSize().background(DarkOverlay))

        // ── 3. Screen content ───────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Top spacer pushes hero section to vertical center ────────────
            Spacer(modifier = Modifier.weight(1f))

            // ── App icon — brand green circle with shopping icon inside ──────
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .background(color = BrandGreen, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_purchase_collections),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── App name ─────────────────────────────────────────────────────
            Text(
                text = "Purchase",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-1).sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // ── Tagline ───────────────────────────────────────────────────────
            Text(
                text = "Manage shared expenses easily",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            // ── Bottom spacer before card ─────────────────────────────────────
            Spacer(modifier = Modifier.weight(1.5f))

            // ── Login card ────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = CardBackground,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Get started",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "Sign in to sync your list across all devices",
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                GoogleButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Continue with Google",
                    loadingText = "Signing in…",
                    isClicked = state.isLoading,
                    onClicked = {
                        googleSignInHelper?.let { helper ->
                            viewModel.signInWithGoogle { helper.signIn() }
                        } ?: viewModel.signInWithGoogle {
                            throw NotImplementedError("Google Sign-In not available on this platform")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
