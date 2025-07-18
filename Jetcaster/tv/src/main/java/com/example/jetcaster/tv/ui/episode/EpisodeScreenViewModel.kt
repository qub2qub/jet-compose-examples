package com.example.jetcaster.tv.ui.episode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcaster.core.data.repository.EpisodeStore
import com.example.jetcaster.core.data.repository.PodcastsRepository
import com.example.jetcaster.core.player.EpisodePlayer
import com.example.jetcaster.core.player.model.PlayerEpisode
import com.example.jetcaster.core.player.model.toPlayerEpisode
import com.example.jetcaster.tv.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class EpisodeScreenViewModel @Inject constructor(
    handle: SavedStateHandle,
    podcastsRepository: PodcastsRepository,
    episodeStore: EpisodeStore,
    private val episodePlayer: EpisodePlayer,
) : ViewModel() {

    private val episodeUriFlow = handle.getStateFlow<String?>(Screen.Episode.PARAMETER_NAME, null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val episodeToPodcastFlow = episodeUriFlow.flatMapLatest {
        if (it != null) {
            episodeStore.episodeAndPodcastWithUri(it)
        } else {
            flowOf(null)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null,
    )

    val uiStateFlow = episodeToPodcastFlow.map {
        if (it != null) {
            EpisodeScreenUiState.Ready(it.toPlayerEpisode())
        } else {
            EpisodeScreenUiState.Error
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        EpisodeScreenUiState.Loading,
    )

    fun addPlayList(episode: PlayerEpisode) {
        episodePlayer.addToQueue(episode)
    }

    fun play(playerEpisode: PlayerEpisode) {
        episodePlayer.play(playerEpisode)
    }

    init {
        viewModelScope.launch {
            podcastsRepository.updatePodcasts(false)
        }
    }
}

sealed interface EpisodeScreenUiState {
    data object Loading : EpisodeScreenUiState
    data object Error : EpisodeScreenUiState
    data class Ready(val playerEpisode: PlayerEpisode) : EpisodeScreenUiState
}
