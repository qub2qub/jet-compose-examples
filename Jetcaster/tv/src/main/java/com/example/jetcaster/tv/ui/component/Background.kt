package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.designsystem.component.ImageBackgroundRadialGradientScrim

@Composable
internal fun BackgroundContainer(
    playerEpisode: PlayerEpisode,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) = BackgroundContainer(
    imageUrl = playerEpisode.podcastImageUrl,
    modifier,
    contentAlignment,
    content,
)

@Composable
internal fun BackgroundContainer(
    podcastInfo: PodcastInfo,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) = BackgroundContainer(imageUrl = podcastInfo.imageUrl, modifier, contentAlignment, content)

@Composable
internal fun BackgroundContainer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier, contentAlignment = contentAlignment) {
        Background(imageUrl = imageUrl, modifier = Modifier.fillMaxSize())
        content()
    }
}

@Composable
private fun Background(imageUrl: String, modifier: Modifier = Modifier) {
    ImageBackgroundRadialGradientScrim(
        url = imageUrl,
        colors = listOf(Color.Black, Color.Transparent),
        modifier = modifier,
    )
}
