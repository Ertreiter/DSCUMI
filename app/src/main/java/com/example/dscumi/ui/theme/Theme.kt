package com.example.dscumi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = Color.White,
    onBackground = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    background = LightBackground,
    surface = LightBackground,
    onPrimary = Color.White,
    onBackground = Color.Black
)

@Composable
fun DscumiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}