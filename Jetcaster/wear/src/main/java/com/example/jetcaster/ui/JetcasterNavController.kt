package com.example.jetcaster.ui

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.android.horologist.media.ui.material3.navigation.NavigationScreens

/**
 * NavController extensions that links to the screens of the Jetcaster app.
 */
public object JetcasterNavController {

    public fun NavController.navigateToYourPodcast() {
        navigate(YourPodcasts.destination())
    }

    public fun NavController.navigateToLatestEpisode() {
        navigate(LatestEpisodes.destination())
    }

    public fun NavController.navigateToPodcastDetails(podcastUri: String) {
        navigate(PodcastDetails.destination(podcastUri))
    }

    public fun NavController.navigateToUpNext() {
        navigate(UpNext.destination())
    }

    public fun NavController.navigateToEpisode(episodeUri: String) {
        navigate(Episode.destination(episodeUri))
    }
}

public object YourPodcasts : NavigationScreens("yourPodcasts") {
    public fun destination(): String = navRoute
}

public object LatestEpisodes : NavigationScreens("latestEpisodes") {
    public fun destination(): String = navRoute
}

public object PodcastDetails : NavigationScreens("podcast?podcastUri={podcastUri}") {
    public const val PODCAST_URI: String = "podcastUri"
    public fun destination(podcastUri: String): String {
        val encodedUri = Uri.encode(podcastUri)
        return "podcast?$PODCAST_URI=$encodedUri"
    }

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(PODCAST_URI) {
                type = NavType.StringType
            },
        )
}

public object Episode : NavigationScreens("episode?episodeUri={episodeUri}") {
    public const val EPISODE_URI: String = "episodeUri"
    public fun destination(episodeUri: String): String {
        val encodedUri = Uri.encode(episodeUri)
        return "episode?$EPISODE_URI=$encodedUri"
    }

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(EPISODE_URI) {
                type = NavType.StringType
            },
        )
}

public object UpNext : NavigationScreens("upNext") {
    public fun destination(): String = navRoute
}
