package com.example.jetcaster.ui.home.library

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetcaster.R
import com.example.jetcaster.core.model.EpisodeInfo
import com.example.jetcaster.core.model.LibraryInfo
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.designsystem.theme.Keyline1
import com.example.jetcaster.ui.shared.EpisodeListItem
import com.example.jetcaster.util.fullWidthItem

fun LazyGridScope.libraryItems(
    library: LibraryInfo,
    navigateToPlayer: (EpisodeInfo) -> Unit,
    onQueueEpisode: (PlayerEpisode) -> Unit,
    removeFromQueue: (EpisodeInfo) -> Unit,
) {
    fullWidthItem {
        Text(
            text = stringResource(id = R.string.latest_episodes),
            modifier = Modifier.padding(
                start = Keyline1,
                top = 16.dp,
            ),
            style = MaterialTheme.typography.headlineMedium,
        )
    }

    items(
        library,
        key = { it.episode.uri },
    ) { item ->
        EpisodeListItem(
            episode = item.episode,
            podcast = item.podcast,
            onClick = navigateToPlayer,
            onQueueEpisode = onQueueEpisode,
            modifier = Modifier
                .fillMaxWidth()
                .animateItem(),
            removeFromQueue = removeFromQueue,
        )
    }
}
