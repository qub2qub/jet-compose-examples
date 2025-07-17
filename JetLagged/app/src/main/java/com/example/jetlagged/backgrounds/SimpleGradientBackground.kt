package com.example.jetlagged.backgrounds

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import com.example.jetlagged.ui.theme.White
import com.example.jetlagged.ui.theme.Yellow
import com.example.jetlagged.ui.theme.YellowVariant

fun Modifier.simpleGradient(): Modifier = drawWithCache {
    val gradientBrush = Brush.verticalGradient(listOf(Yellow, YellowVariant, White))
    onDrawBehind {
        drawRect(gradientBrush, alpha = 1f)
    }
}
