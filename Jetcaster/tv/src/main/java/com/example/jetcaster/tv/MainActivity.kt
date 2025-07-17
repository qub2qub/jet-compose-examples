package com.example.jetcaster.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.Surface
import com.example.jetcaster.tv.ui.JetcasterApp
import com.example.jetcaster.tv.ui.theme.JetcasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TV is hardcoded to dark mode to match TV ui
            JetcasterTheme(isInDarkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape,
                ) {
                    JetcasterApp()
                }
            }
        }
    }
}
