package com.example.jetcaster.tv.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetcaster.core.player.model.PlayerEpisode
import kotlinx.coroutines.flow.map

class JetcasterAppState(val navHostController: NavHostController) {

    val currentRouteFlow = navHostController.currentBackStackEntryFlow.map {
        it.destination.route
    }

    private fun navigate(screen: Screen) {
        navHostController.navigate(screen.route)
    }

    fun navigateToDiscover() {
        navigate(Screen.Discover)
    }

    fun navigateToLibrary() {
        navigate(Screen.Library)
    }

    fun navigateToProfile() {
        navigate(Screen.Profile)
    }

    fun navigateToSearch() {
        navigate(Screen.Search)
    }

    fun navigateToSettings() {
        navigate(Screen.Settings)
    }

    fun showPodcastDetails(podcastUri: String) {
        val encodedUrL = Uri.encode(podcastUri)
        val screen = Screen.Podcast(encodedUrL)
        navigate(screen)
    }

    fun showEpisodeDetails(episodeUri: String) {
        val encodeUrl = Uri.encode(episodeUri)
        val screen = Screen.Episode(encodeUrl)
        navigate(screen)
    }

    fun showEpisodeDetails(playerEpisode: PlayerEpisode) {
        showEpisodeDetails(playerEpisode.uri)
    }

    fun playEpisode() {
        navigate(Screen.Player)
    }

    fun backToHome() {
        navHostController.popBackStack()
        navigateToDiscover()
    }
}

@Composable
fun rememberJetcasterAppState(navHostController: NavHostController = rememberNavController()) = remember(navHostController) {
    JetcasterAppState(navHostController)
}

sealed interface Screen {
    val route: String

    data object Discover : Screen {
        override val route = "/discover"
    }

    data object Library : Screen {
        override val route = "/library"
    }

    data object Search : Screen {
        override val route = "/search"
    }

    data object Profile : Screen {
        override val route = "/profile"
    }

    data object Settings : Screen {
        override val route: String = "settings"
    }

    data class Podcast(private val podcastUri: String) : Screen {
        override val route = "$ROOT/$podcastUri"

        companion object : Screen {
            private const val ROOT = "/podcast"
            const val PARAMETER_NAME = "podcastUri"
            override val route = "$ROOT/{$PARAMETER_NAME}"
        }
    }

    data class Episode(private val episodeUri: String) : Screen {

        override val route: String = "$ROOT/$episodeUri"

        companion object : Screen {
            private const val ROOT = "/episode"
            const val PARAMETER_NAME = "episodeUri"
            override val route = "$ROOT/{$PARAMETER_NAME}"
        }
    }

    data object Player : Screen {
        override val route = "player"
    }
}
