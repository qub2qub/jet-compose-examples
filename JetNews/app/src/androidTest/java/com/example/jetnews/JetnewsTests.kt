package com.example.jetnews

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jetnews.data.posts.impl.manuel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class JetnewsTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        // Using targetContext as the Context of the instrumentation code
        composeTestRule.launchJetNewsApp(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun app_launches() {
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.home_top_section_title))
            .assertExists()
    }

    @Test
    fun app_opensArticle() {

        println(composeTestRule.onRoot().printToString())
        composeTestRule.onAllNodes(hasText(manuel.name, substring = true))[0].performClick()

        println(composeTestRule.onRoot().printToString())
        try {
            composeTestRule.onAllNodes(hasText("3 min read", substring = true))[0].assertExists()
        } catch (e: AssertionError) {
            println(composeTestRule.onRoot().printToString())
            throw e
        }
    }

    @Test
    fun app_opensInterests() {
        composeTestRule.onNodeWithContentDescription(
            label = "Open navigation drawer",
            useUnmergedTree = true,
        ).performClick()
        composeTestRule.onNodeWithText("Interests").performClick()
        composeTestRule.waitUntilAtLeastOneExists(hasText("Topics"), 5000L)
    }
}
