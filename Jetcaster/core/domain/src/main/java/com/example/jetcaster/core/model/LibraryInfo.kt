package com.example.jetcaster.core.model

data class LibraryInfo(val episodes: List<PodcastToEpisodeInfo> = emptyList()) : List<PodcastToEpisodeInfo> by episodes
