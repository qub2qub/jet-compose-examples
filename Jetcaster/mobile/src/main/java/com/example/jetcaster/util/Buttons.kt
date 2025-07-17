package com.example.jetcaster.util

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.IconToggleButtonShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetcaster.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ToggleFollowPodcastIconButton(isFollowed: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconToggleButton(
        checked = isFollowed,
        onCheckedChange = { onClick() },
        modifier = modifier,
        colors = IconToggleButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary,
            checkedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            checkedContentColor = MaterialTheme.colorScheme.secondary,
        ),
        shapes = IconToggleButtonShapes(
            shape = RoundedCornerShape(10.dp),
            pressedShape = if (isFollowed) RoundedCornerShape(10.dp) else CircleShape,
            checkedShape = CircleShape,
        ),
    ) {
        Icon(
            // TODO: think about animating these icons
            painter = when {
                isFollowed -> painterResource(id = R.drawable.ic_check)
                else -> painterResource(id = R.drawable.ic_add)
            },
            contentDescription = when {
                isFollowed -> stringResource(R.string.cd_following)
                else -> stringResource(R.string.cd_not_following)
            },
        )
    }
}
