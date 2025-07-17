package com.example.jetlagged

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.jetlagged.ui.theme.JetLaggedTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetLaggedTheme {
                JetLaggedScreen()
            }
        }
    }

    @Test
    fun app_launches() {
        // Check app launches at the correct destination
        composeTestRule.onNodeWithText("JetLagged").assertIsDisplayed()
    }
}
