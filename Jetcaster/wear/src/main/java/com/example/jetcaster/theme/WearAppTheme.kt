package com.example.jetcaster.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun WearAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = wearColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
