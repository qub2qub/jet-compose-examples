package com.example.jetcaster.core.domain.testing

import com.example.jetcaster.core.model.CategoryInfo
import com.example.jetcaster.core.model.EpisodeInfo
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.core.model.PodcastToEpisodeInfo
import com.example.jetcaster.core.player.model.PlayerEpisode
import java.time.OffsetDateTime
import java.time.ZoneOffset

val PreviewCategories = listOf(
    CategoryInfo(id = 1, name = "Crime"),
    CategoryInfo(id = 2, name = "News"),
    CategoryInfo(id = 3, name = "Comedy"),
)

val PreviewPodcasts = listOf(
    PodcastInfo(
        uri = "fakeUri://podcast/1",
        title = "Android Developers Backstage",
        author = "Android Developers",
        isSubscribed = true,
        lastEpisodeDate = OffsetDateTime.now(),
    ),
    PodcastInfo(
        uri = "fakeUri://podcast/2",
        title = "Google Developers podcast",
        author = "Google Developers",
        lastEpisodeDate = OffsetDateTime.now(),
    ),
)

val PreviewEpisodes = listOf(
    EpisodeInfo(
        uri = "fakeUri://episode/1",
        title = "Episode 140: Lorem ipsum dolor",
        summary = "In this episode, Romain, Chet and Tor talked with Mady Melor and Artur " +
            "Tsurkan from the System UI team about... Bubbles!",
        published = OffsetDateTime.of(
            2020, 6, 2, 9,
            27, 0, 0, ZoneOffset.of("-0800"),
        ),
    ),
)

val PreviewPlayerEpisodes = listOf(
    PlayerEpisode(
        PreviewPodcasts[0],
        PreviewEpisodes[0],
    ),
)

val PreviewPodcastEpisodes = listOf(
    PodcastToEpisodeInfo(
        podcast = PreviewPodcasts[0],
        episode = PreviewEpisodes[0],
    ),
)
