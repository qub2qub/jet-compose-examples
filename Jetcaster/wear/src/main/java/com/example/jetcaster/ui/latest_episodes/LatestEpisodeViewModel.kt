package com.example.jetcaster.ui.latest_episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcaster.core.domain.GetLatestFollowedEpisodesUseCase
import com.example.jetcaster.core.player.EpisodePlayer
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.core.player.model.toPlayerEpisode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class LatestEpisodeViewModel @Inject constructor(
    episodesFromFavouritePodcasts: GetLatestFollowedEpisodesUseCase,
    private val episodePlayer: EpisodePlayer,
) : ViewModel() {

    val uiState: StateFlow<LatestEpisodeScreenState> =
        episodesFromFavouritePodcasts.invoke().map { episodeToPodcastList ->
            if (episodeToPodcastList.isNotEmpty()) {
                LatestEpisodeScreenState.Loaded(
                    episodeToPodcastList.map {
                        it.toPlayerEpisode()
                    },
                )
            } else {
                LatestEpisodeScreenState.Empty
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LatestEpisodeScreenState.Loading,
        )

    fun onPlayEpisodes(episodes: List<PlayerEpisode>) {
        episodePlayer.currentEpisode = episodes[0]
        episodePlayer.play(episodes)
    }

    fun onPlayEpisode(episode: PlayerEpisode) {
        episodePlayer.currentEpisode = episode
        episodePlayer.play()
    }
}

sealed interface LatestEpisodeScreenState {

    data object Loading : LatestEpisodeScreenState

    data class Loaded(val episodeList: List<PlayerEpisode>) : LatestEpisodeScreenState

    data object Empty : LatestEpisodeScreenState
}
