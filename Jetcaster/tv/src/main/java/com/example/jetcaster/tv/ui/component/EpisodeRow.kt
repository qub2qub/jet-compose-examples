package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.tv.model.EpisodeList
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EpisodeRow(
    playerEpisodeList: EpisodeList,
    onSelected: (PlayerEpisode) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal =
        Arrangement.spacedBy(JetcasterAppDefaults.gap.item),
    contentPadding: PaddingValues = JetcasterAppDefaults.padding.episodeRowContentPadding,
    focusRequester: FocusRequester = remember { FocusRequester() },
    lazyListState: LazyListState = remember(playerEpisodeList) { LazyListState() },
) {
    val firstItem = remember { FocusRequester() }
    var previousEpisodeListHash by remember { mutableIntStateOf(playerEpisodeList.hashCode()) }
    val isSameList = previousEpisodeListHash == playerEpisodeList.hashCode()

    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusProperties {
                enter = {
                    when {
                        lazyListState.layoutInfo.visibleItemsInfo.isEmpty() -> FocusRequester.Cancel
                        isSameList && focusRequester.restoreFocusedChild() -> FocusRequester.Cancel
                        else -> firstItem
                    }
                }
                exit = {
                    previousEpisodeListHash = playerEpisodeList.hashCode()
                    focusRequester.saveFocusedChild()
                    FocusRequester.Default
                }
            }
            .then(modifier),
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
    ) {
        itemsIndexed(playerEpisodeList) { index, item ->
            val cardModifier = if (index == 0) {
                Modifier.focusRequester(firstItem)
            } else {
                Modifier
            }
            EpisodeCard(
                playerEpisode = item,
                onClick = { onSelected(item) },
                modifier = cardModifier,
            )
        }
    }
}
