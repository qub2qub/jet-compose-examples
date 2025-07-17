package com.example.jetcaster.core.domain

import com.example.jetcaster.core.data.database.model.Episode
import com.example.jetcaster.core.data.testing.repository.TestEpisodeStore
import com.example.jetcaster.core.data.testing.repository.TestPodcastStore
import java.time.Duration
import java.time.OffsetDateTime
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class GetLatestFollowedEpisodesUseCaseTest {

    private val episodeStore = TestEpisodeStore()
    private val podcastStore = TestPodcastStore()

    val useCase = GetLatestFollowedEpisodesUseCase(
        episodeStore = episodeStore,
        podcastStore = podcastStore,
    )

    val testEpisodes = listOf(
        Episode(
            uri = "",
            podcastUri = testPodcasts[0].podcast.uri,
            title = "title1",
            published = OffsetDateTime.MIN,
            subtitle = "subtitle1",
            summary = "summary1",
            author = "author1",
            duration = Duration.ofMinutes(1),
            mediaUrls = listOf("Url1"),
        ),
        Episode(
            uri = "",
            podcastUri = testPodcasts[0].podcast.uri,
            title = "title2",
            published = OffsetDateTime.now(),
            subtitle = "subtitle2",
            summary = "summary2",
            author = "author2",
            duration = Duration.ofMinutes(1),
            mediaUrls = listOf("Url1"),
        ),
        Episode(
            uri = "",
            podcastUri = testPodcasts[1].podcast.uri,
            title = "title3",
            published = OffsetDateTime.MAX,
            subtitle = "subtitle3",
            summary = "summary3",
            author = "author3",
            duration = Duration.ofMinutes(1),
            mediaUrls = listOf("Url1"),
        ),
    )

    @Test
    fun whenNoFollowedPodcasts_emptyFlow() = runTest {
        val result = useCase()

        episodeStore.addEpisodes(testEpisodes)
        testPodcasts.forEach {
            podcastStore.addPodcast(it.podcast)
        }

        assertTrue(result.first().isEmpty())
    }

    @Test
    fun whenFollowedPodcasts_nonEmptyFlow() = runTest {
        val result = useCase()

        episodeStore.addEpisodes(testEpisodes)
        testPodcasts.forEach {
            podcastStore.addPodcast(it.podcast)
        }
        podcastStore.togglePodcastFollowed(testPodcasts[0].podcast.uri)

        assertTrue(result.first().isNotEmpty())
    }

    @Test
    fun whenFollowedPodcasts_sortedByPublished() = runTest {
        val result = useCase()

        episodeStore.addEpisodes(testEpisodes)
        testPodcasts.forEach {
            podcastStore.addPodcast(it.podcast)
        }
        podcastStore.togglePodcastFollowed(testPodcasts[0].podcast.uri)

        result.first().zipWithNext { ep1, ep2 ->
            ep1.episode.published > ep2.episode.published
        }.all { it }
    }
}
