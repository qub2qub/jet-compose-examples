package com.example.jetcaster.core.data.repository

import com.example.jetcaster.core.data.database.dao.EpisodesDao
import com.example.jetcaster.core.data.database.model.Episode
import com.example.jetcaster.core.data.database.model.EpisodeToPodcast
import kotlinx.coroutines.flow.Flow

interface EpisodeStore {
    /**
     * Returns a flow containing the episode given [episodeUri].
     */
    fun episodeWithUri(episodeUri: String): Flow<Episode>

    /**
     * Returns a flow containing the episode and corresponding podcast given an [episodeUri].
     */
    fun episodeAndPodcastWithUri(episodeUri: String): Flow<EpisodeToPodcast>

    /**
     * Returns a flow containing the list of episodes associated with the podcast with the
     * given [podcastUri].
     */
    fun episodesInPodcast(podcastUri: String, limit: Int = Integer.MAX_VALUE): Flow<List<EpisodeToPodcast>>

    /**
     * Returns a list of episodes for the given podcast URIs ordering by most recently published
     * to least recently published.
     */
    fun episodesInPodcasts(podcastUris: List<String>, limit: Int = Integer.MAX_VALUE): Flow<List<EpisodeToPodcast>>

    /**
     * Add a new [Episode] to this store.
     *
     * This automatically switches to the main thread to maintain thread consistency.
     */
    suspend fun addEpisodes(episodes: Collection<Episode>)

    /**
     * Deletes an [Episode] from this store.
     */
    suspend fun deleteEpisode(episode: Episode)

    suspend fun isEmpty(): Boolean
}

/**
 * A data repository for [Episode] instances.
 */
class LocalEpisodeStore(private val episodesDao: EpisodesDao) : EpisodeStore {
    /**
     * Returns a flow containing the episode given [episodeUri].
     */
    override fun episodeWithUri(episodeUri: String): Flow<Episode> {
        return episodesDao.episode(episodeUri)
    }

    override fun episodeAndPodcastWithUri(episodeUri: String): Flow<EpisodeToPodcast> = episodesDao.episodeAndPodcast(episodeUri)

    /**
     * Returns a flow containing the list of episodes associated with the podcast with the
     * given [podcastUri].
     */
    override fun episodesInPodcast(podcastUri: String, limit: Int): Flow<List<EpisodeToPodcast>> {
        return episodesDao.episodesForPodcastUri(podcastUri, limit)
    }

    /**
     * Returns a list of episodes for the given podcast URIs ordering by most recently published
     * to least recently published.
     */
    override fun episodesInPodcasts(podcastUris: List<String>, limit: Int): Flow<List<EpisodeToPodcast>> =
        episodesDao.episodesForPodcasts(podcastUris, limit)

    /**
     * Add a new [Episode] to this store.
     *
     * This automatically switches to the main thread to maintain thread consistency.
     */
    override suspend fun addEpisodes(episodes: Collection<Episode>) = episodesDao.insertAll(episodes)

    /**
     * Deletes an [Episode] from this store.
     */
    override suspend fun deleteEpisode(episode: Episode) {
        episodesDao.delete(episode)
    }

    override suspend fun isEmpty(): Boolean = episodesDao.count() == 0
}
