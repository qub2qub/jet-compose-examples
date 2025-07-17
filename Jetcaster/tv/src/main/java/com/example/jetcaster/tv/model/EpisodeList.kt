package com.example.jetcaster.tv.model

import androidx.compose.runtime.Immutable
import com.example.jetcaster.core.player.model.PlayerEpisode

@Immutable
data class EpisodeList(val member: List<PlayerEpisode>) : List<PlayerEpisode> by member
