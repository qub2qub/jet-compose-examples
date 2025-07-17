package com.example.jetcaster.ui.shared

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetcaster.R
import com.example.jetcaster.core.domain.testing.PreviewEpisodes
import com.example.jetcaster.core.domain.testing.PreviewPodcasts
import com.example.jetcaster.core.model.EpisodeInfo
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.designsystem.component.HtmlTextContainer
import com.example.jetcaster.designsystem.component.PodcastImage
import com.example.jetcaster.ui.theme.JetcasterTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun EpisodeListItem(
    episode: EpisodeInfo,
    podcast: PodcastInfo,
    onClick: (EpisodeInfo) -> Unit,
    removeFromQueue: (EpisodeInfo) -> Unit = {},
    onQueueEpisode: (PlayerEpisode) -> Unit,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    showPodcastImage: Boolean = true,
    showSummary: Boolean = false,
) {
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 40.dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            Surface(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surfaceContainer,
                onClick = { onClick(episode) },
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    // Top Part
                    EpisodeListItemHeader(
                        episode = episode,
                        podcast = podcast,
                        showPodcastImage = showPodcastImage,
                        showSummary = showSummary,
                        modifier = Modifier.padding(bottom = 8.dp),
                        imageModifier = imageModifier,
                    )

                    // Bottom Part
                    EpisodeListItemFooter(
                        episode = episode,
                        podcast = podcast,
                        onQueueEpisode = onQueueEpisode,
                    )
                }
            }
        }
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.EndToStart -> {
                removeFromQueue(episode)
            }

            SwipeToDismissBoxValue.StartToEnd -> {
            }

            SwipeToDismissBoxValue.Settled -> {
            }
        }
    }
}

@Composable
private fun EpisodeListItemFooter(
    episode: EpisodeInfo,
    podcast: PodcastInfo,
    onQueueEpisode: (PlayerEpisode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painterResource(id = R.drawable.ic_play_circle),
            contentDescription = stringResource(R.string.cd_play),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false, radius = 24.dp),
                ) { /* TODO */ }
                .size(48.dp)
                .padding(6.dp)
                .semantics { role = Role.Button },
        )

        val duration = episode.duration
        Text(
            text = when {
                duration != null -> {
                    // If we have the duration, we combine the date/duration via a
                    // formatted string
                    stringResource(
                        R.string.episode_date_duration,
                        MediumDateFormatter.format(episode.published),
                        duration.toMinutes().toInt(),
                    )
                }
                // Otherwise we just use the date
                else -> MediumDateFormatter.format(episode.published)
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
        )

        IconButton(
            onClick = {
                onQueueEpisode(
                    PlayerEpisode(
                        podcastInfo = podcast,
                        episodeInfo = episode,
                    ),
                )
            },
        ) {
            Icon(
                painterResource(id = R.drawable.ic_playlist_add),
                contentDescription = stringResource(R.string.cd_add),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        IconButton(
            onClick = { /* TODO */ },
        ) {
            Icon(
                painterResource(id = R.drawable.ic_more_vert),
                contentDescription = stringResource(R.string.cd_more),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun EpisodeListItemHeader(
    episode: EpisodeInfo,
    podcast: PodcastInfo,
    showPodcastImage: Boolean,
    showSummary: Boolean,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Column(
            modifier =
            Modifier
                .weight(1f)
                .padding(end = 16.dp),
        ) {
            Text(
                text = episode.title,
                maxLines = 2,
                minLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp),
            )

            if (showSummary) {
                HtmlTextContainer(text = episode.summary) {
                    Text(
                        text = it,
                        maxLines = 2,
                        minLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            } else {
                Text(
                    text = podcast.title,
                    maxLines = 2,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
        if (showPodcastImage) {
            EpisodeListItemImage(
                podcast = podcast,
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.medium),
                imageModifier = imageModifier,
            )
        }
    }
}

@Composable
private fun EpisodeListItemImage(podcast: PodcastInfo, modifier: Modifier = Modifier, imageModifier: Modifier = Modifier) {
    PodcastImage(
        podcastImageUrl = podcast.imageUrl,
        contentDescription = null,
        modifier = modifier,
        imageModifier = imageModifier,
    )
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun EpisodeListItemPreview() {
    JetcasterTheme {
        EpisodeListItem(
            episode = PreviewEpisodes[0],
            podcast = PreviewPodcasts[0],
            onClick = {},
            onQueueEpisode = {},
            showSummary = true,
        )
    }
}

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
}
