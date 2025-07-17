package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
internal fun EpisodeDetails(
    playerEpisode: PlayerEpisode,
    modifier: Modifier = Modifier,
    controls: (@Composable () -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(JetcasterAppDefaults.gap.item),
    content: @Composable ColumnScope.() -> Unit,
) {
    TwoColumn(
        modifier = modifier,
        first = {
            Thumbnail(
                playerEpisode,
                size = JetcasterAppDefaults.thumbnailSize.episodeDetails,
            )
        },
        second = {
            Column(
                modifier = modifier,
                verticalArrangement = verticalArrangement,
            ) {
                EpisodeAuthor(playerEpisode = playerEpisode)
                EpisodeTitle(playerEpisode = playerEpisode)
                content()
                if (controls != null) {
                    controls()
                }
            }
        },
    )
}

@Composable
internal fun EpisodeAuthor(
    playerEpisode: PlayerEpisode,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Text(text = playerEpisode.author, modifier = modifier, style = style)
}

@Composable
internal fun EpisodeTitle(
    playerEpisode: PlayerEpisode,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
) {
    Text(text = playerEpisode.title, modifier = modifier, style = style)
}
