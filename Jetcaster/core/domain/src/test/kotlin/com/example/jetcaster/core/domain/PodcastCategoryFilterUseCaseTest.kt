package com.example.jetcaster.core.domain

import com.example.jetcaster.core.data.database.model.Category
import com.example.jetcaster.core.data.database.model.Episode
import com.example.jetcaster.core.data.database.model.EpisodeToPodcast
import com.example.jetcaster.core.data.database.model.Podcast
import com.example.jetcaster.core.data.database.model.PodcastWithExtraInfo
import com.example.jetcaster.core.data.testing.repository.TestCategoryStore
import com.example.jetcaster.core.model.asExternalModel
import com.example.jetcaster.core.model.asPodcastToEpisodeInfo
import java.time.Duration
import java.time.OffsetDateTime
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PodcastCategoryFilterUseCaseTest {

    private val categoriesStore = TestCategoryStore()
    private val testEpisodeToPodcast = listOf(
        EpisodeToPodcast().apply {
            episode = Episode(
                "",
                "",
                "Episode 1",
                published = OffsetDateTime.now(),
                subtitle = "subtitle1",
                summary = "summary1",
                author = "author1",
                duration = Duration.ofMinutes(1),
                mediaUrls = listOf("Url1"),
            )
            _podcasts = listOf(
                Podcast(
                    uri = "",
                    title = "Podcast 1",
                ),
            )
        },
        EpisodeToPodcast().apply {
            episode = Episode(
                "",
                "",
                "Episode 2",
                published = OffsetDateTime.now(),
                subtitle = "subtitle2",
                summary = "summary2",
                author = "author2",
                duration = Duration.ofMinutes(1),
                mediaUrls = listOf("Url1"),
            )
            _podcasts = listOf(
                Podcast(
                    uri = "",
                    title = "Podcast 2",
                ),
            )
        },
        EpisodeToPodcast().apply {
            episode = Episode(
                "",
                "",
                "Episode 3",
                published = OffsetDateTime.now(),
                subtitle = "subtitle3",
                summary = "summary3",
                author = "author2",
                duration = Duration.ofMinutes(1),
                mediaUrls = listOf("Url1"),
            )
            _podcasts = listOf(
                Podcast(
                    uri = "",
                    title = "Podcast 3",
                ),
            )
        },
    )
    private val testCategory = Category(1, "Technology")

    val useCase = PodcastCategoryFilterUseCase(
        categoryStore = categoriesStore,
    )

    @Test
    fun whenCategoryNull_emptyFlow() = runTest {
        val resultFlow = useCase(null)

        categoriesStore.setEpisodesFromPodcast(testCategory.id, testEpisodeToPodcast)
        categoriesStore.setPodcastsInCategory(testCategory.id, testPodcasts)

        val result = resultFlow.first()
        assertTrue(result.topPodcasts.isEmpty())
        assertTrue(result.episodes.isEmpty())
    }

    @Test
    fun whenCategoryNotNull_validFlow() = runTest {
        val resultFlow = useCase(testCategory.asExternalModel())

        categoriesStore.setEpisodesFromPodcast(testCategory.id, testEpisodeToPodcast)
        categoriesStore.setPodcastsInCategory(testCategory.id, testPodcasts)

        val result = resultFlow.first()
        assertEquals(
            testPodcasts.map { it.asExternalModel() },
            result.topPodcasts,
        )
        assertEquals(
            testEpisodeToPodcast.map { it.asPodcastToEpisodeInfo() },
            result.episodes,
        )
    }

    @Test
    fun whenCategoryInfoNotNull_verifyLimitFlow() = runTest {
        val resultFlow = useCase(testCategory.asExternalModel())

        categoriesStore.setEpisodesFromPodcast(
            testCategory.id,
            List(8) { testEpisodeToPodcast }.flatten(),
        )
        categoriesStore.setPodcastsInCategory(
            testCategory.id,
            List(4) { testPodcasts }.flatten(),
        )

        val result = resultFlow.first()
        assertEquals(20, result.episodes.size)
        assertEquals(10, result.topPodcasts.size)
    }
}

val testPodcasts = listOf(
    PodcastWithExtraInfo().apply {
        podcast = Podcast(uri = "nia", title = "Now in Android")
    },
    PodcastWithExtraInfo().apply {
        podcast = Podcast(uri = "adb", title = "Android Developers Backstage")
    },
    PodcastWithExtraInfo().apply {
        podcast = Podcast(uri = "techcrunch", title = "Techcrunch")
    },
)
