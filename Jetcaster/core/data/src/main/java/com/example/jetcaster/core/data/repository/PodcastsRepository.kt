package com.example.jetcaster.core.data.repository

import com.example.jetcaster.core.data.Dispatcher
import com.example.jetcaster.core.data.JetcasterDispatchers
import com.example.jetcaster.core.data.database.dao.TransactionRunner
import com.example.jetcaster.core.data.network.PodcastRssResponse
import com.example.jetcaster.core.data.network.PodcastsFetcher
import com.example.jetcaster.core.data.network.SampleFeeds
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Data repository for Podcasts.
 */
class PodcastsRepository @Inject constructor(
    private val podcastsFetcher: PodcastsFetcher,
    private val podcastStore: PodcastStore,
    private val episodeStore: EpisodeStore,
    private val categoryStore: CategoryStore,
    private val transactionRunner: TransactionRunner,
    @Dispatcher(JetcasterDispatchers.Main) mainDispatcher: CoroutineDispatcher,
) {
    private var refreshingJob: Job? = null

    private val scope = CoroutineScope(mainDispatcher)

    suspend fun updatePodcasts(force: Boolean) {
        if (refreshingJob?.isActive == true) {
            refreshingJob?.join()
        } else if (force || podcastStore.isEmpty()) {
            val job = scope.launch {
                // Now fetch the podcasts, and add each to each store
                podcastsFetcher(SampleFeeds)
                    .filter { it is PodcastRssResponse.Success }
                    .map { it as PodcastRssResponse.Success }
                    .collect { (podcast, episodes, categories) ->
                        transactionRunner {
                            podcastStore.addPodcast(podcast)
                            episodeStore.addEpisodes(episodes)

                            categories.forEach { category ->
                                // First insert the category
                                val categoryId = categoryStore.addCategory(category)
                                // Now we can add the podcast to the category
                                categoryStore.addPodcastToCategory(
                                    podcastUri = podcast.uri,
                                    categoryId = categoryId,
                                )
                            }
                        }
                    }
            }
            refreshingJob = job
            // We need to wait here for the job to finish, otherwise the coroutine completes ~immediatelly
            job.join()
        }
    }
}
