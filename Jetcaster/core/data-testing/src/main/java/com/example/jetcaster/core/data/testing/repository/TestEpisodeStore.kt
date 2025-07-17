package com.example.jetcaster.core.data.testing.repository

import com.example.jetcaster.core.data.database.model.Episode
import com.example.jetcaster.core.data.database.model.EpisodeToPodcast
import com.example.jetcaster.core.data.repository.EpisodeStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

// TODO: Move to :testing module upon merging PR #1379
class TestEpisodeStore : EpisodeStore {

    private val episodesFlow = MutableStateFlow<List<Episode>>(listOf())
    override fun episodeWithUri(episodeUri: String): Flow<Episode> = episodesFlow.map { episodes ->
        episodes.first { it.uri == episodeUri }
    }

    override fun episodeAndPodcastWithUri(episodeUri: String): Flow<EpisodeToPodcast> = episodesFlow.map { episodes ->
        val e = episodes.first {
            it.uri == episodeUri
        }
        EpisodeToPodcast().apply {
            episode = e
            _podcasts = emptyList()
        }
    }

    override fun episodesInPodcast(podcastUri: String, limit: Int): Flow<List<EpisodeToPodcast>> = episodesFlow.map { episodes ->
        episodes.filter {
            it.podcastUri == podcastUri
        }.map { e ->
            EpisodeToPodcast().apply {
                episode = e
            }
        }
    }

    override fun episodesInPodcasts(podcastUris: List<String>, limit: Int): Flow<List<EpisodeToPodcast>> = episodesFlow.map { episodes ->
        episodes.filter {
            podcastUris.contains(it.podcastUri)
        }.map { ep ->
            EpisodeToPodcast().apply {
                episode = ep
            }
        }
    }

    override suspend fun addEpisodes(episodes: Collection<Episode>) = episodesFlow.update {
        it + episodes
    }

    override suspend fun deleteEpisode(episode: Episode) = episodesFlow.update {
        it - episode
    }

    override suspend fun isEmpty(): Boolean = episodesFlow.first().isEmpty()
}
