package com.example.compose.jetchat.widget.theme

import androidx.glance.material3.ColorProviders
import com.example.compose.jetchat.theme.JetchatDarkColorScheme
import com.example.compose.jetchat.theme.JetchatLightColorScheme

object JetchatGlanceColorScheme {
    val colors = ColorProviders(
        light = JetchatLightColorScheme,
        dark = JetchatDarkColorScheme,
    )
}
