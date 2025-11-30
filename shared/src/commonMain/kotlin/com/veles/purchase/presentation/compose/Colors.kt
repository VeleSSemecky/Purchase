package com.veles.purchase.presentation.compose

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * Centralized Colors object for the app
 *
 * Migrated from: /presentation/presentation/compose/Color.kt
 *
 * These are the original app colors used throughout all screens.
 */
object Colors {
    val colorPrimary = Color(0xff212121)
    val colorPrimaryDark = Color(0xff303030)
    val colorAccent = Color(0xff424242)
    val gr = Color(0xff4ACFAC)  // Green accent color
    val surface = Color(0xFF121212)
    val progress = Color(0x99000000)
}

/**
 * Text style functions matching original design
 */
fun textStyle() = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
fun textStyle1() = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
fun textStyle2() = TextStyle(color = Color.White.copy(alpha = 0.6f))

@Composable
fun MyTheme(
    darkTheme: Boolean = true,  // Always dark theme for now
    content: @Composable () -> Unit
) {
    androidx.compose.material3.MaterialTheme(
        colorScheme = darkColorScheme().copy(
            primary = Colors.colorPrimary,
            surface = Colors.surface,
            primaryContainer = Colors.gr
        ),
        content = content
    )
}

@Composable
fun textFieldColorsMaterial3() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Colors.gr,
    unfocusedBorderColor = Color.White.copy(alpha = 0.38f),
    cursorColor = Colors.colorAccent
)