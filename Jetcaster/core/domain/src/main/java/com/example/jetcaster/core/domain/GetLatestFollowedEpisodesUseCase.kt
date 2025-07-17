package com.example.jetcaster.core.domain

import com.example.jetcaster.core.data.database.model.EpisodeToPodcast
import com.example.jetcaster.core.data.repository.EpisodeStore
import com.example.jetcaster.core.data.repository.PodcastStore
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * A use case which returns all the latest episodes from all the podcasts the user follows.
 */
class GetLatestFollowedEpisodesUseCase @Inject constructor(
    private val episodeStore: EpisodeStore,
    private val podcastStore: PodcastStore,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<EpisodeToPodcast>> = podcastStore.followedPodcastsSortedByLastEpisode()
        .flatMapLatest { followedPodcasts ->
            episodeStore.episodesInPodcasts(
                followedPodcasts.map { it.podcast.uri },
                followedPodcasts.size * 5,
            )
        }
}
