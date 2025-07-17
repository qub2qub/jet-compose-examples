package com.example.jetnews

import android.content.Context
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.example.jetnews.ui.JetnewsApp

/**
 * Launches the app from a test context
 */
fun ComposeContentTestRule.launchJetNewsApp(context: Context) {
    setContent {
        JetnewsApp(
            appContainer = TestAppContainer(context),
            widthSizeClass = WindowWidthSizeClass.Compact,
        )
    }
}
