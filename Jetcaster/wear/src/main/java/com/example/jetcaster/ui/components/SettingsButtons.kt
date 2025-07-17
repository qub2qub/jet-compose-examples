package com.example.jetcaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.jetcaster.R
import com.example.jetcaster.ui.player.PlayerUiState
import com.google.android.horologist.audio.AudioOutput
import com.google.android.horologist.audio.ui.VolumeUiState
import com.google.android.horologist.audio.ui.material3.components.actions.SettingsButton
import com.google.android.horologist.audio.ui.material3.components.actions.VolumeButtonWithBadge
import com.google.android.horologist.audio.ui.material3.components.toAudioOutputUi

/**
 * Settings buttons for the Jetcaster media app.
 * Add to queue and Set Volume.
 */
@Composable
fun SettingsButtons(
    volumeUiState: VolumeUiState,
    onVolumeClick: () -> Unit,
    playerUiState: PlayerUiState,
    onPlaybackSpeedChange: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(0.8124f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            VolumeButtonWithBadge(
                onOutputClick = onVolumeClick,
                audioOutputUi = AudioOutput.BluetoothHeadset(id = "id", name = "name")
                    .toAudioOutputUi(),
                volumeUiState = volumeUiState,
            )
        }

        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            PlaybackSpeedButton(
                currentPlayerSpeed = playerUiState.episodePlayerState
                    .playbackSpeed.toMillis().toFloat() / 1000,
                onPlaybackSpeedChange = onPlaybackSpeedChange,
                enabled = enabled,
            )
        }
    }
}

@Composable
fun PlaybackSpeedButton(
    currentPlayerSpeed: Float,
    onPlaybackSpeedChange: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SettingsButton(
        modifier = modifier,
        onClick = onPlaybackSpeedChange,
        enabled = enabled,
        imageVector =
        when (currentPlayerSpeed) {
            1f -> ImageVector.vectorResource(R.drawable.speed_1x)
            1.5f -> ImageVector.vectorResource(R.drawable.speed_15x)
            else -> {
                ImageVector.vectorResource(R.drawable.speed_2x)
            }
        },
        contentDescription = stringResource(R.string.change_playback_speed_content_description),
    )
}
