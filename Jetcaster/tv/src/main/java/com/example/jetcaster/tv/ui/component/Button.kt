package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ButtonScale
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import com.example.jetcaster.tv.R

@Composable
internal fun PlayButton(onClick: () -> Unit, modifier: Modifier = Modifier, scale: ButtonScale = ButtonDefaults.scale()) = ButtonWithIcon(
    iconId = R.drawable.ic_play_arrow,
    label = stringResource(R.string.label_play),
    onClick = onClick,
    modifier = modifier,
    scale = scale,
)

@Composable
internal fun EnqueueButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = R.drawable.ic_playlist_add),
            contentDescription = stringResource(R.string.label_add_playlist),
        )
    }
}

@Composable
internal fun InfoButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = R.drawable.ic_info),
            contentDescription = stringResource(R.string.label_info),
        )
    }
}

@Composable
internal fun PreviousButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = R.drawable.ic_skip_previous),
            contentDescription = stringResource(R.string.label_previous_episode),
        )
    }
}

@Composable
internal fun NextButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = R.drawable.ic_skip_next),
            contentDescription = stringResource(R.string.label_next_episode),
        )
    }
}

@Composable
internal fun PlayPauseButton(isPlaying: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val (icon, description) = if (isPlaying) {
        painterResource(id = R.drawable.ic_pause) to stringResource(R.string.label_pause)
    } else {
        painterResource(id = R.drawable.ic_play_arrow) to stringResource(R.string.label_play)
    }
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(icon, description, modifier = Modifier.size(48.dp))
    }
}

@Composable
internal fun RewindButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = R.drawable.ic_replay_10),
            contentDescription = stringResource(R.string.label_rewind),
        )
    }
}

@Composable
internal fun SkipButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = R.drawable.ic_forward_10),
            contentDescription = stringResource(R.string.label_skip),
        )
    }
}
