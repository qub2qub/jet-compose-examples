package com.example.jetcaster.core.model

import com.example.jetcaster.core.data.database.model.EpisodeToPodcast

data class PodcastToEpisodeInfo(val episode: EpisodeInfo, val podcast: PodcastInfo)

fun EpisodeToPodcast.asPodcastToEpisodeInfo(): PodcastToEpisodeInfo = PodcastToEpisodeInfo(
    episode = episode.asExternalModel(),
    podcast = podcast.asExternalModel(),
)
