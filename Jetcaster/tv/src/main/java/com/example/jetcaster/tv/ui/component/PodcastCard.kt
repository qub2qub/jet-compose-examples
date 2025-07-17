package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CardScale
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Text
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
internal fun PodcastCard(podcastInfo: PodcastInfo, onClick: () -> Unit, modifier: Modifier = Modifier) {
    StandardCardContainer(
        imageCard = {
            Card(
                onClick = onClick,
                interactionSource = it,
                scale = CardScale.None,
                shape = CardDefaults.shape(RoundedCornerShape(12.dp)),
            ) {
                Thumbnail(
                    podcastInfo = podcastInfo,
                    size = JetcasterAppDefaults.thumbnailSize.podcast,
                )
            }
        },
        title = {
            Text(text = podcastInfo.title, modifier = Modifier.padding(top = 12.dp))
        },
        modifier = modifier,
    )
}
