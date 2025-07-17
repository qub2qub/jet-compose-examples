package com.example.jetcaster.tv.ui.discover

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import com.example.jetcaster.core.model.CategoryInfo
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.tv.model.CategoryInfoList
import com.example.jetcaster.tv.model.EpisodeList
import com.example.jetcaster.tv.model.PodcastList
import com.example.jetcaster.tv.ui.component.Catalog
import com.example.jetcaster.tv.ui.component.Loading
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
fun DiscoverScreen(
    showPodcastDetails: (PodcastInfo) -> Unit,
    playEpisode: (PlayerEpisode) -> Unit,
    modifier: Modifier = Modifier,
    discoverScreenViewModel: DiscoverScreenViewModel = hiltViewModel(),
) {
    val uiState by discoverScreenViewModel.uiState.collectAsState()

    when (val s = uiState) {
        DiscoverScreenUiState.Loading -> {
            Loading(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
            )
        }

        is DiscoverScreenUiState.Ready -> {
            CatalogWithCategorySelection(
                categoryInfoList = s.categoryInfoList,
                podcastList = s.podcastList,
                selectedCategory = s.selectedCategory,
                latestEpisodeList = s.latestEpisodeList,
                onPodcastSelected = showPodcastDetails,
                onCategorySelected = discoverScreenViewModel::selectCategory,
                onEpisodeSelected = {
                    discoverScreenViewModel.play(it)
                    playEpisode(it)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CatalogWithCategorySelection(
    categoryInfoList: CategoryInfoList,
    podcastList: PodcastList,
    selectedCategory: CategoryInfo,
    latestEpisodeList: EpisodeList,
    onPodcastSelected: (PodcastInfo) -> Unit,
    onEpisodeSelected: (PlayerEpisode) -> Unit,
    onCategorySelected: (CategoryInfo) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val (focusRequester, selectedTab) = remember {
        FocusRequester.createRefs()
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val selectedTabIndex = categoryInfoList.indexOf(selectedCategory)

    Catalog(
        podcastList = podcastList,
        latestEpisodeList = latestEpisodeList,
        onPodcastSelected = {
            focusRequester.saveFocusedChild()
            onPodcastSelected(it)
        },
        onEpisodeSelected = {
            focusRequester.saveFocusedChild()
            onEpisodeSelected(it)
        },
        modifier = modifier.focusRequester(focusRequester),
        state = state,
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.focusProperties {
                enter = {
                    selectedTab
                }
            },
        ) {
            categoryInfoList.forEachIndexed { index, category ->
                val tabModifier = if (selectedTabIndex == index) {
                    Modifier.focusRequester(selectedTab)
                } else {
                    Modifier
                }

                Tab(
                    selected = index == selectedTabIndex,
                    onFocus = {
                        onCategorySelected(category)
                    },
                    modifier = tabModifier,
                ) {
                    Text(
                        text = category.name,
                        modifier = Modifier.padding(JetcasterAppDefaults.padding.tab),
                    )
                }
            }
        }
    }
}
