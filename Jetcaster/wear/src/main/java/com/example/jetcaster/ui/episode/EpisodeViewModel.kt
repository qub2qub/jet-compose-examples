package com.example.jetcaster.ui.episode

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcaster.core.data.database.model.EpisodeToPodcast
import com.example.jetcaster.core.data.repository.EpisodeStore
import com.example.jetcaster.core.player.EpisodePlayer
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.ui.Episode
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel that handles the business logic and screen state of the Episode screen.
 */
@HiltViewModel
class EpisodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    episodeStore: EpisodeStore,
    private val episodePlayer: EpisodePlayer,
) : ViewModel() {

    private val episodeUri: String =
        savedStateHandle.get<String>(Episode.EPISODE_URI).let {
            Uri.decode(it)
        }

    private val episodeFlow = if (episodeUri != null) {
        episodeStore.episodeAndPodcastWithUri(episodeUri)
    } else {
        flowOf(null)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null,
    )

    val uiState: StateFlow<EpisodeScreenState> =
        episodeFlow.map {
            if (it != null) {
                EpisodeScreenState.Loaded(it)
            } else {
                EpisodeScreenState.Empty
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            EpisodeScreenState.Loading,
        )

    fun onPlayEpisode(episode: PlayerEpisode) {
        episodePlayer.currentEpisode = episode
        episodePlayer.play()
    }
    fun addToQueue(episode: PlayerEpisode) {
        episodePlayer.addToQueue(episode)
    }
}

@ExperimentalHorologistApi
sealed interface EpisodeScreenState {

    data object Loading : EpisodeScreenState

    data class Loaded(val episode: EpisodeToPodcast) : EpisodeScreenState

    data object Empty : EpisodeScreenState
}
