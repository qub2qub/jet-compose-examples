package com.example.jetcaster.ui.podcasts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcaster.core.data.database.model.PodcastWithExtraInfo
import com.example.jetcaster.core.data.repository.PodcastStore
import com.example.jetcaster.core.model.PodcastInfo
import com.example.jetcaster.core.model.asExternalModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PodcastsViewModel @Inject constructor(podcastStore: PodcastStore) : ViewModel() {

    val uiState: StateFlow<PodcastsScreenState> =
        podcastStore.followedPodcastsSortedByLastEpisode(limit = 10).map {
            if (it.isNotEmpty()) {
                PodcastsScreenState.Loaded(it.map(PodcastMapper::map))
            } else {
                PodcastsScreenState.Empty
            }
        }.catch {
            emit(PodcastsScreenState.Empty)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = PodcastsScreenState.Loading,
        )
}

object PodcastMapper {

    /**
     * Maps from [Podcast].
     */
    fun map(podcastWithExtraInfo: PodcastWithExtraInfo): PodcastInfo = podcastWithExtraInfo.asExternalModel()
}

@ExperimentalHorologistApi
sealed interface PodcastsScreenState {

    data object Loading : PodcastsScreenState

    data class Loaded(val podcastList: List<PodcastInfo>) : PodcastsScreenState

    data object Empty : PodcastsScreenState
}
