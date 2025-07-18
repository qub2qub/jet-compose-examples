package com.example.jetcaster.tv.ui

import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Text
import com.example.jetcaster.tv.R
import com.example.jetcaster.tv.ui.discover.DiscoverScreen
import com.example.jetcaster.tv.ui.episode.EpisodeScreen
import com.example.jetcaster.tv.ui.library.LibraryScreen
import com.example.jetcaster.tv.ui.player.PlayerScreen
import com.example.jetcaster.tv.ui.podcast.PodcastDetailsScreen
import com.example.jetcaster.tv.ui.profile.ProfileScreen
import com.example.jetcaster.tv.ui.search.SearchScreen
import com.example.jetcaster.tv.ui.settings.SettingsScreen
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
fun JetcasterApp(jetcasterAppState: JetcasterAppState = rememberJetcasterAppState()) {
    Route(jetcasterAppState = jetcasterAppState)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun GlobalNavigationContainer(
    jetcasterAppState: JetcasterAppState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val (discover, library) = remember { FocusRequester.createRefs() }
    val currentRoute
        by jetcasterAppState.currentRouteFlow.collectAsStateWithLifecycle(initialValue = null)

    NavigationDrawer(
        drawerContent = {
            val isClosed = it == DrawerValue.Closed
            Column(
                modifier = Modifier
                    .padding(JetcasterAppDefaults.overScanMargin.drawer.intoPaddingValues())
                    .focusProperties {
                        enter = {
                            when (currentRoute) {
                                Screen.Discover.route -> discover
                                Screen.Library.route -> library
                                else -> FocusRequester.Default
                            }
                        }
                    }
                    .focusGroup(),
            ) {
                NavigationDrawerItem(
                    selected = isClosed && currentRoute == Screen.Profile.route,
                    onClick = jetcasterAppState::navigateToProfile,
                    leadingContent = { Icon(painterResource(id = R.drawable.ic_person), contentDescription = null) },
                ) {
                    Column {
                        Text(text = "Name")
                        Text(
                            text = "Switch Account",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    selected = isClosed && currentRoute == Screen.Search.route,
                    onClick = jetcasterAppState::navigateToSearch,
                    leadingContent = {
                        Icon(
                            painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                        )
                    },
                ) {
                    Text(text = "Search")
                }
                NavigationDrawerItem(
                    selected = isClosed && currentRoute == Screen.Discover.route,
                    onClick = jetcasterAppState::navigateToDiscover,
                    leadingContent = {
                        Icon(
                            painterResource(id = R.drawable.ic_home),
                            contentDescription = null,
                        )
                    },
                    modifier = Modifier.focusRequester(discover),
                ) {
                    Text(text = "Discover")
                }
                NavigationDrawerItem(
                    selected = isClosed && currentRoute == Screen.Library.route,
                    onClick = jetcasterAppState::navigateToLibrary,
                    leadingContent = {
                        Icon(
                            painterResource(id = R.drawable.ic_video_library),
                            contentDescription = null,
                        )
                    },
                    modifier = Modifier.focusRequester(library),
                ) {
                    Text(text = "Library")
                }
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    selected = isClosed && currentRoute == Screen.Settings.route,
                    onClick = jetcasterAppState::navigateToSettings,
                    leadingContent = { Icon(painterResource(id = R.drawable.ic_settings), contentDescription = null) },
                ) {
                    Text(text = "Settings")
                }
            }
        },
        content = content,
        modifier = modifier,
    )
}

@Composable
private fun Route(jetcasterAppState: JetcasterAppState) {
    NavHost(navController = jetcasterAppState.navHostController, Screen.Discover.route) {
        composable(Screen.Discover.route) {
            GlobalNavigationContainer(jetcasterAppState = jetcasterAppState) {
                DiscoverScreen(
                    showPodcastDetails = {
                        jetcasterAppState.showPodcastDetails(it.uri)
                    },
                    playEpisode = {
                        jetcasterAppState.playEpisode()
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        composable(Screen.Library.route) {
            GlobalNavigationContainer(jetcasterAppState = jetcasterAppState) {
                LibraryScreen(
                    navigateToDiscover = jetcasterAppState::navigateToDiscover,
                    showPodcastDetails = {
                        jetcasterAppState.showPodcastDetails(it.uri)
                    },
                    playEpisode = {
                        jetcasterAppState.playEpisode()
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onPodcastSelected = {
                    jetcasterAppState.showPodcastDetails(it.uri)
                },
                modifier = Modifier
                    .padding(JetcasterAppDefaults.overScanMargin.default.intoPaddingValues())
                    .fillMaxSize(),
            )
        }

        composable(Screen.Podcast.route) {
            PodcastDetailsScreen(
                backToHomeScreen = jetcasterAppState::navigateToDiscover,
                playEpisode = {
                    jetcasterAppState.playEpisode()
                },
                showEpisodeDetails = { jetcasterAppState.showEpisodeDetails(it.uri) },
                modifier = Modifier
                    .padding(JetcasterAppDefaults.overScanMargin.podcast.intoPaddingValues())
                    .fillMaxSize(),
            )
        }

        composable(Screen.Episode.route) {
            EpisodeScreen(
                playEpisode = {
                    jetcasterAppState.playEpisode()
                },
                backToHome = jetcasterAppState::backToHome,
            )
        }

        composable(Screen.Player.route) {
            PlayerScreen(
                backToHome = jetcasterAppState::backToHome,
                modifier = Modifier.fillMaxSize(),
                showDetails = jetcasterAppState::showEpisodeDetails,
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(JetcasterAppDefaults.overScanMargin.default.intoPaddingValues()),
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(JetcasterAppDefaults.overScanMargin.default.intoPaddingValues()),
            )
        }
    }
}
