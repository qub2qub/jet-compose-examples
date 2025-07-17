package com.example.jetcaster.tv.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.tv.material3.MaterialTheme

@Composable
fun JetcasterTheme(isInDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (isInDarkTheme) {
        colorSchemeForDarkMode
    } else {
        colorSchemeForLightMode
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
