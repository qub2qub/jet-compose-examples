package com.example.jetnews

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.jetnews.ui.home.HomeFeedScreen
import com.example.jetnews.ui.home.HomeUiState
import com.example.jetnews.ui.theme.JetnewsTheme
import com.example.jetnews.utils.ErrorMessage
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Checks that the Snackbar is shown when the HomeScreen data contains an error.
     */
    @Test
    fun postsContainError_snackbarShown() {
        val snackbarHostState = SnackbarHostState()
        composeTestRule.setContent {
            JetnewsTheme {

                // When the Home screen receives data with an error
                HomeFeedScreen(
                    uiState = HomeUiState.NoPosts(
                        isLoading = false,
                        errorMessages = listOf(ErrorMessage(0L, R.string.load_error)),
                        searchInput = "",
                    ),
                    showTopAppBar = false,
                    onToggleFavorite = {},
                    onSelectPost = {},
                    onRefreshPosts = {},
                    onErrorDismiss = {},
                    openDrawer = {},
                    homeListLazyListState = rememberLazyListState(),
                    snackbarHostState = snackbarHostState,
                    onSearchInputChanged = {},
                )
            }
        }

        // Then the first message received in the Snackbar is an error message
        runBlocking {
            // snapshotFlow converts a State to a Kotlin Flow so we can observe it
            // wait for the first a non-null `currentSnackbarData`
            val actualSnackbarText = snapshotFlow { snackbarHostState.currentSnackbarData }
                .filterNotNull().first().visuals.message
            val expectedSnackbarText = InstrumentationRegistry.getInstrumentation()
                .targetContext.resources.getString(R.string.load_error)
            assertEquals(expectedSnackbarText, actualSnackbarText)
        }
    }
}
