package com.example.jetcaster.core.data.testing.repository

import com.example.jetcaster.core.data.database.model.Category
import com.example.jetcaster.core.data.database.model.EpisodeToPodcast
import com.example.jetcaster.core.data.database.model.PodcastWithExtraInfo
import com.example.jetcaster.core.data.repository.CategoryStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/**
 * A [CategoryStore] used for testing.
 *
 * // TODO: Move to :testing module upon merging PR #1379
 */
class TestCategoryStore : CategoryStore {

    private val categoryFlow = MutableStateFlow<List<Category>>(emptyList())
    private val podcastsInCategoryFlow =
        MutableStateFlow<Map<Long, List<PodcastWithExtraInfo>>>(emptyMap())
    private val episodesFromPodcasts =
        MutableStateFlow<Map<Long, List<EpisodeToPodcast>>>(emptyMap())

    override fun categoriesSortedByPodcastCount(limit: Int): Flow<List<Category>> = categoryFlow

    override fun podcastsInCategorySortedByPodcastCount(categoryId: Long, limit: Int): Flow<List<PodcastWithExtraInfo>> =
        podcastsInCategoryFlow.map {
            it[categoryId]?.take(limit) ?: emptyList()
        }

    override fun episodesFromPodcastsInCategory(categoryId: Long, limit: Int): Flow<List<EpisodeToPodcast>> = episodesFromPodcasts.map {
        it[categoryId]?.take(limit) ?: emptyList()
    }

    override suspend fun addCategory(category: Category): Long = -1

    override suspend fun addPodcastToCategory(podcastUri: String, categoryId: Long) {}

    override fun getCategory(name: String): Flow<Category?> = flowOf()

    /**
     * Test-only API for setting the list of categories backed by this [TestCategoryStore].
     */
    fun setCategories(categories: List<Category>) {
        categoryFlow.value = categories
    }

    /**
     * Test-only API for setting the list of podcasts in a category backed by this
     * [TestCategoryStore].
     */
    fun setPodcastsInCategory(categoryId: Long, podcastsInCategory: List<PodcastWithExtraInfo>) {
        podcastsInCategoryFlow.update {
            it + Pair(categoryId, podcastsInCategory)
        }
    }

    /**
     * Test-only API for setting the list of podcasts in a category backed by this
     * [TestCategoryStore].
     */
    fun setEpisodesFromPodcast(categoryId: Long, podcastsInCategory: List<EpisodeToPodcast>) {
        episodesFromPodcasts.update {
            it + Pair(categoryId, podcastsInCategory)
        }
    }
}
