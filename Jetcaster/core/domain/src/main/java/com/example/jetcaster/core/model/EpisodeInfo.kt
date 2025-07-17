package com.example.jetcaster.core.model

import com.example.jetcaster.core.data.database.model.Episode
import java.time.Duration
import java.time.OffsetDateTime

/**
 * External data layer representation of an episode.
 */
data class EpisodeInfo(
    val uri: String = "",
    val podcastUri: String = "",
    val title: String = "",
    val subTitle: String = "",
    val summary: String = "",
    val author: String = "",
    val published: OffsetDateTime = OffsetDateTime.MIN,
    val duration: Duration? = null,
    val mediaUrls: List<String> = emptyList(),
)

fun Episode.asExternalModel(): EpisodeInfo = EpisodeInfo(
    uri = uri,
    podcastUri = podcastUri,
    title = title,
    subTitle = subtitle ?: "",
    summary = summary ?: "",
    author = author ?: "",
    published = published,
    duration = duration,
    mediaUrls = mediaUrls,
)

fun EpisodeInfo.asDaoModel(): Episode = Episode(
    uri = uri,
    title = title,
    subtitle = subTitle,
    summary = summary,
    author = author,
    published = published,
    duration = duration,
    podcastUri = podcastUri,
    mediaUrls = mediaUrls,
)
