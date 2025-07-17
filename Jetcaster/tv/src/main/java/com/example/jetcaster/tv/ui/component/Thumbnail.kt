package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.designsystem.component.PodcastImage
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
fun Thumbnail(
    podcastInfo: PodcastInfo,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    size: DpSize = DpSize(
        JetcasterAppDefaults.cardWidth.medium,
        JetcasterAppDefaults.cardWidth.medium,
    ),
    contentScale: ContentScale = ContentScale.Crop,
) = Thumbnail(
    podcastInfo.imageUrl,
    modifier,
    shape,
    size,
    contentScale,
)

@Composable
fun Thumbnail(
    episode: PlayerEpisode,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    size: DpSize = DpSize(
        JetcasterAppDefaults.cardWidth.medium,
        JetcasterAppDefaults.cardWidth.medium,
    ),
    contentScale: ContentScale = ContentScale.Crop,
) = Thumbnail(
    episode.podcastImageUrl,
    modifier,
    shape,
    size,
    contentScale,
)

@Composable
fun Thumbnail(
    url: String,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    size: DpSize = DpSize(
        JetcasterAppDefaults.cardWidth.medium,
        JetcasterAppDefaults.cardWidth.medium,
    ),
    contentScale: ContentScale = ContentScale.Crop,
) = PodcastImage(
    podcastImageUrl = url,
    contentDescription = null,
    contentScale = contentScale,
    modifier = modifier
        .clip(shape)
        .size(size),
)
