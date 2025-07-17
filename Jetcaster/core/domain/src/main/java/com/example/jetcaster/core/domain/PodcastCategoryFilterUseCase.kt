package com.example.jetcaster.core.domain

import com.example.jetcaster.core.data.database.model.Category
import com.example.jetcaster.core.data.repository.CategoryStore
import com.example.jetcaster.core.model.CategoryInfo
import com.example.jetcaster.core.model.PodcastCategoryFilterResult
import com.example.jetcaster.core.model.asExternalModel
import com.example.jetcaster.core.model.asPodcastToEpisodeInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

/**
 *  A use case which returns top podcasts and matching episodes in a given [Category].
 */
class PodcastCategoryFilterUseCase @Inject constructor(private val categoryStore: CategoryStore) {
    operator fun invoke(category: CategoryInfo?): Flow<PodcastCategoryFilterResult> {
        if (category == null) {
            return flowOf(PodcastCategoryFilterResult())
        }

        val recentPodcastsFlow = categoryStore.podcastsInCategorySortedByPodcastCount(
            category.id,
            limit = 10,
        )

        val episodesFlow = categoryStore.episodesFromPodcastsInCategory(
            category.id,
            limit = 20,
        )

        // Combine our flows and collect them into the view state StateFlow
        return combine(recentPodcastsFlow, episodesFlow) { topPodcasts, episodes ->
            PodcastCategoryFilterResult(
                topPodcasts = topPodcasts.map { it.asExternalModel() },
                episodes = episodes.map { it.asPodcastToEpisodeInfo() },
            )
        }
    }
}
