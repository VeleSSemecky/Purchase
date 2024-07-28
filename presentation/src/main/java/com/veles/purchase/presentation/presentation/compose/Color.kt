package com.veles.purchase.presentation.presentation.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

object Colors {
    val colorPrimary = Color(0xff212121)
    val colorPrimaryDark = Color(0xff303030)
    val colorAccent = Color(0xff424242)
    val gr = Color(0xff4ACFAC)
    val surface = Color(0xFF121212)
    val progress = Color(0x99000000)
}

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    androidx.compose.material3.MaterialTheme(
//        colors = if (darkTheme) DarkColors else LightColors,
        colorScheme = darkColorScheme().copy(
            primary = Colors.colorPrimary,
            surface = Colors.surface,
            primaryContainer = Colors.gr
        ),
        content = content
    )
}

fun textStyle() = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
fun textStyle1() = TextStyle(color = Color.White)

//@Composable
//fun textFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
//    textColor = Color.White,
//    focusedBorderColor = Colors.gr,
//    unfocusedBorderColor = Color.White.copy(alpha = ContentAlpha.disabled),
//    cursorColor = Colors.colorAccent
//)

@Composable
fun textFieldColorsMaterial3() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Colors.gr,
    unfocusedBorderColor = Color.White.copy(alpha = 0.38f),
    cursorColor = Colors.colorAccent
)

//private val DarkColors = darkColors(
//    primary = Colors.colorPrimary
//)
//private val LightColors = lightColors(
//    primary = Colors.colorPrimary
//)
